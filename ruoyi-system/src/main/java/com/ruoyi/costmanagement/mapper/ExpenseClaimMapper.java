package com.ruoyi.costmanagement.mapper;

import java.util.List;
import com.ruoyi.costmanagement.domain.ExpenseClaim;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * 报销单主表Mapper接口
 *
 * @author YJ
 * @date 2025-07-31
 */
public interface ExpenseClaimMapper
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
     * 新增报销单主表
     *
     * @param expenseClaim 报销单主表
     * @return 结果
     */
    int insertExpenseClaim(ExpenseClaim expenseClaim);

    /**
     * 修改报销单主表
     *
     * @param expenseClaim 报销单主表
     * @return 结果
     */
    int updateExpenseClaim(ExpenseClaim expenseClaim);

    /**
     * 删除报销单主表
     *
     * @param id 报销单主表主键
     * @param updateBy 操作人
     * @return 结果
     */
    int deleteExpenseClaimById(@Param("id") Long id,@Param("updateBy")String updateBy);

    /**
     * 批量删除报销单主表
     *
     * @param ids 需要删除的数据主键集合
     * @param updateBy 操作人
     * @return 结果
     */
    int deleteExpenseClaimByIds(@NonNull @Param("ids")Long[] ids,@Param("updateBy")String updateBy);

    /**
     * 查询给定的id列表中,有哪些项是可以删除的
     * @param ids id列表
     * @return 可以执行删除操作的项
     */
    int selectDeleteAble(@NonNull @Param("ids")Long[] ids);
}
