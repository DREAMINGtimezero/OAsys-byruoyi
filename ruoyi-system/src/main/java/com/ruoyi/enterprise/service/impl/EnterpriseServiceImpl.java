package com.ruoyi.enterprise.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.enterprise.domain.Enterprise;
import com.ruoyi.enterprise.domain.EnterpriseExport;
import com.ruoyi.enterprise.mapper.EnterpriseMapper;
import com.ruoyi.enterprise.service.EnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class EnterpriseServiceImpl extends BaseServiceImpl implements EnterpriseService {
    @Autowired
    private EnterpriseMapper enterpriseMapper;

    @Override
    public int insertEnterprise(Enterprise enterprise) {
        enterprise.setDevStatus(1);
        enterprise.setCreateBy(getLoginName());
        return enterpriseMapper.insertEnterprise(enterprise);
    }

    @Override
    public int updateEnterprise(Enterprise enterprise) {
        initDomainUpdate(enterprise);
        return enterpriseMapper.updateEnterprise(enterprise);
    }

    @Override
    public int changeStatus(Long id, Integer devStatus) {
        if (!allowUpdate(id)) {
            return 0;
        }
        Enterprise enterprise = new Enterprise();
        enterprise.setId(id);
        enterprise.setDevStatus(devStatus);
        return updateEnterprise(enterprise);
    }

    public boolean allowUpdate(long id) {
        Enterprise previousEnterprise = selectEnterpriseById(id);
        return !previousEnterprise.getDevStatus().equals(4);
    }

    @Override
    public Enterprise selectEnterpriseById(Long id) {
        return enterpriseMapper.selectEnterpriseById(id);
    }

    @Override
    public List<Enterprise> selectEnterpriseList(Enterprise enterprise) {
        List<Enterprise> list = enterpriseMapper.selectEnterpriseList((Enterprise) initDomainCreate(enterprise));
        createByReplace(list);
        updateByReplace(list);
        return list;
    }

    @Override
    public int deleteEnterpriseByIds(String ids) {
        Long[] idList = Convert.toLongArray(ids);
        for (Long id : idList) {
            Enterprise enterprise = selectEnterpriseById(id);
            initDomainUpdate(enterprise);
            enterpriseMapper.updateEnterprise(enterprise);
        }
        return enterpriseMapper.deleteEnterpriseByIds(Convert.toStrArray(ids));
    }

    @Override
    public List<EnterpriseExport> selectEnterpriseExportList(Enterprise enterprise) {
        return enterpriseMapper.selectEnterpriseExportList(enterprise);
    }

    @Override
    public String importEnterprise(List<Enterprise> enterpriseList, boolean isUpdateSupport, String operName) {
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(enterpriseList)) {
            return "导入企业数据不能为空！";
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Enterprise enterprise : enterpriseList) {
            try {
                // 验证企业是否存在
                Enterprise existingEnterprise = enterpriseMapper.selectEnterpriseById(enterprise.getId());
                if (existingEnterprise == null) {
                    // 新增企业
                    enterprise.setDevStatus(1);
                    enterprise.setCreateBy(operName);
                    enterpriseMapper.insertEnterprise(enterprise);
                    successNum++;
                } else if (isUpdateSupport) {
                    // 更新企业
                    enterprise.setId(existingEnterprise.getId());
                    enterprise.setUpdateBy(operName);
                    enterpriseMapper.updateEnterprise(enterprise);
                    successNum++;
                } else {
                    failureNum++;
                    failureMsg.append("<br/>企业名称 " + enterprise.getEnterpriseName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "企业名称 " + enterprise.getEnterpriseName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "共导入" + successNum + "条数据，失败" + failureNum + "条数据：");
            return failureMsg.toString();
        } else {
            successMsg.append("共导入" + successNum + "条数据");
            return successMsg.toString();
        }
    }
}