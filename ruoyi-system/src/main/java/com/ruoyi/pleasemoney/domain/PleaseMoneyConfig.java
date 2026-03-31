package com.ruoyi.pleasemoney.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @Description 请款配置主表bean
 * @Author wangshilin
 * @Date 2025/7/4 09:15
 **/
@Data
public class PleaseMoneyConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 请款名称 */
    private String configName;

    /** 请款详情 */
    private String configDetail;

    /** 项目代码 */
    private String projectCode;

    /** 项目名称 */
    private String projectName;

    /** 合同名称 */
    private String contractName;

    /** 技术协议名称 */
    private String title;

    /** 状态(1未提交，2已提交) */
    private Integer status;

    /** 删除标记 (false:存在；true:删除) */
    private Integer deleteFlag;

    /******************* 查询条件使用 ********************/
    /** 起始创建时间 */
    private String beginCreateTime;

    /** 结束创建时间 */
    private String endCreateTime;
    /******************* 查询条件使用 ********************/
}
