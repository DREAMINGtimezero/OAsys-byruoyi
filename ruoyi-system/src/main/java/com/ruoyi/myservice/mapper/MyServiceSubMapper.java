package com.ruoyi.myservice.mapper;

import java.util.List;

import com.ruoyi.myservice.domain.MyServiceSub;
import org.apache.ibatis.annotations.Param;

/**
 * 服务子表Mapper接口
 *
 * @author YJ
 * @date 2025-07-09
 */
public interface MyServiceSubMapper {
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
     * 删除服务子表
     *
     * @param id 服务子表主键
     * @return 结果
     */
    int deleteMyServiceSubById(@Param("id") Long id, @Param("updateBy") String updateBy);

    /**
     * 批量删除服务子表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    int deleteMyServiceSubByIds(@Param("ids") String[] ids, @Param("updateBy") String updateBy);

    /**
     * 查询符合指定状态的项的数量
     *
     * @param statusList 状态列表
     * @param relativeId 主表的关联id
     * @return 数量
     */
    int selectByStatusList(@Param("statusList") Integer[] statusList, @Param("relativeId") Long relativeId);

    /**
     * 删除与主表关联的数据
     * @param ids  关联的主表id的集合
     * @param updateBy 操作人
     * @return 删除结果
     */
    int deleteRelativeData(@Param("ids") Long[] ids,@Param("updateBy") String updateBy);
}
