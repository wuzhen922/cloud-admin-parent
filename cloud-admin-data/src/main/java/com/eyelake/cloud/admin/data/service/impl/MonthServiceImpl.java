package com.eyelake.cloud.admin.data.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.*;
import com.eyelake.cloud.admin.data.service.intf.MonthService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.framework.core.trace.ServiceTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author:xudajan
 * @date:2018/1/25
 */
@Service("monthService")
@ServiceTrace
public class MonthServiceImpl implements MonthService {

    private static final Logger logger = LoggerFactory.getLogger(MonthServiceImpl.class);


    @Autowired
    private CommonDao commonDao;

    @Override
    public void rtuYearTrafficTable(RtuDmo rtuDmo) {
        RtuYearDmo rtuYearDmo = new RtuYearDmo();
        rtuYearDmo.setSnNumber(rtuDmo.getSnNumber());
        rtuYearDmo.setStatus(Constants.TrafficStatus.NORMAL);
        RtuYearDmo rtuYearDmo1 = (RtuYearDmo) commonDao.selectOne("MonthTaskMapper.queryRtuLastMonthYearTraffic", rtuYearDmo);
        rtuYearDmo.setYearDate(getLastMonthYear());
        RtuYearDmo existRtuYearDmo = (RtuYearDmo) commonDao.selectOne(rtuYearDmo);
        if (null != rtuYearDmo1) {
            if (null == existRtuYearDmo) {
                rtuYearDmo.setIntegratorId(rtuYearDmo1.getIntegratorId());
                rtuYearDmo.setYearTraffic(rtuYearDmo1.getYearTraffic());
                rtuYearDmo.setCreateTime(new Date());
                rtuYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(rtuYearDmo);
                if (1 != i) {
                    logger.error("插入网关年流量信息失败");
                }
            } else {
                existRtuYearDmo.setYearTraffic(rtuYearDmo1.getYearTraffic());
                existRtuYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.update(existRtuYearDmo);
                if (1 != i) {
                    logger.error("更新网关年流量信息失败");
                }
            }
        }
        else {
            if (null == existRtuYearDmo) {
                rtuYearDmo.setIntegratorId(rtuDmo.getIntegratorId());
                rtuYearDmo.setYearDate(getLastMonthYear());
                rtuYearDmo.setYearTraffic(0d);
                rtuYearDmo.setCreateTime(new Date());
                rtuYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(rtuYearDmo);
                if (1 != i) {
                    logger.error("插入网关年流量信息失败");
                }
            }
        }

    }

    @Override
    public void ownerYearTrafficTable(OwnerDmo ownerDmo) {

        OwnerYearDmo ownerYearDmo = new OwnerYearDmo();
        ownerYearDmo.setOwnerId(ownerDmo.getId());
        ownerYearDmo.setStatus(Constants.TrafficStatus.NORMAL);
        OwnerYearDmo ownerYearDmo1 = (OwnerYearDmo) commonDao.selectOne("MonthTaskMapper.queryOwnerLastMonthYearTraffic", ownerYearDmo);
        ownerYearDmo.setYearDate(getLastMonthYear());
        OwnerYearDmo existOwnerYearDmo = (OwnerYearDmo) commonDao.selectOne(ownerYearDmo);
        if (null != ownerYearDmo1) {
            if (null == existOwnerYearDmo) {
                ownerYearDmo.setIntegratorId(ownerYearDmo1.getIntegratorId());
                ownerYearDmo.setYearTraffic(ownerYearDmo1.getYearTraffic());
                ownerYearDmo.setCreateTime(new Date());
                ownerYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(ownerYearDmo);
                if (1 != i) {
                    logger.error("插入业主企业年流量信息失败");
                }
            } else {
                existOwnerYearDmo.setYearTraffic(ownerYearDmo1.getYearTraffic());
                existOwnerYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.update(existOwnerYearDmo);
                if (1 != i) {
                    logger.error("更新业主企业年流量信息失败");
                }
            }
        }
        else {
            if (null == existOwnerYearDmo) {
                ownerYearDmo.setIntegratorId(ownerDmo.getIntegratorId());
                ownerYearDmo.setYearDate(getLastMonthYear());
                ownerYearDmo.setYearTraffic(0d);
                ownerYearDmo.setCreateTime(new Date());
                ownerYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(ownerYearDmo);
                if (1 != i) {
                    logger.error("插入业主企业年流量信息失败");
                }
            }
        }
    }

    @Override
    public void integratorYearTrafficTable(IntegratorDmo integratorDmo) {

        IntegratorYearDmo integratorYearDmo = new IntegratorYearDmo();
        integratorYearDmo.setIntegratorId(integratorDmo.getId());
        integratorYearDmo.setStatus(Constants.TrafficStatus.NORMAL);
        IntegratorYearDmo integratorYearDmo1 = (IntegratorYearDmo) commonDao.selectOne("MonthTaskMapper.queryIntegratorLastMonthYearTraffic", integratorYearDmo);
        integratorYearDmo.setYearDate(getLastMonthYear());
        IntegratorYearDmo existIntegratorYearDmo = (IntegratorYearDmo) commonDao.selectOne(integratorYearDmo);
        if (null != integratorYearDmo1) {
            if (null == existIntegratorYearDmo) {
                integratorYearDmo.setYearTraffic(integratorYearDmo1.getYearTraffic());
                integratorYearDmo.setCreateTime(new Date());
                integratorYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(integratorYearDmo);
                if (1 != i) {
                    logger.error("插入集成商年流量信息失败");
                }
            } else {
                existIntegratorYearDmo.setYearTraffic(integratorYearDmo1.getYearTraffic());
                existIntegratorYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.update(existIntegratorYearDmo);
                if (1 != i) {
                    logger.error("更新集成商年流量信息失败");
                }
            }
        }
        else {
            if (null == existIntegratorYearDmo) {
                integratorYearDmo.setYearDate(getLastMonthYear());
                integratorYearDmo.setYearTraffic(0d);
                integratorYearDmo.setCreateTime(new Date());
                integratorYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(integratorYearDmo);
                if (1 != i) {
                    logger.error("插入集成商年流量信息失败");
                }
            }
        }

    }

    @Override
    public void systemYearTrafficTable() {
        SystemYearDmo systemYearDmo = new SystemYearDmo();
        systemYearDmo.setStatus(Constants.TrafficStatus.NORMAL);
        SystemYearDmo systemYearDmo1 = (SystemYearDmo) commonDao.selectOne("MonthTaskMapper.querySystemLastMonthYearTraffic", systemYearDmo);
        systemYearDmo.setYearDate(getLastMonthYear());
        SystemYearDmo existSystemYearDmo = (SystemYearDmo) commonDao.selectOne(systemYearDmo);
        if (null != systemYearDmo1) {
            if (null == existSystemYearDmo) {
                systemYearDmo.setYearTraffic(systemYearDmo1.getYearTraffic());
                systemYearDmo.setCreateTime(new Date());
                systemYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(systemYearDmo);
                if (1 != i) {
                    logger.error("插入云平台年流量信息失败");
                }
            } else {
                existSystemYearDmo.setYearTraffic(systemYearDmo1.getYearTraffic());
                existSystemYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.update(existSystemYearDmo);
                if (1 != i) {
                    logger.error("更新云平台年流量信息失败");
                }
            }
        }
        else {
            if (null == existSystemYearDmo) {
                systemYearDmo.setYearDate(getLastMonthYear());
                systemYearDmo.setYearTraffic(0d);
                systemYearDmo.setCreateTime(new Date());
                systemYearDmo.setLastUpdateTime(new Date());
                int i = commonDao.insert(systemYearDmo);
                if (1 != i) {
                    logger.error("插入云平台年流量信息失败");
                }
            }
        }

    }

    @Override
    public List<RtuDmo> queryRtuList() {
        RtuDmo rtuDmo = new RtuDmo();
        return commonDao.selectList("RealTimeTrafficMapper.queryRtuBySomething", rtuDmo);
    }

    @Override
    public List<OwnerDmo> queryOwnerList() {
        OwnerDmo ownerDmo = new OwnerDmo();
        return commonDao.selectList("RealTimeTrafficMapper.queryOwnerList", ownerDmo);
    }

    @Override
    public List<IntegratorDmo> queryIntegratorList() {
        IntegratorDmo integratorDmo = new IntegratorDmo();
        return commonDao.selectList("RealTimeTrafficMapper.queryIntegratorList", integratorDmo);
    }

    private Date getLastMonthYear() {

        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
// 将时分秒,毫秒域清零
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

}
