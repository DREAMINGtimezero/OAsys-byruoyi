package com.ruoyi.technicalaggrement.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.ruoyi.base.service.impl.BaseServiceImpl;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreementSub;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementSubService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.ruoyi.technicalaggrement.mapper.TechnicalAgreementMapper;
import com.ruoyi.technicalaggrement.domain.TechnicalAgreement;
import com.ruoyi.technicalaggrement.service.TechnicalAgreementService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 技术协议Service业务层处理
 *
 * @author YJ
 * @date 2025-06-24
 */
@Slf4j
@Service
public class TechnicalAgreementServiceImpl extends BaseServiceImpl implements TechnicalAgreementService {
    @Autowired
    private TechnicalAgreementMapper mainMapper;
    @Autowired
    private TechnicalAgreementSubService subService;
    @Autowired
    private TransactionTemplate transaction;

    /**
     * 查询技术协议
     *
     * @param id 技术协议主键
     * @return 技术协议
     */
    @Override
    public TechnicalAgreement selectTechnicalAgreementById(Long id) {
        TechnicalAgreement mainData = mainMapper.selectTechnicalAgreementById(id);
        if (Objects.isNull(mainData)) {
            return null;
        }
        List<TechnicalAgreement> list = new ArrayList<>(1);
        list.add(mainData);
        createByReplace(list);
        updateByReplace(list);
        return mainData;
    }

    /**
     * 查询技术协议列表
     *
     * @param technicalAgreement 技术协议
     * @return 技术协议
     */
    @Override
    public List<TechnicalAgreement> selectTechnicalAgreementList(@NonNull TechnicalAgreement technicalAgreement) {
        //为查询条件填充createBy
        technicalAgreement = (TechnicalAgreement) initDomainCreate(technicalAgreement);
        List<TechnicalAgreement> list = mainMapper.selectTechnicalAgreementList(technicalAgreement);
        //为用户过滤未提交项(现在已经在sql中实现)
//        filterMainForHandler(list);
        //进行创建人映射
        createByReplace(list);
        //处理最后更新人
        updateByReplace(list);
        return list;
    }

    /**
     * 新增技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertTechnicalAgreement(@NonNull TechnicalAgreement technicalAgreement) {
        //填充发起人/数据创建人
        technicalAgreement = (TechnicalAgreement) initDomainCreate(technicalAgreement);
        int result = mainMapper.insertTechnicalAgreement(technicalAgreement);
        /*插入子表*/
        TechnicalAgreementSub subData = new TechnicalAgreementSub();
        subData.setTechnicalAgreementId(technicalAgreement.getId());
        return result + subService.insertTechnicalAgreementSub(subData);
    }


    /**
     * 修改技术协议
     *
     * @param technicalAgreement 技术协议
     * @return 结果
     */
    @Override
    public int updateTechnicalAgreement(@NonNull TechnicalAgreement technicalAgreement) {
        //设置修改者
        technicalAgreement = (TechnicalAgreement) initDomainUpdate(technicalAgreement);
        return mainMapper.updateTechnicalAgreement(technicalAgreement);
    }

    /**
     * 批量删除技术协议
     *
     * @param ids 需要删除的技术协议主键
     * @return 结果
     */
    @Override
    public int deleteTechnicalAgreementByIds(@NonNull String ids) {
        Long[] idList = Convert.toLongArray(ids);
        if (!isDeleteAble(idList)) {
            return 0;
        }
        Integer result = transaction.execute(tranStatus -> {
            try {
                //删除子表相关数据
                return subService.deleteRelativeData(idList) +
                        //删除主表数据
                        mainMapper.deleteTechnicalAgreementByIds(idList, getLoginName());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("技术协议主表批量删除操作发生未知异常", e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }

    /**
     * 删除技术协议信息
     *
     * @param id 技术协议主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteTechnicalAgreementById(Long id) {
        if (!isDeleteAble(new Long[]{id})) {
            return 0;
        }
        Integer result = transaction.execute(tranStatus -> {
            try {
                return subService.deleteRelativeData(new Long[]{id}) +
                        mainMapper.deleteTechnicalAgreementById(id, getLoginName());
            } catch (Exception e) {
                tranStatus.setRollbackOnly();
                log.error("技术协议主表删除操作发生未知异常", e);
                return 0;
            }
        });
        return result == null ? 0 : result;
    }


    /**
     * 检测是否可以结束
     *
     * @param id 主表的主键id
     * @return 是否可以结束
     */
    @Override
    public boolean checkOverAble(@NonNull Long id) {
        //如果子表中存在未提交和进行中的项,则不能结束
        int num = subService.selectByStatuses(new Integer[]{Constants.SubStatus.UNSUBMIT, Constants.SubStatus.PROCESSING}, id);
        return num <= 0;
    }

    @Override
    public String getLastEditor(Long id) {
        TechnicalAgreement mainData = mainMapper.selectTechnicalAgreementById(id);
        String result = mainData.getUpdateBy();
        //如果该数据的最后更新人为空,则返回创建人
        if (StringUtils.isEmpty(result)) {
            result = mainData.getCreateBy();
        }
        return result;
    }

    @Override
    public boolean isDeleteAble(@lombok.NonNull Long[] idList) {
        return mainMapper.selectDeleteAble(idList) == idList.length;
    }
}
