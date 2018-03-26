package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dto.admin.QueryIntegratorDto;
import com.eyelake.cloud.admin.assist.dto.admin.TreeNodeDataInfoDto;
import com.eyelake.cloud.admin.assist.dto.admin.TreeNodeDto;
import com.eyelake.cloud.admin.assist.result.RtuTreeResult;
import com.eyelake.cloud.admin.system.service.intf.QueryRtuTreeService;
import com.eyelake.cloud.admin.system.service.intf.RtuService;
import com.eyelake.framework.core.trace.ServiceTrace;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * @author xudajan
 */
@Service("queryRtuTreeService")
@ServiceTrace
public class QueryRtuTreeServiceImpl implements QueryRtuTreeService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public RtuTreeResult queryRtuTree() {

        RtuTreeResult result = new RtuTreeResult();

        //树结构
        List<TreeNodeDto> treeNodeDtoList = new ArrayList<>();

        HashMap<String, String> map = new HashMap<>();

        //查询集成商
        List<TreeNodeDto> integratorNodesList = commonDao.selectList("RealTimeTrafficMapper.queryIntegratorNodes", map);

        if (CollectionUtils.isEmpty(integratorNodesList)) {

            result.fail("", "没有查找到集成商企业");
            return result;
        }

        for (TreeNodeDto integratorNode : integratorNodesList) {
            map.put("integratorId", integratorNode.getId().replace("I",""));
            List<TreeNodeDto> rtuNodesList = commonDao.selectList("RealTimeTrafficMapper.queryRtuNodes", map);
            for (TreeNodeDto rtuNode : rtuNodesList) {
                TreeNodeDataInfoDto rtuTreeNodeDataInfo = new TreeNodeDataInfoDto();
                rtuTreeNodeDataInfo.setWarningStatus(rtuNode.getStatus());
                rtuTreeNodeDataInfo.setSnNumber(rtuNode.getSnNumber());
                rtuNode.setData(rtuTreeNodeDataInfo);
            }
            integratorNode.setChildren(rtuNodesList);
        }

        TreeNodeDto systemNode  = new TreeNodeDto();
        systemNode.setId("P0");
        systemNode.setTitle("云平台");
        systemNode.setChildren(integratorNodesList);
        treeNodeDtoList.add(systemNode);
        result.setList(treeNodeDtoList);
        return result;
    }
}
