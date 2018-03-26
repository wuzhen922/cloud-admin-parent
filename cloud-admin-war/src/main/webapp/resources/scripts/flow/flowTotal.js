/**
 * Created by wtw on 2018/1/18.
 */

var flowTotal = new FlowTotal();
Flow.flowTotal = clone(flowTotal);


/**
 * 桥接，集成商，公司，rtu年数据回调
 * @param result
 */
Flow.flowTotal.queryIntegratorYear=function(result){
    var that = this;
    if(result.hasOwnProperty('yearStatisticsList')&& result.yearStatisticsList.length>0){
        //套餐内外饼图
        var yearOutPack = parseFloat(result.yearOutPackTraffic.toFixed(1)) ;
        var yearInPack = result.yearUseTraffic - result.yearOutPackTraffic;
        var yearPack = parseFloat(yearInPack.toFixed(1)) ;
        var yearTotalFlow = result.yearUseTraffic;
        var yearOutPackPercentage ;
        var yearPackPercentage;
        if(yearTotalFlow === 0){
            yearOutPackPercentage = 50;
            yearPackPercentage = 50;
        }else {
            yearOutPackPercentage = (yearOutPack/(yearTotalFlow) *100).toFixed(1);
            yearPackPercentage = 100-yearOutPackPercentage;
        }



        var flowRatioObj = {
            legendData : ['套餐内流量（'+yearPackPercentage+'%）','套餐外流量（'+yearOutPackPercentage+'%）'],
            seriesData: [
                {value:yearPack, name: '套餐内流量（'+yearPackPercentage+'%）'},
                {value:yearOutPack, name: '套餐外流量（'+yearOutPackPercentage+'%）'}


            ]

        };
        that.flowRatio(flowRatioObj);

        //年度套餐内外详情

        var flowDistributionXArr=[],flowDistributionFixedYArr=[],flowDistributionExtraYArr=[];
        var flowRankYearLegend =[],flowRankYearSeries=[];
        var yearList =result.yearStatisticsList;
        var yearUseTraffic = result.yearUseTraffic;
        if(yearUseTraffic === 0){
            for(var i =0;i<yearList.length;i++){
                var ratio = (100/(yearList.length)).toFixed(1) +'%';
                var month = parseInt(yearList[i].queryMonth);
                var legendData = month+'月('+ratio+')';
                var seriesObj ={
                    value:null,
                    name:''
                };
                seriesObj.value = 0 ;
                seriesObj.name =legendData;
                flowDistributionXArr.push(month);
                flowDistributionFixedYArr.push(0);
                flowDistributionExtraYArr.push(0);
                flowRankYearLegend.push(legendData);
                flowRankYearSeries.push(seriesObj);

            }
        }else {
            for(var i =0;i<yearList.length;i++){
                var ratio = ((yearList[i].monthTraffic/yearUseTraffic)*100).toFixed(1) +'%';
                var month = parseInt(yearList[i].queryMonth);
                var legendData = month+'月('+ratio+')';
                var seriesObj ={
                    value:null,
                    name:''
                };
                seriesObj.value = parseFloat(yearList[i].monthTraffic.toFixed(1)) ;
                seriesObj.name =legendData;
                flowDistributionXArr.push(month);
                flowDistributionFixedYArr.push(parseFloat(yearList[i].monthInPackTraffic.toFixed(1)));
                flowDistributionExtraYArr.push(parseFloat(yearList[i].monthOutPackTraffic.toFixed(1)));
                flowRankYearLegend.push(legendData);
                flowRankYearSeries.push(seriesObj);

            }
        }






        var flowDistributionYearObj ={
            xAxisArr:flowDistributionXArr,
            fixedFlowArr:flowDistributionFixedYArr,
            extraFlowArr:flowDistributionExtraYArr

        };
        that.FlowDistributionYear(flowDistributionYearObj);


        //流量占比

        var  flowRankYearObj = {
            legendData :flowRankYearLegend,
            seriesData:flowRankYearSeries

        };




        that.flowRankYear(flowRankYearObj);


    }else{
        YHu.ui.alert(result.message);
    }
};

//集成商与企业月查询
Flow.flowTotal.queryIntegratorAndCompanyMonth =function(result) {
    var that = this;

    if (result.hasOwnProperty('monthStatisticsList') && result.monthStatisticsList.length > 0) {
        //套餐内外饼图
        var monthStatisticsList = result.monthStatisticsList;
        var monthOutPack = parseFloat(result.monthOutPackTraffic.toFixed(1));
        var monthInPack = result.monthUseTraffic - result.monthOutPackTraffic;
        var monthPack = parseFloat(monthInPack.toFixed(1));
        var monthTotalFlow = result.monthUseTraffic;

        var monthOutPackPercentage;
        var monthPackPercentage;
        if(monthTotalFlow === 0){
            monthOutPackPercentage = 50;
            monthPackPercentage = 50;
        }else {
            monthOutPackPercentage = (monthOutPack / (monthTotalFlow) * 100).toFixed(1) ;
            monthPackPercentage = 100 - monthOutPackPercentage;
        }



        var flowRatioObj = {
            legendData: ['套餐内流量（' + monthPackPercentage + '%）', '套餐外流量（' + monthOutPackPercentage + '%）'],
            seriesData: [
                {value: monthPack, name: '套餐内流量（' + monthPackPercentage + '%）'},
                {value: monthOutPack, name: '套餐外流量（' + monthOutPackPercentage + '%）'}


            ]

        };

        //日套餐使用详情
        var seriesData = [];
        var xData=[];
        for (var i = 0; i < monthStatisticsList.length; i++) {


            xData.push(parseInt(monthStatisticsList[i].queryDay).toString());
            seriesData.push(monthStatisticsList[i].dayTraffic);

        }


        var FlowDistributionMonthMonth = {
            xAxisData:xData,
            seriesData: seriesData

        };
        that.FlowDistributionMonth(FlowDistributionMonthMonth);
        //流量排名
        var rtuMonthRankList = result.rtuMonthRankList;
        var rtuMonthRankArr = [];
        if (result.hasOwnProperty('rtuMonthRankList') && rtuMonthRankList.length > 0) {
            for (var j = 0; j < rtuMonthRankList.length; j++) {
                var objRank = {day: '', value: '',type:'other'};
                objRank.day = rtuMonthRankList[j].rtuName;
                objRank.value = rtuMonthRankList[j].rtuMonthTraffic;
                rtuMonthRankArr.push(objRank);

            }

            var flowRankMonthObj = {
                arr: rtuMonthRankArr
            };
            that.flowRankMonth(flowRankMonthObj);
        }else{
            $('.leftTotalBar').html('<div class="lackData">暂无数据</div>')
        }




        that.flowRatio(flowRatioObj);
    } else {
        YHu.ui.alert(result.message);
    }

};

//rtu月数据处理
Flow.flowTotal.queryRtuMonth=function(result){

    var that = this;

    if (result.hasOwnProperty('monthStatisticsList') && result.monthStatisticsList.length > 0) {
        //套餐内外饼图
        var monthStatisticsList = result.monthStatisticsList;
        var monthOutPack = parseFloat(result.monthOutPackTraffic.toFixed(1));
        var monthInPack = result.monthUseTraffic - result.monthOutPackTraffic;
        var monthPack = parseFloat(monthInPack.toFixed(1));
        var monthTotalFlow = result.monthUseTraffic;

        var monthOutPackPercentage;
        var monthPackPercentage;
        if(monthTotalFlow === 0){
            monthOutPackPercentage = 50;
            monthPackPercentage = 50;
        }else {
            monthOutPackPercentage = (monthOutPack / (monthTotalFlow) * 100).toFixed(1) ;
            monthPackPercentage = 100 - monthOutPackPercentage;
        }



        var flowRatioObj = {
            legendData: ['套餐内流量（' + monthPackPercentage + '%）', '套餐外流量（' + monthOutPackPercentage + '%）'],
            seriesData: [
                {value: monthPack, name: '套餐内流量（' + monthPackPercentage + '%）'},
                {value: monthOutPack, name: '套餐外流量（' + monthOutPackPercentage + '%）'}

            ]

        };

        //日套餐使用详情
        var seriesData = [],xData =[];
        for (var i = 0; i < monthStatisticsList.length; i++) {
            xData.push(parseInt(monthStatisticsList[i].queryDay).toString());
            seriesData.push(parseFloat(monthStatisticsList[i].dayTraffic.toFixed(1)));

        }


        var FlowDistributionMonthMonth = {
            xAxisData: xData,
            seriesData: seriesData

        };
        that.FlowDistributionMonth(FlowDistributionMonthMonth);


        //流量排名
        var rtuMonthRankList = result.rtuDayTrafficRankList;
        var rtuMonthRankArr = [];
        if (result.hasOwnProperty('rtuDayTrafficRankList') && rtuMonthRankList.length > 0) {
            for (var j = 0; j < rtuMonthRankList.length; j++) {
                var objRank = {day: '', value: '',type:'rtu'};
                objRank.day = parseInt(rtuMonthRankList[j].queryDay)+'日';
                objRank.value = parseFloat(rtuMonthRankList[j].dayTraffic.toFixed(1));
                rtuMonthRankArr.push(objRank);

            }

            var flowRankMonthObj = {
                arr: rtuMonthRankArr
            };
            that.flowRankMonth(flowRankMonthObj);
        }else{
            $('.leftTotalBar').html('<div class="lackData">暂无数据</div>')
        }




        that.flowRatio(flowRatioObj);
    } else {
        YHu.ui.alert(result.message);
    }

};

Flow.flowTotal.searchEchart = function(node){

    //查数据，将数据化为下列格式，直接塞函数展示
    var that = this;
    $('.totalShow').html('');

    if(node.key.indexOf('I')== 0){
        //集成商
        var queryIntegrator = {
            integratorId:node.key,
            accountDate:null,
        };
        if ($('.totalChoice span:eq(0)').hasClass('active')) {
            //年

            queryIntegrator.accountDate = $('#test0').val();
            var result1 =  that.queryTotalData('/flow/statistics/integratorYear',queryIntegrator,Flow.flowTotal.queryIntegratorYear);

        }else{
            //月
            queryIntegrator.accountDate = $('#test1').val();
            var result2 =  that.queryTotalData('/flow/statistics/integratorMonth',queryIntegrator,Flow.flowTotal.queryIntegratorAndCompanyMonth);



        }




    }else if(node.key.indexOf('P')== 0){
        //云平台
        var querySystem = {
            accountDate:null,
        }
        if ($('.totalChoice span:eq(0)').hasClass('active')) {
            //年

            querySystem.accountDate = $('#test0').val();
            var result3 =  that.queryTotalData('/flow/statistics/systemYear',querySystem,Flow.flowTotal.queryIntegratorYear);

        }else{
            //月
            querySystem.accountDate = $('#test1').val();
            var result4 =  that.queryTotalData('/flow/statistics/systemMonth',querySystem,Flow.flowTotal.queryIntegratorAndCompanyMonth);



        }


    }else if(node.key.indexOf('R')== 0){
        //RTU
        //企业
        var queryRtu = {
            snNumber:node.data.snNumber,
            accountDate:null,
        }
        if ($('.totalChoice span:eq(0)').hasClass('active')) {
            //年

            queryRtu.accountDate = $('#test0').val();
            var result5 =  that.queryTotalData('/flow/statistics/rtuYear',queryRtu,Flow.flowTotal.queryIntegratorYear);

        }else{
            //月
            queryRtu.accountDate = $('#test1').val();
            var result6 =  that.queryTotalData('/flow/statistics/rtuMonth',queryRtu,Flow.flowTotal.queryRtuMonth);



        }


    }



};


Flow.flowTotal.initEvent = function(node){
    var that = this;

    $('.searchBtn').on('click',function(){
        if(node.selected == true){
            that.searchEchart(node);//查询
        }

    })

};
//查统计数据
Flow.flowTotal.selectedQueryTotal=function(node){


    Flow.flowTotal.initEvent(node);//监听事件

};


//初始化页面用

Flow.InitTotalPage = {

    //树选第一个
    defaultTreeSelected :function (callback){
        $('#realDataTree').fancytree('getTree').visit(function(node){

            if(node.key.indexOf(CloudPlatformTreeFirstChar) == 0){

                node.setSelected(true);
                node.extraClasses = 'selected';
                $(node.span).addClass('selected');
                callback.call(this,node);
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
                    //点树查数据
                    EchartOption.tree.getInstance().getTreeSelected('#realDataTree',Flow.flowTotal.selectedQueryTotal);
                    //默认选树第一个
                    Flow.InitTotalPage.defaultTreeSelected(Flow.flowTotal.selectedQueryTotal);
                    $('.searchBtn').trigger('click');

                }

            } else {
                //暂无数据提示
                $('.realDataFlow').html('').append($('.blankNotice1').html());


            }
        });
    },



};


(function(){
    Flow.InitTotalPage.queryTreeList();
    Flow.init.initContent();

    $('.thirdMenu').outerHeight($('.lastContainer').height());

})();