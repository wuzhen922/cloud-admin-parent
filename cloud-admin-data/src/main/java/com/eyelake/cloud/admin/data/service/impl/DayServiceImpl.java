package com.eyelake.cloud.admin.data.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyiotapi.model.v20171111.QueryCardFlowInfoRequest;
import com.aliyuncs.dyiotapi.model.v20171111.QueryCardFlowInfoResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.*;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.result.QueryRtuMonthTrafficAndPackResult;
import com.eyelake.cloud.admin.data.service.intf.DayService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.eyelake.cloud.common.utils.PropertiesUtil;
import com.eyelake.cloud.common.utils.SDKUtil;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.utils.DateUtil;
import com.yjh.framework.lang.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("dayService")
@ServiceTrace
public class DayServiceImpl implements DayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    private static final String REQUEST_SUCCESS_FLAG = "OK";
    private static final String FIRST_DAY_OF_MONTH_FLAG = "01";

//
//    //初始化ascClient需要的几个参数
//    private static final String PRODUCT = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_PRODUCT);//物联卡API产品名称（短信产品名固定，无需修改）
//    private static final String DOMAIN = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_DOMAIN);//物联卡API产品域名（接口地址固定，无需修改）
//    //替换成你的AK
//    private static final String ACCESS_KEY_ID = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_ID);;//你的accessKeyId,参考本文档步骤2
//    private static final String ACCESS_KEY_SECRET = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG,PropertiesConstants.API_ACCESS_KEY_SECRET);;//你的accessKeySecret，参考本文档步骤2
//
//

//
//
//    public static IAcsClient init() {
//        //设置超时时间-可自行调整
//        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
//        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
//        //初始化ascClient需要的几个参数
//
//        //初始化ascClient,暂时不支持多region（请勿修改）
//        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESS_KEY_ID,
//                ACCESS_KEY_SECRET);
//        try {
//            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
//        } catch (ClientException e) {
//            e.printStackTrace();
//        }
//        return new DefaultAcsClient(profile);
//    }


    @Override
    public Result insertRtuDayTraffic(QueryRtuDto rtuDto) {

        Result result = new Result();

        //判断当前日期是否为当月第一天
        String firstDayOfMonth = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMdd).substring(6);

        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        Date queryDate = dateFormatToMonth(DateUtil.getDate());
        rtuMonthDmo.setMonthDate(queryDate);
        rtuMonthDmo.setSnNumber(rtuDto.getSnNumber());
        rtuMonthDmo.setStatus(Constants.RtuStatus.NORMAL);

        //判断当前查询时间的网关月流量数据是否存在
        RtuMonthDmo existRtu = (RtuMonthDmo) commonDao.selectOne(rtuMonthDmo);

        /*
         * 计算日流量，此处分为三种情况
         * 1、每月的第一天，所查到的流量记为当日流量
         * 2、网关月中被激活，当天的日流量记为0
         * 3、除此之外，今日查询的流量减去昨日查询的流量，记为网关日流量
         */
        Double dayTraffic = 0D;
        if (FIRST_DAY_OF_MONTH_FLAG.equals(firstDayOfMonth)) {
            dayTraffic = rtuDto.getCostTraffic();
        } else {
            dayTraffic = (existRtu == null ? 0D : (rtuDto.getCostTraffic() - existRtu.getMonthTraffic()));
        }

        // TODO: 2018-1-24 缺少非空校验
        RtuDayDmo rtuDayDmo = new RtuDayDmo();
        rtuDayDmo.setSnNumber(rtuDto.getSnNumber());
        rtuDayDmo.setDayDate(dateFormatToDay(DateUtil.getDate()));
        rtuDayDmo.setStatus(Constants.RtuStatus.NORMAL);

        //根据网关的snNumber和当前时间（精确到日）查询网关日流量表中是否有此记录
        //如果有，则更新，否则插入

        RtuDayDmo existRtuDayTraffic = (RtuDayDmo) commonDao.selectOne(rtuDayDmo);

        rtuDayDmo.setDayTraffic(dayTraffic);
        rtuDayDmo.setIntegratorId(rtuDto.getIntegratorId());
        rtuDayDmo.setCreateTime(DateUtil.getDate());
        rtuDayDmo.setLastUpdateTime(DateUtil.getDate());

        if (null == existRtuDayTraffic) {

            int insertRtuDay = commonDao.insert(rtuDayDmo);
            if (insertRtuDay < 1) {
                result.fail("", "新增网关日流量信息失败");
                LOGGER.error("新增网关日流量信息失败");
            }
        } else {
            rtuDayDmo.setId(existRtuDayTraffic.getId());
            int updateRtuDay = commonDao.update(rtuDayDmo);

            if (updateRtuDay < 1) {
                result.fail("", "更新网关日流量信息失败");
                LOGGER.error("更新网关日流量信息失败");
            }
        }

        result.setSuccess(true);
        return result;
    }

    @Override
    public Result updateRtuWarningStatus(QueryRtuDto rtuDto) {

        Result result = new Result();

        //套餐已使用量(K)
        Double costNum = rtuDto.getCostTraffic();

        //套餐流量(K)
        Long packTraffic = rtuDto.getPackTraffic();

        RtuWarningDmo warningDmo = new RtuWarningDmo();
        warningDmo.setSnNumber(rtuDto.getSnNumber());
        warningDmo.setStatus(Constants.RtuStatus.NORMAL);
        RtuWarningDmo existRtuWarn = (RtuWarningDmo) commonDao.selectOne(warningDmo);

        //如果该网关预警记录已存在，则更新
        if (null != existRtuWarn) {

            //如果预警值为空，设定预警值为当月套餐总流量
            Double warningNum = existRtuWarn.getWarningNum() == null ? packTraffic : existRtuWarn.getWarningNum();

            /*
             * 比较套餐已使用量（costNum）与预警值（warningNum），总流量（packTraffic）大小关系，确定预警状态
             * 1、costNum < warningNum 正常
             * 2、warningNum <= costNum < packTraffic 预警
             * 3、costNum >= packTraffic   流量用尽
             */
            if (costNum < warningNum) {
                warningDmo.setWarningStatus(Constants.RtuWarningStatus.NORMAL);
            } else if (costNum < packTraffic) {
                warningDmo.setWarningStatus(Constants.RtuWarningStatus.WARNING);
            } else {
                warningDmo.setWarningStatus(Constants.RtuWarningStatus.EXHAUST);
            }

            warningDmo.setId(existRtuWarn.getId());
            warningDmo.setCostNum(costNum);
            warningDmo.setPackTraffic(packTraffic / 1024);
            warningDmo.setWarningNum(warningNum);
            warningDmo.setPackName(rtuDto.getFixedPackName());
            warningDmo.setLastUpdateTime(DateUtil.getDate());

            int updateWarn = commonDao.update(warningDmo);
            if (updateWarn < 0) {
                result.fail("","更新网关预警信息失败");
                LOGGER.error("更新网关预警信息失败");
            }

        } else {

            //如果网关预警表不存在，则插入
            warningDmo.setSnNumber(rtuDto.getSnNumber());
            warningDmo.setCostNum(costNum);
            warningDmo.setPackTraffic(packTraffic / 1024);
            warningDmo.setPackName(rtuDto.getFixedPackName());
            warningDmo.setWarningNum(packTraffic.doubleValue());
            warningDmo.setCreateTime(DateUtil.getDate());
            warningDmo.setWarningStatus(Constants.RtuWarningStatus.NORMAL);
            warningDmo.setStatus(Constants.RtuStatus.NORMAL);
            warningDmo.setLastUpdateTime(DateUtil.getDate());

            int insertWarn = commonDao.insert(warningDmo);
            if (insertWarn != 1) {
                result.fail("","新增网关预警信息失败");
                LOGGER.error("新增网关预警信息失败");
            }
        }

        result.setSuccess(true);
        return result;
    }


    @Override
    public List<QueryRtuDto> selectRtuList() {

        RtuDmo rtuDmo = new RtuDmo();
        return commonDao.selectList("DayTimer.queryRtuList", rtuDmo);
    }


    @Override
    public QueryRtuMonthTrafficAndPackResult queryRtuMonthTrafficAndPack(QueryRtuDto rtuDto) {

        QueryRtuMonthTrafficAndPackResult result = new QueryRtuMonthTrafficAndPackResult();

        QueryRtuDto queryRtuDto = new QueryRtuDto();

        queryRtuDto.setId(rtuDto.getId());
        queryRtuDto.setSnNumber(rtuDto.getSnNumber());
        queryRtuDto.setIntegratorId(rtuDto.getIntegratorId());

        QueryCardFlowInfoRequest request = new QueryCardFlowInfoRequest();



        QueryCardFlowInfoResponse acsResponse = null;

        try {
            IAcsClient client = SDKUtil.init();
            request.setIccid(rtuDto.getSnNumber());
            acsResponse = client.getAcsResponse(request);
        } catch (ClientException e) {
            LOGGER.info("调用接口异常");
            e.printStackTrace();
        }


        //接口测试用例

//        QueryCardFlowInfoResponse acsResponse = new QueryCardFlowInfoResponse();
//
//        List<QueryCardFlowInfoResponse.CardFlowInfo> list = new ArrayList<>();
//
//        QueryCardFlowInfoResponse.CardFlowInfo cardFlowInfo1 = new QueryCardFlowInfoResponse.CardFlowInfo();
//        cardFlowInfo1.setResourceType("6700001");
//        cardFlowInfo1.setResName("物联网-联通-Internet-自定义流量包");
//        cardFlowInfo1.setFlowResource(1048576L);
//        cardFlowInfo1.setRestOfFlow(1048476L);
//        cardFlowInfo1.setFlowUsed(100L);
//        cardFlowInfo1.setValidDate("20171106174912");
//        cardFlowInfo1.setExpireDate("20180504235959");
//
//        list.add(cardFlowInfo1);
//
//        acsResponse.setCardFlowInfos(list);
//        acsResponse.setCode("OK");



        //请求成功处理逻辑
        if (acsResponse != null && REQUEST_SUCCESS_FLAG.equals(acsResponse.getCode())) {


            if (!CollectionUtils.isEmpty(acsResponse.getCardFlowInfos())) {

                //套餐总流量
                Long totalFlowResource = 0L;
                //套餐剩余总流量
                Long totalRestOfFlow = 0L;
                //套餐总已使用用量
                Long totalFlowUsed = 0L;
                //套餐总名称（多个套餐名称拼接而成）
                StringBuffer stringBuffer = new StringBuffer();

                for (QueryCardFlowInfoResponse.CardFlowInfo cardFlowInfo : acsResponse.getCardFlowInfos()) {

                    //获取固定套餐信息（套餐名称、套餐流量、已使用流量）
                    String packName = cardFlowInfo.getResName();

                    stringBuffer.append(packName).append(",");

                    if (null != cardFlowInfo.getFlowResource()) {
                        totalFlowResource += cardFlowInfo.getFlowResource();
                    }

                    if (null != cardFlowInfo.getFlowUsed()) {
                        totalFlowUsed += cardFlowInfo.getFlowUsed();
                    }
                }

                String totalPackName = stringBuffer.toString();
                queryRtuDto.setPackTraffic(totalFlowResource);
                queryRtuDto.setCostTraffic(totalFlowUsed.doubleValue());
                queryRtuDto.setFixedPackName(totalPackName);

                //更新网关月流量,同时插入网关日流量表
                Result updateRtuMonthResult = updateRtuMonth(queryRtuDto);
                if (!updateRtuMonthResult.isSuccess()) {
                    result.setSuccess(false);
                    LOGGER.error("更新(插入)网关月流量信息失败");

                }

                //更新网关预警表信息
                Result updateRtuWaringStatusResult = updateRtuWarningStatus(queryRtuDto);
                if (!updateRtuWaringStatusResult.isSuccess()) {
                    result.fail("","更新(插入)网关预警表信息失败");
                    LOGGER.error("更新(插入)网关预警表信息失败");
                    result.setRtudto(queryRtuDto);

                }


            }

            //请求失败处理逻辑，更新（插入）网关月流量，插入网关日流量表（设为0）

        } else {

            LOGGER.error("调用QueryCardFlowInfoRequest接口失败情况下，处理逻辑开始.......");

            RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
            rtuMonthDmo.setMonthDate(getMonth());
            rtuMonthDmo.setSnNumber(rtuDto.getSnNumber());
            rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);

            RtuMonthDmo existRtu = (RtuMonthDmo) commonDao.selectOne(rtuMonthDmo);

            if (existRtu != null) {

                RtuMonthDmo updateRtu = new RtuMonthDmo();
                updateRtu.setId(existRtu.getId());
                updateRtu.setLastUpdateTime(DateUtil.getDate());

                int updateRtuMonth = commonDao.update(updateRtu);

                if (updateRtuMonth < 0) {
                    LOGGER.info("接口调用失败情况下，更新网关月流量表失败");
                    result.fail("","接口调用失败情况下，更新网关月流量表失败");
                }
            } else {
                rtuMonthDmo.setMonthDate(dateFormatToMonth(DateUtil.getDate()));
                rtuMonthDmo.setMonthTraffic(0D);
                rtuMonthDmo.setMonthPackTraffic(0L);
                rtuMonthDmo.setIntegratorId(queryRtuDto.getIntegratorId());
                rtuMonthDmo.setCreateTime(DateUtil.getDate());
                rtuMonthDmo.setLastUpdateTime(DateUtil.getDate());

                int insertRtuMonth = commonDao.insert(rtuMonthDmo);

                if (insertRtuMonth < 1) {
                    LOGGER.info("接口调用失败情况下，新增网关月流量表失败");
                    result.fail("","接口调用失败情况下，新增网关月流量表失败");

                }
            }

            //如果调用接口失败，新增网关日流量表，日流量设为0

            RtuDayDmo rtuDayDmo = new RtuDayDmo();
            rtuDayDmo.setSnNumber(rtuDto.getSnNumber());
            rtuDayDmo.setDayDate(dateFormatToDay(DateUtil.getDate()));

            //判断当天网关日流量表是否存在，主要防止该接口被多次调用，导致重复添加日流量记录
            RtuDayDmo existRtuDayTraffic = (RtuDayDmo) commonDao.selectOne(rtuDayDmo);

            if (null == existRtuDayTraffic) {

                rtuDayDmo.setSnNumber(rtuDto.getSnNumber());
                rtuDayDmo.setDayTraffic(0D);
                rtuDayDmo.setDayDate(dateFormatToDay(DateUtil.getDate()));
                rtuDayDmo.setIntegratorId(rtuDto.getIntegratorId());
                rtuDayDmo.setCreateTime(DateUtil.getDate());
                rtuDayDmo.setLastUpdateTime(DateUtil.getDate());
                rtuDayDmo.setStatus(Constants.TrafficStatus.NORMAL);

                int insertRtuDayResult = commonDao.insert(rtuDayDmo);

                if (insertRtuDayResult < 1) {
                    result.setSuccess(false);
                    LOGGER.error("调用接口失败情况下，新增网关日流量信息失败");
                    return result;
                }

            } else {

                RtuDayDmo rtuDayDmo1 = new RtuDayDmo();
                rtuDayDmo1.setId(existRtuDayTraffic.getId());
                rtuDayDmo1.setLastUpdateTime(DateUtil.getDate());

                int updateRtuResult = commonDao.update(rtuDayDmo1);

                if (updateRtuResult < 1) {
                    result.setSuccess(false);
                    LOGGER.error("调用接口失败情况下，更新网关日流量信息失败");
                    return result;
                }
            }

            LOGGER.error("调用QueryCardFlowInfoRequest接口失败情况下，处理逻辑结束.......");
            result.setSuccess(false);
            return result;
        }


        result.setRtudto(queryRtuDto);
        result.setSuccess(true);

        return result;
    }

    private Date getMonth() {
        return getDayOfMonth(1);
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


    @Override
    public Result updateRtuMonth(QueryRtuDto con) {

        Result result = new Result();

        //插入网关日流量表
        Result insertRtuDayResult = insertRtuDayTraffic(con);
        if (!insertRtuDayResult.isSuccess()) {
            result.fail("", "插入网关日流量失败！");
        }

        RtuMonthDmo rtuMonthDmo = new RtuMonthDmo();
        Date queryDate = dateFormatToMonth(DateUtil.getDate());
        rtuMonthDmo.setMonthDate(queryDate);
        rtuMonthDmo.setSnNumber(con.getSnNumber());
        rtuMonthDmo.setStatus(Constants.TrafficStatus.NORMAL);

        //根据网关SN码和查询月（精确到月）查询是否存在此记录
        RtuMonthDmo existRtu = (RtuMonthDmo) commonDao.selectOne(rtuMonthDmo);

        rtuMonthDmo.setMonthTraffic((con.getCostTraffic()/1024));
        rtuMonthDmo.setMonthPackTraffic(con.getPackTraffic()/1024);
        rtuMonthDmo.setIntegratorId(con.getIntegratorId());
        rtuMonthDmo.setFixedPackName(con.getFixedPackName());
        rtuMonthDmo.setLastUpdateTime(DateUtil.getDate());

        if (null != existRtu) {

            rtuMonthDmo.setId(existRtu.getId());
            //更新网关月使用流量
            int updateRtuResult = commonDao.update(rtuMonthDmo);
            if (updateRtuResult < 1) {
                LOGGER.error("更新网关月流量信息失败");
                result.setSuccess(false);
                return result;
            }

        } else {
            //插入网关月流量信息
            rtuMonthDmo.setSnNumber(con.getSnNumber());
            rtuMonthDmo.setCreateTime(DateUtil.getDate());
            rtuMonthDmo.setStatus(Constants.RtuStatus.NORMAL);

            int insertRtuResult = commonDao.insert(rtuMonthDmo);

            if (insertRtuResult < 1) {
                LOGGER.error("插入网关月流量信息失败");

                result.setSuccess(false);
                return result;
            }
        }
        result.setSuccess(true);
        return result;
    }


    private Date dateFormatToDay (Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date newDate = null;
        try {
            newDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDate;
    }

    private Date dateFormatToMonth (Date date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date newDate = null;
        try {
            newDate = sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

}

