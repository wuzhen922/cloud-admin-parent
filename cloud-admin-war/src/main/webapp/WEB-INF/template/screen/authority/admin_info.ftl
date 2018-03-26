<#include "/layout/baseLayout.ftl">
<#macro html_title>管理员管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>管理员管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">

                        <div class="col-sm-3">
                            <input type="text" class="form-control" id="admin-name" name="admin-name"
                                   placeholder="请输入用户名">
                        </div>

                        <div class="col-sm-3" style=" margin-left: -12px;">
                            <button type="button" id="search" class="btn btn-sm btn-primary">查询</button>
                            <button type="button" id="clear" class="btn btn-sm btn-white">清空</button>
                            <button type="button" id="addMember" onclick="addAdminUser()"
                                    class="btn btn-sm btn-primary">
                                <i class="fa fa-plus">新增</i></button>
                        </div>

                    </div>
                    <div id="memberTable">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 删除弹出层 -->
<div id="deleteLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要删除该条记录吗？</p>
</div>
<!-- 解锁弹出层 -->
<div id="unlockLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要解锁该条记录吗？</p>
</div>

<!-- 新增弹出层 -->
<div id="addLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="addAdminUserForm">
        <#--<div class="form-group">
            <label for="addRealName" class="control-label col-xs-4">姓名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addRealName" name="addRealName"
                       placeholder="请输入姓名">
            </div>
        </div>-->
        <div class="form-group">
            <label for="addUserName" class="control-label col-xs-4">登录名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addUserName" name="addUserName"
                       placeholder="请输入登录名">
            </div>
        </div>
            <div class="form-group">
                <label for="addRealName" class="control-label col-xs-4">真实姓名</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="addRealName" name="addRealName"
                           placeholder="请输入真实姓名">
                </div>
            </div>

            <div class="form-group">
                <label for="addPhone" class="control-label col-xs-4">电话号码</label>
                <div class="col-xs-8">
                <input type="text" class="form-control" id="addPhone" name="addPhone" value=" "
                       placeholder="请输入电话号码">
            </div>
            </div>

            <div class="form-group">
            <label for="addPassword" class="control-label col-xs-4">登录密码</label>
            <div class="col-xs-8">
                <input type="password" class="form-control" id="addPassword" name="addPassword"  value=" "
                       placeholder="请输入登录密码">
            </div>
        </div>

            <div class="form-group">
                <label for="addRepeatPassword" class="control-label col-xs-4">确认密码</label>
                <div class="col-xs-8">
                    <input type="password" class="form-control" id="addRepeatPassword" name="addRepeatPassword"
                           placeholder="请输入确认密码">
                </div>
            </div>

        <div class="form-group">
            <label for="addRoleInfo" class="control-label col-xs-4">角色</label>
            <div class="col-xs-8">
                <select class="form-control" id="addRoleInfo" name="addRoleInfo">
                    <option value="">请选择角色</option>
                </select>
            </div>
        </div>
    </form>
</div>

<!-- 修改弹出层 -->
<div id="modifyLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="modifyAdminUserForm">
        <#--<div class="form-group">
            <label for="modifyRealName" class="control-label col-xs-4">姓名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyRealName" name="modifyRealName"
                       placeholder="请输入姓名">
            </div>
        </div>-->
        <div class="form-group">
            <label for="modifyUserName" class="control-label col-xs-4">登录名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyUserName" name="modifyUserName"
                       placeholder="请输入登录名">
            </div>

        </div>
        <#--<div class="form-group">-->
            <#--<label for="modifyPassword" class="control-label col-xs-4">登录密码</label>-->
            <#--<div class="col-xs-8">-->
                <#--<input type="password" class="form-control" id="modifyPassword" name="modifyPassword"-->
                       <#--placeholder="请输入登录密码">-->
            <#--</div>-->
        <#--</div>-->
            <div class="form-group">
                <label for="modifyRealName" class="control-label col-xs-4">真实姓名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyRealName" name="modifyRealName"
                       placeholder="请输入真实姓名">
            </div>
            </div>

            <div class="form-group">
                <label for="modifyPhone" class="control-label col-xs-4">电话号码</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyPhone" name="modifyPhone"
                       placeholder="请输入电话号码">
            </div>
            </div>
            <div class="form-group">
                <label for="modifyPassword" class="control-label col-xs-4">登录密码</label>
                <div class="col-xs-8">
                    <input type="password" class="form-control" id="modifyPassword" name="modifyPassword"  value=" "
                           placeholder="请输入登录密码">
                </div>
            </div>

            <div class="form-group">
                <label for="modifyRepeatPassword" class="control-label col-xs-4">确认密码</label>
                <div class="col-xs-8">
                    <input type="password" class="form-control" id="modifyRepeatPassword" name="modifyRepeatPassword"
                           placeholder="请输入确认密码">
                </div>
            </div>

        <div class="form-group">
            <label for="modifyRoleInfo" class="control-label col-xs-4">角色</label>
            <div class="col-xs-8">
                <select class="form-control" id="modifyRoleInfo" name="modifyRoleInfo">
                    <option value="">请选择角色</option>
                </select>
            </div>
        </div>
    </form>
</div>

<!-- 重置密码弹出层 -->
<div id="resetPasswordLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="resetPasswordForm">
        <div class="form-group">
            <label for="newPassword" class="control-label col-xs-4">新密码</label>
            <div class="zf-layer-input col-xs-8">
                <input type="password" class="form-control" id="newPassword" name="newPassword"
                       placeholder="请输入新密码">
            </div>
        </div>
        <div class="form-group">
            <label for="repeatPassword" class="control-label col-xs-4">确认密码</label>
            <div class="zf-layer-input col-xs-8">
                <input type="password" class="form-control" id="repeatPassword" name="repeatPassword"
                       placeholder="请输入确认密码">
            </div>
        </div>
    </form>
</div>

</#macro>
<#macro html_foot>
    <@p.js src="authority/admin.js"/>
    <@p.js src="third/md5.js"/>
</#macro>