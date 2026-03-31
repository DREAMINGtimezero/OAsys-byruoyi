package com.ruoyi.pleasemoney.service;

import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfig;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSub;
import com.ruoyi.pleasemoney.domain.PleaseMoneyConfigSun;

import java.util.List;

/**
 * @Description 请款配置service
 * @Author wangshilin
 * @Date 2025/7/4 09:15
 **/
public interface PleaseMoneyConfigService{

    /**
     * 查询请款配置主列表
     *
     * @param pleaseMoneyConfig 请款配置主
     * @return 请款配置主集合
     */
    public List<PleaseMoneyConfig> selectPleaseMoneyConfigList(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 查询请款配置子表
     *
     * @param pleaseMoneyConfigSub 请款配置主键
     * @return 结果
     */
    public List<PleaseMoneyConfigSub> selectPleaseMoneyConfigSubList(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 查询请款配置孙表 --通过子表id查询孙表数据，通过二层id差三层数据
     *
     * @param pleaseMoneyConfigSun 请款配置子
     * @return 结果
     */
    public List<PleaseMoneyConfigSun> selectSunListByPleaseMoneyConfigSubId(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 查询请款配置孙
     *
     * @param pleaseMoneyConfigSun 请款配置子
     * @return 结果
     */
    public List<PleaseMoneyConfigSun> selectPleaseMoneyConfigSunList(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 新增请款配置主
     *
     * @param pleaseMoneyConfig 请款配置主
     * @return 结果
     */
    public int insertPleaseMoneyConfig(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 新增请款配置子
     *
     * @param pleaseMoneyConfigSub 请款配置子
     * @return 结果
     */
    public int insertPleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub);

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
     *
     * @param pleaseMoneyConfigSub 需要删除的请款配置子ID
     * @return 结果
     */
    public int updatePleaseMoneyConfigSub(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 批量删除请款配置主
     *
     * @param pleaseMoneyConfig 需要删除的请款配置主主键集合
     * @return 结果
     */
    public int deletePleaseMoneyConfigByIds(PleaseMoneyConfig pleaseMoneyConfig);

    /**
     * 批量删除请款配置子
     *
     * @param pleaseMoneyConfigSub 需要删除的请款配置子主键集合
     * @return 结果
     */
    public int deletePleaseMoneyConfigSubByIds(PleaseMoneyConfigSub pleaseMoneyConfigSub);


    /**
     * 孙表提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
     *
     * @param pleaseMoneyConfigSun 需要删除的请款配置子主键集合
     * @return 结果
     */
    public int PleaseMoneyConfigSunSubmit(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 孙项处理功能，添加处理意见，处理时间,修改三表状态为 3或 4,通过主ID
     *
     * @param pleaseMoneyConfigSun 需要删除的请款配置子主键集合
     * @return 结果
     */
    public int PleaseMoneyConfigSunHandle(PleaseMoneyConfigSun pleaseMoneyConfigSun);

    /**
     * 孙项结束功能，修改二表状态为 3,通过主ID
     *
     * @param pleaseMoneyConfigSub 需要删除的请款配置子主键集合
     * @return 结果
     */
    public int PleaseMoneyConfigSubFinish(PleaseMoneyConfigSub pleaseMoneyConfigSub);

    /**
     * 孙项查询中的文件下载
     *
     * @param
     * @return 结果
     */
    public String PleaseMoneyConfigSunFiles(Long id);

    /**
     * 请款主项-完成
     *
     * @return
     */
    public int configComplete(PleaseMoneyConfig pleaseMoneyConfig);
}
