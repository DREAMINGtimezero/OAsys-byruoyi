package com.ruoyi.web.controller.staffservice;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.email.domain.Mail;
import com.ruoyi.email.service.EmailService;
import com.ruoyi.project.domain.ProjectStatis;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.work.domain.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description  发送邮件controller
 * @Author wym
 * @Date 2025/10/9
 **/
@Controller
@RequestMapping("/staff-service/email")
public class EmailController extends BaseController {
    private String prefix = "staff-service/email";

    @Autowired
    private EmailService emailService;

    @Autowired
    private ISysPostService postService;

    //跳转主页面
    @GetMapping("/initLoads")
    public String initLoads(ModelMap mmap){
        mmap.put("userName", getSysUser().getUserName());
        return prefix + "/email";
    }

     //跳转至新增页面
    @GetMapping("/add")
    public String add(){
        return prefix + "/email-add";
    }

    //跳转至修改页面
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap) {
        Mail email = emailService.selectEmailById(id);
        mmap.put("email", email);
        return prefix + "/email-edit";
    }

    //查看邮件内容跳转
    @GetMapping("/emailDetail/{id}")
    public String emailDetail(@PathVariable("id") Long id, ModelMap mmap) {
        mmap.put("emailDetail", emailService.selectEmailById(id));
        return prefix + "/email-emailDetail";
    }

    //**********************************************************
    //主页面查询功能
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Mail mail) {
        startPage();
        List<Mail> mails = emailService.selectEmailList(mail);
        return getDataTable(mails);
    }

    //新增要发送邮件
    @Log(title = "发送邮件-新增", businessType = BusinessType.INSERT)
    @PostMapping("/email-add")
    @ResponseBody
    public AjaxResult insertEmail(Mail mail) {
        return toAjax(emailService.insertEmail(mail));
    }

    //修改要发送邮件
    @Log(title = "发送邮件-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/email-edit")
    @ResponseBody
    public AjaxResult updateEmail(Mail mail) {
        return toAjax(emailService.updateEmail(mail));
    }

    //删除要发送邮件
    @Log(title = "发送邮件-删除", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult deleteEmailByIds(Mail mail) {
        return toAjax(emailService.deleteEmailByIds(mail));
    }

    //确认发送邮件
    @Log(title = "确认发送邮件", businessType = BusinessType.UPDATE)
    @PutMapping("/send/{id}")
    @ResponseBody
    public AjaxResult sendEmails(@PathVariable Long id) {
        try {
           boolean success = emailService.sendEmailsByEmailId(id);
           if (!success) {
               return AjaxResult.error("邮件发送失败");
           }
           return AjaxResult.success("邮件发送成功");
        } catch (Exception e) {
            return AjaxResult.error("邮件发送失败：" + e.getMessage());
        }
    }

    /**
     * 根据部门id和岗位id查询用户
     * @return 用户列表
     */
    @GetMapping("/get-user")
    @ResponseBody
    public AjaxResult deptPostUsers(){
        List<SysUser> list = postService.getUserListByPostAndDeptId(null,null);
        return AjaxResult.success(list);
    }
}
