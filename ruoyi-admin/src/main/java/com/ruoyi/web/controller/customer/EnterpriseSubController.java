package com.ruoyi.web.controller.customer;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.enterprise.domain.Enterprise;
import com.ruoyi.enterprise.domain.EnterpriseSub;
import com.ruoyi.enterprise.service.EnterpriseService;
import com.ruoyi.enterprise.service.EnterpriseSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer/dev/enterprise-sub")
public class EnterpriseSubController extends BaseController {
    private final String prefix = "customer/dev/enterprise-sub";

    @Autowired
    private EnterpriseSubService enterpriseSubService;
    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/detail/{id}")
    public String sub(@PathVariable("id") Long id, ModelMap modelMap) {
        Enterprise enterprise = enterpriseService.selectEnterpriseById(id);
        modelMap.put("enterprise", enterprise);
        return prefix + "/enterprise-sub";
    }

    /**
     * 查询企业沟通流程
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam("enterpriseId") Long enterpriseId) {
        startPage();
        List<EnterpriseSub> list = enterpriseSubService.selectEnterpriseSubListByEnterpriseId(enterpriseId);
        return getDataTable(list);
    }

    /**
     * 导出企业沟通流程
     */
    @Log(title = "企业沟通流程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Enterprise enterprise) {
        EnterpriseSub enterpriseSub = new EnterpriseSub();
        enterpriseSub.setEnterpriseId(enterprise.getId());
        List<EnterpriseSub> list = enterpriseSubService.selectEnterpriseSubList(enterpriseSub);
        ExcelUtil<EnterpriseSub> util = new ExcelUtil<EnterpriseSub>(EnterpriseSub.class);
        return util.exportExcel(list, "企业沟通流程数据");
    }

    /**
     * 新增企业沟通流程数据
     */
    @GetMapping("/add")
    public String add(@RequestParam("enterpriseId") Long enterpriseId, ModelMap modelMap) {
        modelMap.put("enterpriseId", enterpriseId);
        return prefix + "/enterprise-sub-add";
    }

    /**
     * 新增保存企业沟通流程数据
     */
    @Log(title = "企业沟通流程数据", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(EnterpriseSub enterpriseSub) {
        return toAjax(enterpriseSubService.insertEnterpriseSub(enterpriseSub));
    }

    /**
     * 修改企业沟通流程数据
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        EnterpriseSub enterpriseSub = enterpriseSubService.selectEnterpriseSubById(id);
        mmap.put("enterpriseSub", enterpriseSub);
        return prefix + "/enterprise-sub-edit";
    }

    /**
     * 修改保存企业沟通流程
     */
    @Log(title = "企业沟通流程", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(EnterpriseSub enterpriseSub) {
        return toAjax(enterpriseSubService.updateEnterpriseSub(enterpriseSub));
    }

    /**
     * 删除企业沟通流程
     */
    @Log(title = "企业沟通流程", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(enterpriseSubService.deleteEnterpriseSubByIds(ids));
    }
}