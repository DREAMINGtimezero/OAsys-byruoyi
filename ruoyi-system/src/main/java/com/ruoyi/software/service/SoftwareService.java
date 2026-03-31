package com.ruoyi.software.service;

import com.ruoyi.software.domain.Software;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 软件Service接口
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
public interface SoftwareService {
    /**
     * 查询软件
     * 
     * @param id 软件主键
     * @return 软件
     */
    public Software selectSoftwareById(Long id);

    /**
     * 查询软件列表
     * 
     * @param software 软件
     * @return 软件集合
     */
    public List<Software> selectSoftwareList(Software software);

    /**
     * 新增软件
     * 
     * @param software 软件
     * @return 结果
     */
    public int insertSoftware(Software software);

    /**
     * 修改软件
     * 
     * @param software 软件
     * @return 结果
     */
    public int updateSoftware(Software software);

    /**
     * 批量删除软件
     * 
     * @param software 需要删除的软件主键集合
     * @return 结果
     */
    public int deleteSoftwareByIds(Software software);

    /**
     * 删除软件信息
     * 
     * @param id 软件主键
     * @return 结果
     */
    public int deleteSoftwareById(Long id);

    /**
     * 结束软件流程
     *
     * @param software
     * @return
     */
    int finishSoftware(Software software);
}
