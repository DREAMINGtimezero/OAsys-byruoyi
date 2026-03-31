package com.ruoyi.base.service.impl;

import com.ruoyi.base.service.BaseService;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.mapper.SysUserPostMapper;
import org.apache.commons.collections.MapUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wangshilin
 */
@Transactional
public class BaseServiceImpl implements BaseService {

    /**
     * 定义全局session对象
     */
    private SysUser sysUser;

    @Autowired
    private SysUserPostMapper sysUserPostMapper;

    @Override
    public BaseEntity initDomainCreate(BaseEntity baseEntity) {
        sysUser = ShiroUtils.getSysUser();
        if(!baseEntity.getIsCeo()){
            //  强转数据对象为baseEntity
            baseEntity.setCreateBy(sysUser.getLoginName());
        }
        //  返回数据对象
        return baseEntity;
    }

    @Override
    public BaseEntity initDomainUpdate(Object object) {
        sysUser = ShiroUtils.getSysUser();
        //  强转数据对象为baseEntity
        BaseEntity baseEntity = (BaseEntity) object;
        baseEntity.setUpdateBy(sysUser.getLoginName());
        //  返回数据对象
        return baseEntity;
    }

    @Override
    public void createByReplace(List<?> list) {
        if (list.isEmpty()) return;
        List<Map> dataList = sysUserPostMapper.selectAllUserDeptPost();
        try {
            for (Object entity : list) {
                Class<?> clazz = entity.getClass();
                Field attributeField = null;
                try {
                    attributeField = clazz.getDeclaredField("createBy");
                } catch (NoSuchFieldException e) {
                    Class<?> superClass = clazz.getSuperclass();
                    if (superClass != null) {
                        attributeField = superClass.getDeclaredField("createBy");
                    }
                }
                attributeField.setAccessible(true);
                String createBy = StringUtils.toString(attributeField.get(entity));
                Method setCreateBy = clazz.getSuperclass().getDeclaredMethod("setCreateBy", String.class);
                Method setCreateDept = clazz.getSuperclass().getDeclaredMethod("setCreateDept", String.class);
                Method setCreatePost = clazz.getSuperclass().getDeclaredMethod("setCreatePost", String.class);
                for (Map map : dataList) {
                    if (MapUtils.getString(map, "loginName").equals(createBy)) {
                        setCreateBy.invoke(entity, MapUtils.getString(map, "userName"));
                        setCreateDept.invoke(entity, MapUtils.getString(map, "deptName"));
                        setCreatePost.invoke(entity, MapUtils.getString(map, "postName"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleByReplace(List<?> list) {
        if (list.isEmpty()) return;
        List<Map> dataList = sysUserPostMapper.selectAllUserDeptPost();
        try {
            for (Object entity : list) {
                Class<?> clazz = entity.getClass();
                Field attributeField = clazz.getDeclaredField("handleBy");
                attributeField.setAccessible(true);
                String createBy = StringUtils.toString(attributeField.get(entity));
                Method setHandleBy = clazz.getSuperclass().getDeclaredMethod("setHandleBy", String.class);
                Method setHandleDept = clazz.getSuperclass().getDeclaredMethod("setHandleDept", String.class);
                Method setHandlePost = clazz.getSuperclass().getDeclaredMethod("setHandlePost", String.class);
                for (Map map : dataList) {
                    if (MapUtils.getString(map, "loginName").equals(createBy)) {
                        setHandleBy.invoke(entity, MapUtils.getString(map, "userName"));
                        setHandleDept.invoke(entity, MapUtils.getString(map, "deptName"));
                        setHandlePost.invoke(entity, MapUtils.getString(map, "postName"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateByReplace(List<?> list) {
        if (list.isEmpty()) return;
        List<Map> dataList = sysUserPostMapper.selectAllUserDeptPost();
        try {
            for (Object entity : list) {
                Class<?> clazz = entity.getClass();
                Field attributeField = null;
                try {
                    attributeField = clazz.getDeclaredField("updateBy");
                } catch (NoSuchFieldException e) {
                    Class<?> superClass = clazz.getSuperclass();
                    if (superClass != null) {
                        attributeField = superClass.getDeclaredField("updateBy");
                    }
                }
                attributeField.setAccessible(true);
                String createBy = StringUtils.toString(attributeField.get(entity));
                Method setHandleBy = clazz.getSuperclass().getDeclaredMethod("setUpdateBy", String.class);
                for (Map map : dataList) {
                    if (MapUtils.getString(map, "loginName").equals(createBy)) {
                        setHandleBy.invoke(entity, MapUtils.getString(map, "userName"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filterSubForHandler(List<? extends BaseEntity> list) {
        if(Objects.isNull(list)||list.isEmpty())
        {
            return;
        }
        try {
            String loginName = ShiroUtils.getLoginName();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                Object data = it.next();
                Class<?> clazz = data.getClass();
                Field field = clazz.getSuperclass().getDeclaredField("createBy");
                field.setAccessible(true);
                String createBy = field.get(data).toString();
                field = clazz.getDeclaredField("status");
                field.setAccessible(true);
                Integer status = (Integer) field.get(data);
                //数据还未提交，且当前登录用户不是创建者
                if (status.equals(Constants.SubStatus.UNSUBMIT) && !loginName.equals(createBy)) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filterMainForHandler(List<? extends BaseEntity> list) {
        if(Objects.isNull(list)||list.isEmpty())
        {
            return;
        }
        try {
            String loginName = ShiroUtils.getLoginName();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                Object data = it.next();
                Class<?> clazz = data.getClass();
                Field field = clazz.getSuperclass().getDeclaredField("createBy");
                field.setAccessible(true);
                String createBy = field.get(data).toString();
                field = clazz.getDeclaredField("status");
                field.setAccessible(true);
                String status = (String) field.get(data);
                //数据还未提交，且当前登录用户不是创建者
                if (status.equals(Constants.MainStatus.UNSUBMIT) && !loginName.equals(createBy)) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public String getLoginName() {
        return ShiroUtils.getLoginName();
    }
}
