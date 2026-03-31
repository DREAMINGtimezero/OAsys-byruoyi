package com.ruoyi.technicalaggrement.mapper;

import java.util.List;

import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import org.apache.ibatis.annotations.Param;

/**
 * 技术协议Mapper接口
 *
 * @author YJ
 * @date 2025-06-24
 */
public interface TechnicalAgreementMapper {
    /**
     * 查询技术协议
     *
     * @param id 技术协议主键
     * @return 技术协议
     */
    TechnicalAgreement selectTechnicalAgreementById(Long id);

    /**
     * 查询技术协议列表
     *
     * @param technicalAgreement 技术协议
     * @return 技术协议集合
     */
    List<TechnicalAgreement> selectTechnicalAgreementList(TechnicalAgreement technicalAgreement);

    /**
     * 新增技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    int insertTechnicalAgreement(TechnicalAgreement technicalAgreement);

    /**
     * 修改技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    int updateTechnicalAgreement(TechnicalAgreement technicalAgreement);

    /**
     * 删除技术协议
     *
     * @param id 技术协议主键
     * @param updateBy 修改人
     * @return 结果
     */
    int deleteTechnicalAgreementById(@Param("id")Long id,@Param("updateBy") String updateBy);

    /**
     * 批量删除技术协议
     *
     * @param ids      需要删除的[技术协议]主键集合
     * @param updateBy 修改人
     * @return 结果
     */
    int deleteTechnicalAgreementByIds(@Param("ids") Long[] ids, @Param("updateBy") String updateBy);

    /**
     * 查询给定的id中有多少符合可删除条件
     * @param ids id列表
     * @return 符合删除条件的项数
     */
    int selectDeleteAble(@Param("ids") Long[] ids);
}
