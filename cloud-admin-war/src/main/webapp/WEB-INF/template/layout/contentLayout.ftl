<#-- 基本布局模板 -->
<#include "/common/baselib.ftl">
<#compress>
    <@html_body/>

    <script type="text/javascript">
        var YHu_Config = {
            bathPath: "${ctx}"
        };
    </script>

    <@p.js src="common/global.js"/>


    <#if html_foot??>
        <@html_foot/>
    </#if>
</#compress>
