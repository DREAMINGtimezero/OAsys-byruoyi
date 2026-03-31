package com.ruoyi.staffservice.mapper;

import com.ruoyi.staffservice.domain.ApplyFormal;
import com.ruoyi.staffservice.domain.ApplyFormalSub;

import java.util.List;

public interface ApplyFormalMapper {

    /**
     * 查询员工转正申请主
     *
     * @param id 员工转正申请主主键
     * @return 员工转正申请主
     */
    public ApplyFormal selectApplyFormalById(Long id);

    /**
     * 查询员工转正申请主列表
     *
     * @param applyFormal 员工转正申请主
     * @return 员工转正申请主集合
     */
    public List<ApplyFormal> selectApplyFormalList(ApplyFormal applyFormal);

    /**
     * 查询子表数据 通过主表的id
     *
     * @param applyFormalSub
     * @return 结果
     */
    public List<ApplyFormalSub> selectApplyFormalSubByApplyFormalId(ApplyFormalSub applyFormalSub);

    /**
     * 查询子项数据
     *
     * @param applyFormalSub
     * @return 批量结果
     */
    public List<ApplyFormalSub> selectApplyFormalSubList(ApplyFormalSub applyFormalSub);

    /**
     * 通过子表id查子项
     *
     * @param id
     * @return 结果
     */
    public ApplyFormalSub selectApplyFormalSubById(Long id);

    /**
     * 查询子表的状态为1或2的数量通过主id
     *
     * @param id
     * @return 批量结果
     */
    public int selectApplyFormalSubStatusSum(Long id);

    /**
     * 新增员工转正申请主
     *
     * @param applyFormal 员工转正申请主
     * @return 结果
     */
    public int insertApplyFormal(ApplyFormal applyFormal);

    /**
     * 新增员工转正申请子表数据
     *
     * @param applyFormalSub
     * @return 结果
     */
    public int insertApplyFormalSub(ApplyFormalSub applyFormalSub);

    /**
     * 修改员工转正申请主
     *
     * @param applyFormal 员工转正申请主
     * @return 结果
     */
    public int updateApplyFormal(ApplyFormal applyFormal);

    /**
     * 修改子项数据
     *
     * @param applyFormalSub
     * @return 结果
     */
    public int updateApplyFormalSub(ApplyFormalSub applyFormalSub);

    /**
     * 批量删除员工转正申请主
     *
     * @param applyFormal 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteApplyFormalByIds(ApplyFormal applyFormal);
}
