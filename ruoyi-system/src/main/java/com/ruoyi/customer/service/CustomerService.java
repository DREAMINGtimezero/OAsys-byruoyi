package com.ruoyi.customer.service;

import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.domain.CustomerExport;
import com.ruoyi.website.domain.RawCustomerMessage;

import java.util.List;

public interface CustomerService{
    /**
     * 新增来自CRM系统的客户
     * @param customer 客户
     * @return 结果
     */
    int insertCustomerFromSys(Customer customer);

    /**
     * 新增来自网页的客户
     * @param customer  客户信息
     * @return 结果
     */
    int insertCustomerFromWebsite(Customer customer);

    /**
     * 修改客户
     * @param customer 客户
     * @return 结果
     */
    int updateCustomer(Customer customer);

    /**
     * 查询客户列表
     * @param customer 客户
     * @return 客户列表
     */
    List<Customer> selectCustomerList(Customer customer);

    /**
     * 删除客户
     * @param ids 客户主键集合
     * @return 结果
     */
    int deleteCustomerByIds(String ids);


    int changeStatus(Long id, Integer devStatus);

    /**
     * 查询客户
     * @param id 客户主键
     * @return  客户
     */
    Customer selectCustomerById(Long id);

    /**
     * 根据网页信息添加客户
     * @param message 网页信息
     * @return 添加结果
     */
    int addWithMessage(RawCustomerMessage message);

    boolean exists(Customer customer);

    List<CustomerExport> selectCustomerExportList(Customer customer);

    /**
     * 导入客户数据
     * @param customerList 客户数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @return 结果
     */
    String importCustomer(List<Customer> customerList, Boolean isUpdateSupport);


}

