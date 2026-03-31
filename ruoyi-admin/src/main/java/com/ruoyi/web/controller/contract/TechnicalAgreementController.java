package com.ruoyi.web.controller.contract;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.ruoyi.common.core.text.Convert;

/**
 * 技术协议管理
 *
 * @author YJ
 */
@Slf4j
@Controller
@RequestMapping("/contract/technical-agreement")
public class TechnicalAgreementController extends BaseController { //签订销售合同&技术协议    技术协议
    /**
     * 页面访问地址前缀
     */
    private final String prefix = "contract/technicalagreement";

    @Autowired
    private TechnicalAgreementService mainService;

    /**
     * 获取服务主页面
     *
     * @param statusList 状态列表,从系统首页跳转到服务页面时,会带一个statusList参数过来,用于查询指定状态的服务
     * @param mp         ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/initLoads")//主页面跳转页面
    public String initLoads(@RequestParam(name = "statusList", required = false) String statusList, ModelMap mp) {
        mp.put("statusList", statusList);
        return prefix + "/technical-agreement";
    }

    /**
     * 查询技术协议列表
     *
     * @param technicalAgreement 查询条件
     * @return 列表数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(TechnicalAgreement technicalAgreement) {
        //分页
        startPage();
        //处理状态列表(前端多选下拉框)
        if (StringUtils.isNotEmpty(technicalAgreement.getStatus())) {
            technicalAgreement.setStatusList(Convert.toIntArray(technicalAgreement.getStatus()));
        }
        technicalAgreement.setDeleteFlag(0);
        List<TechnicalAgreement> list = mainService.selectTechnicalAgreementList(technicalAgreement);
        return getDataTable(list);
    }

    /**
     * 导出技术协议列表
     */
    @Log(title = "技术协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TechnicalAgreement technicalAgreement) {
        List<TechnicalAgreement> list = mainService.selectTechnicalAgreementList(technicalAgreement);
        ExcelUtil<TechnicalAgreement> util = new ExcelUtil<TechnicalAgreement>(TechnicalAgreement.class);
        return util.exportExcel(list, "技术协议数据");
    }

    /**
     * 获取新增技术协议页面
     *
     * @return 跳转页面
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/technical-agreement-add";
    }

    /**
     * 新增保存技术协议
     *
     * @param technicalAgreement 添加的数据
     * @return 修改结果
     */
    @Log(title = "添加技术协议主表", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(TechnicalAgreement technicalAgreement) {
        return toAjax(mainService.insertTechnicalAgreement(technicalAgreement));
    }

    /**
     * 修改技术协议
     *
     * @param id 需要修改的技术协议主项主键id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mp) {
        TechnicalAgreement technicalAgreement = mainService.selectTechnicalAgreementById(id);
        //只能修改未提交的
        if (Constants.MainStatus.UNSUBMIT.equals(technicalAgreement.getStatus())) {
            mp.put("technicalAgreement", technicalAgreement);
            return prefix + "/technical-agreement-edit";
        }
        return "error/unauth";
    }

    /**
     * 修改保存技术协议
     *
     * @param technicalAgreement 修改的数据
     * @return 修改结果
     */
    @Log(title = "技术协议", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(TechnicalAgreement technicalAgreement) {
        return toAjax(mainService.updateTechnicalAgreement(technicalAgreement));
    }

    /**
     * 删除技术协议
     *
     * @param ids 需要删除的技术协议主项主键id列表
     * @return 删除结果
     */
    @Log(title = "技术协议", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(mainService.deleteTechnicalAgreementByIds(ids));
    }

    /**
     * 获取技术协议详情页面
     *
     * @param id 主项主键id
     * @param mp ModelMap实例,用于向页面传递数据
     * @return 跳转页面
     */
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable("id") Long id, ModelMap mp) {
        TechnicalAgreement technicalAgreement = mainService.selectTechnicalAgreementById(id);
        mp.put("technicalAgreement", technicalAgreement);
        return prefix + "/sub/detail";
    }

    /**
     * 结束技术协议流程
     *
     * @param id 主项主键id
     * @return 结果
     */
    @Log(title = "结束技术协议流程", businessType = BusinessType.UPDATE)
    @PutMapping("/over/{id}")
    @ResponseBody
    public AjaxResult overProcess(@PathVariable("id") Long id) {
        //校验
        if (!mainService.checkOverAble(id)) {
            return AjaxResult.error("还有进行中的流程");
        }
        TechnicalAgreement mainData = new TechnicalAgreement();
        mainData.setId(id);
        mainData.setStatus(Constants.MainStatus.COMPLETE);
        return toAjax(mainService.updateTechnicalAgreement(mainData));
    }

    /**
     * 放开技术协议流程的编辑权限
     *
     * @param id 主表id
     * @return 修改结果
     */
    @Log(title = "放开技术协议流程的编辑权限", businessType = BusinessType.UPDATE)
    @PutMapping("/proceed/{id}")
    @ResponseBody
    public AjaxResult proceedProcess(@PathVariable("id") Long id) {
        TechnicalAgreement mainData = new TechnicalAgreement();
        mainData.setId(id);
        mainData.setStatus(Constants.MainStatus.PROCESSING);
        return toAjax(mainService.updateTechnicalAgreement(mainData));
    }

    /**
     * 获取技术协议信息
     *
     * @param id 主表id
     * @return 技术协议信息
     */
    @GetMapping("/agreement/{id}")
    @ResponseBody
    public AjaxResult handle(@PathVariable("id") Long id) {
        return AjaxResult.success(mainService.selectTechnicalAgreementById(id));
    }

    /**
     * 获取最后编辑人
     *
     * @param id 主表id
     * @return 最后编辑人
     */
    @GetMapping("/last-editor/{id}")
    @ResponseBody
    public AjaxResult getLastEditor(@PathVariable("id") Long id) {
        return AjaxResult.success(mainService.getLastEditor(id));
    }
}
