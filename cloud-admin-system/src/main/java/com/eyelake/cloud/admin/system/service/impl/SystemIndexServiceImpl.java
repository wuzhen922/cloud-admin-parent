package com.eyelake.cloud.admin.system.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.IntegratorDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RtuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.IntegratorRankingDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryRtuDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorByRtuWarningStatusDto;
import com.eyelake.cloud.admin.assist.dto.admin.RtuRankingDto;
import com.eyelake.cloud.admin.system.service.intf.DayTimerService;
import com.eyelake.cloud.admin.system.service.intf.SystemIndexService;
import com.eyelake.framework.core.trace.ServiceTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * 云平台首页具体实现
 *
 * @author  j_cong
 * @date    2018/01/22
 * @version V1.0
 */
@Service("systemIndexService")
@ServiceTrace
public class SystemIndexServiceImpl implements SystemIndexService {

    @Autowired
    private CommonDao commonDao;
    @Autowired
    private DayTimerService dayTimerService;

    @Override
    public List<RtuRankingDto> rankRtuByMonthTraffic() {

        RtuDmo con = new RtuDmo();

        List<RtuRankingDto> rtuRankingDtos = commonDao.selectList("SystemIndex.rankRtuByMonthTraffic", con);

        //查询出月使用流量为空的网关列表
        final int diffFlag = -1;
        List<QueryRtuDto> diffList = new LinkedList<>();

        for (RtuRankingDto dto : rtuRankingDtos) {

            if (diffFlag == dto.getMonthTraffic()) {
                QueryRtuDto queryRtuDto = new QueryRtuDto();

                queryRtuDto.setId(dto.getId());
                queryRtuDto.setIntegratorId(dto.getIntegratorId());
                queryRtuDto.setSnNumber(dto.getSnNumber());

                diffList.add(queryRtuDto);
            }
        }

        //调用接口查询缺失的网关月使用流量
        for (QueryRtuDto diff : diffList) {
                dayTimerService.queryRtuMonthTrafficAndPack(diff);
        }

        return commonDao.selectList("SystemIndex.rankRtuByMonthTrafficLimit5", con);
    }

    @Override
    public List<IntegratorRankingDto> rankIntegratorByMonthTraffic() {

        //返回当月使用流量最多的前五个集成商企业
        //如果集成商月流量表中无记录，将month_traffic设为0
        IntegratorDmo con = new IntegratorDmo();

        return commonDao.selectList("SystemIndex.rankIntegratorByMonthTrafficLimit5", con);
    }

    @Override
    public Long countSystemTotalTraffic() {

        IntegratorDmo con = new IntegratorDmo();

        Long yearTraffic = commonDao.selectCount("SystemIndex.countSystemYearTraffic", con);
        Long yearTraffic1 = yearTraffic == null ? 0 : yearTraffic;

        Long monthTraffic = commonDao.selectCount("SystemIndex.countSystemMonthTraffic", con);
        Long monthTraffic1 = monthTraffic == null ? 0 : monthTraffic;

        return yearTraffic1 + monthTraffic1;
    }

    @Override
    public List<QueryIntegratorByRtuWarningStatusDto> selectIntegratorByRtuWarningStatus() {
        IntegratorDmo con = new IntegratorDmo();

        return commonDao.selectList("SystemIndex.selectIntegratorByRtuWarningStatusLimit6", con);
    }
}
