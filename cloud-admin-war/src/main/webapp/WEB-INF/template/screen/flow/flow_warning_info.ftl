<#include "/layout/baseLayout.ftl">
<#macro html_title>流量预警</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>流量预警</h5>
                </div>
                <div class="ibox-content">

                    <div class="row search-bar">
                        <div class="col-sm-3 form-group" style="width:300px;padding:0;margin:0">
                            <label class="col-sm-4" >网关名称</label>
                            <div class="input-group col-sm-8">
                                <input type="text" placeholder="请输入网关名称" id="queryWarningName" name="queryWarningName" class="input-sm form-control">
                            </div>
                        </div>
                        <button type="button" id="search" class="btn btn-sm btn-primary" style="margin-left:8px"> 搜索</button>
                        <button type="button" id="clear" class="btn btn-sm btn-white"> 清空</button>
                    </div>

                    <div id="flowWarningTable">
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>





</#macro>
<#macro html_foot>
    <@p.js src="third/jquery-ui.js"/>

    <@p.js src="third/slideWarning/js/ion.rangeSlider.min.js"/>

    <@p.js src="flow/flowWarning.js"/>



</#macro>
<#macro html_head>
    <@p.css src="init.css"/>

    <@p.css src="main.css"/>
    <@p.css src="flow.css"/>


    <@p.css src="third/slideWarning/ion.rangeSlider.css"/>
    <@p.css src="third/slideWarning/ion.rangeSlider.skinHTML5.css"/>
</#macro>