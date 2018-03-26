package com.eyelake.cloud.admin.assist.result;


import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.framework.lang.Result;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author CuiXw
 * @Date 2018/1/16
 */
public class ImportRtusResult extends Result {

    private static final long serialVersionUID = 412293223986395092L;
    private String serverFileName;
    private String sourceFileName;
    private List<RtuDmo> importRtuList;
    String[] headers;
    List<LinkedHashMap<String, Object>> contentList;

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public List<RtuDmo> getImportRtuList() {
        return importRtuList;
    }

    public void setImportRtuList(List<RtuDmo> importRtuList) {
        this.importRtuList = importRtuList;
    }

    public String[] getHeaders() {
        return headers;
    }

    public void setHeaders(String[] headers) {
        this.headers = headers;
    }

    public List<LinkedHashMap<String, Object>> getContentList() {
        return contentList;
    }

    public void setContentList(List<LinkedHashMap<String, Object>> contentList) {
        this.contentList = contentList;
    }
}
