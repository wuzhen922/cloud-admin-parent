package com.eyelake.cloud.common.utils;

import com.yjh.framework.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.StringEscapeUtils.unescapeJava;

/**
 * 群英云考勤工具类
 * Created by wunder on 2016/11/7 14:09.
 */
public class AttendSyncUtils {

    private static final Logger LOG = LoggerFactory.getLogger(AttendSyncUtils.class);

    /**
     * 分页获取公司打卡记录
     *
     * @param page
     * @param account
     * @param secret
     * @param startDate
     * @param endDate
     * @return
     */
    public static String getRecordLogByPage(int page, String account, String secret, Date startDate, Date endDate) {

        String param = "";

        param = buildParam(param, "account", account);
        String nowDateStr = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMddHHmmss);
        param = buildParam(param, "requesttime", nowDateStr);

        param = buildParam(param, "start", DateUtil.format(startDate, DateUtil.yyyy_MM_dd));
        param = buildParam(param, "end", DateUtil.format(endDate, DateUtil.yyyy_MM_dd));
        param = buildParam(param, "page", String.valueOf(page));

        String signData = account + DateUtil.format(endDate, DateUtil.yyyy_MM_dd) + String.valueOf(page) + nowDateStr + DateUtil.format(startDate, DateUtil.yyyy_MM_dd) + secret;

        param = buildParam(param, "sign", EncryptUtil.md5Encrypt(signData));

        return sendGet("http://kq.qycn.com/index.php/Api/Api/recordlog", param);
    }

    /**
     * 获取员工详细
     *
     * @param userAccount
     * @param account
     * @param secret
     * @return
     */
    public static String getStaffDetail(String userAccount, String account, String secret) {

        String param = "";

        param = buildParam(param, "account", account);
        String nowDateStr = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMddHHmmss);
        param = buildParam(param, "requesttime", nowDateStr);

        param = buildParam(param, "useraccount", userAccount);

        String signData = account + nowDateStr + userAccount + secret;

        param = buildParam(param, "sign", EncryptUtil.md5Encrypt(signData));

        return sendGet("http://kq.qycn.com/index.php/Api/Api/getUserDetail", param);

    }

    /**
     * 添加员工
     *
     * @param realname 姓名
     * @param password 密码
     * @param mobile   手机号码
     * @param email    邮箱
     * @param card     员工卡号（刷卡卡号）
     * @param deptid   部门id
     * @param sex      性别:1男，2女
     * @param sn       要同步设备的SN，多个用英文逗号分隔
     * @return
     */
    public static String addEmployee(String realname, String password, String mobile, String email, String card, String deptid, String sex, String sn, String account, String secret) {

        String param = "";

        param = buildParam(param, "account", account);
        String nowDateStr = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMddHHmmss);
        param = buildParam(param, "requesttime", nowDateStr);

        param = buildParam(param, "realname", realname);
        param = buildParam(param, "password", password);
        param = buildParam(param, "mobile", mobile);
        param = buildParam(param, "email", email);
        param = buildParam(param, "card", card);
        param = buildParam(param, "deptid", deptid);
        param = buildParam(param, "sex", sex);
        param = buildParam(param, "sn", sn);

        String signData = account + card + deptid + email + mobile + password + realname + nowDateStr + sex + sn + secret;

        param = buildParam(param, "sign", EncryptUtil.md5Encrypt(signData));

        return sendGet("http://kq.qycn.com/index.php/Api/Api/addEmployee", param);

    }

    /**
     * 更新员工信息
     *
     * @param useraccount cc号
     * @param realname    真实姓名
     * @param password    密码
     * @param mobile      手机号码
     * @param email       邮箱
     * @param card        员工卡号（刷卡卡号）
     * @param deptid      部门id
     * @param sex         性别:1男，2女
     * @param sn          要同步设备的SN，多个用英文逗号分隔
     * @param account
     * @param secret
     * @return
     */
    public static String updateEmployee(String useraccount, String realname, String password, String mobile, String email, String card, String deptid, String sex, String sn, String account, String secret) {

        String param = "";

        param = buildParam(param, "account", account);
        String nowDateStr = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMddHHmmss);
        param = buildParam(param, "requesttime", nowDateStr);

        param = buildParam(param, "useraccount", useraccount);
        param = buildParam(param, "realname", realname);
        param = buildParam(param, "password", password);
        param = buildParam(param, "mobile", mobile);
        param = buildParam(param, "email", email);
        param = buildParam(param, "card", card);
        param = buildParam(param, "deptid", deptid);
        param = buildParam(param, "sex", sex);
        param = buildParam(param, "sn", sn);

        String signData = account + card + deptid + email + mobile + password + realname + nowDateStr + sex + sn + useraccount + secret;

        param = buildParam(param, "sign", EncryptUtil.md5Encrypt(signData));

        return sendGet("http://kq.qycn.com/index.php/Api/Api/updateEmployee", param);

    }

    /**
     * 同步员工到设备
     *
     * @param useraccount
     * @param sn
     * @param account
     * @param secret
     * @return
     */
    public static String syncEmployee(String useraccount, String sn, String account, String secret) {

        String param = "";

        param = buildParam(param, "account", account);
        String nowDateStr = DateUtil.format(DateUtil.getDate(), DateUtil.yyyyMMddHHmmss);
        param = buildParam(param, "requesttime", nowDateStr);

        param = buildParam(param, "useraccount", useraccount);
        param = buildParam(param, "sn", sn);

        String signData = account + nowDateStr + sn + useraccount + secret;

        param = buildParam(param, "sign", EncryptUtil.md5Encrypt(signData));

        return sendGet("http://kq.qycn.com/index.php/Api/Api/syncEmployee", param);

    }

    private static String buildParam(String param, String key, String value) {

        if (StringUtils.isBlank(param)) {
            return key + "=" + value;
        } else {
            return param + "&" + key + "=" + value;
        }
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    private static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {

//        String result = getQuestionnaireListByPage("1588824", "174da7aac00c03f78223cc6d7f223004", "123123123qweasdasd");

//        String result = addEmployee("测试人员", "abcd1234", "", "000012@1234567.com", "12345678", "", "1", "6276162300069", "174da7aac00c03f78223cc6d7f223004", "123123123qweasdasd");

//        String result = updateEmployee("1610896", "测试人员", "abcd1234", "", "000012@1234567.com", "12345678", "", "1", "6276162300069", "174da7aac00c03f78223cc6d7f223004", "123123123qweasdasd");

        String result = syncEmployee("1610896", "6276162300069", "174da7aac00c03f78223cc6d7f223004", "123123123qweasdasd");

        System.out.println(unescapeJava(result));


    }

}
