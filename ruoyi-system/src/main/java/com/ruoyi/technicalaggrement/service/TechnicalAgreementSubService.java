package com.ruoyi.technicalaggrement.service;

import java.util.List;

import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

/**
 * 技术协议子Service接口
 *
 * @author YJ
 */
public interface TechnicalAgreementSubService
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
     * @param conditions 查询条件
     * @return 技术协议子集合
     */
    List<TechnicalAgreementSub> selectTechnicalAgreementSubList(TechnicalAgreementSub conditions);

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
     * 批量删除技术协议子
     *
     * @param ids 需要删除的技术协议子主键集合
     * @return 结果
     */
    int deleteTechnicalAgreementSubByIds(String ids);

    /**
     * 删除技术协议子信息
     *
     * @param id 技术协议子主键
     * @return 结果
     */
    int deleteTechnicalAgreementSubById(Long id);

    /**
     * 删除相关数据
     * @param ids 技术协议主键
     * @return 删除结果
     */
    int deleteRelativeData(Long[] ids);

    /**
     * 提交
     * @param technicalAgreementSub
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int submitProcess(TechnicalAgreementSub technicalAgreementSub);

    @Transactional
    int handleProcess(TechnicalAgreementSub sub);

    /**
     * 发起一个流程节点(增加一个与主表关联的子表数据)
     * @param relativeId 关联的主表Id
     * @return 新增的子表数据
     */
    TechnicalAgreementSub add(Long relativeId);

    /**
     * 根据指定状态列表查询
     * @param statusList 状态列表
     * @param relativeId 关联的主表Id
     * @return 数据量
     */
    int selectByStatuses(Integer[] statusList,Long relativeId);

    /**
     * 校验是否有处理权限
     * @param id 子表数据的id
     */

    /**
     * 检查当前用户对于子表数据是否有处理权限
     * @param subId 子表id
     * @return 有权限则为true
     */
    boolean handlePermission(@NonNull Long subId);

    /**
     * 检查当前用户对于子表数据是否有处理权限
     * 注意main与sub都是数据库中的原始数据
     * @param main 关联的主表数据
     * @param sub 关联的子表数据
     * @return 有权限则为true
     */
    boolean handlePermission(@NonNull TechnicalAgreement main, @NonNull TechnicalAgreementSub sub);

    /**
     * 检查当前用户对于子表数据是否有提交权限
     * @param subId 子表id
     * @return 有权限则为true
     */
    boolean submitPermission(@NonNull Long subId);

    /**
     * 检查当前用户对于子表数据是否有提交权限
     * 注意main与sub都是数据库中的原始数据
     * @param main 关联的主表数据
     * @param sub 关联的子表数据
     * @return 有权限则为true
     */
    boolean submitPermission(@NonNull TechnicalAgreement main, @NonNull TechnicalAgreementSub sub);
}
