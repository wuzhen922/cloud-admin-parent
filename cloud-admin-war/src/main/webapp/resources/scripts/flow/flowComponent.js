/**
 * Created by wtw on 2018/1/24.
 */
var queryRealTimeData = new Interface('queryRealTimeData',['flowSurvey','FlowDistribution','dailyFlow','remainFlow']);
var queryTotalData = new Interface('queryTotalData',['flowRatio','flowRankYear','FlowDistributionMonth','FlowDistributionYear','flowRankMonth']);

var FlowRealTime = function(){

    Interface.ensureImplements(this,queryRealTimeData);
};
FlowRealTime.prototype ={

    initPage:function(){
        $('.realDataShow').addClass('none');
        $('.bottomBarTable').empty();


    },

    queryRealData:function(url,searchCondition,callback){
        var handleRequest = $.post(YHu.util
            .ctxPath(url), searchCondition);
        handleRequest.done(function (jsonResult) {

            if (jsonResult.success) {
                callback(jsonResult.data);


            } else {

                YHu.ui.alert(jsonResult.message);
            }
        });
    },

    //流量概况
    flowSurvey:function(obj){
        var that = this;

        $('.leftbarTableDetail').removeClass('none');

        $('.leaveFlowValue').text(obj.leaveFlowValue);
        $('.costFlowValue').text(obj.costFlowValue);
     //   $('.remainedDay').text( Flow.init.remainedDay());//剩余天数
        $('.remainedDay').text( obj.restDayOfMonth);//剩余天数
        //塞数据
        that.initFlowSurveyEchart.call(that,obj);
    },
    //通用流量占比圆形图
    FlowDistribution:function(obj){
        var that = this;

        eye.contentShowAndHide('.rtuRealData','.companyAndIntegrator');
        $('.companyAndIntegrator').html('').append(' <p>通用流量占比</p><div id="echartFlowRatio" ></div>');
        that.flowRatioEchart = echarts.init(document.getElementById('echartFlowRatio'));
        var option = EchartOption.option.pieOption();

        option.legend ={
            orient: 'vertical',
            type: 'scroll',
            right:5,
            top:50,
            textStyle:{
                fontSize:13,
                fontWeight:'normal',
                color: '#556073'
            },
            data:obj.legendData
        };
        option.series={
            name: '',
            type: 'pie',
            center:['36%','50%'],
            radius : ['40%', '60%'],

            label: {
                normal: {
                    show: true,
                    formatter:'({d}%)'

                }
            },
            itemStyle: {
                normal: {
                    color: function (params) {

                        var colorList = [
                            '#F3647C', '#8E6EF5','#CE64F3', '#6E9DF5', '#8E9AAC',
                            '#64BFF3', '#64F3F2', '#64F3A7', '#F3ED64', '#EB9A44',
                            '#E9744B', '#F364B3', '#F4E001', '#F0805A', '#26C0C0'
                        ];
                        return colorList[params.dataIndex]
                    },

                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            },



            data: obj.seriesData
        };
        that.flowRatioEchart.setOption(option);




    },
    //rtu剩余流量
    remainFlow:function(obj){
        var that = this;

        eye.contentShowAndHide('.companyAndIntegrator','.rtuRealData');
        //把detailUsed节点拿过来动态加载，再生成占比
        //把detailUsed节点拿过来动态加载，再生成占比
        $('.rtuRealData').html('').append('<p>通用流量 <span>总流量: <span class="blue allTotolFlow"></span></span>'+
            ' <span>剩余: <span class="blue allLeaveFlow"></span></span> </p>'+
            ' <div class="packageDetailContent">');
        $('.allTotolFlow').text(obj.allTotolFlow+'MB');
        $('.allLeaveFlow').text(obj.allLeaveFlow+'MB');
        $('.packageDetailContent').html('');
        for(var i = 0;i<obj.list.length;i++){

            var content =
                '<div class="detailUsed">'+
                ' <div class="flowUsedDetail clearfix">'+
                ' <div class="leftDetail eachFlowName">'+obj.list[i].name+'</div>'+
                '<div class="rightDetail"><p class="blue ">剩余 '+obj.list[i].remainFlow+'M</p>' +
                '<p class="flowTotalDetail">总流量'+obj.list[i].totalFlow+'M</p>' +
                '</div>'+

                '</div>' +
                '<div class="detailUsedContent" id=detailUsedContent'+i+'>' +
                '<div class="usedLength" id= usedLength'+i+'>' +
                '</div></div>'+
                ' </div>'


            $('.packageDetailContent').append(content);
            //套餐占用比例
            var length = (obj.list[i].usedFlow/obj.list[i].totalFlow) * parseFloat($('#detailUsedContent'+i).width())+'px';
            $('#usedLength'+i).width(length);
        }
    },


    //每日流量统计
    dailyFlow:function(obj){
        var that = this;

        $('.bottomBarTable').html('').append(' <div id="realDataBarEchart3"></div>');
        Flow.dailyFlowEchart = echarts.init(document.getElementById('realDataBarEchart3'));


        var option = EchartOption.option.dayBarOption();
        option.title = {
            text:'流量日使用情况',
            left:18,
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },
        };
        option.xAxis.data=obj.xAxisData;

        option.series={
            name: '',
            type: 'bar',

            barMaxWidth:'40',
            itemStyle: {
                normal: {
                    color: '#06a1ff'

                },
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }

            },



            data:   obj.seriesData

        };
        Flow.dailyFlowEchart.setOption(option);



    },
    //初始化流量统计echart
    initFlowSurveyEchart:function(obj){
        var that = this;
        var option = EchartOption.option.pieOption();

        option.series={
            name: '',
            type: 'pie',
            center:['60%','50%'],
            radius: ['60%', '80%'],
            avoidLabelOverlap: false,
            label: {
                normal: {
                    show: true,
                    position: 'center',
                    textStyle: {
                        fontSize: '14',
                        fontWeight: 'normal',
                        color: '#06a1ff',

                    },
                }
            },
            itemStyle: {
                normal: {
                    color: function (params) {

                        var colorList = [
                            '#06a1ff', '#EBE9E9', '#6E9DF5', '#8E9AAC', '#8E9AAC',
                            '#64BFF3', '#64F3F2', '#64F3A7', '#F3ED64', '#EB9A44',
                            '#E9744B', '#F364B3', '#F4E001', '#F0805A', '#26C0C0'
                        ];
                        return colorList[params.dataIndex]
                    },

                }

            },

            labelLine: {
                normal: {
                    show: false
                }
            },
            data: obj.seriesData

        };
        Flow.flowSurveyEchart.setOption(option);


    }


};






var FlowTotal = function(){

    Interface.ensureImplements(this,queryTotalData);
};
FlowTotal.prototype ={

    queryTotalData:function(url,searchCondition,callback){
        var that = this;
        var handleRequest = $.post(YHu.util
            .ctxPath(url), searchCondition);
        handleRequest.done(function (jsonResult) {

            if (jsonResult.success) {
                callback.call(that,jsonResult.data) ;


            } else {

                YHu.ui.alert(jsonResult.message);
            }
        });
    },

    //流量外、流量内占比
    flowRatio:function(obj){
        var that = this;


        $('.rightTotalBar').removeClass('none').html('').append(' <p>套餐内及套餐外流量占比</p><div id="totalEchart3" ></div>');
        that.flowRatioTotalEchart = echarts.init(document.getElementById('totalEchart3'));
        var option = EchartOption.option.pieOption();

        option.legend ={
            orient: 'vertical',
            // x: 'right',
            type: 'scroll',

            right:0,
            top:60,
            data:obj.legendData
        };
        option.series={
            name: '',
            type: 'pie',
            center:['32%','50%'],
            radius : ['40%', '60%'],
            label: {
                normal: {
                    show: true,
                    formatter:'({c}M)'

                }
            },
            itemStyle: {
                normal: {
                    color: function (params) {

                        var colorList = [
                            '#50B1ED', '#50EDC3'
                        ];
                        return colorList[params.dataIndex]
                    },

                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            },



            data:  obj.seriesData

        };
        that.flowRatioTotalEchart.setOption(option);



    },

    //月流量分布
    FlowDistributionMonth:function(obj){
        var that = this;

        $('.totalEchartDistribution').removeClass('none').html('').append(' <div  id="totalEchartMonth"></div>');
        that.flowDistributionMonthEchart = echarts.init(document.getElementById('totalEchartMonth'));
        var option = EchartOption.option.dayBarOption();
        var dataArr = $('#test1').val().split('-');
        var title = dataArr[0]+'年度'+dataArr[1]+'月流量使用情况';

        option.title = {
            text:title,
            left:18,
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },
        };

        option.xAxis.data =obj.xAxisData;
        option.legend = {};
        option.series=[{
            name: '',
            type: 'bar',
            barMaxWidth:'40',

            itemStyle: {
                normal: {
                    color: '#06a1ff'

                },
                emphasis: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                }

            },



            data:  obj.seriesData

        }];



        that.flowDistributionMonthEchart.setOption(option);




    },
    //月流量分布
    FlowDistributionYear:function(obj){
        var that = this;

        $('.totalEchartDistribution').removeClass('none').html('').append(' <div  id="totalEchart1"></div>');
        that.flowDistributionYearEchart = echarts.init(document.getElementById('totalEchart1'));
        var option = EchartOption.option.monthBarOption();
        var legendData = ['套餐内流量','套餐外流量'];
        var title = $('#test0').val()+'年度流量使用情况';
        option.legend ={

            right:0,

            data:legendData
        };
        option.title = {
            text:title,
            left:18,
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },
        };
        option.xAxis.data =obj.xAxisArr;
        option.xAxis.type='category';
        option.series=[
            {
                name: '套餐内流量',
                type: 'bar',
                stack: '总量',
                barMaxWidth:'40',
                itemStyle: {
                    normal: {
                        color:'#50B1ED',
                    }
                },

                label: {
                    normal: {
                        show: true,
                        position: 'insideTop'

                    }
                },
                data: obj.fixedFlowArr
            },
            {
                name: '套餐外流量',
                type: 'bar',
                barMaxWidth:'40',
                stack: '总量',
                itemStyle: {
                    normal: {
                        color:'#50EDC3',
                    }
                },

                label: {
                    normal: {
                        show: true,
                        position: 'top'
                        //  position: 'insideTop'
                    }
                },
                data: obj.extraFlowArr
            }];

        that.flowDistributionYearEchart.setOption(option);




    },
    //流量排名月
    flowRankMonth:function(obj){
        var that = this;

        //传过来的数据
        var arr =obj.arr;
        arr.sort(rank('value'));//倒序排序
        var rankDay =[];//天
        var rankValue = [];//value
        var dataShadow = new Array(rankValue.length);//阴影
        var length = arr.length -1;
        arr.forEach(function(val,key){
            rankDay.push(val.day);
            rankValue.push(val.value);
            if(arr[length].value == 0){//传来最大值为0
                dataShadow.push(10);//阴影不为0
            }else{
                dataShadow.push(arr[length].value*1.25);
            }
        });


        $('.leftTotalBar').removeClass('none').html('').append(' <p>流量排名</p><div id="flowRankMonth" ></div>');
        that.flowRankMonthEchart = echarts.init(document.getElementById('flowRankMonth'));
        var option = EchartOption.option.rankBarOption();

        option.title = {
            text:'当月流量使用排名',
            left: 'center',
            top:5,
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },
        };
        if(arr[length].type == 'rtu'){
            //rtu
            option.series[1].label.normal.formatter ='{c}K';

        }else{
            option.series[1].label.normal.formatter ='{c}M';
        }
        option.yAxis.data =rankDay;
        option.series[0].data =dataShadow;
        option.series[1].data =rankValue;
        that.flowRankMonthEchart.setOption(option);


    },
    //流量占比
    flowRankYear:function(obj){
        var that = this;

        $('.leftTotalBar').removeClass('none').html('').append(' <p>流量占比</p><div id="totalEchart2" ></div>');
        that.flowRankYearEchart = echarts.init(document.getElementById('totalEchart2'));
        var option = EchartOption.option.pieOption();

        option.legend ={
            orient: 'vertical',
            type: 'scroll',
            right:4,
            top:4,
            textStyle:{
                fontSize:12,
                fontWeight:'normal',
                color: '#556073'
            },
            data:obj.legendData
        };

        option.series={
            name: '',
            type: 'pie',
            center:['30%','50%'],
            radius : ['40%', '60%'],
            label: {
                normal: {
                    show: true,
                    formatter:"({c}M)"

                }
            },
            itemStyle: {
                normal: {
                    color: function (params) {

                        var colorList = [
                            '#F3647C', '#8E6EF5', '#6E9DF5', '#8E9AAC', '#8E9AAC',
                            '#64BFF3', '#64F3F2', '#64F3A7', '#F3ED64', '#EB9A44',
                            '#E9744B', '#F364B3', '#F4E001', '#F0805A', '#26C0C0'
                        ];
                        return colorList[params.dataIndex]
                    },

                },
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            },



            data:   obj.seriesData

        };
        that.flowRankYearEchart.setOption(option);



    },

};