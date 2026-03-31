package com.ruoyi.officesupplies.service.impl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.contract.domain.Contract;
import com.ruoyi.contract.domain.ContractProcess;
import com.ruoyi.costmanagement.domain.ExpenseClaim;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import com.ruoyi.officesupplies.service.OfficeSuppliesService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.officesupplies.mapper.OfficeSuppliesMapper;
import com.ruoyi.officesupplies.domain.OfficeSupplies;
import com.ruoyi.officesupplies.domain.OfficeSuppliesSub;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.base.service.impl.BaseServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.xml.transform.Result;

/**
 * 办公用品Service业务层处理
 *
 * @author gyx
 * @date 2025-08-06
 */
@Slf4j
@Service
public class OfficeSuppliesServicelmpl extends BaseServiceImpl implements OfficeSuppliesService {
    @Autowired
    private OfficeSuppliesMapper officeSuppliesMapper;
    @Autowired
    private TransactionTemplate transaction;
    /**
     * 查询办公用品主表
     *
     * @param id 办公用品主表主键
     * @return 办公用品主表
     */
    @Override
    public OfficeSupplies selectOfficeSuppliesById(Long id)
    {   OfficeSupplies officesupplies = officeSuppliesMapper.selectOfficeSuppliesById(id);
        if (Objects.isNull(officesupplies)) {
            return null;
        }
        List<OfficeSupplies> list = new ArrayList<>(1);
        list.add(officesupplies);
        createByReplace(list);
        updateByReplace(list);
        return officesupplies;
    }

    /**
     * 查询办公用品主表列表
     *
     * @param officeSupplies 办公用品主表
     * @return 办公用品主表
     */
    @Override
    public List<OfficeSupplies> selectOfficeSuppliesList(OfficeSupplies officeSupplies)
    {
        List<OfficeSupplies> officesupplies =officeSuppliesMapper.selectOfficeSuppliesList((OfficeSupplies)initDomainCreate(officeSupplies));
        createByReplace(officesupplies);
        updateByReplace(officesupplies);
        return officesupplies;
    }

    /**
     * 新增办公用品主表
     *
     * @param officeSupplies 办公用品主表
     * @return 结果
     */
    @Override
    public int insertOfficeSupplies(OfficeSupplies officeSupplies)
    {
        officeSupplies=(OfficeSupplies)initDomainCreate(officeSupplies);
        int result= officeSuppliesMapper.insertOfficeSupplies(officeSupplies);
        OfficeSuppliesSub officeSuppliesSub=new OfficeSuppliesSub();
        officeSuppliesSub.setOfficeSuppliesId(officeSupplies.getId());
        return result+officeSuppliesMapper.insertOfficeSuppliesSub((OfficeSuppliesSub)initDomainCreate(officeSuppliesSub));
    }

    /**
     * 修改办公用品主表
     *
     * @param officeSupplies 办公用品主表
     * @return 结果
     */
    @Override
    public int updateOfficeSupplies(OfficeSupplies officeSupplies)
    {
        initDomainUpdate(officeSupplies);
        officeSupplies.setUpdateTime(DateUtils.getNowDate());
        return officeSuppliesMapper.updateOfficeSupplies(officeSupplies);
    }

    /**
     * 批量删除办公用品主表
     *
     * @param ids 需要删除的办公用品主表主键
     * @return 结果
     */
    @Override
    public int deleteOfficeSuppliesByIds(String ids)
    {
        SysUser sysUser =new SysUser();
        Long[] idList = Convert.toLongArray(ids);
        for (Long id : idList) {
            OfficeSupplies officesupplies = selectOfficeSuppliesById(id);
            if (officesupplies == null) {
                throw new RuntimeException("办公用品不存在! " );
            }
            if (!officesupplies.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT))) {
                throw new RuntimeException("操作失败！只能操作'未提交'状态的办公用品! " );
            }

            else {
                initDomainUpdate(officesupplies);
                officeSuppliesMapper.updateOfficeSupplies(officesupplies);
            }
        }
        int result=officeSuppliesMapper.deleteOfficeSuppliesByIds(Convert.toStrArray(ids));
        return result+officeSuppliesMapper.deleteOfficeSuppliesSubByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除办公用品主表信息
     *
     * @param id 办公用品主表主键
     * @return 结果
     */
    @Override
    public int deleteOfficeSuppliesById(Long id)
    {
        return officeSuppliesMapper.deleteOfficeSuppliesById(id);
    }


    /**
     * 查询办公用品子
     *
     * @param id 办公用品子主键
     * @return 办公用品子
     */
    @Override
    public OfficeSuppliesSub selectOfficeSuppliesSubById(Long id)
    {
        OfficeSuppliesSub officeSuppliesSub=officeSuppliesMapper.selectOfficeSuppliesSubById(id);
        if (Objects.isNull(officeSuppliesSub)) {
            return null;
        }
        List<OfficeSuppliesSub> list = new ArrayList<>(1);
        list.add(officeSuppliesSub);
        createByReplace(list);
        updateByReplace(list);
        handleByReplace(list);
        return list.get(0);
    }

    /**
     * 查询办公用品子列表
     *
     * @param officeSuppliesSub 办公用品子
     * @return 办公用品子
     */
    @Override
    public List<OfficeSuppliesSub> selectOfficeSuppliesSubList(OfficeSuppliesSub officeSuppliesSub)
    {
        List<OfficeSuppliesSub> OfficeSuppliesSub =officeSuppliesMapper.selectOfficeSuppliesSubList(officeSuppliesSub);
        createByReplace(OfficeSuppliesSub);
        handleByReplace(OfficeSuppliesSub);
        filterMainForHandle(OfficeSuppliesSub);
        return OfficeSuppliesSub;
    }

    public void filterMainForHandle(List<? extends BaseEntity> list) {
        if(Objects.isNull(list)||list.isEmpty())
        {
            return;
        }
        try {
            //获取username
            String loginName = ShiroUtils.getSysUser().getUserName();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                Object data = it.next();
                Class<?> clazz = data.getClass();
                Field field = clazz.getSuperclass().getDeclaredField("createBy");
                field.setAccessible(true);
                String createBy = field.get(data).toString();
                field = clazz.getDeclaredField("status");
                field.setAccessible(true);
                String status = field.get(data).toString();
                //数据还未提交，且当前登录用户不是创建者
                if (status.equals(Constants.MainStatus.UNSUBMIT) && !loginName.equals(createBy)) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增办公用品子
     *
     * @param officeSuppliesSub 办公用品子表
     * @return 结果
     */
    @Override
    public int insertOfficeSuppliesSub(OfficeSuppliesSub officeSuppliesSub)
    {
        OfficeSupplies officeSupplies =officeSuppliesMapper.selectOfficeSuppliesById(officeSuppliesSub.getOfficeSuppliesId()) ;
        if(officeSupplies.getStatus().equals(Integer.valueOf(Constants.MainStatus.COMPLETE))){
            throw new RuntimeException("操作失败！只能操作'未提交'状态的办公用品! " );
        }
        OfficeSuppliesSub officeSuppliesSub1 = new OfficeSuppliesSub();
        officeSuppliesSub1.setOfficeSuppliesId(officeSuppliesSub.getOfficeSuppliesId());
        officeSuppliesSub1.setStatus(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
        List<OfficeSuppliesSub> list =officeSuppliesMapper.selectOfficeSuppliesSubList(officeSuppliesSub1);
        if(!list.isEmpty()){
            throw new RuntimeException("存在未提交的流程" );
        }
        officeSuppliesSub1.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        if(!officeSuppliesMapper.selectOfficeSuppliesSubList(officeSuppliesSub1).isEmpty()){
            throw new RuntimeException("存在待处理的流程" );
        }
        return officeSuppliesMapper.insertOfficeSuppliesSub((OfficeSuppliesSub)initDomainCreate(officeSuppliesSub));
    }

    /**
     * 结束合同
     */
    @Override
    public int finishOfficeSupplies(OfficeSupplies officeSupplies){
        OfficeSuppliesSub officeSuppliesSub =new OfficeSuppliesSub();
        officeSuppliesSub.setOfficeSuppliesId(officeSupplies.getId());
        officeSuppliesSub.setStatus(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
        if (!officeSuppliesMapper.selectOfficeSuppliesSubList(officeSuppliesSub).isEmpty()){
            throw new RuntimeException("存在未提交的流程" );
        }
        officeSuppliesSub.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        if (!officeSuppliesMapper.selectOfficeSuppliesSubList(officeSuppliesSub).isEmpty()){
            throw new RuntimeException("存在进行中的流程" );
        }
        officeSupplies.setUpdateTime(DateUtils.getNowDate());
        officeSupplies = (OfficeSupplies) initDomainUpdate(officeSupplies);
        return officeSuppliesMapper.updateOfficeSupplies(officeSupplies);
    }
    /**
     * 修改办公用品子
     *
     * @param officeSuppliesSub 办公用品子
     * @return 结果
     */
    @Override
    public int updateOfficeSuppliesSub(OfficeSuppliesSub officeSuppliesSub)
    {
        officeSuppliesSub.setUpdateTime(DateUtils.getNowDate());
        return officeSuppliesMapper.updateOfficeSuppliesSub(officeSuppliesSub);
    }
    public boolean DeleteAble(Long[] idList){
        for (Long id : idList) {
            OfficeSupplies officesupplies = selectOfficeSuppliesById(id);
            if (officesupplies == null) {
                throw new RuntimeException("办公用品不存在! " );
            }
            if (!officesupplies.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT))) {
                throw new RuntimeException("操作失败！只能操作'未提交'状态的办公用品! " );
            }
        }
        return true;
    }
    @Override
    public boolean editAble(Long id) {
        //只能编辑未提交的项
        OfficeSupplies mainlist = selectOfficeSuppliesById(id);
        return mainlist.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
    }
    /*
    *提交发起流程
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int submitsub(OfficeSuppliesSub officeSuppliesSub) {
        //防止覆盖
        officeSuppliesSub.setCreateBy(null);
        //设置发起时间
        officeSuppliesSub.setInitiateTime(DateUtils.getNowDate());
        //设置状态为处理中
        officeSuppliesSub.setStatus(Constants.SubStatus.PROCESSING);

        OfficeSupplies officeSupplies = new OfficeSupplies();
        //根据关联Id更新主表
        officeSupplies.setId(officeSuppliesSub.getOfficeSuppliesId());
        //设置主表进行中
        officeSupplies.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        Integer result = transaction.execute(tranStatus -> {
            try {
                //更新子表为已处理
                return updateOfficeSuppliesSub(officeSuppliesSub) +
                        //更新主表
                        updateOfficeSupplies(officeSupplies);
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error(e.toString());
                throw new RuntimeException(e);
            }
        });
        return result == null ? 0 : result;
    }

    @Override
    public int subhandle(OfficeSuppliesSub officeSuppliesSub){
        //防止覆盖
        officeSuppliesSub.setCreateBy(null);
        officeSuppliesSub.setHandleBy(null);
        //设置处理时间
        officeSuppliesSub.setHandleTime(DateUtils.getNowDate());

        OfficeSupplies officeSupplies = new OfficeSupplies();
        //根据关联Id更新主表
        officeSupplies.setId(officeSuppliesSub.getOfficeSuppliesId());
        //设置主表进行中
        officeSupplies.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        Integer result = transaction.execute(tranStatus -> {
            try {
                //更新子表为已处理
                return updateOfficeSuppliesSub(officeSuppliesSub) +
                        //更新主表
                        updateOfficeSupplies(officeSupplies);
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error(e.toString());
                throw new RuntimeException(e);
            }
        });
        return result == null ? 0 : result;
    }

    /*
    *查询文件名字
     */

    @Override
    public String getsubFiles(Long id) {
        OfficeSuppliesSub  officeSuppliesSub = officeSuppliesMapper.selectOfficeSuppliesSubById(id);
        return officeSuppliesSub.getFileNames();
    }
    /**
     * 批量删除办公用品子
     *
     * @param ids 需要删除的办公用品子表主键
     * @return 结果
     */
    @Override
    public int deleteOfficeSuppliesSubByIds(String ids)
    {
        return officeSuppliesMapper.deleteOfficeSuppliesSubByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除办公用品子信息
     *
     * @param id 办公用品子表主键
     * @return 结果
     */
    @Override
    public int deleteOfficeSuppliesSubById(Long id)
    {
        return officeSuppliesMapper.deleteOfficeSuppliesSubById(id);
    }}
