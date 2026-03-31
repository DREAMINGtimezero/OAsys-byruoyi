package com.ruoyi.selfevaluation.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.selfevaluation.domain.SelfEvaluation;
import com.ruoyi.selfevaluation.mapper.SelfEvaluationMapper;
import com.ruoyi.selfevaluation.service.SelfEvaluationService;
import com.ruoyi.work.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.ruoyi.common.utils.ShiroUtils.getSysUser;

@Service
public class SelfEvaluationServiceImpl extends BaseServiceImpl implements SelfEvaluationService {
    @Autowired
    private SelfEvaluationMapper selfEvaluationMapper;
    @Override
    public List<SelfEvaluation> selectEvalutionList(SelfEvaluation selfEvaluation) {
        if(selfEvaluation.getIsCeo()==true){
            selfEvaluation.setCreateBy(getSysUser().getLoginName());
        }
        List<SelfEvaluation> evaluations = selfEvaluationMapper.selectEvalutionList((SelfEvaluation) initDomainCreate(selfEvaluation));
        createByReplace(evaluations);
        return evaluations;

    }

    @Override
    public int insertEvalution(SelfEvaluation selfEvaluation){
        selfEvaluation.setCreateTime(DateUtils.getNowDate());
        selfEvaluation.setUpdateTime(DateUtils.getNowDate());
        selfEvaluation.setUpdateBy(getSysUser().getUserName());
        return selfEvaluationMapper.insertEvalution((SelfEvaluation)initDomainCreate(selfEvaluation));
    }

    @Override
    public int deleteSelfEvaluationById(SelfEvaluation selfEvaluation) {
        selfEvaluation.setDeleteFlag(1);
        return selfEvaluationMapper.deleteSelfEvaluationById(selfEvaluation);
    }

    @Override
    public int submitSelfEvaluation(SelfEvaluation selfEvaluation) {
        return selfEvaluationMapper.submitSelfEvaluation((SelfEvaluation)initDomainUpdate(selfEvaluation));
    }
}
