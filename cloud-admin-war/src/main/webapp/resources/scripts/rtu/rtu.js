$("document").ready(function() {

	$("#search").click(function() {
		setCondition();
		queryRtuList();
	});

    queryIntegratorCompany();
	setCondition();
	queryRtuList();
});

var queryRtuCondition = {
	pageNum : 1,
	pageSize : 10,
	rtuName : null,
    rtuTransType : null,
    integratorId : null
};
var changeRtuCondition = {
	id:null,
    status:null
};

var rtuInfo = {
    id: null,
    rtuName: null,
    rtuModel: null,
    rtuTransType: null,
    rtuAccessType: null,
    machineCode:null,
    snNumber: null,
    operator:null,
    integratorId: null,
    integratorName: null,
    status:null

};

var setCondition = function() {
	queryRtuCondition.rtuName = $("#rtuName").val();
	queryRtuCondition.rtuTransType = $("#rtuTransType").val();
	queryRtuCondition.integratorId = $("#integratorId").val();
    queryRtuCondition.pageNum = "1";
    queryRtuCondition.pageSize = "10";

};

$("#clear").click(function() {
	clearCondition();
	setCondition();
	queryRtuList();
});

var clearCondition = function() {
	$("#rtuName").val('');
    $("#rtuTransType").val('');
    $("#integratorId").val('');
    queryRtuCondition.pageNum = "1";
    queryRtuCondition.pageSize = "10";

};


//查询RTU列表
var queryRtuList = function() {

        batchDeleteStatusThings();
	YHu.ui.tableLoad("#rtuTable", YHu.util
			.ctxPath("/rtu/queryRtuList"), queryRtuCondition);
};

var pageHandler = function(event, pageSize) {
	event.preventDefault();
	queryRtuCondition.pageNum = event.target.title;
	if (pageSize != null && typeof (pageSize) != "undefined") {
		queryRtuCondition.pageSize = pageSize;
		queryRtuCondition.pageNum = 1;
	}
	queryRtuList();
};




//checkbox全选
var allcheck = function () {

    var nn = $("#allboxs").is(":checked");

    if (nn == true) {
        var namebox = $("input[name='boxs']");
        for (var i = 0; i < namebox.length; i++) {
            namebox[i].checked = true;
        }
    }

    if (nn == false) {
        var namebox = $("input[name='boxs']");
        for (var i = 0; i < namebox.length; i++) {
            namebox[i].checked = false;
        }
    }

};

var modifyStatusThings = function () {



    $("input[type='checkbox']").removeAttr("checked");
    $(".checkbox-t").removeClass('none');

    $("#modifyButton").addClass('none');
    $("#batchDelete").removeClass('none');

};

var batchDeleteStatusThings = function () {
    $(".checkbox-t").addClass('none');
    $("#modifyButton").removeClass('none');
    $("#batchDelete").addClass('none');
};


/**
 * 批量删除操作
 */

$("#batchDelete").click( function () {

    // 判断是否至少选择一项
    var checkedNum = $("input[name='boxs']:checked").length;
    if(checkedNum == 0) {

        YHu.ui.alert("请选择至少一项！");
        return;
    }
    var batchDeleteRtuList = {
        rtuIdList:[]
    };

    // 批量选择

    layer.open({
        type: 1,
        title: "删除RTU",
        area: ['350px', '180px'],
        content: $('#batchDeleteRtuLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var checkedList = new Array();
            $("input[name='boxs']:checked").each(function () {
               //   checkedList.push($(this).val());
                var objectRtu = {
                    id:null
                };
                objectRtu.id = $(this).val();
                checkedList.push(objectRtu);

            });

            batchDeleteRtuList.rtuIdList = JSON.stringify(checkedList);


            var handleRequest = $.post(YHu.util.ctxPath('/rtu/batchDeleteRtu'), batchDeleteRtuList);

            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("批量删除RTU成功", function () {
                        YHu.ui.closeAllLayer();

                        batchDeleteStatusThings();

                        queryRtuList();

                    });
                } else {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert(jsonResult.message);
                }
            });
        },
        cancel: function () {


            YHu.ui.closeAllLayer();
            batchDeleteStatusThings();

        }
   });

});

//删除RTU
var deleteRtu = function(no) {

    var id = $("#id" + no).text();

    layer.open({
        type: 1,
        title: "删除RTU",
        area: ['350px', '180px'],
        content: $('#deleteLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var handleRequest = $.post(YHu.util
                .ctxPath('/rtu/deleteRtu'), {
                id: id
            });
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("删除RTU成功", function () {
                        YHu.ui.closeAllLayer();
                        queryRtuList();
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
};

//查询集成商企业，用于下拉框选择使用
var queryIntegratorCompany = function (element, value) {

    YHu.ui.loading();
    var handleRequest = $.post(YHu.util.ctxPath('/integrator/queryIntegratorCompany'));

    // $("#company").html('<option value="">请选择集成商企业</option>');

    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            YHu.ui.closeLoading();
            var data = jsonResult.data;

            var result = [];

            for (var i = 0; i < data.length; i++) {
                var resultSub = {id: null, text: null};
                resultSub.id = data[i].id;
                resultSub.text = data[i].company;
                result.push(resultSub);
            }

            $("#addIntegrator").empty();
            $("#addIntegrator").html('<option value="">请选择集成商企业</option>');
            $("#modifyIntegrator").empty();
            $("#modifyIntegrator").html('<option value="">请选择集成商企业</option>');

            for (var i = 0; i < data.length; i++) {
                $("#integratorId").append("<option value=" + result[i].id + ">" + result[i].text + "</option>");
                $("#addIntegrator").append("<option value=" + result[i].id + ">" + result[i].text + "</option>");
                $("#modifyIntegrator").append("<option value=" + result[i].id + ">" + result[i].text + "</option>");
            }

            if ('undefinded' != element) {
                $('#' + element).val(value);
            }

            $("#addIntegrator").select2({
                data: data,
                width: '100%',
                multiple: false,
                placeholder: '请选择业主企业',
                allowClear: true
            });

            $("#modifyIntegrator").select2({
                data: data,
                width: '100%',
                multiple: false,
                placeholder: '请选择业主企业',
                allowClear: true
            });

        } else {
            YHu.ui.closeLoading();
        }
    });

};

//更改RTU状态，根据status进入禁用或者启用
var changeRtuStatus = function(no) {

	var id = $("#id" + no).text();
	var status = $("#status" + no).attr('value');
    changeRtuCondition.id=id;
    changeRtuCondition.status=status;

    //禁用RTU
	if("10"===status){
        layer.open({
            type : 1,
            title : "禁用RTU信息",
            area : [ '350px', '180px' ],
            content : $('#disabledLayer'),
            btn : [ "确定", "取消" ],
            yes : function() {
                YHu.ui.loading();
                var handleRequest = $.post(YHu.util
                    .ctxPath('/rtu/changeRtuStatus'), changeRtuCondition);
                handleRequest.done(function(jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("禁用RTU成功", function() {
                            YHu.ui.closeAllLayer();
                            queryRtuList();
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

	//启用RTU
    if("20"===status){
        layer.open({
            type : 1,
            title : "启用RTU",
            area : [ '350px', '180px' ],
            content : $('#enabledLayer'),
            btn : [ "确定", "取消" ],
            yes : function() {
                YHu.ui.loading();
                var handleRequest =$.post(YHu.util
                    .ctxPath('/rtu/changeRtuStatus'), changeRtuCondition);
                handleRequest.done(function(jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("启用RTU成功", function() {
                            YHu.ui.closeAllLayer();
                            queryRtuList();
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

    if ("00" === status) {

        rtuInfo.id = $("#id" + no).text();
        var integratorId = $("#integratorId" + no).text();
        $('.form-group').removeClass('has-error');
        $('.form-group').removeClass('has-success');
        $('span.help-block').remove();

        var companyId = [];
        companyId.push(integratorId);
        $("#modifyIntegrator").val(companyId).trigger("change");
        $("#modifyRtuName").val($("#rtuName" + no).text());
        $("#modifyRtuModel").val($("#rtuModel" + no).text());
        $("#modifyMachineCode").val($("#machineCode"+no).text());
        $("#modifySnNumber").val($("#snNumber" + no).text());
        $("#modifyRtuAccessType").val($("#rtuAccessType" + no).attr('value'));
        $("#modifyRtuTransType").val($("#rtuTransType" + no).attr('value'));
        $("#modifyOperator").val($("#operator" + no).attr('value'));

        layer.open({
            type: 1,
            title: "修改网关信息",
            area: 'auto',
            maxWidth: '450px',
            shadeClose: false,
            content: $('#modifyLayer'),
            btn: ["确定", "取消"],
            yes: function () {

                if (!$('#modifyRtuForm').valid()) {
                    return 0;
                }

                var integratorArray = $("#modifyIntegrator").select2('data');

                rtuInfo.integratorId = integratorArray[0].id;
                rtuInfo.integratorName = integratorArray[0].text;

                rtuInfo.rtuName = $("#modifyRtuName").val();
                rtuInfo.rtuModel = $("#modifyRtuModel").val();
                rtuInfo.rtuTransType = $("#modifyRtuTransType").val();
                rtuInfo.rtuAccessType = $("#modifyRtuAccessType").val();
                rtuInfo.machineCode = $("#modifyMachineCode").val();
                rtuInfo.snNumber = $("#modifySnNumber").val();
                rtuInfo.operator = $("#modifyOperator").val();
                rtuInfo.status = status;


                YHu.ui.loading();

                var handleRequest = $.post(YHu.util.ctxPath('/rtu/updateRtuInfo'), rtuInfo);
                handleRequest.done(function (jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("修改网关信息成功", function () {
                            YHu.ui.closeAllLayer();
                            queryRtuList();
                            YHu.ui.closeAllLayer();

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

    }

};

//导入rtu信息
$("#excelImport").click(function () {

    $("#filePath").val('');
    layer.open({
        type: 1,
        title: "导入网关信息",
        area: ['400px', '220px'],
        content: $('#exportRtus'),
        btn: ["确认导入", "取消"],
        yes: function (index, layero) {
            //layer.load();
            YHu.ui.loading();
            uploadFileToServer("filePath", YHu.util.ctxPath('/rtu/importRtus'));
            layer.close(index);
            },
        no: function (index, layero) {
            layer.close(index);
        }
    });

});

var uploadFileToServer = function (elementId, url, callback) {


    $.ajaxFileUpload({
        url: url, // 需要链接到服务器地址
        secureuri: false,
        type: "POST",

        fileElementId: elementId, // 文件选择框的id属性
        data: {
            filePath: elementId
        },
        dataType: 'json', // 服务器返回的格式，可以是json
        success: function (result, textStatus) { // 相当于java中try语句块的用法
            YHu.ui.closeAllLayer();
            if (result.success) {
                YHu.ui.alert(result.message, function () {
                    YHu.ui.closeAllLayer();
                    queryRtuList();
                });
            } else {
                YHu.ui.closeAllLayer();
                $('.errorRtuMessage').text(result.message);
                $("#excelDownFailRtu").attr("href", YHu.util.ctxPath('/rtu/exportFailRtu'));
                layer.open({
                    type: 1,
                    title: "导出错误信息",
                    area: ['400px', '220px'],
                    content: $('#rtuExit'),
                    btn: ["确定", "取消"],
                    yes: function (index, layero) {

                        layer.close(index);
                        // querySensorList();


                    },
                    no: function (index, layero) {
                        layer.close(index);
                        // querySensorList();

                    }
                });
                queryRtuList();
            }

        },
        error: function (data, status, e) { // 相当于java中catch语句块的用法
            YHu.ui.closeAllLayer();
            YHu.ui.alert("文件上传失败");
        }
    });


};


var validExcelFile = function (filePath) {
    if ("" == filePath || typeof filePath == "undefined") {
        YHu.ui.closeAllLayer();
        YHu.ui.alert("文件名不能为空");
        return false;
    }
    if (/^.*?\.(xlsx)$/.test(filePath.toLowerCase())) {
        return true;
    } else {
        YHu.ui.closeAllLayer();
        YHu.ui.alert("只能导入Excel2007的文件");
        return false;
    }
};

var filePathOnchange = function (obj) {
    var objval = $(obj).val();
    var tmpFilePath = objval.substring(objval.lastIndexOf("\\") + 1,
        objval.length);
    tmpFilePath = (tmpFilePath.length > 30 ? (tmpFilePath).substring(0, 25)
        + "..." : tmpFilePath);
    $("#inputFileName").val(tmpFilePath);
    if (!validExcelFile(objval)) {
        return false;
    }
};
//RTU新增
var addRtu = function () {

    $("#addRtuName").val("");
    $("#addRtuModel").val("");
    $("#addRtuTransType").val("");
    $("#addRtuAccessType").val("");
    $("#addMachineCode").val("");
    $("#addSnNumber").val("");
    $("#addOperator").val("");
    $("#addIntegrator").val("").trigger("change");

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    layer.open({
        type: 1,
        title: "新增网关信息",
        area: 'auto',
        maxWidth: '550px',
        shadeClose: false,
        content: $('#addLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#addRtuForm').valid()) {
                return 0;
            }

            var integratorArray = $("#addIntegrator").select2('data');

            rtuInfo.integratorId = integratorArray[0].id;
            rtuInfo.integratorName = integratorArray[0].company;

            rtuInfo.rtuName = $("#addRtuName").val();
            rtuInfo.rtuModel = $("#addRtuModel").val();
            rtuInfo.rtuTransType = $("#addRtuTransType").val();
            rtuInfo.rtuAccessType = $("#addRtuAccessType").val();
            rtuInfo.machineCode = $("#addMachineCode").val();
            rtuInfo.snNumber = $("#addSnNumber").val();
            rtuInfo.operator = $("#addOperator").val();


            var handleRequest = $.post(YHu.util.ctxPath('/rtu/addRtuInfo'), rtuInfo);
            handleRequest.done(function (jsonResult) {

                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("新增网关信息成功", function (index) {
                        //do something
                        layer.close(index);
                        queryRtuList();
                        YHu.ui.closeAllLayer();
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

};

