package com.ruoyi.hardware.service;

import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import com.ruoyi.installation.domain.OutChild;

import java.util.List;

public interface HardwareService {
    //主页面查询功能
    public List<Hardware> selectHardwareList(Hardware hardware);

    //添加硬件
    public int insertHardware(Hardware hardware);

    //修改硬件数据
    public int updateHardware(Hardware hardware);

    //删除硬件数据
    public int deleteHardwareByIds(Hardware hardware);

    //子项流程结束,修改主项状态3
    public int hardwareFinish(Hardware hardware);

    /****************************  子表   ****************************/
    //添加硬件采买同时添加子项的数据
    public int insertHardwareChild(HardwareChild hardwareChild);

    //通过主表的id查询子表数据
    public List<HardwareChild> findChildByHardwareId(HardwareChild hardwareChild);

    //通过子表id查询子表数据
    public HardwareChild selectChildById(Long id);

    //查询子项数据
    public List<HardwareChild> selectChildList(HardwareChild hardwareChild);

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    public int commit(HardwareChild hardwareChild);

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3,通过主ID
    public int handle(HardwareChild hardwareChild);

    //子项查询中的文件下载
    public String hardwareFiles(Long id);
}
