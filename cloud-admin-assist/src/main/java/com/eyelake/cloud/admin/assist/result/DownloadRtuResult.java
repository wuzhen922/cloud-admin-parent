package com.eyelake.cloud.admin.assist.result;

import com.yjh.framework.lang.Result;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author CuiXw
 * @Date 2018/1/19
 */
public class DownloadRtuResult extends Result {

    private static final long serialVersionUID = -6789638103975358765L;
    String[] headers;
    List<LinkedHashMap<String, String>> contentList;

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<LinkedHashMap<String, String>> getContentList() {
        return contentList;
    }

    public void setContentList(List<LinkedHashMap<String, String>> contentList) {
        this.contentList = contentList;
    }
}
