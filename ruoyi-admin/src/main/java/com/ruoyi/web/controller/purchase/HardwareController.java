package com.ruoyi.web.controller.purchase;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.hardware.service.HardwareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/purchase/hardware")
public class HardwareController extends BaseController { //采买设备    硬件
    private String prefix = "purchase/hardware";

    @Autowired
    HardwareService hardwareService;

    //主页面跳转页面
    @GetMapping("/initLoads")
    public String initLoads() {
        return prefix + "/hardware";
    }
    //添加按钮跳转页面
    @GetMapping("/add")
    public String add() {
        return prefix + "/hardware-add";
    }
    //主页面修改功能跳转页面
    @GetMapping("/edit/{id}")
    public String edit(Hardware hardware, ModelMap mmap) {
        mmap.put("hardware", hardwareService.selectHardwareList(hardware).get(Constants.ZERO));
        return prefix + "/hardware-edit";
    }
    //主页面详情功能跳转页面
    @GetMapping("/detail/{id}")
    public String detail(Hardware hardware, ModelMap mmap) {
        mmap.put("hardware", hardwareService.selectHardwareList(hardware).get(Constants.ZERO));
        return prefix + "/hardware-detail";
    }
    //详情页面提交功能跳转页面
    @GetMapping("/jump-submit/{id}")
    public String jumpSubmit(HardwareChild hardwareChild, ModelMap mmap) {
        mmap.put("hardwareChild", hardwareService.selectChildList(hardwareChild).get(Constants.ZERO));
        return prefix + "/hardware-submit";
    }
    //详情页面处理功能跳转页面
    @GetMapping("/jump-handle/{id}")
    public String jumpHandle(@PathVariable("id") Long id, ModelMap mmap) {
        HardwareChild hardwareChild = hardwareService.selectChildById(id);
        String loginUser = getLoginName();
        if (hardwareChild != null && loginUser.equals(hardwareChild.getHandleBy())){
            mmap.put("hardwareChild", hardwareChild);
            return prefix + "/hardware-handle";
        }else {
            return "error/unauth";
        }
    }


    //*******************************************


    //主页面查询功能
    //@RequiresPermissions("system:out:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Hardware hardware) {
        startPage();
        List<Hardware> hardwares = hardwareService.selectHardwareList(hardware);
        return getDataTable(hardwares);
    }

    //添加硬件
    //@RequiresPermissions("system:out:add")
    @Log(title = "添加硬件", businessType = BusinessType.INSERT)
    @PostMapping("/insertHardware")
    @ResponseBody
    public AjaxResult insertHardware(Hardware hardware) {
        return toAjax(hardwareService.insertHardware(hardware));
    }

    //修改硬件
    //@RequiresPermissions("system:out:edit")
    @Log(title = "修改硬件", businessType = BusinessType.UPDATE)
    @PostMapping("/updateHardware")
    @ResponseBody
    public AjaxResult updateHardware(Hardware hardware) {
        return toAjax(hardwareService.updateHardware(hardware));
    }

    //删除硬件
    //@RequiresPermissions("system:out:remove")
    @Log(title = "删除硬件", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Hardware hardware) {
        return toAjax(hardwareService.deleteHardwareByIds(hardware));
    }

    //子表结束按钮，主表状态改成1
    @Log(title = "结束硬件子表流程", businessType = BusinessType.UPDATE)
    @PostMapping("/hardwareFinish")
    @ResponseBody
    public AjaxResult hardwareFinish(Hardware hardware) {
        return toAjax(hardwareService.hardwareFinish(hardware));
    }



    /*************************  子表  *************************/

    //添加子表数据
    //@RequiresPermissions("system:out:add")
    @Log(title = "新增硬件子表数据", businessType = BusinessType.INSERT)
    @PostMapping("/insertHardwareChild")
    @ResponseBody
    public AjaxResult insertHardwareChild(@RequestBody HardwareChild hardwareChild) {
        //判断主表ID不能为空
        if(hardwareChild.getHardwareId()==null){
            return AjaxResult.error("主表ID不能为空");
        }
        return toAjax(hardwareService.insertHardwareChild(hardwareChild));
    }

    //通过主表的id查询子表数据
    //@RequiresPermissions("system:out:list")
    @PostMapping("/findChildByHardwareId")
    @ResponseBody
    public TableDataInfo findChildByHardwareId(HardwareChild hardwareChild) {
        return getDataTable(hardwareService.findChildByHardwareId(hardwareChild));
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Log(title = "硬件子项提交功能", businessType = BusinessType.UPDATE)
    @PostMapping("/commit")
    @ResponseBody
    public AjaxResult commit(HardwareChild hardwareChild){
        try {
            //System.out.println(1/0);
            // 更新流程信息
            hardwareService.commit(hardwareChild);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3或 4,通过主ID
    @Log(title = "硬件子项处理功能", businessType = BusinessType.UPDATE)
    @PostMapping("/handle")
    @ResponseBody
    public AjaxResult handle(HardwareChild hardwareChild) {
        try {
            // 更新流程信息
            hardwareService.handle(hardwareChild);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("处理失败：" + e.getMessage());
        }
    }

    //子项查询中的文件下载
    @GetMapping("/hardware-files/{id}")
    @ResponseBody
    public AjaxResult hardwareFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = hardwareService.hardwareFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }
}
