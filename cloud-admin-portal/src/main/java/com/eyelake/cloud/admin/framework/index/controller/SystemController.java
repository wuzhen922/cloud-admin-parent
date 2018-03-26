package com.eyelake.cloud.admin.framework.index.controller;


import com.eyelake.cloud.admin.assist.dto.account.FlowWarningDto;
import com.eyelake.cloud.admin.assist.dto.admin.IntegratorRankingDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorByRtuWarningStatusDto;
import com.eyelake.cloud.admin.assist.dto.admin.RtuRankingDto;
import com.eyelake.cloud.admin.assist.result.FlowWarningResult;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.framework.common.vo.PageVo;
import com.eyelake.cloud.admin.framework.index.form.QueryFlowWarningValueForm;
import com.eyelake.cloud.admin.system.service.intf.FlowWarningService;
import com.eyelake.cloud.admin.system.service.intf.SystemIndexService;
import com.yjh.framework.page.Page;
import com.yjh.framework.web.result.JsonResult;
import org.apache.commons.collections.CollectionUtils;
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

/**
 * 云平台首页管理设计
 * 
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */
@Controller
@RequestMapping("/system")
public class SystemController extends BaseController {

    @Autowired
    private SystemIndexService systemIndexService;
    @Autowired
    private FlowWarningService flowWarningService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return integratorView("integrator_info");
    }

    @RequestMapping(value = "/queryFlowWarningValueListByPage", method = RequestMethod.POST)
    public String queryFlowListByPage(HttpServletRequest request, HttpServletResponse response, Model model, QueryFlowWarningValueForm form) {
        FlowWarningDto con = new FlowWarningDto();

        Page page = new Page();

        if (StringUtils.isNotBlank(form.getPageNum())) {
            page.setCurrentPage(Integer.parseInt(form.getPageNum()));
        }
        if (StringUtils.isNotBlank(form.getPageSize())) {
            page.setPageSize(Integer.parseInt(form.getPageSize()));
        }

        List<FlowWarningDto> flowWarningDtoList = flowWarningService.queryFlowListByPage(con, page);

        model.addAttribute("flowList", flowWarningDtoList);
        model.addAttribute("page", PageVo.createPageVo(request, page));

        return mainView("main_table");

    }

    @RequestMapping(value = "/countSystemTotalTraffic", method = RequestMethod.POST)
    public @ResponseBody
    JsonResult countSystemTotalTraffic(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        //系统总流量，单位为M
        Long totalTraffic = systemIndexService.countSystemTotalTraffic();

        //单位换算为G,转为四位小数显示
        String resultTraffic = String.format("%.4f", totalTraffic.doubleValue() / 1024);

        if (StringUtils.isEmpty(resultTraffic)) {
            jsonResult.setSuccess(false);
            jsonResult.fail("查询系统总流量失败！");
        }
        jsonResult.setData(resultTraffic);
        jsonResult.setSuccess(true);
        return jsonResult;
    }

    @RequestMapping(value = "/rankRtuByMonthTraffic", method = RequestMethod.POST)
    public @ResponseBody
    JsonResult rankRtuByMonthTraffic(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        List<RtuRankingDto> rtuList = systemIndexService.rankRtuByMonthTraffic();

        if (CollectionUtils.isEmpty(rtuList)) {
            jsonResult.setSuccess(false);
            jsonResult.fail("查询网关月流量错误！");
        }

        jsonResult.setData(rtuList);
        jsonResult.setSuccess(true);

        return jsonResult;
    }

    @RequestMapping(value = "/rankIntegratorByMonthTraffic",method = RequestMethod.POST)
    public @ResponseBody
    JsonResult rankIntegratorByMonthTraffic(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        List<IntegratorRankingDto> integratorList = systemIndexService.rankIntegratorByMonthTraffic();

        if (CollectionUtils.isEmpty(integratorList)) {
            jsonResult.setSuccess(false);
            jsonResult.fail("查询集成商企业月流量错误！");
        }

        jsonResult.setData(integratorList);
        jsonResult.setSuccess(true);

        return jsonResult;
    }

    @RequestMapping(value = "/selectIntegratorByRtuWarningStatus",method = RequestMethod.POST)
    public @ResponseBody
    JsonResult selectIntegratorByRtuWarningStatus(HttpServletRequest request, Model model) {

        JsonResult jsonResult = new JsonResult();

        List<QueryIntegratorByRtuWarningStatusDto> integratorList = systemIndexService.selectIntegratorByRtuWarningStatus();

        if (CollectionUtils.isEmpty(integratorList)) {
            jsonResult.setSuccess(false);
            jsonResult.fail("查询集成商企业下网关预警状态错误！");

        }

        jsonResult.setSuccess(true);
        jsonResult.setData(integratorList);

        return jsonResult;
    }
}
