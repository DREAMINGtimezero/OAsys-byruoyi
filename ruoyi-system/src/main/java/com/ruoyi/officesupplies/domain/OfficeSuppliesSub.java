package com.ruoyi.officesupplies.domain;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 办公用品子对象 t_office_supplies_sub
 *
 * @author gyx
 * @date 2025-08-06
 */
public class OfficeSuppliesSub extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 办公用品主表id */
    @Excel(name = "办公用品主表id")
    private Long officeSuppliesId;

    /** 发起人意见 */
    @Excel(name = "发起人意见")
    private String initiateNote;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date initiateTime;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handleBy;

    /** 处理人意见 */
    @Excel(name = "处理人意见")
    private String handleNote;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String fileNames;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 主键id */
    private Long id;

    /** 删除标记 */
    @Excel(name = "删除标记")
    private Integer deleteFlag;

    public void setOfficeSuppliesId(Long officeSuppliesId)
    {
        this.officeSuppliesId = officeSuppliesId;
    }

    public Long getOfficeSuppliesId()
    {
        return officeSuppliesId;
    }

    public void setInitiateNote(String initiateNote)
    {
        this.initiateNote = initiateNote;
    }

    public String getInitiateNote()
    {
        return initiateNote;
    }

    public void setInitiateTime(Date initiateTime)
    {
        this.initiateTime = initiateTime;
    }

    public Date getInitiateTime()
    {
        return initiateTime;
    }

    public void setHandleBy(String handleBy)
    {
        this.handleBy = handleBy;
    }

    public String getHandleBy()
    {
        return handleBy;
    }

    public void setHandleNote(String handleNote)
    {
        this.handleNote = handleNote;
    }

    public String getHandleNote()
    {
        return handleNote;
    }

    public void setHandleTime(Date handleTime)
    {
        this.handleTime = handleTime;
    }

    public Date getHandleTime()
    {
        return handleTime;
    }

    public void setFileNames(String fileNames)
    {
        this.fileNames = fileNames;
    }

    public String getFileNames()
    {
        return fileNames;
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
                .append("officeSuppliesId", getOfficeSuppliesId())
                .append("initiateNote", getInitiateNote())
                .append("initiateTime", getInitiateTime())
                .append("handleBy", getHandleBy())
                .append("handleNote", getHandleNote())
                .append("handleTime", getHandleTime())
                .append("fileNames", getFileNames())
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
