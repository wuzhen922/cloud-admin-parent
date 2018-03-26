<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
        <th>姓名</th>
        <th>登录名</th>
        <th>管理城市</th>
        <th>城市编码</th>
        <th>备注</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list managerList as managerList>
        <tr>
            <td id="id${managerList_index}" class="none">${managerList.id}</td>
            <td id="managerName${managerList_index}">${managerList.managerName}</td>
            <td id="loginName${managerList_index}">${managerList.loginName}</td>
            <td id="areaName${managerList_index}">${managerList.areaName}</td>
            <td id="areaCode${managerList_index}">${managerList.areaCode}</td>
            <td id="remark${managerList_index}">${managerList.remark}</td> 
            <td id="createTime${managerList_index}"><#if managerList.createTime??><@p.out value="${managerList.createTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td>
                <a href="javascript:;" class="canteen-modify a-inline-block" onclick="modifyManager(${managerList_index})"><i
                        class="fa fa-edit"></i>修改</a>
                <a href="javascript:;" class="canteen-delete a-inline-block" style="padding-left: 0;" onclick="deleteManager(${managerList_index})"><i
                        class="fa fa-trash-o"></i>删除</a>
                <a href="javascript:;" class="canteen-modify a-inline-block" onclick="resetPassword(${managerList_index})"><i
                        class="fa fa-repeat"></i>重置密码</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>