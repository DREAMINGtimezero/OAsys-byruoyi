package com.ruoyi.installation.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

//差旅费用审核流程对象 t_out_child
@Data   //getset方法
@AllArgsConstructor  //全参
@NoArgsConstructor   //无参
public class OutChild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 差旅费主键id */
    @Excel(name = "差旅费主键id")
    private Long outId;

    /** 发起人意见 */
    @Excel(name = "发起人意见")
    private String initiateNote;

    /** 发起时间  */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date initiateTime;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handleBy;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 处理人意见 */
    @Excel(name = "处理人意见")
    private String handleNote;

    /** 状态  1未提交 2待处理
     3已处理 4已驳回 */
    @Excel(name = "状态  1未提交 2待处理3已处理 4已驳回")
    private Integer status;

    /** 删除标记   删除标记  false=存在；true=删除 */
    @Excel(name = "删除标记   删除标记  false=存在；true=删除")
    private Integer deleteFlag;

    /** 文件访问路径 */
    @Excel(name = "文件访问路径")
    private String fileNames;

    private Integer outStatus;

}
