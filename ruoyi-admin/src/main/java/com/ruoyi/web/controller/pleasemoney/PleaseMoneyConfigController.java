package com.ruoyi.web.controller.pleasemoney;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfig;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSub;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSun;
import com.ruoyi.pleasemoney.service.PleaseMoneyConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Description  请款配置controller
 * @Author wangshilin
 * @Date 2025/7/3 15:52
 **/
@Controller
@RequestMapping("/please-money/config")
public class PleaseMoneyConfigController extends BaseController {

    private String prefix = "please-money/config";

    @Autowired
    private PleaseMoneyConfigService pleaseMoneyConfigService;

    /**
     * @Description  页面初始化
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @GetMapping("/initLoads")
    public String initLoads() {
        return prefix + "/config";
    }

    /**
     * @Description  跳转至详情页面
     * @Author wangshilin
     * @Date 2025/7/4 13:22
     **/
    @GetMapping("/config-detail/{id}")
    public String configDetail(PleaseMoneyConfig pleaseMoneyConfig, ModelMap mmap) {
        mmap.put("pleaseMoneyConfig", pleaseMoneyConfigService.selectPleaseMoneyConfigList(pleaseMoneyConfig).get(Constants.ZERO));
        return prefix + "/config-detail";
    }

    /**
     * @Description  跳转至新增页面
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @GetMapping("/add")
    public String add() {
        return prefix + "/config-add";
    }

    /**
     * @Description  跳转至子项新增页面
     * @Author wangshilin
     * @Date 2025/7/4 15:42
     **/
    @GetMapping("/config-detail-add/{mId}")
    public String configDetailAdd(PleaseMoneyConfigSub pleaseMoneyConfigSub,ModelMap mmap) {
        mmap.put("pleaseMoneyConfigSub",pleaseMoneyConfigSub);
        return prefix + "/config-detail-add";
    }

    /**
     * @Description  跳转至修改页面
     * @Author wangshilin
     * @Date 2025/6/25 09:53
     **/
    @GetMapping("/edit/{id}")
    public String edit(PleaseMoneyConfig pleaseMoneyConfig, ModelMap mmap) {
        mmap.put("pleaseMoneyConfig", pleaseMoneyConfigService.selectPleaseMoneyConfigList(pleaseMoneyConfig).get(Constants.ZERO));
        return prefix + "/config-edit";
    }

    /**
     * @Description  跳转至子项修改页面
     * @Author wangshilin
     * @Date 2025/6/25 09:53
     **/
    @GetMapping("/config-detail-edit/{id}")
    public String configDetailEdit(PleaseMoneyConfigSub pleaseMoneyConfigSub, ModelMap mmap) {
        mmap.put("pleaseMoneyConfigSub", pleaseMoneyConfigService.selectPleaseMoneyConfigSubList(pleaseMoneyConfigSub).get(Constants.ZERO));
        return prefix + "/config-detail-edit";
    }

    /**
     * @Description  跳转至孙项详情页面
     * @Author wym
     * @Date 2025/7/14 11:39
     **/
    @GetMapping("/config-sun-detail/{id}")
    public String sunDetail(PleaseMoneyConfigSub pleaseMoneyConfigSub, ModelMap mmap) {
        mmap.put("pleaseMoneyConfigSub", pleaseMoneyConfigService.selectPleaseMoneyConfigSubList(pleaseMoneyConfigSub).get(Constants.ZERO));
        return prefix + "/config-sun-detail";
    }

    /**
     * @Description  孙项提交页面跳转
     * @Author wym
     * @Date 2025/7/14 16:16
     **/
    @GetMapping("/jump-submit/{id}")
    public String jumpSubmit(PleaseMoneyConfigSun pleaseMoneyConfigSun, ModelMap mmap) {
        PleaseMoneyConfigSun data = pleaseMoneyConfigService.selectPleaseMoneyConfigSunList(pleaseMoneyConfigSun).get(Constants.ZERO);
        if (!ObjectUtils.isEmpty(data.getCreateBy()) && data.getCreateBy().equals(getSysUser().getUserName())){
            mmap.put("pleaseMoneyConfigSun", data);
            return prefix + "/config-sun-detail-submit";
        }
        return "error/unauth";
    }

    /**
     * @Description  孙项处理页面跳转
     * @Author wym
     * @Date 2025/7/15 9:32
     **/
    @GetMapping("/jump-handle/{id}")
    public String jumpHandle(PleaseMoneyConfigSun pleaseMoneyConfigSun, ModelMap mmap) {
        PleaseMoneyConfigSun data = pleaseMoneyConfigService.selectPleaseMoneyConfigSunList(pleaseMoneyConfigSun).get(Constants.ZERO);
        if (!ObjectUtils.isEmpty(data.getHandleBy()) && data.getHandleBy().equals(getSysUser().getUserName())){
            mmap.put("pleaseMoneyConfigSun", data);
            return prefix + "/config-sun-detail-handle";
        }
        return "error/unauth";
    }

    /**
     * @Description  主项查询
     * @Author wangshilin
     * @Date 2025/7/4 14:25
     **/
    @PostMapping("/config-list")
    @ResponseBody
    public TableDataInfo configList(PleaseMoneyConfig pleaseMoneyConfig){
        startPage();
        return getDataTable(pleaseMoneyConfigService.selectPleaseMoneyConfigList(pleaseMoneyConfig));
    }

    /**
     * @Description  子项查询
     * @Author wangshilin
     * @Date 2025/7/4 14:25
     **/
    @PostMapping("/config-detail-list")
    @ResponseBody
    public TableDataInfo configDetailList(PleaseMoneyConfigSub pleaseMoneyConfigSub){
        return getDataTable(pleaseMoneyConfigService.selectPleaseMoneyConfigSubList(pleaseMoneyConfigSub));
    }

    /**
     * @Description  孙项通过子项id查询
     * @Author wym
     * @Date 2025/7/4 14:25
     **/
    @PostMapping("/config-sun-detail-list")
    @ResponseBody
    public TableDataInfo selectSunListByPleaseMoneyConfigSubId(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        return getDataTable(pleaseMoneyConfigService.selectSunListByPleaseMoneyConfigSubId(pleaseMoneyConfigSun));
    }

    /**
     * @Description  请款配置新增
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @Log(title = "请款配置-新增", businessType = BusinessType.INSERT)
    @PostMapping("/config-add")
    @ResponseBody
    public AjaxResult configAdd(PleaseMoneyConfig pleaseMoneyConfig) {
        return toAjax(pleaseMoneyConfigService.insertPleaseMoneyConfig(pleaseMoneyConfig));
    }

    /**
     * @Description  请款配置子项新增
     * @Author wangshilin
     * @Date 2025/7/4 16:04
     **/
    @Log(title = "请款配置子项-新增", businessType = BusinessType.INSERT)
    @PostMapping("/config-detail-add")
    @ResponseBody
    public AjaxResult configDetailAdd(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        return toAjax(pleaseMoneyConfigService.insertPleaseMoneyConfigSub(pleaseMoneyConfigSub));
    }

    /**
     * @Description  请款配置孙项新增
     * @Author wym
     * @Date 2025/7/14 15:20
     **/
    @Log(title = "请款配置孙项-新增", businessType = BusinessType.INSERT)
    @PostMapping("/config-sun-detail-add")
    @ResponseBody
    public AjaxResult insertPleaseMoneyConfigSun(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        //判断主表ID不能为空
        if(pleaseMoneyConfigSun.getSubId()==null){
            return AjaxResult.error("主表ID不能为空");
        }
        return toAjax(pleaseMoneyConfigService.insertPleaseMoneyConfigSun(pleaseMoneyConfigSun));
    }

    /**
     * @Description  请款配置修改
     * @Author wangshilin
     * @Date 2025/6/25 09:38
     **/
    @Log(title = "请款配置-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/config-edit")
    @ResponseBody
    public AjaxResult configEdit(PleaseMoneyConfig pleaseMoneyConfig) {
        return toAjax(pleaseMoneyConfigService.updatePleaseMoneyConfig(pleaseMoneyConfig));
    }

    /**
     * @Description  请款配置子项修改
     * @Author wangshilin
     * @Date 2025/6/25 09:38
     **/
    @Log(title = "请款配置子项-修改", businessType = BusinessType.DELETE)
    @PostMapping("/config-detail-edit")
    @ResponseBody
    public AjaxResult configDetailEdit(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        return toAjax(pleaseMoneyConfigService.updatePleaseMoneyConfigSub(pleaseMoneyConfigSub));
    }

    /**
     * @Description  请款配置删除
     * @Author wangshilin
     * @Date 2025/6/25 09:16
     **/
    @Log(title = "请款配置-删除", businessType = BusinessType.DELETE)
    @PostMapping("/config-remove")
    @ResponseBody
    public AjaxResult configRemove(PleaseMoneyConfig pleaseMoneyConfig) {
        return toAjax(pleaseMoneyConfigService.deletePleaseMoneyConfigByIds(pleaseMoneyConfig));
    }

    /**
     * @Description  请款配置删除
     * @Author wangshilin
     * @Date 2025/6/25 09:16
     **/
    @Log(title = "请款配置子项-删除", businessType = BusinessType.DELETE)
    @PostMapping("/config-detail-remove")
    @ResponseBody
    public AjaxResult configDetailRemove(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        return toAjax(pleaseMoneyConfigService.deletePleaseMoneyConfigSubByIds(pleaseMoneyConfigSub));
    }

    /**
     * @Description  请款配置提交
     * @Author wangshilin
     * @Date 2025/6/25 10:17
     **/
    @Log(title = "请款配置-提交", businessType = BusinessType.UPDATE)
    @PostMapping("/config-commit")
    @ResponseBody
    public AjaxResult configCommit(PleaseMoneyConfig pleaseMoneyConfig) {
        return toAjax(pleaseMoneyConfigService.updatePleaseMoneyConfig(pleaseMoneyConfig));
    }

    /**
     * @Description  请款配置-完成
     * @Author wangshilin
     * @Date 2025/7/18 14:42
     **/
    @Log(title = "请款配置-完成", businessType = BusinessType.UPDATE)
    @PostMapping("/config-complete")
    @ResponseBody
    public AjaxResult configComplete(PleaseMoneyConfig pleaseMoneyConfig) {
        return toAjax(pleaseMoneyConfigService.configComplete(pleaseMoneyConfig));
    }

    /**
     * @Description  孙表的提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
     * @Author wym
     * @Date 2025/7/14 16:51
     **/
    @Log(title = "请款配置孙项-提交", businessType = BusinessType.UPDATE)
    @PostMapping("/pleaseMoneyConfigSun-submit")
    @ResponseBody
    public AjaxResult PleaseMoneyConfigSunSubmit(PleaseMoneyConfigSun pleaseMoneyConfigSun){
        try {
            pleaseMoneyConfigService.PleaseMoneyConfigSunSubmit(pleaseMoneyConfigSun);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("提交失败：" + e.getMessage());
        }
    }

    /**
     * @Description  孙项处理功能，添加处理意见，处理时间,修改三表状态为 3或 4,通过主ID
     * @Author wym
     * @Date 2025/7/15 10:21
     **/
    @Log(title = "请款配置孙项-处理", businessType = BusinessType.UPDATE)
    @PostMapping("/pleaseMoneyConfigSun-handle")
    @ResponseBody
    public AjaxResult PleaseMoneyConfigSunHandle(PleaseMoneyConfigSun pleaseMoneyConfigSun) {
        try {
            // 更新流程信息
            pleaseMoneyConfigService.PleaseMoneyConfigSunHandle(pleaseMoneyConfigSun);
            return AjaxResult.success();
        } catch (Exception e) {
            return AjaxResult.error("处理失败：" + e.getMessage());
        }
    }

    /**
     * @Description  结束功能，修改二表状态为 3,通过主ID
     * @Author wym
     * @Date 2025/7/15 10:38
     **/
    @Log(title = "请款配置子项-结束", businessType = BusinessType.UPDATE)
    @PostMapping("/pleaseMoneyConfigSub-finish")
    @ResponseBody
    public AjaxResult PleaseMoneyConfigSubFinish(PleaseMoneyConfigSub pleaseMoneyConfigSub) {
        return toAjax(pleaseMoneyConfigService.PleaseMoneyConfigSubFinish(pleaseMoneyConfigSub));
    }

    /**
     * @Description  孙项查询中的文件下载
     * @Author wym
     * @Date 2025/7/15 13:57
     **/
    @GetMapping("/pleaseMoneyConfigSun-files/{id}")
    @ResponseBody
    public AjaxResult PleaseMoneyConfigSunFiles(@PathVariable("id") Long id) {
        try {
            // 调用服务层方法获取文件信息
            String files = pleaseMoneyConfigService.PleaseMoneyConfigSunFiles(id);
            return AjaxResult.success(files);
        }
        catch (Exception e) {
            return AjaxResult.error("文件下载失败：" + e.getMessage());
        }
    }
}
