var queryRoleInfoCondition = {
    pageNum: 1,
    pageSize: 10,
    roleName: null
};

var roleInfo = {
    id: null,
    roleName: null,
    description: null
};

var roleMenuInfo = {
    roleId: null,
    menuIdList: null
};

var zTree;

var initZTree = function (setting, zNodes) {

    $.fn.zTree.init($("#menuTree"), setting, zNodes);

    zTree = $.fn.zTree.getZTreeObj("menuTree");

    zTree.setting.check.chkboxType = {
        "Y": "ps",
        "N": "ps"
    };
};

//zTree设置项
var setting = {
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    }
};

var queryRoleInfoList = function () {

    YHu.ui.tableLoad("#roleTable", YHu.util.ctxPath("/role/queryRoleList"), queryRoleInfoCondition);

};

var setCondition = function () {
    queryRoleInfoCondition.roleName = $("#admin-role-name").val();
};

$("document").ready(function () {

    $("#search").click(function () {

        setCondition();
        queryRoleInfoList();
    });

    setCondition();
    queryRoleInfoList();

});

$("#clear").click(function () {

    clearCondition();
    setCondition();
    queryRoleInfoList();

});

var clearCondition = function () {

    $("#admin-role-name").val('');

};


var pageHandler = function (event, pageSize) {
    event.preventDefault();
    queryRoleInfoCondition.pageNum = event.target.title;
    if (pageSize != null && typeof (pageSize) != "undefined") {
        queryRoleInfoCondition.pageSize = pageSize;
        queryRoleInfoCondition.pageNum = 1;
    }
    queryRoleInfoList();
};

var deleteRoleInfo = function (no) {

    var id = $("#id" + no).text();

    layer.open({
        type: 1,
        title: "删除角色信息",
        area: ['350px', '180px'],
        content: $('#deleteLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var handleRequest = $.post(YHu.util.ctxPath('/role/deleteRole'), {id: id});
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    queryRoleInfoList();
                    YHu.ui.alert("删除角色信息成功",function () {
                        YHu.ui.closeAllLayer();
                        queryRoleInfoList();
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

var modifyRoleInfo = function (no) {

    var id = $("#id" + no).text();

    $("#modifyRoleName").val($("#roleName" + no).text());
    $("#modifyDesc").val($("#desc" + no).text());

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    layer.open({
        type: 1,
        title: "修改角色信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#modifyLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#modifyRoleForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            roleInfo.id = id;
            roleInfo.roleName = $("#modifyRoleName").val();
            roleInfo.description = $("#modifyDesc").val();

            var handleRequest = $.post(YHu.util.ctxPath('/role/updateRole'), roleInfo);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("修改角色信息成功",function () {
                        YHu.ui.closeAllLayer();
                        queryRoleInfoList();
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

var addRoleInfo = function () {

    $("#addRoleName").val("");
    $("#addDesc").val("");

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    layer.open({
        type: 1,
        title: "新增角色信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#addLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#addRoleForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            roleInfo.roleName = $("#addRoleName").val();
            roleInfo.description = $("#addDesc").val();

            var handleRequest = $.post(YHu.util.ctxPath('/role/addRole'), roleInfo);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("新增角色信息成功",function () {
                        YHu.ui.closeAllLayer();
                        queryRoleInfoList();
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

var updateRoleMenu = function (no) {

    var id = $("#id" + no).text();

    $.get(YHu.util.ctxPath('/role/queryMenuList/' + id), function (result) {

        var menuList = result.data;

        for (var i = 0; i < menuList.length; i++) {

            menuList[i].pId = menuList[i].parentId;

        }

        // 初始化权限树
        initZTree(setting, menuList);

        layer.open({
            type: 1,
            title: "更新角色菜单信息",
            area: 'auto',
            maxWidth: '450px',
            shadeClose: false,
            content: $('#updateMenuLayer'),
            btn: ["确定", "取消"],
            yes: function () {

                YHu.ui.loading();

                var checkedNodes = zTree.getCheckedNodes(true);

                roleMenuInfo.roleId = id;

                var menuIdList = [];

                for (var i = 0; i < checkedNodes.length; i++) {
                    menuIdList.push(checkedNodes[i].id);
                }

                roleMenuInfo.menuIdList = JSON.stringify(menuIdList);

                YHu.ui.loading();
                var handleRequest = $.post(YHu.util.ctxPath('/role/updateRoleMenu'), roleMenuInfo);
                handleRequest.done(function (jsonResult) {
                    YHu.ui.closeLoading();
                    if (jsonResult.success) {
                        YHu.ui.closeAllLayer();
                        YHu.ui.alert("更新角色菜单信息成功");
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

    });


};