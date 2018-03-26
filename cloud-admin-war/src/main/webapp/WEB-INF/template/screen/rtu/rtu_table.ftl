<#include "/layout/tableDataLayout.ftl">
<@tabledata>
<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr class="thead">
        <th class="checkbox-t none"><input type="checkbox" id="allboxs" onclick="allcheck()">全选</th>
        <th>网关名称</th>
        <th>传输类型</th>
        <th>接入类型</th>
        <th>序列码</th>
    <!--    <th>运营商</th> -->
        <th>所属集成商</th>
        <th>订购套餐</th>
        <th>状态</th>
        <th>创建时间</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
    <tr><!-- 该行仅为控制隔行变色 --></tr>
        <#list rtuList as rtuList>
        <tr>
            <td id="id${rtuList_index}" class="none">${rtuList.id}</td>
            <td class="checkbox-t none">
                <#if rtuList.status=='00'><input type="checkbox" name="boxs" value="${rtuList.id}"/>
                </#if>
            </td>
            <td id="rtuName${rtuList_index}">${rtuList.rtuName}</td>
            <td id="rtuModel${rtuList_index}" class="none">${rtuList.rtuModel}</td>
            <td id="rtuTransType${rtuList_index}" value=${rtuList.rtuTransType}>

                    <#if rtuList.rtuTransType=='00'>4G
                    <#elseif rtuList.rtuTransType=='10'>NB-IoT
                    <#elseif rtuList.rtuTransType=='20'>北斗
                    <#elseif rtuList.rtuTransType=='30'>天通
                    </#if>
            </td>
            <td id="rtuAccessType${rtuList_index}" value=${rtuList.rtuAccessType}>
                    <#if rtuList.rtuAccessType=='10'>多通道
                    <#elseif rtuList.rtuAccessType=='99'>单通道
                    </#if>
            </td>
            <td id="machineCode${rtuList_index}" class="none">${rtuList.machineCode}</td>
            <td id="snNumber${rtuList_index}">${rtuList.snNumber}</td>
            <td id="operator${rtuList_index}" class="none" value=${rtuList.operator} >
                <#if rtuList.operator=='00'>中国移动
                <#elseif rtuList.operator=='01'>中国联通
                <#elseif rtuList.operator=='03'>中国电信
                </#if>
            </td>
            <td id="integratorId${rtuList_index}" class="none">${rtuList.integratorId}</td>
            <td id="company${rtuList_index}">${rtuList.company}</td>
            <td id="packName${rtuList_index}">${rtuList.packName}</td>
            <td id="status${rtuList_index}" value="${rtuList.status}">
                    <#if rtuList.status=='10'>正常
                    <#elseif rtuList.status=='20'>禁用
                    <#elseif rtuList.status=='00'>未激活
                    </#if>
            </td>
            <td id="createTime${rtuList_index}" ><#if rtuList.createTime??><@p.out value="${rtuList.createTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td id="lastUpdateTime${rtuList_index}" class="none"><#if rtuList.lastUpdateTime??><@p.out value="${rtuList.lastUpdateTime?string('yyyy-MM-dd HH:mm:ss')}" /></#if></td>
            <td>
                <a href="javascript:;" class="canteen-modify a-inline-block"  onclick="changeRtuStatus(${rtuList_index})"><i
                        class="fa fa-edit"></i>
                    <#if rtuList.status=='10'>禁用
                    <#elseif rtuList.status=='20'>启用
                    <#elseif rtuList.status=='00'>修改
                    </#if>
                </a>
                <a href="javascript:;" class="canteen-delete a-inline-block" style="padding-left: 0;" onclick="deleteRtu(${rtuList_index})">
                    <#if rtuList.status=='00'><i
                            class="fa fa-trash-o"></i>删除
                    </#if>
                </a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</@tabledata>
