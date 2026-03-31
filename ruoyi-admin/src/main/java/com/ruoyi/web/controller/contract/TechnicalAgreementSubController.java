package com.ruoyi.web.controller.contract;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementSubService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 技术协议子表Controller
 *
 * @author YJ
 * @date 2025-06-25
 */
@Controller
@RequestMapping("/contract/technical-agreement/sub")
public class TechnicalAgreementSubController extends BaseController {
    private final String prefix = "contract/technicalagreement/sub";
    @Autowired
    private TechnicalAgreementSubService subService;

    /**
     * 查询技术协议子表列表
     * @param technicalAgreementId 关联的主表id
     * @return 列表数据
     */
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(@RequestParam(name = "technicalAgreementId") Long technicalAgreementId) {
//        startPage();
        TechnicalAgreementSub condition = new TechnicalAgreementSub();
        condition.setTechnicalAgreementId(technicalAgreementId);
        condition.setDeleteFlag(0);
        List<TechnicalAgreementSub> list = subService.selectTechnicalAgreementSubList(condition);
        return getDataTable(list);
    }

    /**
     * 导出技术协议子表列表
     */
    @Log(title = "导出技术协议子表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TechnicalAgreementSub technicalAgreementSub) {
        List<TechnicalAgreementSub> list = subService.selectTechnicalAgreementSubList(technicalAgreementSub);
        ExcelUtil<TechnicalAgreementSub> util = new ExcelUtil<TechnicalAgreementSub>(TechnicalAgreementSub.class);
        return util.exportExcel(list, "技术协议子表数据");
    }

    /**
     * 删除技术协议子表
     */
    @Log(title = "技术协议子表", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(String ids) {
        return toAjax(subService.deleteTechnicalAgreementSubByIds(ids));
    }

    /**
     * 通过技术协议流程
     * @param sub 需要更新的数据,根据主键查询
     * @return 处理的结果
     */
    @Log(title = "通过技术协议流程", businessType = BusinessType.UPDATE)
    @PostMapping("/pass")
    @ResponseBody
    public AjaxResult passProcess(TechnicalAgreementSub sub)
    {
        sub.setStatus(Constants.SubStatus.PASSED);
        return toAjax(subService.handleProcess(sub));
    }

    /**
     * 拒绝技术协议流程
     * @param sub 待更新数据,根据主键id查询
     * @return 处理结果
     */
    @Log(title = "拒绝技术协议流程", businessType = BusinessType.UPDATE)
    @PostMapping("/reject")
    @ResponseBody
    public AjaxResult rejectProcess(TechnicalAgreementSub sub){
       sub.setStatus(Constants.SubStatus.REJECTED);
       return toAjax(subService.handleProcess(sub));
    }

    /**
     * 新增技术协议流程
     * @param relativeId 主表与子表的关联id
     * @return 新增的子表数据
     */
    @Log(title = "新增技术协议流程", businessType = BusinessType.UPDATE)
    @GetMapping("/add/{id}")
    @ResponseBody
    public AjaxResult add(@PathVariable("id") Long relativeId)
    {
        TechnicalAgreementSub result = subService.add(relativeId);
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
     * 提交技术协议流程
     * @param technicalAgreementSub 需要更新的数据,主键id为查找字段
     * @return 处理结果
     */

    @Log(title = "提交技术协议流程", businessType = BusinessType.UPDATE)
    @PostMapping("/submit")
    @ResponseBody
    public AjaxResult submitProcess(TechnicalAgreementSub technicalAgreementSub) {
        return toAjax(subService.submitProcess(technicalAgreementSub));
    }

    /**
     * 获取提交页面
     * @param id 子表数据的id
     * @param mp ModelMap实例,用于向前端传递数据
     * @return 提交页面
     */
    @GetMapping("/submit/{id}")
    public String submit(@PathVariable("id") Long id, ModelMap mp)
    {
        if(!subService.submitPermission(id))
        {
            return "error/unauth";
        }
        TechnicalAgreementSub technicalAgreementSub = subService.selectTechnicalAgreementSubById(id);
        mp.put("technicalAgreementSub", technicalAgreementSub);
        return prefix + "/agreement-sub-creator-edit";
    }

    /**
     * 获取处理页面
     * @param id 子表数据的id
     * @param mp ModelMap实例,用于向前端传递数据
     * @return 处理页面
     */
    @GetMapping("/handle/{id}")
    public String handle(@PathVariable("id") Long id, ModelMap mp)
    {
        if(!subService.handlePermission(id))
        {
            return "error/unauth";
        }
        TechnicalAgreementSub technicalAgreementSub = subService.selectTechnicalAgreementSubById(id);
        mp.put("technicalAgreementSub", technicalAgreementSub);
        return prefix + "/agreement-sub-handler-edit";
    }
}
