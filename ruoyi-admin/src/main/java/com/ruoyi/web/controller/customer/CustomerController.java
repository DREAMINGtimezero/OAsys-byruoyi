package com.ruoyi.web.controller.customer;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.customer.domain.Customer;
import com.ruoyi.customer.domain.CustomerExport;
import com.ruoyi.customer.service.CustomerService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/customer/dev/customer")
public class CustomerController extends BaseController {
    private String prefix = "customer/dev/customer";
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ISysPostService sysPostService;

    @GetMapping("/initLoads")//主页面跳转页面
    public String initLoads(@RequestParam(name = "status", required = false) Integer status, ModelMap modelMap) {
        modelMap.put("isCeo", false);
        modelMap.put("status", status);
        SysPost post = new SysPost();
        post.setPostCode(Constants.GENERAL_MANAGER_CODE);
        List<SysUser> userList = sysPostService.getUserListByPost(post);
        for (SysUser sysUser : userList) {
            if (sysUser.getLoginName().equals(getSysUser().getLoginName())) {
                modelMap.put("isCeo", true);
            }
        }
        return prefix + "/customer";
    }

    /**
     * 新增客户
     *
     * @return 新增页面
     */
    @GetMapping("/add")//添加按钮跳转页面
    public String add(ModelMap mp) {
        return prefix + "/customer-add";
    }
    /**
     * 新增客户
     *
     * @return 新增结果
     */
    @Log(title = "客户-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Validated Customer customer) {
        log.info("新增客户：{}", customer);
        customer.setDataSource(1);
        return toAjax(customerService.insertCustomerFromSys(customer));
    }

    /**
     * 修改客户
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Customer customer = customerService.selectCustomerById(id);
        mmap.put("customer", customer);
        return prefix + "/customer-edit";
    }

    /**
     * 客户修改
     *
     * @return 修改
     */
    @Log(title = "客户-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Customer customer) {
        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 修改开发状态
     *
     * @return 修改
     */
    @Log(title = "客户-修改开发状态", businessType = BusinessType.UPDATE)
    @PutMapping("/devStatus")
    @ResponseBody
    public AjaxResult changeStatus(Customer customer) {
        return toAjax(customerService.changeStatus(customer.getId(), customer.getDevStatus()));
    }

    /**
     * 查询客户列表
     *
     * @return 列表数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Customer customer) {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 导出客户列表
     */
    @Log(title = "客户数据导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Customer customer)
    {
        List<CustomerExport> list = customerService.selectCustomerExportList(customer);
        ExcelUtil<CustomerExport> util = new ExcelUtil<CustomerExport>(CustomerExport.class);
        return util.exportExcel(list, "客户数据");
    }
    /**
     * 删除客户
     *
     * @return 删除结果
     */
    @Log(title = "客户-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
