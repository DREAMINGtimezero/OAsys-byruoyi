package com.ruoyi.installation.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.hardware.domain.Hardware;
import com.ruoyi.installation.domain.Out;
import com.ruoyi.installation.domain.OutChild;
import com.ruoyi.installation.mapper.OutMapper;
import com.ruoyi.installation.service.OutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
//差旅费用申请Service业务层处理   实现类
public class OutServiceImpl extends BaseServiceImpl implements OutService {
    @Autowired
    private OutMapper outMapper;

    //主页面查询功能
    @Override
    public List<Out> selectOutList(Out out) {
        List<Out> outs = outMapper.selectOutList((Out) initDomainCreate(out));
        createByReplace(outs);
        return outs;
    }

    //添加差旅费
    @Override
    @Transactional
    public int insertOut(Out out) {
        out.setOutCode(Constants.OUT_CODE_FIX+DateUtils.dateTimeNow(DateUtils.YYYYMMDDHHMMSSSSS));
        //判断影响行数是否大于0
        if (outMapper.insertOut((Out) initDomainCreate(out)) > Constants.ZERO){
            OutChild outChild = new OutChild();
            outChild.setOutId(out.getId());//把主表ID拿给子表用主键ID
            return  outMapper.insertChild((OutChild)initDomainCreate(outChild));
        }
        return Constants.ZERO;
    }

    //修改差旅费
    @Override
    public int updateOut(Out out) {
        outId(out.getId());
        out.setUpdateTime(DateUtils.getNowDate());
        return outMapper.updateOut((Out)initDomainUpdate(out));
    }

    //删除差旅费用申请
    @Override
    public int deleteOutByIds(Out out) {
        for (Long ids : out.getIds()){
            outId(ids);
        }
        return outMapper.deleteOutByIds((Out)initDomainUpdate(out));
    }
    private void outId(Long id) {
        Out out = outMapper.selectOutById(id);
        if (out == null) {
            throw new RuntimeException("差旅申请不存在!");
        }
        if (out.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作'未提交'状态的差旅申请!");
        }
    }



    /*******************************   子表   *****************************/
    //查询子表通过主表ID
    @Override
    public List<OutChild> findOutChildByOutId(OutChild outChild) {
        List<OutChild> outChildByOutId = outMapper.findOutChildByOutId((OutChild)initDomainCreate (outChild));
        createByReplace(outChildByOutId);
        handleByReplace(outChildByOutId);
        return outChildByOutId;
    }

    @Override
    public OutChild selectChildById(Long id) {
        return outMapper.selectChildById(id);
    }

    @Override
    public List<OutChild> selectChildList(OutChild outChild) {
        List<OutChild> outChildren = outMapper.selectChildList((OutChild)initDomainCreate (outChild));
        createByReplace(outChildren);
        handleByReplace(outChildren);
        return outChildren;
    }

    //子项提交功能，添加发起意见，处理人，文件，发起时间,修改主子表状态为 2,通过主ID
    @Override
    @Transactional
    public int commit(OutChild outChild) {
        if(outMapper.updateChild((OutChild) initDomainUpdate(outChild)) > Constants.ZERO){
            outChild.setInitiateTime(DateUtils.getNowDate());
            outChild.setStatus(Constants.TWO);
            Out out = new Out();
            out.setId(outChild.getOutId());
            out.setStatus(Constants.TWO);
            outMapper.updateOut(out);
            return outMapper.updateChild((OutChild) initDomainUpdate(outChild));
        }
        return Constants.ZERO;
    }

    //子项处理功能，添加处理意见，处理时间,修改主子表状态为 3或 4,通过主ID
    @Override
    @Transactional
    public int handle(OutChild outChild) {
        if(outMapper.updateChild((OutChild) initDomainUpdate(outChild)) > Constants.ZERO) {
            outChild.setHandleTime(DateUtils.getNowDate());
            Out out = new Out();
            out.setId(outChild.getOutId());
            out.setStatus(Constants.THREE);
            outMapper.updateOut(out);
            return outMapper.updateChild((OutChild) initDomainUpdate(outChild));
        }
        return Constants.ZERO;
    }

    //子项查询中的文件下载
    @Override
    public String outFiles(Long id) {
        OutChild outChild = outMapper.selectChildById(id);
        return outChild.getFileNames();
    }
}
