package com.ruoyi.base.service;

import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * @Description  用于数据处理，远程调用
 * @Author wangshilin
 * @Date 2025/5/16 15:10
 * @Param 
 * @return 
 **/
public interface BaseService {

    /**
     * @Description  封装创建对象
     * @Author wangshilin
     * @Date 2025/6/6 09:07
     **/
    BaseEntity initDomainCreate(BaseEntity object);

    /**
     * @Description  封装修改对象
     * @Author wangshilin
     * @Date 2025/6/6 09:07
     **/
    BaseEntity initDomainUpdate(Object object);

    /**
     * @Description  根据创建人（登陆账号），替换掉创建人createBy，创建人部门createDept，创建人岗位createPost
     * @Author wangshilin
     * @Date 2025/6/25 11:15
     **/
    void createByReplace(List<?> list);

    /**
     * @Description  根据处理人（登陆账号），替换掉处理人handleBy，处理人部门handleDept，处理人岗位handlePost
     * @Author wangshilin
     * @Date 2025/6/25 11:15
     **/
    void handleByReplace(List<?> list);

    /**
     * @Description  根据更新人（登陆账号），替换掉更新人updateBy
     * @Author wangshilin
     * @Date 2025/6/25 11:15
     **/
    void updateByReplace(List<?> list);

    /**
     * 过滤不能暴露给处理人的数据,方法应该在进行用户的loginName与userName映射之前调用
     * @param list 待过滤数据(数据库中的原始数据)
     */
    void filterSubForHandler(List<? extends BaseEntity> list);

    /**
     * 过滤不能暴露给处理人的数据,方法应该在进行用户的loginName与userName映射之前调用
     * @param list 待过滤数据(数据库中的原始数据)
     */
    void filterMainForHandler(List<? extends BaseEntity> list);

    /**
     * 获取当前登陆用户的登陆名
     * @return 登陆名
     */
    String getLoginName();
}
