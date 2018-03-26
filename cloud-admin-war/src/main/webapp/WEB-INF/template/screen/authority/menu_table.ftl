<#include "/layout/tableDataLayout.ftl">
<@tabledata icon="true" pageHandler="${pageHandler}">
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
        <th>菜单图标</th>
        <th>菜单名称</th>
        <th>菜单位置</th>
        <th>菜单地址</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list menuList as menu>
        <tr>
            <td id="id${menu.menuLevel}${menu_index}" class="none">${menu.id}</td>
            <td id="parentId${menu.menuLevel}${menu_index}" class="none">${menu.parentId}</td>
            <td><i class="${menu.menuIcon}"></i></td>
            <td id="menuIcon${menu.menuLevel}${menu_index}" class="none">${menu.menuIcon}</td>
            <td id="menuName${menu.menuLevel}${menu_index}">${menu.menuName}</td>
            <td id="menuLevel${menu.menuLevel}${menu_index}" class="none">${menu.menuLevel}</td>
            <td id="parentMenuName${menu.menuLevel}${menu_index}" class="none">${menu.parentMenuName}</td>
            <td id="sort${menu.menuLevel}${menu_index}">${menu.sort}</td>
            <td id="menuUrl${menu.menuLevel}${menu_index}">${menu.menuUrl}</td>
            <td>
                <a href="javascript:;" class="canteen-modify a-inline-block" onclick="modifyMenu(${menu.menuLevel}${menu_index})">
                    <i class="fa fa-edit"></i>修改</a>
                <a href="javascript:;" class="canteen-delete a-inline-block" onclick="deleteMenu(${menu.menuLevel}${menu_index})">
                    <i class="fa fa-trash-o"></i>删除</a>
                <#if menu.menuLevel=="1"><a href="javascript:;" class="canteen-modify a-inline-block" onclick="viewSubMenu(${menu.menuLevel}${menu_index})">
                    <i class="fa fa-eye"></i>查看子菜单</a></#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>