/**
 * Created by wtw on 2018/1/18.
 */
//init

var FlowInit =  new Interface('FlowInit',['heightAuto','judgeStatus','initContent']);//接口

Flow.init = (function(){
    var queryWarning = {
        rtuName:'',

        pageNum: 1,
        pageSize: 10


    };
    //点击右侧展示切换
    function containerShowAndHide(element){
        $(element + ' li').each(function (i, data) {
            if ($(this).hasClass('active') && (i == 0 ||i == 1 )) {
                $('.detail:eq(0)').removeClass('none').siblings('div').addClass('none');
                if(i == 0){
                    eye.contentShowAndHide('.totalFlowContent','.realDataContent');
                    $('.realDataShow').addClass('none');
                    $('.bottomBarTable').empty();
                    $('#realDataTree').fancytree({
                        selected: false,
                        extraClasses: ''
                    });
                    defaultTreeSelected(Flow.flowRealTime.selectedQueryRealTime);


                }else{
                    eye.contentShowAndHide('.realDataContent','.totalFlowContent');
                    $('.totalShow').empty();
                    $('#realDataTree').fancytree({
                        selected: false,
                        extraClasses: ''
                    });
                    defaultTreeSelected(Flow.flowTotal.selectedQueryTotal);

                }

            }else if($(this).hasClass('active') && (i == 2 )){
                $('.detail:eq(1)').removeClass('none').siblings('div').addClass('none');
            }

        });

    }

    /**
     * 查树
     */
    function  queryTreeList(){
        var handleRequest = $.post(YHu.util.ctxPath('/flow/realFlow/queryTreeList'));
        handleRequest.done(function (jsonResult) {
            if (jsonResult.hasOwnProperty('data')&& jsonResult.data.success) {

                var resultList = jsonResult.data.list;

                if(!!resultList) {
                    //初始化树
                    EchartOption.tree.getInstance().initFanctree(resultList,'#realDataTree');
                    EchartOption.tree.getInstance().getTreeSelected('#realDataTree',Flow.flowRealTime.selectedQueryRealTime,Flow.flowTotal.selectedQueryTotal);
                    jumpRealDataPage(Flow.flowRealTime.selectedQueryRealTime);
                }

            } else {
                //暂无数据提示
                $('.realDataFlow').html('').append($('.blankNotice1').html());


            }
        });
    }




    /**
     * 得到某月天数
     * @param year
     * @param month
     * @returns {number}
     */
    function mGetDate(year, month){
        var d = new Date(year, month, 0);
        return d.getDate();
    }

    /**
     * 时间控件赋初值
     */
    function initTimePickerValue(){
        var d = new Date();
        var s,y;

        function addzero(v) {if (v < 10) return '0' + v;return v.toString();}
        if(d.getMonth() == '0'){
            s = (d.getFullYear()-1).toString() + '-12';
            y = (d.getFullYear()-1).toString();
        }else{
            s = d.getFullYear().toString() + '-'+addzero(d.getMonth());
            y = (d.getFullYear()-1).toString();
        }
        document.getElementById('test0').value=y;
        document.getElementById('test1').value=s;
    }
    function jumpRealDataPage(callback) {
        if (sessionStorage.hasOwnProperty('warningId') && (sessionStorage.warningId == 'warning')) {
            $('.sencondMenu li:eq(2)').trigger('click');
            sessionStorage.warningId = 'null';

        }
        else if (sessionStorage.hasOwnProperty('realDataId') && (sessionStorage.realDataId != 'null')) {
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
            defaultTreeSelected(callback);
        }
    }
        /**
         * 默认选中第一个
         * @param node
         * @param callback
         */
    function defaultTreeSelected(callback){
        $('#realDataTree').fancytree('getTree').visit(function(node){

            if(node.key.indexOf(CloudPlatformTreeFirstChar) == 0){

                node.setSelected(true);
                node.extraClasses = 'selected';
                $(node.span).addClass('selected');
                callback(node);
            }
        });

    }

    function initEvent(){
        //年、月数据切换
        $('.totalChoice').on('click', 'span', function () {
            $(this).addClass('active').siblings('span').removeClass('active');
            $('.totalChoice span').each(function (i, data) {
                if ($(this).hasClass('active')) {
                    $('.totaltime div:eq(' + i + ')').removeClass('none').siblings('div').addClass('none');

                }

            });
            initTimePickerValue();//初始化时间控件
        });

       /* //用rtuName查流量阈值表
        $('#queryWarningName').on('blur',function(){
            Flow.init.queryWarningTable();//查预警表
        }).on('keyup',function(e){
            if(e.keyCode == '13'){
                $(this).trigger('blur');
            }
        })
*/

    }
    var init = {
        //高度自适应
        heightAuto:function(){
            eye.menuHeightAuto('.sencondMenu', '.thirdContainer');
            eye.resizeDiv('.thirdContainer ');

            eye.menuHeightAuto('.thirdMenu', '.sencondMenu');


        },


        judgeStatus:function() {
            //二级菜单切换内容

            $('.sencondMenu').on('click', 'li', function (e) {

                $(this).addClass('active').siblings('li').removeClass('active');
                containerShowAndHide('.sencondMenu');

                eye.menuHeightAuto('.sencondMenu', '.thirdContainer');

                eye.menuHeightAuto('.thirdMenu', '.sencondMenu');

            });

        },
        //查询、初始化页面,门面模式
        initContent:function(){
           // queryTreeList();//查树
         //   Flow.init.queryWarningTable();//查预警表

            initEvent();//初始化时间切换事件
            initTimePickerValue()//设置时间插件初始值

        },

        // 初始化年月时间选择控件
        initTimePicker:function(){
            WdatePicker({dateFmt:'yyyy-MM',maxDate:'%y-#{%M-1}'});
        },
        initTimePickerYear:function(){
            if(new Date().getMonth() == '0') {
                WdatePicker({dateFmt: 'yyyy-MM', maxDate: '#{%y-1}'});
            }else{
                WdatePicker({dateFmt: 'yyyy-MM', maxDate: '%y'});
            }

        },

       /* /!**
         * 查预警数据
         *!/
        queryWarningTable:function(){
            queryWarning.rtuName = $("#queryWarningName").val();

            YHu.ui.tableLoad("#flowWarningTable", YHu.util
                .ctxPath("/flow/flowWarning/queryFlowListByPage"), queryWarning);

        },*/

        /*/!**
         * 分页回调
         * @param event
         * @param pageSize
         *!/
        pageHandler:function(event, pageSize){
            event.preventDefault();
            queryWarning.pageNum = event.target.title;
            if (pageSize != null && typeof (pageSize) != "undefined") {
                queryWarning.pageSize = pageSize;
                queryWarning.pageNum = 1;
            }
            Flow.init.queryWarningTable();
        },*/
        //当月剩余天数
        remainedDay:function(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth()+1;
            var d = new Date(year, month, 0);
            return d.getDate()-date.getDate();
        }

    };
    Interface.ensureImplements(init,FlowInit);//实现接口
    return   init;



})();

/*
(function(){

   // Flow.init.heightAuto();

    Flow.init.initContent();



})();*/
