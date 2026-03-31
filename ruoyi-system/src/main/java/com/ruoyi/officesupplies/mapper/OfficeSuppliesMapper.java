package com.ruoyi.officesupplies.mapper;
import java.util.List;
import com.ruoyi.officesupplies.domain.OfficeSupplies;
import com.ruoyi.officesupplies.domain.OfficeSuppliesSub;

/**
 * 办公用品Mapper接口
 *
 * @author gyx
 * @date 2025-08-06
 */
public interface OfficeSuppliesMapper {
    /**
     * 查询办公用品主表
     *
     * @param id 办公用品主表主键
     * @return 办公用品主表
     */
    public OfficeSupplies selectOfficeSuppliesById(Long id);



    public List<OfficeSupplies> selectOfficeSuppliesByIds(List<Long> ids);

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
     * 删除办公用品主表
     *
     * @param id 办公用品主表主键
     * @return 结果
     */
    public int deleteOfficeSuppliesById(Long id);

    /**
     * 批量删除办公用品主表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOfficeSuppliesByIds(String[] ids);


    //以下是子表的mapper接口

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
     * 修改办公用品子
     *
     * @param officeSuppliesSub 办公用品子
     * @return 结果
     */
    public int updateOfficeSuppliesSub(OfficeSuppliesSub officeSuppliesSub);

    /**
     * 删除办公用品子
     *
     * @param id 办公用品子主键
     * @return 结果
     */
    public int deleteOfficeSuppliesSubById(Long id);

    /**
     * 批量删除办公用品子
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOfficeSuppliesSubByIds(String[] ids);
}
