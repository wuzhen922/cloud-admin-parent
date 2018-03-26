<#include "/layout/baseLayout.ftl">
<#macro html_title>角色管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>角色管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                        <div class="col-sm-3">
                            <button type="button" id="addMember" onclick="addRoleInfo()"
                                    class="btn btn-sm btn-primary">
                                <i class="fa fa-plus">新增</i></button>
                        </div>
                    </div>
                    <div id="roleTable">
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
    <form class="form-horizontal" role="form" id="addRoleForm">
        <div class="form-group">
            <label for="addRoleName" class="control-label col-xs-4">角色名称</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addRoleName" name="addRoleName"
                       placeholder="请输入角色名称">
            </div>
        </div>
        <div class="form-group">
            <label for="addDesc" class="control-label col-xs-4">描述</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addDesc" name="addDesc"
                       placeholder="请输入角色描述">
            </div>
        </div>
    </form>
</div>

<!-- 修改弹出层 -->
<div id="modifyLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="modifyRoleForm">
        <div class="form-group">
            <label for="modifyRoleName" class="control-label col-xs-4">角色名称</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyRoleName" name="modifyRoleName"
                       placeholder="请输入角色名称">
            </div>
        </div>
        <div class="form-group">
            <label for="modifyDesc" class="control-label col-xs-4">描述</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyDesc" name="modifyDesc"
                       placeholder="请输入角色描述">
            </div>
        </div>
    </form>
</div>

<div id="updateMenuLayer" class="zTreeDemoBackground none">
    <ul id="menuTree" class="ztree"></ul>
</div>

</#macro>
<#macro html_foot>
    <@p.js src="authority/role.js"/>
    <@p.js src="third/zTree/js/jquery.ztree.core.min.js"/>
    <@p.js src="third/zTree/js/jquery.ztree.excheck.min.js"/>
</#macro>

<#macro html_head>
<link rel="stylesheet" type="text/css" href="${resourcesPath}/scripts/third/zTree/css/zTreeStyle/zTreeStyle.css"/>
</#macro>