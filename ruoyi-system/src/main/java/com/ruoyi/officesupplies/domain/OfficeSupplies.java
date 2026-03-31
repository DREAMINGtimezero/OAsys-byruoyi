package com.ruoyi.officesupplies.domain;
import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
/**
 * 办公用品主表对象 t_office_supplies
 *
 * @author gyx
 * @date 2025-08-06
 */
public class OfficeSupplies extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用品名称 */
    @Excel(name = "用品名称")
    private String suppliesName;

    /** 办公用品单价 */
    @Excel(name = "办公用品单价")
    private BigDecimal unitPrice;

    /** 办公用品数量 */
    @Excel(name = "办公用品数量")
    private Long quantity;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalCost;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String description;

    /** 状态: 1=未提交，2=处理中，3=已完成 */
    @Excel(name = "状态: 1=未提交，2=处理中，3=已完成")
    private Integer status;

    /** 主键id */
    private Long id;

    /** 删除标记 */
    @Excel(name = "删除标记")
    private Integer deleteFlag;

    public void setSuppliesName(String suppliesName)
    {
        this.suppliesName = suppliesName;
    }

    public String getSuppliesName()
    {
        return suppliesName;
    }

    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }

    public void setQuantity(Long quantity)
    {
        this.quantity = quantity;
    }

    public Long getQuantity()
    {
        return quantity;
    }

    public void setTotalCost(BigDecimal totalCost)
    {
        this.totalCost = totalCost;
    }

    public BigDecimal getTotalCost()
    {
        return totalCost;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setDeleteFlag(Integer deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public Integer getDeleteFlag()
    {
        return deleteFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("suppliesName", getSuppliesName())
                .append("unitPrice", getUnitPrice())
                .append("quantity", getQuantity())
                .append("totalCost", getTotalCost())
                .append("description", getDescription())
                .append("status", getStatus())
                .append("id", getId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("deleteFlag", getDeleteFlag())
                .toString();
    }
}
