package com.ruoyi.work.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Work extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 考勤描述 */
    @Excel(name = "考勤描述")
    private String workDescribe;

    /** 文件 */
    @Excel(name = "文件")
    private String fileNames;

    /** 删除标记  false=存在；true=删除 */
    @Excel(name = "删除标记  false=存在；true=删除")
    private Integer deleteFlag;

    /** 备注 */
    @Excel(name = "备注")
    private String note;

    /******************* 查询条件使用 ********************/
    /** 起始创建时间 */
    private String beginCreateTime;

    /** 结束创建时间 */
    private String endCreateTime;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
}
