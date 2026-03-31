package com.ruoyi.costmanagement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.costmanagement.domain.ExpenseClaimSub;
import com.ruoyi.costmanagement.service.ExpenseClaimSubService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.costmanagement.mapper.ExpenseClaimMapper;
import com.ruoyi.costmanagement.domain.ExpenseClaim;
import com.ruoyi.costmanagement.service.ExpenseClaimService;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 报销单主表Service业务层处理
 *
 * @author YJ
 * @date 2025-07-31
 */
@Slf4j
@Service
public class ExpenseClaimServiceImpl extends BaseServiceImpl implements ExpenseClaimService {
    @Autowired
    private ExpenseClaimMapper mainMapper;
    @Autowired
    private ExpenseClaimSubService subService;
    @Autowired
    private TransactionTemplate transaction;

    @Override
    public ExpenseClaim selectExpenseClaimById(Long id) {
        ExpenseClaim mainData = mainMapper.selectExpenseClaimById(id);
        if (Objects.isNull(mainData)) {
            return null;
        }
        List<ExpenseClaim> list = new ArrayList<>(1);
        list.add(mainData);
        createByReplace(list);
        updateByReplace(list);
        return mainData;
    }

    @Override
    public List<ExpenseClaim> selectExpenseClaimList(ExpenseClaim expenseClaim) {
        //将当前登录用户的信息记录
        initDomainCreate(expenseClaim);
        expenseClaim.setDeleteFlag(0);
        List<ExpenseClaim> list = mainMapper.selectExpenseClaimList(expenseClaim);
        //映射创建人
        createByReplace(list);
        //映射修改人
        updateByReplace(list);
        return list;
    }

    @Override
    public int updateExpenseClaim(ExpenseClaim expenseClaim) {
        initDomainUpdate(expenseClaim);
        return mainMapper.updateExpenseClaim(expenseClaim);
    }

    @Override
    public int deleteExpenseClaim(String ids) {
        Long[] idList = Convert.toLongArray(ids);
        if (!deleteAble(idList)) {
            return 0;
        }
        Integer result = transaction.execute(tranStatus -> {
            try {
                //删除相关的子表数据
                return subService.deleteRelativeData(idList) +
                        //删除主表数据
                        mainMapper.deleteExpenseClaimByIds(idList, getLoginName());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("财务报销-主表-删除 发生异常", e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    @Override
    public int insertExpenseClaim(ExpenseClaim mainData) {
        initDomainCreate(mainData);
        return mainMapper.insertExpenseClaim(mainData);
    }

    @Override
    public int addExpenseClaim(ExpenseClaim mainData) {
        //填充创建人
        ExpenseClaimSub subData = new ExpenseClaimSub();
        Integer result = transaction.execute(tranStatus -> {
            //向主表中插入数据
            int temp = insertExpenseClaim(mainData);
            //设置关联id
            subData.setExpenseClaimId(mainData.getId());
            //向子表中插入数据
            return temp + subService.insertExpenseClaimSub(subData);
        });
        return result == null ? 0 : result;
    }

    @Override
    public boolean overAble(Long id) {
        Integer[] statusList = {Constants.SubStatus.UNSUBMIT, Constants.SubStatus.PROCESSING};
        //没有未提交和待处理的子项
        return subService.selectByStatusList(statusList, id) <= 0;
    }

    @Override
    public boolean deleteAble(Long[] idList) {
        return mainMapper.selectDeleteAble(idList) == idList.length;
    }

    @Override
    public boolean editAble(Long id) {
        //只能编辑未提交的项
        ExpenseClaim mainData = selectExpenseClaimById(id);
        return mainData.getStatus().equals(Integer.valueOf(Constants.MainStatus.UNSUBMIT));
    }

}
