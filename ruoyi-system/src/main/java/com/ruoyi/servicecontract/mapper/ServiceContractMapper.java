package com.ruoyi.servicecontract.mapper;

import com.ruoyi.servicecontract.domain.ServiceContract;
import com.ruoyi.servicecontract.domain.ServiceContractProcess;

import java.util.List;

public interface ServiceContractMapper {

    /**
     * 查询服务合同信息
     *
     * @param id 服务合同主键
     * @return 服务合同信息
     */
    public ServiceContract selectServiceContractById(Long id);

    /**
     * 查询服务合同信息列表
     *
     * @param serviceContract 服务合同信息查询条件
     * @return 服务合同信息集合
     */
    public List<ServiceContract> selectServiceContractList(ServiceContract serviceContract);

    /**
     * 新增服务合同信息
     *
     * @param serviceContract 服务合同信息
     * @return 结果
     */
    public int insertServiceContract(ServiceContract serviceContract);

    /**
     * 新增服务合同流程信息
     *
     * @param serviceContractProcess 服务合同流程信息
     * @return 结果
     */
    public int insertServiceContractProcess(ServiceContractProcess serviceContractProcess);

    /**
     * 修改服务合同信息
     *
     * @param serviceContract 服务合同信息
     * @return 结果
     */
    public int updateServiceContract(ServiceContract serviceContract);

    /**
     * 批量删除服务合同信息
     *
     * @param ids 服务合同信息主键集合
     * @return 结果
     */
    public int deleteServiceContractByIds(String[] ids);
    /**
     * 批量删除服务合同流程信息
     *
     * @param ids 服务合同过程信息主键集合
     * @return 结果
     */
    public int deleteServiceContractProcessByIds(String[] ids);

    /**
     * 查询合同处理流程列表
     *
     * @param serviceContractProcess
     * @return 影响行数
     */
    public List<ServiceContractProcess> selectServiceContractProcessList(ServiceContractProcess serviceContractProcess);
    /**
     * 查询合同处理流程列表
     *
     * @param serviceContractProcess 服务合同流程信息查询条件
     * @return 影响行数
     */
    public int updateServiceContractProcess(ServiceContractProcess serviceContractProcess);
    /**
     * 查询合同处理流程详情
     *
     * @param id 合同处理流程主键
     * @return 合同处理流程详情
     */
    public ServiceContractProcess selectServiceContractProcessById(Long id);
}
