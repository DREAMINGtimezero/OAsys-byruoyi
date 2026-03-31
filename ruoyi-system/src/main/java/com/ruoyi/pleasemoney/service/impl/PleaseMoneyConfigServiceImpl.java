package com.ruoyi.pleasemoney.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfig;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSub;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSun;
import com.ruoyi.pleasemoney.mapper.PleaseMoneyConfigMapper;
import com.ruoyi.pleasemoney.service.PleaseMoneyConfigService;
import com.ruoyi.project.domain.ProjectStatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @Description 请款配置impl
 * @Author wangshilin
 * @Date 2025/7/4 09:15
 **/
@Service
public class PleaseMoneyConfigServiceImpl extends BaseServiceImpl implements PleaseMoneyConfigService {

    @Autowired
    private PleaseMoneyConfigMapper pleaseMoneyConfigMapper;


    @Override
    public List<PleaseMoneyConfig> selectPleaseMoneyConfigList(PleaseMoneyConfig pleaseMoneyConfig) {
        List<PleaseMoneyConfig> pleaseMoneyConfigs = pleaseMoneyConfigMapper.selectPleaseMoneyConfigList(
                (PleaseMoneyConfig) initDomainCreate(pleaseMoneyConfig));
        createByReplace(pleaseMoneyConfigs);
        return pleaseMoneyConfigs;
    }

    @Override
    public List<PleaseMoneyConfigSub> selectPleaseMoneyConfigSubList(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        List<PleaseMoneyConfigSub> pleaseMoneyConfigSubList = pleaseMoneyConfigMapper.selectPleaseMoneyConfigSubList(
                (PleaseMoneyConfigSub) initDomainCreate(pleaseMoneyConfigSub));
        createByReplace(pleaseMoneyConfigSubList);
        return pleaseMoneyConfigSubList;
    }

    //通过子表id查询孙表数据， 通过二层id差三层数据
    @Override
    public List<PleaseMoneyConfigSun> selectSunListByPleaseMoneyConfigSubId(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        List<PleaseMoneyConfigSun> pleaseMoneyConfigSuns = pleaseMoneyConfigMapper.selectSunListByPleaseMoneyConfigSubId
                    ((PleaseMoneyConfigSun)initDomainCreate (pleaseMoneyConfigSun));
        createByReplace(pleaseMoneyConfigSuns);
        handleByReplace(pleaseMoneyConfigSuns);
        return pleaseMoneyConfigSuns;

    }

    @Override
    public List<PleaseMoneyConfigSun> selectPleaseMoneyConfigSunList(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        List<PleaseMoneyConfigSun> pleaseMoneyConfigSuns = pleaseMoneyConfigMapper.selectPleaseMoneyConfigSunList
                ((PleaseMoneyConfigSun) initDomainCreate(pleaseMoneyConfigSun));
        createByReplace(pleaseMoneyConfigSuns);
        handleByReplace(pleaseMoneyConfigSuns);
        return pleaseMoneyConfigSuns;
    }

    @Override
    public int insertPleaseMoneyConfig(PleaseMoneyConfig pleaseMoneyConfig) {
        return pleaseMoneyConfigMapper.insertPleaseMoneyConfig((PleaseMoneyConfig)initDomainCreate(pleaseMoneyConfig));
    }

    @Override
    public int insertPleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        return pleaseMoneyConfigMapper.insertPleaseMoneyConfigSub((PleaseMoneyConfigSub)initDomainCreate(pleaseMoneyConfigSub));
    }

    @Override
    public int insertPleaseMoneyConfigSun(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        //return pleaseMoneyConfigMapper.insertPleaseMoneyConfigSun((PleaseMoneyConfigSun)initDomainCreate(pleaseMoneyConfigSun));
        PleaseMoneyConfigSun pleaseMoneyConfigSun1 = new PleaseMoneyConfigSun();
        pleaseMoneyConfigSun1.setSubId(pleaseMoneyConfigSun.getSubId());
        pleaseMoneyConfigSun1.setStatus(Constants.SubStatus.UNSUBMIT);
        if(!pleaseMoneyConfigMapper.selectPleaseMoneyConfigSunList(pleaseMoneyConfigSun1).isEmpty()){
            throw new RuntimeException("存在未提交的流程");
        }
        pleaseMoneyConfigSun1.setStatus(Constants.SubStatus.PROCESSING);
        if(!pleaseMoneyConfigMapper.selectPleaseMoneyConfigSunList(pleaseMoneyConfigSun1).isEmpty()){
            throw new RuntimeException("存在待处理的流程");
        }
        return pleaseMoneyConfigMapper.insertPleaseMoneyConfigSun((PleaseMoneyConfigSun)initDomainCreate(pleaseMoneyConfigSun));
    }

    @Override
    public int updatePleaseMoneyConfig(PleaseMoneyConfig pleaseMoneyConfig) {
        checkPleaseMoneyConfig(pleaseMoneyConfig.getId());
        return pleaseMoneyConfigMapper.updatePleaseMoneyConfig((PleaseMoneyConfig)initDomainUpdate(pleaseMoneyConfig));
    }

    @Override
    public int updatePleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        checkPleaseMoneyConfigSub(pleaseMoneyConfigSub.getId());
        return pleaseMoneyConfigMapper.updatePleaseMoneyConfigSub((PleaseMoneyConfigSub)initDomainUpdate(pleaseMoneyConfigSub));
    }

    @Override
    public int deletePleaseMoneyConfigByIds(PleaseMoneyConfig pleaseMoneyConfig) {
        for (Long id : pleaseMoneyConfig.getIds()) {
            checkPleaseMoneyConfig(id);
        }
        return pleaseMoneyConfigMapper.deletePleaseMoneyConfigByIds((PleaseMoneyConfig)initDomainUpdate(pleaseMoneyConfig));
    }

    @Override
    public int deletePleaseMoneyConfigSubByIds(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        for (Long id : pleaseMoneyConfigSub.getIds()) {
            checkPleaseMoneyConfigSub(id);
        }
        return pleaseMoneyConfigMapper.deletePleaseMoneyConfigSubByIds((PleaseMoneyConfigSub)initDomainUpdate(pleaseMoneyConfigSub));
    }

    // 主项：根据项目id判断当前数据是否是未提交，只有未提交的才可以操作
    public void checkPleaseMoneyConfig(Long id) {
        // 根据项目号查询项目
        PleaseMoneyConfig pleaseMoneyConfig = new PleaseMoneyConfig();
        pleaseMoneyConfig.setId(id);
        pleaseMoneyConfig = pleaseMoneyConfigMapper.selectPleaseMoneyConfigList(pleaseMoneyConfig).get(Constants.ZERO);
        if (pleaseMoneyConfig == null || id == null) {
            throw new RuntimeException("请款配置不存在!");
        }
        if (pleaseMoneyConfig.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作状态是'未提交'的请款配置!");
        }
    }

    // 子项：根据项目id判断当前数据的主项是否是未提交，只有未提交的才可以操作
    public void checkPleaseMoneyConfigSub(Long id) {
        // 根据项目号查询项目
        PleaseMoneyConfigSub pleaseMoneyConfigSub = new PleaseMoneyConfigSub();
        pleaseMoneyConfigSub.setId(id);
        pleaseMoneyConfigSub = pleaseMoneyConfigMapper.selectPleaseMoneyConfigSubList(pleaseMoneyConfigSub).get(Constants.ZERO);
        if (pleaseMoneyConfigSub == null || id == null) {
            throw new RuntimeException("请款类型不存在!");
        }
        if (pleaseMoneyConfigSub.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作状态是'未开始'的请款类型!");
        }
    }

    //孙表的提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Override
    public int PleaseMoneyConfigSunSubmit(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        if(pleaseMoneyConfigMapper.updatePleaseMoneyConfigSun((PleaseMoneyConfigSun) initDomainUpdate(pleaseMoneyConfigSun)) > Constants.ZERO){
            pleaseMoneyConfigSun.setInitiateTime(DateUtils.getNowDate());
            pleaseMoneyConfigSun.setStatus(Constants.TWO);
            PleaseMoneyConfigSub pleaseMoneyConfigSub = new PleaseMoneyConfigSub();
            pleaseMoneyConfigSub.setId(pleaseMoneyConfigSun.getSubId());
            pleaseMoneyConfigSub.setStatus(Constants.TWO);
            pleaseMoneyConfigMapper.updatePleaseMoneyConfigSub(pleaseMoneyConfigSub);
            pleaseMoneyConfigMapper.updatePleaseMoneyConfigSun((PleaseMoneyConfigSun) initDomainUpdate(pleaseMoneyConfigSun));
        }
        return Constants.ZERO;
    }

    //孙项处理功能，添加处理意见，处理时间,修改三表状态为 3或 4,通过主ID
    @Override
    public int PleaseMoneyConfigSunHandle(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        if(pleaseMoneyConfigMapper.updatePleaseMoneyConfigSun((PleaseMoneyConfigSun) initDomainUpdate(pleaseMoneyConfigSun)) > Constants.ZERO) {
            pleaseMoneyConfigSun.setHandleTime(DateUtils.getNowDate());
            return pleaseMoneyConfigMapper.updatePleaseMoneyConfigSun((PleaseMoneyConfigSun) initDomainUpdate(pleaseMoneyConfigSun));
        }
        return Constants.ZERO;
    }

    //结束功能，修改二表状态为 3,通过主ID
    @Override
    public int PleaseMoneyConfigSubFinish(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        int pendingCount = pleaseMoneyConfigMapper.selectSunStatusSum(pleaseMoneyConfigSub.getId());
        if (pendingCount > 0) {
            throw new RuntimeException("存在未完成的记录，无法完成！");
        }
        if(pleaseMoneyConfigMapper.updatePleaseMoneyConfigSub((PleaseMoneyConfigSub) initDomainUpdate(pleaseMoneyConfigSub)) > Constants.ZERO) {
            pleaseMoneyConfigSub.setUpdateTime(DateUtils.getNowDate());
            return pleaseMoneyConfigMapper.updatePleaseMoneyConfigSub((PleaseMoneyConfigSub) initDomainUpdate(pleaseMoneyConfigSub));
        }
        return Constants.ZERO;
    }

    //孙项查询中的文件下载
    @Override
    public String PleaseMoneyConfigSunFiles(Long id) {
        PleaseMoneyConfigSun pleaseMoneyConfigSun = pleaseMoneyConfigMapper.selectPleaseMoneyConfigSunById(id);
        return pleaseMoneyConfigSun.getFileNames();
    }

    @Override
    public int configComplete(PleaseMoneyConfig pleaseMoneyConfig) {
        // 查询此请款主项对应的子项的状态是否全部是已完成
        PleaseMoneyConfigSub pleaseMoneyConfigSub = new PleaseMoneyConfigSub();
        pleaseMoneyConfigSub.setMId(pleaseMoneyConfig.getId());
        List<PleaseMoneyConfigSub> list = pleaseMoneyConfigMapper.selectPleaseMoneyConfigSubList(pleaseMoneyConfigSub);
        list.forEach(sub -> {
            if (sub.getStatus() != Constants.THREE) {
                throw new RuntimeException("请款未完成！请等待所有请款子项完成！");
            }
        });
        return pleaseMoneyConfigMapper.updatePleaseMoneyConfig(pleaseMoneyConfig);
    }
}
