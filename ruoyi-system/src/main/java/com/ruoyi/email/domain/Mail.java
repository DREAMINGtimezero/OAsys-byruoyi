package com.ruoyi.email.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail extends BaseEntity {
    /** 主键，自增 */
    private Long id;

    /** 邮件主题 */
    @Excel(name = "邮件主题")
    private String emailSubject;

    /** 邮件内容 */
    @Excel(name = "邮件内容")
    private String emailDetail;

    /** 发送邮件的附件文件 */
    @Excel(name = "发送邮件的附件文件")
    private String fileNames;

    /** 接收人 */
    @Excel(name = "接收人")
    private String emailBy;

    /** 邮件发送时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "邮件发送时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date emailTime;

    /** 状态 1=未发送2=已发送 */
    @Excel(name = "状态 1=未发送2=已发送")
    private Integer status;

    /** 删除标记  false=存在；true=删除 */
    @Excel(name = "删除标记  false=存在；true=删除")
    private Integer deleteFlag;

    /** 备注 */
    @Excel(name = "备注")
    private String note;


    /******************* 查询条件使用 ********************/
    /** 起始创建时间 */
    private String beginCreateTime;

    /** 结束创建时间 */
    private String endCreateTime;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
}
