package com.ruoyi.contract.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同信息对象 t_contract
 * 
 * @author ruoyi
 * @date 2025-06-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseEntity
{
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

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractId;

    /** 合同名称 */
    @Excel(name = "合同名称")
    private String contractName;

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


}