package com.ruoyi.web.controller.website;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.customer.domain.Customer;
import com.ruoyi.website.domain.Email;
import com.ruoyi.website.domain.RawCustomerMessage;
import com.ruoyi.customer.service.CustomerService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.website.service.EmailSenderService;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author GYX
 */
@Controller
@RequestMapping("/huajufeng")
public class WebsiteController {
    @Autowired
    private EmailSenderService emailSenderService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    @Qualifier("myTaskThreadPool")
    private ThreadPoolExecutor threadPoolExecutor;

    private final String prefix = "huajufeng/website";

    /**
     * @Description 华钜峰工程技术有限公司网站
     * @Author wangshilin
     * @Date 2025/6/24 15:37
     **/
    @GetMapping()
    public String initLoad() {
        return prefix + "/index";
    }
    @GetMapping("/website")
    public String initLoads() {
        return prefix + "/index";
    }

    @GetMapping("/website/overview")
    public String overview() {
        return prefix + "/overview";
    }
    @GetMapping("/website/service")
    public String service() {
        return prefix + "/service";
    }
    @GetMapping("/website/cases")
    public String cases() {
        return prefix + "/cases";
    }
    @GetMapping("/website/honor")
    public String honor(){
        return prefix+"/honor";
    }
    @GetMapping("/website/news")
    public String news(){
        return prefix+"/news";
    }

    @Log(title = "发送邮件", businessType = BusinessType.INSERT)
    @PostMapping("/website/send-email")
    @ResponseBody
    public AjaxResult sendEmail(@Valid @RequestBody RawCustomerMessage message) {
        //先查询是否存在
        Customer condition = new Customer();
        condition.setEmail(message.getEmail());
        condition.setPhone(message.getTelephone());
        condition.setCustomerName(message.getName());
        if(customerService.exists(condition))
        {
            return AjaxResult.error("该客户已存在");
        }
        //NOTE 可以考虑发信的重试机制
        threadPoolExecutor.execute(() -> {
            emailSenderService.recordCustomerMessage(message);
        });
        int result = customerService.addWithMessage(message);
        if (result == 0) {
            return AjaxResult.error("添加失败，请重试");
        }
        return AjaxResult.success("添加成功");
    }
}
