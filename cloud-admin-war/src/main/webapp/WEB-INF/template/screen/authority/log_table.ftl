<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
        <th>姓名</th>
        <th>登录名</th>
        <th>登录时间</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list logList as log>
        <tr>
            <td id="id${log_index}" class="none">${log.id}</td>
            <td id="realName${log_index}">${log.realName}</td>
            <td id="userName${log_index}">${log.userName}</td>
            <td id="loginTime${log_index}"><#if log.loginTime??><@p.out value="${log.loginTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>