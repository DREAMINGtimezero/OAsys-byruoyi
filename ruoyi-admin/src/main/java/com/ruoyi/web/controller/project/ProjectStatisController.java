package com.ruoyi.web.controller.project;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.project.domain.ProjectStatis;
import com.ruoyi.project.service.ProjectStatisService;
import com.ruoyi.system.domain.SysConfig;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.web.controller.demo.domain.UserOperateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description  项目统计controller
 * @Author wangshilin
 * @Date 2025/6/24 15:37
 **/
@Controller
@RequestMapping("/project/statis")
public class ProjectStatisController extends BaseController {

    private String prefix = "project/statis";

    @Autowired
    private ProjectStatisService projectStatisService;

    @Autowired
    private ISysPostService sysPostService;

    /**
     * @Description  页面初始化
     * @Author wangshilin
     * @Date 2025/6/24 15:37
     **/
    @GetMapping("/initLoads")
    public String initLoads(ModelMap modelMap) {
        SysPost post = new SysPost();
        post.setPostCode(Constants.GENERAL_MANAGER_CODE);
        List<SysUser> userList = sysPostService.getUserListByPost(post);
        for (SysUser sysUser : userList) {
            if (sysUser.getLoginName().equals(getSysUser().getLoginName())) {
                modelMap.put("isCeo", true);
                return prefix + "/project-statis";
            }
        }
        modelMap.put("isCeo", false);
        return prefix + "/project-statis";
    }

    /**
     * @Description  跳转至详情页面
     * @Author wangshilin
     * @Date 2025/6/24 15:37
     **/
    @GetMapping("/project-statis-detail/{id}")
    public String projectStatisDetail(@PathVariable Long id, ModelMap mmap) {
        ProjectStatis projectStatis = new ProjectStatis();
        projectStatis.setId(id);
        mmap.put("projectStatis", projectStatisService.selectProjectStatisList(projectStatis).get(Constants.ZERO));
        return prefix + "/project-statis-detail";
    }

    /**
     * @Description  跳转至新增页面
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @GetMapping("/add")
    public String add() {
        return prefix + "/project-statis-add";
    }

    /**
     * @Description  跳转至修改页面
     * @Author wangshilin
     * @Date 2025/6/25 09:53
     **/
    @GetMapping("/edit/{id}")
    public String edit(ProjectStatis projectStatis,ModelMap mmap) {
        mmap.put("projectStatis", projectStatisService.selectProjectStatisList(projectStatis).get(Constants.ZERO));
        return prefix + "/project-statis-edit";
    }

    /**
     * @Description  项目列表查询
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(ProjectStatis projectStatis) {
        startPage();
        return getDataTable(projectStatisService.selectProjectStatisList(projectStatis));
    }

    /**
     * @Description  项目新增
     * @Author wangshilin
     * @Date 2025/6/24 15:38
     **/
    @Log(title = "项目管理-新增", businessType = BusinessType.INSERT)
    @PostMapping("/project-statis-add")
    @ResponseBody
    public AjaxResult projectStatisAdd(ProjectStatis projectStatis) {
        return toAjax(projectStatisService.insertProjectStatis(projectStatis));
    }

    /**
     * @Description  项目修改
     * @Author wangshilin
     * @Date 2025/6/25 09:38
     **/
    @Log(title = "项目管理-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/project-statis-edit")
    @ResponseBody
    public AjaxResult projectStatisEdit(ProjectStatis projectStatis) {
        return toAjax(projectStatisService.updateProjectStatis(projectStatis));
    }

    /**
     * @Description  项目删除
     * @Author wangshilin
     * @Date 2025/6/25 09:16
     **/
    @Log(title = "项目管理-删除", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    @ResponseBody
    public AjaxResult remove(ProjectStatis projectStatis) {
        return toAjax(projectStatisService.deleteProjectStatisByIds(projectStatis));
    }

    /**
     * @Description  项目提交
     * @Author wangshilin
     * @Date 2025/6/25 10:17
     **/
    @Log(title = "项目管理-提交", businessType = BusinessType.UPDATE)
    @PostMapping("/commit")
    @ResponseBody
    public AjaxResult commit(ProjectStatis projectStatis) {
        return toAjax(projectStatisService.updateProjectStatis(projectStatis));
    }

    /**
     * @Description  项目导出
     * @Author wangshilin
     * @Date 2025/6/25 10:52
     **/
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(ProjectStatis projectStatis) {
        List<ProjectStatis> list = projectStatisService.selectProjectStatisList(projectStatis);
        ExcelUtil<ProjectStatis> util = new ExcelUtil(ProjectStatis.class);
        return util.exportExcel(list, "项目数据");
    }
}
