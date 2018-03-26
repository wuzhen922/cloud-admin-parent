<#include "/layout/baseLayout.ftl">
<#macro html_title>集成商管理</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>集成商管理</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                         <div class="col-sm-3 form-group" style=" height: 35px;    padding: 0; line-height: 35px;">

                             <label class="col-sm-4 col-md-4" >集成商用户</label>
                            <div class="input-group col-sm-8 col-md-8">
                                <input type="text" placeholder="请输入集成商用户名" id="userName" name="userName" class="input-sm form-control">
                            </div>

                        </div>


                        <div class="col-sm-3 form-group" style=" height: 35px;     padding: 0;line-height: 35px;">
                            <label class="col-sm-4 col-md-4" >集成商企业</label>
                            <div class="input-group col-sm-8 col-md-8">
                                <input type="text" placeholder="请输入集成商企业名称" id="company" name="company" class="input-sm form-control">
                            </div>
                        </div>


                        <div class="col-sm-3 form-group" style=" height: 35px;    padding: 0; line-height: 35px;">
                            <label class="col-sm-4 col-md-4">手机号码</label>
                            <div class="input-group col-sm-8 col-md-8">
                                <input type="text" placeholder="请输入手机号码" id="phone" name="phone" class="input-sm form-control">
                            </div>
                        </div>


                        	<button type="button" id="search" class="btn btn-sm btn-primary" style="    margin-left: 15px;"> 搜索</button>
                        	<button type="button" id="clear" class="btn btn-sm btn-white"> 清空</button>

                    </div>
                    <div id="IntegratorTable">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- 禁用弹出层 -->
<div id="disabledLayer" style="display:none" class="canteen-modal">
    <#--<p class="deleteInfo modal-body">您确定要禁用该集成商吗？</p>-->
    <form class="form-horizontal" role="form" id="disabledReasonForm">
    <div class="form-group">
        <label for="disabledReason" class="control-label col-xs-4">禁用原因</label>
        <div class="col-xs-8">
            <input type="text" class="form-control" id="disabledReason" name="disabledReason"
                   placeholder="请输入禁用原因">
        </div>
    </div>
    </form>
</div>

<!-- 启用弹出层 -->
<div id="enabledLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要启用该集成商吗？</p>
</div>

<!-- 删除弹出层 -->
<div id="deleteLayer" style="display:none" class="canteen-modal">
    <p class="deleteInfo modal-body">您确定要删除该集成商吗？</p>

</div>

</#macro>
<#macro html_foot>
    <@p.js src="third/My97DatePicker/WdatePicker.js"/>
    <@p.js src="third/ajaxfileupload.js"/>
    <@p.js src="integrator/integrator.js"/>
</#macro>

