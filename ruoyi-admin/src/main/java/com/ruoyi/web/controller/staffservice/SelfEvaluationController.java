package com.ruoyi.web.controller.staffservice;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.selfevaluation.domain.SelfEvaluation;
import com.ruoyi.selfevaluation.service.SelfEvaluationService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.work.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.system.service.ISysPostService;
import java.util.List;

import static com.ruoyi.common.utils.PageUtils.startPage;
import static com.ruoyi.common.utils.ShiroUtils.getSysUser;

@Controller
@RequestMapping("/staff-service/self-evaluation")
public class SelfEvaluationController extends BaseController {
    private String prefix = "staff-service/self-evaluation";
    @Autowired
    private SelfEvaluationService selfEvaluationService;
    @Autowired
    private ISysPostService sysPostService;

    //跳转页面
    @GetMapping("/initLoads")
    public String initLoads(ModelMap mmap) {
        if (getSysUser().getLoginName().equals(Constants.GENERAL_PERSONNEL)){
            mmap.put("isCeo", true);
            mmap.put("userName", getSysUser().getUserName());
            return prefix + "/self-evaluation";
        }
        SysPost post = new SysPost();
        post.setPostCode(Constants.GENERAL_MANAGER_CODE);
        List<SysUser> userList = sysPostService.getUserListByPost(post);
        for (SysUser sysUser : userList) {
            if (sysUser.getLoginName().equals(getSysUser().getLoginName())) {
                mmap.put("isCeo", true);
                mmap.put("userName", getSysUser().getUserName());
                return prefix + "/self-evaluation";
            }
        }
        mmap.put("isCeo", false);
        mmap.put("userName", getSysUser().getUserName());
        return prefix + "/self-evaluation";
    }

    //新增页面跳转
    @GetMapping("/add")
    public String add() {
        return prefix + "/self-evaluation-add";
    }
    //查询功能
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(SelfEvaluation selfEvaluation) {
        startPage();
        logger.info("查询参数：{}",selfEvaluation);
        List<SelfEvaluation> evaluations =selfEvaluationService.selectEvalutionList(selfEvaluation);
        return getDataTable(evaluations);
    }

    /**
     * 新增功能
     */
    @Log(title = "个人评价-新增", businessType = BusinessType.INSERT)
    @PostMapping("/evaluation-add")
    @ResponseBody
    public AjaxResult insertWork(SelfEvaluation selfEvaluation)
    {
        return toAjax(selfEvaluationService.insertEvalution(selfEvaluation));
    }
   /**
     * 提交功能
     */
    @Log(title = "个人评价-提交", businessType = BusinessType.UPDATE)
    @PostMapping("/submit/{id}")
    @ResponseBody
    public AjaxResult submit(@PathVariable Long id) {
        SelfEvaluation selfEvaluation = new SelfEvaluation();
        selfEvaluation.setId(id);
        logger.info("提交参数：{}",selfEvaluation);
        return toAjax(selfEvaluationService.submitSelfEvaluation(selfEvaluation));
    }
    /**
     * 删除功能
     */
    @Log(title = "个人评价-删除", businessType = BusinessType.DELETE)
    @PostMapping( "/remove/{id}")
    @ResponseBody
    public AjaxResult remove(@PathVariable Long id)
    {
        SelfEvaluation selfEvaluation = new SelfEvaluation();
        selfEvaluation.setId(id);
        logger.info("删除参数：{}",selfEvaluation);
        return toAjax(selfEvaluationService.deleteSelfEvaluationById(selfEvaluation));
    }



}
