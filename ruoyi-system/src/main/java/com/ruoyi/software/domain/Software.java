package com.ruoyi.software.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 软件对象 software
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Software extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    private Long id;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 删除标记：0=未删除，1=已删除 */
    @Excel(name = "删除标记：0=未删除，1=已删除")
    private Integer deleteFlag;

    /** 软件编号 */
    @Excel(name = "软件编号")
    private String softwareCode;

    /** 软件名称 */
    @Excel(name = "软件名称")
    private String softwareName;

    /** 所属项目 */
    @Excel(name = "所属项目")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 状态：1=未提交，2=处理中，3=已完成 */
    @Excel(name = "状态：1=未提交，2=处理中，3=已完成")
    private Integer status;

    /** 描述信息 */
    @Excel(name = "描述信息")
    private String description;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String note;

    /** 费控类型：1=费控内，2=费控外 */
    @Excel(name = "费控类型：1=费控内，2=费控外")
    private Integer costType;

    /** 金额（存储实际金额的100倍） */
    @Excel(name = "金额", readConverterExp = "存=储实际金额的100倍")
    private BigDecimal money;


}
