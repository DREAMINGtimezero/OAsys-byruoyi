package com.ruoyi.costmanagement.service;

import java.util.List;
import com.ruoyi.costmanagement.domain.ExpenseClaim;

/**
 * 报销单主表Service接口
 *
 * @author YJ
 * @date 2025-07-31
 */
public interface ExpenseClaimService
{
    /**
     * 查询报销单主表
     *
     * @param id 报销单主表主键
     * @return 报销单主表
     */
    ExpenseClaim selectExpenseClaimById(Long id);

    /**
     * 查询报销单主表列表
     *
     * @param expenseClaim 报销单主表
     * @return 报销单主表集合
     */
    List<ExpenseClaim> selectExpenseClaimList(ExpenseClaim expenseClaim);

    /**
     * 修改报销单主表
     *
     * @param expenseClaim 报销单主表
     * @return 结果
     */
    int updateExpenseClaim(ExpenseClaim expenseClaim);


    /**
     * 批量删除报销单主表信息,并且关联删除相关的子表数据
     *
     * @param ids 需要删除的报销单主表主键集合
     * @return 结果
     */
    int deleteExpenseClaim(String ids);

    int insertExpenseClaim(ExpenseClaim mainData);

    /**
     * 添加主项,并在子表中添加相关的子项
     * @param mainData 需要新增的数据
     * @return 结果
     */
    int addExpenseClaim(ExpenseClaim mainData);


    /**
     * 检查是否可以结束
     * @param id 主项id
     * @return 结果
     */
    boolean overAble(Long id);
    /**
     * 检查是否可以删除
     * @param idList 主项id列表
     * @return 结果
     */
    boolean deleteAble(Long[] idList);

    /**
     * 检查是否可以修改
     * @param id 主项id
     * @return 结果
     */
    boolean editAble(Long id);

}
