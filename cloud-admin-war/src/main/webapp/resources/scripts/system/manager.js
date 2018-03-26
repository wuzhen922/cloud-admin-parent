$("document").ready(function() {

	$("#search").click(function() {

		setCondition();
		queryManagerList();
	});

	setCondition();
	queryManagerList();
});

var queryManagerCondition = {
	pageNum : 1,
	pageSize : 10,
	managerName : null
};

var setCondition = function() {
	queryManagerCondition.managerName = $("#managerName").val();
};

$("#clear").click(function() {
	clearCondition();
	setCondition();
	queryManagerList();
});

var clearCondition = function() {
	$("#managerName").val('');
};

var queryManagerList = function() {

	YHu.ui.tableLoad("#managerTable", YHu.util
			.ctxPath("/member/queryManagerList"), queryManagerCondition);
};

var pageHandler = function(event, pageSize) {
	event.preventDefault();
	queryManagerCondition.pageNum = event.target.title;
	if (pageSize != null && typeof (pageSize) != "undefined") {
		queryManagerCondition.pageSize = pageSize;
		queryManagerCondition.pageNum = 1;
	}
	queryManagerList();
};

var deleteManager = function(no) {

	var id = $("#id" + no).text();

	layer.open({
		type : 1,
		title : "删除用户信息",
		area : [ '350px', '180px' ],
		content : $('#deleteLayer'),
		btn : [ "确定", "取消" ],
		yes : function() {
			YHu.ui.loading();
			var handleRequest = $.post(YHu.util
					.ctxPath('/member/deleteManager'), {
				id : id
			});
			handleRequest.done(function(jsonResult) {
				YHu.ui.closeLoading();
				if (jsonResult.success) {
					YHu.ui.closeAllLayer();
					YHu.ui.alert("删除用户信息成功", function() {
						YHu.ui.closeAllLayer();
						queryManagerList();
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

var managerInfo = {
	id : null,
	managerName : null,
	loginName : null,
	password : null,
	department : null,
	phone : null,
	remark: null
};

var modifyManager = function(no) {
	var id = $("#id" + no).text();

	$('.form-group').removeClass('has-error');
	$('.form-group').removeClass('has-success');
	$('span.help-block').remove();
	
	$("#modifyManagerName").val($("#managerName" + no).text());
	
	$("#modifyPhone").val($("#phone" + no).text());
	$("#modifyRemark").val($("#remark" + no).text());
	layer.open({
		type : 1,
		title : "修改用户信息",
		area : 'auto',
		maxWidth : '450px',
		shadeClose : false,
		content : $('#modifyLayer'),
		btn : [ "确定", "取消" ],
		yes : function() {

			if (!$('#modifyManagerForm').valid()) {
				return 0;
			}
			YHu.ui.loading();

			managerInfo.id = id;
			managerInfo.managerName = $("#modifyManagerName").val();
			managerInfo.areaCode = $("#modifyAreaCode option:selected").data("code");
			managerInfo.areaName = $("#modifyAreaCode").val();
			managerInfo.phone = $("#modifyPhone").val();
            managerInfo.remark = $("#modifyRemark").val();
			var handleRequest = $.post(YHu.util
					.ctxPath('/member/updateManager'), managerInfo);
			handleRequest.done(function(jsonResult) {
				YHu.ui.closeLoading();
				if (jsonResult.success) {
					YHu.ui.closeAllLayer();
					YHu.ui.alert("修改用户信息成功", function() {
						YHu.ui.closeAllLayer();
						queryManagerList();
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

var addManager = function() {

	$('.form-group').removeClass('has-error');
	$('.form-group').removeClass('has-success');
	$('span.help-block').remove();

	$("#addManagerName").val("");
	$("#addLoginName").val("");
	$("#addPassword").val("");
	$("#addPhone").val("");
	$("#addRemark").val("");
	$("#province").val("");
	$("#addAreaCode").val("");
	layer.open({
		type : 1,
		title : "新增用户信息",
		area : 'auto',
		maxWidth : '450px',
		shadeClose : false,
		content : $('#addLayer'),
		btn : [ "确定", "取消" ],
		yes : function() {

			if (!$('#addManagerForm').valid()) {
				return 0;
			}

			YHu.ui.loading();

			managerInfo.managerName = $("#addManagerName").val();
			managerInfo.loginName = $("#addLoginName").val();
			managerInfo.password = hex_md5($("#addPassword").val());
			managerInfo.areaCode = $("#addAreaCode option:selected").data("code");
			managerInfo.areaName = $("#addAreaCode").val();
			managerInfo.phone = $("#addPhone").val();
			managerInfo.remark = $("#addRemark").val();

			var handleRequest = $.post(YHu.util.ctxPath('/member/addManager'),
					managerInfo);
			handleRequest.done(function(jsonResult) {
				YHu.ui.closeLoading();
				if (jsonResult.success) {
					YHu.ui.closeAllLayer();
					YHu.ui.alert("新增用户信息成功", function() {
						YHu.ui.closeAllLayer();
						queryManagerList();
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


var resetPassword = function(no) {

	var id = $("#id" + no).text();

	$("#newPassword").val("");
	$("#repeatPassword").val("");

	$('.form-group').removeClass('has-error');
	$('.form-group').removeClass('has-success');
	$('span.help-block').remove();

	layer.open({
		type : 1,
		title : "重置密码",
		area : 'auto',
		maxWidth : '450px',
		shadeClose : false,
		content : $('#resetPasswordLayer'),
		btn : [ "确定", "取消" ],
		yes : function() {

			if (!$('#resetManagerPasswordForm').valid()) {
				return 0;
			}

			YHu.ui.loading();

			managerInfo.id = id;
			managerInfo.password = hex_md5($("#newPassword").val());

			var handleRequest = $.post(YHu.util
					.ctxPath('/member/resetPassword'), managerInfo);
			handleRequest.done(function(jsonResult) {
				YHu.ui.closeLoading();
				if (jsonResult.success) {
					YHu.ui.closeAllLayer();
					YHu.ui.alert("重置用户密码成功");
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
