package com.ruoyi.website.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Objects;

/**
 * @author YJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RawCustomerMessage {
    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "公司名称不能为空")
    private String company;

    @NotBlank(message = "电话不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "电话格式不正确")
    private String telephone;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;

    @NotBlank(message = "主题不能为空")
    private String subject;

    @NotBlank(message = "内容不能为空")
    private String content;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RawCustomerMessage)) {
            return false;
        }
        RawCustomerMessage that = (RawCustomerMessage) o;
        return Objects.equals(name, that.name) && Objects.equals(company, that.company) && Objects.equals(telephone, that.telephone) && Objects.equals(email, that.email) && Objects.equals(subject, that.subject) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, company, telephone, email, subject, content);
    }

    @Override
    public String toString() {
        return "RawCustomerMessage{" +
                "name='" + name + '\'' +
                ", company='" + company + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
