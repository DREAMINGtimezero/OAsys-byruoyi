package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description  首页卡片实体类
 * @Author wangshilin
 * @Date 2025/7/14 16:12
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardData extends BaseEntity {

    /** 卡片标题 */
    private String cardTitle;

    /** 总数 */
    private Integer total;

    /** 未提交 */
    private Integer unSubmitTed;

    /** 进行中 */
    private Integer inProgress;

    /** 已完成 */
    private Integer completed;
}
