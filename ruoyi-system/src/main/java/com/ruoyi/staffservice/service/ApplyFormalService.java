package com.ruoyi.staffservice.service;

import com.ruoyi.staffservice.domain.ApplyFormal;
import com.ruoyi.staffservice.domain.ApplyFormalSub;

import java.util.List;

public interface ApplyFormalService {
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
     * 批量删除员工转正申请主
     *
     * @param applyFormal 需要删除的员工转正申请主主键集合
     * @return 结果
     */
    public int deleteApplyFormalByIds(ApplyFormal applyFormal);

    /**
     * 子表提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
     *
     * @param applyFormalSub 需要删除的请款配置子主键集合
     * @return 结果
     */
    public int applyFormalSubSubmit(ApplyFormalSub applyFormalSub);

    /**
     * 子项处理功能，添加处理意见，处理时间,修改子表状态为 3,通过主ID
     *
     * @param applyFormalSub
     * @return 结果
     */
    public int applyFormalSubHandle(ApplyFormalSub applyFormalSub);

    /**
     * 结束子项流程
     *
     * @param applyFormal
     * @return 结果
     */
    public int applyFormalFinish(ApplyFormal applyFormal);

}
