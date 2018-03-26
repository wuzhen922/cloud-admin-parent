package com.eyelake.cloud.admin.system.dto;


import com.eyelake.cloud.admin.assist.dmo.admin.MenuInfoDmo;

import java.util.List;

/**
 * 一级菜单实体
 * Created by wunder on 2016/10/14 07:37.
 */
public class FirstMenuDto {

    private MenuInfoDmo menuInfoDmo;

    private List<SecondMenuDto> secondMenuList;

    public MenuInfoDmo getMenuInfoDmo() {
        return menuInfoDmo;
    }

    public void setMenuInfoDmo(MenuInfoDmo menuInfoDmo) {
        this.menuInfoDmo = menuInfoDmo;
    }

    public List<SecondMenuDto> getSecondMenuList() {
        return secondMenuList;
    }

    public void setSecondMenuList(List<SecondMenuDto> secondMenuList) {
        this.secondMenuList = secondMenuList;
    }
}
