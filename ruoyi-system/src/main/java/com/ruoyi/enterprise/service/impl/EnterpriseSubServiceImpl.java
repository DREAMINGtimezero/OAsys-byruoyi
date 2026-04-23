package com.ruoyi.enterprise.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.enterprise.domain.EnterpriseSub;
import com.ruoyi.enterprise.mapper.EnterpriseSubMapper;
import com.ruoyi.enterprise.service.EnterpriseSubService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EnterpriseSubServiceImpl extends BaseServiceImpl implements EnterpriseSubService {
    @Autowired
    private EnterpriseSubMapper enterpriseSubMapper;

    @Override
    public int insertEnterpriseSub(EnterpriseSub enterpriseSub) {
        enterpriseSub.setCreateBy(getLoginName());
        return enterpriseSubMapper.insertEnterpriseSub(enterpriseSub);
    }

    @Override
    public int updateEnterpriseSub(EnterpriseSub enterpriseSub) {
        initDomainUpdate(enterpriseSub);
        return enterpriseSubMapper.updateEnterpriseSub(enterpriseSub);
    }

    @Override
    public List<EnterpriseSub> selectEnterpriseSubList(EnterpriseSub enterpriseSub) {
        return enterpriseSubMapper.selectEnterpriseSubList(enterpriseSub);
    }

    @Override
    public List<EnterpriseSub> selectEnterpriseSubListByEnterpriseId(Long enterpriseId) {
        return enterpriseSubMapper.selectEnterpriseSubListByEnterpriseId(enterpriseId);
    }

    @Override
    public int deleteEnterpriseSubByIds(String ids) {
        Long[] idList = Convert.toLongArray(ids);
        for (Long id : idList) {
            EnterpriseSub enterpriseSub = selectEnterpriseSubById(id);
            initDomainUpdate(enterpriseSub);
            enterpriseSubMapper.updateEnterpriseSub(enterpriseSub);
        }
        return enterpriseSubMapper.deleteEnterpriseSubByIds(Convert.toStrArray(ids));
    }

    @Override
    public EnterpriseSub selectEnterpriseSubById(Long id) {
        return enterpriseSubMapper.selectEnterpriseSubById(id);
    }
}