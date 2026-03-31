package com.ruoyi.web.controller.costmanagement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.ruoyi.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import com.ruoyi.costmanagement.service.ExpenseClaimSubService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报销单子Controller
 *
 * @author YJ
 * @date 2025-07-31
 */
@Controller
@RequestMapping("/cost-management/expense-claim/sub")
public class ExpenseClaimSubController extends BaseController
{
    private final String prefix = "cost-management/expense-claim/sub";

    @Autowired
    private ExpenseClaimSubService subService;

    
    @GetMapping("/initLoads")
    public String initLoads()
    {
        return prefix + "/expense-claim-sub";
    }

    /**
     * 查询报销单子列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam Long relativeId)
    {
//        startPage();
        ExpenseClaimSub conditions = new ExpenseClaimSub();
        conditions.setExpenseClaimId(relativeId);
        List<ExpenseClaimSub> list = subService.selectExpenseClaimSubList(conditions);
        return getDataTable(list);
    }

    /**
     * 导出报销单子列表
     */
    @Log(title = "报销单子", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExpenseClaimSub expenseClaimSub)
    {
        List<ExpenseClaimSub> list = subService.selectExpenseClaimSubList(expenseClaimSub);
        ExcelUtil<ExpenseClaimSub> util = new ExcelUtil<ExpenseClaimSub>(ExpenseClaimSub.class);
        return util.exportExcel(list, "报销单子数据");
    }

    /**
     * 新增保存报销单子
     */
    @Log(title = "报销单子", businessType = BusinessType.INSERT)
    @GetMapping("/add/{relativeId}")
    @ResponseBody
    public AjaxResult addSave(@PathVariable("relativeId") Long relativeId)
    {
        ExpenseClaimSub result = subService.addProcess(relativeId);
        Map<Integer,String> msg = new HashMap<>(4);
        msg.put(-1,"流程不存在");
        msg.put(Constants.SubStatus.PASSED,"主表已经结束,不能新增新流程");
        msg.put(Constants.SubStatus.REJECTED,"存在未提交/未处理的子表项");
        String message = msg.get(result.getStatus());
        if(Objects.nonNull(message))
        {
            return AjaxResult.error(message);
        }
        return AjaxResult.success(result);
    }

    /**
     * 获取发起流程页面
     * @param id 子表id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/submit/{id}")
    public String submit(@PathVariable("id")Long id,ModelMap mp)
    {
        if(!subService.submitPermission(id))
        {
            return "error/unauth";
        }
        ExpenseClaimSub sub = subService.selectExpenseClaimSubById(id);
        mp.put("expenseClaimSub",sub);
        return prefix + "/expense-claim-sub-submit";
    }

    /**
     * 获取处理页面
     * @param id 子表id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/handle/{id}")
    public String handle(@PathVariable("id")Long id,ModelMap mp)
    {
        if(!subService.handlePermission(id))
        {
            return "error/unauth";
        }
        ExpenseClaimSub sub = subService.selectExpenseClaimSubById(id);
        mp.put("expenseClaimSub",sub);
        return prefix + "/expense-claim-sub-handle";
    }
    /**
     * 删除报销单子
     */
    @PostMapping("/submit-process")
    @ResponseBody
    public AjaxResult submitProcess(ExpenseClaimSub expenseClaimSub)
    {
        return toAjax(subService.submitProcess(expenseClaimSub));
    }

    @PostMapping("/reject-process")
    @ResponseBody
    public AjaxResult rejectProcess(ExpenseClaimSub expenseClaimSub)
    {
        expenseClaimSub.setStatus(Constants.SubStatus.REJECTED);
        return toAjax(subService.handleProcess(expenseClaimSub));
    }
    @PostMapping("/pass-process")
    @ResponseBody
    public AjaxResult passProcess(ExpenseClaimSub expenseClaimSub)
    {
        expenseClaimSub.setStatus(Constants.SubStatus.PASSED);
        return toAjax(subService.handleProcess(expenseClaimSub));
    }
}
