package com.ruoyi.pleasemoney.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

//请款配置孙-第三层对象 t_please_money_config_sun
@Data   //getset方法
@AllArgsConstructor  //全参
@NoArgsConstructor   //无参
public class PleaseMoneyConfigSun extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    private Long id;

    /** 子表的主键 */
    @Excel(name = "子表的主键")
    private Long subId;

    /** 发起人意见 */
    @Excel(name = "发起人意见")
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

    /** 处理意见 */
    @Excel(name = "处理意见")
    private String handleNote;

    /** 1未提交 2待处理
     3已处理 4已驳回 */
    @Excel(name = "1未提交 2待处理 3已处理 4已驳回")
    private Integer status;

    /** 删除标识 */
    @Excel(name = "删除标识")
    private Integer deleteFlag;

    /** 文件 */
    @Excel(name = "文件")
    private String fileNames;

    /** 备注 */
    @Excel(name = "备注")
    private String note;
}
