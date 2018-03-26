package com.eyelake.cloud.admin.assist.result;

import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.yjh.framework.lang.Result;

/**
 * 调用接口查询网关月使用流量和套餐信息返回结果
 *
 * @author  j_cong
 * @date    2018/01/25
 * @version V1.0
 */
public class QueryRtuMonthTrafficAndPackResult extends Result {

    QueryRtuDto rtudto = new QueryRtuDto();


    public QueryRtuDto getRtudto() {
        return rtudto;
    }

    public void setRtudto(QueryRtuDto rtudto) {
        this.rtudto = rtudto;
    }
}
