package com.ruoyi.software.mapper;

import java.util.List;

import com.ruoyi.software.domain.SoftwareProcess;


/**
 * 软件流程处理Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
public interface SoftwareProcessMapper
{
    /**
     * 查询软件流程处理
     * 
     * @param id 软件流程处理主键
     * @return 软件流程处理
     */
    public SoftwareProcess selectSoftwareProcessById(Long id);

    /**
     * 查询软件流程处理列表
     * 
     * @param softwareProcess 软件流程处理
     * @return 软件流程处理集合
     */
    public List<SoftwareProcess> selectSoftwareProcessList(SoftwareProcess softwareProcess);

    /**
     * 新增软件流程处理
     * 
     * @param softwareProcess 软件流程处理
     * @return 结果
     */
    public int insertSoftwareProcess(SoftwareProcess softwareProcess);

    /**
     * 修改软件流程处理
     * 
     * @param softwareProcess 软件流程处理
     * @return 结果
     */
    public int updateSoftwareProcess(SoftwareProcess softwareProcess);

    /**
     * 删除软件流程处理
     * 
     * @param id 软件流程处理主键
     * @return 结果
     */
    public int deleteSoftwareProcessById(Long id);

    /**
     * 批量删除软件流程处理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSoftwareProcessByIds(String[] ids);
}
