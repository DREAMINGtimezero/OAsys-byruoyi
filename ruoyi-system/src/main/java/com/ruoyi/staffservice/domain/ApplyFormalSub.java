package com.ruoyi.staffservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyFormalSub extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 主表主键id */
    private Long applyFormalId;

    /** 发起人意见 */
    private String initiateNote;

    /** 发起时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date initiateTime;

    /** 处理人 */
    private String handleBy;

    /** 处理人意见 */
    private String handleNote;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date handleTime;

    /** 文件访问路径 */
    private String fileNames;

    /** 状态(1=未提交，2=待处理，3=已处理，4=已驳回) */
    private Integer status;

    /** 删除标记 */
    private Integer deleteFlag;
}
