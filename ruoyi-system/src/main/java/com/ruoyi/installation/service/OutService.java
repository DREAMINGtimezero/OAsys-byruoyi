package com.ruoyi.installation.service;

import com.ruoyi.installation.domain.Out;
import com.ruoyi.installation.domain.OutChild;

import java.util.List;

//差旅费用申请Service接口
public interface OutService {
    //主页面查询功能
    public List<Out> selectOutList(Out out);

    //添加差旅费用申请
    public int insertOut(Out out);

    //修改差旅费用申请
    public int updateOut(Out out);

    //删除差旅费用申请
    public int deleteOutByIds(Out out);


    /**************************   子表   ************************/
    //查询子表通过主表ID
    public List<OutChild> findOutChildByOutId(OutChild outChild);

    //查询子表通过子ID
    public OutChild selectChildById(Long id);

    //查询子表
    public List<OutChild> selectChildList(OutChild outChild);

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    public int commit(OutChild outChild);

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3,通过主ID
    public int handle(OutChild outChild);

    //子项查询中的文件下载
    public String outFiles(Long id);
}
