var queryAdminCondition = {
    pageNum: 1,
    pageSize: 10,
    userName: null
};

var adminIno = {
    id: null,
    userName: null,
    password: null,
    phone: null,
    email: null,
    // job: null,
    userDesc: null,
    realName: null,
    roleId: null
};

var modifyPasswordInfo = {
    oldPassword: null,
    newPassword: null
};

var queryAdminList = function () {

    YHu.ui.tableLoad("#memberTable", YHu.util.ctxPath("/user/queryUserList"), queryAdminCondition);

};



var setCondition = function () {
    queryAdminCondition.userName = $("#admin-name").val();
    queryAdminCondition.pageSize = "10";
    queryAdminCondition.pageNum = "1";
};

$("#clear").click(function () {
    clearCondition();
    setCondition();
    queryAdminList();
    queryAdminCondition.pageSize = "10";
    queryAdminCondition.pageNum = "1";
});

var clearCondition = function () {
    $("#admin-name").val('');
};

$("document").ready(function () {

    $("#search").click(function () {

        setCondition();
        queryAdminList();
    });

    setCondition();
    queryAdminList();
});

var deleteAdminUser = function (no) {

    var id = $("#id" + no).text();

    layer.open({
        type: 1,
        title: "删除管理员信息",
        area: ['350px', '180px'],
        content: $('#deleteLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var handleRequest = $.post(YHu.util.ctxPath('/user/deleteUser'), {id: id});
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("删除管理员信息成功", function () {
                        YHu.ui.closeAllLayer();
                        queryAdminList();
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

var modifyAdminUser = function (no) {

    loadRoleInfo(modifyAdminUserLayer,no);

};

var modifyAdminUserLayer = function (no) {

    var id = $("#id" + no).text();


    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();


    $("#modifyUserName").val($("#userName" + no).text());
    $("#modifyPhone").val($("#phone" + no).text());
    $("#modifyEmail").val($("#email" + no).text());
    // $("#modifyJob").val($("#job" + no).text());
    $("#modifyDesc").val($("#desc" + no).text());
    $("#modifyRealName").val($("#realName" + no).text());
    $("#modifyRoleInfo").val($("#roleId" + no).text());

    layer.open({
        type: 1,
        title: "修改管理员信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#modifyLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#modifyAdminUserForm').valid()) {
                return 0;
            }
            YHu.ui.loading();

            adminIno.id = id;
            adminIno.userName = $("#modifyUserName").val();
            adminIno.phone = $("#modifyPhone").val();
            adminIno.email = $("#modifyEmail").val();
            // adminIno.job = $("#modifyJob").val();
            adminIno.userDesc = $("#modifyDesc").val();
            adminIno.realName = $("#modifyRealName").val();
            adminIno.roleId = $("#modifyRoleInfo").val();

            var handleRequest = $.post(YHu.util.ctxPath('/user/updateUser'), adminIno);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("修改管理员信息成功", function () {
                        YHu.ui.closeAllLayer();
                        queryAdminList();
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

var addAdminUser = function () {

    $("#addUserName").val("");
    $("#addPassword").val("");
    $("#addRepeatPassword").val("");

    $("#addPhone").val("");
    $("#addEmail").val("");
    // $("#addJob").val("");
    $("#addDesc").val("");
    $("#addRealName").val("");
    $("#addRoleInfo").val("");

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    loadRoleInfo(addAdminUserLayer);

};

var addAdminUserLayer = function () {
    // $("#addPhone").val("");
    // $("#addPassword").val("");
    layer.open({
        type: 1,
        title: "新增管理员信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#addLayer'),
        btn: ["确定", "取消"],
        success:function(){
            $("#addPhone").val("");
            $("#addPassword").val("");
            $("#addRepeatPassword").val("");
        },//todo:#addPhone和#addPassword总是在最后一步填充了无关的值，没法清空
        yes: function () {

            if (!$('#addAdminUserForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            adminIno.userName = $("#addUserName").val();
            adminIno.password = hex_md5($("#addPassword").val());
            adminIno.phone = $("#addPhone").val();
            adminIno.email = $("#addEmail").val();
            // adminIno.job = $("#addJob").val();
            adminIno.userDesc = $("#addDesc").val();
            adminIno.realName = $("#addRealName").val();
            adminIno.roleId = $("#addRoleInfo").val();

            var handleRequest = $.post(YHu.util.ctxPath('/user/addUser'), adminIno);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("新增管理员信息成功", function () {
                        YHu.ui.closeAllLayer();
                        queryAdminList();
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

var resetPassword = function (no) {

    var id = $("#id" + no).text();

    $("#newPassword").val("");
    $("#repeatPassword").val("");

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    layer.open({
        type: 1,
        title: "重置密码",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#resetPasswordLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#resetPasswordForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            adminIno.id = id;
            adminIno.password = hex_md5($("#newPassword").val());

            var handleRequest = $.post(YHu.util.ctxPath('/user/resetPassword'), adminIno);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("重置管理员密码成功");
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

var unLockUser = function (no) {

    var id = $("#id" + no).text();

    layer.open({
        type: 1,
        title: "解锁管理员",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#unlockLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var handleRequest = $.post(YHu.util.ctxPath('/user/unlockUser'), {id: id});
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("解锁管理员成功", function () {
                        YHu.ui.closeAllLayer();
                        queryAdminList();
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

var bindRole = function (no) {

     var id = $("#id" + no).text();


    $("#roleInfo").val($("#roleId" + no).text());

    layer.open({
        type: 1,
        title: "绑定角色",
        area: ['350px', '180px'],
        content: $('#bindLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();

            var roleId = $("#roleInfo").val();

            var handleRequest = $.post(YHu.util.ctxPath('/userManagement/bindingRole'), {userId: id, roleId: roleId});
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("绑定角色成功！", function () {
                        YHu.ui.closeAllLayer();
                        queryAdminList();
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

var pageHandler = function (event, pageSize) {
    event.preventDefault();
    queryAdminCondition.pageNum = event.target.title;
    if (pageSize != null && typeof (pageSize) != "undefined") {
        queryAdminCondition.pageSize = pageSize;
        queryAdminCondition.pageNum = 1;
    }
    queryAdminList();
};

var modifyPassword = function () {

    layer.open({
        type: 1,
        title: "修改密码",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#modifyPasswordLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#modifyPasswordForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            modifyPasswordInfo.oldPassword = hex_md5($("#oldPassword").val());
            modifyPasswordInfo.newPassword = hex_md5($("#newPassword").val());

            var handleRequest = $.post(YHu.util.ctxPath('/user/modifyPassword'), modifyPasswordInfo);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("修改密码成功");
                    window.location.href = YHu.util.ctxPath('/logout');
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

var loadRoleInfo = function (callback,no) {
    YHu.ui.loading();
    var handleRequest = $.post(YHu.util.ctxPath('/role/queryRole'));
    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            var roleInfoList = jsonResult.data;
            $("#addRoleInfo").empty();
            $("#modifyRoleInfo").empty();
            $("#addRoleInfo").append("<option value=\"\" >请选择角色</option>");
            $("#modifyRoleInfo").append("<option value=\"\" >请选择角色</option>");
            for (var i = 0; i < roleInfoList.length; i++) {
                $("#addRoleInfo").append("<option value=" + roleInfoList[i].id + ">" + roleInfoList[i].roleName + "</option>");
                $("#modifyRoleInfo").append("<option value=" + roleInfoList[i].id + ">" + roleInfoList[i].roleName + "</option>");
            }
            callback(no);
        } else {
            YHu.ui.alert(jsonResult.message);
        }
    });
};