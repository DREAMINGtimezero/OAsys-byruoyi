package com.ruoyi.installation.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

//差旅费用申请实体类  t_out
@Data   //getset方法
@AllArgsConstructor  //全参
@NoArgsConstructor   //无参
public class Out extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 差旅费编号   CL yyyy-MM-DD HH:mm:ss.SSS */
    @Excel(name = "差旅费编号   CL yyyy-MM-DD HH:mm:ss.SSS")
    private String outCode;

    /** 差旅描述 */
    @Excel(name = "差旅描述")
    private String outDescribe;

    /** 差旅详情 */
    @Excel(name = "差旅详情")
    private String outDetails;

    /** 申请金额 */
    @Excel(name = "申请金额")
    private BigDecimal outMoney;

    /** 所属项目编号 */
    @Excel(name = "所属项目编号")
    private String projectCode;

    /** 状态  1：未提交，2：处理中3：已完成 */
    @Excel(name = "状态  1：未提交，2：处理中3：已完成")
    private Integer status;

    /** 删除标记   删除标记  false=存在；true=删除 */
    @Excel(name = "删除标记   删除标记  false=存在；true=删除")
    private Long deleteFlag;

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
