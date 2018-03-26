<#include "/common/baselib.ftl">
<#macro tabledata page="true" pageSize="10" pageHandler="pageHandler" loadFirst="true" icon="false">
    <#nested/>
    <#if page="true">
        <@p.pageControl pageHandler=pageHandler loadFirst=loadFirst icon=icon/>
    </#if>
</#macro>