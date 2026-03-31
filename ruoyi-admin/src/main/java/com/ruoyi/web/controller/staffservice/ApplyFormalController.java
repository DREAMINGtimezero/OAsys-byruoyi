package com.ruoyi.web.controller.staffservice;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.project.domain.ProjectStatis;
import com.ruoyi.project.service.ProjectStatisService;
import com.ruoyi.staffservice.domain.ApplyFormal;
import com.ruoyi.staffservice.domain.ApplyFormalSub;
import com.ruoyi.staffservice.service.ApplyFormalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Description  转正申请controller
 * @Author wangshilin
 * @Date 2025/6/24 15:37
 **/
@Controller
@RequestMapping("/staff-service/apply-formal")
public class ApplyFormalController extends BaseController {

    private String prefix = "staff-service/apply-formal";

    @Autowired
    private ApplyFormalService applyFormalService;

    @GetMapping("/initLoads")
    public String initLoads(ModelMap mmap){
        mmap.put("userName", getSysUser().getUserName());
        return prefix + "/apply-formal";
    }

    /**
     * 跳转至新增页面
     */
    @GetMapping("/add")
    public String add(){
        return prefix + "/apply-formal-add";
    }

    /**
     * 跳转至修改页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        ApplyFormal applyFormal = applyFormalService.selectApplyFormalById(id);
        mmap.put("applyFormal", applyFormal);
        return prefix + "/apply-formal-edit";
    }

    /**
     * 跳转至详情页面
     */
    @GetMapping("/detail/{id}")
    public String detail(ApplyFormal applyFormal, ModelMap mmap) {
        mmap.put("applyFormal", applyFormalService.selectApplyFormalList(applyFormal).get(Constants.ZERO));
        mmap.put("userName", getSysUser().getUserName());
        return prefix + "/apply-formal-detail";
    }

    /**
     * 跳转至详情提交页面
     */
    @GetMapping("/jump-submit/{id}")
    public String jumpSubmit(ApplyFormalSub applyFormalSub, ModelMap mmap) {
        mmap.put("applyFormalSub", applyFormalService.selectApplyFormalSubList(applyFormalSub).get(Constants.ZERO));
        return prefix + "/apply-formal-detail-submit";
    }

    /**
     * 跳转至详情处理页面
     */
    @GetMapping("/jump-handle/{id}")
    public String jumpHandle(@PathVariable("id") Long id, ModelMap mmap) {
        ApplyFormalSub applyFormalSub = applyFormalService.selectApplyFormalSubById(id);
        String loginUser = getLoginName();
        if (applyFormalSub != null && loginUser.equals(applyFormalSub.getHandleBy())){
            mmap.put("applyFormalSub", applyFormalSub);
            return prefix + "/apply-formal-detail-handle";
        }else {
            return "error/unauth";
        }
    }

    /**
     * 查看工作总结跳转
     */
    @GetMapping("/summarize/{id}")
    public String summarize(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("summarize", applyFormalService.selectApplyFormalById(id));
        return prefix + "/apply-formal-summarize";
    }

    /**
     * 查看给公司的建议跳转
     */
    @GetMapping("/opinion/{id}")
    public String opinion(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("opinion", applyFormalService.selectApplyFormalById(id));
        return prefix + "/apply-formal-opinion";
    }

    /**
     * 查询员工转正申请主列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ApplyFormal applyFormal){
        startPage();
        List<ApplyFormal> list = applyFormalService.selectApplyFormalList(applyFormal);
        return getDataTable(list);
    }

    /**
     * 查询子表数据 通过主表的id
     */
    @PostMapping("/select-sub-byApplyId")
    @ResponseBody
    public TableDataInfo selectApplyFormalSubByApplyFormalId(ApplyFormalSub applyFormalSub) {
        return getDataTable(applyFormalService.selectApplyFormalSubByApplyFormalId(applyFormalSub));
    }

    /**
     * 新增保存员工转正申请主
     */
    @Log(title = "员工转正申请主-新增", businessType = BusinessType.INSERT)
    @PostMapping("/apply-formal-add")
    @ResponseBody
    public AjaxResult applyFormalAdd(ApplyFormal applyFormal)
    {
        return toAjax(applyFormalService.insertApplyFormal(applyFormal));
    }

    /**
     * 新增子项
     */
    @Log(title = "员工转正申请子项-新增", businessType = BusinessType.INSERT)
    @PostMapping("/apply-formal-sub-add")
    @ResponseBody
    public AjaxResult insertApplyFormalSub(@RequestBody ApplyFormalSub applyFormalSub) {
        //判断主表ID不能为空
        if(applyFormalSub.getApplyFormalId()==null){
            return AjaxResult.error("主表ID不能为空");
        }
        return toAjax(applyFormalService.insertApplyFormalSub(applyFormalSub));
    }

    /**
     * 修改保存员工转正申请主
     */
    @Log(title = "员工转正申请主-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/apply-formal-edit")
    @ResponseBody
    public AjaxResult applyFormalEdit(ApplyFormal applyFormal)
    {
        return toAjax(applyFormalService.updateApplyFormal(applyFormal));
    }

    /**
     * 删除员工转正申请主
     */
    @Log(title = "员工转正申请主-删除", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(ApplyFormal applyFormal)
    {
        return toAjax(applyFormalService.deleteApplyFormalByIds(applyFormal));
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Log(title = "员工转正申请子项-提交", businessType = BusinessType.UPDATE)
    @PostMapping("/applyFormalSub-submit")
    @ResponseBody
    public AjaxResult applyFormalSubSubmit(ApplyFormalSub applyFormalSub){
        try {
            //System.out.println(1/0);
            // 更新流程信息
            applyFormalService.applyFormalSubSubmit(applyFormalSub);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3或 4,通过主ID
    @Log(title = "员工转正申请子项-处理", businessType = BusinessType.UPDATE)
    @PostMapping("/applyFormalSub-handle")
    @ResponseBody
    public AjaxResult applyFormalSubHandle(ApplyFormalSub applyFormalSub) {
        try {
            // 更新流程信息
            applyFormalService.applyFormalSubHandle(applyFormalSub);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("处理失败：" + e.getMessage());
        }
    }

    //子表结束按钮，主表状态改成1
    @Log(title = "员工转正申请结束子表流程", businessType = BusinessType.UPDATE)
    @PostMapping("/applyFormalSub-end")
    @ResponseBody
    public AjaxResult applyFormalFinish(ApplyFormal applyFormal) {
        return toAjax(applyFormalService.applyFormalFinish(applyFormal));
    }
}
