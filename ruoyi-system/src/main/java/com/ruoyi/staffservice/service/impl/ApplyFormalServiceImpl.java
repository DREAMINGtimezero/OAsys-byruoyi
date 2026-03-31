package com.ruoyi.staffservice.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.staffservice.domain.ApplyFormal;
import com.ruoyi.staffservice.domain.ApplyFormalSub;
import com.ruoyi.staffservice.mapper.ApplyFormalMapper;
import com.ruoyi.staffservice.service.ApplyFormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ApplyFormalServiceImpl extends BaseServiceImpl implements ApplyFormalService {

    @Autowired
    private ApplyFormalMapper applyFormalMapper;

    @Override
    public ApplyFormal selectApplyFormalById(Long id)
    {
        return applyFormalMapper.selectApplyFormalById(id);
    }


    @Override
    public List<ApplyFormal> selectApplyFormalList(ApplyFormal applyFormal)
    {
        List<ApplyFormal> applyFormals = applyFormalMapper.selectApplyFormalList((ApplyFormal) initDomainCreate(applyFormal));
        createByReplace(applyFormals);
        return applyFormals;
    }

    @Override
    public List<ApplyFormalSub> selectApplyFormalSubByApplyFormalId(ApplyFormalSub applyFormalSub) {
        List<ApplyFormalSub> applyFormalSubs = applyFormalMapper.selectApplyFormalSubByApplyFormalId((ApplyFormalSub) initDomainCreate(applyFormalSub));
        createByReplace(applyFormalSubs);
        handleByReplace(applyFormalSubs);
        return applyFormalSubs;
    }

    @Override
    public List<ApplyFormalSub> selectApplyFormalSubList(ApplyFormalSub applyFormalSub) {
        List<ApplyFormalSub> applyFormalSubs = applyFormalMapper.selectApplyFormalSubList((ApplyFormalSub) initDomainCreate(applyFormalSub));
        createByReplace(applyFormalSubs);
        handleByReplace(applyFormalSubs);
        return applyFormalSubs;
    }

    @Override
    public ApplyFormalSub selectApplyFormalSubById(Long id) {
        return applyFormalMapper.selectApplyFormalSubById(id);
    }

    @Override
    public int insertApplyFormal(ApplyFormal applyFormal) {
        //判断影响行数是否大于0
        if (applyFormalMapper.insertApplyFormal((ApplyFormal) initDomainCreate(applyFormal)) > Constants.ZERO){
            ApplyFormalSub applyFormalSub = new ApplyFormalSub();
            //把主表ID拿给子表用主键ID
            applyFormalSub.setApplyFormalId(applyFormal.getId());
            return applyFormalMapper.insertApplyFormalSub((ApplyFormalSub) initDomainCreate(applyFormalSub));
        }
        return Constants.ZERO;
    }

    @Override
    public int insertApplyFormalSub(ApplyFormalSub applyFormalSub) {
        ApplyFormalSub applyFormalSub1 = new ApplyFormalSub();
        applyFormalSub1.setApplyFormalId(applyFormalSub.getApplyFormalId());
        applyFormalSub1.setStatus(Constants.SubStatus.UNSUBMIT);
        if(!applyFormalMapper.selectApplyFormalSubList(applyFormalSub1).isEmpty()){
            throw new RuntimeException("存在未提交的流程");
        }
        applyFormalSub1.setStatus(Constants.SubStatus.PROCESSING);
        if(!applyFormalMapper.selectApplyFormalSubList(applyFormalSub1).isEmpty()){
            throw new RuntimeException("存在待处理的流程");
        }
        //检查是否有未处理的项
        return applyFormalMapper.insertApplyFormalSub((ApplyFormalSub) initDomainCreate(applyFormalSub));
    }


    @Override
    public int updateApplyFormal(ApplyFormal applyFormal) {
        return applyFormalMapper.updateApplyFormal((ApplyFormal)initDomainUpdate(applyFormal));
    }


    @Override
    public int deleteApplyFormalByIds(ApplyFormal applyFormal) {
        return applyFormalMapper.deleteApplyFormalByIds((ApplyFormal)initDomainUpdate(applyFormal));
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Override
    public int applyFormalSubSubmit(ApplyFormalSub applyFormalSub) {
        if(applyFormalMapper.updateApplyFormalSub((ApplyFormalSub) initDomainUpdate(applyFormalSub)) > Constants.ZERO){
            applyFormalSub.setInitiateTime(DateUtils.getNowDate());
            applyFormalSub.setStatus(Constants.TWO);
            ApplyFormal applyFormal = new ApplyFormal();
            applyFormal.setId(applyFormalSub.getApplyFormalId());
            applyFormal.setStatus(Constants.TWO);
            applyFormalMapper.updateApplyFormal(applyFormal);
            applyFormalMapper.updateApplyFormalSub((ApplyFormalSub) initDomainUpdate(applyFormalSub));
        }
        return Constants.ZERO;
    }

    //子项处理功能，添加处理意见，处理时间，修改子表状态为 3或 4,主表状态不变，通过主ID
    @Override
    public int applyFormalSubHandle(ApplyFormalSub applyFormalSub) {
        if(applyFormalMapper.updateApplyFormalSub((ApplyFormalSub) initDomainUpdate(applyFormalSub)) > Constants.ZERO) {
            applyFormalSub.setHandleTime(DateUtils.getNowDate());
            return applyFormalMapper.updateApplyFormalSub((ApplyFormalSub) initDomainUpdate(applyFormalSub));
        }
        return Constants.ZERO;
    }

    //结束功能，修改主表状态为 3,通过主ID
    @Override
    public int applyFormalFinish(ApplyFormal applyFormal) {
        // 1. 新增：先查未完结的子记录数
        int pendingCount = applyFormalMapper.selectApplyFormalSubStatusSum(applyFormal.getId());
        if (pendingCount > 0) {
            throw new RuntimeException("存在未完结的子记录，无法完成！");
        }

        // 2. 原有逻辑
        ApplyFormal applyFormal1 = applyFormalMapper.selectApplyFormalById(applyFormal.getId());
        if (applyFormal1 == null) {
            throw new RuntimeException("该数据不存在!");
        }
            ApplyFormalSub applyFormalSub = new ApplyFormalSub();
            applyFormalSub.setApplyFormalId(applyFormal.getId());
            List<ApplyFormalSub> applyFormalSubs = applyFormalMapper.selectApplyFormalSubList(applyFormalSub);
            handleByReplace(applyFormalSubs);
            // 检查是否有 总经理 处理过
            boolean hasGeneralManagerApproved = applyFormalSubs.stream()
                    .anyMatch(process -> {
                        // 判断处理人是否为总经理（这里需要根据实际情况调整判断条件）
                        return process.getHandlePost() != null &&
                                (Constants.GENERAL_MANAGER.equals(process.getHandlePost()) &&
                                        process.getStatus() == 3); // 状态为已处理
                    });
            if (!hasGeneralManagerApproved) {
                throw new RuntimeException("转正申请需要总经理审核并通过");
            }
        applyFormal.setUpdateTime(DateUtils.getNowDate());
        return applyFormalMapper.updateApplyFormal((ApplyFormal) initDomainUpdate(applyFormal));
    }
}
