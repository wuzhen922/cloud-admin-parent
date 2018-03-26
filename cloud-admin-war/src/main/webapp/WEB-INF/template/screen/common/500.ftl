<#include "/layout/baseLayout.ftl">
<#macro html_title>500 页面</#macro>
<#macro html_body>

<div class="middle-box text-center animated fadeInDown">
    <h1>500</h1>
    <h3 class="font-bold">服务器内部错误</h3>

    <div class="error-desc">
        服务器好像出错了...
        <br/>您可以返回主页看看
        <br/><a href="javascript:;" class="btn btn-primary m-t">主页</a>
    </div>
</div>

</#macro>
<#macro html_foot>
    <@p.js src="common/500.js"/>
</#macro>