package com.ruoyi.enterprise.service;

import com.ruoyi.enterprise.domain.Enterprise;
import com.ruoyi.enterprise.domain.EnterpriseExport;
import java.util.List;

public interface EnterpriseService {
    /**
     * 新增企业
     * @param enterprise 企业
     * @return 结果
     */
    int insertEnterprise(Enterprise enterprise);

    /**
     * 修改企业
     * @param enterprise 企业
     * @return 结果
     */
    int updateEnterprise(Enterprise enterprise);

    /**
     * 查询企业列表
     * @param enterprise 企业
     * @return 企业列表
     */
    List<Enterprise> selectEnterpriseList(Enterprise enterprise);

    /**
     * 删除企业
     * @param ids 企业主键集合
     * @return 结果
     */
    int deleteEnterpriseByIds(String ids);

    /**
     * 修改开发状态
     * @param id 企业主键
     * @param devStatus 开发状态
     * @return 结果
     */
    int changeStatus(Long id, Integer devStatus);

    /**
     * 查询企业
     * @param id 企业主键
     * @return 企业
     */
    Enterprise selectEnterpriseById(Long id);

    /**
     * 导出企业列表
     * @param enterprise 企业
     * @return 企业导出列表
     */
    List<EnterpriseExport> selectEnterpriseExportList(Enterprise enterprise);

    /**
     * 导入企业数据
     * @param enterpriseList 企业数据列表
     * @param isUpdateSupport 是否支持更新
     * @param operName 操作人
     * @return 结果
     */
    String importEnterprise(List<Enterprise> enterpriseList, boolean isUpdateSupport, String operName);
}