package com.ruoyi.web.controller.staffservice;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.work.domain.Work;
import com.ruoyi.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description  考勤及办公申请模板controller
 * @Author wym
 * @Date 2025/8/7 11:44
 **/
@Controller
@RequestMapping("/staff-service/work")
public class WorkController extends BaseController {
    private String prefix = "staff-service/work";

    @Autowired
    WorkService workService;

    //主页面跳转页面
    @GetMapping("/initLoads")
    public String initLoads(ModelMap mmap) {
        mmap.put("userName", getSysUser().getUserName());//前台得到当前登录用户
        return prefix + "/work";
    }

    //新增页面跳转页面
    @GetMapping("/add")
    public String add() {
        return prefix + "/work-add";
    }

    /**
     * 跳转至修改页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Work work = workService.selectWorkById(id);
        mmap.put("work", work);
        return prefix + "/work-edit";
    }

    //**************************************************************************


    //主页面查询功能
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Work work) {
        startPage();
        List<Work> works = workService.selectWorkList(work);
        return getDataTable(works);
    }

    /**
     * 新增考勤
     */
    @Log(title = "考勤-新增", businessType = BusinessType.INSERT)
    @PostMapping("/work-add")
    @ResponseBody
    public AjaxResult insertWork(Work work)
    {
        return toAjax(workService.insertWork(work));
    }

    /**
     * 修改保存员工转正申请主
     */
    @Log(title = "考勤-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/work-edit")
    @ResponseBody
    public AjaxResult updateWork(Work work) {
        return toAjax(workService.updateWork(work));
    }

    /**
     * 删除员工转正申请主
     */
    @Log(title = "考勤-删除", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult deleteWorkByIds(Work work) {
        return toAjax(workService.deleteWorkByIds(work));
    }
}
