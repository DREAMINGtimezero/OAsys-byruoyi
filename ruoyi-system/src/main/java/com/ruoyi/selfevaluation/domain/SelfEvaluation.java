package com.ruoyi.selfevaluation.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.time.LocalDateTime;
@Data
public class SelfEvaluation extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 文件 */
    @Excel(name = "文件")
    private String fileNames;

    /** 1=未提交
     2=已提交 */
    @Excel(name = "1=未提交 2=已提交")
    private Long status;

    /** 删除标记  false=存在；true=删除 */
    @Excel(name = "删除标记  false=存在；true=删除")
    private Integer deleteFlag;
    /** 起始创建时间 */
    private String startTime;

    /** 结束创建时间 */
    private String endTime;
}
