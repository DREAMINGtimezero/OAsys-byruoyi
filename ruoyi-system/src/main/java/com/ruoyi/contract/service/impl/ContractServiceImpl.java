package com.ruoyi.contract.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.project.domain.ProjectStatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contract.mapper.ContractMapper;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.service.ContractService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合同信息Service实现层处理
 *
 * @author lty
 * @date 2025-06-24
 */
@Service
public class ContractServiceImpl extends BaseServiceImpl implements ContractService {
    @Autowired
    private ContractMapper contractMapper;

    @Override
    public Contract selectContractById(Long id) {
        return contractMapper.selectContractById(id);
    }

    @Override
    public List<Contract> selectContractList(Contract contract) {
        List<Contract> contracts = contractMapper.selectContractList((Contract) initDomainCreate(contract));
        createByReplace(contracts);
        updateByReplace(contracts);
        return contracts;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertContract(Contract contract) {
        contract = (Contract) initDomainCreate(contract);
        int result = contractMapper.insertContract(contract);
        ContractProcess contractProcess = new ContractProcess();
        contractProcess.setContractId(contract.getId());
        return result + contractMapper.insertContractProcess((ContractProcess) initDomainCreate(contractProcess));
    }

    @Override
    public int updateContract(Contract contract) {
        checkContractById(contract.getId());
        contract.setUpdateTime(DateUtils.getNowDate());
        return contractMapper.updateContract((Contract) initDomainUpdate(contract));
    }

    @Override
    public int deleteContractByIds(Contract contract) {
        for (Long ids : contract.getIds()) {
            checkContractById(ids);
        }
        return contractMapper.deleteContractByIds((Contract) initDomainUpdate(contract));
    }

    private void checkContractById(Long id) {
        // 根据项目号查询项目
        Contract contract = contractMapper.selectContractById(id);
        if (contract == null) {
            throw new RuntimeException("合同不存在!");
        }
        if (contract.getStatus() != Constants.ONE) {
            throw new RuntimeException("操作失败！只能操作'未提交'状态的合同!");
        }
    }

    @Override
    public int deleteContractById(Long id) {
        return contractMapper.deleteContractById(id);
    }

    @Override
    public List<ContractProcess> selectContractProcessList(ContractProcess contractProcess) {
        List<ContractProcess> contractProcesses = contractMapper.selectContractProcessList(contractProcess);
        createByReplace(contractProcesses);
        handleByReplace(contractProcesses);
        return contractProcesses;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertContractProcess(ContractProcess contractProcess) {
        Contract contract = contractMapper.selectContractById(contractProcess.getContractId());
        //检查主项是否已经完成
        if (contract.getStatus().equals(Integer.valueOf(Constants.MainStatus.COMPLETE))) {
            throw new RuntimeException("合同已经完成，不能进行操作！");
        }
        ContractProcess conditions = new ContractProcess();
        //设置关联id
        conditions.setContractId(contractProcess.getContractId());
        //检查是否有未提交的
        conditions.setStatus(Constants.SubStatus.UNSUBMIT);
        List<ContractProcess> list = contractMapper.selectContractProcessList(conditions);
        if (!list.isEmpty()) {
            throw new RuntimeException("存在未提交的流程");
        }
        //检查是否有进行中的
        conditions.setStatus(Constants.SubStatus.PROCESSING);
        if (!contractMapper.selectContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在待处理的流程");
        }
        return contractMapper.insertContractProcess((ContractProcess) initDomainCreate(contractProcess));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int initiateContractProcess(ContractProcess contractProcess) {
        if (contractMapper.updateContractProcess((ContractProcess) initDomainUpdate(contractProcess)) > Constants.ZERO) {
            contractProcess.setInitiateTime(DateUtils.getNowDate());
            contractProcess.setStatus(Constants.TWO);
            Contract contract = new Contract();
            contract.setStatus(Constants.TWO);
            contract.setId(contractProcess.getContractId());
            contractMapper.updateContract(contract);
            return contractMapper.updateContractProcess((ContractProcess) initDomainUpdate(contractProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public int finishContract(Contract contract) {
        ContractProcess conditions = new ContractProcess();
        conditions.setContractId(contract.getId());
        //检查是否有未提交的子项
        conditions.setStatus(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
        if (!contractMapper.selectContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在未提交的流程!");
        }
        //检查是否有进行中的子项
        conditions.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        if (!contractMapper.selectContractProcessList(conditions).isEmpty()) {
            throw new RuntimeException("存在进行中的流程!");
        }
        contract.setUpdateTime(DateUtils.getNowDate());
        contract = (Contract) initDomainUpdate(contract);
        return contractMapper.updateContract(contract);
    }

    @Override
    public ContractProcess selectContractProcessById(Long id) {
        ContractProcess process = contractMapper.selectContractProcessById(id);
        List<ContractProcess> list = new ArrayList<>(1);
        list.add(process);
//        createByReplace(list);
        return process;
    }

    @Override
    public int handleContractProcess(ContractProcess contractProcess) {
        if (contractMapper.updateContractProcess((ContractProcess) initDomainUpdate(contractProcess)) > Constants.ZERO) {
            contractProcess.setHandleTime(DateUtils.getNowDate());
            return contractMapper.updateContractProcess((ContractProcess) initDomainUpdate(contractProcess));
        }
        return Constants.ZERO;
    }

    @Override
    public String getProcessFiles(Long id) {
        ContractProcess contractProcess = contractMapper.selectContractProcessById(id);
        return contractProcess.getFileNames();
    }


}

