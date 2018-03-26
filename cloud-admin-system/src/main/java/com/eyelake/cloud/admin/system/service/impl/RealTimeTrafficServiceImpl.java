package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.data.dto.StatisticsDto;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyiotapi.model.v20171111.QueryCardFlowInfoRequest;
import com.aliyuncs.dyiotapi.model.v20171111.QueryCardFlowInfoResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.*;


import com.eyelake.cloud.admin.assist.dto.admin.DayTrafficDto;
import com.eyelake.cloud.admin.assist.dto.admin.PackageUsageDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.dto.admin.RealTimeTrafficDto;
import com.eyelake.cloud.admin.assist.result.QueryRtuMonthTrafficAndPackResult;
import com.eyelake.cloud.admin.assist.result.RealTimeTrafficResult;
import com.eyelake.cloud.admin.system.service.intf.RealTimeTrafficService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.eyelake.cloud.common.constants.StatisticsConstants;
import com.eyelake.cloud.common.utils.PropertiesUtil;
import com.eyelake.framework.core.trace.ServiceTrace;

import com.eyelake.framework.utils.DateUtil;
import com.yjh.framework.lang.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:xudajan
 * @date:2018/1/22
 */
@Service("realTimeTrafficService")
@ServiceTrace
public class RealTimeTrafficServiceImpl implements RealTimeTrafficService {

    private static final Logger logger = LoggerFactory.getLogger(RealTimeTrafficServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    //初始化ascClient需要的几个参数
    private static final String PRODUCT = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_PRODUCT);//物联卡API产品名称（短信产品名固定，无需修改）
    private static final String DOMAIN = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_DOMAIN);//物联卡API产品域名（接口地址固定，无需修改）
    //替换成你的AK
    private static final String ACCESS_KEY_ID = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_ID);;//你的accessKeyId,参考本文档步骤2
    private static final String ACCESS_KEY_SECRET = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_SECRET);;//你的accessKeySecret，参考本文档步骤2


    private IAcsClient acsClient = init();

    private IAcsClient init() {
        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID,
                ACCESS_KEY_SECRET);
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        } catch (ClientException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new DefaultAcsClient(profile);
    }

    @Override
    public RealTimeTrafficResult rtuRealTimeTraffic(RealTimeTrafficDto realTimeTrafficDto) {
        RealTimeTrafficResult realTimeTrafficResult = new RealTimeTrafficResult();
        String snNumber = realTimeTrafficDto.getSnNumber();
        if (StringUtils.isBlank(snNumber)) {
            realTimeTrafficResult.fail("", "数据错误");
            return realTimeTrafficResult;
        }
        int dayOfMonth = dayOfMonth();
        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        rtuMonthDmo.setSnNumber(snNumber);
        rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);
        rtuMonthDmo.setMonthDate(getMonth());
        try {
            rtuMonthDmo = (RtuMonthDmo) commonDao.selectOne("RealTimeTrafficMapper.queryRtuMonth",rtuMonthDmo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            realTimeTrafficResult.fail("", "数据库中存在重复网关月流量数据");
            return realTimeTrafficResult;
        }
        if (null == rtuMonthDmo) {
            rtuMonthDmo = createRtuMonth(snNumber);
        }

        Double monthTraffic = rtuMonthDmo.getMonthTraffic();
        Long monthPackTraffic = rtuMonthDmo.getMonthPackTraffic();
        Double monthRemainingTraffic;
        if (monthTraffic >= monthPackTraffic) {
            monthRemainingTraffic = 0d;
        } else {
            monthRemainingTraffic = monthPackTraffic - monthTraffic;
        }
        realTimeTrafficDto.setMonthTraffic(rtuMonthDmo.getMonthTraffic());
        realTimeTrafficDto.setMonthPackTraffic(rtuMonthDmo.getMonthPackTraffic());
        realTimeTrafficDto.setMonthRemainingTraffic(monthRemainingTraffic);
        List<PackageUsageDto> packageUsageDtoList = new ArrayList<>();
//        调用流量查询接口，查询各个流量套餐使用情况
        QueryCardFlowInfoRequest request = new QueryCardFlowInfoRequest();
//填入你要查询的iccid值
        request.setIccid(snNumber);
//请求失败这里会抛ClientException异常
        QueryCardFlowInfoResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
//请求成功
        if (null != acsResponse && acsResponse.getCode() != null && "OK".equals(acsResponse.getCode())) {
            List<QueryCardFlowInfoResponse.CardFlowInfo> cardFlowInfoList = acsResponse.getCardFlowInfos();
            for (QueryCardFlowInfoResponse.CardFlowInfo cardFlowInfo : cardFlowInfoList) {
                PackageUsageDto packageUsageDto = new PackageUsageDto();
                packageUsageDto.setPackName(cardFlowInfo.getResName());
                packageUsageDto.setPackTraffic(cardFlowInfo.getFlowResource() / 1024);
                packageUsageDto.setPackUsedTraffic(cardFlowInfo.getFlowUsed()==null ? 0d : (cardFlowInfo.getFlowUsed().doubleValue() / 1024));
                packageUsageDto.setPackRemainingTraffic(cardFlowInfo.getRestOfFlow()==null ? 0d : (cardFlowInfo.getRestOfFlow().doubleValue() / 1024));
                packageUsageDtoList.add(packageUsageDto);
            }
        }
        realTimeTrafficDto.setPackageUsageDtoList(packageUsageDtoList);
        RtuDayDmo rtuDayDmo = new RtuDayDmo();
        rtuDayDmo.setSnNumber(snNumber);
        rtuDayDmo.setStatus(Constants.TrafficStatus.NORMAL);
        List<DayTrafficDto> dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.queryRtuDayTrafficUsage", rtuDayDmo);
        if (null == dayTrafficDtoList) {
            dayTrafficDtoList = new ArrayList<>();
        }
        if (dayTrafficDtoList.size() != dayOfMonth - 1) {
            List<Integer> existDayList = commonDao.selectList("RealTimeTrafficMapper.queryRtuDayTrafficDay", rtuDayDmo);
            for (int i = 1; i < dayOfMonth; i++) {
                if (null == existDayList || !existDayList.contains(i)) {
                    RtuDayDmo rtuDayDmo1 = new RtuDayDmo();
                    Date date = getDayOfMonth(i);
                    rtuDayDmo1.setSnNumber(snNumber);
                    rtuDayDmo1.setIntegratorId(rtuMonthDmo.getIntegratorId());
                    rtuDayDmo1.setDayDate(date);
                    rtuDayDmo1.setDayTraffic(0d);
                    rtuDayDmo1.setCreateTime(new Date());
                    rtuDayDmo1.setLastUpdateTime(new Date());
                    rtuDayDmo1.setStatus(Constants.TrafficStatus.NORMAL);
                    int j=commonDao.insert(rtuDayDmo1);
                    if(1!=j){
                        logger.error("插入网关日流量信息失败");
                    }
                }
            }
        }
        dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.queryRtuDayTrafficUsage", rtuDayDmo);
        realTimeTrafficDto.setDayTrafficDtoList(dayTrafficDtoList);
        realTimeTrafficResult.setSuccess(true);
        realTimeTrafficResult.setRealTimeTrafficDto(realTimeTrafficDto);
        return realTimeTrafficResult;
    }

    private RtuMonthDmo createRtuMonth(String snNumber) {

        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setSnNumber(snNumber);
        rtuDmo = (RtuDmo) commonDao.selectOne("RealTimeTrafficMapper.queryRtuBySomething", rtuDmo);

        QueryCardFlowInfoRequest request = new QueryCardFlowInfoRequest();
//填入你要查询的iccid值
        request.setIccid(snNumber);
//请求失败这里会抛ClientException异常
        QueryCardFlowInfoResponse acsResponse = null;
        try {
            acsResponse = acsClient.getAcsResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
//请求成功
        Long totalFlowResource = 0L;
        Long totalFlowUsed = 0L;
        StringBuffer stringBuffer = new StringBuffer("");
        if (null != acsResponse && acsResponse.getCode() != null && "OK".equals(acsResponse.getCode())) {

            for (QueryCardFlowInfoResponse.CardFlowInfo cardFlowInfo : acsResponse.getCardFlowInfos()) {
                stringBuffer.append(",");
                stringBuffer.append(cardFlowInfo.getResName());
                totalFlowResource += cardFlowInfo.getFlowResource();
                totalFlowUsed += cardFlowInfo.getFlowUsed();

            }
        }
        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        rtuMonthDmo.setSnNumber(rtuDmo.getSnNumber());
        rtuMonthDmo.setIntegratorId(rtuDmo.getIntegratorId());
        rtuMonthDmo.setMonthDate(getMonth());
        rtuMonthDmo.setMonthTraffic(totalFlowUsed.doubleValue() / 1024);
        rtuMonthDmo.setMonthPackTraffic(totalFlowResource / 1024);
        rtuMonthDmo.setFixedPackName(stringBuffer.toString());
        rtuMonthDmo.setCreateTime(DateUtil.getDate());
        rtuMonthDmo.setLastUpdateTime(DateUtil.getDate());
        rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);
        //插入网关日流量表
        int i = commonDao.insert(rtuMonthDmo);
        if (1 != i) {
            logger.error("插入网关月流量信息失败");
        }
        return rtuMonthDmo;
    }

    @Override
    public RealTimeTrafficResult integratorRealTimeTraffic(RealTimeTrafficDto realTimeTrafficDto) {

        RealTimeTrafficResult realTimeTrafficResult = new RealTimeTrafficResult();
        String id = realTimeTrafficDto.getId();
        if (StringUtils.isBlank(id)) {
            realTimeTrafficResult.fail("", "数据错误");
            return realTimeTrafficResult;
        }
        int dayOfMonth = dayOfMonth();
        IntegratorMonthDmo integratorMonthDmo = new IntegratorMonthDmo();
        integratorMonthDmo.setIntegratorId(Long.parseLong(id));
        integratorMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);
        integratorMonthDmo.setMonthDate(getMonth());
        integratorMonthDmo = (IntegratorMonthDmo) commonDao.selectOne("RealTimeTrafficMapper.queryIntegratorMonth",integratorMonthDmo);
        if (null == integratorMonthDmo) {
            integratorMonthDmo = createIntegratorMonth(id);
        }

        Double monthTraffic = integratorMonthDmo.getMonthTraffic();
        Long monthPackTraffic = integratorMonthDmo.getMonthPackTraffic();
        Double monthRemainingTraffic;
        if (monthTraffic >= monthPackTraffic) {
            monthRemainingTraffic = 0d;
        } else {
            monthRemainingTraffic = monthPackTraffic - monthTraffic;
        }
        realTimeTrafficDto.setMonthTraffic(integratorMonthDmo.getMonthTraffic());
        realTimeTrafficDto.setMonthPackTraffic(integratorMonthDmo.getMonthPackTraffic());
        realTimeTrafficDto.setMonthRemainingTraffic(monthRemainingTraffic);

        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        rtuMonthDmo.setMonthDate(getMonth());
        rtuMonthDmo.setIntegratorId(Long.parseLong(id));
        rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);

//        套餐流量占比
        List<HashMap> packageNumberMapList = commonDao.selectList("RealTimeTrafficMapper.queryIntegratorPackageNumberMapList", rtuMonthDmo);
        if (CollectionUtils.isEmpty(packageNumberMapList)) {
            packageNumberMapList = new ArrayList<>();
        }
        realTimeTrafficDto.setPackageNumberMapList(packageNumberMapList);

        IntegratorDayDmo integratorDayDmo = new IntegratorDayDmo();
        integratorDayDmo.setIntegratorId(Long.parseLong(id));
        integratorDayDmo.setStatus(Constants.TrafficStatus.NORMAL);
        List<DayTrafficDto> dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.queryIntegratorDayTrafficUsage", integratorDayDmo);
        if (null == dayTrafficDtoList) {
            dayTrafficDtoList = new ArrayList<>();
        }
        if (dayTrafficDtoList.size() != dayOfMonth - 1) {
            List<Integer> existDayList = commonDao.selectList("RealTimeTrafficMapper.queryIntegratorDayTrafficDay", integratorDayDmo);
            for (int i = 1; i < dayOfMonth; i++) {
                if (null == existDayList || !existDayList.contains(i)) {
                    IntegratorDayDmo integratorDayDmo1 = new IntegratorDayDmo();
                    Date date = getDayOfMonth(i);
                    integratorDayDmo1.setIntegratorId(Long.parseLong(id));
                    integratorDayDmo1.setDayDate(date);
                    integratorDayDmo1.setDayTraffic(0d);
                    integratorDayDmo1.setCreateTime(new Date());
                    integratorDayDmo1.setLastUpdateTime(new Date());
                    integratorDayDmo1.setStatus(Constants.TrafficStatus.NORMAL);
                    int j=commonDao.insert(integratorDayDmo1);
                    if(1!=j){
                        logger.error("插入集成商日流量信息失败");
                    }
                }
            }
        }
        dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.queryIntegratorDayTrafficUsage", integratorDayDmo);
        realTimeTrafficDto.setDayTrafficDtoList(dayTrafficDtoList);
        realTimeTrafficResult.setSuccess(true);
        realTimeTrafficResult.setRealTimeTrafficDto(realTimeTrafficDto);
        return realTimeTrafficResult;
    }

    private IntegratorMonthDmo createIntegratorMonth(String id) {

        StatisticsDto integratorDayMonthDmo = new StatisticsDto();
        integratorDayMonthDmo.setIntegratorId(Long.parseLong(id));
        integratorDayMonthDmo.setStatisticsDate(getMonth());

        //    集成商月流量
        StatisticsDto statisticsMonthDto = (StatisticsDto) commonDao.selectOne("RealTimeTrafficMapper.statisticsMonthTraffic", integratorDayMonthDmo);
        Double monthTraffic = 0D;
        Long monthPackTraffic = 0L;
        if (null != statisticsMonthDto) {
            if (null != statisticsMonthDto.getUsedTraffic()) {
                monthTraffic = statisticsMonthDto.getUsedTraffic();
            }
            if (null != statisticsMonthDto.getPackTraffic()) {
                monthPackTraffic = statisticsMonthDto.getPackTraffic();
            }
        }
        IntegratorMonthDmo addOrUpdateDmo = new IntegratorMonthDmo();
        addOrUpdateDmo.setIntegratorId(Long.parseLong(id));
        addOrUpdateDmo.setMonthDate(getMonth());
        addOrUpdateDmo.setMonthTraffic(monthTraffic);
        addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
        addOrUpdateDmo.setCreateTime(DateUtil.getDate());
        addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
        addOrUpdateDmo.setStatus(Constants.TrafficStatus.NORMAL);
        int i = commonDao.insert(addOrUpdateDmo);
        if (1 != i) {
            logger.error("插入集成商月流量信息失败");
        }
        return addOrUpdateDmo;
    }

    @Override
    public RealTimeTrafficResult systemRealTimeTraffic() {

        RealTimeTrafficResult realTimeTrafficResult = new RealTimeTrafficResult();
        RealTimeTrafficDto realTimeTrafficDto = new RealTimeTrafficDto();
        int dayOfMonth = dayOfMonth();
        SystemMonthDmo systemMonthDmo = new SystemMonthDmo();
        systemMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);
        systemMonthDmo.setMonthDate(getMonth());
        systemMonthDmo = (SystemMonthDmo) commonDao.selectOne("RealTimeTrafficMapper.querySystemMonth",systemMonthDmo);
        if (null == systemMonthDmo) {
            systemMonthDmo = createSystemMonth();
        }
        Double monthTraffic = systemMonthDmo.getMonthTraffic();
        Long monthPackTraffic = systemMonthDmo.getMonthPackTraffic();
        Double monthRemainingTraffic;
        if (monthTraffic >= monthPackTraffic) {
            monthRemainingTraffic = 0d;
        } else {
            monthRemainingTraffic = monthPackTraffic - monthTraffic;
        }
        realTimeTrafficDto.setMonthTraffic(systemMonthDmo.getMonthTraffic());
        realTimeTrafficDto.setMonthPackTraffic(systemMonthDmo.getMonthPackTraffic());
        realTimeTrafficDto.setMonthRemainingTraffic(monthRemainingTraffic);

        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        rtuMonthDmo.setMonthDate(getMonth());
        rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);
        //        套餐流量占比
        List<HashMap> packageNumberMapList = commonDao.selectList("RealTimeTrafficMapper.querySystemPackageNumberMapList", rtuMonthDmo);
        if (CollectionUtils.isEmpty(packageNumberMapList)) {
            packageNumberMapList = new ArrayList<>();
        }
        realTimeTrafficDto.setPackageNumberMapList(packageNumberMapList);
        SystemDayDmo systemDayDmo = new SystemDayDmo();
        systemDayDmo.setStatus(Constants.TrafficStatus.NORMAL);
        List<DayTrafficDto> dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.querySystemDayTrafficUsage", systemDayDmo);
        if (null == dayTrafficDtoList) {
            dayTrafficDtoList = new ArrayList<>();
        }
        if (dayTrafficDtoList.size() != dayOfMonth - 1) {
            List<Integer> existDayList = commonDao.selectList("RealTimeTrafficMapper.querySystemDayTrafficDay", systemDayDmo);
            for (int i = 1; i < dayOfMonth; i++) {
                if (null == existDayList || !existDayList.contains(i)) {
                    SystemDayDmo systemDayDmo1 = new SystemDayDmo();
                    Date date = getDayOfMonth(i);
                    systemDayDmo1.setDayDate(date);
                    systemDayDmo1.setDayTraffic(0d);
                    systemDayDmo1.setCreateTime(new Date());
                    systemDayDmo1.setLastUpdateTime(new Date());
                    systemDayDmo1.setStatus(Constants.TrafficStatus.NORMAL);
                    int j=commonDao.insert(systemDayDmo1);
                    if(1!=j){
                        logger.error("插入云平台日流量信息失败");
                    }
                }
            }
        }
        dayTrafficDtoList = commonDao.selectList("RealTimeTrafficMapper.querySystemDayTrafficUsage", systemDayDmo);
        realTimeTrafficDto.setDayTrafficDtoList(dayTrafficDtoList);
        realTimeTrafficResult.setSuccess(true);
        realTimeTrafficResult.setRealTimeTrafficDto(realTimeTrafficDto);
        return realTimeTrafficResult;
    }

    private SystemMonthDmo createSystemMonth() {

        StatisticsDto systemDayMonthDto = new StatisticsDto();
        systemDayMonthDto.setStatisticsDate(getMonth());

        //    云平台月流量
        StatisticsDto statisticsMonthDto = (StatisticsDto) commonDao.selectOne("RealTimeTrafficMapper.statisticsMonthTraffic", systemDayMonthDto);

        Double monthTraffic = 0D;
        Long monthPackTraffic = 0L;
        if (null != statisticsMonthDto) {
            if (null != statisticsMonthDto.getUsedTraffic()) {
                monthTraffic = statisticsMonthDto.getUsedTraffic();
            }
            if (null != statisticsMonthDto.getPackTraffic()) {
                monthPackTraffic = statisticsMonthDto.getPackTraffic();
            }
        }
            SystemMonthDmo addOrUpdateDmo = new SystemMonthDmo();
            addOrUpdateDmo.setMonthDate(getMonth());
            addOrUpdateDmo.setMonthTraffic(monthTraffic);
            addOrUpdateDmo.setMonthPackTraffic(monthPackTraffic);
            addOrUpdateDmo.setCreateTime(DateUtil.getDate());
            addOrUpdateDmo.setLastUpdateTime(DateUtil.getDate());
            addOrUpdateDmo.setStatus(StatisticsConstants.Status.NORMAL);
            int i = commonDao.insert(addOrUpdateDmo);
            if(1!=i){
                logger.error("插入云平台月流量信息失败");
            }
            return addOrUpdateDmo;
        }

    private boolean isToday(Date date) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        return fmt.format(date).equals(fmt.format(new Date()));
    }

    private Date getMonth() {
        return getDayOfMonth(1);
    }

    private int dayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    private Date getDayOfMonth(int index) {

        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, index);
// 将时分秒,毫秒域清零
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        cale.set(Calendar.MILLISECOND, 0);
        return cale.getTime();
    }

}
