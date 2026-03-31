package com.ruoyi.contract.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;

/**
 * 合同信息Service接口
 * 
 * @author ruoyi
 * @date 2025-06-24
 */
public interface ContractService
{
    /**
     * 查询单个合同信息
     * 
     * @param id 合同信息主键
     * @return 合同信息
     */
    public Contract selectContractById(Long id);

    /**
     * 查询合同信息列表
     * 
     * @param Contract 合同信息
     * @return 合同信息集合
     */
    public List<Contract> selectContractList(Contract Contract);

    /**
     * 新增合同信息  同时插入合同流程信息（流程第一步  只有创始人  创始时间）
     * 
     * @param Contract 合同信息
     * @return 结果
     */
    public int insertContract(Contract Contract);

    /**
     * 修改合同信息
     * 
     * @param Contract 合同信息
     * @return 结果
     */
    public int updateContract(Contract Contract);

    /**
     * 批量删除合同信息
     * 
     * @param contract 需要删除的合同信息主键集合
     * @return 结果
     */
    public int deleteContractByIds(Contract contract);

    /**
     * 删除合同信息信息
     * 
     * @param id 合同信息主键
     * @return 结果
     */
    public int deleteContractById(Long id);

    /**
     * 查询合同流程信息列表
     *
     * @param contractProcess 合同流程
     * @return 合同信息列表
     */
    List<ContractProcess> selectContractProcessList(ContractProcess contractProcess);

    /**
     * 新增合同流程信息
     *
     * @param contractProcess
     */
    int insertContractProcess(ContractProcess contractProcess);

    /**
     * 更新合同流程信息
        *
     * @param contractProcess
     */
    int initiateContractProcess(ContractProcess contractProcess);


    /**
     * 结束合同
     * @param contract
     * @return
     */
    int finishContract(Contract contract);

    /**
     * 查询合同流程信息
     *
     * @param id
     * @return
     */
    Object selectContractProcessById(Long id);

    /**
     * 处理合同流程信息
     *
     * @param contractProcess
     */
    int handleContractProcess(ContractProcess contractProcess);

    /**
     * 获取合同流程文件
     * @param id
     * @return
     */
    String getProcessFiles(Long id);
}