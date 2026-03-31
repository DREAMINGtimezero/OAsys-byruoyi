package com.ruoyi.contract.mapper;

import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;

import java.util.List;


/**
 * 合同信息Mapper接口
 * 
 * @author ruoyi
 * @date 2025-06-24
 */
public interface ContractMapper
{
    /**
     * 查询合同信息
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
     * 新增合同信息
     * 
     * @param Contract 合同信息
     * @return 结果
     */
    public int insertContract(Contract Contract);

    /**
     * 修改合同信息
     * 
     * @param contract 合同信息
     * @return 结果
     */
    public int updateContract(Contract contract);

    /**
     * 删除合同信息
     * 
     * @param id 合同信息主键
     * @return 结果
     */
    public int deleteContractById(Long id);

    /**
     * 批量删除合同信息
     * 
     * @param contract
     * @return 结果
     */
    public int deleteContractByIds(Contract contract);

    /**
     * 查询合同处理流程列表
     *
     * @param contractProcess
     * @return 影响行数
     */
    List<ContractProcess> selectContractProcessList(ContractProcess contractProcess);

    /**
     * 新增合同处理流程
     *
     * @param contractProcess
     * @return 影响行数
     */
    int insertContractProcess(ContractProcess contractProcess);

    /**
     * 根据id查询合同处理流程
     *
     * @param id
     * @return 影响行数
     */
    ContractProcess selectContractProcessById(Long id);

    /**
     * 修改合同处理流程
     *
     * @param updateProcess
     * @return 影响行数
     */
    int updateContractProcess(ContractProcess updateProcess);
}