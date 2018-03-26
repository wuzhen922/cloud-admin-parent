<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
        <th>角色名</th>
        <th>描述</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list roleInfoList as roleInfo>
        <tr>
            <td id="id${roleInfo_index}" class="none">${roleInfo.id}</td>
            <td id="roleName${roleInfo_index}">${roleInfo.roleName}</td>
            <td id="desc${roleInfo_index}">${roleInfo.description}</td>
            <td id="createTime${roleInfo_index}"><#if roleInfo.createTime??><@p.out value="${roleInfo.createTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td>
                <a href="javascript:;" class="canteen-modify a-inline-block" onclick="modifyRoleInfo(${roleInfo_index})">
                    <i class="fa fa-edit"></i>修改</a>
            <a href="javascript:;" class="canteen-modify a-inline-block" onclick="updateRoleMenu(${roleInfo_index})">
                <i class="fa fa-sitemap"></i>修改角色菜单</a>
            <a href="javascript:;" class="canteen-delete a-inline-block" onclick="deleteRoleInfo(${roleInfo_index})">
                <i class="fa fa-trash"></i>删除</a>

        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>