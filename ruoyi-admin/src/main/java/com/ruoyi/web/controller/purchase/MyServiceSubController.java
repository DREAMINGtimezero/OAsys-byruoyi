package com.ruoyi.web.controller.purchase;

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
import com.ruoyi.myservice.domain.MyServiceSub;
import com.ruoyi.myservice.service.MyServiceSubService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 服务子表Controller
 *
 * @author YJ
 * @date 2025-07-09
 */
@Controller
@RequestMapping("/purchase/service/sub")
public class MyServiceSubController extends BaseController
{
    private final String prefix = "purchase/service/sub";

    @Autowired
    private MyServiceSubService subService;

    @GetMapping("/initLoads")
    public String initLoads()
    {
        return prefix + "/service-detail";
    }

    /**
     * 查询服务子表列表
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam Long relativeId)
    {

//        startPage();
        MyServiceSub condition = new MyServiceSub();
        condition.setServiceId(relativeId);
        condition.setDeleteFlag(0);
        List<MyServiceSub> list = subService.selectMyServiceSubList(condition);
        return getDataTable(list);
    }

    /**
     * 导出服务子表列表
     */
    @Log(title = "服务子表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MyServiceSub myServiceSub)
    {
        List<MyServiceSub> list = subService.selectMyServiceSubList(myServiceSub);
        ExcelUtil<MyServiceSub> util = new ExcelUtil<MyServiceSub>(MyServiceSub.class);
        return util.exportExcel(list, "服务子表数据");
    }

    /**
     * 修改服务子表
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mp)
    {
        MyServiceSub myServiceSub = subService.selectMyServiceSubById(id);
        mp.put("myServiceSub", myServiceSub);
        return prefix + "/edit";
    }

    /**
     * 修改保存服务子表
     */
    @Log(title = "服务子表", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MyServiceSub myServiceSub)
    {
        return toAjax(subService.updateMyServiceSub(myServiceSub));
    }

    /**
     * 删除服务子表
     */
    @Log(title = "服务子表", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(subService.deleteMyServiceSubByIds(ids));
    }

    /**
     * 新增服务流程(子表数据),在子表中添加一条与主项关联的子项
     * @param relativeId 关联的主表Id
     * @return 添加结果
     */
    @Log(title = "添加服务流程", businessType = BusinessType.INSERT)
    @GetMapping("/add/{id}")
    @ResponseBody
    public AjaxResult add(@PathVariable("id") Long relativeId)
    {
        MyServiceSub result = subService.add(relativeId);
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
        MyServiceSub sub = subService.selectMyServiceSubById(id);
        mp.put("myServiceSub",sub);
        return prefix + "/service-sub-creator-edit";
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
        MyServiceSub sub = subService.selectMyServiceSubById(id);
        mp.put("myServiceSub",sub);
        return prefix + "/service-sub-handler-edit";
    }

    /**
     * 发起新流程,将对应的子表数据置为待处理
     * @param sub 需要更新的子表数据
     * @return 更新结果
     */
    @Log(title="发起服务流程",businessType = BusinessType.UPDATE)
    @PostMapping("/submit")
    @ResponseBody
    public AjaxResult submitProcess(MyServiceSub sub)
    {
        return toAjax(subService.submitProcess(sub));
    }
    /**
     * 拒绝流程,将对应子表数据的状态字段置为拒绝
     * @param sub 需要更新的子表数据
     * @return 更新结果
     */
    @Log(title="拒绝服务流程",businessType = BusinessType.UPDATE)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult rejectProcess(MyServiceSub sub)
    {
        sub.setStatus(Constants.SubStatus.REJECTED);
        return toAjax(subService.handleProcess(sub));
    }

    /**
     * 通过流程,将对应子表数据状态字段置为通过
     * @param sub 需要更新的子表数据
     * @return 更新结果
     */
    @Log(title="通过服务流程",businessType = BusinessType.UPDATE)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult passProcess(MyServiceSub sub)
    {
        sub.setStatus(Constants.SubStatus.PASSED);
        return toAjax(subService.handleProcess(sub));
    }
}
