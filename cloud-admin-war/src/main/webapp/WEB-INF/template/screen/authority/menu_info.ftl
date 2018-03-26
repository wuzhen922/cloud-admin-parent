<#include "/layout/baseLayout.ftl">
<#macro html_title>菜单管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>主菜单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                        <div class="col-sm-3">
                            <button type="button" id="addFirstMenu" onclick="addMenu()" class="btn btn-sm btn-primary">
                                <i class="fa fa-plus">新增</i></button>
                        </div>
                    </div>
                    <div id="firstMenuTable">
                    </div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>子菜单管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                        <div class="col-sm-3">
                            <button type="button" id="addSecondMenu" onclick="addSubMenu()"
                                    class="btn btn-sm btn-primary"><i class="fa fa-plus">新增</i></button>
                        </div>
                    </div>
                    <div id="secondMenuTable">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 新增弹出层 -->
<div id="addLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="addMenuForm">
        <div class="form-group">
            <label for="addMenuIcon" class="control-label col-xs-4">菜单图标</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addMenuIcon" name="addMenuIcon" placeholder="请输菜单图标">
            </div>
        </div>

        <div class="form-group">
            <label for="addMenuName" class="control-label col-xs-4">菜单名称</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addMenuName" name="addMenuName" placeholder="请输菜单名称">
            </div>
        </div>

        <div class="form-group">
            <label for="addMenuUrl" class="control-label col-xs-4">菜单地址</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="addMenuUrl" name="addMenuUrl" placeholder="请输入菜单地址">
            </div>
        </div>

        <div class="form-group">
            <label for="addMenuSort" class="control-label col-xs-4">菜单位置</label>
            <div class="col-xs-8">
                <input type="text" class="form-control zf-layer-input" id="addMenuSort" name="addMenuSort"
                       placeholder="请输入菜单位置">
            </div>
        </div>
    </form>
</div>

<!-- 修改弹出层 -->
<div id="modifyLayer" style="display:none" class="canteen-modal">
    <form class="form-horizontal" role="form" id="modifyMenuForm">
        <div class="form-group">
            <label for="modifyMenuIcon" class="control-label col-xs-4">菜单图标</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyMenuIcon" name="modifyMenuIcon"
                       placeholder="请输菜单图标">
            </div>
        </div>

        <div class="form-group">
            <label for="modifyMenuName" class="control-label col-xs-4">菜单名称</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyMenuName" name="modifyMenuName"
                       placeholder="请输菜单名称">
            </div>
        </div>

        <div class="form-group">
            <label for="modifyMenuUrl" class="control-label col-xs-4">菜单地址</label>
            <div class="col-xs-8">
                <input type="text" class="form-control" id="modifyMenuUrl" name="modifyMenuUrl"
                       placeholder="请输入菜单地址">
            </div>
        </div>

        <div class="form-group">
            <label for="modifyMenuSort" class="control-label col-xs-4">菜单位置</label>
            <div class="col-xs-8">
                <input type="text" class="form-control zf-layer-input" id="modifyMenuSort" name="modifyMenuSort"
                       placeholder="请输入菜单位置">
            </div>
        </div>

    </form>
</div>

</#macro>
<!-- 弹出层 -->
<div id="deleteLayer" style="display:none">
    <p class="deleteInfo modal-body">您确定要删除该条记录吗？</p>
</div>


<#macro html_foot>
    <@p.js src="authority/menu.js"/>
</#macro>