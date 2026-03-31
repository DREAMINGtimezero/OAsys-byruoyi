package com.ruoyi.myservice.service;

import java.util.List;

import com.ruoyi.myservice.domain.MyService;
import com.ruoyi.myservice.domain.MyServiceSub;
import org.springframework.lang.NonNull;

/**
 * 服务子表Service接口
 *
 * @author YJ
 * @date 2025-07-09
 */
public interface MyServiceSubService
{
    /**
     * 查询服务子表
     *
     * @param id 服务子表主键
     * @return 服务子表
     */
    MyServiceSub selectMyServiceSubById(Long id);

    /**
     * 查询服务子表列表
     *
     * @param myServiceSub 服务子表
     * @return 服务子表集合
     */
    List<MyServiceSub> selectMyServiceSubList(MyServiceSub myServiceSub);

    /**
     * 新增服务子表
     *
     * @param myServiceSub 服务子表
     * @return 结果
     */
    int insertMyServiceSub(MyServiceSub myServiceSub);

    /**
     * 修改服务子表
     *
     * @param myServiceSub 服务子表
     * @return 结果
     */
    int updateMyServiceSub(MyServiceSub myServiceSub);

    /**
     * 批量删除服务子表
     *
     * @param ids 需要删除的服务子表主键集合
     * @return 结果
     */
    int deleteMyServiceSubByIds(String ids);

    /**
     * 删除服务子表信息
     *
     * @param id 服务子表主键
     * @return 结果
     */
    int deleteMyServiceSubById(Long id);

    /**
     * 新增服务子表项
     * @param relativeId 关联的父表项id
     * @return 新增的服务子表项
     */
    MyServiceSub add(Long relativeId);


    /**
     * 查询子表中有多少项符合指定的状态
     * @param statusList 状态列表
     * @param relativeId 关联的主表id
     * @return 符合状态的项数
     */
    int searchByStatusList(@NonNull Integer[] statusList, @NonNull Long relativeId);

    /**
     * 提交进程
     * @param sub 待提交的子表项
     * @return 提交结果
     */
    Integer submitProcess(MyServiceSub sub);

    /**
     * 处理进程
     * @param sub 待处理的子表项
     * @return 处理结果
     */
    Integer handleProcess(@NonNull MyServiceSub sub);

    /**
     * 验证当前登录用户是否有提交权限
     * @param subId 子表项id
     * @return 验证结果
     */
    boolean submitPermission(@NonNull Long subId);

    /**
     * 验证当前登录用户是否有提交权限
     * 两个参数都是数据库中的原始数据
     * @param mainData 主表数据
     * @param subData 关联的子表数据
     * @return 验证结果
     */
    boolean submitPermission(@NonNull MyService mainData, @NonNull MyServiceSub subData);

    /**
     * 验证当前登录用户是否有处理权限
     * @param subId 子表项id
     * @return 验证结果
     */
    boolean handlePermission(@NonNull Long subId);

    /**
     * 验证当前登录用户是否有处理权限
     * 两个参数都是数据库中的原始数据
     * @param mainData 主表数据
     * @param subData 关联的子表数据
     * @return 验证结果
     */
    boolean handlePermission(@NonNull MyService mainData, @NonNull MyServiceSub subData);

    /**
     * 删除与主表关联的子表数据
     * @param ids 主键id的集合
     * @return 删除结果
     */
    int deleteRelativeData(@NonNull Long[] ids);
}
