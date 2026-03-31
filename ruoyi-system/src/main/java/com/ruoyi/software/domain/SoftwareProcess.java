package com.ruoyi.software.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 软件流程处理对象 t_software_process
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoftwareProcess extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    private Long id;

    /** 删除标记：0=未删除，1=已删除 */
    @Excel(name = "删除标记：0=未删除，1=已删除")
    private Integer deleteFlag;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date initiateTime;

    /** 发起意见 */
    @Excel(name = "发起意见")
    private String initiateNote;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handleBy;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handleTime;

    /** 处理意见 */
    @Excel(name = "处理意见")
    private String handleNote;

    /** 状态：1=未提交，2=待处理，3=已处理，4=已驳回 */
    @Excel(name = "状态：1=未提交，2=待处理，3=已处理，4=已驳回")
    private Integer status;

    /** 软件主键id */
    @Excel(name = "软件主键id")
    private Long softwareId;

    /** 文件访问路径 */
    @Excel(name = "文件访问路径")
    private String fileNames;

}
