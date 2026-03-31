package com.ruoyi.costmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.costmanagement.domain.ExpenseClaim;
import com.ruoyi.costmanagement.mapper.ExpenseClaimMapper;
import com.ruoyi.costmanagement.service.ExpenseClaimService;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.ruoyi.costmanagement.mapper.ExpenseClaimSubMapper;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import com.ruoyi.costmanagement.service.ExpenseClaimSubService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 报销单子Service业务层处理
 *
 * @author YJ
 * @date 2025-07-31
 */
@Slf4j
@Service
public class ExpenseClaimSubServiceImpl extends BaseServiceImpl implements ExpenseClaimSubService {
    @Autowired
    private ExpenseClaimSubMapper subMapper;
    @Autowired
    private ExpenseClaimService mainService;
    @Autowired
    private ExpenseClaimMapper mainMapper;
    @Autowired
    private TransactionTemplate transaction;

    @Override
    public ExpenseClaimSub selectExpenseClaimSubById(Long id) {
        ExpenseClaimSub data = subMapper.selectExpenseClaimSubById(id);
        if (Objects.isNull(data)) {
            return null;
        }
        List<ExpenseClaimSub> list = new ArrayList<>(1);
        list.add(data);
        createByReplace(list);
        handleByReplace(list);
        return list.get(0);
    }

    @Override
    public List<ExpenseClaimSub> selectExpenseClaimSubList(ExpenseClaimSub expenseClaimSub) {
        expenseClaimSub.setDeleteFlag(0);
        List<ExpenseClaimSub> list = subMapper.selectExpenseClaimSubList(expenseClaimSub);
        //过滤子项
        filterSubForHandler(list);
        //映射创建人
        createByReplace(list);
        //映射修改人
        handleByReplace(list);
        return list;
    }

    @Override
    public int insertExpenseClaimSub(ExpenseClaimSub expenseClaimSub) {
        //填充创建人
        initDomainCreate(expenseClaimSub);
        return subMapper.insertExpenseClaimSub(expenseClaimSub);
    }

    @Override
    public int updateExpenseClaimSub(ExpenseClaimSub expenseClaimSub) {
        //填充修改人
        initDomainUpdate(expenseClaimSub);
        return subMapper.updateExpenseClaimSub(expenseClaimSub);
    }

    @Override
    public int deleteExpenseClaimSubByIds(String ids) {
        return subMapper.deleteExpenseClaimSubByIds(Convert.toStrArray(ids), getLoginName());
    }

    @Override
    public int deleteExpenseClaimSubById(Long id) {
        return subMapper.deleteExpenseClaimSubById(id, getLoginName());
    }

    @Override
    public boolean handlePermission(Long subId) {
        ExpenseClaimSub subData = subMapper.selectExpenseClaimSubById(subId);
        ExpenseClaim mainData = mainMapper.selectExpenseClaimById(subData.getExpenseClaimId());
        //主项结束,不可处理
        if (Integer.valueOf(Constants.MainStatus.COMPLETE).equals(mainData.getStatus())) {
            return false;
        }
        //当前用户是子项的指定处理人且子项处于待处理状态
        return getLoginName().equals(subData.getHandleBy())
                && Constants.SubStatus.PROCESSING.equals(subData.getStatus());
    }

    @Override
    public boolean submitPermission(Long subId) {
        ExpenseClaimSub subData = subMapper.selectExpenseClaimSubById(subId);
        ExpenseClaim mainData = mainMapper.selectExpenseClaimById(subData.getExpenseClaimId());
        //主项结束,不可处理
        if (Integer.valueOf(Constants.MainStatus.COMPLETE).equals(mainData.getStatus())) {
            return false;
        }
        //当前用户是子项创建人且子项处于未提交状态
        return getLoginName().equals(subData.getCreateBy())
                && Constants.SubStatus.UNSUBMIT.equals(subData.getStatus());
    }

    @Override
    public int deleteRelativeData(@NonNull Long[] ids) {
        assert (ids.length > 0);
        return subMapper.deleteRelativeData(ids, getLoginName());
    }

    @Override
    public int submitProcess(ExpenseClaimSub subData) {
        //防止覆盖
        subData.setCreateBy(null);
        //设置发起时间
        subData.setInitiateTime(DateUtils.getNowDate());
        //设置状态为处理中
        subData.setStatus(Constants.SubStatus.PROCESSING);

        ExpenseClaim mainData = new ExpenseClaim();
        //根据关联Id更新主表
        mainData.setId(subData.getExpenseClaimId());
        //设置主表进行中
        mainData.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        Integer result = transaction.execute(tranStatus -> {
            try {
                //更新子表为已处理
                return updateExpenseClaimSub(subData) +
                        //更新主表
                        mainService.updateExpenseClaim(mainData);
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("财务报销-子表-提交操作出现异常",e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    @Override
    public int handleProcess(ExpenseClaimSub subData) {
        //防止覆盖
        subData.setCreateBy(null);
        subData.setHandleBy(null);
        //设置处理时间
        subData.setHandleTime(DateUtils.getNowDate());

        ExpenseClaim mainData = new ExpenseClaim();
        //根据关联Id更新主表
        mainData.setId(subData.getExpenseClaimId());
        //设置主表进行中
        mainData.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        Integer result = transaction.execute(tranStatus -> {
            try {
                //更新子表为已处理
                return updateExpenseClaimSub(subData) +
                        //更新主表
                        mainService.updateExpenseClaim(mainData);
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("财务报销-子表-处理操作发生异常", e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    @Override
    public int selectByStatusList(@NonNull Integer[] statusList, Long relativeId) {
        assert (statusList.length > 0);
        return subMapper.selectByStatusList(statusList, relativeId);
    }

    @Override
    public ExpenseClaimSub addProcess(Long relativeId) {
        //验证主表还在进行状态/未提交状态
        ExpenseClaim main = mainMapper.selectExpenseClaimById(relativeId);
        ExpenseClaimSub sub = new ExpenseClaimSub();
        if (Objects.isNull(main)) {
            log.error("主表项不存在,不符合业务流程");
            sub.setStatus(-1);
            return sub;
        }
        if (main.getStatus().equals(Integer.valueOf(Constants.MainStatus.COMPLETE))) {
            log.debug("主表项已结束");
            sub.setStatus(Constants.SubStatus.PASSED);
            return sub;
        }
        //验证子表中是否存在未提交和未处理的申请
        Integer[] statusList = {Constants.SubStatus.PROCESSING, Constants.SubStatus.UNSUBMIT};
        if (selectByStatusList(statusList, relativeId) > 0) {
            log.debug("存在未提交/未处理的子表项");
            sub.setStatus(Constants.SubStatus.REJECTED);
            return sub;
        }
        final ExpenseClaim mainData = new ExpenseClaim();
        mainData.setId(relativeId);
        mainData.setStatus(Integer.valueOf(Constants.MainStatus.PROCESSING));
        return transaction.execute(tranStatus -> {
            try {
                ExpenseClaimSub subData = new ExpenseClaimSub();
                //设置关联依据
                subData.setExpenseClaimId(relativeId);
                //向子表中插入基本数据
                insertExpenseClaimSub(subData);
                //查询出新增的数据(填充处理人等信息)
                subData = selectExpenseClaimSubById(subData.getId());
                //修改主表为进行中(更改状态的同时,记录最后操作人)
                mainService.updateExpenseClaim(mainData);
                return subData;
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("财务报销-子表-新增操作发生异常", e);
                throw new RuntimeException(e);
            }
        });
    }
}
