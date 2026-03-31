package com.ruoyi.officesupplies.service;
import java.util.List;

import com.ruoyi.contract.domain.Contract;
import com.ruoyi.officesupplies.domain.OfficeSupplies;
import com.ruoyi.officesupplies.domain.OfficeSuppliesSub;

public interface OfficeSuppliesService {
    /**
     * 查询办公用品主表
     *
     * @param id 办公用品主表主键
     * @return 办公用品主表
     */
    public OfficeSupplies selectOfficeSuppliesById (Long id);

    /**
     * 查询办公用品主表列表
     *
     * @param officeSupplies 办公用品主表
     * @return 办公用品主表集合
     */
    public List<OfficeSupplies> selectOfficeSuppliesList(OfficeSupplies officeSupplies);

    /**
     * 新增办公用品主表
     *
     * @param officeSupplies 办公用品主表
     * @return 结果
     */
    public int insertOfficeSupplies(OfficeSupplies officeSupplies);

    /**
     * 修改办公用品主表
     *
     * @param officeSupplies 办公用品主表
     * @return 结果
     */
    public int updateOfficeSupplies(OfficeSupplies officeSupplies);

    /**
     * 批量删除办公用品主表
     *
     * @param ids 需要删除的办公用品主表主键集合
     * @return 结果
     */
    public int deleteOfficeSuppliesByIds(String ids);

    /**
     * 删除办公用品主表信息
     *
     * @param id 办公用品主表主键
     * @return 结果
     */
    public int deleteOfficeSuppliesById(Long id);

//以下是子表的接口！！！！

    /**
     * 查询办公用品子
     *
     * @param id 办公用品子主键
     * @return 办公用品子
     */
    public OfficeSuppliesSub selectOfficeSuppliesSubById(Long id);

    /**
     * 查询办公用品子列表
     *
     * @param officeSuppliesSub 办公用品子
     * @return 办公用品子集合
     */
    public List<OfficeSuppliesSub> selectOfficeSuppliesSubList(OfficeSuppliesSub officeSuppliesSub);

    /**
     * 新增办公用品子
     *
     * @param officeSuppliesSub 办公用品子
     * @return 结果
     */
    public int insertOfficeSuppliesSub(OfficeSuppliesSub officeSuppliesSub);

    /**
     * 结束流程
     */
    public int finishOfficeSupplies(OfficeSupplies officeSupplies);



    /**
     * 修改办公用品子
     *
     * @param officeSuppliesSub 办公用品子
     * @return 结果
     */
    public int updateOfficeSuppliesSub(OfficeSuppliesSub officeSuppliesSub);

    /**
     * 检查是否可以修改
     * @param id 主项id
     * @return 结果
     */
    boolean editAble(Long id);

    /*
    *提交发起流程
     */
    public int submitsub(OfficeSuppliesSub officeSuppliesSub);

    /*
     *处理起流程
     */
    public int subhandle(OfficeSuppliesSub officeSuppliesSub);


    /*
    *文件名查询
    */

    public String getsubFiles(Long id);



    /**
     * 批量删除办公用品子
     *
     * @param ids 需要删除的办公用品子主键集合
     * @return 结果
     */
    public int deleteOfficeSuppliesSubByIds(String ids);

    /**
     * 删除办公用品子信息
     *
     * @param id 办公用品子主键
     * @return 结果
     */
    public int deleteOfficeSuppliesSubById(Long id);
}
