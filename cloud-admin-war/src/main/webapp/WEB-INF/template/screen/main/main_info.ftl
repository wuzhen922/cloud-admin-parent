<#include "/layout/baseLayout.ftl">
<#macro html_title>首页</#macro>
<#macro html_body>

<div class="wrapper wrapper-content  animated fadeInRight">
<div class="row">
<div class="col-sm-12">
<div class="ibox ">
    <div class="ibox-title">
        <h5>首页</h5>
    </div>
    <div class="ibox-content">



        <div class="panel panel-default systemDefault">

                <p class="panel-title ">系统概述</p>

            <div class="panel-body totalDataStatistics">

            </div>
        </div>
        <div class="flowWarning realTimeTable">
            <p ><span class="icon-gaojing iconfont "></span>流量预警<span class="more" id="warningMore">更多<span class="iconfont icon-jiantou1"></span></span></p>

            <div  id="flowWarningTable">

                <!-- 表格-->
            </div>

        </div>
        <div class="realDataFlow  clearfix">


        <#--实时-->
            <div class='clearfix realDataContent'>

                    <div class="realTimeTable leftbarTable clearfix">
                        <p><span class="icon-xuniwangqia81 iconfont "></span>网卡概览<span class="more" id="realDataWarning">更多<span class="iconfont icon-jiantou1"></span></span></p>
                       <div class="totalPieEchart">
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie1" class="pieEchart"></div>


                            </div>
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie2" class="pieEchart"></div>


                            </div>
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie3" class="pieEchart"></div>


                            </div>
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie4" class="pieEchart"></div>


                            </div>
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie5" class="pieEchart"></div>


                            </div>
                            <div class="leftbarTableDetail echartPie">
                                <div id="warningPie6" class="pieEchart"></div>


                            </div>
                        </div>
                    </div>
                    <div class="realTimeTable rightbarTable" >
                        <p><span  class="icon-paimingkaoqian-01 iconfont"></span>流量排名</p>
                        <div class="rankDiv">
                            <div id="rankBarEchart1" class="rankBar" ></div>
                            <div id="rankBarEchart2" class="rankBar"></div>
                     </div>
                </div>


            </div>



    </div>

    <div class="blankNotice1 none">
        <p >
            <span class="iconfont icon-fail"></span >
            暂无数据
        </p>
    </div>

</div>
</div>
</div>
</div>

<#--放集成商的状态-->
<input id="integratorSta" name="integratorSta" class="none" value="${integratorStatus}"/>
<input id="integratorId" name="integratorId" class="none" value="${integratorId}"/>




</#macro>
<#macro html_foot>


    <@p.js src="third/echarts/echarts.min.js"/>

    <@p.js src="components/echartOption.js"/>

    <@p.js src="main/index.js"/>

</#macro>
<#macro html_head>
    <@p.css src="init.css"/>

    <@p.css src="main.css"/>
    <@p.css src="index.css"/>


</#macro>
