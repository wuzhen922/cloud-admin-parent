package com.eyelake.cloud.admin.assist.result;

import com.eyelake.cloud.admin.assist.dto.account.FlowWarningDto;
import com.eyelake.framework.lang.Result;
import com.eyelake.framework.page.Page;

import java.util.List;

/**
 * Created by wtw on 2018/1/23.
 */
public class FlowWarningResult extends Result {
    private static final long serialVersionUID = 6217332314823531745L;
    /**
     * 分页信息
     */
    private Page page;
    private List<FlowWarningDto> list ;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<FlowWarningDto> getList() {
        return list;
    }

    public void setList(List<FlowWarningDto> list) {
        this.list = list;
    }
}
