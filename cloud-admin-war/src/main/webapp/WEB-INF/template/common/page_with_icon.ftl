<#if page?? && (page.pages>0)>
<#-- 总页数 -->
    <#assign countPage = page.pages>
<#-- 当前页数 -->
    <#assign currentPage = page.currentPage>
<#-- 当前分页size -->
    <#assign pageSize = page.pageSize>
<#-- 如果请求是get,则为get的url -->
    <#assign pageUrl = page.requestUrl?replace("#pageSize",page.pageSize)>

<#-- 参数验证并赋值 -->
    <#assign headAndTailNum = 2>
    <#assign pageInterval = 2>

    <#assign firstPage = 1>

<#-- 分页地址设置函数 -->
    <#function getPageUrl pageIndex>
        <#assign requestUrl = pageUrl?replace("#currentPage",pageIndex)/>
        <#return requestUrl>
    </#function>
<#-- 返回分页标签 -->
    <#function getPageItem index>
        <#if currentPage == index>
            <#return "<a class='active' href='javaScript:void(0)'>${index}</a>">
        <#else>
            <#return "<a href='${getPageUrl(index)}' title='${index}'>${index}</a>">
        </#if>
    </#function>

<div id="page<@p.out value="${page.pageId}"/>" class="page clearfix">
    <div  style="float: right">

          <span class="page-span">
                <#-- 设置第一页、前一页 -->
                    <#if currentPage != 1>
                        <a class="page-pic pre fa fa-step-backward" href="${getPageUrl(firstPage)}" title="${firstPage}"></a>
                    <#else>
                        <a class="page-pic pre fa fa-step-backward disabled"></a>
                    </#if>
                    <#if currentPage == 1>
                        <a class="page-pic pre fa fa-backward disabled"></a>
                    <#else>
                        <a class="page-pic pre fa fa-backward" href="${getPageUrl(currentPage - 1)}" title="${currentPage - 1}"></a>
                    </#if>

                <#-- 如果总页数大于固定标签数量 -->
                    <#if (countPage > headAndTailNum*2+pageInterval*2+1) >
                        <#if (currentPage-pageInterval-1 <= headAndTailNum)>
                            <#list 1..1+pageInterval*2+headAndTailNum as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                            <a class="a-text">...</a>
                            <#list countPage-headAndTailNum+1..countPage as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                        <#elseif (currentPage+pageInterval+1 >= countPage-headAndTailNum)>
                            <#list 1..headAndTailNum as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                            <a class="a-text">...</a>
                            <#list countPage-2*pageInterval-headAndTailNum..countPage as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                        <#else>
                            <#list 1..headAndTailNum as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                            <a class="a-text">...</a>
                            <#list currentPage-pageInterval..currentPage+pageInterval as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                            <a class="a-text">...</a>
                            <#list countPage-headAndTailNum+1..countPage as index>
                                <#if (index > 0) && (index <= countPage)>
                                ${getPageItem(index)}
                                </#if>
                            </#list>
                        </#if>

                    <#-- 总页数小于标签固定页数 -->
                    <#else>
                        <#list 1..countPage as index>
                        ${getPageItem(index)}
                        </#list>
                    </#if>

                <#-- 设置后一页和最后一页 -->
                    <#if countPage != currentPage>
                        <a class="page-pic next fa fa-forward" href="${getPageUrl(currentPage + 1)}" title="${currentPage + 1}"></a>
                    <#else>
                        <a class="page-pic next fa fa-forward disabled"></a>
                    </#if>
                    <a class="page-pic next fa fa-step-forward" href="${getPageUrl(countPage)}" title="${countPage}"></a>
        </span>
    </div>
    <span style="border:none;float:left;color: #6D6D6D;height: 30px;line-height:30px">
        	<label class="w-73"></label>
    <#-- 选择展示条数后跳到第一页 -->
			<select class="pageSizeList" name="pageSizeList" style="width:50px;" title="1" value="${pageSize}">
				<option value="10" <#if pageSize==10>selected='selected'</#if>>10</option>
				<option value="20" <#if pageSize==20>selected='selected'</#if>>20</option>
				<option value="50" <#if pageSize==50>selected='selected'</#if>>50</option>
				<option value="100" <#if pageSize==100>selected='selected'</#if>>100</option>
			</select>

            <font style="margin:0 5px 0 0;"></font>
            <font><@p.out value="${(page.currentPage-1)*page.pageSize+1}"/></font>
            <font>-</font>
        <#if (page.currentPage*page.pageSize>page.count)>
            <font><@p.out value="${page.count}"/></font>
        <#else>
            <font><@p.out value="${page.currentPage*page.pageSize}"/></font>
        </#if>
            <font style="margin-left:5px;"></font>
            <font style="margin:0 5px 0 0;">共<font style="margin: 0 5px;"><@p.out value="${page.count}"/></font>条</font>
        </span>
</div>


    <@p.script>
    if(${loadFirst}){
    var page =  $("#page<@p.out value="${page.pageId}"/>");
    var pageLinks = page.find('a[title]');
    page.find('.pageSizeList').find("option[value='<@p.out value="${page.pageSize}" />']").attr("selected",true);
    pageLinks.bind("click",function(event){
    ${pageHandler}(jQuery.event.fix(event));
    });
    page.find('.pageSizeList').change(function(event){
    var pageSize = $(event.target).val();
    ${pageHandler}(jQuery.event.fix(event),pageSize);
    });
    }else{
    window.onload = function(){
    var page =  $("#page<@p.out value="${page.pageId}"/>");
    var pageLinks = page.find('a[title]');
    page.find('.pageSizeList').find("option[value='<@p.out value="${page.pageSize}" />']").attr("selected",true);
    pageLinks.bind("click",function(event){
    ${pageHandler}(jQuery.event.fix(event));
    });
    page.find('.pageSizeList').change(function(event){
    var pageSize = $(event.target).val();
    ${pageHandler}(jQuery.event.fix(event),pageSize);
    });
    };
    }
    </@p.script>

    <@p.style>
    .pages .a-text:hover{color: #444; text-decoration:none;}
    </@p.style>

</#if>

