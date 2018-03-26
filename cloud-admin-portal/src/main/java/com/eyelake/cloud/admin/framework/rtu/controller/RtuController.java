package com.eyelake.cloud.admin.framework.rtu.controller;

import com.alibaba.fastjson.JSONArray;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.result.ImportRtusResult;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.framework.rtu.form.*;
import com.eyelake.cloud.admin.system.service.intf.RtuService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.constants.PropertiesConstants;
import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.cloud.common.utils.ExcelUtil;
import com.eyelake.cloud.common.utils.ImportFileUtil;
import com.eyelake.cloud.common.utils.PropertiesUtil;
import com.eyelake.cloud.common.utils.SessionUtil;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.web.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * RTU Controller
 *
 * @author  j_cong
 * @date    2017/12/14
 * @version V1.0
 */
@Controller
@RequestMapping("/rtu")
public class RtuController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RtuController.class);
    @Autowired
    private RtuService rtuService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return rtuView("rtu_info");
    }

    /**
     * 查询RTU信息
     * @param request
     * @param model
     * @param queryRtuForm
     * @return
     */
    @RequestMapping(value = "/queryRtuList")
    public String queryRtuList(HttpServletRequest request, Model model,
                                      QueryRtuForm queryRtuForm) {

        String pageNum = queryRtuForm.getPageNum();

        String pageSize = queryRtuForm.getPageSize();

        Page page = new Page();

        if (!StringUtils.isBlank(pageNum)) {
            page.setCurrentPage(Integer.valueOf(pageNum));
        }

        if (!StringUtils.isBlank(pageSize)) {
            page.setPageSize(Integer.valueOf(pageSize));
        }

        QueryRtuDto con = new QueryRtuDto();

        if (!StringUtils.isBlank(queryRtuForm.getRtuName())) {

            con.setRtuName(queryRtuForm.getRtuName());
        }

        if (!StringUtils.isBlank(queryRtuForm.getIntegratorId())) {

            con.setIntegratorId(Long.parseLong(queryRtuForm.getIntegratorId()));
        }

        if (!StringUtils.isBlank(queryRtuForm.getRtuTransType())) {

            con.setRtuTransType(queryRtuForm.getRtuTransType());
        }

        List<QueryRtuDto> rtuList = rtuService.selectListByPage(con, page);

        model.addAttribute("rtuList", rtuList);
        model.addAttribute("page", PageVo.createPageVo(request, page));

        return rtuView("rtu_table");
    }

    @RequestMapping(value = "/changeRtuStatus")
    public @ResponseBody
    JsonResult changeRtuStatus(ChangeRtuStatusForm changeRtuStatusForm,
                                      HttpServletRequest request, Model model) {

        JsonResult jsonResult = validateDeleteForm(changeRtuStatusForm);

        if (!jsonResult.getSuccess()) {
            return jsonResult;
        }

        RtuDmo rtuDmo = new RtuDmo();

        rtuDmo.setStatus(changeRtuStatusForm.getStatus());
        rtuDmo.setId(Long.valueOf(changeRtuStatusForm.getId()));

        //根据获取获取到的RTU状态，进入禁用或者启用操作
        Result integratorResult = rtuService.changeRtuStatus(rtuDmo);

        if (integratorResult.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(integratorResult.getMessage());
        }
        return jsonResult;
    }

    private JsonResult validateDeleteForm(ChangeRtuStatusForm changeRtuStatusForm) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(changeRtuStatusForm.getId()) && StringUtils.isNotBlank(changeRtuStatusForm.getStatus())) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    /**
     * 删除RTU，同时删除关联的传感器
     *
     * @param changeRtuStatusForm
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteRtu")
    public @ResponseBody
    JsonResult deleteRtu(ChangeRtuStatusForm changeRtuStatusForm, HttpServletRequest request,
                              Model model) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isBlank(changeRtuStatusForm.getId())) {

            jsonResult.fail("参数异常");
            return jsonResult;
        }
        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setStatus(Constants.RtuStatus.DELETE);
        rtuDmo.setId(Long.parseLong(changeRtuStatusForm.getId()));

        Result integratorResult = rtuService.deleteRtu(rtuDmo);

        if (integratorResult.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(integratorResult.getMessage());
        }

        return jsonResult;
    }

    /**
     * 批量删除
     * @param batchDeleteForm
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/batchDeleteRtu")
    public @ResponseBody
    JsonResult deleteRtu(BatchDeleteForm batchDeleteForm, HttpServletRequest request,
                         Model model) {

        JsonResult jsonResult = new JsonResult();
        if (StringUtils.isBlank(batchDeleteForm.getRtuIdList())) {

            jsonResult.fail("参数异常");
            return jsonResult;
        }

        String jsonArray = batchDeleteForm.getRtuIdList();
        List<RtuDmo> deleteRtuList = JSONArray.parseArray(jsonArray,RtuDmo.class);
        Result result = rtuService.batchDeleteRtu(deleteRtuList);
        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(result.getMessage());
        }

        return jsonResult;
    }

    /**
     * Rtu新增
     * @param addRtuInfoForm
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/addRtuInfo")
    public @ResponseBody
    JsonResult addRtuInfo(AddRtuInfoForm addRtuInfoForm, HttpServletRequest request,
                          Model model) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isBlank(addRtuInfoForm.getRtuName()) || StringUtils.isBlank(addRtuInfoForm.getRtuModel())
                || StringUtils.isBlank(addRtuInfoForm.getRtuTransType()) || StringUtils.isBlank(addRtuInfoForm.getRtuAccessType())
                || StringUtils.isBlank(addRtuInfoForm.getMachineCode()) || StringUtils.isBlank(addRtuInfoForm.getSnNumber())) {

            jsonResult.fail("参数异常");
            return jsonResult;
        }

        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setRtuName(addRtuInfoForm.getRtuName());
        rtuDmo.setRtuModel(addRtuInfoForm.getRtuModel());
        rtuDmo.setRtuTransType(addRtuInfoForm.getRtuTransType());
        rtuDmo.setRtuAccessType(addRtuInfoForm.getRtuAccessType());
        rtuDmo.setMachineCode(addRtuInfoForm.getMachineCode());
        rtuDmo.setSnNumber(addRtuInfoForm.getSnNumber());

        if (StringUtils.isNotBlank(addRtuInfoForm.getOperator())) {
            rtuDmo.setCarrierOperatorEnum(addRtuInfoForm.getOperator());
        }
        if (StringUtils.isNotBlank(addRtuInfoForm.getIntegratorId()) && StringUtils.isNotBlank(addRtuInfoForm.getIntegratorName())) {

            rtuDmo.setIntegratorId(Long.parseLong(addRtuInfoForm.getIntegratorId()));
            rtuDmo.setIntegratorName(addRtuInfoForm.getIntegratorName());
        }

        Result result = rtuService.addRtuInfo(rtuDmo);
        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(result.getMessage());
        }


        return jsonResult;
    }

    /**
     * RTU修改
     * @param updateRtuInfoForm
     * @param request
     * @param model
     * @return
     */

    @RequestMapping(value = "/updateRtuInfo")
    public @ResponseBody
    JsonResult updateRtuInfo(UpdateRtuInfoForm updateRtuInfoForm, HttpServletRequest request,
                          Model model) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isBlank(updateRtuInfoForm.getId())|| StringUtils.isBlank(updateRtuInfoForm.getRtuName()) || StringUtils.isBlank(updateRtuInfoForm.getRtuModel())
                || StringUtils.isBlank(updateRtuInfoForm.getRtuTransType()) || StringUtils.isBlank(updateRtuInfoForm.getRtuAccessType())
                || StringUtils.isBlank(updateRtuInfoForm.getMachineCode()) || StringUtils.isBlank(updateRtuInfoForm.getSnNumber())
                || !Constants.RtuStatus.INACTIVE.equals(updateRtuInfoForm.getStatus()) ) {

            jsonResult.fail("参数异常");
            return jsonResult;
        }


        RtuDmo rtuDmo = new RtuDmo();
        rtuDmo.setId(Long.parseLong(updateRtuInfoForm.getId()));
        rtuDmo.setRtuName(updateRtuInfoForm.getRtuName());
        rtuDmo.setRtuModel(updateRtuInfoForm.getRtuModel());
        rtuDmo.setRtuTransType(updateRtuInfoForm.getRtuTransType());
        rtuDmo.setRtuAccessType(updateRtuInfoForm.getRtuAccessType());
        rtuDmo.setMachineCode(updateRtuInfoForm.getMachineCode());
        rtuDmo.setSnNumber(updateRtuInfoForm.getSnNumber());
        rtuDmo.setCarrierOperatorEnum(updateRtuInfoForm.getOperator());
        if (StringUtils.isNotBlank(updateRtuInfoForm.getOperator())) {
            rtuDmo.setCarrierOperatorEnum(updateRtuInfoForm.getOperator());
        }
        if (StringUtils.isNotBlank(updateRtuInfoForm.getIntegratorId()) && StringUtils.isNotBlank(updateRtuInfoForm.getIntegratorName())) {

            rtuDmo.setIntegratorId(Long.parseLong(updateRtuInfoForm.getIntegratorId()));
            rtuDmo.setIntegratorName(updateRtuInfoForm.getIntegratorName());
        }

        Result result = rtuService.updateRtuInfo(rtuDmo);
        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(result.getMessage());
        }


        return jsonResult;
    }

    /*
     *  导出错误rtu
     */
    @RequestMapping(value = "/exportFailRtu", method = RequestMethod.GET)
    public void downloadFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserSession userSession = SessionUtil.getUserSession(request);
        String fileName = userSession.getName() + "_云平台网关导入失败信息表.xlsx";
        String downloadPath = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG, PropertiesConstants.CLOUD_FAIL_EXPORT_URL) + fileName;

        ServletOutputStream outStream = null;
        try {
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(downloadPath));
            byte[] datas = new byte[1024];
            int length = 0;

            fileName = new String(fileName.getBytes("GB2312"), "iso-8859-1");
            response.reset();
            response.setContentType("application/download;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            outStream = response.getOutputStream();
            while ((length = inputStream.read(datas)) > 0)
                outStream.write(datas, 0, length);

            outStream.flush();
        } catch (Exception e) {
            LOGGER.info("download excel failed." + e.toString());
        } finally {
            if (null != outStream) {
                try {
                    outStream.close();
                } catch (Exception e) {
                    LOGGER.info("Close excel outStream failed." + e.toString());
                }
            }
        }

    }

    /**
     * 导入rtu信息
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/importRtus", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody
    JsonResult importRtus(HttpServletRequest request, HttpServletResponse response, Model model) {

        UserSession userSession = SessionUtil.getUserSession(request);

        JsonResult jsonResult = new JsonResult();
        ImportRtusResult parseResult = new ImportRtusResult();
        String filePath = request.getParameter("filePath");
        String serverFileName = null;
        MultipartHttpServletRequest multipartHttpservletRequest = (MultipartHttpServletRequest) request;

        CommonsMultipartFile multipartFile = (CommonsMultipartFile) multipartHttpservletRequest.getFile(filePath);
        if (multipartFile.isEmpty()) {
            jsonResult.fail("读取文件错误");
            return jsonResult;
        }

        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            jsonResult.fail("读取文件流错误");
            return jsonResult;
        }

        if (StringUtils.isBlank(filePath) || filePath.length() > 300) {
            jsonResult.fail("文件路径不能为空或超过300个字符");
            return jsonResult;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        serverFileName = getFilePathName(originalFileName);
        String servrPath = getServePath(serverFileName);
        if (parseResult.isSuccess()) {
            boolean saveFile = ImportFileUtil.saveFile(inputStream, servrPath);
            if (!saveFile) {
                jsonResult.fail("保存上传文件发生异常，请重新上传或联系运维人员");
                return jsonResult;
            }
        }

        // 校验文件内容有效性
        if (parseResult.isSuccess()) {
            List<LinkedHashMap<String, Object>> data = ExcelUtil.readExcel(servrPath);
            if (null == data) {
                jsonResult.fail("解析文件异常，请重新上传或联系运维人员");
                return jsonResult;
            }
            parseResult = rtuService.importRtu(data);
        }

        //将要导出的数据为null 的换成空字符串
        List<LinkedHashMap<String,Object>> list = parseResult.getContentList();
        List<LinkedHashMap<String,Object>> newList = new ArrayList<>();



        if (!CollectionUtils.isEmpty(list)){
            for (LinkedHashMap<String,Object> failRtu : list){
                //重新创建对象，保证顺序
                LinkedHashMap<String,Object> map = new LinkedHashMap<>();

                Object rtuName = failRtu.get(Constants.RtuImport.RTU_NAME);
                Object rtuModel = failRtu.get(Constants.RtuImport.RTU_MODEL);
                Object rtuTransType = failRtu.get(Constants.RtuImport.RTU_TRANS_TYPE);
                Object rtuAccessType = failRtu.get(Constants.RtuImport.RTU_ACCESS_TYPE);
                Object snNumber = failRtu.get(Constants.RtuImport.SN_NUMBER);
                Object machineCode = failRtu.get(Constants.RtuImport.RTU_MACHINE_CODE);

                Object operatorEnum = failRtu.get(Constants.RtuImport.RTU_OPERATOR);
                Object integratorAccessCode = failRtu.get(Constants.RtuImport.INTEGRATOR_ACCESS_CODE);
                Object failReason = failRtu.get(Constants.RtuImport.FAIL_REASON);


                if (null == rtuName){
                    map.put(Constants.RtuImport.RTU_NAME,"");
                }else {
                    map.put(Constants.RtuImport.RTU_NAME,rtuName);
                }

                if (null == rtuModel){
                    map.put(Constants.RtuImport.RTU_MODEL,"");
                }else {
                    map.put(Constants.RtuImport.RTU_MODEL,rtuModel);
                }

                if (null == rtuTransType){
                    map.put(Constants.RtuImport.RTU_TRANS_TYPE,"");
                }else {
                    map.put(Constants.RtuImport.RTU_TRANS_TYPE,rtuTransType);

                }

                if (null == rtuAccessType){
                    map.put(Constants.RtuImport.RTU_ACCESS_TYPE,"");
                }else {
                    map.put(Constants.RtuImport.RTU_ACCESS_TYPE,rtuAccessType);
                }

                if (null == snNumber){
                    map.put(Constants.RtuImport.SN_NUMBER,"");
                }else {
                    map.put(Constants.RtuImport.SN_NUMBER,snNumber);
                }

                if (null == machineCode){
                    map.put(Constants.RtuImport.RTU_MACHINE_CODE,"");
                }else {
                    map.put(Constants.RtuImport.RTU_MACHINE_CODE,machineCode);
                }


                if (null == operatorEnum){
                    map.put(Constants.RtuImport.RTU_OPERATOR,"");
                }else {
                    map.put(Constants.RtuImport.RTU_OPERATOR,operatorEnum);
                }

                if (null == integratorAccessCode ){
                    map.put(Constants.RtuImport.INTEGRATOR_ACCESS_CODE,"");
                }else {
                    map.put(Constants.RtuImport.INTEGRATOR_ACCESS_CODE,integratorAccessCode);
                }

                map.put(Constants.RtuImport.FAIL_REASON, failReason);
                newList.add(map);
            }

        }

        String url = PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG, PropertiesConstants.CLOUD_FAIL_EXPORT_URL) +userSession.getName() + "_云平台网关导入失败信息表";

        if (parseResult.isSuccess()) {
            parseResult.setSourceFileName(originalFileName);
            parseResult.setServerFileName(serverFileName);

            jsonResult.success(parseResult);
            jsonResult.setMessage(parseResult.getMessage());
        } else {
            jsonResult.fail();
            jsonResult.setMessage(parseResult.getMessage());
            exportExcel(url, parseResult, newList);
     }

        return jsonResult;
    }

    /**
     * 导出excel
     * @param url
     * @param result
     * @param list
     */

    private void exportExcel (String url ,ImportRtusResult result, List<LinkedHashMap<String,Object>> list){

        ExcelUtil.writeXLSXFile(result.getHeaders(), list, url);

    }


    /**
     * 获取路径
     * @param originalFileName
     * @return
     */
    private String getFilePathName(String originalFileName) {
        originalFileName = originalFileName.substring(0, originalFileName.lastIndexOf(".xlsx"));
        String date = com.eyelake.framework.utils.DateUtil.format(com.eyelake.framework.utils.DateUtil.getDate(), com.eyelake.framework.utils.DateUtil.yyyyMMddhhmmss);
        return originalFileName + date + ".xlsx";
    }

    private String getServePath(String serverFileName) {

        return PropertiesUtil.getString(PropertiesConstants.APPLICATION_ENV_CONFIG, PropertiesConstants.CLOUD_IMPORT_RTUS_PATH) + serverFileName;
    }

}
