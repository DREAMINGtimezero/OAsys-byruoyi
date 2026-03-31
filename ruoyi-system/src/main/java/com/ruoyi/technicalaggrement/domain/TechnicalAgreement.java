package com.ruoyi.technicalaggrement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * 技术协议对象 t_technical_agreement
 * @author YJ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TechnicalAgreement  extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    /**
     * 所属项目编号
     */
    @Excel(name = "所属项目编号")
    private String projectCode;

    /**
     * 技术协议编号
     */
    @Excel(name = "技术协议编号")
    private String technicalAgreementCode;

    /**
     * 技术协议文件名称
     */
    @Excel(name = "技术协议文件名称")
    private String title;

    /**
     * 描述信息
     */
    @Excel(name = "描述信息")
    private String description;

    /**
     * 备注信息
     */
    @Excel(name = "备注信息")
    private String note;

    /**
     * 1=未提交，2=处理中，3=已完成
     */
    @Excel(name = "1=未提交，2=处理中，3=已完成")
    private String status;

    /**
     * 自增主键
     */
    private Long id;

    /**
     * 逻辑删除 0-正常 1-删除
     */
    private Integer deleteFlag;


    /**
     * 开始时间(时间范围的下限)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    /**
     * 结束时间(时间范围的上限)
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;

    /**
     * 项目名称
     */
    @Excel(name = "所属项目名称")
    private String projectName;

    /**
     * 状态列表
     */
    private Integer[] statusList;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof TechnicalAgreement)) {return false;}
        TechnicalAgreement that = (TechnicalAgreement) o;
        return Objects.equals(projectCode, that.projectCode) && Objects.equals(technicalAgreementCode, that.technicalAgreementCode) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(note, that.note) && Objects.equals(status, that.status) && Objects.equals(id, that.id) && Objects.equals(deleteFlag, that.deleteFlag) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(projectName, that.projectName) && Objects.deepEquals(statusList, that.statusList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, technicalAgreementCode, title, description, note, status, id, deleteFlag, startTime, endTime, projectName, Arrays.hashCode(statusList));
    }
}
