package com.ruoyi.myservice.mapper;

import java.util.List;

import com.ruoyi.myservice.domain.MyService;
import org.apache.ibatis.annotations.Param;

/**
 * 服务Mapper接口
 *
 * @author YJ
 * @date 2025-07-09
 */
public interface MyServiceMapper {
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
     * 删除服务
     *
     * @param id       服务主键
     * @param updateBy 修改人
     * @return 结果
     */
    int deleteMyServiceById(@Param("id") Long id, @Param("updateBy") String updateBy);

    /**
     * 批量删除服务
     *
     * @param ids      需要删除的数据主键集合
     * @param updateBy 修改人
     * @return 结果
     */
    int deleteMyServiceByIds(@Param("ids") Long[] ids, @Param("updateBy") String updateBy);

    /**
     * 查询给定的id中有多少是符合删除条件的
     * @param ids 主键id列表
     * @return 符合可删除条件的项数
     */
    int selectDeleteAble(@Param("ids") Long[] ids);
}
