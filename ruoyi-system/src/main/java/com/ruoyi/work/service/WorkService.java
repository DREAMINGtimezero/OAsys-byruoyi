package com.ruoyi.work.service;

import com.ruoyi.work.domain.Work;

import java.util.List;

public interface WorkService {
    /**
     * 查询考勤表
     *
     * @param work
     * @return
     */
    public List<Work> selectWorkList(Work work);

    /**
     * 查询考勤表通过id
     *
     * @param id
     * @return
     */
    public Work selectWorkById(Long id);

    /**
     * 新增考勤表
     *
     * @param work
     * @return
     */
    public int insertWork(Work work);

    /**
     * 修改考勤表
     *
     * @param work
     * @return
     */
    public int updateWork(Work work);

    /**
     * 删除考勤表
     *
     * @param work
     * @return
     */
    public int deleteWorkByIds(Work work);
}
