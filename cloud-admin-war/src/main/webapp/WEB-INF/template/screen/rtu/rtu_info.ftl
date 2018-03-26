<#include "/layout/baseLayout.ftl">
<#macro html_title>网关管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>网关管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                         <div class="col-sm-3" style="height: 35px;line-height: 35px ;padding:0">
                            <div class="form-group">

                                <label for="rtuName" class="control-label col-xs-4 col-md-4" style=" white-space:nowrap">网关名称</label>
                                <input type="text" placeholder="请输入网关名称" id="rtuName" name="rtuName" class="col-xs-8 col-md-8 form-control">
                            </div>
                        </div>

                        <div class="col-sm-3" style="height: 35px;line-height: 35px ;padding:0">

                            <div class="form-group">
                                <label for="rtuTransType" class="control-label col-xs-4 col-md-4" style=" white-space:nowrap">网关传输类型</label>
                                <div class="col-xs-8 col-md-8">
                                    <select class="form-control " id="rtuTransType" name="rtuTransType">
                                        <option value="">请选择传输类型</option>
                                        <option value="00">4G</option>
                                        <option value="10">NB-IoT</option>
                                        <option value="20">北斗</option>
                                        <option value="30">天通</option>
                                    </select>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-3" style="height: 35px;line-height: 35px ;padding:0">
                            <div class="form-group">
                                <label for="rtuTransType" class="control-label col-md-4" style=" white-space:nowrap">集成商企业</label>
                                <div class="col-xs-8 col-md-8">
                                <select class="form-control" id="integratorId" name="integratorId">
                                    <option value="">请选择集成商</option>
                                </select>
                                </div>
                            </div>
                        </div>

                        <div>
                        	<button type="button" id="search" class="btn btn-sm btn-primary"> 搜索</button>
                        	<button type="button" id="clear" class="btn btn-sm btn-white"> 清空</button>
                            <button type="button" id="addRtu" onclick="addRtu()" class="btn btn-sm btn-primary">新增</button>
                            <button type="button" id="batchDelete" class="btn btn-sm btn-primary none">批量删除</button>
                            <button type="button" id="modifyButton" class="btn btn-sm btn-primary" onclick="modifyStatusThings()">编辑</button>
                            <button type="button" id="excelImport" class="btn btn-sm import importBtu">导入</button>
                        </div>
                    </div>
                    <div id="rtuTable">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 新增弹出层 -->
<div id="addLayer" style="display:none" class="canteen-modal"  >
    <form class="form-horizontal"  role="form" id="addRtuForm">
        <div class="col-xs-12">

            <div class="form-group">
                <label for="addRtuName" class="control-label col-xs-4">网关名称</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="addRtuName" name="addRtuName"
                           placeholder="请输入网关名称">
                </div>
            </div>

            <div class="form-group">
                <label for="addRtuModel" class="control-label col-xs-4">网关型号</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="addRtuModel" name="addRtuModel"
                           placeholder="请输入网关型号">
                </div>
            </div>

            <div class="form-group">
                <label for="addMachineCode" class="control-label col-xs-4">机器码</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="addMachineCode" name="addMachineCode"
                           placeholder="请输入机器码">
                </div>
            </div>

            <div class="form-group">
                <label for="addSnNumber" class="control-label col-xs-4">网关序列码</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="addSnNumber" name="addSnNumber"
                           placeholder="请输入网关序列码">
                </div>
            </div>

            <div class="form-group">
                <label for="addRtuTransType" class="control-label col-xs-4">网关传输类型</label>
                <div class="col-xs-8">
                    <select class="form-control" id="addRtuTransType" name="addRtuTransType">
                        <option value="">请选择网关传输类型</option>
                        <option value="00">4G</option>
                        <option value="10">NB-IoT</option>
                        <option value="20">北斗</option>
                        <option value="30">天通</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="addRtuAccessType" class="control-label col-xs-4">网关接入类型</label>
                <div class="col-xs-8">
                    <select class="form-control" id="addRtuAccessType" name="addRtuAccessType">
                        <option value="">请选择网关接入类型</option>
                        <option value="10">多通道</option>
                        <option value="99">单通道</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="addOperator" class="control-label col-xs-4">所属运营商</label>
                <div class="col-xs-8">
                    <select class="form-control" id="addOperator" name="addOperator">
                        <option value="">请选择所属运营商</option>
                        <option value="00">中国移动</option>
                        <option value="01">中国联通</option>
                        <option value="03">中国电信</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="addIntegrator" class="control-label col-xs-4">所属集成商</label>
                <div class="col-xs-8">
                    <select class="form-control" id="addIntegrator" name="addIntegrator">
                        <option value="">请选择集成商</option>
                    </select>
                </div>
            </div>
        </div>

    </form>
</div>

<!-- 修改弹出层 -->
<div id="modifyLayer" style="display:none" class="canteen-modal"  >
    <form class="form-horizontal"  role="form" id="modifyRtuForm">
        <div class="col-xs-12">

            <div class="form-group">
                <label for="modifyRtuName" class="control-label col-xs-4">网关名称</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="modifyRtuName" name="modifyRtuName"
                           placeholder="请输入网关名称">
                </div>
            </div>

            <div class="form-group">
                <label for="modifyRtuModel" class="control-label col-xs-4">网关型号</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="modifyRtuModel" name="modifyRtuModel"
                           placeholder="请输入网关型号">
                </div>
            </div>
            <!--不可修改字段-->
            <div class="form-group">
                <label for="modifyMachineCode" class="control-label col-xs-4">机器码</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="modifyMachineCode" name="modifyMachineCode"
                           disabled="disabled" placeholder="请输入机器码" >
                </div>
            </div>

            <div class="form-group">
                <label for="modifySnNumber" class="control-label col-xs-4">网关序列码</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control" id="modifySnNumber" name="modifySnNumber"
                           placeholder="请输入网关序列码">
                </div>
            </div>

            <div class="form-group">
                <label for="modifyRtuTransType" class="control-label col-xs-4">网关传输类型</label>
                <div class="col-xs-8">
                    <select class="form-control" id="modifyRtuTransType" name="modifyRtuTransType">
                        <option value="">请选择网关传输类型</option>
                        <option value="00">4G</option>
                        <option value="10">NB-IoT</option>
                        <option value="20">北斗</option>
                        <option value="30">天通</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="modifyRtuAccessType" class="control-label col-xs-4">网关接入类型</label>
                <div class="col-xs-8">
                    <select class="form-control" id="modifyRtuAccessType" name="modifyRtuAccessType">
                        <option value="">请选择网关接入类型</option>
                        <option value="10">多通道</option>
                        <option value="99">单通道</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="modifyOperator" class="control-label col-xs-4">所属运营商</label>
                <div class="col-xs-8">
                    <select class="form-control" id="modifyOperator" name="modifyOperator">
                        <option value="">请选择所属运营商</option>
                        <option value="00">中国移动</option>
                        <option value="01">中国联通</option>
                        <option value="03">中国电信</option>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="modifyIntegrator" class="control-label col-xs-4">所属集成商</label>
                <div class="col-xs-8">
                    <select class="form-control" id="modifyIntegrator" name="modifyIntegrator">
                        <option value="">请选择集成商</option>
                    </select>
                </div>
            </div>
        </div>

    </form>
</div>


<!-- rtu导入弹出层 -->
<div id="exportRtus" class="zf-modal" style="display:none">
    <form class="form-horizontal" role="form">
        <div class="layerWidth layerLabel">
            <span>导入网关之前请先<a class="iconfont icon-excel" href="${ctx}/resources/template/云平台网关设备导入模板.xlsx" id="excelDown">下载模板</a></span>
        </div>
        <div class="layerWidth">
            <input class="form-control" type="file" name="filePath"
                   onchange="filePathOnchange('#filePath')" id="filePath"/>

        </div>
    </form>
</div>
<!--导入失败弹出框-->
<div id="rtuExit" class="zf-modal" style="display:none">
    <form  class="form-horizontal" role="form" id='rtuExitForm' accept-charset="UTF-8" method="get">
        <div class="layerWidth layerLabel">
            <span class="errorRtuMessage"></span> &nbsp; <span> 查看错误详情请<a class="iconfont icon-excel" href="javascript;" id="excelDownFailRtu">下载错误报告</a></span>
        </div>

    </form>

</div>

<!-- 禁用弹出层 -->
<div id="disabledLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要禁用该网关吗？</p>
</div>

<!-- 启用弹出层 -->
<div id="enabledLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要启用该网关吗？</p>
</div>

<!-- 删除弹出层 -->
<div id="deleteLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要删除该网关吗？</p>
</div>
<div id="batchDeleteRtuLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要删除所有所选网关吗？</p>
</div>
</#macro>
<#macro html_foot>
    <@p.js src="third/My97DatePicker/WdatePicker.js"/>
    <@p.js src="third/ajaxfileupload.js"/>
    <@p.js src="rtu/rtu.js"/>
</#macro>

