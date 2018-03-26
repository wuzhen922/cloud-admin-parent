<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
    <#--<th>姓名</th>-->
        <th>登录名</th>
        <th>真实姓名</th>
        <th>电话</th>
        <th>角色名</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list adminList as admin>
        <tr>
            <td id="id${admin_index}" class="none">${admin.id}</td>
            <td id="roleId${admin_index}" class="none">${admin.roleId}</td>
            <td id="userName${admin_index}">${admin.userName}</td>
            <td id="realName${admin_index}">${admin.realName}</td>
            <td id="phone${admin_index}">${admin.phone}</td>
            <td id="email${admin_index}" class="none">${admin.email}</td>
            <td id="desc${admin_index}" class="none">${admin.userDesc}</td>
        <#--<td id="job${admin_index}" class="none">${admin.job}</td>-->
            <td id="roleName${admin_index}">${admin.roleName}</td>
            <td id="createTime${admin_index}"><#if admin.createTime??><@p.out value="${admin.createTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
        <#--<td id="status${admin_index}" class="none">-->
        <#--<#if admin.status=='10'>正常-->
        <#--<#elseif admin.status=='20'>锁定-->
        <#--<#elseif admin.status=='90'>删除-->
        <#--</#if>-->
        <#--</td>-->
            <td>
                <a href="javascript:;" class="canteen-modify a-inline-block"
                   onclick="modifyAdminUser(${admin_index})"><i
                        class="fa fa-edit"></i>修改</a>
                <a href="javascript:;" class="canteen-modify a-inline-block"
                   onclick="resetPassword(${admin_index})"><i
                        class="fa fa-repeat"></i>重置密码</a>
            <#--<a href="javascript:;" class="canteen-modify a-inline-block" onclick="unLockUser(${admin_index})"><i-->
            <#--class="glyphicon glyphicon-lock"></i>解锁用户</a>-->
            <#--<a href="javascript:;" class="canteen-modify a-inline-block" onclick="bindRole(${admin_index})"><i-->
            <#--class="glyphicon glyphicon-user"></i>绑定角色</a>-->
                <a href="javascript:;" class="canteen-delete a-inline-block" style="padding-left: 0;"
                   onclick="deleteAdminUser(${admin_index})"><i
                        class="fa fa-trash-o"></i>删除</a>
                <#if admin.status=='20'>
                    <a href="javascript:;" class="canteen-modify a-inline-block" onclick="unLockUser(${admin_index})"><i
                            class="glyphicon glyphicon-lock"></i>解锁用户</a>
                </#if>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>