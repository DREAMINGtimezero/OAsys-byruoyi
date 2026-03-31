package com.ruoyi.myservice.service.impl;

import java.util.*;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.myservice.domain.MyService;
import com.ruoyi.myservice.mapper.MyServiceMapper;
import com.ruoyi.myservice.service.MyServiceService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.ruoyi.myservice.mapper.MyServiceSubMapper;
import com.ruoyi.myservice.domain.MyServiceSub;
import com.ruoyi.myservice.service.MyServiceSubService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 服务子表Service业务层处理
 *
 * @author YJ
 * @date 2025-07-09
 */
@Slf4j
@Service
public class MyServiceSubServiceImpl extends BaseServiceImpl implements MyServiceSubService {
    @Autowired
    private MyServiceSubMapper subMapper;

    @Autowired
    private MyServiceMapper mainMapper;

    @Autowired
    TransactionTemplate transaction;

    @Autowired
    private MyServiceService mainService;

    /**
     * 查询服务子表
     *
     * @param id 服务子表主键
     * @return 服务子表
     */
    @Override
    public MyServiceSub selectMyServiceSubById(Long id) {
        MyServiceSub data = subMapper.selectMyServiceSubById(id);
        if (data == null) {
            return null;
        }
        List<MyServiceSub> list = new ArrayList<>(1);
        list.add(data);
        createByReplace(list);
        handleByReplace(list);
        return list.get(0);
    }

    /**
     * 查询服务子表列表
     *
     * @param myServiceSub 服务子表
     * @return 服务子表
     */
    @Override
    public List<MyServiceSub> selectMyServiceSubList(MyServiceSub myServiceSub) {
        List<MyServiceSub> list = subMapper.selectMyServiceSubList(myServiceSub);
        if (!list.isEmpty()) {
            //为当前登录用户过滤未提交数据
            filterSubForHandler(list);
            /*
            //获取权限
            Map<Long, Boolean> submitPermission = new HashMap<>(list.size() * 4 / 3);
            Map<Long, Boolean> handlePermission = new HashMap<>(list.size() * 4 / 3);
            assert Objects.nonNull(myServiceSub.getServiceId());
            MyService mainData = mainMapper.selectMyServiceById(myServiceSub.getServiceId());
            list.forEach(data -> {
                submitPermission.put(data.getId(), submitPermission(mainData, data));
                handlePermission.put(data.getId(), handlePermission(mainData, data));
            });
            list.get(0).setSubmitPermission(submitPermission);
            list.get(0).setHandlePermission(handlePermission);
            */
            //进行发起人的登录名与用户名的映射
            createByReplace(list);
            //进行处理的登录名与用户名的映射
            handleByReplace(list);
        }
        return list;
    }

    /**
     * 新增服务子表
     *
     * @param myServiceSub 服务子表
     * @return 结果
     */
    @Override
    public int insertMyServiceSub(MyServiceSub myServiceSub) {
        //填充创建人
        initDomainCreate(myServiceSub);
        return subMapper.insertMyServiceSub(myServiceSub);
    }

    /**
     * 修改服务子表
     *
     * @param myServiceSub 服务子表
     * @return 结果
     */
    @Override
    public int updateMyServiceSub(MyServiceSub myServiceSub) {
        //填充修改人
        initDomainUpdate(myServiceSub);
        return subMapper.updateMyServiceSub(myServiceSub);
    }

    /**
     * 批量删除服务子表
     *
     * @param ids 需要删除的服务子表主键
     * @return 结果
     */
    @Override
    public int deleteMyServiceSubByIds(String ids) {
        return subMapper.deleteMyServiceSubByIds(Convert.toStrArray(ids), getLoginName());
    }

    /**
     * 删除服务子表信息
     *
     * @param id 服务子表主键
     * @return 结果
     */
    @Override
    public int deleteMyServiceSubById(Long id) {
        return subMapper.deleteMyServiceSubById(id, getLoginName());
    }

    @Override
    public MyServiceSub add(Long relativeId) {
        MyServiceSub sub = new MyServiceSub();
        /*操作权限校验*/
        //如果主表结束,不能操作
        MyService mainData = mainMapper.selectMyServiceById(relativeId);
        if (Objects.isNull(mainData)) {
            log.error("主表项不存在,不符合业务流程!");
            sub.setStatus(-1);
            return sub;
        }
        if (Constants.MainStatus.COMPLETE.equals(mainData.getStatus())) {
            log.debug("主表项已结束!");
            sub.setStatus(Constants.SubStatus.PASSED);
            return sub;
        }
        //如果子表中存在未提交和未处理的项,不能操作
        Integer[] statusList = {Constants.SubStatus.UNSUBMIT, Constants.SubStatus.PROCESSING};
        if (searchByStatusList(statusList, relativeId) > 0) {
            log.debug("存在未提交/未处理的流程");
            sub.setStatus(Constants.SubStatus.REJECTED);
            return sub;
        }

        /*子表待插入数据*/
        //设置关联id
        sub.setServiceId(relativeId);
        //设置状态为未提交
        sub.setStatus(Constants.SubStatus.UNSUBMIT);

        /*主表待修改数据*/
        MyService conditions = new MyService();
        //更新查询条件
        conditions.setId(relativeId);
        //设置为进行中
        conditions.setStatus(Constants.MainStatus.PROCESSING);

        return transaction.execute(tranStatus -> {
            try {
                //插入子表
                insertMyServiceSub(sub);
                //更新主表
                mainService.updateMyService(conditions);
                //从数据查询子表插入结果
                return selectMyServiceSubById(sub.getId());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.debug("添加服务操作发生异常", e);
                throw e;
            }
        });
    }

    @Override
    public int searchByStatusList(@NonNull Integer[] statusList, @NonNull Long relativeId) {
        assert (statusList.length > 0);
        return subMapper.selectByStatusList(statusList, relativeId);
    }

    @Override
    public Integer submitProcess(@NonNull MyServiceSub sub) {
        /*子表更新数据*/
        //防止覆盖
        sub.setCreateBy(null);
        //设置子表状态为进行中
        sub.setStatus(Constants.SubStatus.PROCESSING);
        sub.setInitiateTime(DateUtils.getNowDate());

        /*主表更新数据*/
        MyService conditions = new MyService();
        assert (Objects.nonNull(sub.getServiceId()));
        conditions.setId(sub.getServiceId());
        conditions.setStatus(Constants.MainStatus.PROCESSING);
        return transaction.execute(tranStatus -> {
            try {
                int result = updateMyServiceSub(sub);
                result += mainService.updateMyService(conditions);
                return result;
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.debug("服务子项提交操作发生异常", e);
                return 0;
            }
        });
    }

    @Override
    public Integer handleProcess(@NonNull MyServiceSub sub) {
        sub.setCreateBy(null);
        sub.setHandleBy(null);
        sub.setHandleTime(DateUtils.getNowDate());
        //主表更新数据
        MyService conditions = new MyService();
        assert (Objects.nonNull(sub.getServiceId()));
        conditions.setId(sub.getServiceId());
        conditions.setStatus(Constants.MainStatus.PROCESSING);
        return transaction.execute(tranStatus -> {
            try {
                /*更新子表*/
                int result = updateMyServiceSub(sub);
                /*更新主表*/
                return result + mainService.updateMyService(conditions);
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                throw e;
            }
        });

    }

    @Override
    public boolean submitPermission(@NonNull Long subId) {
        //1.主表进行中/未提交,2.子表项待处理,3.用户为创建者
        MyServiceSub subData = subMapper.selectMyServiceSubById(subId);
        MyService mainData = mainMapper.selectMyServiceById(subData.getServiceId());
        return submitPermission(mainData, subData);
    }

    @Override
    public boolean submitPermission(@NonNull MyService mainData, @NonNull MyServiceSub subData) {
        //1.主表进行中/未提交,2.子表项待处理,3.用户为创建者
        return (Constants.MainStatus.PROCESSING.equals(mainData.getStatus()) || Constants.MainStatus.UNSUBMIT.equals(mainData.getStatus()))
                && Constants.SubStatus.UNSUBMIT.equals(subData.getStatus())
                && getLoginName().equals(subData.getCreateBy());
    }

    @Override
    public boolean handlePermission(@NonNull Long subId) {
        //1.主表进行中,2.子表项待处理,3.用户为处理人
        MyServiceSub subData = subMapper.selectMyServiceSubById(subId);
        MyService mainData = mainMapper.selectMyServiceById(subData.getServiceId());
        return handlePermission(mainData, subData);
    }

    @Override
    public boolean handlePermission(@NonNull MyService mainData, @NonNull MyServiceSub subData) {
        //1.主表进行中,2.子表项待处理,3.用户为处理人
        return Constants.MainStatus.PROCESSING.equals(mainData.getStatus())
                && Constants.SubStatus.PROCESSING.equals(subData.getStatus())
                && getLoginName().equals(subData.getHandleBy());
    }

    @Override
    public int deleteRelativeData(@NonNull Long[] ids) {
        assert(ids.length>0);
        return subMapper.deleteRelativeData(ids,getLoginName());
    }
}
