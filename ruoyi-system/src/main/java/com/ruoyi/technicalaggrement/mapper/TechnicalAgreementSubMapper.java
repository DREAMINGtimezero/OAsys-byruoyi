package com.ruoyi.technicalaggrement.mapper;

import java.util.List;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 技术协议子Mapper接口
 *
 * @author YJ
 * @date 2025-06-25
 */
@Mapper
public interface TechnicalAgreementSubMapper
{
    /**
     * 查询技术协议子
     *
     * @param id 技术协议子主键
     * @return 技术协议子
     */
     TechnicalAgreementSub selectTechnicalAgreementSubById(Long id);

    /**
     * 查询技术协议子列表
     *
     * @param technicalAgreementSub 技术协议子
     * @return 技术协议子集合
     */
     List<TechnicalAgreementSub> selectTechnicalAgreementSubList(TechnicalAgreementSub technicalAgreementSub);

    /**
     * 新增技术协议子
     *
     * @param technicalAgreementSub 技术协议子
     * @return 结果
     */
     int insertTechnicalAgreementSub(TechnicalAgreementSub technicalAgreementSub);

    /**
     * 修改技术协议子
     *
     * @param technicalAgreementSub 技术协议子
     * @return 结果
     */
     int updateTechnicalAgreementSub(TechnicalAgreementSub technicalAgreementSub);

    /**
     * 删除技术协议子
     *
     * @param id 技术协议子主键
     * @param updateBy 操作人
     * @return 结果
     */
     int deleteTechnicalAgreementSubById(@Param("id")Long id,@Param("updateBy")String updateBy);

    /**
     * 批量删除技术协议子
     *
     * @param ids 需要删除的数据主键集合
     * @param updateBy 操作人
     * @return 结果
     */
     int deleteTechnicalAgreementSubByIds(@Param("ids")String[] ids,@Param("updateBy")String updateBy);

    /**
     * 批量删除技术协议子
     *
     * @param ids 需要删除的数据主键集合
     * @param updateBy 操作人
     * @return 结果
     */
    int deleteByTechnicalAgreementIds(@Param("ids")Long[] ids,@Param("updateBy")String updateBy);

    /**
     * 查询指定状态的数据的数量
     * @param statusList 状态列表
     * @param relativeId 关联ID
     * @return 数量
     */
    int selectByStatuses(@Param("statusList") Integer[] statusList,@Param("relativeId")Long relativeId);
}
