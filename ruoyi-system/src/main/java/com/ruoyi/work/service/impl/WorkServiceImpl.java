package com.ruoyi.work.service.impl;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.work.domain.Work;
import com.ruoyi.work.mapper.WorkMapper;
import com.ruoyi.work.service.WorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkServiceImpl extends BaseServiceImpl implements WorkService {
    @Autowired
    WorkMapper workMapper;

    @Override
    public List<Work> selectWorkList(Work work) {
        List<Work> works = workMapper.selectWorkList((Work) initDomainCreate(work));
        createByReplace(works);
        return works;
    }

    @Override
    public Work selectWorkById(Long id) {
        return workMapper.selectWorkById(id);
    }

    @Override
    public int insertWork(Work work) {
        return workMapper.insertWork((Work)initDomainCreate(work));
    }

    @Override
    public int updateWork(Work work) {
        return workMapper.updateWork((Work)initDomainUpdate(work));
    }

    @Override
    public int deleteWorkByIds(Work work) {
        return workMapper.deleteWorkByIds((Work)initDomainUpdate (work));
    }
}
