<#include "/layout/baseLayout.ftl">
<#macro html_title>登录日志</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>登录日志</h5>
                </div>
                <div class="ibox-content">
                    <div class="row search-bar">
                        <div class="col-sm-2">
                            <input class="input-sm form-control" style="border: 1px solid #ddd;" id="startDate"
                                   type="text" onClick="WdatePicker()" placeholder="请选择开始时间">
                        </div>
                        <div class="col-sm-2">
                            <input class="input-sm form-control" style="border: 1px solid #ddd;" id="endDate"
                                   type="text"
                                   onClick="WdatePicker()" placeholder="请选择截止时间">
                        </div>
                        <div class="col-sm-2">
                            <button type="button" id="search" class="btn btn-sm btn-primary"> 搜索</button>
                            <button type="button" id="clear" class="btn btn-sm btn-white"> 清空</button>
                        </div>
                    </div>
                    <div id="loginLogTable">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</#macro>
<#macro html_foot>
    <@p.js src="authority/log.js"/>
    <@p.js src="third/My97DatePicker/WdatePicker.js"/>
</#macro>