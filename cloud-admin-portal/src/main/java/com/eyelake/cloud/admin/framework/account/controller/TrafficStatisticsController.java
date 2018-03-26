package com.eyelake.cloud.admin.framework.account.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.eyelake.cloud.admin.assist.result.MonthStatisticsResult;
import com.eyelake.cloud.admin.assist.result.YearStatisticsResult;
import com.eyelake.cloud.admin.framework.account.form.IntegratorStatisticsForm;
import com.eyelake.cloud.admin.framework.account.form.RtuStatisticsForm;
import com.eyelake.cloud.admin.framework.account.form.SystemStatisticsForm;
import com.eyelake.cloud.admin.framework.common.controller.BaseController;
import com.eyelake.cloud.admin.system.service.intf.TrafficStatisticsService;
import com.eyelake.cloud.common.constants.CommonConst;
import com.eyelake.framework.utils.DateUtil;
import com.eyelake.framework.web.result.JsonResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.TimeZone;

@Controller
@RequestMapping(value = "/flow/statistics")
public class TrafficStatisticsController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrafficStatisticsController.class);


    @Autowired
    private TrafficStatisticsService trafficStatisticsService;

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String main(HttpServletRequest request, Model model) {

        return flowView("traffic_statistics_info");
    }

    /**
     * 查询台账树
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
   /* @RequestMapping(value = "/queryTreeList", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult queryTreeList(HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        UserSession userSession = SessionUtil.getUserSession(request);
        String integratorId = userSession.getIntegratorId();

        AccountTreeResult result = trafficStatisticsExtService.queryAccountTree(integratorId);

        if (!result.isSuccess()) {

            result.setSuccess(false);
            jsonResult.setData(result);
            return jsonResult;
        }

        jsonResult.setData(result);

        return jsonResult;
    }
*/


    /*
    *  云平台年统计(初始化页面，云平台上一年统计)
    */
    @RequestMapping(value = "/systemYear", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult systemYear(SystemStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {

        JsonResult jsonResult = new JsonResult();

        //要统计的年份
        String countYear = null;
       // 云平台没有id，统计时间为空 表示初始化页面;
        if (StringUtils.isBlank(form.getAccountDate())) {

            int currentYear = currentYear();
            countYear = String.valueOf(currentYear - 1);
        }

        if ( StringUtils.isNotBlank(form.getAccountDate())) {
            int currentYear = currentYear();
            int currentMonth = currentMonth();

            if (CommonConst.ONE == currentMonth && form.getAccountDate().equals(String.valueOf(currentYear))) {
                jsonResult.fail("当前为一月份，统计当前年的年数据失败！");
                return jsonResult;
            }

            countYear = form.getAccountDate();
        }

        YearStatisticsResult result = trafficStatisticsService.systemYearStatistics(countYear);
        jsonResult.setSuccess(true);
        jsonResult.setData(result);

        return jsonResult;
    }

     /*
      *  云平台月统计
      */
    @RequestMapping(value = "/systemMonth", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult systemMonth(SystemStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult jsonResult = new JsonResult();

        String countYearMonth ;

        if (StringUtils.isBlank(form.getAccountDate())) {
            //默认取当前时间的上个月
            int currentYear = currentYear();
            int currentMonth = currentMonth();
            String date = DateUtil.getLastMonthDate(currentYear, currentMonth);
            String countYear = date.substring(0, 4);
            String countMonth = date.substring(4, 6);
            StringBuffer stringBuffer = new StringBuffer();
            countYearMonth = stringBuffer.append(countYear).append("-").append(countMonth).toString();

        } else {
            countYearMonth = form.getAccountDate();
        }


        MonthStatisticsResult result = trafficStatisticsService.systemMonthStatistics(countYearMonth);
        jsonResult.setSuccess(true);
        jsonResult.setData(result);

        return jsonResult;
    }

    /*
    *  集成商年统计
    */
    @RequestMapping(value = "/integratorYear", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult integratorYear(IntegratorStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult jsonResult = new JsonResult();

        //要统计的年份
        String countYear = null;

        String integratorId = form.getIntegratorId();

        if (StringUtils.isBlank(integratorId)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }
        String newIntegratorId = integratorId.substring(1);

        if (StringUtils.isBlank(form.getAccountDate())) {

            int currentYear = currentYear();
            countYear = String.valueOf(currentYear - 1);
        } else {
            int currentYear = currentYear();
            int currentMonth = currentMonth();

            if (CommonConst.ONE == currentMonth && form.getAccountDate().equals(String.valueOf(currentYear))) {
                jsonResult.fail("当前为一月份，统计当前年的年数据失败！");
                return jsonResult;
            }

            countYear = form.getAccountDate();
        }

        YearStatisticsResult result = trafficStatisticsService.integratorYearStatistics(newIntegratorId, countYear);
        jsonResult.setSuccess(true);
        jsonResult.setData(result);

        return jsonResult;
    }

    /*
   *  集成商月统计
   */
    @RequestMapping(value = "/integratorMonth", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult integratorMonth(IntegratorStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult jsonResult = new JsonResult();

        String countYearMonth = null;

        String integratorId = form.getIntegratorId();

        if (StringUtils.isBlank(integratorId)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }
        String newIntegratorId = integratorId.substring(1);

        if (StringUtils.isBlank(form.getAccountDate())) {
            //默认取当前时间的上个月
            int currentYear = currentYear();
            int currentMonth = currentMonth();
            String date = DateUtil.getLastMonthDate(currentYear, currentMonth);
            String countYear = date.substring(0, 4);
            String countMonth = date.substring(4, 6);
            StringBuffer stringBuffer = new StringBuffer();
            countYearMonth = stringBuffer.append(countYear).append("-").append(countMonth).toString();

        } else {
            countYearMonth = form.getAccountDate();
        }

        MonthStatisticsResult result = trafficStatisticsService.integratorMonthStatistics(newIntegratorId, countYearMonth);
        jsonResult.setSuccess(true);
        jsonResult.setData(result);

        return jsonResult;
    }


    /*
    *  rtu年统计
    */
    @RequestMapping(value = "/rtuYear", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult rtuYear(RtuStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult jsonResult = new JsonResult();

        //要统计的年份
        String countYear = null;

        String snNumber = form.getSnNumber();

        if (StringUtils.isBlank(snNumber)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        if (StringUtils.isBlank(form.getAccountDate())) {

            int currentYear = currentYear();
            countYear = String.valueOf(currentYear - 1);
        } else {
            int currentYear = currentYear();
            int currentMonth = currentMonth();

            if (CommonConst.ONE == currentMonth && form.getAccountDate().equals(String.valueOf(currentYear))) {
                jsonResult.fail("当前为一月份，统计当前年的年数据失败！");
                return jsonResult;
            }

            countYear = form.getAccountDate();
        }

        YearStatisticsResult result = trafficStatisticsService.rtuYearStatistics(snNumber, countYear);
        jsonResult.setSuccess(true);
        jsonResult.setData(result);

        return jsonResult;
    }


    /*
     *  rtu月统计
     */
    @RequestMapping(value = "/rtuMonth", method = RequestMethod.POST)
    public
    @ResponseBody
    JsonResult integratorMonth(RtuStatisticsForm form, HttpServletRequest request, HttpServletResponse response, Model model) {
        JsonResult jsonResult = new JsonResult();

        String countYearMonth = null;

        String snNumber = form.getSnNumber();

        if (StringUtils.isBlank(snNumber)) {
            jsonResult.fail("参数异常");
            return jsonResult;
        }

        if (StringUtils.isBlank(form.getAccountDate())) {
            //默认取当前时间的上个月
            int currentYear = currentYear();
            int currentMonth = currentMonth();
            String date = DateUtil.getLastMonthDate(currentYear, currentMonth);
            String countYear = date.substring(0, 4);
            String countMonth = date.substring(4, 6);
            StringBuffer stringBuffer = new StringBuffer();
            countYearMonth = stringBuffer.append(countYear).append("-").append(countMonth).toString();

        } else {
            countYearMonth = form.getAccountDate();
        }


        MonthStatisticsResult result = trafficStatisticsService.rtuMonthStatistics( snNumber, countYearMonth);

        String resultString = JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);

        JSONObject resultObject = JSONObject.parseObject(resultString);


        jsonResult.setSuccess(true);
        jsonResult.setData(resultObject);

        return jsonResult;
    }

    //获取当前年
    public int currentYear() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int currentYear = c.get(Calendar.YEAR);
        return currentYear;
    }

    //获取当前月份
    public int currentMonth() {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间
        int currentMonth = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        return currentMonth;
    }
}


