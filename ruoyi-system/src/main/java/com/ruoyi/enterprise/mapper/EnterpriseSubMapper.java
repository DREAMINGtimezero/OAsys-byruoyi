package com.ruoyi.enterprise.mapper;

import com.ruoyi.enterprise.domain.EnterpriseSub;

import java.util.List;

public interface EnterpriseSubMapper {
    /**
     * 新增企业沟通流程
     *
     * @param enterpriseSub 企业沟通流程
     * @return 结果
     */
    public int insertEnterpriseSub(EnterpriseSub enterpriseSub);

    /**
     * 更新企业沟通流程
     *
     * @param enterpriseSub 企业沟通流程
     * @return 结果
     */
    public int updateEnterpriseSub(EnterpriseSub enterpriseSub);

    /**
     * 删除企业沟通流程
     *
     * @param id 企业沟通流程主键
     * @return 结果
     */
    public int deleteEnterpriseSubById(Long id);

    /**
     * 批量删除企业沟通流程
     *
     * @param ids 需要删除的企业沟通流程主键
     * @return 结果
     */
    public int deleteEnterpriseSubByIds(String[] ids);

    /**
     * 查询企业沟通流程
     *
     * @param id 企业沟通流程主键
     * @return 企业沟通流程
     */
    public EnterpriseSub selectEnterpriseSubById(Long id);

    /**
     * 查询企业沟通流程列表
     *
     * @param enterpriseSub 企业沟通流程
     * @return 企业沟通流程集合
     */
    public List<EnterpriseSub> selectEnterpriseSubList(EnterpriseSub enterpriseSub);

    /**
     * 根据企业ID查询企业沟通流程列表
     *
     * @param enterpriseId 企业ID
     * @return 企业沟通流程集合
     */
    public List<EnterpriseSub> selectEnterpriseSubListByEnterpriseId(Long enterpriseId);
}