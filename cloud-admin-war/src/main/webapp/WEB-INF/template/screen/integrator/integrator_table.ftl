<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="integratorTr table table-hover table-bordered table-striped table-condensed" style=" width: 100%;
    display: block;
    overflow: hidden;">
    <thead style="height: 100%;
    display: block;" >
    <tr class="thead">
        <th>用户名</th>
        <th>企业名称</th>
        <th>地址</th>
        <th>手机号</th>
        <th>鉴权key</th>
        <th>鉴权secret</th>
        <th>入网许可号</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody style="width: 100%;
    display: block;">
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list integratorList as integratorList>
        <tr>
            <td id="id${integratorList_index}" class="none">${integratorList.id}</td>
            <td id="userName${integratorList_index}">${integratorList.userName}</td>
            <td id="company${integratorList_index}">${integratorList.company}</td>
            <td id="address${integratorList_index}">${integratorList.address}</td>
            <td id="phone${integratorList_index}">${integratorList.phone}</td>
            <td id="key${integratorList_index}">${integratorList.integratorKey}</td>
            <td id="secret${integratorList_index}">${integratorList.secret}</td>
            <td id="admittance${integratorList_index}">${integratorList.admittance}</td>
            <td id="status${integratorList_index}" class="none">${integratorList.status}</td>
            <td id="remark${integratorList_index}" class="none">${integratorList.remark}</td>
            <td id="createTime${integratorList_index}" class="none"><#if integratorList.createTime??><@p.out value="${integratorList.createTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td id="lastUpdateTime${integratorList_index}" class="none"><#if integratorList.lastUpdateTime??><@p.out value="${integratorList.lastUpdateTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td>

                <#if integratorList.status == "10">
                <form id="exportForm" accept-charset="UTF-8" method="post">
                    <input id = "integratorId" name = "integratorId" class="none"/>
                    <input id = "integratorName" name = "integratorName" class="none"/>
                    <a href="javascript:;"  class="canteen-modify a-inline-block" onclick="excelExport(${integratorList_index})">
                        <i class="fa fa-edit"></i>导出</a>
                </form>
                </#if>
                <a href="javascript:;" class="canteen-modify a-inline-block"  onclick="changeIntegratorStatus(${integratorList_index})">
                    <i class="fa fa-edit"></i>
                    <#if integratorList.status=='10'>禁用
                    <#elseif integratorList.status=='20'>启用
                    </#if>
                </a>
                <a href="javascript:;" class="canteen-delete a-inline-block" style="padding-left: 0;" onclick="deleteIntegrator(${integratorList_index})"><i
                        class="fa fa-trash-o"></i>删除</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>
