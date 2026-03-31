package com.ruoyi.pleasemoney.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Description 请款配置子表bean
 * @Author wangshilin
 * @Date 2025/7/4 09:15
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PleaseMoneyConfigSub extends BaseEntity {

    /** 主键 */
    private Long id;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 主表主键 */
    private Long mId;

    /** 请款类型 */
    private String pleaseMoneyType;

    /** 请款金额 */
    private BigDecimal money;

    /** 排序 */
    private Integer sort;

    /** 状态 */
    private Integer status;

    /** 删除标记 (false:存在；true:删除) */
    private Integer deleteFlag;
}
