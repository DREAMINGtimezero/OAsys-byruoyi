package com.ruoyi.myservice.domain;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务子表对象 t_my_service_sub
 *
 * @author YJ
 * @date 2025-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyServiceSub extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程所属的服务主键id */
    @Excel(name = "流程所属的服务主键id")
    private Long serviceId;

    /** 流程发起人备注 */
    @Excel(name = "流程发起人备注")
    private String initiateNote;

    /** 流程发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "流程发起时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date initiateTime;

    /** 流程处理人 */
    @Excel(name = "流程处理人")
    private String handleBy;

    /** 处理人意见 */
    @Excel(name = "处理人意见")
    private String handleNote;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 1=未提交，2=待处理，3=已处理，4=已驳回 */
    @Excel(name = "1=未提交，2=待处理，3=已处理，4=已驳回")
    private Integer status;

    /** 自增主键 */
    private Long id;

    /** 逻辑删除 0-正常 1-删除 */
    @Excel(name = "逻辑删除 0-正常 1-删除")
    private Integer deleteFlag;

    /**
     * 当前登录用户对于子表数据的提交权限
     */
    private Map<Long, Boolean> submitPermission;
    /**
     * 当前登录用户对于子表数据的处理权限
     */
    private Map<Long, Boolean> handlePermission;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyServiceSub)){ return false;}
        MyServiceSub that = (MyServiceSub) o;
        return Objects.equals(serviceId, that.serviceId) && Objects.equals(initiateNote, that.initiateNote) && Objects.equals(initiateTime, that.initiateTime) && Objects.equals(handleBy, that.handleBy) && Objects.equals(handleNote, that.handleNote) && Objects.equals(handleTime, that.handleTime) && Objects.equals(getFileNames(), that.getFileNames()) && Objects.equals(status, that.status) && Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceId, initiateNote, initiateTime, handleBy, handleNote, handleTime, getFileNames(), status, id, deleteFlag);
    }
}
