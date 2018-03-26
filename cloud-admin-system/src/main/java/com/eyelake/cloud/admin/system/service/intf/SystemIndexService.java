package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dto.admin.IntegratorRankingDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorByRtuWarningStatusDto;
import com.eyelake.cloud.admin.assist.dto.admin.RtuRankingDto;

import java.util.List;
/**
 * 云平台首页管理业务接口
 *
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */

public interface SystemIndexService {

    /**
     * 根据月使用流量为网关排序
     *
     * @return  月使用流量最多的前五个网关
     */
    List<RtuRankingDto> rankRtuByMonthTraffic();

    /**
     * 根据月使用流量为集成商企业排序
     *
     * @return 月使用流量最多的前五个集成商企业
     */
    List<IntegratorRankingDto> rankIntegratorByMonthTraffic();

    /**
     * 查询截至当日云平台使用的总流量
     *
     * @return 年总流量(M)
     */
    Long countSystemTotalTraffic();


    /**
     * 根据集成商企业下的网关状态（正常，预警，流量用尽）给集成商排序，并选择前六个展示
     *
     * @return 月总流量(M)
     */
    List<QueryIntegratorByRtuWarningStatusDto> selectIntegratorByRtuWarningStatus();

}








