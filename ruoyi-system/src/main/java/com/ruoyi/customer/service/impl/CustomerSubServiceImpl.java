package com.ruoyi.customer.service.impl;

import java.beans.Transient;
import java.util.List;
import java.util.concurrent.locks.Condition;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.customer.mapper.CustomerSubMapper;
import com.ruoyi.customer.domain.CustomerSub;
import com.ruoyi.customer.service.CustomerSubService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 客户子表Service业务层处理
 *
 * @author YJ
 * @date 2025-09-22
 */
@Service
public class CustomerSubServiceImpl extends BaseServiceImpl implements CustomerSubService {
    @Autowired
    private CustomerSubMapper customerSubMapper;
    @Autowired
    private CustomerService customerService;

    /**
     * 查询客户子表
     *
     * @param id 客户子表主键
     * @return 客户子表
     */
    @Override
    public CustomerSub selectCustomerSubById(Long id) {
        return customerSubMapper.selectCustomerSubById(id);
    }

    /**
     * 查询客户子表列表
     *
     * @param customerSub 客户子表
     * @return 客户子表
     */
    @Override
    public List<CustomerSub> selectCustomerSubList(CustomerSub customerSub) {
        return customerSubMapper.selectCustomerSubList(customerSub);
    }

    /**
     * 新增客户子表
     *
     * @param customerSub 客户子表
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCustomerSub(CustomerSub customerSub) {
        Customer customer = new Customer();
        customer.setId(customerSub.getCustomerId());

        initDomainCreate(customerSub);
        return customerSubMapper.insertCustomerSub(customerSub)
                + customerService.updateCustomer(customer);
    }

    /**
     * 修改客户子表数据
     *
     * @param customerSub 客户子表数据
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCustomerSub(CustomerSub customerSub) {
        Customer customer = new Customer();
        customer.setId(customerSub.getCustomerId());

        initDomainUpdate(customerSub);
        return customerSubMapper.updateCustomerSub(customerSub)
                + customerService.updateCustomer(customer);
    }

    /**
     * 批量删除客户子表数据
     *
     * @param ids 需要删除的客户子表主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCustomerSubByIds(String ids) {
        return customerSubMapper.deleteCustomerSubByIds(Convert.toStrArray(ids), getLoginName());
    }

    /**
     * 删除客户子表信息
     *
     * @param id 客户子表主键
     * @return 结果
     */
    @Override
    public int deleteCustomerSubById(Long id) {
        return customerSubMapper.deleteCustomerSubById(id, getLoginName());
    }

    @Override
    public List<CustomerSub> selectCustomerSubListByCustomerId(Long customerId) {
        CustomerSub condition = new CustomerSub();
        condition.setCustomerId(customerId);
        return customerSubMapper.selectCustomerSubList(condition);
    }
}
