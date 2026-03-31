package com.ruoyi.customer.mapper;

import java.util.List;
import com.ruoyi.customer.domain.CustomerSub;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户子Mapper接口
 *
 * @author YJ
 * @date 2025-09-22
 */
@Mapper
public interface CustomerSubMapper
{
    /**
     * 查询客户子
     *
     * @param id 客户子主键
     * @return 客户子
     */
     CustomerSub selectCustomerSubById(Long id);

    /**
     * 查询客户子列表
     *
     * @param customerSub 客户子
     * @return 客户子集合
     */
     List<CustomerSub> selectCustomerSubList(CustomerSub customerSub);

    /**
     * 新增客户子
     *
     * @param customerSub 客户子
     * @return 结果
     */
     int insertCustomerSub(CustomerSub customerSub);

    /**
     * 修改客户子
     *
     * @param customerSub 客户子
     * @return 结果
     */
     int updateCustomerSub(CustomerSub customerSub);

    /**
     * 删除客户子
     *
     * @param id 客户子主键
     * @return 结果
     */
     int deleteCustomerSubById(@Param("id")Long id, @Param("loginName")String loginName);

    /**
     * 批量删除客户子
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
     int deleteCustomerSubByIds(@Param("ids")String[] ids, @Param("loginName")String loginName);
}
