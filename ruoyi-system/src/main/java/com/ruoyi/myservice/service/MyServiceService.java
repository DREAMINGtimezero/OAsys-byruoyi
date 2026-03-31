package com.ruoyi.myservice.service;

import java.util.List;
import com.ruoyi.myservice.domain.MyService;
import lombok.NonNull;

/**
 * 服务Service接口
 *
 * @author YJ
 * @date 2025-07-09
 */
 public interface MyServiceService
{
    /**
     * 查询服务
     *
     * @param id 服务主键
     * @return 服务
     */
     MyService selectMyServiceById(Long id);

    /**
     * 查询服务列表
     *
     * @param myService 服务
     * @return 服务集合
     */
     List<MyService> selectMyServiceList(MyService myService);

    /**
     * 新增服务
     *
     * @param myService 服务
     * @return 结果
     */
     int insertMyService(MyService myService);

    /**
     * 修改服务
     *
     * @param myService 服务
     * @return 结果
     */
     int updateMyService(MyService myService);

    /**
     * 批量删除服务
     *
     * @param ids 需要删除的服务主键集合
     * @return 结果
     */
     int deleteMyServiceByIds(String ids);

    /**
     * 删除服务信息
     *
     * @param id 服务主键
     * @return 结果
     */
     int deleteMyServiceById(Long id);

    /**
     * 检查是否可以结束
     * @param id 服务主键
     * @return 检查结果
     */
    boolean overAble(Long id);

    /**
     * 检查是否可以对给定的id列表执行批量删除
     * @param idList id列表
     * @return 是否可以执行批量删除
     */
    boolean isDeleteAble(@NonNull Long[] idList);
}
