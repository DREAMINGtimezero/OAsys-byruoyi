package com.ruoyi.software.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.software.domain.Software;
import com.ruoyi.software.domain.SoftwareProcess;
import com.ruoyi.software.mapper.SoftwareMapper;
import com.ruoyi.software.mapper.SoftwareProcessMapper;
import com.ruoyi.software.service.SoftwareProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 软件流程处理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-09
 */
@Service
public class SoftwareProcessServiceImpl extends BaseServiceImpl implements SoftwareProcessService {
    @Autowired
    private SoftwareProcessMapper softwareProcessMapper;

    @Autowired
    private SoftwareMapper softwareMapper;

    @Override
    public SoftwareProcess selectSoftwareProcessById(Long id) {
        SoftwareProcess process = softwareProcessMapper.selectSoftwareProcessById(id);
        List<SoftwareProcess> list = new ArrayList<>(1);
        list.add(process);
//        createByReplace(list);
        return process;
    }

    @Override
    public List<SoftwareProcess> selectSoftwareProcessList(SoftwareProcess softwareProcess) {
        List<SoftwareProcess> list =softwareProcessMapper.selectSoftwareProcessList(softwareProcess);
        createByReplace(list);
        handleByReplace(list);
        return list;
    }

    @Override
    public int insertSoftwareProcess(SoftwareProcess softwareProcess) {
        //检查主项是否结束
        Software software = softwareMapper.selectSoftwareById(softwareProcess.getSoftwareId());
        if(software.getStatus().equals(Integer.valueOf(Constants.MainStatus.COMPLETE)))
        {
            throw new RuntimeException("流程已结束");
        }
        //检查是否有未提交项
        SoftwareProcess conditions = new SoftwareProcess();
        conditions.setSoftwareId(softwareProcess.getSoftwareId());
        conditions.setStatus(Constants.SubStatus.UNSUBMIT);
        if(!softwareProcessMapper.selectSoftwareProcessList(conditions).isEmpty())
        {
            throw new RuntimeException("存在未提交的流程");
        }
        conditions.setStatus(Constants.SubStatus.PROCESSING);
        if(!softwareProcessMapper.selectSoftwareProcessList(conditions).isEmpty())
        {
            throw new RuntimeException("存在待处理的流程");
        }
        //检查是否有未处理的项
        return softwareProcessMapper.insertSoftwareProcess((SoftwareProcess)initDomainCreate(softwareProcess));
    }

    @Override
    public int updateSoftwareProcess(SoftwareProcess softwareProcess) {
        softwareProcess.setUpdateTime(DateUtils.getNowDate());
        return softwareProcessMapper.updateSoftwareProcess(softwareProcess);
    }

    @Override
    public int deleteSoftwareProcessByIds(String ids) {
        return softwareProcessMapper.deleteSoftwareProcessByIds(Convert.toStrArray(ids));
    }

    @Override
    public int deleteSoftwareProcessById(Long id) {
        return softwareProcessMapper.deleteSoftwareProcessById(id);
    }

    @Override
    @Transactional
    public int initiateSoftwareProcess(SoftwareProcess softwareProcess) {
        if(softwareProcessMapper.updateSoftwareProcess((SoftwareProcess)initDomainUpdate(softwareProcess)) > Constants.ZERO){
            softwareProcess.setInitiateTime(DateUtils.getNowDate());
            softwareProcess.setStatus(Constants.TWO);
            // 更新相关的软件状态
             Software software = new Software();
             software.setStatus(Constants.TWO);
             software.setId(softwareProcess.getSoftwareId());
             softwareMapper.updateSoftware(software);
            return softwareProcessMapper.updateSoftwareProcess((SoftwareProcess)initDomainUpdate(softwareProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public int handleSoftwareProcess(SoftwareProcess softwareProcess) {
        if(softwareProcessMapper.updateSoftwareProcess((SoftwareProcess)initDomainUpdate(softwareProcess)) > Constants.ZERO) {
            softwareProcess.setHandleTime(DateUtils.getNowDate());
            return softwareProcessMapper.updateSoftwareProcess((SoftwareProcess)initDomainUpdate(softwareProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public String getProcessFiles(Long id) {
        SoftwareProcess softwareProcess = softwareProcessMapper.selectSoftwareProcessById(id);
        return softwareProcess.getFileNames();
    }
}
