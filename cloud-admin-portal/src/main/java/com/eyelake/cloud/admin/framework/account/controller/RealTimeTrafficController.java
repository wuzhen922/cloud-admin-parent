package com.eyelake.cloud.admin.framework.account.controller;

import com.eyelake.cloud.admin.assist.dto.admin.PackageUsageDto;
import com.eyelake.cloud.admin.assist.dto.admin.RealTimeTrafficDto;
import com.eyelake.cloud.admin.assist.result.RtuTreeResult;
import com.eyelake.cloud.admin.assist.result.RealTimeTrafficResult;
import com.eyelake.cloud.admin.framework.account.form.RealTimeTrafficForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.system.service.intf.QueryRtuTreeService;
import com.eyelake.cloud.admin.system.service.intf.RealTimeTrafficService;
import com.eyelake.framework.web.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author:xudajan
 * @date:2018/1/22
 */
@Controller
@RequestMapping(value = "/flow/realFlow")
public class RealTimeTrafficController extends BaseController {

    @Autowired
    private QueryRtuTreeService queryRtuTreeService;

    @Autowired
    private RealTimeTrafficService realTimeTrafficService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return flowView("flow_real_info");
    }

    /**
     * 查询树
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/queryTreeList", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult queryTreeList(HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        RtuTreeResult result = queryRtuTreeService.queryRtuTree();

        if (!result.isSuccess()) {
            result.setSuccess(false);
            jsonResult.setData(result);
            return jsonResult;
        }
        jsonResult.setData(result);

        return jsonResult;
    }

    /**
     * rtu实时流量
     */
    @RequestMapping(value = "/rtuRealTimeTraffic", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult rtuRealTimeTraffic(HttpServletRequest request, HttpServletResponse response, Model model, RealTimeTrafficForm form) {

        JsonResult jsonResult = new JsonResult(true);
        RealTimeTrafficDto realTimeTrafficDto = new RealTimeTrafficDto();
        if (null == form || StringUtils.isBlank(form.getSnNumber())) {
            jsonResult.fail("请求参数缺失");
            return jsonResult;
        }
        realTimeTrafficDto.setSnNumber(form.getSnNumber());
        RealTimeTrafficResult realTimeTrafficResult = realTimeTrafficService.rtuRealTimeTraffic(realTimeTrafficDto);
        if (!realTimeTrafficResult.isSuccess()) {
            jsonResult.fail(realTimeTrafficResult.getMessage());
            return jsonResult;
        }
        realTimeTrafficResult.getRealTimeTrafficDto().setRestDayOfMonth(String.valueOf(getRestDayOfMonth()));

        jsonResult.setData(realTimeTrafficResult.getRealTimeTrafficDto());
        return jsonResult;
    }
    /**
     * 集成商实时流量
     */
    @RequestMapping(value = "/integratorRealTimeTraffic", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult integratorRealTimeTraffic(HttpServletRequest request, HttpServletResponse response, Model model, RealTimeTrafficForm form) {

        JsonResult jsonResult = new JsonResult(true);
        RealTimeTrafficDto realTimeTrafficDto = new RealTimeTrafficDto();
        if (null == form || StringUtils.isBlank(form.getId())||!form.getId().startsWith("I")) {
            jsonResult.fail("请求参数缺失");
            return jsonResult;
        }
        realTimeTrafficDto.setId(form.getId().replace("I",""));
        RealTimeTrafficResult realTimeTrafficResult = realTimeTrafficService.integratorRealTimeTraffic(realTimeTrafficDto);
        if (!realTimeTrafficResult.isSuccess()) {
            jsonResult.fail(realTimeTrafficResult.getMessage());
            return jsonResult;
        }
        realTimeTrafficResult.getRealTimeTrafficDto().setRestDayOfMonth(String.valueOf(getRestDayOfMonth()));

        jsonResult.setData(realTimeTrafficResult.getRealTimeTrafficDto());
        return jsonResult;
    }
    /**
     * 云平台实时流量
     */
    @RequestMapping(value = "/systemRealTimeTraffic", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult systemRealTimeTraffic(HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult(true);
        RealTimeTrafficResult realTimeTrafficResult = realTimeTrafficService.systemRealTimeTraffic();
        if (!realTimeTrafficResult.isSuccess()) {
            jsonResult.fail(realTimeTrafficResult.getMessage());
            return jsonResult;
        }
        realTimeTrafficResult.getRealTimeTrafficDto().setRestDayOfMonth(String.valueOf(getRestDayOfMonth()));

        jsonResult.setData(realTimeTrafficResult.getRealTimeTrafficDto());
        return jsonResult;
    }

    private int getRestDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-calendar.get(Calendar.DAY_OF_MONTH);
    }

}
