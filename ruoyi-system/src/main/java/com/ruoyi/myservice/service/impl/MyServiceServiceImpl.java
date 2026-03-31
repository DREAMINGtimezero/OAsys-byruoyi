package com.ruoyi.myservice.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.myservice.domain.MyServiceSub;
import com.ruoyi.myservice.mapper.MyServiceSubMapper;
import com.ruoyi.myservice.service.MyServiceSubService;
import com.ruoyi.system.domain.SysPost;
import com.ruoyi.system.service.ISysPostService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.myservice.mapper.MyServiceMapper;
import com.ruoyi.myservice.domain.MyService;
import com.ruoyi.myservice.service.MyServiceService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 服务Service业务层处理
 *
 * @author YJ
 * @date 2025-07-09
 */
@Slf4j
@Service
public class MyServiceServiceImpl extends BaseServiceImpl implements MyServiceService {
    @Autowired
    private MyServiceMapper mainMapper;
    @Autowired
    private MyServiceSubMapper subMapper;
    @Autowired
    private ISysPostService sysPostService;
    @Autowired
    private MyServiceSubService subService;

    @Autowired
    TransactionTemplate transaction;

    /**
     * 查询服务
     *
     * @param id 服务主键
     * @return 服务
     */
    @Override
    public MyService selectMyServiceById(Long id) {
        MyService mainData = mainMapper.selectMyServiceById(id);
        List<MyService> list = new ArrayList<>(1);
        list.add(mainData);
        createByReplace(list);
        updateByReplace(list);
        return mainData;
    }

    /**
     * 查询服务列表
     *
     * @param myService 服务
     * @return 服务
     */
    @Override
    public List<MyService> selectMyServiceList(MyService myService) {
        myService = (MyService) initDomainCreate(myService);
        List<MyService> list = mainMapper.selectMyServiceList(myService);
        //为用户过滤未提交项
//        filterMainForHandler(list);
        //进行创建人映射
        createByReplace(list);
        //处理最后更新人
        updateByReplace(list);
        return list;
    }

    /**
     * 新增服务
     *
     * @param myService 服务
     * @return 结果
     */
    @Override
    public int insertMyService(MyService myService) {
        myService = (MyService) initDomainCreate(myService);
        MyService finalMyService = myService;
        /*子表数据*/
        MyServiceSub subData = new MyServiceSub();
        Integer result = transaction.execute(transactionStatus -> {
            int temp = mainMapper.insertMyService(finalMyService);
            subData.setServiceId(finalMyService.getId());
            return temp + subService.insertMyServiceSub(subData);
        });
        return result == null ? 0 : result;
    }

    /**
     * 修改服务
     *
     * @param myService 服务
     * @return 结果
     */
    @Override
    public int updateMyService(MyService myService) {
        //填充更新操作人
        myService = (MyService) initDomainUpdate(myService);
        return mainMapper.updateMyService(myService);
    }

    /**
     * 批量删除服务
     *
     * @param ids 需要删除的服务主键
     * @return 结果
     */
    @Override
    public int deleteMyServiceByIds(String ids) {
        //检查是否可以删除
        Long[] idList = Convert.toLongArray(ids);
        if (!isDeleteAble(idList)) {
            return 0;
        }
        Integer result = transaction.execute(tranStatus -> {
            try {
                return subService.deleteRelativeData(idList) +
                        mainMapper.deleteMyServiceByIds(idList, getLoginName());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("批量删除服务操作出现错误",e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    /**
     * 删除服务信息
     *
     * @param id 服务主键
     * @return 结果
     */
    @Override
    public int deleteMyServiceById(Long id) {
        if (!isDeleteAble(new Long[]{id})) {
            return 0;
        }
        Integer result = transaction.execute(tranStatus -> {
            try {
                return subService.deleteRelativeData(new Long[]{id}) +
                        mainMapper.deleteMyServiceById(id, getLoginName());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("批量删除服务操作出现错误",e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    @Override
    public boolean overAble(Long id) {
        MyService myService = selectMyServiceById(id);
        /*如果是费控内*/
        if (Constants.ONE == myService.getCostType()) {
            //判断是否有未结束、未处理的子项,如果所有子项已经被处理过了,才可以结束
            return subMapper.selectByStatusList(new Integer[]{Constants.SubStatus.UNSUBMIT, Constants.SubStatus.PROCESSING}, id) <= 0;
        }
        /*如果是费控外*/
        MyServiceSub conditions = new MyServiceSub();
        //查询出对应的子项(通过状态)
        conditions.setServiceId(id);
        conditions.setDeleteFlag(Constants.ZERO);
        conditions.setStatus(Constants.SubStatus.PASSED);
        List<MyServiceSub> subList = subMapper.selectMyServiceSubList(conditions);

        //查询总经理岗位的用户列表
        SysPost post = new SysPost();
        post.setPostCode(Constants.GENERAL_MANAGER_CODE);
        List<SysUser> userList = sysPostService.getUserListByPost(post);
        Set<String> loginNames = new HashSet<>(userList.size() * 4 / 3);
        userList.forEach(user -> loginNames.add(user.getLoginName()));

        for (MyServiceSub sub : subList) {
            //如果查出的子项中,包含被总经理职位的用户处理过的子项,进行下一步判断
            if (loginNames.contains(sub.getHandleBy())) {
                //判断是否有未结束、未处理的子项,如果所有子项已经被处理过了,才可以结束
                return subMapper.selectByStatusList(new Integer[]{Constants.SubStatus.UNSUBMIT, Constants.SubStatus.PROCESSING}, id) <= 0;
            }
        }
        return false;
    }

    @Override
    public boolean isDeleteAble(@NonNull Long[] idList) {
        return mainMapper.selectDeleteAble(idList) == idList.length;
    }
}
