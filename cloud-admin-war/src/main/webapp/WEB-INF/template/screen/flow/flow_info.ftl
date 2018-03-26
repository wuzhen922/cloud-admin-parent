<#include "/layout/baseLayout.ftl">
<#macro html_title>网卡台帐</#macro>
<#macro html_body>

<div class="sencondContainer">

    <div class="leftMenu sencondMenu">
        <h4>网卡台帐</h4>
        <ul>

            <li class=" active">
                <a>实时流量</a>

            </li>

            <li>
                <a>流量统计</a>
            </li>

            <li>
                <a>流量预警</a>
            </li>

        </ul>
    </div>

    <div class='right-content thirdContainer'>

        <div class="realDataFlow detail clearfix">

            <div class="thirdMenu">
                <div id="realDataTree" class="panel-body fancytree-colorize-hover fancytree-fade-expander"></div>

            </div>
        <#--实时-->
            <div class='right-content lastContainer clearfix realDataContent'>
                <p>实时流量</p>
                <div class="bottomEchart clearfix " >
                    <div class="realTimeTable leftbarTable clearfix">
                        <div class="leftbarTableDetail none realDataShow">
                            <p>流量概览</p>
                            <div class="tableContent">
                                <p>距离月底还剩
                                    <span class="red remainedDay">1</span>天
                                    <span class="middle">本月已用：<span class="blue costFlowValue">1G</span> </span>

                                </p>
                                <div class="blue clearfix">
                                    <div class="leaveFlow"><p>本月剩余：</p><p class="leaveFlowValue">1.2G</p></div>
                                    <div id="realDataBarEchart1" class="clearfix"></div>
                                </div>
                                <div class="blue flowName clearfix"> 通用流量</div>
                            </div>
                        </div>



                    </div>
                    <div class="realTimeTable rightbarTable" >
                        <div class="companyAndIntegrator realDataShow">
                        <#--  <p>通用流量占比</p>-->


                        </div>

                        <div class="rtuRealData none realDataShow">
                            <p>通用流量
                                <span>总流量: <span class="blue allTotolFlow">1.05GB</span></span>
                                <span>剩余: <span class="blue allLeaveFlow">789MB</span></span>
                            </p>
                            <div class="packageDetailContent">
                                <div class="detailUsed">
                                    <div class="flowUsedDetail clearfix">
                                        <div class="leftDetail eachFlowName">通用流量包30元-套餐内通用流量</div>
                                        <div class="rightDetail"><p class="blue ">剩余200M</p><p class='flowTotalDetail'>总流量500M</p></div>

                                    </div>
                                    <div class="detailUsedContent"><div class="usedLength"></div></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="realTimeTable barTable bottomBarTable" >


                </div>

            </div>
        <#--统计-->
            <div class='right-content lastContainer totalFlowContent none'>

                <p>流量统计</p>
                <div class="totalChoice"><span class="modifyBtn totalYear active">年统计数据</span><span class="modifyBtn totalMonth">月统计数据</span></div>
                <div class="totaltime">
                    <div class='form-group '>
                        <label class=" col-md-3 col-sm-3" >

                            请选择年份：
                        </label>
                        <input id="test0" onfocus = "WdatePicker({dateFmt:'yyyy',maxDate:'%y'})" class="Wdate loginInput form-control col-md-6 col-sm-6 "  placeholder="请选择年份"/>
                        <button class="btn btn-sm btn-white searchBtn">查询</button>
                    </div>
                    <div class='form-group none'>
                        <label class=" col-md-3 col-sm-3" >

                            请选择年月：
                        </label>
                        <input id="test1" onfocus = 'Flow.init.initTimePicker()' class="Wdate loginInput form-control col-md-6 col-sm-6 "  placeholder="请选择年月"/>
                        <button class="btn btn-sm btn-white searchBtn ">查询</button>
                    </div>


                </div>
                <div class="realTimeTable barTable totalEchartDistribution totalShow">

                </div>
                <div class="bottomEchart" >
                    <div class="realTimeTable leftbarTable leftTotalBar totalShow">


                    </div>
                    <div class="realTimeTable rightbarTable rightTotalBar totalShow" >


                    </div>
                </div>

            </div>
        </div>

        <div class="flowWarning detail none">
            <p>流量预警</p>
            <div class="warningTable">
                <div class="query">
                  <span class="form-control" style="width:220px;display:inline-block">
                        <input placeholder="请输入网关名称" id="queryWarningName" >
                        <span class="iconfont icon-sousu" style="float:right"></span>
                        </span>
                </div>

                <div  id="flowWarningTable">

                    <!-- 表格-->
                </div>
            </div>

        </div>
    </div>

    <div class="blankNotice1 none">
        <div class="blankNoticeContent">
            <p >
                <span class="iconfont icon-fail"></span >
                暂无数据
            </p>
        </div>
    </div>

</div>



<#--放集成商的状态-->
<input id="integratorSta" name="integratorSta" class="none" value="${integratorStatus}"/>
<input id="integratorId" name="integratorId" class="none" value="${integratorId}"/>
<#--阈值设置-->
<div id="warningSetting" class="none">

    <input id="ex6" type="text" name="ex6">
    <span id="ex6CurrentSliderValLabel">当前值: <span id="ex6SliderVal">3</span></span>
</div>



</#macro>
<#macro html_foot>

    <@p.js src="third/jquery-ui.js"/>

    <@p.js src="third/echarts/echarts.min.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.dnd.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.edit.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.glyph.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.wide.js"/>
    <@p.js src="third/fancytree-master/src/jquery.fancytree.select.js"/>
    <@p.js src="third/slideWarning/js/ion.rangeSlider.min.js"/>
    <@p.js src="third/My97DatePicker/WdatePicker.js"/>
    <@p.js src="components/echartOption.js"/>
    <@p.js src="components/treeInit.js"/>

    <@p.js src="flow/flowComponent.js"/>
    <@p.js src="flow/flowRealTime.js"/>
    <@p.js src="flow/flowTotal.js"/>
    <@p.js src="flow/flowWarning.js"/>
    <@p.js src="flow/index.js"/>
</#macro>
<#macro html_head>
    <@p.css src="init.css"/>

    <@p.css src="main.css"/>
    <@p.css src="flow.css"/>

    <@p.css src="ui.fancytree.css"/>
    <@p.css src="third/slideWarning/ion.rangeSlider.css"/>
    <@p.css src="third/slideWarning/ion.rangeSlider.skinHTML5.css"/>
</#macro>
