/**
 * Created by wtw on 2018/1/18.
 */
Flow.warningSetting = (function(){

    var queryWarning = {
        rtuName:'',

        pageNum: 1,
        pageSize: 10


    };

    //用rtuName查流量阈值表
    $('#search').on('click',function(){
        Flow.warningSetting.initWarning().queryWarningTable();//查预警表
    }).on('keyup',function(e){
        if(e.keyCode == '13'){
            $(this).trigger('blur');
        }
    })
   /* var warnningObj;
    var warningInfo = {
        snNumber:null,
        warningNum:null
    };
    var treeInstance;*/
    var treeInstance;
    var constructor =function() {
        return{
          /*  warningSetting:function(id){
               var init =  this.initWarningSetting(id);
               if(init == false){
                   return;
               }

                layer.open({
                    type: 1,
                    title: "设置阈值",
                    area: ['400px','240px'],

                    shadeClose: false,
                    content: $('#warningSetting'),
                    btn: ["确定", "取消"],
                    yes: function () {
                        warningInfo.warningNum = parseFloat($('#ex6SliderVal').text()) *1024;
                        warningInfo.snNumber = $('#snNumber'+id).text();
                        YHu.ui.loading();

                        var handleRequest = $.post(YHu.util
                            .ctxPath('/flow/flowWarning/settingFlowWarning'), warningInfo);
                        handleRequest.done(function (jsonResult) {
                            YHu.ui.closeLoading();
                            if (jsonResult.success) {
                                YHu.ui.closeAllLayer();
                                YHu.ui.alert("设置成功", function () {
                                    YHu.ui.closeAllLayer();
                                    Flow.init.queryWarningTable();

                                });
                            } else {
                                YHu.ui.closeAllLayer();
                                YHu.ui.alert(jsonResult.message);
                            }
                        });
                    },
                    no: function () {
                        YHu.ui.closeAllLayer();
                    }
                });
            },
            initWarningSetting:function(id){
                var maxNum = parseFloat($('#packTraffic'+id).text());
                if (!$('#packTraffic'+id).text()){
                    layer.msg('未查到套餐值', {
                        icon:2,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    });
                    return false;

                }

                var currentNum =parseFloat((parseFloat($('#warningNum'+id).text())/1024).toFixed(2));
                $("#ex6SliderVal").text(currentNum+'M');
                try{
                    if(!!warnningObj){
                        warnningObj.destroy()
                    }
                }catch(e){
                }
                $('#ex6').ionRangeSlider({
                    type:'single',
                    min:0,
                    max:maxNum,
                    from:currentNum,
                    postfix: "M",
                    onChange:function(data) {
                        console.log(data);

                        $("#ex6SliderVal").text(data.from +'M');
                    }
                });
                warnningObj = $("#ex6").data("ionRangeSlider");
                warnningObj.update({
                    min: 0,
                    max: maxNum,
                    from: currentNum,
                    postfix: "M",
                    // etc.
                });
            }
*/
            queryWarningTable:function(){
                queryWarning.rtuName = $("#queryWarningName").val();

                YHu.ui.tableLoad("#flowWarningTable", YHu.util
                    .ctxPath("/flow/flowWarning/queryFlowListByPage"), queryWarning);

            },
            /**
             * 分页回调
             * @param event
             * @param pageSize
             */
            pageHandler:function(event, pageSize){
                event.preventDefault();
                queryWarning.pageNum = event.target.title;
                if (pageSize != null && typeof (pageSize) != "undefined") {
                    queryWarning.pageSize = pageSize;
                    queryWarning.pageNum = 1;
                }
                this.queryWarningTable();
            },


        }


    };
    return{
        initWarning:function(){
            if(!treeInstance){
                treeInstance = constructor();
            }
            return treeInstance;

        }
    }
})();

$("#clear").click(function() {
    clearCondition();

    Flow.warningSetting.initWarning().queryWarningTable();
});
var clearCondition = function() {
    $("#queryWarningName").val('');

};

$(function(){
    Flow.warningSetting.initWarning().queryWarningTable();

});
