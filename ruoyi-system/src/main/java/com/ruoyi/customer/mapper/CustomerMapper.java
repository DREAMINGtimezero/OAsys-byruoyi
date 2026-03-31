package com.ruoyi.customer.mapper;

import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.domain.CustomerExport;

import java.util.List;

public interface CustomerMapper {
    /**
     * 新增客户
     *
     * @param customer 客户
     * @return 结果
     */
    public  int insertCustomer(Customer customer);
    /**
     * 更新客户
     *
     * @param customer 客户
     * @return 结果
     */
    public int updateCustomer(Customer customer);
    /**
     * 删除客户
     *
     * @param id 客户主键
     * @return 结果
     */
    public int deleteCustomerById(Long id);
    /**
     * 批量删除客户
     *
     * @param ids 需要删除的客户主键
     * @return 结果
     */
    public int deleteCustomerByIds(String[] ids);
    /**
     * 查询客户
     *
     * @param id 客户主键
     * @return 客户
     */
    public Customer selectCustomerById(Long id);
    /**
     * 查询客户列表
     *
     * @param customer 客户
     * @return 客户集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 导出客户列表
     *
     * @param customer 客户
     * @return 客户集合
     */
    public List<CustomerExport> selectCustomerExportList(Customer customer);
}
