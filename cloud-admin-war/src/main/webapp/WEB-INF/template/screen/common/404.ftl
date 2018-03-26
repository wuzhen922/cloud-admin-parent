<#include "/layout/baseLayout.ftl">
<#macro html_title>404 页面</#macro>
<#macro html_body>

<div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">页面未找到！</h3>

    <div class="error-desc">
        抱歉，页面好像去火星了~
        <br/>您可以返回主页看看
        <br/><a href="javascript:;" class="btn btn-primary m-t">主页</a>
    </div>
</div>

</#macro>
<#macro html_foot>
    <@p.js src="common/404.js"/>
</#macro>