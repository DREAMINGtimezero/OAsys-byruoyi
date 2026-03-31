package com.ruoyi.system.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.system.domain.BarChart;
import com.ruoyi.system.domain.CardData;
import com.ruoyi.system.domain.StatisticResult;
import com.ruoyi.system.mapper.SysIndexMapper;
import com.ruoyi.system.service.ISysIndexService;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.mapper.TechnicalAgreementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author YJ
 */
@Service
public class SysIndexServiceImpl extends BaseServiceImpl implements ISysIndexService {

    @Autowired
    SysIndexMapper indexMapper;
    @Override
    public BarChart getBarChart()  {

        BarChart barChart = new BarChart();
        //设置图例
        barChart.setLegend(new String[]{"待处理"});
        //设置横坐标
        barChart.setXAxis(new String[]{"合同", "技术协议", "差旅", "请款", "软件", "服务", "硬件","财务报销","办公用品","服务合同","转正申请"});
        /*获取横坐标对应的数值*/
        Map<String, Integer[]> series = new HashMap<>(barChart.getLegend().length * 4 / 3);
        //统计进行中的项
        StatisticResult statisticArray = indexMapper.getStatistic(Constants.SubStatus.PROCESSING, getLoginName());
        Integer[] processing = new Integer[]{
                statisticArray.getContractCount(),
                statisticArray.getTechnicalCount(),
                statisticArray.getOutCount(),
                statisticArray.getMoneyCount(),
                statisticArray.getSoftwareCount(),
                statisticArray.getServiceCount(),
                statisticArray.getHardwareCount(),
                statisticArray.getClaimCount(),
                statisticArray.getOfficeSuppliesCount(),
                statisticArray.getServiceContractCount(),
                statisticArray.getApplyCount()
        };
        //统计完成的项
        series.put(barChart.getLegend()[0], processing);
        barChart.setSeries(series);
        return barChart;
    }

    @Override
    public List<CardData> getCardData(CardData cardData) {
        return indexMapper.getCardDataList((CardData)initDomainCreate(cardData));
    }

    @Override
    public Map<String, Integer> countByStatus() {
        // 查询各状态的项目数量
        List<Map<String, Object>> statusCounts = indexMapper.countGroupByStatus();

        // 将结果转换为简单的Map格式
        Map<String, Integer> result = new HashMap<>();
        for (Map<String, Object> count : statusCounts) {
            String status = String.valueOf(count.get("status"));
            Integer countValue = Integer.valueOf(String.valueOf(count.get("count")));
            result.put(status, countValue);
        }

        return result;
    }
}
