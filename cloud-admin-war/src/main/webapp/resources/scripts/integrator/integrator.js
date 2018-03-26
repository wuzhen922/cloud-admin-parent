$("document").ready(function() {

	$("#search").click(function() {

		setCondition();
		queryIntegratorList();
	});

	setCondition();
	queryIntegratorList();
});

var queryIntegratorCondition = {
	pageNum : 1,
	pageSize : 10,
	userName : null,
    company:null,
    phone:null
};
var changeIntegratorCondition = {
	id:null,
    status:null,
    disabledReason:null
};

var setCondition = function() {
	queryIntegratorCondition.userName = $("#userName").val();
	queryIntegratorCondition.company = $("#company").val();
	queryIntegratorCondition.phone = $("#phone").val();
    queryIntegratorCondition.pageNum = "1";
    queryIntegratorCondition.pageSize = "10";

};

$("#clear").click(function() {
	clearCondition();
	setCondition();
	queryIntegratorList();
});

var clearCondition = function() {
	$("#userName").val('');
    $("#company").val('');
    $("#phone").val('');
    queryIntegratorCondition.pageNum = "1";
    queryIntegratorCondition.pageSize = "10";

};

//查询集成商列表
var queryIntegratorList = function() {

	YHu.ui.tableLoad("#IntegratorTable", YHu.util
			.ctxPath("/integrator/queryIntegratorList"), queryIntegratorCondition);
};

var pageHandler = function(event, pageSize) {
	event.preventDefault();
	queryIntegratorCondition.pageNum = event.target.title;
	if (pageSize != null && typeof (pageSize) != "undefined") {
		queryIntegratorCondition.pageSize = pageSize;
		queryIntegratorCondition.pageNum = 1;
	}
	queryIntegratorList();
};

//删除集成商
var deleteIntegrator = function(no) {

    var id = $("#id" + no).text();

	layer.open({
		type : 1,
		title : "删除集成商",
		area : [ '350px', '180px' ],
		content : $('#deleteLayer'),
		btn : [ "确定", "取消" ],
		yes : function() {
			YHu.ui.loading();
			var handleRequest = $.post(YHu.util
					.ctxPath('/integrator/deleteIntegrator'), {
				id : id
			});
			handleRequest.done(function(jsonResult) {
				YHu.ui.closeLoading();
				if (jsonResult.success) {
					YHu.ui.closeAllLayer();
					YHu.ui.alert("删除集成商成功", function() {
						YHu.ui.closeAllLayer();
                        queryIntegratorList();
					});
				} else {
					YHu.ui.closeAllLayer();
					YHu.ui.alert(jsonResult.message);
				}
			});
		},
		no : function() {
			YHu.ui.closeAllLayer();
		}
	});
};

//更改集成商状态，根据status进入禁用或者启用操作
var changeIntegratorStatus = function(no) {

	var id = $("#id" + no).text();
	var status = $("#status" + no).text();
    $("#disabledReason").val("");
    changeIntegratorCondition.id=id;
    changeIntegratorCondition.status=status;

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    //禁用集成商
    if("10"===status){
        layer.open({
            type : 1,
            title : "禁用集成商信息",
            area : [ '350px', '180px' ],
            content : $('#disabledLayer'),
            btn : [ "确定", "取消" ],
            yes : function() {
                YHu.ui.loading();

                changeIntegratorCondition.disabledReason = $("#disabledReason").val();
                var handleRequest = $.post(YHu.util
                    .ctxPath('/integrator/changeIntegratorStatus'), changeIntegratorCondition);
                handleRequest.done(function(jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("禁用集成商成功", function() {
                            YHu.ui.closeAllLayer();
                            queryIntegratorList();
                        });
                    } else {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert(jsonResult.message);
                    }
                });
            },
            no : function() {
                YHu.ui.closeAllLayer();
            }
        });
	}

	//启用集成商
    if("20"===status){
        layer.open({
            type : 1,
            title : "启用集成商",
            area : [ '350px', '180px' ],
            content : $('#enabledLayer'),
            btn : [ "确定", "取消" ],
            yes : function() {
                YHu.ui.loading();
                var handleRequest =$.post(YHu.util
                    .ctxPath('/integrator/changeIntegratorStatus'), changeIntegratorCondition);
                handleRequest.done(function(jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("启用集成商成功", function() {
                            YHu.ui.closeAllLayer();
                            queryIntegratorList();
                        });
                    } else {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert(jsonResult.message);
                    }
                });
            },
            no : function() {
                YHu.ui.closeAllLayer();
            }
        });
    }

};

/**
 * 导出设备信息
 */

var excelExport = function (no) {


    var id = $("#id" + no).text();

    var $form = $("#exportForm");

    $("#integratorId").val(id);
    $("#integratorName").val($("#company" + no).text());

    $form.attr("action", YHu.util.ctxPath("/integrator/exportExcel"));

    $form.submit();
};

