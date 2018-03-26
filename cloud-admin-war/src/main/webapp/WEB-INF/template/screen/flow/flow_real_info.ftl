<#include "/layout/baseLayout.ftl">
<#macro html_title>实时流量</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>实时流量</h5>
                </div>
                <div class="ibox-content" style="padding-top: 0;">


                    <div class='right-content'>

                        <div class="realDataFlow detail clearfix">

                            <div class="thirdMenu">
                                <div id="realDataTree" class="panel-body fancytree-colorize-hover fancytree-fade-expander"></div>

                            </div>
                        <#--实时-->
                            <div class='right-content lastContainer clearfix realDataContent'>
                                <p style="border-bottom:none">实时流量</p>
                                <div class="echartAll">
                                    <div class="bottomEchart clearfix " >
                                        <div class="realTimeTable leftbarTable clearfix">
                                            <div class="leftbarTableDetail none ">
                                                <p>流量概览</p>
                                                <div class="tableContent">
                                                    <p>距离月底还剩
                                                        <span class="red remainedDay">1</span>天
                                                        <span class="middle">本月已用：<span class="blue costFlowValue">1G</span> </span>

                                                    </p>
                                                    <div class="blue clearfix remainFlowContent">
                                                        <div class="leaveFlow"><p>本月剩余：</p><p class="leaveFlowValue">1.2G</p></div>
                                                      <div class="leaveEchart">   <div id="realDataBarEchart1" class="clearfix"></div>
                                                      </div>
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
                                              <#--  <p>通用流量
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
                                                </div>-->
                                            </div>
                                        </div>
                                    </div>
                                    <div class="realTimeTable barTable bottomBarTable" >
                                        <div class="realDataShow"></div>

                                    </div>

                                </div>

                            </div>
                        <#--统计-->

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
            </div>
        </div>
    </div>
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
    <@p.js src="flow/index.js"/>
   <#-- <@p.js src="flow/flowTotal.js"/>
    <@p.js src="flow/flowWarning.js"/>
    <@p.js src="flow/index.js"/>-->


</#macro>
<#macro html_head>
    <@p.css src="init.css"/>

    <@p.css src="main.css"/>
    <@p.css src="flow.css"/>

    <@p.css src="ui.fancytree.css"/>
    <@p.css src="third/slideWarning/ion.rangeSlider.css"/>
    <@p.css src="third/slideWarning/ion.rangeSlider.skinHTML5.css"/>
</#macro>