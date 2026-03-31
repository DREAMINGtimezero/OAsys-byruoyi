package com.ruoyi.selfevaluation.service;

import com.ruoyi.selfevaluation.domain.SelfEvaluation;

import java.util.List;

public interface SelfEvaluationService {

    List<SelfEvaluation> selectEvalutionList(SelfEvaluation selfEvaluation);

    int insertEvalution(SelfEvaluation selfEvaluation);

    int deleteSelfEvaluationById(SelfEvaluation selfEvaluation);

    int submitSelfEvaluation(SelfEvaluation selfEvaluation);
}
