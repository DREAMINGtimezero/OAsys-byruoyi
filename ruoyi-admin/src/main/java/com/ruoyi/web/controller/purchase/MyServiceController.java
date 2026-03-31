package com.ruoyi.web.controller.purchase;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.myservice.domain.MyService;
import com.ruoyi.myservice.service.MyServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务Controller
 * @author YJ
 * @date 2025-07-09
 */
@Controller
@RequestMapping("/purchase/service")
public class MyServiceController extends BaseController
{
    private final String prefix = "purchase/service";

    @Autowired
    private MyServiceService mainService;

    /**
     * 获取服务主页面
     * @param statusList 状态列表,从系统首页跳转到服务页面时,会带一个statusList参数过来,用于查询指定状态的服务
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/initLoads")
    public String initLoads(@RequestParam(name="statusList",required = false) String statusList,ModelMap mp) {
        mp.put("statusList", statusList);
        return prefix + "/service";
    }

    /**
     * 查询服务列表
     * @return 服务主项数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(MyService myService)
    {
        startPage();
        myService.setDeleteFlag(0);
        if(!StringUtils.isEmpty(myService.getStatus())) {
            myService.setStatusList(Convert.toIntArray(myService.getStatus()));
        }
        List<MyService> list = mainService.selectMyServiceList(myService);
        return getDataTable(list);
    }

    /**
     * 导出服务列表
     */
    @Log(title = "服务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(MyService myService)
    {
        List<MyService> list = mainService.selectMyServiceList(myService);
        ExcelUtil<MyService> util = new ExcelUtil<MyService>(MyService.class);
        return util.exportExcel(list, "服务数据");
    }

    /**
     * 获取新增服务页面
     * @return 跳转页面
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/service-add";
    }

    /**
     * 新增保存服务
     */
    @Log(title = "服务", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(MyService myService)
    {
        return toAjax(mainService.insertMyService(myService));
    }

    /**
     * 获取服务主项修改页面
     * @param id 服务主项的主键id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mp)
    {
        MyService myService = mainService.selectMyServiceById(id);
        if(Constants.MainStatus.UNSUBMIT.equals(myService.getStatus()))
        {
            mp.put("myService", myService);
            return prefix + "/service-edit";
        }
        return "error/unauth";
    }

    /**
     * 修改保存服务
     * @param myService 需要更新的服务主项数据
     */
    @Log(title = "服务", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(MyService myService)
    {
        return toAjax(mainService.updateMyService(myService));
    }

    /**
     * 删除服务
     * @param ids 需要删除的服务主项id列表
     */
    @Log(title = "服务", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(mainService.deleteMyServiceByIds(ids));
    }

    /**
     * 获取服务详情页面
     * @param id 服务主项id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id,ModelMap mp)
    {
        MyService myService = mainService.selectMyServiceById(id);
        mp.put("myService",myService);
        return prefix+"/sub/service-detail";
    }

    /**
     * 结束服务流程
     * @param id 需要更新状态的服务主项id
     * @return 结果
     */
    @Log(title="结束服务流程",businessType = BusinessType.UPDATE)
    @PutMapping("/over/{id}")
    @ResponseBody
    public AjaxResult overProcess(@PathVariable("id") Long id)
    {
        //校验
        if (!mainService.overAble(id)) {
            return AjaxResult.error("还有进行中的流程或还未经过总经理处理");
        }
        MyService conditions = new MyService();
        conditions.setId(id);
        conditions.setStatus(Constants.MainStatus.COMPLETE);
        return toAjax(mainService.updateMyService(conditions));
    }

    /**
     * 根据id获取服务主项的信息
     * @param id 服务主项id
     * @return 服务主项信息
     */
    @GetMapping("/service/{id}")
    @ResponseBody
    public AjaxResult  myService(@PathVariable("id") Long id)
    {
        return AjaxResult.success(mainService.selectMyServiceById(id));
    }
}
