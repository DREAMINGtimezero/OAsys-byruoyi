package com.ruoyi.staffservice.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplyFormal extends BaseEntity {

    /** 主键 */
    private Long id;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 首次工作日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date workDate;

    /** 入职日期 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceDate;

    /** 试用期岗位 */
    private String servicePost;

    /** 工作总结 */
    private String workSummary;

    /** 给公司的建议 */
    private String suggestion;

    /** 状态 （1=未提交2=处理中3=已完成） */
    private Integer status;

    /** 删除标记 */
    private Integer deleteFlag;
}
