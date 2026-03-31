package com.ruoyi.hardware.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Arrays;

//硬件实体类  t_hardware
@Data   //getset方法
@AllArgsConstructor  //全参
@NoArgsConstructor   //无参
public class Hardware  extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键，自增 */
    private Long id;

    /** 硬件编号 */
    @Excel(name = "硬件编号")
    private String hardwareCode;

    /** 硬件名称 */
    @Excel(name = "硬件名称")
    private String hardwareName;

    /** 硬件详情 */
    @Excel(name = "硬件详情")
    private String hardwareDetails;

    /** 硬件金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 所属项目编号 */
    @Excel(name = "所属项目编号")
    private String projectCode;

    /** 状态 1：未提交，2：处理中3：已完成 */
    @Excel(name = "状态 1：未提交，2：处理中3：已完成")
    private Integer status;

    /** 费控类型  1=费控内，2=费控外 */
    @Excel(name = "费控类型  1=费控内，2=费控外")
    private Integer costType;

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

    /** 项目名称 */
    private String projectName;
}
