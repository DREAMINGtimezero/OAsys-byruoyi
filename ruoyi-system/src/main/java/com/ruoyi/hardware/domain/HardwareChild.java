package com.ruoyi.hardware.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//硬件子表
@Data   //getset方法
@AllArgsConstructor  //全参
@NoArgsConstructor   //无参
public class HardwareChild extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    private Long id;

    /** 对应t_hardware的id */
    @Excel(name = "对应t_hardware的id")
    private Long hardwareId;

    /** 发起意见 */
    @Excel(name = "发起意见")
    private String initiateNote;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "发起时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
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

    /** 删除标记 */
    @Excel(name = "删除标记")
    private Long deleteFlag;

    /** 文件 */
    @Excel(name = "文件")
    private String fileNames;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    private Integer hardwareStatus;
}
