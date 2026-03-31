package com.ruoyi.customer.domain;

import com.ruoyi.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author YJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class CustomerExport{
    /** 客户名称 */
    @Excel(name = "客户名称")
    private String customerName;
    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;
    /** 微信 */
    @Excel(name = "微信")
    private String wechat;
    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;
    /** 渠道 */
    @Excel(name = "渠道")
    private String channel;
    /** 资方 */
    @Excel(name = "资方")
    private String capital;
    /** 模式 */
    @Excel(name = "模式")
    private String mode;
    /** 来源 */
    @Excel(name = "来源", readConverterExp = "1=公司网页,2=系统新增")
    private Integer dataSource;
    /** 开发状态 */
    @Excel(name = "开发状态",readConverterExp = "1=未开发,2=开发中,3=失败,4=成功")
    private Integer devStatus;
    @Excel(name="进展情况")
    private String progress;
}
