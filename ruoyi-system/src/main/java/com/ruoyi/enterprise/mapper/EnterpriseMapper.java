package com.ruoyi.enterprise.mapper;

import com.ruoyi.enterprise.domain.Enterprise;
import com.ruoyi.enterprise.domain.EnterpriseExport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EnterpriseMapper {
    /**
     * 新增企业
     *
     * @param enterprise 企业
     * @return 结果
     */
    public int insertEnterprise(Enterprise enterprise);

    /**
     * 更新企业
     *
     * @param enterprise 企业
     * @return 结果
     */
    public int updateEnterprise(Enterprise enterprise);

    /**
     * 删除企业
     *
     * @param id 企业主键
     * @return 结果
     */
    public int deleteEnterpriseById(Long id);

    /**
     * 批量删除企业
     *
     * @param ids 需要删除的企业主键
     * @return 结果
     */
    public int deleteEnterpriseByIds(String[] ids);

    /**
     * 查询企业
     *
     * @param id 企业主键
     * @return 企业
     */
    public Enterprise selectEnterpriseById(Long id);

    /**
     * 查询企业列表
     *
     * @param enterprise 企业
     * @return 企业集合
     */
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise);

    /**
     * 导出企业列表
     *
     * @param enterprise 企业
     * @return 企业集合
     */
    public List<EnterpriseExport> selectEnterpriseExportList(Enterprise enterprise);
}