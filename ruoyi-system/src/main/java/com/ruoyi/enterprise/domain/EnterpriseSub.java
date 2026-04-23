package com.ruoyi.enterprise.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EnterpriseSub extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键，自增
     */
    private Long id;

    /**
     * 删除标记：0=未删除，1=已删除
     */
    private Long deleteFlag;

    /**
     * 企业id
     */
    private Long enterpriseId;

    /**
     * 访问方式
     * 选项：电话、线下拜访、微信沟通、其他
     */
    @Excel(name = "访问方式")
    private String wayOfVision;

    /**
     * 进展情况
     */
    @Excel(name = "进展情况")
    private String progress;

    /**
     * 沟通时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "沟通时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date communicationTime;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String note;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof EnterpriseSub)) {
            return false;
        }
        EnterpriseSub that = (EnterpriseSub) o;
        return Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag) && Objects.equals(enterpriseId, that.enterpriseId) && Objects.equals(wayOfVision, that.wayOfVision) && Objects.equals(progress, that.progress) && Objects.equals(communicationTime, that.communicationTime) && Objects.equals(note, that.note);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deleteFlag, enterpriseId, wayOfVision, progress, communicationTime, note);
    }
}