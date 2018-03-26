/**
 * Created by wtw on 2018/1/21.
 */

var queryMainData = new Interface('queryMainData',['queryTotalDataStatistics','queryTable','queryPie','queryBarRtu','queryBarIntegrator']);
var mainShow =window.mainShow|| {};



var MainDataMethod = function(){

    Interface.ensureImplements(this,queryMainData);
};
MainDataMethod.prototype ={

    queryTotalDataStatistics : function (url) {
        var handleRequest = $.post(YHu.util.ctxPath('/system/countSystemTotalTraffic'));

        handleRequest.done(function (jsonResult) {
            YHu.ui.closeLoading();
            if (jsonResult.success) {

                var t = new Date().format("yyyy年MM月dd日 hh:mm:ss");

                var p = "<p style='font-size: 15px;text-indent:2em;'>"+"截至到" + t +"系统承载流量累计"+"<span style='color: red;'>"+jsonResult.data+"</span>"+"G"+"</p>";

                $('.totalDataStatistics').append(p);
            } else {
                YHu.ui.closeAllLayer();
                YHu.ui.alert(jsonResult.message);
            }
        });
    },

    queryTable:function(node,url,searchCondition){
        var queryWarning = {

            pageNum: 1,
            pageSize: 6
            };


        YHu.ui.tableLoad("#flowWarningTable", YHu.util
            .ctxPath("/system/queryFlowWarningValueListByPage"), queryWarning);
    },

    queryPie:function(url){
        var that = this;
        var handleRequest = $.post(YHu.util
         .ctxPath(url));
         handleRequest.done(function (jsonResult) {

         if (jsonResult.success) {

             if(jsonResult.hasOwnProperty('data') &&jsonResult.data.length>0 ){
                 var resultList = jsonResult.data;
                 for(var i = 0;i < resultList.length; i++){

                     var option = that.initPie();
                     option.title.text = resultList[i].integratorName;
                     $('#warningPie'+(i+1)).attr('data-id','I'+resultList[i].integratorId);//给每个实例化echart加唯一标识，与台帐实时树对应

                     option.legend.data = [
                         '流量超出 '+resultList[i].exhaustSum+'('+(resultList[i].exhaustPercentage*100).toFixed(1)+'%)',
                         '流量预警 '+resultList[i].warningSum+'('+(resultList[i].warningPercentage*100).toFixed(1)+'%)',
                         '正常 '+resultList[i].normalSum+'('+(resultList[i].normalPercentage*100).toFixed(1)+'%)'];
                     option.series.data=[
                         {value:resultList[i].exhaustSum, name: '流量超出 '+resultList[i].exhaustSum+'('+(resultList[i].exhaustPercentage*100).toFixed(1)+'%)'},
                         {value:resultList[i].warningSum, name:  '流量预警 '+resultList[i].warningSum+'('+(resultList[i].warningPercentage*100).toFixed(1)+'%)'},
                         {value:resultList[i].normalSum, name:  '正常 '+resultList[i].normalSum+'('+(resultList[i].normalPercentage*100).toFixed(1)+'%)'}
                     ];

                     that['warningPie'+(i+1)].setOption( option);
                 }
             }

         } else {

         YHu.ui.alert(jsonResult.message);
         }
         });

    },
    initPie:function(){
        var option = EchartOption.option.pieOption();

        option.title = {
            text:'',
            top:5,
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },

            x: 'center',

        };
        option.legend ={
            orient: 'vertical',
           // x: 'right',
            type: 'scroll',
            itemHeight:12,
            itemWidth:16,
            right:-5,

            top:50,
            textStyle:{
                fontSize:12,
                fontWeight:'normal',
                color: '#676a6c'
            },
            data:[]
        };
        option.series={
            name: '',
            type: 'pie',
            center:['28%','56%'],
            radius : ['32%', '44%'],


            label: {
                normal: {
                    show: true,
                    formatter:'{c}'

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

            data:   []
        };
        return option;
    },
    queryBarRtu:function(url){
        var that = this;
        var handleRequest = $.post(YHu.util
         .ctxPath(url));
         handleRequest.done(function (jsonResult) {

         if (jsonResult.success) {

             if(jsonResult.hasOwnProperty('data') &&jsonResult.data.length>0 ){
                 var resultList = jsonResult.data;
                 var option1 = that.initBar();
                 var arr=[];
                 for(var i =0;i<resultList.length;i++) {

                   var arrObj = {day:'',value:''};

                     arrObj.day = resultList[i].rtuName;
                     arrObj.value = resultList[i].monthTraffic.toFixed(3);
                     arr.push(arrObj);

                 }
                 var obj = that.barDataMatch(arr);

                 option1.title.text = '当月采集终端流量使用排名';
                 option1.yAxis.data = obj.rankDay;
                 option1.series[0].data = obj.dataShadow;
                 option1.series[1].data = obj.rankValue;

                 that.rankEchart1.setOption(option1);
             }

         } else {

         YHu.ui.alert(jsonResult.message);
         }
         });
    },
    queryBarIntegrator:function(url){
        var that = this;
        var handleRequest = $.post(YHu.util
         .ctxPath(url));
         handleRequest.done(function (jsonResult) {

         if (jsonResult.success) {

             if (jsonResult.hasOwnProperty('data') && jsonResult.data.length > 0) {

                 var resultList = jsonResult.data;
                 var option1 = that.initBar();
                 var arr = [];

                 for (var i = 0; i < resultList.length; i++) {

                     var arrobj = {day:'', value:''};

                     arrobj.day = resultList[i].integratorName;
                     arrobj.value = resultList[i].monthTraffic.toFixed(3);
                     arr.push(arrobj);
                 }

                 var  obj = that.barDataMatch(arr);

                 option1.title.text ='当月集成商企业流量使用排名';
                 option1.yAxis.data =obj.rankDay;
                 option1.series[0].data =obj.dataShadow;
                 option1.series[1].data =obj.rankValue;

                 that.rankEchart2.setOption( option1);

             }

         } else {

         YHu.ui.alert(jsonResult.message);
         }
         });
    },


    barDataMatch:function(arr){

        arr.sort(rank('value'));//倒序排序
        var rankDay =[];//天
        var rankValue = [];//value
        var dataShadow = new Array(rankValue.length);//阴影
        var obj = {
            arr:arr,
            rankDay:rankDay,
            rankValue:rankValue,
            dataShadow:dataShadow
        };

        var length = obj.arr.length -1;

        obj.arr.forEach(function(val,key){
            obj.rankDay.push(val.day);
            obj.rankValue.push(val.value);
            if(arr[length].value == 0){//传来最大值为0
                obj.dataShadow.push(10);//阴影不为0
            }else{
                obj.dataShadow.push(obj.arr[length].value);
            }



        });

        return obj;


    },
    initBar:function(){



        var option = EchartOption.option.rankBarOption();
        option.title = {
            text:'',
            top:10,
            left: 'center',
            textStyle:{
                fontWeight:'bold',
                color: '#676a6c',
                fontSize:14
            },

            x: 'center'

        };


      return option;

    }




};
var mainData = new MainDataMethod();


mainShow= clone(mainData);

mainShow.initEvent = function(){
    $('#realDataWarning').on('click',function(){
        //跳台帐实时
        var li = window.parent.$('#side-menu').children('li:last-child').children('ul').children('li:first-child').children('a');

        li.trigger('click');



    });
    $('#warningMore').on('click',function(){
        //跳预警

        var li1 = window.parent.$('#side-menu').children('li:last-child').children('ul').children('li:last-child').children('a');

        li1.trigger('click');


    });
    $('.pieEchart').on('click',function(){
        //跳台帐实时
        sessionStorage.realDataId = $(this).attr('data-id');
        var li2 = window.parent.$('#side-menu').children('li:last-child').children('ul').children('li:first-child').children('a');
        li2.trigger('click');//返回子页面



    });
};

mainShow.searchData=(function(){
    mainData.rankEchart1 = echarts.init(document.getElementById('rankBarEchart1'));
    mainData.rankEchart2 = echarts.init(document.getElementById('rankBarEchart2'));
    mainData.warningPie1 = echarts.init(document.getElementById('warningPie1'));
    mainData.warningPie2 = echarts.init(document.getElementById('warningPie2'));
    mainData.warningPie3 = echarts.init(document.getElementById('warningPie3'));
    mainData.warningPie4 = echarts.init(document.getElementById('warningPie4'));
    mainData.warningPie5 = echarts.init(document.getElementById('warningPie5'));
    mainData.warningPie6 = echarts.init(document.getElementById('warningPie6'));
    mainData.queryTotalDataStatistics();
    mainShow.queryTable();
    mainShow.queryPie('/system/selectIntegratorByRtuWarningStatus');  //url,网卡预览
    mainShow.queryBarRtu('/system/rankRtuByMonthTraffic');   //url,网卡排名
    mainShow.queryBarIntegrator('/system/rankIntegratorByMonthTraffic');  //url,集成商排名
    mainShow.initEvent();

})();





