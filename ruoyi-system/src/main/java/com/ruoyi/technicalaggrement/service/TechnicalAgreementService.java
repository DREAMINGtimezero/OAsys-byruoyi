package com.ruoyi.technicalaggrement.service;

import java.util.List;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;

/**
 * 技术协议Service接口
 *
 * @author YJ
 * @date 2025-06-24
 */
public interface TechnicalAgreementService
{
    /**
     * 查询技术协议
     *
     * @param id 技术协议主键
     * @return 技术协议
     */
    public TechnicalAgreement selectTechnicalAgreementById(Long id);

    /**
     * 查询技术协议列表
     *
     * @param technicalAgreement 技术协议
     * @return 技术协议集合
     */
    public List<TechnicalAgreement> selectTechnicalAgreementList(TechnicalAgreement technicalAgreement);

    /**
     * 新增技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    public int insertTechnicalAgreement(TechnicalAgreement technicalAgreement);

    /**
     * 修改技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    public int updateTechnicalAgreement(TechnicalAgreement technicalAgreement);

    /**
     * 批量删除技术协议
     *
     * @param ids 需要删除的技术协议主键集合
     * @return 结果
     */
    public int deleteTechnicalAgreementByIds(String ids);

    /**
     * 删除技术协议信息
     *
     * @param id 技术协议主键
     * @return 结果
     */
    public int deleteTechnicalAgreementById(Long id);


    /**
     * 检查是否可结束流程
     * @param id 主表id
     * @return 是否可结束
     */
    boolean checkOverAble(Long id);

    /**
     * 获取最后修改人
     * @param id 主表id
     * @return 最后修改人
     */
    String getLastEditor(Long id);

    /**
     * 检查是否可进行批量删除
     * @param idList 主表id集合
     * @return 是否可进行批量删除
     */
    boolean isDeleteAble(@lombok.NonNull Long[] idList);
}
