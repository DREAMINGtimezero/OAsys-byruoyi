package com.ruoyi.software.service;

import com.ruoyi.software.domain.Software;
import com.ruoyi.software.domain.SoftwareProcess;

import java.util.List;


/**
 * 软件流程处理Service接口
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
public interface SoftwareProcessService {
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
     * 批量删除软件流程处理
     * 
     * @param ids 需要删除的软件流程处理主键集合
     * @return 结果
     */
    public int deleteSoftwareProcessByIds(String ids);

    /**
     * 删除软件流程处理信息
     * 
     * @param id 软件流程处理主键
     * @return 结果
     */
    public int deleteSoftwareProcessById(Long id);

    /**
     * 发起流程
     * @param softwareProcess
     * @return
     */
    int initiateSoftwareProcess(SoftwareProcess softwareProcess);

    /**
     * 处理流程
     * @param softwareProcess
     * @return
     */
    int handleSoftwareProcess(SoftwareProcess softwareProcess);

    /**
     * 获取流程文件
     * @param id
     * @return
     */
    String getProcessFiles(Long id);
}
