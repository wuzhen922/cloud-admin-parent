<#include "/layout/baseLayout.ftl">
<#macro html_title>用户管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>用户管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                    	<div class="col-sm-3">
                            <input type="text" placeholder="请输入用户姓名" id="managerName" class="input-sm form-control">
                        </div>
                        <div class="col-sm-4">
                        	<button type="button" id="search" class="btn btn-sm btn-primary"> 搜索</button>
                            <button type="button" id="clear" class="btn btn-sm btn-white"> 清空</button>
                            <button type="button" id="addManager" onclick="addManager()"
                                    class="btn btn-sm btn-primary">
                                <i class="fa fa-plus">新增</i></button>
                        </div>
                    </div>
                    <div id="managerTable">
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

<!-- 新增弹出层 -->
<div id="addLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="addManagerForm">
        <div class="form-group">
            <label for="addManagerName" class="control-label col-xs-4">用户姓名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addManagerName" name="addManagerName"
                       placeholder="请输入用户姓名">
            </div>
        </div>
        <div class="form-group">
            <label for="addLoginName" class="control-label col-xs-4">登录名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addLoginName" name="addLoginName"
                       placeholder="请输入登录名">
            </div>
        </div>
        <div class="form-group">
            <label for="addPassword" class="control-label col-xs-4">登录密码</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addPassword" name="addPassword"
                       placeholder="请输入登录密码">
            </div>
        </div>
        <div class="form-group">
        	<label for="addAreaCode" class="control-label col-xs-4">管理城市 </label>
			<div class="col-xs-8" data-toggle="distpicker">
			  <select id = "province"></select>
			  <select id="addAreaCode"></select>
			</div>
        </div>
        <!--<div class="form-group">
            <label for="addPhone" class="control-label col-xs-4">手机号 </label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addPhone" name="addPhone"
                       placeholder="请输入手机号">
            </div>
        </div>-->
        <div class="form-group">
            <label for="addRemark" class="control-label col-xs-4">备注 </label>
            <div class="col-xs-8">
                <textarea class="form-control" id="addRemark" name="addRemark" style="width:171px;height:80px;overflow-y:visible"
                       placeholder="请输入备注信息"></textarea>
            </div>
        </div>
    </form>
</div>

<!-- 修改弹出层 -->
<div id="modifyLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="modifyManagerForm">
        <div class="form-group">
            <label for="modifyManagerName" class="control-label col-xs-4">姓名</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyManagerName" name="modifyManagerName"
                       placeholder="请输入用户姓名">
            </div>
        </div>
       <!-- <div class="form-group">
            <label for="modifyAreaCode" class="control-label col-xs-4">管理城市 </label>
			<div class="col-xs-8" data-toggle="distpicker">
			  <select></select>
			  <select id="modifyAreaCode"></select>
			</div>
        </div>-->
        <!--<div class="form-group">
            <label for="modifyPhone" class="control-label col-xs-4">手机号 </label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyPhone" name="modifyPhone"
                       placeholder="请输入手机号">
            </div>
        </div>-->
        <div class="form-group">
            <label for="modifyRemark" class="control-label col-xs-4">备注 </label>
            <div class="col-xs-8">
                <input type="textarea" class="form-control" id="modifyRemark" name="modifyRemark"
                       placeholder="请输入备注信息">
            </div>
        </div>
    </form>
</div>

<!-- 重置密码弹出层 -->
<div id="resetPasswordLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="resetManagerPasswordForm">
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
    <@p.js src="system/manager.js"/>
    <@p.js src="third/md5.js"/>
    <@p.js src="third/distpicker/distpicker.data.js"/>
    <@p.js src="third/distpicker/distpicker.js"/>
</#macro>