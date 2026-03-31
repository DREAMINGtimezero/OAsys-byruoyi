package com.ruoyi.costmanagement.domain;

import java.math.BigDecimal;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 报销单主表对象 t_expense_claim
 *
 * @author YJ
 * @date 2025-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseClaim extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 报销项目 */
    @Excel(name = "报销项目")
    private String costType;

    /** 费用金额 */
    @Excel(name = "费用金额")
    private BigDecimal cost;

    /** 描述费用产生的缘由等 */
    @Excel(name = "描述费用产生的缘由等")
    private String description;

    /** 1=未提交，2=处理中，3=已完成 */
    @Excel(name = "1=未提交，2=处理中，3=已完成")
    private Integer status;

    /** 自增主键 */
    private Long id;

    /** 逻辑删除 0-正常 1-删除 */
    @Excel(name = "逻辑删除 0-正常 1-删除")
    private Integer deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExpenseClaim))
        {
            return false;
        }
        ExpenseClaim that = (ExpenseClaim) o;
        return Objects.equals(costType, that.costType) && Objects.equals(cost, that.cost) && Objects.equals(description, that.description) && Objects.equals(status, that.status) && Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(costType, cost, description, status, id, deleteFlag);
    }
}
