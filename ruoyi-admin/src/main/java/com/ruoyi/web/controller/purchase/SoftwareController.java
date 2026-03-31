package com.ruoyi.web.controller.purchase;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.software.domain.Software;
import com.ruoyi.software.domain.SoftwareProcess;
import com.ruoyi.software.service.SoftwareProcessService;
import com.ruoyi.software.service.SoftwareService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;

@Controller
@RequestMapping("/purchase/software")
public class SoftwareController extends BaseController {
    private String prefix = "purchase/software";

    @Autowired
    private SoftwareService softwareService;

    @Autowired
    private SoftwareProcessService softwareProcessService;

    /**
     * 主页面
     */
    @GetMapping("/initLoads")//主页面跳转页面
    public String initLoads(@RequestParam(name="status",required = false) Integer status,ModelMap mp) {
        mp.put("status", status);
        return prefix + "/software";
    }

    /**
     * 添加页面
     */
    @GetMapping("/add")//添加按钮跳转页面
    public String add() {
        return prefix + "/software-add";
    }

    /**
     * 修改页面
     */
//    @RequiresPermissions("system:software:edit")
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("software", softwareService.selectSoftwareById(id));
        return prefix + "/software-edit";
    }

    /**
     * 软件详情页
     **/
    @GetMapping("/details/{id}")
    public String details(Software software, ModelMap mmap) {
        mmap.put("software", softwareService.selectSoftwareList(software).get(Constants.ZERO));
        return prefix + "/software-details";
    }

    /**
     * @Description 软件流程-发起页面
     * @Author LTY
     **/
    @GetMapping("/process-initiate/{id}")
    public String processInitiate(@PathVariable("id") Long id, ModelMap mmap) {
        SoftwareProcess softwareProcess = softwareProcessService.selectSoftwareProcessById(id);
        String loginUser = getLoginName();
        if (softwareProcess != null && loginUser.equals(softwareProcess.getCreateBy())) {
            mmap.put("softwareProcess", softwareProcess);
            return prefix + "/software-process-initiate";
        }else {
            return "error/unauth";
        }
    }

    /**
     * @Description 软件流程-处理页面
     * @Author LTY
     **/
    @GetMapping("/process-handle/{id}")
    public String processHandle(@PathVariable("id") Long id, ModelMap mmap) {
        SoftwareProcess softwareProcess = softwareProcessService.selectSoftwareProcessById(id);
        String loginUser = getLoginName();
        if (softwareProcess != null && loginUser.equals(softwareProcess.getHandleBy())) {
            mmap.put("softwareProcess", softwareProcess);
            return prefix + "/software-process-handle";
        }else {
            return "error/unauth";
        }
    }

    /**
     * 查询软件列表
     */
//    @RequiresPermissions("system:software:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Software software) {
        startPage();
        List<Software> list = softwareService.selectSoftwareList(software);
        return getDataTable(list);
    }

    /**
     * 新增软件
     */
//    @RequiresPermissions("system:software:add")
    @Log(title = "软件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Software software) {
        return toAjax(softwareService.insertSoftware(software));
    }

    /**
     * 修改软件
     */
//    @RequiresPermissions("system:software:edit")
    @Log(title = "软件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Software software) {
        return toAjax(softwareService.updateSoftware(software));
    }

    /**
     * 删除软件
     */
//    @RequiresPermissions("system:software:remove")
    @Log(title = "软件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(Software software) {
        return toAjax(softwareService.deleteSoftwareByIds(software));
    }

    /**
     * @Description  软件流程-列表查询
     * @Author LTY
     * @Date 2025/6/26
     **/
    @PostMapping("/process-list")
    @ResponseBody
    public TableDataInfo processList(SoftwareProcess softwareProcess) {
//        startPage();
        List<SoftwareProcess> list = softwareProcessService.selectSoftwareProcessList(softwareProcess);
        return getDataTable(list);
    }

    /**
     * @Description  流程-新增
     * @Author LTY
     * @Date
     **/
    @Log(title = "软件流程-新增", businessType = BusinessType.INSERT)
    @PostMapping("/process-add")
    @ResponseBody
    public AjaxResult processAdd(@RequestBody SoftwareProcess softwareProcess) {
        // 确保softwareId被正确设置
        if (softwareProcess.getSoftwareId() == null) {
            return AjaxResult.error("软件ID不能为空");
        }
        return toAjax(softwareProcessService.insertSoftwareProcess(softwareProcess));
    }

    /**
     * @Description  流程-结束
     * @Author LTY
     **/
    @Log(title = "流程-结束", businessType = BusinessType.UPDATE)
    @PostMapping("/process-finish")
    @ResponseBody
    public AjaxResult processFinish(Software software) {
        return toAjax(softwareService.finishSoftware(software));
    }

    /**
     * @Description  流程-发起
     * @Author LTY
     **/
    @Log(title = "流程-发起", businessType = BusinessType.UPDATE)
    @PostMapping("/process-initiate")
    @ResponseBody
    public AjaxResult processInitiate(SoftwareProcess softwareProcess){
        try {
            // 更新流程信息
            softwareProcessService.initiateSoftwareProcess(softwareProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    /**
     * @Description  流程-处理
     * @Author LTY
     **/
    @Log(title = "流程-处理", businessType = BusinessType.UPDATE)
    @PostMapping("/process-handle")
    @ResponseBody
    public AjaxResult processHandle(SoftwareProcess softwareProcess){
        try {
            // 更新流程信息
            softwareProcessService.handleSoftwareProcess(softwareProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("处理失败：" + e.getMessage());
        }
    }

    /**
     * @Description  文件下载路径获取
     * @Author lty
     **/
    @GetMapping("/process-files/{id}")
    @ResponseBody
    public AjaxResult processFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = softwareProcessService.getProcessFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }
}
