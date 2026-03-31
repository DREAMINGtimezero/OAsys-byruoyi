package com.ruoyi.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticResult {
    /**
     * 待处理合同数量
     */
    private Integer contractCount;

    /**
     * 待处理技术协议数量
     */
    private Integer technicalCount;

    /**
     * 待处理差旅费申请数量
     */
    private Integer outCount;

    /**
     * 待处理请款数量
     */
    private Integer moneyCount;
    /**
     * 待处采买-软件申请数量
     */
    private Integer softwareCount;

    /**
     * 待处采买-软件申请数量
     */
    private Integer serviceCount;
    /**
     * 待处采买-硬件申请数量
     */
    private Integer hardwareCount;

    /**
     * 财务报销待处理数量
     */
    private Integer claimCount;

    /**
     * 转正申请待处理数量
     */
    private Integer applyCount;

    /**
     * 办公用品申请待处理数量
     */
    private Integer officeSuppliesCount;

    /**
     * 服务合同待处理数量
     */
    private Integer serviceContractCount;
}
