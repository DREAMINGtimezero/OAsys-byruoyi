package com.ruoyi.customer.service;

import java.util.List;
import com.ruoyi.customer.domain.CustomerSub;

/**
 * 沟通流程Service接口
 *
 * @author YJ
 * @date 2025-09-22
 */
public interface CustomerSubService
{
    /**
     * 查询沟通流程
     *
     * @param id 沟通流程主键
     * @return 沟通流程
     */
     CustomerSub selectCustomerSubById(Long id);

    /**
     * 查询沟通流程列表
     *
     * @param customerSub 沟通流程
     * @return 沟通流程集合
     */
     List<CustomerSub> selectCustomerSubList(CustomerSub customerSub);

    /**
     * 新增沟通流程
     *
     * @param customerSub 沟通流程
     * @return 结果
     */
    int insertCustomerSub(CustomerSub customerSub);

    /**
     * 修改沟通流程
     *
     * @param customerSub 沟通流程
     * @return 结果
     */
    int updateCustomerSub(CustomerSub customerSub);

    /**
     * 批量删除沟通流程
     *
     * @param ids 需要删除的沟通流程主键集合
     * @return 结果
     */
    int deleteCustomerSubByIds(String ids);

    /**
     * 删除沟通流程信息
     *
     * @param id 沟通流程主键
     * @return 结果
     */
    int deleteCustomerSubById(Long id);

    /**
     * 根据客户id查询沟通流程
     * @param customerId 客户id
     * @return 沟通流程列表
     */
    List<CustomerSub> selectCustomerSubListByCustomerId(Long customerId);
}
