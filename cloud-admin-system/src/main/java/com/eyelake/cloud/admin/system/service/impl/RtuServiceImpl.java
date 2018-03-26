package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.*;
import com.eyelake.cloud.admin.assist.result.ImportRtusResult;
import com.eyelake.cloud.admin.system.service.intf.RtuService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.eyelake.cloud.common.utils.PropertiesUtil;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.eyelake.framework.lang.AppException;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RtuService接口实现类
 *
 * @author  j_cong
 * @date    2017/12/14
 * @version V1.0
 */

@Service("rtuService")
@ServiceTrace
public class RtuServiceImpl implements RtuService {

    private static Logger logger = LoggerFactory.getLogger(RtuServiceImpl.class);

    @Autowired
    private CommonDao commonDao;

    @Override
    public List<QueryRtuDto> selectListByPage(QueryRtuDto con, Page page) {
        return commonDao.selectListByPage("RtuMapper.queryRtuCount",
                "RtuMapper.queryRtuList", con, page);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result changeRtuStatus(RtuDmo con) {

        Result result = new Result();

        String status = con.getStatus();
        con.setLastUpdateTime(DateUtil.getDate());

            //如果RTU状态为“正常”，则进入的操作为“禁用”
            //如果RTU状态为“禁用”，则进入的操作为“启用”
            if (Constants.RtuStatus.NORMAL.equals(status)) {
                con.setStatus(Constants.RtuStatus.DISABLED);
            } else {
                con.setStatus(Constants.RtuStatus.NORMAL);
            }

            //更改RTU状态
            int i = commonDao.update("RtuMapper.changeRtuStatus", con);

            if (i < 0) {
                result.fail();
                result.setMessage("更改RTU状态失败");
                return result;
            }

        return result;
    }

    @Override
    public Result deleteRtu(RtuDmo con) {

        Result result = new Result();

        int i = commonDao.update("RtuMapper.deleteRtu", con);

        if (i < 0) {
            result.fail();
            result.setMessage("删除RTU失败");
            return result;
        }
        return result;
    }

    @Override
    public Result batchDeleteRtu(List<RtuDmo> deleteRtuIdList) {

        Result result = new Result();

        BatchDeleteRtuDto batchDeleteRtuDto = new BatchDeleteRtuDto();
        batchDeleteRtuDto.setRtuDmoList(deleteRtuIdList);
        batchDeleteRtuDto.setStatus(Constants.RtuStatus.DELETE);
        int i = commonDao.update("RtuMapper.batchDeleteRtu", batchDeleteRtuDto);

        if (i < 1) {
            result.fail();
            result.setMessage("批量删除RTU失败");
            return result;
        }

        return result;
    }

    @Override
    public ImportRtusResult importRtu(List<LinkedHashMap<String, Object>> excelData) {
        int count = 0;
        int dataSize = excelData.size();
        //导出数据的头
        String[] headers = new String[]{"网关名称","网关型号", "网关传输类型", "网关接入类型","网关序列码","网关机器码","运营商编号","集成商入网许可号","失败原因"};

        ImportRtusResult result = new ImportRtusResult();
        result.setHeaders(headers);

        //需要批量插入的数据
        List<LinkedHashMap<String, Object>> batchInsertFailedList = new ArrayList<>();

        //导入失败的数据
        List<LinkedHashMap<String, Object>> importFailedList = new ArrayList<>();

        List<RtuDmo> rtuDmoList = new ArrayList<>();

        IntegratorDmo dmo = new IntegratorDmo();

        HashMap<String,IntegratorDmo> integratorMap = new HashMap<>();
        List<IntegratorDmo> integratorList= commonDao.selectList("IntegratorMapper.selectIntegratorAccessCode",dmo);
       if (!CollectionUtils.isEmpty(integratorList)) {
           for (IntegratorDmo integrator : integratorList) {

               integratorMap.put(integrator.getAdmittance(),integrator);
           }
       } else {
           logger.info("查询集成商列表为空");
       }

        Map<String,RtuDmo> machineCodeMap = new HashMap<>();
        Map<String,RtuDmo> snNumberMap = new HashMap<>();

        RtuDmo rtuDmo = null;


        try {
            for (LinkedHashMap<String, Object> data : excelData) {

                rtuDmo = new RtuDmo();

                String rtuName = getDataString(data,Constants.RtuImport.RTU_NAME );
                String rtuModel = getDataString(data,Constants.RtuImport.RTU_MODEL );
                String rtuTransType = getDataString(data,Constants.RtuImport.RTU_TRANS_TYPE);
                String rtuAccessType = getDataString(data,Constants.RtuImport.RTU_ACCESS_TYPE );
                String rtuMachineCode = getDataString(data,Constants.RtuImport.RTU_MACHINE_CODE);
                String snNumber = getDataString(data,Constants.RtuImport.SN_NUMBER);
                String rtuOperator = getDataString(data,Constants.RtuImport.RTU_OPERATOR);
                String integratorAccessCode = getDataString(data,Constants.RtuImport.INTEGRATOR_ACCESS_CODE);

                if (checkVehicleParam(rtuName,rtuModel,rtuTransType,rtuAccessType,rtuMachineCode,snNumber)) {

                    String rtuNameMaxLength = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG, PropertiesConstants.IMPORT_RTU_NAME_MAX_LENGTH);


                    if ( Integer.valueOf(rtuNameMaxLength) < rtuName.length()) {

                        count++;
                        data.put("失败原因","网关名称字符长度应小于32位");
                        importFailedList.add(data);
                        logger.warn("网关名称字符长度应小于32位！");
                        continue;

                    }

                    rtuDmo.setRtuName(rtuName);
                    rtuDmo.setRtuModel(rtuModel);

                    //网关传输类型
                    String rtuTransTypeEnum = Constants.RTU_TRANS_TYPE_MAP.get(rtuTransType);

                    if(StringUtils.isBlank(rtuTransTypeEnum)){
                        count++;
                        data.put("失败原因","网关传输类型只能填4G、NB-IoT、北斗和天通");
                        importFailedList.add(data);
                        logger.warn("网关传输类型只能填4G、NB-IoT、北斗和天通！");
                        continue;
                    }

                    rtuDmo.setRtuTransType(rtuTransTypeEnum);

                    //网关接入类型
                    String rtuAccessTypeEnum = Constants.RTU_ACCESS_TYPE_MAP.get(rtuAccessType);

                    if(StringUtils.isBlank(rtuAccessTypeEnum)){
                        count++;
                        data.put("失败原因","网关接入类型只能填单通道或多通道");
                        importFailedList.add(data);
                        logger.warn("网关接入类型只能填单通道或多通道！");
                        continue;
                    }

                    rtuDmo.setRtuAccessType(rtuAccessTypeEnum);

                    //运营商
                    if (StringUtils.isNotBlank(rtuOperator)) {

                        if (!(Constants.OperatorEnum.CHINA_MOBILE.equals(rtuOperator) || Constants.OperatorEnum.CHINA_TELECOM.equals(rtuOperator)
                                || Constants.OperatorEnum.CHINA_UNICOM.equals(rtuOperator))) {

                            count++;
                            data.put("失败原因","运营商编号填写错误，移动00，联通01，电信03");
                            importFailedList.add(data);
                            logger.warn("运营商编号填写错误，移动00，联通01，电信03！");
                            continue;
                        }
                    }


                    rtuDmo.setCarrierOperatorEnum(rtuOperator);

                    // 集成商，去集成商Map查是否存在该集成商,不存在就失败次数count加1
                    if(StringUtils.isNotBlank(integratorAccessCode)){

                        if (!integratorMap.containsKey(integratorAccessCode)) {

                            count++;
                            data.put("失败原因","该集成商已不存在");
                            importFailedList.add(data);
                            logger.warn("该集成商已不存在！");
                            continue;
                        } else{

                            IntegratorDmo existIntegratorDmo = integratorMap.get(integratorAccessCode);
                            if (Constants.IntegratorStatus.DISABLED.equals(existIntegratorDmo.getStatus())) {
                                count++;
                                data.put("失败原因","该集成商已被禁用，不可添加网关");
                                importFailedList.add(data);
                                logger.warn("该集成商已被禁用，不可添加网关！");
                                continue;
                            }
                            rtuDmo.setIntegratorId(existIntegratorDmo.getId());
                            if (StringUtils.isNotBlank(existIntegratorDmo.getCompany())){
                                rtuDmo.setIntegratorName(existIntegratorDmo.getCompany());
                            }
                        }

                    }

                    //网关序列码
                    RtuDmo existBySnDmo = new RtuDmo();
                    existBySnDmo.setSnNumber(snNumber);
                    RtuDmo existBySnRtu = (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existBySnDmo);

                    if (null != existBySnRtu && !rtuMachineCode.equals(existBySnRtu.getMachineCode())) {

                        count++;
                        data.put("失败原因","该网关序列码已被其他网关占用，请检查所填写的网关序列码");
                        importFailedList.add(data);
                        logger.warn("该网关序列码已被其他网关占用，请检查所填写的网关序列码！");
                        continue;
                    }else {
                        if (!CollectionUtils.isEmpty(snNumberMap)) {
                            RtuDmo existRtuSnNumberInMap = snNumberMap.get(snNumber);
                            //若map中已存在，就count加1
                            if (null != existRtuSnNumberInMap) {
                                count++;
                                data.put("失败原因", "该网关序列码与本次导入成功的记录有重复，不能重复导入");
                                importFailedList.add(data);
                                continue;
                            }

                        }
                    }

                    rtuDmo.setSnNumber(snNumber);

                    //根据机器码和状态（10.20）判断rtu是否存在，禁用的不修改，正常并且是该集成商下,接入类型相同的的可更新
                    //网关机器码
                    RtuDmo existByMachineCodeDmo = new RtuDmo();
                    existByMachineCodeDmo.setMachineCode(rtuMachineCode);
                    RtuDmo existByMachineCodeRtu = (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existByMachineCodeDmo);
                    if (null != existByMachineCodeRtu) {

                        if(! Constants.RtuStatus.INACTIVE.equals(existByMachineCodeRtu.getStatus())) {

                            count++;
                            data.put("失败原因", "该网关已经被激活，不可更改网关信息，请查看网关机器码");
                            importFailedList.add(data);
                            continue;
                        }

                        rtuDmo.setMachineCode(rtuMachineCode);
                        rtuDmo.setLastUpdateTime(DateUtil.getDate());
                        int i = commonDao.update("RtuMapper.updateRtuByMachine",rtuDmo);
                        if(i < 1){
                            count++;
                            data.put("失败原因","网关信息更新失败");
                            importFailedList.add(data);
                            logger.warn("网关信息更新失败！");
                        }
                        continue;
                    }else {

                        if (!CollectionUtils.isEmpty(machineCodeMap)) {
                            RtuDmo existRtuMachineCodeInMap = machineCodeMap.get(rtuMachineCode);
                            //若map中已存在，就count加1
                            if (null != existRtuMachineCodeInMap) {
                                count++;
                                data.put("失败原因", "该网关机器码与本次导入成功的记录有重复，不能重复导入");
                                importFailedList.add(data);
                                continue;
                            }

                        }

                    }

                    rtuDmo.setMachineCode(rtuMachineCode);
                    rtuDmo.setCreateTime(DateUtil.getDate());
                    rtuDmo.setLastUpdateTime(DateUtil.getDate());
                    rtuDmo.setStatus(Constants.RtuStatus.INACTIVE);

                    machineCodeMap.put(rtuMachineCode,rtuDmo);
                    snNumberMap.put(snNumber,rtuDmo);
                    rtuDmoList.add(rtuDmo);

                    batchInsertFailedList.add(data);

                } else {
                    count++;
                    data.put("失败原因","导入数据有误，网关名称、型号、接入类型、传输类型、机器码、序列码不能为空，机器码32位数字字母组合，序列码20位数字");
                    importFailedList.add(data);
                    logger.warn("导入数据有误，网关名称、型号、接入类型、传输类型、机器码、序列码不能为空，机器码32位数字字母组合，序列码20位数字！");
                    continue;
                }

            }
        } catch (Exception e) {
            logger.error("exist error row data", e);
            throw new AppException("导入的网关数据解析异常！");
        }

        if (!CollectionUtils.isEmpty(rtuDmoList)) {

            BatchInsertRtuDto batchInsertRtuDto = new BatchInsertRtuDto();
            batchInsertRtuDto.setInsertRtuList(rtuDmoList);

            int u = commonDao.batchInsert("RtuMapper.batchInsertRtuList", batchInsertRtuDto);

        }

        if (CollectionUtils.isEmpty(importFailedList)){
            result.setSuccess(true);
            result.setImportRtuList(rtuDmoList);
            result.setContentList(importFailedList);
            result.setMessage("本次一共导入"+ dataSize + "条，其中失败了" + count +"条");

        }else {
            result.fail();
            result.setImportRtuList(rtuDmoList);
            result.setContentList(importFailedList);
            result.setMessage("本次一共导入"+ dataSize + "条，其中失败了" + count +"条");
        }

        return result;
    }

    @Override
    public Result addRtuInfo(RtuDmo con) {
        Result result = new Result();

        RtuDmo addRtuDmo = new RtuDmo();

        RtuDmo existRtuMachineCodeDmo = new RtuDmo();
        existRtuMachineCodeDmo.setMachineCode(con.getMachineCode());
        //查询machineCode是否存在
        RtuDmo existRtuMachineCode= (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existRtuMachineCodeDmo);

        if (null != existRtuMachineCode) {
            result.fail();
            result.setMessage("该网关的机器码已存在，请查看填写参数");
            logger.debug("该网关的机器码已存在，请查看填写参数");
            return result;
        }

        RtuDmo existRtuSnDmo = new RtuDmo();
        existRtuSnDmo.setSnNumber(con.getSnNumber());
        //查询sn码是否存在
        RtuDmo existRtuSn = (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existRtuSnDmo);

        if (null != existRtuSn) {
            result.fail();
            result.setMessage("该网关序列码已存在，请查看填写参数");
            logger.debug("该网关序列码已存在，请查看填写参数");
            return result;
        }


        addRtuDmo.setRtuName(con.getRtuName());
        addRtuDmo.setRtuModel(con.getRtuModel());
        addRtuDmo.setRtuTransType(con.getRtuTransType());
        addRtuDmo.setRtuAccessType(con.getRtuAccessType());
        addRtuDmo.setMachineCode(con.getMachineCode());
        addRtuDmo.setSnNumber(con.getSnNumber());
        if (StringUtils.isNotBlank(con.getCarrierOperatorEnum())) {
            addRtuDmo.setCarrierOperatorEnum(con.getCarrierOperatorEnum());
        }
        if (null != con.getIntegratorId() && StringUtils.isNotBlank(con.getIntegratorName())) {

            addRtuDmo.setIntegratorId(con.getIntegratorId());
            addRtuDmo.setIntegratorName(con.getIntegratorName());
        }

        addRtuDmo.setStatus(Constants.RtuStatus.INACTIVE);
        addRtuDmo.setCreateTime(DateUtil.getDate());
        addRtuDmo.setLastUpdateTime(DateUtil.getDate());

        int i = commonDao.insert(addRtuDmo);

        if (i<1) {

            result.fail();
            result.setMessage("该网关信息新增失败");
            logger.debug("该网关信息新增失败");
            return result;
        }

        return result;
    }

    @Override
    public Result updateRtuInfo(RtuDmo con) {
        Result result = new Result();

        RtuDmo updateRtuDmo = new RtuDmo();

        RtuDmo existRtuMachineCodeDmo = new RtuDmo();
        existRtuMachineCodeDmo.setMachineCode(con.getMachineCode());
        //查询machineCode是否存在
        RtuDmo existRtuMachineCode= (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existRtuMachineCodeDmo);


        if (null != existRtuMachineCode && con.getId().longValue() != existRtuMachineCode.getId().longValue() ) {
            result.fail();
            result.setMessage("该网关的机器码已存在，请查看填写参数");
            logger.debug("该网关的机器码已存在，请查看填写参数");
            return result;
        }

        RtuDmo existRtuSnDmo = new RtuDmo();
        existRtuSnDmo.setSnNumber(con.getSnNumber());
        //查询sn码是否存在
        RtuDmo existRtuSn = (RtuDmo) commonDao.selectOne("RtuMapper.queryRtuBySomething",existRtuSnDmo);

        if (null != existRtuSn && con.getId().longValue() != existRtuSn.getId().longValue()) {
            result.fail();
            result.setMessage("该网关序列码已存在，请查看填写参数");
            logger.debug("该网关序列码已存在，请查看填写参数");
            return result;
        }

        updateRtuDmo.setId(con.getId());
        updateRtuDmo.setRtuName(con.getRtuName());
        updateRtuDmo.setRtuModel(con.getRtuModel());
        updateRtuDmo.setRtuTransType(con.getRtuTransType());
        updateRtuDmo.setRtuAccessType(con.getRtuAccessType());
        updateRtuDmo.setMachineCode(con.getMachineCode());
        updateRtuDmo.setSnNumber(con.getSnNumber());
        if (StringUtils.isNotBlank(con.getCarrierOperatorEnum())) {
            updateRtuDmo.setCarrierOperatorEnum(con.getCarrierOperatorEnum());
        }
        if (null != con.getIntegratorId() && StringUtils.isNotBlank(con.getIntegratorName())) {

            updateRtuDmo.setIntegratorId(con.getIntegratorId());
            updateRtuDmo.setIntegratorName(con.getIntegratorName());
        }


        updateRtuDmo.setLastUpdateTime(DateUtil.getDate());

        int i = commonDao.update(updateRtuDmo);

        if (i<1) {

            result.fail();
            result.setMessage("该网关信息修改失败");
            logger.debug("该网关信息修改失败");
            return result;
        }

        return result;
    }



    private String getDataString(LinkedHashMap<String, Object> data, String key) {

        Object obj = data.get(key);

        if (obj == null) {
            return null;
        } else {
            return obj.toString();
        }
    }

    private boolean checkVehicleParam(String rtuName,String rtuModel,String rtuTransType,String rtuAccessType,String rtuMachineCode,String snNumber) {

        // 检查各字段合法性,是否必填
        if (StringUtils.isBlank(rtuName) || StringUtils.isBlank(rtuModel) || StringUtils.isBlank(rtuTransType)
                || StringUtils.isBlank(rtuAccessType) || StringUtils.isBlank(rtuMachineCode) || StringUtils.isBlank(snNumber)) {
            return false;
        }

        //    sn码 32位数字字母组合
        String machineCodePattern ="^[0-9A-Za-z]{32}$";

        if(!match(machineCodePattern,rtuMachineCode)){
            return false;
        }
        //
       String snPattern = "^[0-9]{20}$";

        if (!match(snPattern,snNumber)) {
            return false;
        }

        return true;
    }


    /**
     * @param regex
     * 正则表达式字符串
     * @param str
     * 要匹配的字符串
     * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
     */
    private static boolean match(String regex, String str) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }


}
