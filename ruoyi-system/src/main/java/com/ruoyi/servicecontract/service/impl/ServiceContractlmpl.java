package com.ruoyi.servicecontract.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.officesupplies.domain.OfficeSupplies;
import com.ruoyi.servicecontract.domain.ServiceContract;
import com.ruoyi.servicecontract.domain.ServiceContractProcess;
import com.ruoyi.servicecontract.mapper.ServiceContractMapper;
import com.ruoyi.servicecontract.service.ServiceContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Service
public class ServiceContractlmpl extends BaseServiceImpl implements ServiceContractService {
    @Autowired
    private ServiceContractMapper serviceContractMapper;

    @Override
    public boolean editAble(Long id) {
        //只能编辑未提交的项
        ServiceContract mainlist = selectServiceContractById(id);
        return mainlist.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
    }
    @Override
    public ServiceContract selectServiceContractById(Long id) {
        return serviceContractMapper.selectServiceContractById(id);
    }

    @Override
    public List<ServiceContract> selectServiceContractList(ServiceContract serviceContract) {
        List<ServiceContract> contracts = serviceContractMapper.selectServiceContractList((ServiceContract) initDomainCreate(serviceContract));
        createByReplace(contracts);
        updateByReplace(contracts);
        return contracts;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertServiceContract(ServiceContract serviceContract) {
        serviceContract = (ServiceContract) initDomainCreate(serviceContract);
        serviceContract.setCreateTime(DateUtils.getNowDate());
        serviceContract.setUpdateTime(DateUtils.getNowDate());
        serviceContract.setStatus(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
        int result = serviceContractMapper.insertServiceContract(serviceContract);
        ServiceContractProcess contractProcess = new ServiceContractProcess();
        contractProcess.setServiceContractId(serviceContract.getId());
        BeanUtils.copyProperties(serviceContract, contractProcess);
        contractProcess.setInitiateTime(DateUtils.getNowDate());
        return result + serviceContractMapper.insertServiceContractProcess((ServiceContractProcess) initDomainCreate(contractProcess));
    }

    @Override
    public int updateServiceContract(ServiceContract serviceContract) {
        serviceContract.setUpdateTime(DateUtils.getNowDate());
        return serviceContractMapper.updateServiceContract((ServiceContract) initDomainUpdate(serviceContract));
    }

    @Override
    public int deleteServiceContractByIds(String ids) {

        SysUser sysUser =new SysUser();
        Long[] idList = Convert.toLongArray(ids);
        for (Long id : idList) {
            ServiceContract serviceContract = selectServiceContractById(id);
            if (serviceContract == null) {
                throw new RuntimeException("服务合同不存在! " );
            }
            if (!serviceContract.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT))) {
                throw new RuntimeException("操作失败！只能操作'未提交'状态的服务合同! " );
            }

            else {
                initDomainUpdate(serviceContract);
                serviceContractMapper.updateServiceContract(serviceContract);
            }
        }
        int result=serviceContractMapper.deleteServiceContractByIds(Convert.toStrArray(ids));
        return result+serviceContractMapper.deleteServiceContractProcessByIds(Convert.toStrArray(ids));

    }


    @Override
    public List<ServiceContractProcess> selectServiceContractProcessList(ServiceContractProcess serviceContractProcess) {
        List<ServiceContractProcess> contractProcesses = serviceContractMapper.selectServiceContractProcessList(serviceContractProcess);
        for (ServiceContractProcess item : contractProcesses) {
            System.out.println("创建者: " + item.getCreateBy());}

        createByReplace(contractProcesses);
        handleByReplace(contractProcesses);
        filterMainForHandle(contractProcesses);
        return contractProcesses;
    }

    public void filterMainForHandle(List<? extends BaseEntity> list) {
        if (list == null || list.isEmpty()) {
            return;
        }

        String loginName = ShiroUtils.getSysUser().getUserName();
        //System.out.println("登录用户: " + loginName);
        Iterator<?> it = list.iterator();
        while (it.hasNext()) {
            Object data = it.next();
            try {
                // 将对象转换为BaseEntity并使用getter方法获取createBy
                BaseEntity baseEntity = (BaseEntity) data;
                String createBy = baseEntity.getCreateBy();
                //System.out.println("创建者2: " + createBy);
                if (createBy == null) {
                    throw new NullPointerException("createBy 字段为空");
                }

                // 获取 status
                Field statusField = data.getClass().getDeclaredField("status");
                statusField.setAccessible(true);
                Object statusObj = statusField.get(data);
                if (statusObj == null) {
                    throw new NullPointerException("status 字段为空");
                }
                String status = statusObj.toString();
                System.out.println("状态: " + status);
                // 数据还未提交，且当前登录用户不是创建者
                if (Constants.MainStatus.UNSUBMIT.equals(status) && !loginName.equals(createBy)) {
                    it.remove();
                }
            } catch (NullPointerException e) {
                System.err.println("空指针异常: " + e.getMessage());
                throw e;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertServiceContractProcess(ServiceContractProcess serviceContractProcess) {
        ServiceContract contract = serviceContractMapper.selectServiceContractById(serviceContractProcess.getServiceContractId());
        //检查主项是否已经完成
        if (contract.getStatus().equals(Integer.valueOf(Constants.MainStatus.COMPLETE))) {
            throw new RuntimeException("服务合同已经完成，不能进行操作！");
        }
        ServiceContractProcess conditions = new ServiceContractProcess();
        //设置关联id
        conditions.setServiceContractId(serviceContractProcess.getServiceContractId());
        //检查是否有未提交的
        conditions.setStatus(Constants.SubStatus.UNSUBMIT);
        List<ServiceContractProcess> list = serviceContractMapper.selectServiceContractProcessList(conditions);
        if (!list.isEmpty()) {
            throw new RuntimeException("存在未提交的流程");
        }
        //检查是否有进行中的
        conditions.setStatus(Constants.SubStatus.PROCESSING);
        if (!serviceContractMapper.selectServiceContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在待处理的流程");
        }
        serviceContractProcess.setCreateTime(DateUtils.getNowDate());
        serviceContractProcess.setUpdateTime(DateUtils.getNowDate());
        serviceContractProcess.setInitiateTime(DateUtils.getNowDate());
        return serviceContractMapper.insertServiceContractProcess((ServiceContractProcess) initDomainCreate(serviceContractProcess));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int initiateServiceContractProcess(ServiceContractProcess serviceContractProcess) {
        if (serviceContractMapper.updateServiceContractProcess((ServiceContractProcess) initDomainUpdate(serviceContractProcess)) > Constants.ZERO) {
            serviceContractProcess.setInitiateTime(DateUtils.getNowDate());
            serviceContractProcess.setStatus(Constants.TWO);
            ServiceContract contract = new ServiceContract();
            contract.setStatus(Constants.TWO);
            contract.setId(serviceContractProcess.getServiceContractId());
            serviceContractMapper.updateServiceContract(contract);
            return serviceContractMapper.updateServiceContractProcess((ServiceContractProcess) initDomainUpdate(serviceContractProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public int finishServiceContract(ServiceContract serviceContract) {
        ServiceContractProcess conditions = new ServiceContractProcess();
        conditions.setServiceContractId(serviceContract.getId());
        //检查是否有未提交的子项
        conditions.setStatus(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
        if (!serviceContractMapper.selectServiceContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在未提交的流程!");
        }
        //检查是否有进行中的子项
        conditions.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        if (!serviceContractMapper.selectServiceContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在进行中的流程!");
        }
        //设置服务合同状态为已完成
        serviceContract.setStatus(Integer.valueOf(Constants.MainStatus.COMPLETE));
        serviceContract.setUpdateTime(DateUtils.getNowDate());
        serviceContract = (ServiceContract) initDomainUpdate(serviceContract);
        return serviceContractMapper.updateServiceContract(serviceContract);
    }

    @Override
    public Object selectServiceContractProcessById(Long id) {
        ServiceContractProcess process = serviceContractMapper.selectServiceContractProcessById(id);
       // List<ServiceContractProcess> list = new ArrayList<>(1);
        //list.add(process);
//        createByReplace(list);
        return process;
    }

    @Override
    public int handleServiceContractProcess(ServiceContractProcess serviceContractProcess) {
        //1、
        if (serviceContractMapper.updateServiceContractProcess((ServiceContractProcess) initDomainUpdate(serviceContractProcess)) > Constants.ZERO) {
            serviceContractProcess.setHandleTime(DateUtils.getNowDate());
            return serviceContractMapper.updateServiceContractProcess((ServiceContractProcess) initDomainUpdate(serviceContractProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public String getProcessFiles(Long id) {
        ServiceContractProcess contractProcess = serviceContractMapper.selectServiceContractProcessById(id);
        return contractProcess.getFileNames();
    }
}
