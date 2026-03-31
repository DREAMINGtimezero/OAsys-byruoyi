package com.ruoyi.software.mapper;

import java.util.List;
import com.ruoyi.software.domain.Software;

/**
 * 软件Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
public interface SoftwareMapper {
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
     * 删除软件
     * 
     * @param id 软件主键
     * @return 结果
     */
    public int deleteSoftwareById(Long id);

    /**
     * 批量删除软件
     * 
     * @param software
     * @return 结果
     */
    public int deleteSoftwareByIds(Software software);
}
