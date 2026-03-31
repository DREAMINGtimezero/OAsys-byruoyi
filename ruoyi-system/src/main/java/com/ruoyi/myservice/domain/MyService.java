package com.ruoyi.myservice.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.DelayQueue;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 服务对象 t_my_service
 *
 * @author YJ
 * @date 2025-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyService extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 所属项目编号
     */
    @Excel(name = "所属项目编号")
    private String projectCode;

    /**
     * 服务编号
     */
    @Excel(name = "服务编号")
    private String serviceCode;

    /**
     * 服务名称
     */
    @Excel(name = "服务名称")
    private String title;

    /**
     * 描述信息
     */
    @Excel(name = "描述信息")
    private String description;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String note;

    /**
     * 1=未提交，2=处理中，3=已完成
     */
    @Excel(name = "1=未提交，2=处理中，3=已完成")
    private String status;

    /**
     * 服务开销金额
     */
    @Excel(name = "服务开销金额")
    private BigDecimal money;

    /**
     * 1=费控内,2=费控外
     */
    @Excel(name = "费控类型", readConverterExp = "1=费控内,2=费控外")
    private Integer costType;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    private Integer deleteFlag;

    /**
     * 开始时间(时间范围的下限)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    /**
     * 结束时间(时间范围的上限)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    /**
     * 项目名称
     */
    @Excel(name = "所属项目名称")
    private String projectName;

    /**
     * 状态列表
     */
    private Integer[] statusList;


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyService)) {
            return false;
        }
        MyService myService = (MyService) o;
        return Objects.equals(projectCode, myService.projectCode) && Objects.equals(serviceCode, myService.serviceCode) && Objects.equals(title, myService.title) && Objects.equals(description, myService.description) && Objects.equals(note, myService.note) && Objects.equals(status, myService.status) && Objects.equals(money, myService.money) && Objects.equals(costType, myService.costType) && Objects.equals(id, myService.id) && Objects.equals(deleteFlag, myService.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, serviceCode, title, description, note, status, money, costType, id, deleteFlag);
    }
}
