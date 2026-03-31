package com.ruoyi.pleasemoney.mapper;

import com.ruoyi.pleasemoney.domain.PleaseMoneyConfig;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSub;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSun;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description 请款配置mapper
 * @Author wangshilin
 * @Date 2025/7/4 09:15
 **/
@Mapper
public interface PleaseMoneyConfigMapper {
    /**
     * 查询请款配置主列表
     *
     * @param pleaseMoneyConfig 请款配置主
     * @return 请款配置主集合
     */
    public List<PleaseMoneyConfig> selectPleaseMoneyConfigList(PleaseMoneyConfig pleaseMoneyConfig);

     /**
     * 查询请款配置子
     *
     * @param pleaseMoneyConfigSub 请款配置子
     * @return 结果
     */
    public List<PleaseMoneyConfigSub> selectPleaseMoneyConfigSubList(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 查询请款配置孙表 --通过子表id查询孙表数据，通过二层id查三层数据
     *
     * @param pleaseMoneyConfigSun
     * @return 结果
     */
    public List<PleaseMoneyConfigSun> selectSunListByPleaseMoneyConfigSubId(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 查询请款配置孙
     *
     * @param pleaseMoneyConfigSun
     * @return 结果
     */
    public List<PleaseMoneyConfigSun> selectPleaseMoneyConfigSunList(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 查询请款配置孙表 --通过孙表id查询孙表数据，通过自身id查数据
     *
     * @param
     * @return 结果
     */
    public PleaseMoneyConfigSun selectPleaseMoneyConfigSunById(Long id);

    /**
     * 查询三表的状态为1或2的数量通过子表id
     *
     * @param
     * @return 结果
     */
    public int selectSunStatusSum(Long id);

    /**
     * 新增请款配置主
     *
     * @param pleaseMoneyConfig 请款配置主
     * @return 结果
     */
    public int insertPleaseMoneyConfig(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 新增请款配置子
     * @param pleaseMoneyConfigSub
     * @return 结果
     */
    int insertPleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 新增请款配置孙
     * @param pleaseMoneyConfigSun
     * @return 结果
     */
    int insertPleaseMoneyConfigSun(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 修改请款配置主
     *
     * @param pleaseMoneyConfig 请款配置主
     * @return 结果
     */
    public int updatePleaseMoneyConfig(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 修改请款配置子
     * @param pleaseMoneyConfigSub 请款配置子
     * @return 结果
     */
    public int updatePleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 修改请款配置孙
     * @param pleaseMoneyConfigSun
     * @return 结果
     */
    public int updatePleaseMoneyConfigSun(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 批量删除请款配置主
     * @param pleaseMoneyConfig
     * @return
     */
    public int deletePleaseMoneyConfigByIds(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 批量删除请款配置子
     * @param pleaseMoneyConfigSub
     * @return
     */
    public int deletePleaseMoneyConfigSubByIds(PleaseMoneyConfigSub pleaseMoneyConfigSub);
}
