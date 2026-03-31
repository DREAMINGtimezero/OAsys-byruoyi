package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.CardData;
import com.ruoyi.system.domain.StatisticResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author YJ
 */
public interface SysIndexMapper {
   /**
    * 获取各个表的统计信息
    * @param status 筛选的状态条件
    * @param loginName 当前登录用户
    * @return 统计结果
    */
   StatisticResult getStatistic(@Param("status")Integer status, @Param("loginName")String loginName);

   /**
    * @Description  获取首页的卡片信息
    * @Author wangshilin
    * @Date 2025/7/14 16:56
    **/
   List<CardData> getCardDataList(CardData cardData);

   /**
    * 按状态分组统计项目数量
    *
    * @return 状态及对应的项目数量
    */
   List<Map<String, Object>> countGroupByStatus();
}
