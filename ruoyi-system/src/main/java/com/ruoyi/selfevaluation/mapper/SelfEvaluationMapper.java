package com.ruoyi.selfevaluation.mapper;

import com.ruoyi.selfevaluation.domain.SelfEvaluation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SelfEvaluationMapper {

    List<SelfEvaluation> selectEvalutionList(SelfEvaluation selfEvaluation);

    int insertEvalution(SelfEvaluation selfEvaluation);

    int deleteSelfEvaluationById(SelfEvaluation selfEvaluation);

    int submitSelfEvaluation(SelfEvaluation selfEvaluation);
}
