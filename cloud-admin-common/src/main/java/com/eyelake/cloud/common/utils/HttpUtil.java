package com.eyelake.cloud.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Http工具类
 * Created by wunder on 16/9/15 12:55.
 */
public class HttpUtil {

    // 连接超时时间
    private static final int CONNECTION_TIMEOUT = 3000;
    //读取超时时间
    private static final int READ_TIMEOUT = 5000;
    // 参数编码
    private static final String ENCODE_CHARSET = "utf-8";

    /**
     * 发送HTTP_GET请求
     *
     * @param httpUrl 请求地址
     * @return String
     */
    public static String getRequest(String httpUrl) {

        StringBuilder resultData = new StringBuilder();

        URL url = null;
        try {

            url = new URL(httpUrl);

        } catch (MalformedURLException e) {

            e.printStackTrace();

        }

        HttpURLConnection urlConn = null;

        InputStreamReader in = null;

        BufferedReader buffer = null;

        String inputLine = null;

        if (url != null) {
            try {
                urlConn = (HttpURLConnection) url.openConnection();
                urlConn.setRequestMethod("GET");
                urlConn.setConnectTimeout(CONNECTION_TIMEOUT);
                in = new InputStreamReader(urlConn.getInputStream(), HttpUtil.ENCODE_CHARSET);
                buffer = new BufferedReader(in);
                if (urlConn.getResponseCode() == 200) {
                    while ((inputLine = buffer.readLine()) != null) {
                        resultData.append(inputLine);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (buffer != null) {
                        buffer.close();
                    }

                    if (in != null) {
                        in.close();
                    }

                    if (urlConn != null) {
                        urlConn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultData.toString();
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     * @throws Exception
     */
    public static String doPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setUseCaches(false);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(READ_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);

            if (param != null && !param.trim().equals("")) {
                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(conn.getOutputStream());
                // 发送请求参数
                out.print(param);
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

}
