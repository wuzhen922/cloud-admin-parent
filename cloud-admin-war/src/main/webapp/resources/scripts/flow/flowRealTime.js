/**
 * Created by wtw on 2018/1/18.
 */
var flowRealTime = new FlowRealTime();
Flow.flowRealTime = clone(flowRealTime);//继承

//查云平台与集成商数据回调
Flow.flowRealTime.queryRealDataCallBack = function(result){

    //流量概览
    var leaveFlowValue = result.monthRemainingTraffic.toFixed(2);
    var costFlowValue = result.monthTraffic.toFixed(2);//已用量
    var totalFlowValue = result.monthPackTraffic;//总量
    var flowSurveyObj;
    if(totalFlowValue===0){
        flowSurveyObj = {
            restDayOfMonth: result.restDayOfMonth,
            leaveFlowValue:0+'M',
            costFlowValue:0+'M',
            seriesData: [ {value:1, name:'已用0%'},0]
        };
    }
    else{
        costFlowValuePercentage = (((totalFlowValue-leaveFlowValue)/totalFlowValue)*100).toFixed(1)+'%';
        flowSurveyObj = {
            restDayOfMonth: result.restDayOfMonth,
            leaveFlowValue:leaveFlowValue+'M',
            costFlowValue:costFlowValue+'M',
            seriesData: [ {value:costFlowValue, name:'已用'+costFlowValuePercentage},leaveFlowValue]
        };
    }

    Flow.flowRealTime.flowSurvey(flowSurveyObj);
    //通用流量占比
    var FlowDistributionObj ={legendData:[],seriesData:[]};
    if(result.hasOwnProperty('packageNumberMapList') && result.packageNumberMapList.length>0){
        var packageNumberMapList = result.packageNumberMapList;
        var FlowDistributionLengend = [],FlowDistributionSeries=[];
        var totalNumber=0;//套餐总数
        for(var i=0;i<packageNumberMapList.length;i++){
            totalNumber +=packageNumberMapList[i].packNumber;
        }
        packageNumberMapList.forEach(function(val,k){
            var seriesObj = {value:null,name:''};
            var lengendPercentage = ((val.packNumber/totalNumber)*100).toFixed(1)+'%';
            var lengendData = val.packTraffic+ 'M('+lengendPercentage+')';
            seriesObj.value = val.packNumber;
            seriesObj.name = lengendData;
            FlowDistributionLengend.push(lengendData);
            FlowDistributionSeries.push(seriesObj);

        });
        FlowDistributionObj.legendData=FlowDistributionLengend;
        FlowDistributionObj.seriesData=FlowDistributionSeries;


        Flow.flowRealTime.FlowDistribution(FlowDistributionObj);
    }else{

        eye.contentShowAndHide('.rtuRealData','.companyAndIntegrator');
        $('.companyAndIntegrator').html('<div class="lackData">暂无数据</div>')
        //Flow.flowRealTime.FlowDistribution(FlowDistributionObj);
    }

    //流量日使用情况
    if(result.hasOwnProperty('dayTrafficDtoList') && result.dayTrafficDtoList.length>0){
        var dayTrafficDtoList = result.dayTrafficDtoList;
        var seriesData = [],xAxisData=[];
        dayTrafficDtoList.forEach(function(val,k){

            xAxisData.push(new Date(val.dayDate).getDate().toString());
            seriesData.push(val.dayTraffic);


        });

        var dailyFlowObj = {
            seriesData:seriesData,
            xAxisData:xAxisData
        };
        Flow.flowRealTime.dailyFlow(dailyFlowObj);
    }else{

        $('.bottomBarTable').html('<div class="lackData">暂无数据</div>')
    }





};
//实时数据rtu回调
Flow.flowRealTime.queryRealDataRtuCallBack= function(result){
    //流量概览
    var leaveFlowValue = result.monthRemainingTraffic.toFixed(2);
    var costFlowValue = result.monthTraffic.toFixed(2);//已用量
    var totalFlowValue = result.monthPackTraffic;//总量
    var flowSurveyObj;
    if(totalFlowValue===0){
        flowSurveyObj = {
            leaveFlowValue:0+'M',
            costFlowValue:0+'M',
            seriesData: [ {value:1, name:'已用100%'},0]
        };
    }
    else{
        costFlowValuePercentage = (((totalFlowValue-leaveFlowValue)/totalFlowValue)*100).toFixed(1)+'%';
        flowSurveyObj = {
            leaveFlowValue:leaveFlowValue+'M',
            costFlowValue:costFlowValue+'M',
            seriesData: [ {value:costFlowValue, name:'已用'+costFlowValuePercentage},leaveFlowValue]
        };
    }

    Flow.flowRealTime.flowSurvey(flowSurveyObj);

    //通用流量
    if(result.hasOwnProperty('packageUsageDtoList') && result.packageUsageDtoList.length>0){
        var packageNumberMapList = result.packageUsageDtoList;
        var packageArr=[];
        packageNumberMapList.forEach(function(val,k){
            var packageObj ={
                name:'',
                remainFlow:null,
                totalFlow:null,
                usedFlow:null
            };
            packageObj.name = val.packName;
            packageObj.remainFlow = val.packRemainingTraffic.toFixed(2);
            packageObj.totalFlow = val.packTraffic;
            packageObj.usedFlow = val.packUsedTraffic.toFixed(2);
            packageArr.push(packageObj);

        });
        var remainFlowObj = {
            allTotolFlow:totalFlowValue,
            allLeaveFlow:leaveFlowValue,
            list:packageArr

        };//单位M
        Flow.flowRealTime.remainFlow(remainFlowObj);
    }else{
        eye.contentShowAndHide('.companyAndIntegrator','.rtuRealData');
        $('.rtuRealData').html('<div class="lackData">暂无数据</div>')
    }
    //流量日使用情况
    if(result.hasOwnProperty('dayTrafficDtoList') && result.dayTrafficDtoList.length>0){
        var dayTrafficDtoList = result.dayTrafficDtoList;
        var seriesData = [],xAxisData=[];
        dayTrafficDtoList.forEach(function(val,k){
            xAxisData.push(new Date(val.dayDate).getDate().toString());
            seriesData.push(val.dayTraffic);



        });

        var dailyFlowObj = {
            seriesData:seriesData,
            xAxisData:xAxisData
        };
        Flow.flowRealTime.dailyFlow(dailyFlowObj);
    }else{

        $('.bottomBarTable').html('<div class="lackData">暂无数据</div>')
    }



};

//查实时数据
Flow.flowRealTime.selectedQueryRealTime=function(node){

        Flow.flowSurveyEchart = echarts.init(document.getElementById('realDataBarEchart1'));
    $('.realDataShow').addClass('none');

    if(node.key.indexOf(CloudPlatformTreeFirstChar)==0){
        //云平台
        Flow.flowRealTime.queryRealData('/flow/realFlow/systemRealTimeTraffic',{},Flow.flowRealTime.queryRealDataCallBack);

    }else if(node.key.indexOf(IntegratorTreeFirstChar)==0){
        //集成商
        var query2 = {
            id:null

        };
        query2.id = node.key;
         Flow.flowRealTime.queryRealData('/flow/realFlow/integratorRealTimeTraffic',query2,Flow.flowRealTime.queryRealDataCallBack);

    }else if(node.key.indexOf(RtuTreeFirstChar)==0){
        //RTU
        var query3 = {
            snNumber:null
        };
        query3.snNumber = node.data.snNumber;
        Flow.flowRealTime.queryRealData('/flow/realFlow/rtuRealTimeTraffic',query3,Flow.flowRealTime.queryRealDataRtuCallBack);


    }


    };


//初始化页面用

Flow.InitRealDataPage = {

    //树选第一个
    defaultTreeSelected :function (callback){
        $('#realDataTree').fancytree('getTree').visit(function(node){

            if(node.key.indexOf(CloudPlatformTreeFirstChar) == 0){

                node.setSelected(true);
                node.extraClasses = 'selected';
                $(node.span).addClass('selected');
                callback(node);
                return;
            }
        });

    },
    /**
     * 查树
     */
    queryTreeList:function(){
        var handleRequest = $.post(YHu.util.ctxPath('/flow/realFlow/queryTreeList'));
        handleRequest.done(function (jsonResult) {
            if (jsonResult.hasOwnProperty('data')&& jsonResult.data.success) {

                var resultList = jsonResult.data.list;

                if(!!resultList) {
                    //初始化树
                    EchartOption.tree.getInstance().initFanctree(resultList,'#realDataTree');
                    //根据预警状态加颜色
                    $('#realDataTree').fancytree('getTree').visit(function (node) {

                        if (!!node.data && !!node.data.status && node.data.warningStatus =='01') {

                            node.extraClasses = 'yellowTd';

                        }else if(!!node.data && !!node.data.status &&  node.data.warningStatus =='02'){
                            node.extraClasses = 'redTd';
                        }
                    });
                   //点树查数据
                    EchartOption.tree.getInstance().getTreeSelected('#realDataTree',Flow.flowRealTime.selectedQueryRealTime);
                   //默认选树第一个
                    Flow.InitRealDataPage.jumpRealDataPage(Flow.flowRealTime.selectedQueryRealTime);
                }

            } else {
                //暂无数据提示
                $('.realDataFlow').html('').append($('.blankNotice1').html());


            }
        });
    },
    jumpRealDataPage:function(callback) {
        if (sessionStorage.hasOwnProperty('realDataId') && (sessionStorage.realDataId != 'null')) {
            $('#realDataTree').fancytree('getTree').visit(function (node) {
                node.setExpanded();
                if (node.key == sessionStorage.realDataId) {
                    sessionStorage.realDataId = 'null';
                    node.setSelected(true);
                    node.extraClasses = 'selected';
                    $(node.span).addClass('selected');
                    callback(node);
                }
            });

        } else {
            //默认选中第一个
            Flow.InitRealDataPage.defaultTreeSelected(callback);
        }
    }


};


(function(){
    Flow.InitRealDataPage.queryTreeList();
    $('.thirdMenu').outerHeight($('.lastContainer').height());



})();


