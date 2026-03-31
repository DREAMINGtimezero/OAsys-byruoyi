package com.ruoyi.system.service;

import com.ruoyi.system.domain.BarChart;
import com.ruoyi.system.domain.CardData;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author YJ
 */
public interface ISysIndexService {
    /**
     * 获取条状图统计数据
     * @return 统计数据
     */
    BarChart getBarChart();

    /**
     * @Description  获取首页卡片数据信息
     * @Author wangshilin
     * @Date 2025/7/14 16:14
     **/
    List<CardData> getCardData(CardData cardData);

    /**
     * 统计不同状态的项目数量
     *
     * @return 包含各状态数量的Map
     */
    Map<String, Integer> countByStatus();
}
