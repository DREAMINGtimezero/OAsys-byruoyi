package com.ruoyi.hardware.mapper;

import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.hardware.domain.HardwareChild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//硬件Mapper接口
public interface HardwareMapper {
    //主页面查询列表
    public List<Hardware> selectHardwareList(Hardware hardware);

    //主键id查询数据
    public Hardware selectHardwareById(Long id);

    //添加硬件数据
    public int insertHardware(Hardware hardware);

    //修改硬件数据
    public int updateHardware(Hardware hardware);

    //删除硬件数据
    public int deleteHardwareByIds(Hardware hardware);


    /****************************  子表   ****************************/
    //添加硬件采买同时添加子项的数据
    public int insertHardwareChild(HardwareChild hardwareChild);

    //通过主表的id查询子表数据
    public List<HardwareChild> findChildByHardwareId(HardwareChild hardwareChild);

    //通过子表id查询子表数据
    public HardwareChild selectChildById(Long id);

    //查询子项数据
    public List<HardwareChild> selectChildList(HardwareChild hardwareChild);

    //修改子项数据
    public int updateChild(HardwareChild hardwareChild);

    //查询子表的状态为1或2的数量通过主id
    public int selectChildStatusSum(Long id);
}
