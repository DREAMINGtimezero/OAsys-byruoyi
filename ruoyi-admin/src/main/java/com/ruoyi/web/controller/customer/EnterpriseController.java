package com.ruoyi.web.controller.customer;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.enterprise.domain.Enterprise;
import com.ruoyi.enterprise.domain.EnterpriseExport;
import com.ruoyi.enterprise.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/customer/dev/enterprise")
public class EnterpriseController extends BaseController {
    private String prefix = "customer/dev/enterprise";

    @Autowired
    private EnterpriseService enterpriseService;

    @GetMapping("/initLoads")
    public String initLoads(@RequestParam(name = "status", required = false) Integer status, ModelMap modelMap) {
        modelMap.put("isCeo", false);
        modelMap.put("status", status);
        return prefix + "/enterprise";
    }

    /**
     * 新增企业
     */
    @GetMapping("/add")
    public String add(ModelMap mp) {
        return prefix + "/enterprise-add";
    }

    /**
     * 新增保存企业
     */
    @Log(title = "企业-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Validated Enterprise enterprise) {
        log.info("新增企业：{}", enterprise);
        return toAjax(enterpriseService.insertEnterprise(enterprise));
    }

    /**
     * 修改企业
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Enterprise enterprise = enterpriseService.selectEnterpriseById(id);
        mmap.put("enterprise", enterprise);
        return prefix + "/enterprise-edit";
    }

    /**
     * 保存修改企业
     */
    @Log(title = "企业-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Enterprise enterprise) {
        return toAjax(enterpriseService.updateEnterprise(enterprise));
    }

    /**
     * 修改开发状态
     */
    @Log(title = "企业-修改开发状态", businessType = BusinessType.UPDATE)
    @PutMapping("/devStatus")
    @ResponseBody
    public AjaxResult changeStatus(Enterprise enterprise) {
        return toAjax(enterpriseService.changeStatus(enterprise.getId(), enterprise.getDevStatus()));
    }

    /**
     * 查询企业列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Enterprise enterprise) {
        startPage();
        List<Enterprise> list = enterpriseService.selectEnterpriseList(enterprise);
        return getDataTable(list);
    }

    /**
     * 导出企业列表
     */
    @Log(title = "企业数据导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Enterprise enterprise) {
        List<EnterpriseExport> list = enterpriseService.selectEnterpriseExportList(enterprise);
        ExcelUtil<EnterpriseExport> util = new ExcelUtil<EnterpriseExport>(EnterpriseExport.class);
        return util.exportExcel(list, "企业数据");
    }

    /**
     * 删除企业
     */
    @Log(title = "企业-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(enterpriseService.deleteEnterpriseByIds(ids));
    }

    /**
     * 导入企业数据
     */
    @GetMapping("/importData")
    public String importData() {
        return prefix + "/enterprise-import";
    }

    /**
     * 导入企业数据
     */
    @Log(title = "企业数据导入", businessType = BusinessType.IMPORT)
    @PostMapping("/importData")
    @ResponseBody
    public AjaxResult importData(@RequestParam("file") MultipartFile file, @RequestParam("updateSupport") boolean updateSupport) throws Exception {
        ExcelUtil<Enterprise> util = new ExcelUtil<Enterprise>(Enterprise.class);
        List<Enterprise> enterpriseList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = enterpriseService.importEnterprise(enterpriseList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    /**
     * 导出导入模板
     */
    @PostMapping("/importTemplate")
    @ResponseBody
    public AjaxResult importTemplate() {
        ExcelUtil<Enterprise> util = new ExcelUtil<Enterprise>(Enterprise.class);
        return util.importTemplateExcel("企业数据");
    }
}