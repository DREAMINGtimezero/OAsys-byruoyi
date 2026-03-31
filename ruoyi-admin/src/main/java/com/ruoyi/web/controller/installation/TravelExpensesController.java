package com.ruoyi.web.controller.installation;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.installation.domain.Out;
import com.ruoyi.installation.domain.OutChild;
import com.ruoyi.installation.service.OutService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/installation/travel-expenses")
public class TravelExpensesController extends BaseController {//安装调试  差旅费
    private final String prefix = "installation/travel-expenses";

    @Autowired
    private OutService outService;

    //主页面查询功能页面
    @GetMapping("/initLoads")
    public String initLoads() { //主页面跳转页面
        return prefix+"/travel-expenses";
    }
    //主页面添加功能跳转页面
    @GetMapping("/add")
    public String add() { //添加按钮跳转页面
        return prefix+"/travel-expenses-add";
    }
    //主页面修改功能跳转页面
    @GetMapping("/edit/{id}")
    public String edit(Out out, ModelMap mmap) {
        mmap.put("out", outService.selectOutList(out).get(Constants.ZERO));
        return prefix + "/travel-expenses-edit";
    }
    //主页面详情跳转页面
    @GetMapping("/detail/{id}")
    public String detail(Out out, ModelMap mmap) {
        mmap.put("out", outService.selectOutList(out).get(Constants.ZERO));
        return prefix + "/travel-expenses-detail";
    }
    //详情页面提交功能跳转页面
    @GetMapping("/jump-submit/{id}")
    public String jumpSubmit(OutChild outChild, ModelMap mmap) {
        mmap.put("outChild", outService.selectChildList(outChild).get(Constants.ZERO));
        return prefix + "/travel-expenses-submit";
    }
    //详情页面处理功能跳转页面
    @GetMapping("/jump-handle/{id}")
    public String jumpHandle(@PathVariable("id") Long id, ModelMap mmap) {
        OutChild outChild = outService.selectChildById(id);
        String loginUser = getLoginName();

        if (outChild != null && loginUser.equals(outChild.getHandleBy())){
            mmap.put("outChild", outChild);
            return prefix + "/travel-expenses-handle";
        }else {
            return "error/unauth";
        }
    }
    /*public String jumpHandle(OutChild outChild, ModelMap mmap) {
        mmap.put("outChild", outService.selectChildList(outChild).get(Constants.ZERO));
        return prefix + "/travel-expenses-handle";
    }*/


    //**************************************

    //主页面查询功能
    //@RequiresPermissions("system:out:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Out out) {
        startPage();
        List<Out> list = outService.selectOutList(out);
        return getDataTable(list);
    }

    //添加差旅费用申请
    //@RequiresPermissions("system:out:add")
    @Log(title = "添加差旅费用申请", businessType = BusinessType.INSERT)
    @PostMapping("/insertOut")
    @ResponseBody
    public AjaxResult insertOut(Out out) {
        return toAjax(outService.insertOut(out));
    }

    //修改差旅费用申请
    //@RequiresPermissions("system:out:edit")
    @Log(title = "修改差旅费用申请", businessType = BusinessType.UPDATE)
    @PostMapping("/updateOut")
    @ResponseBody
    public AjaxResult updateOut(Out out) {
        return toAjax(outService.updateOut(out));
    }

    //删除差旅费用申请
    //@RequiresPermissions("system:out:remove")
    @Log(title = "删除差旅费用申请", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult deleteOutByIds(Out out) {
        return toAjax(outService.deleteOutByIds(out));
    }



    /***************************   子表   **************************/


    //子项主页面查询功能
    //@RequiresPermissions("system:out:list")
    @PostMapping("/findOutChildByOutId")
    @ResponseBody
    public TableDataInfo findOutChildByOutId(OutChild outChild) {
        return getDataTable(outService.findOutChildByOutId(outChild));
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @PostMapping("/commit")
    @Log(title = "提交差旅费用申请", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult commit(OutChild outChild){
        try {
            // 更新流程信息
            outService.commit(outChild);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3或 4,通过主ID
    @PostMapping("/handle")
    @Log(title = "处理差旅费用申请", businessType = BusinessType.UPDATE)
    @ResponseBody
    public AjaxResult handle(OutChild outChild) {
        try {
            // 更新流程信息
            outService.handle(outChild);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("处理失败：" + e.getMessage());
        }
    }

    //子项查询中的文件下载
    @GetMapping("/out-files/{id}")
    @ResponseBody
    public AjaxResult outFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = outService.outFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }
}