package com.ruoyi.customer.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import java.util.Date;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 客户子对象 t_customer_sub
 *
 * @author YJ
 * @date 2025-09-22
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerSub extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private Long deleteFlag;

    /**
     * 客户id
     */
    private Long customerId;

    /**
     * 访问方式
     * 选项：电话、线下拜访、微信沟通、其他
     */
    @Excel(name = "访问方式")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private WayOfVisionEnum wayOfVision;

    /**
     * 进展情况
     */
    @Excel(name = "进展情况")
    private String progress;

    /**
     * 沟通时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "沟通时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date communicationTime;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String note;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createBy", getCreateBy())
                .append("updateBy", getUpdateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("deleteFlag", getDeleteFlag())
                .append("customerId", getCustomerId())
                .append("wayOfVision", getWayOfVision())
                .append("progress", getProgress())
                .append("communicationTime", getCommunicationTime())
                .append("note", getNote())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CustomerSub)) {
            return false;
        }
        CustomerSub that = (CustomerSub) o;
        return Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag) && Objects.equals(customerId, that.customerId) && Objects.equals(wayOfVision, that.wayOfVision) && Objects.equals(progress, that.progress) && Objects.equals(communicationTime, that.communicationTime) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deleteFlag, customerId, wayOfVision, progress, communicationTime, note);
    }
}
