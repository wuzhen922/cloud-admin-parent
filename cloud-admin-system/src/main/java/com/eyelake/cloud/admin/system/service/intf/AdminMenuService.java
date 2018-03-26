package com.eyelake.cloud.admin.system.service.intf;


import com.eyelake.cloud.admin.assist.dmo.admin.MenuInfoDmo;
import com.eyelake.cloud.admin.assist.dto.admin.AdminMenuDto;
import com.eyelake.cloud.admin.assist.dto.admin.QueryMenuListDto;
import com.eyelake.cloud.admin.system.dto.FirstMenuDto;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import java.util.List;


/**
 * @author wunder
 */
public interface AdminMenuService {

    public Result insert(MenuInfoDmo menuInfoDmo);

    public Result update(MenuInfoDmo menuInfoDmo);

    public List<MenuInfoDmo> selectList(MenuInfoDmo con);
    /**
     * 分页查询菜单列表
     * @param con
     * @param page
     * @return
     */
    public List<AdminMenuDto> selectListByPage(MenuInfoDmo con, Page page);

    /**
     * 查询菜单列表
     * @param con
     * @return
     */
    public List<MenuInfoDmo> queryMenuListByUser(QueryMenuListDto con);

    /**
     * 加载菜单列表
     * @param con
     * @return
     */
    public List<FirstMenuDto> loadMenuListByUser(QueryMenuListDto con);

    /**
     * 新增菜单
     * @param menuInfoDmo
     * @return
     */
    public Result addMenu(MenuInfoDmo menuInfoDmo);

    /**
     * 修改菜单
     * @param menuInfoDmo
     * @return
     */
    public Result updateMenu(MenuInfoDmo menuInfoDmo);

    /**
     * 删除菜单
     * @param menuInfoDmo
     * @return
     */
    public Result deleteMenu(MenuInfoDmo menuInfoDmo);

    /**
     * 查询菜单列表
     * @param con
     * @return
     */
    public List<String> queryMenuListByUserId(QueryMenuListDto con);

}
