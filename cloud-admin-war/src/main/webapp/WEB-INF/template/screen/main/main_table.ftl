<#include "/layout/tableDataLayout.ftl">
<@tabledata page="false">
<table class="table table-hover table-bordered eyelinkTable table-condensed">
    <thead>
    <tr class="thead">

        <th>采集终端名称</th>
    <#-- <th>采集终端厂商</th>-->
        <th>采集终端型号</th>
        <th>所属企业</th>
        <th>网卡运营商</th>
        <th>网卡套餐</th>
        <th>网卡状态</th>


    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list flowList as list>
        <tr>
            <td id="id${list_index}" class="none">${list.id}</td>
            <td id="ownerId${list_index}" class="none">${list.ownerId}</td>
            <td id="status${list_index}" class="none">${list.status}</td>
            <td id="snNumber${list_index}" class="none">${list.snNumber}</td>
            <td id="packTraffic${list_index}" class="none">${list.packTraffic}</td>
            <td id="warningNum${list_index}" class="none">${list.warningNum}</td>

            <td id="rtuName${list_index}">${list.rtuName}</td>
        <#--<td id="rtuIntegratorName${list_index}"> <#if list.integratorName=='null'>
            --<#else>${list.integratorName}
        </#if></td>-->

            <td id="rtuModel${list_index}">${list.rtuModel}</td>

            <td id="orgName${list_index}">${list.orgName}</td>
            <td id="carrierOperatorEnum${list_index}" data-value=${list.carrierOperatorEnum}>
              <#if list.carrierOperatorEnum=="00">移动<#elseif list.carrierOperatorEnum=="01">联通
              <#elseif list.carrierOperatorEnum=="03">电信 <#else>--</#if>
            </td>
            <td id="packName${list_index}">${list.packName}</td>
            <td id="warningStatus${list_index}" data-value=${list.warningStatus}>
                <#if list.warningStatus=="00"><a class="greenTd">正常</a><#elseif list.warningStatus=="01"><a class="yellowTd">流量预警</a>
                <#elseif list.warningStatus=="02"><a class="redTd">流量超出</a> <#else>--</#if>
            </td>


        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>