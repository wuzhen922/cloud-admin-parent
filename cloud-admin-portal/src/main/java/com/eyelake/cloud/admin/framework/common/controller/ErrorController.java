package com.eyelake.cloud.admin.framework.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误页面控制器
 * Created by wunder on 2016/10/14 00:21.
 */
@Controller
public class ErrorController extends BaseController {

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound(HttpServletRequest request, Model model) {

        return commonView("404");
    }

    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public String serverError(HttpServletRequest request, Model model) {

        return commonView("500");
    }

}
