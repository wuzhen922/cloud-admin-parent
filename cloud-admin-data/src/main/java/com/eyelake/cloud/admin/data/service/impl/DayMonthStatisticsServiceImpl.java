package com.eyelake.cloud.admin.data.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.*;
import com.eyelake.cloud.admin.data.dto.StatisticsDto;
import com.eyelake.cloud.admin.data.service.intf.DayMonthStatisticsService;
import com.eyelake.cloud.common.constants.StatisticsConstants;
import com.eyelake.framework.utils.DateUtil;
import com.yjh.framework.core.trace.ServiceTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wff on 2018/1/25.
 */

@Service("dayMonthStatisticsService")
@ServiceTrace
public class DayMonthStatisticsServiceImpl implements DayMonthStatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(DayMonthStatisticsServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public void dayMonthOwnerStatistics(OwnerDmo ownerDmo) {
        //获取昨天的日期
        Date yesterday  = DateUtil.beforeDate(DateUtil.getDate(),1);

        StatisticsDto ownerDayDmo = new StatisticsDto();
        ownerDayDmo.setOwnerId(ownerDmo.getId());
        ownerDayDmo.setIntegratorId(ownerDmo.getIntegratorId());
        ownerDayDmo.setStatisticsDate(yesterday);

        //业主企业日流量
        StatisticsDto statisticsDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsOwnerDayTraffic",ownerDayDmo);

        //查昨天是否有记录
        OwnerDayDmo ownerDayDmo1 = (OwnerDayDmo)commonDao.selectOne("StatisticsMapper.queryOwnerDayTraffic",ownerDayDmo);

        Double dayTraffic = 0D;

        if (null != statisticsDto && null != statisticsDto.getUsedTraffic()) {
            dayTraffic = statisticsDto.getUsedTraffic();
        }

        OwnerDayDmo addOrUpdateDayDmo ;
        if(null == ownerDayDmo1){
            addOrUpdateDayDmo = new OwnerDayDmo();
            addOrUpdateDayDmo.setIntegratorId(ownerDmo.getIntegratorId());
            addOrUpdateDayDmo.setOwnerId(ownerDmo.getId());
            addOrUpdateDayDmo.setDayDate(yesterday);
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("业主企业插入日使用流量失败！");
            }
        }else {
            addOrUpdateDayDmo = new OwnerDayDmo();
            addOrUpdateDayDmo.setId(ownerDayDmo1.getId());
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("业主企业更新日使用流量失败！");
            }
        }

        //    业主企业月流量
        StatisticsDto statisticsMonthDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsOwnerMonthTraffic",ownerDayDmo);
       //   查业主企业月流量表中是否有该月份的流量
        OwnerMonthDmo ownerMonthDmo = (OwnerMonthDmo) commonDao.selectOne("StatisticsMapper.queryOwnerMonthTraffic",ownerDayDmo);
        //若没有就插入
        Double monthTraffic = 0D;
        Long monthPackTraffic = 0L;
        if (null != statisticsMonthDto && null != statisticsMonthDto.getUsedTraffic()) {
            monthTraffic = statisticsMonthDto.getUsedTraffic();
        }
        if (null != statisticsMonthDto && null != statisticsMonthDto.getPackTraffic()) {
            monthPackTraffic = statisticsMonthDto.getPackTraffic();
        }

        OwnerMonthDmo addOrUpdateDmo ;
        if(null == ownerMonthDmo){
            addOrUpdateDmo = new OwnerMonthDmo();
            addOrUpdateDmo.setOwnerId(ownerDmo.getId());
            addOrUpdateDmo.setIntegratorId(ownerDmo.getIntegratorId());
            addOrUpdateDmo.setMonthDate(yesterday);
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDmo);
            if (i < 0){
                logger.error("业主企业插入月使用流量失败！");
            }
        }else {
            addOrUpdateDmo = new OwnerMonthDmo();
            addOrUpdateDmo.setId(ownerMonthDmo.getId());
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDmo);
            if (i < 0){
                logger.error("业主企业更新月使用流量失败！");
            }
        }

    }

    @Override
    public void dayMonthIntegratorStatistics(IntegratorDmo integratorDmo) {
        //获取昨天的日期
        Date yesterday  = DateUtil.beforeDate(DateUtil.getDate(),1);

        StatisticsDto integratorDayMonthDmo = new StatisticsDto();
        integratorDayMonthDmo.setIntegratorId(integratorDmo.getId());
        integratorDayMonthDmo.setStatisticsDate(yesterday);

        //查昨天是否有记录
        IntegratorDayDmo integratorDayDmo = (IntegratorDayDmo)commonDao.selectOne("StatisticsMapper.queryIntegratorDayTraffic",integratorDayMonthDmo);

        //集成商日流量（对rtu流量进行累和）
        StatisticsDto statisticsDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsDayTraffic",integratorDayMonthDmo);

        Double dayTraffic = 0D;

        if (null != statisticsDto && null != statisticsDto.getUsedTraffic()) {
            dayTraffic = statisticsDto.getUsedTraffic();
        }

        IntegratorDayDmo addOrUpdateDayDmo ;
        if(null == integratorDayDmo){
            addOrUpdateDayDmo = new IntegratorDayDmo();
            addOrUpdateDayDmo.setIntegratorId(integratorDmo.getId());
            addOrUpdateDayDmo.setDayDate(yesterday);
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("集成商插入日使用流量失败！");
            }
        }else {
            addOrUpdateDayDmo = new IntegratorDayDmo();
            addOrUpdateDayDmo.setId(integratorDayDmo.getId());
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("集成商更新日使用流量失败！");
            }
        }


        //    集成商月流量
        StatisticsDto statisticsMonthDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsMonthTraffic",integratorDayMonthDmo);
        //   查集成商月流量表中是否有该月份的流量
        IntegratorMonthDmo integratorMonthDmo = (IntegratorMonthDmo) commonDao.selectOne("StatisticsMapper.queryIntegratorMonthTraffic",integratorDayMonthDmo);
        //若没有就插入
        Double monthTraffic = 0D;
        Long monthPackTraffic = 0L;
        if (null != statisticsMonthDto && null != statisticsMonthDto.getUsedTraffic()) {
            monthTraffic = statisticsMonthDto.getUsedTraffic();
        }
        if (null != statisticsMonthDto && null != statisticsMonthDto.getPackTraffic()) {
            monthPackTraffic = statisticsMonthDto.getPackTraffic();
        }

        IntegratorMonthDmo addOrUpdateDmo ;
        if(null == integratorMonthDmo){
            addOrUpdateDmo = new IntegratorMonthDmo();
            addOrUpdateDmo.setIntegratorId(integratorDmo.getId());
            addOrUpdateDmo.setMonthDate(yesterday);
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDmo);
            if (i < 0){
                logger.warn("集成商插入月使用流量失败！");
            }
        }else {
            addOrUpdateDmo = new IntegratorMonthDmo();
            addOrUpdateDmo.setId(integratorMonthDmo.getId());
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDmo);
            if (i < 0){
                logger.warn("集成商更新月使用流量失败！");
            }
        }
    }

    @Override
    public void dayMonthSystemStatistics() {
        //获取昨天的日期
        Date yesterday  = DateUtil.beforeDate(DateUtil.getDate(),1);

        StatisticsDto systemDayMonthDto = new StatisticsDto();
        systemDayMonthDto.setStatisticsDate(yesterday);

        //云平台日流量
        StatisticsDto statisticsDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsDayTraffic",systemDayMonthDto);

        //查昨天是否有记录
        SystemDayDmo systemDayDmo = (SystemDayDmo)commonDao.selectOne("StatisticsMapper.querySystemDayTraffic",systemDayMonthDto);

        Double dayTraffic = 0D;

        if (null != statisticsDto && null != statisticsDto.getUsedTraffic()) {
            dayTraffic = statisticsDto.getUsedTraffic();
        }

        SystemDayDmo addOrUpdateDayDmo ;
        if(null == systemDayDmo){
            addOrUpdateDayDmo = new SystemDayDmo();
            addOrUpdateDayDmo.setDayDate(yesterday);
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDayDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("云平台插入日使用流量失败！");
            }
        }else {
            addOrUpdateDayDmo = new SystemDayDmo();
            addOrUpdateDayDmo.setId(systemDayDmo.getId());
            addOrUpdateDayDmo.setDayTraffic(dayTraffic);
            addOrUpdateDayDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDayDmo);
            if (i < 0){
                logger.error("云平台更新日使用流量失败！");
            }
        }

        //    云平台月流量
        StatisticsDto statisticsMonthDto = (StatisticsDto)commonDao.selectOne("StatisticsMapper.statisticsMonthTraffic",systemDayMonthDto);
        //   查云平台月流量表中是否有该月份的流量
        SystemMonthDmo systemMonthDmo = (SystemMonthDmo) commonDao.selectOne("StatisticsMapper.querySystemMonthTraffic",systemDayMonthDto);
        //若没有就插入
        Double monthTraffic = 0D;
        Long monthPackTraffic = 0L;
        if (null != statisticsMonthDto && null != statisticsMonthDto.getUsedTraffic()) {
            monthTraffic = statisticsMonthDto.getUsedTraffic();
        }
        if (null != statisticsMonthDto && null != statisticsMonthDto.getPackTraffic()) {
            monthPackTraffic = statisticsMonthDto.getPackTraffic();
        }

        SystemMonthDmo addOrUpdateDmo ;
        if(null == systemMonthDmo){
            addOrUpdateDmo = new SystemMonthDmo();
            addOrUpdateDmo.setMonthDate(yesterday);
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDmo);
            if (i < 0){
                logger.error("云平台插入月使用流量失败！");
            }
        }else {
            addOrUpdateDmo = new SystemMonthDmo();
            addOrUpdateDmo.setId(systemMonthDmo.getId());
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            int i = commonDao.update(addOrUpdateDmo);
            if (i < 0){
                logger.error("云平台更新月使用流量失败！");
            }
        }
    }


    @Override
    public List<OwnerDmo> queryOwnerList() {
        //    查所有业主企业
        OwnerDmo ownerDmo = new OwnerDmo();
        List<OwnerDmo> ownerDmoList = commonDao.selectList("StatisticsMapper.queryOwnerList",ownerDmo);

        return ownerDmoList;
    }

    @Override
    public List<IntegratorDmo> queryIntegratorList() {
        //    查所有集成商
        OwnerDmo ownerDmo = new OwnerDmo();
        List<IntegratorDmo> integratorList = commonDao.selectList("StatisticsMapper.queryIntegratorList",ownerDmo);

        return integratorList;
    }


}
