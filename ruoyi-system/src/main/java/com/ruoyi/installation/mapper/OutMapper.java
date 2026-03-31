package com.ruoyi.installation.mapper;

import com.ruoyi.installation.domain.Out;
import com.ruoyi.installation.domain.OutChild;
import org.apache.ibatis.annotations.Param;

import javax.xml.crypto.Data;
import java.util.List;

//差旅费用申请Mapper接口
public interface OutMapper {
    //主页面查询功能
    public List<Out> selectOutList(Out out);

    //主表id查主表
    public Out selectOutById(Long id);

    //添加差旅费用申请
    public int insertOut(Out out);

    //修改差旅费用申请
    public int updateOut(Out out);

    //删除差旅费用申请
    public int deleteOutByIds(Out out);



    /**************************   子表   ************************/
    //查询子表通过主ID
    public List<OutChild> findOutChildByOutId(OutChild outChild);

    //查询子表通过子ID
    public OutChild selectChildById(Long id);

    //查询子表
    public List<OutChild> selectChildList(OutChild outChild);

    //添加差旅费用申请同时添加子项的数据
    public int insertChild(OutChild outChild);

    //修改子表
    public int updateChild(OutChild outChild);
}
