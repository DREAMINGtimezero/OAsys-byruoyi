package com.ruoyi.customer.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.domain.CustomerExport;
import com.ruoyi.customer.domain.CustomerSub;
import com.ruoyi.customer.mapper.CustomerSubMapper;
import com.ruoyi.website.domain.RawCustomerMessage;
import com.ruoyi.customer.mapper.CustomerMapper;
import com.ruoyi.customer.service.CustomerService;

import java.util.List;

import com.ruoyi.customer.service.CustomerSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 客户Service业务层处理
 *
 * @author zjl
 * @date 2025-09-22
 */
@Slf4j
@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerSubService customerSubService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CustomerSubMapper customerSubMapper;

    @Override
    public int insertCustomerFromSys(Customer customer) {
        customer.setDataSource(2);
        customer.setDevStatus(1);
        customer.setCreateBy(getLoginName());
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public int insertCustomerFromWebsite(Customer customer) {
        customer.setDataSource(1);
        customer.setDevStatus(1);
        customer.setCreateBy(Constants.ANONYMOUS_USER);
        return customerMapper.insertCustomer(customer);
    }

    @Override
    public int updateCustomer(Customer customer) {
        initDomainUpdate(customer);
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public int changeStatus(Long id, Integer devStatus) {
        if(!allowUpdate(id))
        {
            return 0;
        }
        Customer customer = new Customer();
        customer.setId(id);
        customer.setDevStatus(devStatus);
        return updateCustomer(customer);
    }

    public boolean allowUpdate(long id) {
        Customer priviousCustomer = selectCustomerById(id);
        return !priviousCustomer.getDevStatus().equals(4);
    }

    @Override
    public Customer selectCustomerById(Long id) {
        return customerMapper.selectCustomerById(id);
    }


    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        List<Customer> list = customerMapper.selectCustomerList((Customer)initDomainCreate(customer));
        createByReplace(list);
        updateByReplace(list);
        return list;
    }

    @Override
    public int deleteCustomerByIds(String ids) {
        //只能删除未提交或者已失败的客户
        Long[] idList = Convert.toLongArray(ids);
        for (Long id : idList) {
            Customer customer = selectCustomerById(id);
            initDomainUpdate(customer);
            customerMapper.updateCustomer(customer);
        }
        return customerMapper.deleteCustomerByIds(Convert.toStrArray(ids));
    }

    @Override
    public int addWithMessage(RawCustomerMessage message) {
        Customer customer = new Customer();
        //设置来源
        customer.setCustomerName(message.getName());
        customer.setEmail(message.getEmail());
        customer.setPhone(message.getTelephone());
        customer.setCapital(message.getCompany());

        CustomerSub customerSub = new CustomerSub();
        customerSub.setNote(message.getSubject() + ":" + message.getContent());
        Integer result = transactionTemplate.execute(status -> {
            try {
                int temp = insertCustomerFromWebsite(customer);
                customerSub.setCustomerId(customer.getId());
                return temp + customerSubMapper.insertCustomerSub(customerSub);
            } catch (Exception e) {
                status.setRollbackOnly();
                log.error("添加网页来源的客户失败,客户信息:{}, error:{}", customer, e.getMessage(), e);
            }
            return 0;
        });
        return result == null ? 0 : result;
    }

    @Override
    public boolean exists(Customer customer) {
        return !selectCustomerList(customer).isEmpty();
    }

    @Override
    public List<CustomerExport> selectCustomerExportList(Customer customer) {
        return customerMapper.selectCustomerExportList(customer);
    }

}
