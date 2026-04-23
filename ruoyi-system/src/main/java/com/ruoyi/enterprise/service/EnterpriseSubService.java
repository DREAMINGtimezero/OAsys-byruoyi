package com.ruoyi.enterprise.service;

import com.ruoyi.enterprise.domain.EnterpriseSub;

import java.util.List;

public interface EnterpriseSubService {
    /**
     * 新增企业沟通流程
     * @param enterpriseSub 企业沟通流程
     * @return 结果
     */
    int insertEnterpriseSub(EnterpriseSub enterpriseSub);

    /**
     * 修改企业沟通流程
     * @param enterpriseSub 企业沟通流程
     * @return 结果
     */
    int updateEnterpriseSub(EnterpriseSub enterpriseSub);

    /**
     * 查询企业沟通流程列表
     * @param enterpriseSub 企业沟通流程
     * @return 企业沟通流程列表
     */
    List<EnterpriseSub> selectEnterpriseSubList(EnterpriseSub enterpriseSub);

    /**
     * 根据企业ID查询企业沟通流程列表
     * @param enterpriseId 企业ID
     * @return 企业沟通流程列表
     */
    List<EnterpriseSub> selectEnterpriseSubListByEnterpriseId(Long enterpriseId);

    /**
     * 删除企业沟通流程
     * @param ids 企业沟通流程主键集合
     * @return 结果
     */
    int deleteEnterpriseSubByIds(String ids);

    /**
     * 查询企业沟通流程
     * @param id 企业沟通流程主键
     * @return 企业沟通流程
     */
    EnterpriseSub selectEnterpriseSubById(Long id);
}