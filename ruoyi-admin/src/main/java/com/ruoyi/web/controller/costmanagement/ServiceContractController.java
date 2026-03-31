package com.ruoyi.web.controller.costmanagement;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.servicecontract.domain.ServiceContract;
import com.ruoyi.servicecontract.domain.ServiceContractProcess;
import com.ruoyi.servicecontract.service.ServiceContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/cost-management/servicecontract")
public class ServiceContractController extends BaseController{
    private String prefix = "cost-management/servicecontract";

    @Autowired
    private ServiceContractService ServiceContractService;
    /**
     * 页面初始化

     */
    @GetMapping("/initLoads")//主页面跳转页面
    public String initLoads(@RequestParam(name="status",required = false) Integer status, ModelMap mp) {
        mp.put("status", status);
        return prefix + "/servicecontract";
    }
    /**
     * 新增页面
     * @return 新增页面
     */
    @GetMapping("/add")//添加按钮跳转页面
    public String add(ModelMap mp) {
        return prefix + "/servicecontract-add";
    }
    /**
     * 修改页面
     * @return 修改页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        if(!ServiceContractService.editAble(id)){
            return "error/unauth";
        }
        ServiceContract serviceContract = ServiceContractService.selectServiceContractById(id);
        mmap.put("servicecontract", serviceContract);
        return prefix + "/servicecontract-edit";
    }
    /**
     * 合同详情
     * @return 合同详情
     */
    @GetMapping("/details/{id}")
    public String details(ServiceContract serviceContract, ModelMap mmap) {
       ;
        mmap.put("servicecontract", ServiceContractService.selectServiceContractList(serviceContract).get(Constants.ZERO));
        return prefix + "/servicecontract-details";
    }

    /**
     * 发起页面
     * @return 发起页面
     */
    @Log(title = "服务合同流程-发起", businessType = BusinessType.UPDATE)
    @GetMapping("/process-service-initiate/{id}")
    public String processInitiate(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        ServiceContractProcess servicecontractProcess = (ServiceContractProcess) ServiceContractService.selectServiceContractProcessById(id);

        // 获取当前登录用户
        String loginUser = getLoginName();

        // 检查当前用户是否为服务合同发起人
        if (servicecontractProcess != null && loginUser.equals(servicecontractProcess.getCreateBy())) {
            mmap.put("servicecontractProcess", servicecontractProcess);
            return prefix + "/servicecontract-process-initiate";
        } else {
            // 非发起人，返回错误信息
            return "error/unauth";
        }
    }
    /**
     * 处理页面
     * @return 处理页面
     */
    @Log(title = "服务合同流程-处理", businessType = BusinessType.UPDATE)
    @GetMapping("/process-service-handle/{id}")
    public String processHandle(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        ServiceContractProcess servicecontractProcess = (ServiceContractProcess) ServiceContractService.selectServiceContractProcessById(id);

        // 获取当前登录用户
        String loginUser = getLoginName();

        // 检查当前用户是否为指定处理人
        if (servicecontractProcess != null && loginUser.equals(servicecontractProcess.getHandleBy())) {
            mmap.put("servicecontractProcess", servicecontractProcess);
            return prefix + "/servicecontract-process-handle";
        } else {
            // 非指定处理人，返回错误信息
            return "error/unauth";
        }
    }
    /**
     * 服务合同新增
     * @return 新增
     */
    @Log(title = "服务合同-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Validated ServiceContract serviceContract) {
        return toAjax(ServiceContractService.insertServiceContract(serviceContract));
    }

    /**
     * 服务合同修改
     * @return 修改
     */
    @Log(title = "服务合同-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(ServiceContract serviceContract) {
        return toAjax(ServiceContractService.updateServiceContract(serviceContract));
    }
    /**
     * 查询服务合同列表
     * @return 列表数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ServiceContract serviceContract) {
        startPage();
        List<ServiceContract> list = ServiceContractService.selectServiceContractList(serviceContract);
        return getDataTable(list);
    }
    /**
     * 删除服务合同
     * @return 删除结果
     */
    @Log(title = "服务合同-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(ServiceContractService.deleteServiceContractByIds(ids));
    }
    /**
     * 列表查询
     * @return 列表数据
     */
    @PostMapping("/process-service-list")
    @ResponseBody
    public TableDataInfo processList(ServiceContractProcess serviceContractProcess) {
        //startPage();
        List<ServiceContractProcess> list = ServiceContractService.selectServiceContractProcessList(serviceContractProcess);
        return getDataTable(list);
    }
    /**
     * 服务合同流程新增
     * @return 新增
     */
    @Log(title = "服务合同流程-新增", businessType = BusinessType.INSERT)
    @PostMapping("/process-service-add")
    @ResponseBody
    public AjaxResult processAdd(@RequestBody ServiceContractProcess serviceContractProcess) {

        // 打印日志检查接收到的数据
//        logger.info("Received contractProcess: {}", contractProcess);
        // 确保contractId被正确设置
        if (serviceContractProcess.getServiceContractId() == null) {
           return AjaxResult.error("合同ID不能为空");
        }
        return toAjax(ServiceContractService.insertServiceContractProcess(serviceContractProcess));
    }
    /**
     * 结束
     * @return 结束
     */
    @Log(title = "服务合同流程-结束", businessType = BusinessType.UPDATE)
    @PostMapping("/process-service-finish")
    @ResponseBody
    public AjaxResult processFinish(ServiceContract serviceContract) {
        return toAjax(ServiceContractService.finishServiceContract(serviceContract));
    }
    /**
     * 发起
     * @return 发起
     */
    @Log(title = "服务合同流程-发起", businessType = BusinessType.UPDATE)
    @PostMapping("/process-service-initiate")
    @ResponseBody
    public AjaxResult processInitiate(ServiceContractProcess serviceContractProcess){
        try {
            // 更新流程信息
            ServiceContractService.initiateServiceContractProcess(serviceContractProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }
    /**
     * 处理
     * @return 处理
     */
    @Log(title = "服务合同流程-处理", businessType = BusinessType.UPDATE)
    @PostMapping("/process-service-handle")
    @ResponseBody
    public AjaxResult processHandle(ServiceContractProcess serviceContractProcess){
        try {
            // 更新流程信息
            ServiceContractService.handleServiceContractProcess(serviceContractProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    /**
     * 文件下载路径获取
     * @return 文件下载路径
     */
    @GetMapping("/process-service-files/{id}")
    @ResponseBody
    public AjaxResult processFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = ServiceContractService.getProcessFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }

}
