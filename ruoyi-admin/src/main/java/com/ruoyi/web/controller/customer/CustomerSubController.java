package com.ruoyi.web.controller.customer;

import java.util.List;

import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.customer.domain.CustomerSub;
import com.ruoyi.customer.service.CustomerSubService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户沟通流程Controller
 *
 * @author YJ
 * @date 2025-09-22
 */
@Controller
@RequestMapping("/customer/dev/sub")
public class CustomerSubController extends BaseController {
    private final String prefix = "customer/dev/sub";

    @Autowired
    private CustomerSubService customerSubService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/detail/{id}")
    public String sub(@PathVariable("id") Long id, ModelMap modelMap) {
        Customer customer = customerService.selectCustomerById(id);
        modelMap.put("customer", customer);
        return prefix + "/customer-sub";
    }

    /**
     * 查询客户沟通流程
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam("customerId") Long customerId) {
        startPage();
        List<CustomerSub> list = customerSubService.selectCustomerSubListByCustomerId(customerId);
        return getDataTable(list);
    }

    /**
     * 导出客户沟通流程
     */
    @Log(title = "客户沟通流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer) {
        CustomerSub customerSub = new CustomerSub();
        customerSub.setCustomerId(customer.getId());
        List<CustomerSub> list = customerSubService.selectCustomerSubList(customerSub);
        ExcelUtil<CustomerSub> util = new ExcelUtil<CustomerSub>(CustomerSub.class);
        return util.exportExcel(list, "客户沟通流程数据");
    }

    /**
     * 新增客户沟通流程数据
     */
    @GetMapping("/add")
    public String add(@RequestParam("customerId")Long customerId) {
        return prefix + "/customer-sub-add";
    }

    /**
     * 新增保存客户沟通流程数据
     */
    @Log(title = "客户沟通流程数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(CustomerSub customerSub) {
        return toAjax(customerSubService.insertCustomerSub(customerSub));
    }

    /**
     * 修改客户沟通流程数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        CustomerSub customerSub = customerSubService.selectCustomerSubById(id);
        mmap.put("customerSub", customerSub);
        return prefix + "/customer-sub-edit";
    }

    /**
     * 修改保存客户沟通流程
     */
    @Log(title = "客户沟通流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(CustomerSub customerSub) {
        return toAjax(customerSubService.updateCustomerSub(customerSub));
    }

    /**
     * 删除客户沟通流程
     */
    @Log(title = "客户沟通流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customerSubService.deleteCustomerSubByIds(ids));
    }
}
