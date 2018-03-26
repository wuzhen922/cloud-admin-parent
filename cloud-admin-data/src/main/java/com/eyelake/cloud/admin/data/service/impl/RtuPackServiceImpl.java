package com.eyelake.cloud.admin.data.service.impl;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyiotapi.model.QueryCardOfferDtlRequest;
import com.aliyuncs.dyiotapi.model.QueryCardOfferDtlResponse;
import com.aliyuncs.exceptions.ClientException;
import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuPackDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.data.dto.RtuPackDto;
import com.eyelake.cloud.admin.data.service.intf.RtuPackService;

import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.utils.SDKUtil;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.utils.DateUtil;
import com.yjh.framework.lang.Result;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("rtuPackService")
@ServiceTrace
public class RtuPackServiceImpl implements RtuPackService {


    private static final Logger LOGGER = LoggerFactory.getLogger(RtuPackServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public void rtuPackInfo(String snNumber) {

        //调用外部接口，得到套餐List
        //构建该API特定的请求参数
        QueryCardOfferDtlRequest request = new QueryCardOfferDtlRequest ();
        QueryCardOfferDtlResponse acsResponse = null;
        try {
            IAcsClient acsClient = SDKUtil.init();

            //填入你要查询的iccid值
            request.setIccid(snNumber);
            //请求失败这里会抛ClientException异常
            acsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {

            LOGGER.info("网关序列码为" + snNumber + "物联网卡套餐查询（QueryIotCardOfferDtl）接口调用失败");
            e.printStackTrace();
            return;
        }

        if(acsResponse.getCode() != null && acsResponse.getCode().equals("OK")) {
            //请求成功
            List<QueryCardOfferDtlResponse.CardOfferDetail> cardOfferInfoList = acsResponse.getCardOfferDetail();
            //如果没有获取到套餐List，则直接返回，不进行操作
            if (CollectionUtils.isEmpty(cardOfferInfoList)) {
                LOGGER.info("查询"+snNumber+"该网关的套餐列表为空，不进行操作，直接返回");
                return;
            }
            //查询网关与套餐关联表，删除该SN码关联的套餐
            RtuPackDmo rtuPackDmo = new RtuPackDmo();
            rtuPackDmo.setSnNumber(snNumber);
            rtuPackDmo.setStatus(Constants.CommonStatus.DELETE);
            Result deleteResult = deleteRtuPackInfo(rtuPackDmo);

            if (!deleteResult.isSuccess()) {
                LOGGER.info("删除网关序列号为："+snNumber+"的网关与套餐关联表信息失败");
                return;
            }

            //批量新增网关与套餐的关联关系
            List<RtuPackDmo> rtuPackDmoList = new ArrayList<>();
            for (QueryCardOfferDtlResponse.CardOfferDetail cardOffer :cardOfferInfoList ) {

                if (timeCompare(cardOffer.getEffectiveTime(),cardOffer.getExpireTime())){
                    RtuPackDmo rtuPack = new RtuPackDmo();
                    rtuPack.setSnNumber(snNumber);
                    rtuPack.setPackNum(cardOffer.getOfferId());
                    rtuPack.setStatus(Constants.CommonStatus.NORMAL);
                    rtuPack.setCreateTime(DateUtil.getDate());
                    rtuPack.setLastUpdateTime(DateUtil.getDate());

                    rtuPackDmoList.add(rtuPack);
                }

            }

            RtuPackDto rtuPackDto = new RtuPackDto();
            rtuPackDto.setRtuPackDmoList(rtuPackDmoList);

            int i  = commonDao.batchInsert("RtuPackInfoMapper.batchInsertRtuPackInfo",rtuPackDto);

            if (i != rtuPackDmoList.size()) {

                LOGGER.error("网关序列码为" + snNumber + "批量更新网关与套餐关联表失败");
                return;
            }

        } else {
            //打印失败日志
            LOGGER.info(acsResponse.getCode());
            return;
        }


    }

    @Override
    public Result deleteRtuPackInfo(RtuPackDmo con) {

        Result result = new Result();
        int i=commonDao.update("RtuPackInfoMapper.updateRtuPackInfo",con);

        if (i<0) {

            result.fail("","删除网关与套餐关联关系失败");
        }

        return result;
    }

    @Override
    public List<QueryRtuDto> queryRtuByStatus() {

        QueryRtuDto queryRtuDto = new QueryRtuDto();
        List<QueryRtuDto> rtuDtoList = commonDao.selectList("RtuMapper.queryRtuList",queryRtuDto);

        if (org.springframework.util.CollectionUtils.isEmpty(rtuDtoList)) {
            LOGGER.info("查询网关为空");
            return rtuDtoList;
        }

        return  rtuDtoList;

    }

    /**
     * 为了只选取当月套餐，进行时间比较
     * @param effectiveTime
     * @param expireTime
     * @return
     */
    public boolean timeCompare(String effectiveTime,String expireTime) {

        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );

        long dt1 = 0L;
        long dt2 = 0L;
        long now = 0L;
        try {
            dt1 = sdf.parse(effectiveTime).getTime();
            dt2 = sdf.parse(expireTime).getTime();
            now = new Date().getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (now<=dt2 && now >= dt1) {
            return true;
        }
        return false;
    }
}
