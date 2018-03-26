package com.eyelake.cloud.admin.framework.account.controller;


import com.eyelake.cloud.admin.assist.dmo.admin.RtuWarningDmo;
import com.eyelake.cloud.admin.assist.dto.account.FlowWarningDto;
import com.eyelake.cloud.admin.framework.account.form.QueryFlowWarningForm;
import com.eyelake.cloud.admin.framework.account.form.UpdateWarningForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.system.service.intf.FlowWarningService;
import com.eyelake.framework.lang.Result;
import com.yjh.framework.page.Page;
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
import java.util.List;

@Controller
@RequestMapping(value="/flow/flowWarning")
public class FlowWarningController extends BaseController {
    @Autowired
    private FlowWarningService flowWarningService;


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return flowView("flow_warning_info");
    }


    @RequestMapping(value = "/queryFlowListByPage", method = RequestMethod.POST)
    public String queryFlowListByPage(HttpServletRequest request, HttpServletResponse response, Model model, QueryFlowWarningForm form) {

        FlowWarningDto con = new FlowWarningDto();

        if (StringUtils.isNotBlank(form.getRtuName())) {
            con.setRtuName(form.getRtuName());
        }

        Page page = new Page();

        if (StringUtils.isNotBlank(form.getPageNum())) {
            page.setCurrentPage(Integer.valueOf(form.getPageNum()));
        }
        if (StringUtils.isNotBlank(form.getPageSize())) {
            page.setPageSize(Integer.valueOf(form.getPageSize()));
        }

        List<FlowWarningDto> flowWarningList = flowWarningService.queryFlowListByPage(con, page);

        model.addAttribute("flowList", flowWarningList);
        model.addAttribute("page", PageVo.createPageVo(request, page));

        return flowView("flow_warning_table");

    }


    @RequestMapping(value = "/settingFlowWarning", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult settingFlowWarning(HttpServletRequest request, HttpServletResponse response, Model model, UpdateWarningForm form) {
        JsonResult jsonResult = new JsonResult();
        RtuWarningDmo con = new RtuWarningDmo();

        if (StringUtils.isBlank(form.getSnNumber())) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        con.setSnNumber(form.getSnNumber());
        con.setWarningNum(form.getWarningNum());

        Result result = flowWarningService.settingFlowWarning(con);

        if (result.isSuccess()) {
            jsonResult.setSuccess(true);
        } else {
            jsonResult.fail(result.getMessage());
        }

        return jsonResult;
    }




}
