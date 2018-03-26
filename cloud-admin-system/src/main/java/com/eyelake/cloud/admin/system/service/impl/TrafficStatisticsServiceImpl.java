package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dao.mybatis.CommonDaoMybatis;
import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorMonthDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDayDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuMonthDmo;
import com.eyelake.cloud.admin.assist.dto.account.*;
import com.eyelake.cloud.admin.assist.result.MonthStatisticsResult;
import com.eyelake.cloud.admin.assist.result.YearStatisticsResult;
import com.eyelake.cloud.admin.system.service.intf.TrafficStatisticsService;
import com.eyelake.cloud.common.constants.StatisticsConstants;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;


@Service("trafficStatisticsService")
@ServiceTrace
public class TrafficStatisticsServiceImpl extends CommonDaoMybatis<RtuDayDmo> implements TrafficStatisticsService {


    private static Logger logger = LoggerFactory.getLogger(TrafficStatisticsServiceImpl.class);

    @Autowired
    private CommonDao commonDao;


    @Override
    public YearStatisticsResult systemYearStatistics(String accountYear) {

        YearStatisticsResult result = new YearStatisticsResult();

        YearStatisticsDto yearStatisticsDto = new YearStatisticsDto();
        yearStatisticsDto.setAccountYear(accountYear);

        //查询该年每个月的月流量(按月份排)
        List<YearStatisticsDto> systemMonthTrafficList = commonDao.selectList("AccountMapper.querySystemMonthTraffic",yearStatisticsDto);

       /* if(CollectionUtils.isEmpty(systemMonthTrafficList)){
            result.setMessage("云平台该年份还未使用激活的网关！");
            logger.warn("云平台{}年还未使用激活的网关",accountYear);
            return result;
        }*/

        StatisticsTransParamDto statisticsDto = yearCommonDeal(systemMonthTrafficList,accountYear);

        List<YearStatisticsDto> monthTrafficList = statisticsDto.getStatisticsList();
        List<String> diffList = statisticsDto.getDiffList();
        //年套餐流量
        result.setYearPackTraffic(statisticsDto.getYearPackTraffic());
        //年套餐外流量
        result.setYearOutPackTraffic(statisticsDto.getYearOutPackTraffic());

        //年使用总流量
        result.setYearUseTraffic(statisticsDto.getYearUseTraffic());



        List<YearStatisticsDto> batchInsertMonthDataList = new ArrayList<>();
        for(String month : diffList){
            YearStatisticsDto addToListDto = new YearStatisticsDto();

            addToListDto.setMonthOutPackTraffic(0D);
            addToListDto.setMonthPackTraffic(0);
            addToListDto.setMonthTraffic(0D);
            addToListDto.setMonthInPackTraffic(0D);
            addToListDto.setQueryMonth(month);

            monthTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYear).append("-").append(month).append("-").append("01");
            Date monthDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setMonthDate(monthDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            // batchInsertMonthDataList.add(addToListDto);
        }

        //将结果按月份从小到大排
        List<YearStatisticsDto> afterSortList = listSortByMonth(monthTrafficList);
        result.setYearStatisticsList(afterSortList);

        /*if(!CollectionUtils.isEmpty(batchInsertMonthDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setMonthDataList(batchInsertMonthDataList);
            //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertSystemMonthTraffic",batchInsertStatisticsDto);
        }*/
        return result;
    }

    @Override
    public MonthStatisticsResult systemMonthStatistics(String accountYearMonth) {
        MonthStatisticsResult result = new MonthStatisticsResult();

        MonthStatisticsDto monthStatisticsDto = new MonthStatisticsDto();
        monthStatisticsDto.setAccountYearMonth(accountYearMonth);

        //查询该月每天的日流量(按天排)
        List<MonthStatisticsDto> systemDayTrafficList = commonDao.selectList("AccountMapper.querySystemDayTraffic",monthStatisticsDto);

        /*if(CollectionUtils.isEmpty(systemDayTrafficList)){
            result.setMessage("云平台该月份还未使用激活的网关！");
            logger.warn("云平台{}还未使用激活的网关",accountYearMonth);
            return result;
        }*/

        //网关使用流量排名
        List<RtuMonthRankDto> rtuRankList = commonDao.selectList("AccountMapper.systemRtuMonthRank",monthStatisticsDto);
        result.setRtuMonthRankList(rtuRankList);

        //套餐及套餐外流量占比
        IntegratorMonthDmo integratorMonthDmo = (IntegratorMonthDmo)commonDao.selectOne("AccountMapper.systemMonthPackTraffic",monthStatisticsDto);

        if(null != integratorMonthDmo){
            Double monthOutPackTraffic = 0D;
            Double monthTraffic = integratorMonthDmo.getMonthTraffic();
            Long monthPackTraffic = integratorMonthDmo.getMonthPackTraffic();
            monthOutPackTraffic = monthTraffic - monthPackTraffic;
            if(monthOutPackTraffic < 0){
                monthOutPackTraffic = 0D;
            }
            result.setMonthUseTraffic(monthTraffic);
            result.setMonthPackTraffic(monthPackTraffic);
            result.setMonthOutPackTraffic(monthOutPackTraffic);
        }else{
            result.setMonthPackTraffic(0L);
            result.setMonthOutPackTraffic(0D);
            result.setMonthUseTraffic(0D);

        }


        StatisticsTransParamDto statisticsDto = monthCommonDeal(systemDayTrafficList,accountYearMonth);

        List<String> diffList = statisticsDto.getDiffList();

        List<MonthStatisticsDto> batchInsertDayDataList = new ArrayList<>();
        for(String day : diffList){
            MonthStatisticsDto addToListDto = new MonthStatisticsDto();

            addToListDto.setDayTraffic(0D);
            addToListDto.setQueryDay(day);

            systemDayTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYearMonth).append("-").append(day);
            Date dayDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setDayDate(dayDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            // batchInsertDayDataList.add(addToListDto);
        }

        //将结果按每月的日期从小到大排
        List<MonthStatisticsDto> afterSortList = listSortByDay(systemDayTrafficList);
        result.setMonthStatisticsList(afterSortList);

       /* if(!CollectionUtils.isEmpty(batchInsertDayDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setDayDataList(batchInsertDayDataList);
            //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertSystemDayTraffic",batchInsertStatisticsDto);
        }*/

        return result;
    }

    @Override
    public YearStatisticsResult integratorYearStatistics(String integratorId, String accountYear) {

        YearStatisticsResult result = new YearStatisticsResult();

        YearStatisticsDto yearStatisticsDto = new YearStatisticsDto();
        yearStatisticsDto.setAccountYear(accountYear);
        yearStatisticsDto.setIntegratorId(Long.parseLong(integratorId));

        //查询该年每个月的月流量(按月份排)
        List<YearStatisticsDto> integratorMonthTrafficList = commonDao.selectList("AccountMapper.queryIntegratorMonthTraffic",yearStatisticsDto);

        /*if(CollectionUtils.isEmpty(integratorMonthTrafficList)){
            result.setMessage("该集成商该年份还未使用激活的网关！");
            logger.warn("该集成商该年份还未使用激活的网关");
            return result;
        }*/

        StatisticsTransParamDto statisticsDto = yearCommonDeal(integratorMonthTrafficList,accountYear);

        List<YearStatisticsDto> monthTrafficList = statisticsDto.getStatisticsList();
        List<String> diffList = statisticsDto.getDiffList();
        //年套餐流量
        result.setYearPackTraffic(statisticsDto.getYearPackTraffic());
        //年套餐外流量
        result.setYearOutPackTraffic(statisticsDto.getYearOutPackTraffic());

        //年使用总流量
        result.setYearUseTraffic(statisticsDto.getYearUseTraffic());

        List<YearStatisticsDto> batchInsertMonthDataList = new ArrayList<>();
        for(String month : diffList){
            YearStatisticsDto addToListDto = new YearStatisticsDto();

            addToListDto.setMonthOutPackTraffic(0D);
            addToListDto.setMonthPackTraffic(0);
            addToListDto.setMonthTraffic(0D);
            addToListDto.setMonthInPackTraffic(0D);
            addToListDto.setQueryMonth(month);

            monthTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYear).append("-").append(month).append("-").append("01");
            Date monthDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setIntegratorId(Long.parseLong(integratorId));
            addToListDto.setMonthDate(monthDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            // batchInsertMonthDataList.add(addToListDto);
        }

        //将结果按月份从小到大排
        List<YearStatisticsDto> afterSortList = listSortByMonth(monthTrafficList);
        result.setYearStatisticsList(afterSortList);

       /* if(!CollectionUtils.isEmpty(batchInsertMonthDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setMonthDataList(batchInsertMonthDataList);
        //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertIntegratorMonthTraffic",batchInsertStatisticsDto);
        }*/

        return result;
    }


    @Override
    public YearStatisticsResult rtuYearStatistics( String snNumber, String accountYear) {
        YearStatisticsResult result = new YearStatisticsResult();

        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setSnNumber(snNumber);

        //查rtu所属的集成商
        RtuDmo rtuInfoDmo = (RtuDmo)commonDao.selectOne("AccountMapper.queryIntegratorByRtu",rtuDmo);

        if(null == rtuInfoDmo){
            result.fail("","该网关已不存在");
            logger.error("编号为{}的网关已不存在", snNumber );
            return result;

        }
        Long integratorId = rtuInfoDmo.getIntegratorId();
        YearStatisticsDto yearStatisticsDto = new YearStatisticsDto();
        yearStatisticsDto.setAccountYear(accountYear);
        yearStatisticsDto.setSnNumber(snNumber);
        yearStatisticsDto.setIntegratorId(integratorId);

        //查询该年每个月的月流量(按月份排)
        List<YearStatisticsDto> rtuMonthTrafficList = commonDao.selectList("AccountMapper.queryRtuMonthTraffic",yearStatisticsDto);

        /*if(CollectionUtils.isEmpty(rtuMonthTrafficList)){
            result.setMessage("该网关该年份还未开始使用！");
            logger.warn("该网关该年份还未开始使用");
            return result;
        }*/

        StatisticsTransParamDto statisticsDto = yearCommonDeal(rtuMonthTrafficList,accountYear);

        List<YearStatisticsDto> monthTrafficList = statisticsDto.getStatisticsList();
        List<String> diffList = statisticsDto.getDiffList();
        //年套餐流量
        result.setYearPackTraffic(statisticsDto.getYearPackTraffic());
        //年套餐外流量
        result.setYearOutPackTraffic(statisticsDto.getYearOutPackTraffic());

        //年使用总流量
        result.setYearUseTraffic(statisticsDto.getYearUseTraffic());

        List<YearStatisticsDto> batchInsertMonthDataList = new ArrayList<>();
        for(String month : diffList){
            YearStatisticsDto addToListDto = new YearStatisticsDto();

            addToListDto.setMonthOutPackTraffic(0D);
            addToListDto.setMonthPackTraffic(0);
            addToListDto.setMonthTraffic(0D);
            addToListDto.setMonthInPackTraffic(0D);

            addToListDto.setQueryMonth(month);

            monthTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYear).append("-").append(month).append("-").append("01");
            Date monthDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setIntegratorId(integratorId);
            addToListDto.setSnNumber(snNumber);
            addToListDto.setMonthDate(monthDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            // batchInsertMonthDataList.add(addToListDto);
        }

        //将结果按月份从小到大排
        List<YearStatisticsDto> afterSortList = listSortByMonth(monthTrafficList);
        result.setYearStatisticsList(afterSortList);

       /* if(!CollectionUtils.isEmpty(batchInsertMonthDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setMonthDataList(batchInsertMonthDataList);
            //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertRtuMonthTraffic",batchInsertStatisticsDto);
        }*/

        return result;
    }

    @Override
    public MonthStatisticsResult integratorMonthStatistics(String integratorId, String accountYearMonth) {
        MonthStatisticsResult result = new MonthStatisticsResult();

        MonthStatisticsDto monthStatisticsDto = new MonthStatisticsDto();
        monthStatisticsDto.setAccountYearMonth(accountYearMonth);
        monthStatisticsDto.setIntegratorId(Long.parseLong(integratorId));


        //查询该月每天的日流量(按天排)
        List<MonthStatisticsDto> integratorDayTrafficList = commonDao.selectList("AccountMapper.queryIntegratorDayTraffic",monthStatisticsDto);

        /*if(CollectionUtils.isEmpty(integratorDayTrafficList)){
            result.setMessage("该集成商该月份还未使用激活的网关！");
            logger.warn("该集成商{}还未使用激活的网关",accountYearMonth);
            return result;
        }*/

        //网关使用流量排名
        List<RtuMonthRankDto> rtuRankList = commonDao.selectList("AccountMapper.systemRtuMonthRank",monthStatisticsDto);
        result.setRtuMonthRankList(rtuRankList);

        //套餐及套餐外流量占比
        IntegratorMonthDmo integratorMonthDmo = (IntegratorMonthDmo)commonDao.selectOne("AccountMapper.integratorMonthPackTraffic",monthStatisticsDto);

        if(null != integratorMonthDmo){
            Double monthOutPackTraffic = 0D;
            Double monthTraffic = integratorMonthDmo.getMonthTraffic();
            Long monthPackTraffic = integratorMonthDmo.getMonthPackTraffic();
            monthOutPackTraffic = monthTraffic - monthPackTraffic;
            if(monthOutPackTraffic < 0){
                monthOutPackTraffic = 0D;
            }
            result.setMonthUseTraffic(monthTraffic);
            result.setMonthPackTraffic(monthPackTraffic);
            result.setMonthOutPackTraffic(monthOutPackTraffic);
        }else{
            result.setMonthPackTraffic(0L);
            result.setMonthOutPackTraffic(0D);
            result.setMonthUseTraffic(0D);

        }



        StatisticsTransParamDto statisticsDto = monthCommonDeal(integratorDayTrafficList,accountYearMonth);

        List<String> diffList = statisticsDto.getDiffList();

        List<MonthStatisticsDto> batchInsertDayDataList = new ArrayList<>();
        for(String day : diffList){
            MonthStatisticsDto addToListDto = new MonthStatisticsDto();

            addToListDto.setDayTraffic(0D);
            addToListDto.setQueryDay(day);

            integratorDayTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYearMonth).append("-").append(day);
            Date dayDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setIntegratorId(Long.parseLong(integratorId));
            addToListDto.setDayDate(dayDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            // batchInsertDayDataList.add(addToListDto);
        }

        //将结果按每月的日期从小到大排
        List<MonthStatisticsDto> afterSortList = listSortByDay(integratorDayTrafficList);
        result.setMonthStatisticsList(afterSortList);

        /*if(!CollectionUtils.isEmpty(batchInsertDayDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setDayDataList(batchInsertDayDataList);
            //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertIntegratorDayTraffic",batchInsertStatisticsDto);
        }*/

        return result;
    }


    @Override
    public MonthStatisticsResult rtuMonthStatistics( String snNumber, String accountYearMonth) {

        MonthStatisticsResult result = new MonthStatisticsResult();

        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setSnNumber(snNumber);

        //查rtu所属的集成商
        RtuDmo rtuInfoDmo = (RtuDmo)commonDao.selectOne("AccountMapper.queryIntegratorByRtu",rtuDmo);

        if(null == rtuInfoDmo){
            result.fail("","该网关已不存在");
            logger.error("编号为{}的网关已不存在", snNumber );
            return result;

        }
        Long integratorId = rtuInfoDmo.getIntegratorId();

        MonthStatisticsDto monthStatisticsDto = new MonthStatisticsDto();
        monthStatisticsDto.setAccountYearMonth(accountYearMonth);
        monthStatisticsDto.setIntegratorId(integratorId);
        monthStatisticsDto.setSnNumber(snNumber);

        //查询该月每天的日流量(按流量排)
        List<MonthStatisticsDto> rtuDayTrafficList = commonDao.selectList("AccountMapper.queryRtuDayTraffic",monthStatisticsDto);

        /*if(CollectionUtils.isEmpty(rtuDayTrafficList)){
            result.setMessage("该网关该月份还未激活！");
            logger.warn("该网关{}还未激活",accountYearMonth);
            return result;
        }*/

        //套餐及套餐外流量占比
        RtuMonthDmo rtuMonthDmo = (RtuMonthDmo)commonDao.selectOne("AccountMapper.rtuMonthPackTraffic",monthStatisticsDto);

        if(null != rtuMonthDmo){
            Double monthOutPackTraffic = 0D;
            Double monthTraffic = rtuMonthDmo.getMonthTraffic();
            Long monthPackTraffic = rtuMonthDmo.getMonthPackTraffic();
            monthOutPackTraffic = monthTraffic - monthPackTraffic;
            if(monthOutPackTraffic < 0){
                monthOutPackTraffic = 0D;
            }
            result.setMonthUseTraffic(monthTraffic);
            result.setMonthPackTraffic(monthPackTraffic);
            result.setMonthOutPackTraffic(monthOutPackTraffic);
        }else{
            result.setMonthPackTraffic(0L);
            result.setMonthOutPackTraffic(0D);
            result.setMonthUseTraffic(0D);

        }


        //rtu该月日流量排名
        List<MonthStatisticsDto> dayTrafficRankList = commonDao.selectList("AccountMapper.queryRtuDayTrafficRank",monthStatisticsDto);

        result.setRtuDayTrafficRankList(dayTrafficRankList);

       /* int size = rtuDayTrafficList.size();
        if(size > StatisticsConstants.FIVE ){
            List<MonthStatisticsDto> dayTrafficRankList = new ArrayList<>();
            for(int i = 0 ; i < StatisticsConstants.FIVE; i++){
                dayTrafficRankList.add(rtuDayTrafficList.get(i));
            }
            result.setRtuDayTrafficRankList(dayTrafficRankList);
        }else {
            result.setRtuDayTrafficRankList(rtuDayTrafficList);
        }*/



        StatisticsTransParamDto statisticsDto = monthCommonDeal(rtuDayTrafficList,accountYearMonth);

        List<String> diffList = statisticsDto.getDiffList();

        List<MonthStatisticsDto> batchInsertDayDataList = new ArrayList<>();
        for(String day : diffList){
            MonthStatisticsDto addToListDto = new MonthStatisticsDto();

            addToListDto.setDayTraffic(0D);
            addToListDto.setQueryDay(day);

            rtuDayTrafficList.add(addToListDto);

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(accountYearMonth).append("-").append(day);
            Date dayDate = DateUtil.parseDate(stringBuffer.toString(),DateUtil.yyyy_MM_dd);

            addToListDto.setIntegratorId(integratorId);
            addToListDto.setSnNumber(snNumber);
            addToListDto.setDayDate(dayDate);
            addToListDto.setCreateTime(DateUtil.getDate());
            addToListDto.setLastUpdateTime(DateUtil.getDate());
            addToListDto.setStatus(StatisticsConstants.Status.NORMAL);

            batchInsertDayDataList.add(addToListDto);
        }

        //将结果按每月的日期从小到大排
        List<MonthStatisticsDto> afterSortList = listSortByDay(rtuDayTrafficList);
        result.setMonthStatisticsList(afterSortList);

       /* if(!CollectionUtils.isEmpty(batchInsertDayDataList)){
            BatchInsertStatisticsDto batchInsertStatisticsDto = new BatchInsertStatisticsDto();
            batchInsertStatisticsDto.setDayDataList(batchInsertDayDataList);
            //    批量插入数据库
            commonDao.batchInsert("AccountMapper.batchInsertRtuDayTraffic",batchInsertStatisticsDto);
        }*/

        return result;
    }

    //年处理公共处理方法
    private StatisticsTransParamDto yearCommonDeal(List<YearStatisticsDto> monthTrafficList,String accountYear){

        StatisticsTransParamDto statisticsTransParamDto = new StatisticsTransParamDto();
        //年套餐流量
        Long yearPackTraffic = 0L;
        //年使用总流量
        Double yearUseTraffic = 0D;

        //若统计的年是当前年，就将当前月remove 掉
        int currentYear = currentYear();
        Integer currentMonth = currentMonth();
        //日历月
        List<String> calendarMonthsList;
        //数据库中已有的月份
        List<String> hasMonthList = new ArrayList<>();

        //若是当前年
        if(accountYear.equals(String.valueOf(currentYear))){

            if(!CollectionUtils.isEmpty(monthTrafficList)){

                YearStatisticsDto lastDataOfList = monthTrafficList.get(monthTrafficList.size()-1);
                String compareMonth = null;
                String lastMonthOfList = lastDataOfList.getQueryMonth();

                String first = lastMonthOfList.substring(0,1);
                if(first.equals(StatisticsConstants.ZEROSTR)){
                    compareMonth = lastMonthOfList.substring(1);
                }else {
                    compareMonth = lastMonthOfList;
                }

                //若最后一条数据是当前月
                if(compareMonth.equals(String.valueOf(currentMonth))){
                    monthTrafficList.remove(lastDataOfList);
                }

            }

            calendarMonthsList = StatisticsConstants.MONTH_MAP.get(currentMonth);

        }else {
            calendarMonthsList = StatisticsConstants.TWELVE_MONTH_MAP.get(StatisticsConstants.ZERO);
        }

        //将月份取出，同时算出套餐外流量
        Double monthOutPackTraffic = 0D;
        //月套餐内使用流量
        Double monthInPackTraffic = 0D;
        for(YearStatisticsDto monthIntegrator : monthTrafficList){
            //将月通用流量累和，得到年总通用流量
            yearPackTraffic += monthIntegrator.getMonthPackTraffic();
            //将月使用流量累和，得到年总使用流量
            yearUseTraffic += monthIntegrator.getMonthTraffic();

            String hasMonth = monthIntegrator.getQueryMonth();
            hasMonthList.add(hasMonth);

            Double monthTotleTraffic = monthIntegrator.getMonthTraffic();
            int monthPackTraffic = monthIntegrator.getMonthPackTraffic();
            monthOutPackTraffic = monthTotleTraffic - monthPackTraffic;
            //月使用流量小于套餐流量，套餐内使用流量就是月使用量
            if(monthTotleTraffic < monthPackTraffic){
                monthInPackTraffic = monthTotleTraffic;
            }else {
                //转成double类型
                monthInPackTraffic = monthPackTraffic + 0.0;
            }
            monthIntegrator.setMonthInPackTraffic(monthInPackTraffic);

            if(monthOutPackTraffic < 0){
                monthOutPackTraffic = 0D;
            }
            monthIntegrator.setMonthOutPackTraffic(monthOutPackTraffic);
        }

        //该年中数据库中没有的月份
        List<String> diffList = new ArrayList<>();
        for(String calendarDay : calendarMonthsList){
            if(! hasMonthList.contains(calendarDay)){
                diffList.add(calendarDay);
            }
        }

        Double yearOutPackTraffic = 0D;
        yearOutPackTraffic = yearUseTraffic - yearPackTraffic;

        if(yearOutPackTraffic < 0 ){
            yearOutPackTraffic = 0D;
        }

        statisticsTransParamDto.setYearUseTraffic(yearUseTraffic);
        statisticsTransParamDto.setStatisticsList(monthTrafficList);
        statisticsTransParamDto.setDiffList(diffList);
        statisticsTransParamDto.setYearPackTraffic(yearPackTraffic);
        statisticsTransParamDto.setYearOutPackTraffic(yearOutPackTraffic);
        return statisticsTransParamDto;
    }

    //月统计公共处理方法
    private StatisticsTransParamDto monthCommonDeal(List<MonthStatisticsDto> dayTrafficList,String accountYearMonth){

        StatisticsTransParamDto statisticsTransParamDto = new StatisticsTransParamDto();


        //数据库中已有的该月的天数
        List<String> hasDayList = new ArrayList<>();

        //该月中数据库中没有的天数
        List<String> diffList = new ArrayList<>();

        //获取年月对应该月的天数
        String[] yearAndMonth =accountYearMonth.split("-");
        String countYear = yearAndMonth[0];
        String countMonth = yearAndMonth[1];
        //获取月份的天数
        Integer days = DateUtil.maxDayOfMonth(countYear,countMonth);
        //日历天
        List<String> calendarDaysList = StatisticsConstants.DAY_MAP.get(days);

        for(MonthStatisticsDto day : dayTrafficList){

            String hasDay = day.getQueryDay();
            hasDayList.add(hasDay);
        }

        for(String calendarDay : calendarDaysList){
            if(! hasDayList.contains(calendarDay)){
                diffList.add(calendarDay);
            }
        }
        statisticsTransParamDto.setDiffList(diffList);
        return statisticsTransParamDto;
    }

    //获取当前年
    public int currentYear(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int currentYear = c.get(Calendar.YEAR);
        return currentYear;
    }

    //获取当前月份
    public int currentMonth(){
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int currentMonth = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        return currentMonth;
    }

    /**
     * 按照月份从小到大排
     * @param list
     */
    private static List<YearStatisticsDto> listSortByMonth(List<YearStatisticsDto> list) {

        Collections.sort(list, new Comparator<YearStatisticsDto>() {
            @Override
            public int compare(YearStatisticsDto o1, YearStatisticsDto o2) {
                try {
                    int dt1 = Integer.parseInt(o1.getQueryMonth());
                    int dt2 = Integer.parseInt(o2.getQueryMonth());
                    if (dt1 > dt2) {
                        return 1;
                    } else if (dt1 < dt2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return list;
    }



    /**
     * 按照月份从小到大排
     * @param list
     */
    private static List<MonthStatisticsDto> listSortByDay(List<MonthStatisticsDto> list) {

        Collections.sort(list, new Comparator<MonthStatisticsDto>() {
            @Override
            public int compare(MonthStatisticsDto o1, MonthStatisticsDto o2) {
                try {
                    int dt1 = Integer.parseInt(o1.getQueryDay());
                    int dt2 = Integer.parseInt(o2.getQueryDay());
                    if (dt1 > dt2) {
                        return 1;
                    } else if (dt1 < dt2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });

        return list;
    }
}

