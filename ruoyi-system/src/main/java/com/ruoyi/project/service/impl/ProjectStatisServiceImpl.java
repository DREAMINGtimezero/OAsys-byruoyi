package com.ruoyi.project.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.ProjectStatis;
import com.ruoyi.project.mapper.ProjectStatisMapper;
import com.ruoyi.project.service.ProjectStatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @Description  项目统计impl
 * @Author wangshilin
 * @Date 2025/6/24 16:01
 **/
@Service
public class ProjectStatisServiceImpl extends BaseServiceImpl implements ProjectStatisService {

    @Autowired
    private ProjectStatisMapper projectStatisMapper;

    @Override
    public List<ProjectStatis> selectProjectStatisList(ProjectStatis projectStatis) {
        List<ProjectStatis> projectStatisList = projectStatisMapper.selectProjectStatisList(
            (ProjectStatis) initDomainCreate(projectStatis));
        createByReplace(projectStatisList);
        return projectStatisList;
    }

    @Override
    public int insertProjectStatis(ProjectStatis projectStatis) {
        // 项目编号由系统生成
        projectStatis.setProjectCode(Constants.PROJECT_CODE_FIX + DateUtils.dateTimeNow(DateUtils.YYYYMMDDHHMMSSSSS));
        return projectStatisMapper.insertProjectStatis((ProjectStatis)initDomainCreate(projectStatis));
    }

    @Override
    public int updateProjectStatis(ProjectStatis projectStatis) {
        if (ObjectUtils.isEmpty(projectStatis.getStatus()) || projectStatis.getStatus() != Constants.THREE){
            checkProjectStatis(projectStatis.getId());
        }
        return projectStatisMapper.updateProjectStatis((ProjectStatis)initDomainUpdate(projectStatis));
    }

    @Override
    public int deleteProjectStatisByIds(ProjectStatis projectStatis) {
        for (Long id : projectStatis.getIds()) {
            checkProjectStatis(id);
        }
        return projectStatisMapper.deleteProjectStatisByIds((ProjectStatis)initDomainUpdate(projectStatis));
    }

    // 根据项目id判断当前项目是否是未提交，只有未提交的才可以操作
    public void checkProjectStatis(Long id) {
         // 根据项目号查询项目
        ProjectStatis projectStatis = projectStatisMapper.selectProjectStatisById(id);
        if (projectStatis == null) {
            throw new RuntimeException("项目不存在!");
        }
        if (projectStatis.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作状态是'未提交'的项目!");
        }
    }

}
