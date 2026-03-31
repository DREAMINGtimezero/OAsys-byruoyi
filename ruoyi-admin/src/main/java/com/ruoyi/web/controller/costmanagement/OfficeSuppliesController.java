package com.ruoyi.web.controller.costmanagement;
import java.util.List;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.officesupplies.domain.OfficeSupplies;
import com.ruoyi.officesupplies.domain.OfficeSuppliesSub;
import com.ruoyi.officesupplies.service.OfficeSuppliesService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;


/**
 * 办公用品Controller
 *
 * @author gyx
 * @date 2025-08-06
 */
@Controller
@RequestMapping("/cost-management/office-supplies")
public class OfficeSuppliesController extends BaseController {
    private final String prefix = "cost-management/office-supplies";

    @Autowired
    private OfficeSuppliesService officeSuppliesService;

    @GetMapping("/initLoads")
    public String initLoads(@RequestParam(name = "status", required = false) Long status, ModelMap mp) {
        mp.put("status", status);
        return prefix + "/office-supplies";
    }

    /**
     * 跳转新增页面
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/office-supplies-add";
    }

    /**
     * 跳转编辑页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        if(!officeSuppliesService.editAble(id)){
            return "error/unauth";
        }
        OfficeSupplies officeSupplies = officeSuppliesService.selectOfficeSuppliesById(id);
        mmap.put("officeSupplies", officeSupplies);
        return prefix + "/office-supplies-edit";
    }

    /**
     * 跳转详情页面
     */
    @GetMapping("/details/{id}")
    public String detail(OfficeSupplies officeSupplies, ModelMap mmap) {
        mmap.put("officeSupplies", officeSuppliesService.selectOfficeSuppliesById(officeSupplies.getId()));
        return prefix + "/office-supplies-details";
    }

    /**
     * 查询办公用品主表列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(OfficeSupplies officeSupplies) {
        startPage();
        List<OfficeSupplies> list = officeSuppliesService.selectOfficeSuppliesList(officeSupplies);
        return getDataTable(list);
    }

    /**
     * 导出办公用品主表列表
     */
    @Log(title = "导出办公用品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(OfficeSupplies officeSupplies) {
        List<OfficeSupplies> list = officeSuppliesService.selectOfficeSuppliesList(officeSupplies);
        ExcelUtil<OfficeSupplies> util = new ExcelUtil<OfficeSupplies>(OfficeSupplies.class);
        return util.exportExcel(list, "办公用品主表数据");
    }


    /**
     * 新增保存办公用品主表
     */
    @Log(title = "新增办公用品", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(OfficeSupplies officeSupplies) {
        return toAjax(officeSuppliesService.insertOfficeSupplies(officeSupplies));
    }


    /**
     * 修改保存办公用品主表
     */
    @Log(title = "修改办公用品", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(OfficeSupplies officeSupplies) {
        return toAjax(officeSuppliesService.updateOfficeSupplies(officeSupplies));
    }

    /**
     * 删除办公用品主表
     */
    @Log(title = "删除办公用品", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(officeSuppliesService.deleteOfficeSuppliesByIds(ids));
    }

    /*
以下是子表的控制层
 */


    /**
     * 查询办公用品子列表
     */

    @PostMapping("/sub-list")
    @ResponseBody
    public TableDataInfo sublist(@RequestParam Long relativeId) {
        startPage();
        OfficeSuppliesSub officeSuppliesSub = new OfficeSuppliesSub();
        officeSuppliesSub.setOfficeSuppliesId(relativeId);
        List<OfficeSuppliesSub> list = officeSuppliesService.selectOfficeSuppliesSubList(officeSuppliesSub);
        return getDataTable(list);
    }

    /**
     * 新增流程
     */
    @Log(title = "新增办公用品流程", businessType = BusinessType.INSERT)
    @GetMapping("/sub-add/{relativeId}")
    @ResponseBody
    public AjaxResult addSave(@PathVariable("relativeId") Long relativeId) {
        OfficeSuppliesSub officeSuppliesSub = new OfficeSuppliesSub();
        officeSuppliesSub.setOfficeSuppliesId(relativeId);
        int result = officeSuppliesService.insertOfficeSuppliesSub(officeSuppliesSub);
        return AjaxResult.success(result);
    }
    /**
     * 结束流程
     */
    @Log(title = "办公用品流程-结束", businessType = BusinessType.UPDATE)
    @PostMapping("/sub-finish")
    @ResponseBody
    public AjaxResult processFinish(OfficeSupplies officeSupplies) {
        return toAjax(officeSuppliesService.finishOfficeSupplies(officeSupplies));
    }

    /**
     * 跳转至流程发起页面
     **/
    @GetMapping("/sub-submit/{id}")
    public String subInitiate(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        OfficeSuppliesSub officeSuppliesSub = (OfficeSuppliesSub) officeSuppliesService.selectOfficeSuppliesSubById(id);

        // 获取当前登录用户
        String loginUser = getSysUser().getUserName();

        // 检查当前用户是否为发起人
        if (officeSuppliesSub != null && loginUser.equals(officeSuppliesSub.getCreateBy())) {
            mmap.put("officeSuppliesSub", officeSuppliesSub);
            return prefix + "/office-supplies-sub-initiate";
        } else {
            // 非发起人，返回错误信息
            return "error/unauth";
        }
    }

    /**
     * 跳转至流程-处理页面
     **/
    @GetMapping("/sub-handle/{id}")
    public String subHandle(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        OfficeSuppliesSub officeSuppliesSub = (OfficeSuppliesSub) officeSuppliesService.selectOfficeSuppliesSubById(id);

        // 获取当前登录用户
        String loginUser = getSysUser().getUserName();

        // 检查当前用户是否为指定处理人
        if (officeSuppliesSub != null && loginUser.equals(officeSuppliesSub.getHandleBy())) {
            mmap.put("officeSuppliesSub", officeSuppliesSub);
            return prefix + "/office-supplies-sub-handle";
        } else {
            // 非指定处理人，返回错误信息
            return "error/unauth";
        }
    }

    /**
     * 发起流程
     */
    @Log(title = "办公用品流程-发起", businessType = BusinessType.INSERT)
    @PostMapping("/sub-initiate")
    @ResponseBody
    public AjaxResult submitProcess(OfficeSuppliesSub officeSuppliesSub)
    {
        return toAjax(officeSuppliesService.submitsub(officeSuppliesSub));
    }

    /**
     *  处理流程
     **/
    @Log(title = "处理流程通过", businessType = BusinessType.UPDATE)
    @PostMapping("/sub-pass")
    @ResponseBody
    public AjaxResult subpass(OfficeSuppliesSub officeSuppliesSub){
        officeSuppliesSub.setStatus(Constants.SubStatus.PASSED);
        return toAjax(officeSuppliesService.subhandle(officeSuppliesSub));
        }

    @Log(title = "处理流程驳回", businessType = BusinessType.UPDATE)
    @PostMapping("/sub-reject")
    @ResponseBody
    public AjaxResult subreject(OfficeSuppliesSub officeSuppliesSub){
        officeSuppliesSub.setStatus(Constants.SubStatus.REJECTED);
        return toAjax(officeSuppliesService.subhandle(officeSuppliesSub));
    }

    /**
     * 文件下载
     **/
    @GetMapping("/sub-files/{id}")
    @ResponseBody
    public AjaxResult subFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = officeSuppliesService.getsubFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }
}





