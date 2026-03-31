package com.ruoyi.project.service;

import com.ruoyi.project.domain.ProjectStatis;

import java.util.List;

/**
 * @Description  项目统计service
 * @Author wangshilin
 * @Date 2025/6/24 16:01
 **/
public interface ProjectStatisService {

    /**
     * 查询项目统计列表
     *
     * @param tProjectStatis 项目统计
     * @return 项目统计集合
     */
    public List<ProjectStatis> selectProjectStatisList(ProjectStatis tProjectStatis);

    /**
     * 新增项目统计
     *
     * @param projectStatis 项目统计
     * @return 结果
     */
    public int insertProjectStatis(ProjectStatis projectStatis);

    /**
     * 修改项目统计
     *
     * @param projectStatis 项目统计
     * @return 结果
     */
    public int updateProjectStatis(ProjectStatis projectStatis);

    /**
     * 批量删除项目统计信息
     *
     * @param projectStatis 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectStatisByIds(ProjectStatis projectStatis);
}
