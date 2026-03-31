package com.ruoyi.costmanagement.mapper;

import java.util.List;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;

/**
 * 报销单子Mapper接口
 *
 * @author YJ
 * @date 2025-07-31
 */
public interface ExpenseClaimSubMapper
{
    /**
     * 查询报销单子
     *
     * @param id 报销单子主键
     * @return 报销单子
     */
    ExpenseClaimSub selectExpenseClaimSubById(Long id);

    /**
     * 查询报销单子列表
     *
     * @param expenseClaimSub 报销单子
     * @return 报销单子集合
     */
    List<ExpenseClaimSub> selectExpenseClaimSubList(ExpenseClaimSub expenseClaimSub);

    /**
     * 新增报销单子
     *
     * @param expenseClaimSub 报销单子
     * @return 结果
     */
    int insertExpenseClaimSub(ExpenseClaimSub expenseClaimSub);

    /**
     * 修改报销单子
     *
     * @param expenseClaimSub 报销单子
     * @return 结果
     */
    int updateExpenseClaimSub(ExpenseClaimSub expenseClaimSub);

    /**
     * 删除报销单子
     *
     * @param id 报销单子主键
     * @param updateBy 操作人
     * @return 结果
     */
    int deleteExpenseClaimSubById(@Param("id")Long id,@Param("updateBy")String updateBy);

    /**
     * 批量删除报销单子
     *
     * @param ids 需要删除的数据主键集合
     * @param updateBy 操作人
     * @return 结果
     */
    int deleteExpenseClaimSubByIds(@Param("ids") String[] ids,@Param("updateBy")String updateBy);

    /**
     * 删除与主表相关的子表数据
     * @param ids 需要删除的子表的主键列表
     * @param updateBy 操作人
     * @return 删除的行数
     */
    int deleteRelativeData(@NonNull @Param("ids") Long[] ids,@Param("updateBy")String updateBy);

    /**
     * 根据给定的状态列表,查询子表中相应状态的子表项的数量
     * @param statusList 状态列表
     * @param relativeId 主表id
     * @return 状态列表中相应状态的子表项的数量
     */
    int selectByStatusList(@NonNull @Param("statusList")Integer[] statusList,@Param("relativeId")Long relativeId);
}
