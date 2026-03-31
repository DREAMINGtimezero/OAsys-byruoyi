package com.ruoyi.servicecontract.service;

import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.servicecontract.domain.ServiceContract;
import com.ruoyi.servicecontract.domain.ServiceContractProcess;

import javax.xml.ws.Service;
import java.util.List;

/**
 * 服务合同Service接口
 *
 * @author ruoyi
 * @date 2025-06-24
 */
public interface ServiceContractService {
    /**
     * 查询单个服务合同信息
     *
     * @param id 服务合同主键
     * @return 服务合同
     */
    public ServiceContract selectServiceContractById(Long id);

    /**
     * 查询服务合同信息列表
     *
     * @param serviceContract 服务合同信息
     * @return 服务合同信息集合
     */
    public List<ServiceContract> selectServiceContractList(ServiceContract serviceContract);

    /**
     * 编辑服务合同是否可编辑
     * @param id
     * @return
     */
    public boolean editAble(Long id);

    /**
     * 新增服务合同信息  同时插入合同流程信息（流程第一步  只有创始人  创始时间）
     *
     * @param serviceContract 服务合同信息
     * @return 结果
     */
    public int insertServiceContract(ServiceContract serviceContract);

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
     * @param ids 需要删除的服务合同信息主键集合
     * @return 结果
     */
    public int deleteServiceContractByIds(String ids);

    /**
     * 查询服务合同流程信息列表
     *
     * @param serviceContractProcess 服务合同流程
     * @return 服务合同信息列表
     */
    List<ServiceContractProcess> selectServiceContractProcessList(ServiceContractProcess serviceContractProcess);

    /**
     * 新增服务合同流程信息
     *
     * @param serviceContractProcess
     */
    int insertServiceContractProcess(ServiceContractProcess serviceContractProcess);

    /**
     * 更新服务合同流程信息
     *
     * @param serviceContractProcess
     */
    int initiateServiceContractProcess(ServiceContractProcess serviceContractProcess);


    /**
     * 结束服务合同
     * @param serviceContract
     * @return
     */
    int finishServiceContract(ServiceContract serviceContract);

    /**
     * 查询服务合同流程信息
     *
     * @param id
     * @return
     */
    Object selectServiceContractProcessById(Long id);

    /**
     * 处理服务合同流程信息
     *
     * @param serviceContractProcess
     */
    int handleServiceContractProcess(ServiceContractProcess serviceContractProcess);

    /**
     * 获取服务合同流程文件
     * @param id
     * @return
     */
    String getProcessFiles(Long id);

}
