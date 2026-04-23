package com.ruoyi.enterprise.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enterprise extends BaseEntity {
    /** 主键id */
    private Long id;

    /** 主键数组 ids */
    private Long[] ids;

    /** 删除标记：0=未删除，1=已删除 */
    private Integer deleteFlag;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String enterpriseName;

    /** 地区 */
    @Excel(name = "地区")
    private String region;

    /** 成立时间 */
    @Excel(name = "成立时间")
    private String establishmentDate;

    /** 注册资本 */
    @Excel(name = "注册资本")
    private String registeredCapital;

    /** 实缴资本 */
    @Excel(name = "实缴资本")
    private String paidInCapital;

    /** 人数 */
    @Excel(name = "人数")
    private String employeeCount;

    /** 产品 */
    @Excel(name = "产品")
    private String product;

    /** 网站地址 */
    @Excel(name = "网站地址")
    private String website;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 来源 */
    @Excel(name = "来源：1=公司网页，2=系统新增")
    private Integer dataSource;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 详情 */
    @Excel(name = "详情")
    private String detail;

    /** 开发状态：1=未开发，2=开发中，3=失败，4=成功 */
    @Excel(name = "开发状态", readConverterExp = "1=未开发,2=开发中,3=失败,4=成功")
    private Integer devStatus;

    /** 最新进展 */
    private String progress;
}