package com.ruoyi.project.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description 项目管理实体类
 * @Author wangshilin
 * @Date 2025/6/24 15:46
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatis extends BaseEntity {
    /** 主键 */
    private Long id;

    /******************* 删除使用 ********************/
    /** 主键数组 ids */
    private Long[] ids;
    /******************* 删除使用 ********************/

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目详情 */
    @Excel(name = "项目详情")
    private String projectDetail;

    /** 项目备注 */
    @Excel(name = "项目备注")
    private String projectRemark;

    /** 状态 */
    @Excel(name = "状态",readConverterExp = "1=未提交,2=已提交,3=已完成")
    private Integer status;

    /** 删除标记 */
    private Integer deleteFlag;

    /******************* 页面显示,导出使用 ********************/
    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Excel.Type.EXPORT)
    private Date createTime;

    /** 创建人部门 */
    @Excel(name = "创建人部门")
    private String createDept;

    /** 创建人岗位 */
    @Excel(name = "创建人岗位")
    private String createPost;
    /******************* 页面显示,导出使用 ********************/

    /******************* 查询条件使用 ********************/
    /** 起始创建时间 */
    private String beginCreateTime;

    /** 结束创建时间 */
    private String endCreateTime;
    /******************* 查询条件使用 ********************/

}
