package com.ruoyi.technicalaggrement.service.impl;

import java.util.*;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.mapper.TechnicalAgreementMapper;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.ruoyi.technicalaggrement.mapper.TechnicalAgreementSubMapper;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementSubService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 技术协议子Service业务层处理
 *
 * @author YJ
 * @date 2025-06-25
 */
@Service
@Slf4j
public class TechnicalAgreementSubServiceImpl extends BaseServiceImpl implements TechnicalAgreementSubService {
    @Autowired
    private TechnicalAgreementSubMapper subMapper;
    @Autowired
    private TechnicalAgreementMapper mainMapper;
    @Autowired
    private TechnicalAgreementService mainService;

    /**
     * 查询技术协议子表数据
     *
     * @param id 技术协议子表主键
     * @return 技术协议子表数据
     */
    @Override
    public TechnicalAgreementSub selectTechnicalAgreementSubById(Long id) {
        TechnicalAgreementSub data = subMapper.selectTechnicalAgreementSubById(id);
        if(Objects.isNull(data))
        {
            return null;
        }
        List<TechnicalAgreementSub> list = new ArrayList<>(1);
        list.add(data);
        createByReplace(list);
        handleByReplace(list);
        return list.get(0);
    }

    @Override
    public List<TechnicalAgreementSub> selectTechnicalAgreementSubList(TechnicalAgreementSub conditions) {
        List<TechnicalAgreementSub> list = subMapper.selectTechnicalAgreementSubList(conditions);
        if (!list.isEmpty()) {
            //为登录用户过滤未提交的数据
            filterSubForHandler(list);
            //获取权限
            /*
            assert(Objects.nonNull(conditions.getTechnicalAgreementId()));
            TechnicalAgreement main = mainMapper.selectTechnicalAgreementById(conditions.getTechnicalAgreementId());
            Map<Long, Boolean> submitPermission = new HashMap<>(list.size() * 4 / 3);
            Map<Long, Boolean> handlePermission = new HashMap<>(list.size() * 4 / 3);
            list.forEach(sub -> {
                submitPermission.put(sub.getId(), submitPermission(main, sub));
                handlePermission.put(sub.getId(), handlePermission(main, sub));
            });
            //将权限信息放到第一条数据中
            list.get(0).setSubmitPermission(submitPermission);
            list.get(0).setHandlePermission(handlePermission);
            */
            //进行发起人的登录名与用户名的映射
            createByReplace(list);
            //进行处理人的登录名与用户名的映射
            handleByReplace(list);
        }
        return list;
    }

    /**
     * 新增技术协议子
     *
     * @param technicalAgreementSub 技术协议子
     * @return 结果
     */
    @Override
    public int insertTechnicalAgreementSub(TechnicalAgreementSub technicalAgreementSub) {
        //填充发起人/数据创建人
        technicalAgreementSub = (TechnicalAgreementSub) initDomainCreate(technicalAgreementSub);
        return subMapper.insertTechnicalAgreementSub(technicalAgreementSub);
    }

    /**
     * 修改技术协议子
     *
     * @param technicalAgreementSub 技术协议子
     * @return 结果
     */
    @Override
    public int updateTechnicalAgreementSub(TechnicalAgreementSub technicalAgreementSub) {
        //填充数据更新者
        technicalAgreementSub = (TechnicalAgreementSub) initDomainUpdate(technicalAgreementSub);
        return subMapper.updateTechnicalAgreementSub(technicalAgreementSub);
    }

    /**
     * 批量删除技术协议子
     *
     * @param ids 需要删除的技术协议子主键
     * @return 结果
     */
    @Override
    public int deleteTechnicalAgreementSubByIds(String ids) {
        return subMapper.deleteTechnicalAgreementSubByIds(Convert.toStrArray(ids), getLoginName());
    }

    /**
     * 删除技术协议子信息
     *
     * @param id 技术协议子主键
     * @return 结果
     */
    @Override
    public int deleteTechnicalAgreementSubById(Long id) {
        return subMapper.deleteTechnicalAgreementSubById(id, getLoginName());
    }

    /**
     * 删除关联数据
     *
     * @param ids 关联外键
     * @return 删除结果
     */
    @Override
    public int deleteRelativeData(@NonNull Long[] ids) {
        assert(ids.length>0);
        return subMapper.deleteByTechnicalAgreementIds(ids, getLoginName());
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int submitProcess(TechnicalAgreementSub technicalAgreementSub) {
        log.debug("submit technicalAgreementSub:{}", technicalAgreementSub);
        //防止覆盖创建人(前端可能传递创建人的userName,不做处理会覆盖loginName)
        technicalAgreementSub.setCreateBy(null);
        /*子表更新数据*/
        //设置发起时间
        technicalAgreementSub.setInitiateTime(DateUtils.getNowDate());
        //设置状态为处理中
        technicalAgreementSub.setStatus(Constants.SubStatus.PROCESSING);
        //子表更新
        int result = updateTechnicalAgreementSub(technicalAgreementSub);
        /*主表更新数据*/
        TechnicalAgreement technicalAgreement = new TechnicalAgreement();
        //根据关联Id更新主表
        assert(Objects.nonNull(technicalAgreementSub.getTechnicalAgreementId()));
        technicalAgreement.setId(technicalAgreementSub.getTechnicalAgreementId());
        //主表更新为进行中
        technicalAgreement.setStatus(Constants.MainStatus.PROCESSING);
        return result + mainService.updateTechnicalAgreement(technicalAgreement);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int handleProcess(TechnicalAgreementSub sub) {
        //防止覆盖
        sub.setCreateBy(null);
        sub.setHandleBy(null);
        //记录处理时间
        sub.setHandleTime(DateUtils.getNowDate());
        int result = updateTechnicalAgreementSub(sub);
        //更新主表,记录最后操作人
        TechnicalAgreement technicalAgreement = new TechnicalAgreement();
        assert(Objects.nonNull(sub.getTechnicalAgreementId()));
        technicalAgreement.setId(sub.getTechnicalAgreementId());
        technicalAgreement.setStatus(Constants.MainStatus.PROCESSING);
        mainService.updateTechnicalAgreement(technicalAgreement);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TechnicalAgreementSub add(Long relativeId) {
        //验证主表还在进行状态/未提交状态
        TechnicalAgreement mainData = mainMapper.selectTechnicalAgreementById(relativeId);
        TechnicalAgreementSub sub = new TechnicalAgreementSub();
        if(Objects.isNull(mainData))
        {
            log.error("主表项不存在,不符合业务流程");
            sub.setStatus(-1);
            return sub;
        }
        if (!(Constants.MainStatus.UNSUBMIT.equals(mainData.getStatus()) || Constants.MainStatus.PROCESSING.equals(mainData.getStatus())))
        {
            log.debug("主表项已结束");
            sub.setStatus(Constants.SubStatus.PASSED);
            return sub;
        }
        //验证子表中是否存在未提交和未处理的申请
        Integer[] statusList = {Constants.SubStatus.PROCESSING, Constants.SubStatus.UNSUBMIT};
        if (selectByStatuses(statusList, relativeId) > 0) {
            log.debug("存在未提交/未处理的子表项");
            sub.setStatus(Constants.SubStatus.REJECTED);
            return sub;
        }
        //设置关联依据
        sub.setTechnicalAgreementId(relativeId);
        //向子表中插入基本数据
        insertTechnicalAgreementSub(sub);
        //查询出新增的数据
        sub = selectTechnicalAgreementSubById(sub.getId());
        //修改主表为进行中(更改状态的同时,记录最后操作人)
        mainData = new TechnicalAgreement();
        mainData.setId(relativeId);
        mainData.setStatus(Constants.MainStatus.PROCESSING);
        mainService.updateTechnicalAgreement(mainData);
        return sub;
    }

    @Override
    public int selectByStatuses(@NonNull Integer[] statusList, @NonNull Long relativeId) {
        assert(statusList.length > 0);
        return subMapper.selectByStatuses(statusList, relativeId);
    }

    @Override
    public boolean handlePermission(@NonNull Long subId) {
        TechnicalAgreementSub sub = subMapper.selectTechnicalAgreementSubById(subId);
        TechnicalAgreement main = mainMapper.selectTechnicalAgreementById(sub.getTechnicalAgreementId());
        //主表和子表状态为进行中且当前登录用户为处理人
        return handlePermission(main, sub);
    }

    @Override
    public boolean handlePermission(@NonNull TechnicalAgreement main, @NonNull TechnicalAgreementSub sub) {
        assert main.getId().equals(sub.getTechnicalAgreementId());
        //主表和子表状态为进行中且当前登录用户为处理人
        return Constants.MainStatus.PROCESSING.equals(main.getStatus())
                && Constants.SubStatus.PROCESSING.equals(sub.getStatus())
                && StringUtils.equals(getLoginName(), sub.getHandleBy());
    }

    @Override
    public boolean submitPermission(@NonNull Long subId) {
        TechnicalAgreementSub sub = subMapper.selectTechnicalAgreementSubById(subId);
        TechnicalAgreement main = mainMapper.selectTechnicalAgreementById(sub.getTechnicalAgreementId());
        //主表和子表状态为进行中且当前登录用户为处理人
        return submitPermission(main,sub);
    }

    @Override
    public boolean submitPermission(@NonNull TechnicalAgreement main, @NonNull TechnicalAgreementSub sub) {
        assert main.getId().equals(sub.getTechnicalAgreementId());
        //主表和子表状态为进行中且当前登录用户为创建人
        return (Constants.MainStatus.PROCESSING.equals(main.getStatus()) || Constants.MainStatus.UNSUBMIT.equals(main.getStatus()))
                && Constants.SubStatus.UNSUBMIT.equals(sub.getStatus())
                && StringUtils.equals(getLoginName(), sub.getCreateBy());
    }
}
