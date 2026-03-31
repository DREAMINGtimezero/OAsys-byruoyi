package com.ruoyi.web.controller.contract;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.contract.service.ContractService;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description  合同统计controller
 * @Author lty
 * @Date 2025/6/26
 **/
@Controller
@RequestMapping("/contract/contract")
public class ContractController extends BaseController {

    private final String prefix = "contract/contract";

    @Autowired
    private ContractService contractService;

    /**
     * @Description  页面初始化
     * @Author lty
     * @Date 2025/6/25
     **/
    @GetMapping("/initLoads")//主页面跳转页面
    public String initLoads(@RequestParam(name="status",required = false) Integer status,ModelMap mp) {
        mp.put("status", status);
        return prefix + "/contract";
    }

    /**
     * @Description  跳转-新增页面
     * @Author lty
     * @Date 2025/6/25
     **/
    @GetMapping("/add")//添加按钮跳转页面
    public String add() {
        return prefix + "/contract-add";
    }

    /**
     * @Description  跳转-修改页面
     * @Author lty
     * @Date 2025/6/25
     **/
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long contractId, ModelMap mmap) {
        mmap.put("contract", contractService.selectContractById(contractId));
        return prefix + "/contract-edit";
    }

    /**
     * @Description  跳转-合同详情页
     * @Author lty
     * @Date 2025/6/27
     **/
    @GetMapping("/details/{id}")
    public String details(Contract contract, ModelMap mmap) {
        mmap.put("contract", contractService.selectContractList(contract).get(Constants.ZERO));
        return prefix + "/contract-details";
    }

    /**
     * @Description  跳转至流程-发起页面
     * @Author lty
     * @Date 2025/6/26
     **/
    @GetMapping("/process-initiate/{id}")
    public String processInitiate(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        ContractProcess contractProcess = (ContractProcess) contractService.selectContractProcessById(id);

        // 获取当前登录用户
        String loginUser = getLoginName();

        // 检查当前用户是否为合同发起人
        if (contractProcess != null && loginUser.equals(contractProcess.getCreateBy())) {
            mmap.put("contractProcess", contractProcess);
            return prefix + "/contract-process-initiate";
        } else {
            // 非发起人，返回错误信息
            return "error/unauth";
        }
    }

    /**
     * @Description  跳转至流程-处理页面
     * @Author lty
     **/
    @GetMapping("/process-handle/{id}")
    public String processHandle(@PathVariable("id") Long id, ModelMap mmap) {
        // 获取流程信息
        ContractProcess contractProcess = (ContractProcess) contractService.selectContractProcessById(id);

        // 获取当前登录用户
        String loginUser = getLoginName();

        // 检查当前用户是否为指定处理人
        if (contractProcess != null && loginUser.equals(contractProcess.getHandleBy())) {
            mmap.put("contractProcess", contractProcess);
            return prefix + "/contract-process-handle";
        } else {
            // 非指定处理人，返回错误信息
            return "error/unauth";
        }
    }

    /**
     * @Description  合同-新增
     * @Author lty
     * @Date 2025/6/25
     **/
    @Log(title = "合同管理-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(@Validated Contract contract) {
        return toAjax(contractService.insertContract(contract));
    }

    /**
     * @Description  合同-修改
     * @Author lty
     * @Date 2025/6/26
     **/
    @Log(title = "合同管理-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult edit(Contract contract) {
        return toAjax(contractService.updateContract(contract));
    }

    /**
     * @Description  合同列表-查询
     * @Author lty
     * @Date 2025/6/25
     **/
//    @RequiresPermissions("system:contract:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Contract contract) {
        startPage();
        List<Contract> list = contractService.selectContractList(contract);
        return getDataTable(list);
    }

    /**
     * @Description  合同-删除
     * @Author lty
     * @Date 2025/6/26
     **/
    @Log(title = "合同管理-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(Contract contract) {
        return toAjax(contractService.deleteContractByIds(contract));
    }

    /**
     * @Description  合同流程-列表查询
     * @Author LTY
     * @Date 2025/6/26
     **/
    @PostMapping("/process-list")
    @ResponseBody
    public TableDataInfo processList(ContractProcess ContractProcess) {
//        startPage();
        List<ContractProcess> list = contractService.selectContractProcessList(ContractProcess);
        return getDataTable(list);
    }

    /**
     * @Description  合同流程-新增
     * @Author LTY
     * @Date
     **/
    @Log(title = "合同流程-新增", businessType = BusinessType.INSERT)
    @PostMapping("/process-add")
    @ResponseBody
    public AjaxResult processAdd(@RequestBody ContractProcess contractProcess) {

        // 打印日志检查接收到的数据
//        logger.info("Received contractProcess: {}", contractProcess);
        // 确保contractId被正确设置
        if (contractProcess.getContractId() == null) {
            return AjaxResult.error("合同ID不能为空");
        }
        return toAjax(contractService.insertContractProcess(contractProcess));
    }

    /**
     * @Description  合同流程-结束
     * @Author LTY
     **/
    @Log(title = "合同流程-结束", businessType = BusinessType.UPDATE)
    @PostMapping("/process-finish")
    @ResponseBody
    public AjaxResult processFinish(Contract contract) {
        return toAjax(contractService.finishContract(contract));
    }

    /**
     * @Description  合同流程-发起
     * @Author LTY
     **/
    @Log(title = "合同流程-发起", businessType = BusinessType.UPDATE)
    @PostMapping("/process-initiate")
    @ResponseBody
    public AjaxResult processInitiate(ContractProcess contractProcess){
        try {
            // 更新流程信息
            contractService.initiateContractProcess(contractProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    /**
     * @Description  合同流程-处理
     * @Author LTY
     **/
    @Log(title = "合同流程-处理", businessType = BusinessType.UPDATE)
    @PostMapping("/process-handle")
    @ResponseBody
    public AjaxResult processHandle(ContractProcess contractProcess){
        try {
            // 更新流程信息
            contractService.handleContractProcess(contractProcess);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
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
            String files = contractService.getProcessFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }


}
