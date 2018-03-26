var queryMenuCondition = {
    pageNum: 1,
    pageSize: 10
};

var querySubMenuCondition = {
    pageNum: 1,
    pageSize: 10,
    parentId: null
};

var menuIno = {
    id: null,
    menuName: null,
    menuUrl: null,
    sort: null,
    menuLevel: null,
    parentId: null,
    menuIcon: null
};

var menuLevel = {
    first: 1,
    second: 2,
    third: 3
};

var queryFirstMenuList = function () {

    YHu.ui.tableLoad("#firstMenuTable", YHu.util.ctxPath("/menu/queryFirstMenuListByPage"), queryMenuCondition);
};

var querySecondMenuList = function () {

    if (typeof(querySubMenuCondition.parentId) == "undefined" || querySubMenuCondition.parentId == null) {

        return;
    }

    YHu.ui.tableLoad("#secondMenuTable", YHu.util.ctxPath("/menu/querySecondMenuListByPage"), querySubMenuCondition);
};

$("document").ready(function () {
    queryFirstMenuList();
});

var firstLevelMenuPageHandler = function (event, pageSize) {
    event.preventDefault();

    queryMenuCondition.pageNum = event.target.title;
    if (pageSize != null && typeof (pageSize) != "undefined") {
        queryMenuCondition.pageSize = pageSize;
        queryMenuCondition.pageNum = 1;
    }
    queryFirstMenuList();

};

var secondLevelMenuPageHandler = function (event, pageSize) {

    event.preventDefault();

    querySubMenuCondition.pageNum = event.target.title;
    if (pageSize != null && typeof (pageSize) != "undefined") {
        querySubMenuCondition.pageSize = pageSize;
        querySubMenuCondition.pageNum = 1;
    }
    querySecondMenuList();

};

var viewSubMenu = function (no) {

    querySubMenuCondition.parentId = $("#id" + no).text();
    querySecondMenuList();

};

var addMenu = function () {

    menuIno.menuLevel = menuLevel.first;
    addMenuInfo(queryFirstMenuList);

};

var addSubMenu = function () {

    menuIno.menuLevel = menuLevel.second;

    if (typeof(querySubMenuCondition.parentId) == "undefined" || querySubMenuCondition.parentId == null) {

        YHu.ui.alert("请先选择主菜单");
        return;
    }

    menuIno.parentId = querySubMenuCondition.parentId;
    addMenuInfo(querySecondMenuList);

};

var addMenuInfo = function (callback) {

    $("#addMenuIcon").val("");
    $("#addMenuName").val("");
    $("#addMenuUrl").val("");
    $("#addMenuSort").val("");

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    layer.open({
        type: 1,
        title: "新增菜单信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#addLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#addMenuForm').valid()) {
                return 0;
            }

            YHu.ui.loading();

            menuIno.menuName = $("#addMenuName").val();
            menuIno.menuUrl = $("#addMenuUrl").val();
            menuIno.sort = $("#addMenuSort").val();
            menuIno.menuIcon = $("#addMenuIcon").val();

            var handleRequest = $.post(YHu.util.ctxPath('/menu/addMenu'), menuIno);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("新增菜单信息成功", function () {
                        YHu.ui.closeAllLayer();
                        callback();
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

var modifyMenu = function (no) {

    var id = $("#id" + no).text();

    $('.form-group').removeClass('has-error');
    $('.form-group').removeClass('has-success');
    $('span.help-block').remove();

    $("#modifyMenuName").val($("#menuName" + no).text());
    $("#modifyMenuUrl").val($("#menuUrl" + no).text());
    $("#modifyMenuSort").val($("#sort" + no).text());
    $("#modifyMenuIcon").val($("#menuIcon" + no).text());

    layer.open({
        type: 1,
        title: "修改菜单信息",
        area: 'auto',
        maxWidth: '450px',
        shadeClose: false,
        content: $('#modifyLayer'),
        btn: ["确定", "取消"],
        yes: function () {

            if (!$('#modifyMenuForm').valid()) {
                return 0;
            }
            YHu.ui.loading();
            menuIno.id = id;
            menuIno.menuName = $("#modifyMenuName").val();
            menuIno.menuUrl = $("#modifyMenuUrl").val();
            menuIno.sort = $("#modifyMenuSort").val();
            menuIno.menuIcon = $("#modifyMenuIcon").val();

            var handleRequest = $.post(YHu.util.ctxPath('/menu/updateMenu'), menuIno);
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("修改菜单信息成功", function () {
                        YHu.ui.closeAllLayer();
                        queryFirstMenuList();
                        querySecondMenuList();
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


var deleteMenu = function (no) {

    var id = $("#id" + no).text();

    layer.open({
        type: 1,
        title: "删除菜单信息",
        area: ['350px', '180px'],
        content: $('#deleteLayer'),
        btn: ["确定", "取消"],
        yes: function () {
            YHu.ui.loading();
            var handleRequest = $.post(YHu.util.ctxPath('/menu/deleteMenu'), {id: id});
            handleRequest.done(function (jsonResult) {
                YHu.ui.closeLoading();
                if (jsonResult.success) {
                    YHu.ui.closeAllLayer();
                    YHu.ui.alert("删除菜单信息成功", function () {
                        YHu.ui.closeAllLayer();
                        queryFirstMenuList();
                        querySecondMenuList();
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
