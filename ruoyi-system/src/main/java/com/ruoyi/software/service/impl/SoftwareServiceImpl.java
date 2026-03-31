package com.ruoyi.software.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.contract.mapper.ContractMapper;
import com.ruoyi.contract.service.ContractService;
import com.ruoyi.software.domain.Software;
import com.ruoyi.software.domain.SoftwareProcess;
import com.ruoyi.software.mapper.SoftwareMapper;
import com.ruoyi.software.mapper.SoftwareProcessMapper;
import com.ruoyi.software.service.SoftwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.ruoyi.common.constant.Constants.GENERAL_MANAGER;

/**
 * 合同信息Service实现层处理
 *
 * @author lty
 * @date 2025-06-24
 */
@Service
public class SoftwareServiceImpl extends BaseServiceImpl implements SoftwareService {

    @Autowired
    private SoftwareMapper softwareMapper;

    @Autowired
    private SoftwareProcessMapper softwareProcessMapper;

    @Override
    public Software selectSoftwareById(Long id) {
        return softwareMapper.selectSoftwareById(id);
    }

    @Override
    public List<Software> selectSoftwareList(Software software) {
        List<Software> list = softwareMapper.selectSoftwareList((Software) initDomainCreate(software));
        createByReplace(list);
        updateByReplace(list);
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSoftware(Software software) {
        if(softwareMapper.insertSoftware((Software)initDomainCreate(software)) > Constants.ZERO){
            SoftwareProcess softwareProcess = new SoftwareProcess();
            softwareProcess.setSoftwareId(software.getId());
            return softwareProcessMapper.insertSoftwareProcess((SoftwareProcess)initDomainCreate(softwareProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public int updateSoftware(Software software) {
        checkSoftwareById(software.getId());
        software.setUpdateTime(DateUtils.getNowDate());
        return softwareMapper.updateSoftware((Software)initDomainUpdate(software));
    }

    @Override
    public int deleteSoftwareByIds(Software software) {
        for(Long ids : software.getIds()){
            checkSoftwareById(ids);
        }
        return softwareMapper.deleteSoftwareByIds((Software)initDomainUpdate(software));
    }

    private void checkSoftwareById(Long id) {
        // 根据项目号查询项目
        Software software = softwareMapper.selectSoftwareById(id);
        if (software == null) {
            throw new RuntimeException("该数据不存在!");
        }
        if (software.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作'未提交'状态的数据!");
        }
    }

    @Override
    public int deleteSoftwareById(Long id) {
        return 0;
    }

    @Override
    public int finishSoftware(Software software) {
        SoftwareProcess conditions = new SoftwareProcess();
        conditions.setSoftwareId(software.getId());
        conditions.setDeleteFlag(Constants.ZERO);
        //检查是否有未提交的子项
        conditions.setStatus(Constants.SubStatus.UNSUBMIT);
        if(!softwareProcessMapper.selectSoftwareProcessList(conditions).isEmpty())
        {
            throw new RuntimeException("存在未提交的子项!");
        }
        //检查是否有未处理的子项
        conditions.setStatus(Constants.SubStatus.PROCESSING);
        if(!softwareProcessMapper.selectSoftwareProcessList(conditions).isEmpty())
        {
            throw new RuntimeException("存在待处理的子项!");
        }
        // 获取完整的软件数据
        Software existingSoftware = softwareMapper.selectSoftwareById(software.getId());
        if (existingSoftware == null) {
            throw new RuntimeException("该数据不存在!");
        }

        // 检查费控类型
        if (existingSoftware.getCostType() == Constants.TWO) {
            // 费控外需要总经理审核
            SoftwareProcess queryProcess = new SoftwareProcess();
            queryProcess.setSoftwareId(software.getId());
            List<SoftwareProcess> processList = softwareProcessMapper.selectSoftwareProcessList(queryProcess);
            //填充处理人岗位
            handleByReplace(processList);

            // 检查是否有 总经理 处理过
            boolean hasGeneralManagerApproved = processList.stream()
                    .anyMatch(process -> {
                        // 判断处理人是否为总经理（这里需要根据实际情况调整判断条件）
                        return process.getHandlePost() != null &&
                                (Constants.GENERAL_MANAGER.equals(process.getHandlePost()) &&
                                        process.getStatus() == 3); // 状态为已处理
                    });
            if (!hasGeneralManagerApproved) {
                throw new RuntimeException("费控外需要总经理审核并通过");
            }else{
                software.setUpdateTime(DateUtils.getNowDate());
                return softwareMapper.updateSoftware((Software)initDomainUpdate(software));
            }
        }
        // 费控内直接通过，无需额外检查
        software.setUpdateTime(DateUtils.getNowDate());
        return softwareMapper.updateSoftware((Software)initDomainUpdate(software));
    }
}

