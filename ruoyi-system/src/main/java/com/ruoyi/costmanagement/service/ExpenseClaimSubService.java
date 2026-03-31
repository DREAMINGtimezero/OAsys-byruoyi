package com.ruoyi.costmanagement.service;

import java.util.List;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import org.springframework.lang.NonNull;

/**
 * 报销单子Service接口
 *
 * @author YJ
 * @date 2025-07-31
 */
public interface ExpenseClaimSubService {
    /**
     * 查询报销单子表
     *
     * @param id 报销单子主键
     * @return 报销单子表
     */
    ExpenseClaimSub selectExpenseClaimSubById(Long id);

    /**
     * 查询报销单子列表
     *
     * @param expenseClaimSub 报销单子表
     * @return 报销单子集合
     */
    List<ExpenseClaimSub> selectExpenseClaimSubList(ExpenseClaimSub expenseClaimSub);

    /**
     * 新增报销单子表
     *
     * @param expenseClaimSub 报销单子表
     * @return 结果
     */
    int insertExpenseClaimSub(ExpenseClaimSub expenseClaimSub);

    /**
     * 修改报销单子表
     *
     * @param expenseClaimSub 报销单子表
     * @return 结果
     */
    int updateExpenseClaimSub(ExpenseClaimSub expenseClaimSub);

    /**
     * 批量删除报销单子表数据
     *
     * @param ids 需要删除的报销单子主键集合
     * @return 结果
     */
    int deleteExpenseClaimSubByIds(String ids);

    /**
     * 删除报销单子表信息
     *
     * @param id 报销单子表主键
     * @return 结果
     */
    int deleteExpenseClaimSubById(Long id);

    /**
     * 查询当前用户是否有处理权限
     * @param subId 报销单子表主键
     * @return
     */
    boolean handlePermission(Long subId);

    /**
     * 查询当前用户是否有提交权限
     *
     * @return
     */
    boolean submitPermission(Long subId);

    /**
     * 删除子表中与主表相关的数据
     *
     * @param ids 主表主键列表
     * @return 删除结果
     */
    int deleteRelativeData(@NonNull Long[] ids);

    /**
     * 提交子项
     * @param subData 需要提交的子项
     * @return 提交结果
     */
    int submitProcess(ExpenseClaimSub subData);

    /**
     * 处理子项
     * @param subData 需要处理的子项
     * @return 处理结果
     */
    int handleProcess(ExpenseClaimSub subData);

    /**
     * 根据状态列表查询子项
     * @param statusList 状态列表
     * @param relativeId 关联的主表项id
     * @return 子表中,状态值符合给定状态列表的子项的数量
     */
    int selectByStatusList(@NonNull Integer[] statusList,@NonNull Long relativeId);

    /**
     * 添加子项
     * @param relativeId 关联的主表项id
     * @return 添加结果
     */
    ExpenseClaimSub addProcess(Long relativeId);
}
