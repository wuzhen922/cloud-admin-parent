var loginInfo = {
    userName: null,
    password: null
};

$(function () {

    $("#login").click(function () {
        login();
    });

    $("body").keydown(function () {
        if (event.keyCode == "13") {
            login();
        }
    });

    // $("#username").focus(function () {
    //     $("#login-error").addClass("none");
    // });
    //
    // $("#password").focus(function () {
    //     $("#login-error").addClass("none");
    // });
//当前页面不是浏览器主页面，则跳转浏览器页面到login
    if(window.parent != window.self){
        parent.location.href = YHu.util.ctxPath("/login");
    }

});

function login() {

    if (!$('#loginForm').valid()) {
        return 0;
    }

    $("#login").attr("disabled", "disabled");

    YHu.ui.loading();

    loginInfo.userName = $("#username").val();
    loginInfo.password = hex_md5($("#password").val());
    var type=$("input[name = 'managerLogin']:checked");

    if(type.length){
    	  loginInfo.loginType=type[0].defaultValue;
    }

    var handleRequest = $.post(YHu.util.ctxPath('/loginSubmit'), loginInfo);
    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            YHu.ui.closeAllLayer();
            if(type.length){
            	window.location.href = YHu.util.ctxPath("/managerMain");
            	return;
            }
            window.location.href = YHu.util.ctxPath("/main");
        } else {
            YHu.ui.closeAllLayer();
            $("#username").val("");
            $("#password").val("");

            $("#login").removeAttr("disabled");
            $("#login-error-message").text(jsonResult.message);
            $("#login-error").removeClass("none");
        }
    });

}

//CharMode函数  
//测试某个字符是属于哪一类.  
function CharMode(iN){
    if (iN>=48 && iN <=57) //数字  
        return 1;
    if (iN>=65 && iN <=90) //大写字母  
        return 2;
    if (iN>=97 && iN <=122) //小写  
        return 4;
    else
        return 8; //特殊字符  
}

//bitTotal函数  
//计算出当前密码当中一共有多少种模式  
function bitTotal(num){
    modes=0;
    for (i=0;i<6;i++){
        if (num & 1) modes++;
        num>>>=1;
    }
    return modes;
}


//判断输入密码的类型
//CharMode函数
//测试某个字符是属于哪一类.
function CharMode(iN){
    if (iN>=48 && iN <=57) //数字
        return 1;
    if (iN>=65 && iN <=90) //大写字母
        return 2;
    if (iN>=97 && iN <=122) //小写
        return 4;
    else
        return 8; //特殊字符
}

//bitTotal函数
//计算出当前密码当中一共有多少种模式
function bitTotal(num){
    modes=0;
    for (i=0;i<6;i++){
        if (num & 1) modes++;
        num>>>=1;
    }
    return modes;
}

//checkStrong函数
//返回密码的强度级别

function checkStrong(sPW){
    if (sPW.length<=6)
        return 0; //密码太短
    Modes=0;
    for (i=0;i<sPW.length;i++){
//测试每一个字符的类别并统计一共有多少种模式.
        Modes|=CharMode(sPW.charCodeAt(i));
    }

    return bitTotal(Modes);

}

//pwStrength函数
//当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示不同的颜色

function pwStrength(pwd){
    var O_color="#eeeeee";
    var L_color="#FF0000";
    var M_color="#FF9900";
    var H_color="#33CC00";
    if (pwd==null||pwd==''){
        L_color=M_color=H_color=O_color;
    }
    else{
        S_level=checkStrong(pwd);
        switch(S_level) {
            case 0:
                L_color=M_color=H_color=O_color;
            case 1:
                L_color=L_color;
                M_color=H_color=O_color;
                break;
            case 2:
                L_color=M_color=M_color;
                H_color=O_color;
                break;
            default:
                L_color=M_color=H_color=H_color;
        }
    }

    document.getElementById("strength_L").style.background=L_color;
    document.getElementById("strength_M").style.background=M_color;
    document.getElementById("strength_H").style.background=H_color;
    return;
}