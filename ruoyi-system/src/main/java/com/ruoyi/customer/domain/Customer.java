package com.ruoyi.customer.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseEntity {
     /** 主键id */
    private Long id;
    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 删除标记：0=未删除，1=已删除 */
    private Integer deleteFlag;
    /** 客户名称（联系人） */
    @Excel(name = "联系人")
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
   /** 资方类型 */
   @Excel(name = "资方类型", readConverterExp = "1=耐火材料,2=钢铁企业,3=设计院,4=其他", combo = "耐火材料,钢铁企业,设计院,其他")
   private Integer capitalType;
    /** 资方（企业名称） */
    @Excel(name = "企业名称")
    private String capital;
    /** 性质 */
    @Excel(name = "性质")
    private String mode;
    /** 来源 */
    @Excel(name = "来源", readConverterExp = "1=公司网页,2=系统新增", combo = "公司网页,系统新增", separator = "ignore")
    private Integer dataSource;
    /** 开发状态 */
    @Excel(name = "状态", readConverterExp = "1=未开发,2=开发中,3=已失败,4=已完成", combo = "未开发,开发中,已失败,已完成")
    private Integer devStatus;

    /** 最新进展 ：查关联子项的最新的那条进展使用*/
    private String progress;

}
