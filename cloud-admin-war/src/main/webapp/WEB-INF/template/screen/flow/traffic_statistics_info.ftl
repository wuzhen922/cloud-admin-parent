<#include "/layout/baseLayout.ftl">
<#macro html_title>流量统计</#macro>
<#macro html_body>
<div class="wrapper wrapper-content  animated fadeInRight">
    <div class="row">
        <div class="col-sm-12">
            <div class="ibox ">
                <div class="ibox-title">
                    <h5>流量统计</h5>
                </div>
                <div class="ibox-content" style="padding-top:0">

                    <div class='right-content'>

                        <div class="realDataFlow detail clearfix">

                            <div class="thirdMenu">
                                <div id="realDataTree" class="panel-body fancytree-colorize-hover fancytree-fade-expander"></div>

                            </div>

                        <#--统计-->
                            <div class='right-content lastContainer totalFlowContent '>

                                <p>流量统计</p>
                                <div class="totalChoice"><span class="modifyBtn totalYear active">年统计数据</span><span class="modifyBtn totalMonth">月统计数据</span></div>
                                <div class="totaltime">
                                    <div class='form-group '>
                                        <label class=" col-md-3 col-sm-3" >

                                            请选择年份：
                                        </label>
                                        <input id="test0" onfocus = "Flow.init.initTimePickerYear()" class="Wdate loginInput form-control col-md-6 col-sm-6 "  placeholder="请选择年份"/>
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
                                <div class="echartAll">
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
    <@p.js src="flow/index.js"/>
    <@p.js src="flow/flowComponent.js"/>

    <@p.js src="flow/flowTotal.js"/>




</#macro>
<#macro html_head>
    <@p.css src="init.css"/>

    <@p.css src="main.css"/>
    <@p.css src="flow.css"/>

    <@p.css src="ui.fancytree.css"/>

</#macro>