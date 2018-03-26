package com.eyelake.cloud.admin.system.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.dmo.admin.AdminRoleDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RoleInfoDmo;
import com.eyelake.cloud.admin.assist.dmo.admin.RoleMenuDmo;
import com.eyelake.cloud.admin.assist.dto.admin.MenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryMenuTreeNodeDto;
import com.eyelake.cloud.admin.assist.dto.admin.RoleMenuInfoDto;
import com.eyelake.cloud.admin.system.service.intf.AdminRoleInfoService;
import com.eyelake.cloud.common.constants.Constants;


import com.eyelake.cloud.common.utils.SqlAssertUtils;
import com.eyelake.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.AppException;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

@Service("adminRoleInfoService")
@ServiceTrace
public class AdminRoleInfoServiceImpI implements AdminRoleInfoService {

    @Autowired
    private CommonDao commonDao;

    @Override
    public Result insert(RoleInfoDmo roleInfoDmo) {

        int i = commonDao.insert(roleInfoDmo);

        return SqlAssertUtils.insertAssert(i);
    }

    @Override
    public Result update(RoleInfoDmo con) {

        int i = commonDao.update(con);

        return SqlAssertUtils.updateAssert(i);

    }

    @Override
    public Result delete(RoleInfoDmo con) {

        int i = commonDao.delete(con);

        return SqlAssertUtils.deleteAssert(i);
    }

    @Override
    public RoleInfoDmo selectOne(RoleInfoDmo con) {
        return (RoleInfoDmo)commonDao.selectOne(con);
    }

    @Override
    public List<RoleInfoDmo> selectList(RoleInfoDmo con) {
        return commonDao.selectList(con);
    }

    @Override
    public List<RoleInfoDmo> selectListByPage(RoleInfoDmo con, Page page) {
        return commonDao.selectListByPage("AdminRoleInfoMapper.queryRoleInfoCount", "AdminRoleInfoMapper.queryRoleInfoList", con, page);
    }

    @Override
    public Result addRoleInfo(RoleInfoDmo roleInfoDmo) {

        Result result = new Result();

        RoleInfoDmo con = new RoleInfoDmo();

        con.setRoleName(roleInfoDmo.getRoleName());
        con.setStatus(Constants.RoleStatus.NORMAL);

        RoleInfoDmo exist = selectOne(con);

        if (null != exist){

            result.fail("ARI-0001","新增角色失败，角色名已存在");
            return result;
        }

        return insert(roleInfoDmo);
    }

    @Override
    public Result updateRoleInfo(RoleInfoDmo roleInfoDmo) {

        Result result = new Result();

        if(null == roleInfoDmo)
        {
            result.fail("URI-0001","参数异常");
            return result;
        }

        RoleInfoDmo dmo = new RoleInfoDmo();

        dmo.setRoleName(roleInfoDmo.getRoleName());
        dmo.setStatus(Constants.RoleStatus.NORMAL);

        RoleInfoDmo existRoleInfo = selectOne(dmo);

        if(existRoleInfo != null&&!existRoleInfo.getId().equals( roleInfoDmo.getId()))
        {
            result.fail("UAU-0002","角色名已存在");
            return result;
        }

        return update(roleInfoDmo);
    }

    @Override
    @Transactional
    public Result deleteRoleInfo(RoleInfoDmo con) {

        Result result = new Result();

        con.setStatus(Constants.RoleStatus.NORMAL);

        RoleInfoDmo roleInfoDmo = selectOne(con);

        if (null == roleInfoDmo){

            result.fail("DRI-0001","删除角色失败，角色不存在");
            return result;
        }
        
        AdminRoleDmo adminRole=new AdminRoleDmo();
        adminRole.setRoleId(con.getId());
        List<AdminRoleDmo> adminRoleList=commonDao.selectList(adminRole);
        
        if(!CollectionUtils.isEmpty(adminRoleList)){
        	 result.fail("角色已和管理员绑定，删除角色失败");
             return result;
        }
        
        roleInfoDmo.setStatus(Constants.RoleStatus.DELETE);

        result = update(roleInfoDmo);

        if (!result.isSuccess()) {
            result.fail("DRI-0002","删除角色失败，系统异常");
            throw new AppException(result);
        }
        //删除角色菜单关联信息
        RoleMenuDmo roleMenuDmo = new RoleMenuDmo();

        roleMenuDmo.setRoleId(roleInfoDmo.getId());

        int t = commonDao.delete("AdminRoleInfoMapper.deleteRoleMenu",roleMenuDmo);

        if (t < 0) {
            result.fail("DRI-0003","删除角色失败，系统异常");
            throw new AppException(result);
        }

        return result;
    }

    @Override
    public List<MenuTreeNodeDto> queryMenuTreeByRole(QueryMenuTreeNodeDto con) {

        HashMap<String ,String> conMap = new HashMap<>();

        conMap.put("roleId",con.getRoleId());
        conMap.put("subMenuLevel",con.getSubMenuLevel());
        conMap.put("menuStatus", Constants.MenuStatus.NORMAL);

        return commonDao.selectList("AdminRoleInfoMapper.queryMenuListByRole",conMap);
    }

    @Override
    @Transactional
    public Result updateRoleMenuInfo(RoleMenuInfoDto roleMenuInfoDto) {

        Result result = new Result();

        if (null == roleMenuInfoDto.getRoleId()){

            result.fail("UPM-0001","更新角色菜单信息失败");
            return result;

        }
        //清空原有角色菜单关系列表
        RoleMenuDmo con = new RoleMenuDmo();

        con.setRoleId(roleMenuInfoDto.getRoleId());

        int i = commonDao.delete(con);

        if (i<0){

            result.fail("UPM-0002","更新角色菜单信息失败");
            throw new AppException(result);

        }
        //角色菜单列表为空，更新成功
        if (CollectionUtils.isEmpty(roleMenuInfoDto.getMenuIdList())){

            return result;

        }
        //批量插入角色菜单列表
        int j = commonDao.batchInsert("AdminRoleInfoMapper.updateRoleMenuInfo",roleMenuInfoDto);

        if (j != roleMenuInfoDto.getMenuIdList().size()){

            result.fail("UPM-0003","更新角色菜单信息失败");
            throw new AppException(result);

        }

        return result;
    }

    @Override
    public RoleInfoDmo findRoleInfoByUserId(String userId) {

        if(StringUtils.isBlank(userId)){

            return null;
        }

        HashMap<String,String > conMap = new HashMap<>();

        conMap.put("userId",userId.toString());

        return (RoleInfoDmo)commonDao.selectOne("AdminRoleInfoMapper.findRoleInfoByUserId",conMap);
    }


}
