package com.eyelake.cloud.admin.framework.integrator.controller;

import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorDto;
import com.eyelake.cloud.admin.assist.result.DownloadRtuResult;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.framework.integrator.form.ChangeIntegratorStatusForm;
import com.eyelake.cloud.admin.framework.integrator.form.ExportForm;
import com.eyelake.cloud.admin.framework.integrator.form.QueryIntegratorForm;
import com.eyelake.cloud.admin.system.service.intf.IntegratorService;
import com.eyelake.cloud.common.constants.Constants;
import com.eyelake.cloud.common.dto.UserSession;
import com.eyelake.cloud.common.utils.CSVUtil;
import com.eyelake.cloud.common.utils.SessionUtil;
import com.eyelake.framework.lang.AppException;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import com.yjh.framework.utils.DateUtil;
import com.yjh.framework.web.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 集成商信息管理
 *
 * @author  j_cong
 * @date    2017/12/14
 * @version V1.0
 */
@Controller
@RequestMapping("/integrator")
public class IntegratorController extends BaseController {

    @Autowired
    private IntegratorService integratorService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return integratorView("integrator_info");
    }


    @RequestMapping(value = "/queryIntegratorList")
    public String queryIntegratorList(HttpServletRequest request, Model model,
                                      QueryIntegratorForm queryIntegratorForm) {

        String pageNum = queryIntegratorForm.getPageNum();
        String pageSize = queryIntegratorForm.getPageSize();

        Page page = new Page();

        if (!StringUtils.isBlank(pageNum)) {
            page.setCurrentPage(Integer.valueOf(pageNum));
        }

        if (!StringUtils.isBlank(pageSize)) {
            page.setPageSize(Integer.valueOf(pageSize));
        }

        QueryIntegratorDto con = new QueryIntegratorDto();

        if (!StringUtils.isBlank(queryIntegratorForm.getUserName())) {

            con.setUserName(queryIntegratorForm.getUserName());
        }

        if (!StringUtils.isBlank(queryIntegratorForm.getCompany())) {

            con.setCompany(queryIntegratorForm.getCompany());
        }

        if (!StringUtils.isBlank(queryIntegratorForm.getPhone())) {

            con.setPhone(queryIntegratorForm.getPhone());
        }

        List<QueryIntegratorDto> integratorList = integratorService.selectListByPage(con,
                page);

        model.addAttribute("integratorList", integratorList);
        model.addAttribute("page", PageVo.createPageVo(request, page));

        return integratorView("integrator_table");
    }

    @RequestMapping(value = "/changeIntegratorStatus")
    public @ResponseBody
    JsonResult changeIntegratorStatus(ChangeIntegratorStatusForm changeIntegratorStatusForm,
                                      HttpServletRequest request, Model model) {

        JsonResult jsonResult = validateDeleteForm(changeIntegratorStatusForm);

        if (!jsonResult.getSuccess()) {
            return jsonResult;
        }

        IntegratorDmo integratorDmo = new IntegratorDmo();

        integratorDmo.setStatus(changeIntegratorStatusForm.getStatus());
        integratorDmo.setId(Long.valueOf(changeIntegratorStatusForm.getId()));
        integratorDmo.setRemark(changeIntegratorStatusForm.getDisabledReason());

        //根据获取获取到的集成商状态，进入禁用或者启用操作
        Result integratorResult = integratorService.changeIntegratorStatus(integratorDmo);

        if (integratorResult.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(integratorResult.getMessage());
        }

        return jsonResult;

    }

    private JsonResult validateDeleteForm(ChangeIntegratorStatusForm changeIntegratorStatusForm) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isNotBlank(changeIntegratorStatusForm.getId()) && StringUtils.isNotBlank(changeIntegratorStatusForm.getStatus())) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
        }

        return jsonResult;
    }

    @RequestMapping(value = "/deleteIntegrator")
    public @ResponseBody
    JsonResult deleteIntegrator(ChangeIntegratorStatusForm changeIntegratorStatusForm, HttpServletRequest request,
                              Model model) {

        JsonResult jsonResult = new JsonResult();

        if (StringUtils.isBlank(changeIntegratorStatusForm.getId())) {

            jsonResult.fail("参数异常");
            return jsonResult;
        }
        IntegratorDmo integratorDmo = new IntegratorDmo();
        integratorDmo.setStatus(Constants.IntegratorStatus.DELETE);
        integratorDmo.setId(Long.parseLong(changeIntegratorStatusForm.getId()));

        Result integratorResult = integratorService.deleteIntegrator(integratorDmo);

        if (integratorResult.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(integratorResult.getMessage());
        }

        return jsonResult;
    }

    //

    /**
     * 查询集成商企业,用于下拉框选择
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryIntegratorCompany")
    public @ResponseBody
    JsonResult queryIntegratorCompany(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        IntegratorDmo integratorDmo = new IntegratorDmo();

        List<IntegratorDmo> integratorDmoList = integratorService.selectList(integratorDmo);

        jsonResult.setSuccess(true);
        jsonResult.setData(integratorDmoList);

        return jsonResult;

    }

    @RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response, ExportForm form) {


        DownloadRtuResult downloadRtuResult = new DownloadRtuResult();


        IntegratorDmo integratorDmo = new IntegratorDmo();
        integratorDmo.setId(Long.parseLong(form.getIntegratorId()));


        downloadRtuResult = integratorService.downloadRtuInfo(integratorDmo);
        //业主企业名称，用于导出时作为文件名
        String integratorName = "";

        if (StringUtils.isNotBlank(form.getIntegratorName())) {

            integratorName = form.getIntegratorName();
        }

        // 获取导出数据
        if (downloadRtuResult.isSuccess()) {
            // 数据安全加密
            List<LinkedHashMap<String, String>> contentList = downloadRtuResult.getContentList();

            String date = com.eyelake.framework.utils.DateUtil.format(com.eyelake.framework.utils.DateUtil.getDate(), com.eyelake.framework.utils.DateUtil.yyyyYearMMmonthddDay);
            CSVUtil.exportCSVFile(response, downloadRtuResult.getHeaders(), contentList, date + integratorName+"网关设备导出信息表");
        }

    }

}
