package com.ruoyi.hardware.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.hardware.mapper.HardwareMapper;
import com.ruoyi.hardware.service.HardwareService;
import com.ruoyi.installation.domain.Out;
import com.ruoyi.installation.domain.OutChild;
import com.ruoyi.software.domain.Software;
import com.ruoyi.software.domain.SoftwareProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static java.lang.System.out;

@Service
//硬件Service业务层处理   实现类
public class HardwareServiceImpl extends BaseServiceImpl implements HardwareService {
    @Autowired
    HardwareMapper hardwareMapper;
    @Autowired
    private HardwareService hardwareService;

    //查询硬件列表
    @Override
    public List<Hardware> selectHardwareList(Hardware hardware) {
        List<Hardware> hardwares = hardwareMapper.selectHardwareList((Hardware) initDomainCreate(hardware));
        createByReplace(hardwares);
        return hardwares;
    }

    //添加硬件，同时添加一条子项
    @Override
    @Transactional
    public int insertHardware(Hardware hardware) {
        //判断影响行数是否大于0
        if (hardwareMapper.insertHardware((Hardware) initDomainCreate(hardware)) > Constants.ZERO){
            HardwareChild hardwareChild = new HardwareChild();
            //把主表ID拿给子表用主键ID
            hardwareChild.setHardwareId(hardware.getId());
            return hardwareMapper.insertHardwareChild((HardwareChild)initDomainCreate(hardwareChild));
        }
        return Constants.ZERO;
    }

    //修改硬件
    @Override
    public int updateHardware(Hardware hardware) {
        hardwareId(hardware.getId());
        hardware.setUpdateTime(DateUtils.getNowDate());
        return hardwareMapper.updateHardware((Hardware) initDomainUpdate(hardware));
    }

    //删除硬件通过id
    @Override
    public int deleteHardwareByIds(Hardware hardware) {
        for (Long ids : hardware.getIds()){
            hardwareId(ids);
        }
        return hardwareMapper.deleteHardwareByIds((Hardware) initDomainUpdate(hardware));
    }
    private void hardwareId(Long id) {
        Hardware hardware = hardwareMapper.selectHardwareById(id);
        if (hardware == null) {
            throw new RuntimeException("硬件不存在!");
        }
        if (hardware.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作'未提交'状态的硬件!");
        }
    }

    //新增子项功能
    @Override
    public int insertHardwareChild(HardwareChild hardwareChild) {
        //return hardwareMapper.insertHardwareChild((HardwareChild) initDomainCreate(hardwareChild));
        HardwareChild hardwareChild1 = new HardwareChild();
        hardwareChild1.setHardwareId(hardwareChild.getHardwareId());
        hardwareChild1.setStatus(Constants.SubStatus.UNSUBMIT);
        if(!hardwareMapper.selectChildList(hardwareChild1).isEmpty()){
            throw new RuntimeException("存在未提交的流程");
        }
        hardwareChild1.setStatus(Constants.SubStatus.PROCESSING);
        if (!hardwareMapper.selectChildList(hardwareChild1).isEmpty()){
            throw new RuntimeException("存在待处理的流程");
        }
        //检查是否有未处理的项
        return hardwareMapper.insertHardwareChild((HardwareChild) initDomainCreate(hardwareChild));
    }

    //硬件结束功能，修改主表状态为 3,通过主ID
    @Override
    public int hardwareFinish(Hardware hardware) {

        // 1. 新增：先查未完结的子记录数
        int pendingCount = hardwareMapper.selectChildStatusSum(hardware.getId());
        if (pendingCount > 0) {
            throw new RuntimeException("存在未完结的子记录，无法完成！");
        }

        // 2. 原有逻辑
        Hardware hardware1 = hardwareMapper.selectHardwareById(hardware.getId());
        if (hardware1 == null) {
            throw new RuntimeException("该数据不存在!");
        }

        if (hardware1.getCostType() == Constants.TWO) {
            HardwareChild hardwareChild = new HardwareChild();
            hardwareChild.setHardwareId(hardware.getId());
            List<HardwareChild> hardwareChildren = hardwareMapper.selectChildList(hardwareChild);
            handleByReplace(hardwareChildren);
            // 检查是否有 总经理 处理过
            boolean hasGeneralManagerApproved = hardwareChildren.stream()
                    .anyMatch(process -> {
                        // 判断处理人是否为总经理（这里需要根据实际情况调整判断条件）
                        return process.getHandlePost() != null &&
                                (Constants.GENERAL_MANAGER.equals(process.getHandlePost()) &&
                                        process.getStatus() == 3); // 状态为已处理
                    });
            if (!hasGeneralManagerApproved) {
                throw new RuntimeException("费控外需要总经理审核并通过");
            }
        }

        hardware.setUpdateTime(DateUtils.getNowDate());
        return hardwareMapper.updateHardware((Hardware) initDomainUpdate(hardware));
    }


    /***************************  子表  **************************/

    //通过主表的id查询子表数据
    @Override
    public List<HardwareChild> findChildByHardwareId(HardwareChild hardwareChild) {
        List<HardwareChild> childByHardwareId = hardwareMapper.findChildByHardwareId((HardwareChild) initDomainCreate(hardwareChild));
        createByReplace(childByHardwareId);
        handleByReplace(childByHardwareId);
        return childByHardwareId;
    }

    @Override
    public HardwareChild selectChildById(Long id) {
        return hardwareMapper.selectChildById( id);
    }

    //查询子项
    @Override
    public List<HardwareChild> selectChildList(HardwareChild hardwareChild) {
        List<HardwareChild> hardwareChildren = hardwareMapper.selectChildList((HardwareChild) initDomainCreate(hardwareChild));
        createByReplace(hardwareChildren);
        handleByReplace(hardwareChildren);
        return hardwareChildren;
        //return hardwareMapper.selectChildList(hardwareChild);
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Override
    public int commit(HardwareChild hardwareChild) {
        if(hardwareMapper.updateChild((HardwareChild) initDomainUpdate(hardwareChild)) > Constants.ZERO){
            hardwareChild.setInitiateTime(DateUtils.getNowDate());
            hardwareChild.setStatus(Constants.TWO);
            Hardware hardware = new Hardware();
            hardware.setId(hardwareChild.getHardwareId());
            hardware.setStatus(Constants.TWO);
            hardwareMapper.updateHardware(hardware);
            hardwareMapper.updateChild((HardwareChild) initDomainUpdate(hardwareChild));
        }
        return Constants.ZERO;
    }

    //子项处理功能，添加处理意见，处理时间，修改子表状态为 3或 4,主表状态不变，通过主ID
    @Override
    public int handle(HardwareChild hardwareChild) {
        if(hardwareMapper.updateChild((HardwareChild) initDomainUpdate(hardwareChild)) > Constants.ZERO) {
            hardwareChild.setHandleTime(DateUtils.getNowDate());
            return hardwareMapper.updateChild((HardwareChild) initDomainUpdate(hardwareChild));
        }
        return Constants.ZERO;
    }

    //子项查询中的文件下载
    @Override
    public String hardwareFiles(Long id) {
        HardwareChild hardwareChild = hardwareMapper.selectChildById(id);
        return hardwareChild.getFileNames();
    }
}
