<#-- 基本布局模板 -->
<#include "/common/baselib.ftl">
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><@html_title/> - 售后服务管理系统</title>
<#if head_meta??>
    <@head_meta/>
</#if>
    <link rel="shortcut icon" href="${resourcesPath}/theme/images/favicon.ico">
<@p.css src="bootstrap.min.css"/>
<@p.css src="font-awesome.css"/>
<@p.css src="animate.css"/>
<@p.css src="style.css"/>

<#if html_head??>
    <@html_head/>
</#if>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<#compress>
    <@html_body/>

<script type="text/javascript">
    var YHu_Config = {
        bathPath: "${ctx}"
    };
</script>

    <@p.js src="third/jquery.min.js"/>
    <@p.js src="third/bootstrap.min.js"/>
    <@p.js src="common/global.js"/>
    <@p.js src="third/layer/layer.js"/>
    <@p.js src="third/validate/jquery.validate.min.js"/>
    <@p.js src="third/validate/messages_zh.min.js"/>
    <@p.js src="common/form-validate.js"/>
    <@p.js src="third/metisMenu/jquery.metisMenu.js"/>
    <@p.js src="third/slimscroll/jquery.slimscroll.min.js"/>


    <#if html_foot??>
        <@html_foot/>
    </#if>
</#compress>
</body>
</html>