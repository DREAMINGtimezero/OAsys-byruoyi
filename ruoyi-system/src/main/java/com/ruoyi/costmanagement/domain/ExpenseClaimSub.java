package com.ruoyi.costmanagement.domain;

import java.util.Date;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 报销单子对象 t_expense_claim_sub
 *
 * @author YJ
 * @date 2025-07-31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseClaimSub extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程所属的报销项目 */
    @Excel(name = "流程所属的报销项目")
    private Long expenseClaimId;

    /** 流程发起人备注 */
    @Excel(name = "流程发起人备注")
    private String initiateNote;

    /** 流程发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date initiateTime;

    /** 流程处理人 */
    @Excel(name = "流程处理人")
    private String handleBy;

    /** 处理人意见 */
    @Excel(name = "处理人意见")
    private String handleNote;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 文件访问地址 */
    @Excel(name = "文件访问地址")
    private String fileNames;

    /** 1=未提交，2=待处理，3=已处理，4=已驳回 */
    @Excel(name = "1=未提交，2=待处理，3=已处理，4=已驳回")
    private Integer status;

    /** 自增主键 */
    private Long id;

    /** 逻辑删除 0-正常 1-删除 */
    @Excel(name = "逻辑删除 0-正常 1-删除")
    private Integer deleteFlag;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ExpenseClaimSub))
        {
            return false;
        }
        ExpenseClaimSub that = (ExpenseClaimSub) o;
        return Objects.equals(expenseClaimId, that.expenseClaimId) && Objects.equals(initiateNote, that.initiateNote) && Objects.equals(initiateTime, that.initiateTime) && Objects.equals(handleBy, that.handleBy) && Objects.equals(handleNote, that.handleNote) && Objects.equals(handleTime, that.handleTime) && Objects.equals(fileNames, that.fileNames) && Objects.equals(status, that.status) && Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseClaimId, initiateNote, initiateTime, handleBy, handleNote, handleTime, fileNames, status, id, deleteFlag);
    }
}
