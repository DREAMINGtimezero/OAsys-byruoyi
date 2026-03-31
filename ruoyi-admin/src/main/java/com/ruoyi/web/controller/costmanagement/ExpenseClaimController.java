package com.ruoyi.web.controller.costmanagement;

import java.util.List;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import com.ruoyi.costmanagement.service.ExpenseClaimSubService;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.costmanagement.domain.ExpenseClaim;
import com.ruoyi.costmanagement.service.ExpenseClaimService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 报销单主表Controller
 *
 * @author YJ
 * @date 2025-07-31
 */
@Controller
@RequestMapping("/cost-management/expense-claim")
public class ExpenseClaimController extends BaseController
{
    private final String prefix = "cost-management/expense-claim";

    @Autowired
    private ExpenseClaimService mainService;

    @Autowired
    private ExpenseClaimSubService subService;
    @GetMapping("initLoads")
    public String initLoads(@RequestParam(name="status",required = false) Long status,ModelMap mp)
    {
        mp.put("status", status);
        return prefix + "/expense-claim";
    }

    /**
     * 查询报销单主表列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ExpenseClaim expenseClaim)
    {
        startPage();
        List<ExpenseClaim> list = mainService.selectExpenseClaimList(expenseClaim);
        return getDataTable(list);
    }

    /**
     * 导出报销单主表列表
     */
    @Log(title = "报销单主表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ExpenseClaim expenseClaim)
    {
        List<ExpenseClaim> list = mainService.selectExpenseClaimList(expenseClaim);
        ExcelUtil<ExpenseClaim> util = new ExcelUtil<ExpenseClaim>(ExpenseClaim.class);
        return util.exportExcel(list, "报销单主表数据");
    }

    /**
     * 新增报销单主表
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/expense-claim-add";
    }

    /**
     * 新增保存报销单主表
     */
    @Log(title = "报销单主表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(ExpenseClaim expenseClaim)
    {
        return toAjax(mainService.addExpenseClaim(expenseClaim));
    }

    /**
     * 修改报销单主表
     */
    
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        //检查是否允许修改
        if(!mainService.editAble(id))
        {
            return "error/unauth";
        }
        ExpenseClaim expenseClaim = mainService.selectExpenseClaimById(id);
        mmap.put("expenseClaim", expenseClaim);
        return prefix + "/expense-claim-edit";
    }

    /**
     * 修改保存报销单主表
     */
    
    @Log(title = "报销单主表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(ExpenseClaim expenseClaim)
    {
        return toAjax(mainService.updateExpenseClaim(expenseClaim));
    }

    /**
     * 删除报销单主表
     */
    
    @Log(title = "报销单主表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(mainService.deleteExpenseClaim(ids));
    }

    @GetMapping( "/detail/{id}")
    public String detail(@PathVariable("id")Long id,ModelMap mp)
    {
        ExpenseClaim expenseClaim = mainService.selectExpenseClaimById(id);
        mp.put("expenseClaim",expenseClaim);
        return prefix+"/sub/expense-claim-detail";
    }

    /**
     * 结束财务报销流程
     * @param id 主项主键id
     * @return 结果
     */
    @Log(title = "结束财务报销流程", businessType = BusinessType.UPDATE)
    @PutMapping("/over/{id}")
    @ResponseBody
    public AjaxResult overProcess(@PathVariable("id") Long id) {
        //校验
        if (!mainService.overAble(id)) {
            return AjaxResult.error("还有进行中的流程");
        }
        ExpenseClaim mainData = new ExpenseClaim();
        mainData.setId(id);
        mainData.setStatus(Integer.valueOf(Constants.MainStatus.COMPLETE));
        return toAjax(mainService.updateExpenseClaim(mainData));
    }
}
