/**
 * Created by wunder on 2016/10/21.
 */
var queryLogCondition = {
    pageNum: 1,
    pageSize: 10,
    startDate: null,
    endDate:null
};

var queryLogList = function () {

    YHu.ui.tableLoad("#loginLogTable", YHu.util.ctxPath("/log/login/queryLogList"), queryLogCondition);

};

var setCondition = function () {
    queryLogCondition.startDate = $("#startDate").val();
    queryLogCondition.endDate = $("#endDate").val();
};



var clearCondition = function () {
    $("#startDate").val('');
    $("#endDate").val('');
};

$("document").ready(function () {

    $("#search").click(function () {

        setCondition();
        queryLogList();
    });

    $("#clear").click(function () {
        clearCondition();
        setCondition();
        queryLogList();
    });

    queryLogList();
});

var pageHandler = function (event, pageSize) {
    event.preventDefault();
    queryLogCondition.pageNum = event.target.title;
    if (pageSize != null && typeof (pageSize) != "undefined") {
        queryLogCondition.pageSize = pageSize;
        queryLogCondition.pageNum = 1;
    }
    queryLogList();
};