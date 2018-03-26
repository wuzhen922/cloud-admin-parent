/**
 * Created by wtw on 2017/9/14.
 */

var eye = function () {
};
eye.prototype = {
    extend: function (a, b) {

        for (var item in b) {
            a[item] = b[item];


        }
        return a;
    }
};
eye = new eye();


eye.extend(eye, {
    //跳转到其他页面
    jump: function (element, location) {
        $(element).on('click', function (e) {
             if(location=='login'){
                 window.location.href = YHu.util.ctxPath("/login");
             }
             else if(location=='register'){
                 window.location.href = YHu.util.ctxPath("/integrator/register");
             }
             else if(location=='forgetPassword'){
                 window.location.href = YHu.util.ctxPath("/integrator/jumpToForgetPassword");
             }
        });
    },
    //主页面高度自适应
    heightAuto: function (totalHeight, topElement) {
        return (parseInt($(totalHeight).innerHeight() - $(topElement).outerHeight(true))-10);
    },
    //获取验证码
    requireValidate: function (element) {
        $(element).on('click', 'a', function (e) {
            $(".checkValidate").focus().val("");
            //下面的两行代码在这里可以不用
            $(".checkValidate").on('blur',function(event){
                event.preventDefault();
            });
            if($(this).hasClass('disabled1')){
                e.preventDefault();
            }else {

                sendValidateCode(this);
            }
        });
    },
    //检验密码强度
    passwordStrengthVerify: function (element) {
        $(element).on('keyup', function () {
            verifyPasswordStrength(this);
        });
        $(element).on('blur', function (e) {
            $('.passwordStrengthProgress').removeClass('none');
            $('.passwordStrengthProgress').addClass('none');
        })
    },


    //校验验证码
    validateCheck: function (element) {
        $(element).on('blur', function (e) {

            if ($("#validateCode").val()!=null&&$("#validateCode").val()!=''){
                verifyValidateCode(this);
            }
        })

    },
    //手机号查重
    phoneCheck: function (element) {
        $(element).on('blur', function (e) {

            if ($("#phone").val()!=null&&$("#phone").val()!=''){
                checkPhoneNumber(this);
            }
        })

    },
    //用户名查重
    userNameCheck: function (element) {
        $(element).on('blur', function (e) {

            if ($("#registerByUserNameUserName").val()!=null&&$("#registerByUserNameUserName").val()!=''){
                checkUserName(this);
            }
        })

    },


    //二级菜单点击，内容选择性展示，注意菜单与内容各项顺序一致
    containerShowAndHide: function (element, showElement) {
        $(element).on('click', 'li', function () {
            $(this).addClass('active').siblings('li').removeClass('active');
            $(element + ' li').each(function (i, data) {

                if ($(this).hasClass('active')) {
                    $(showElement + ':eq(' + i + ')').removeClass('none').siblings('div').addClass('none');
                    eye.menuHeightAuto('.sencondMenu', '.thirdContainer');
                }

            });
        });
    },
    //element隐藏，showElement展示
    contentShowAndHide: function (element, showElement) {

        $(element).addClass('none');
        $(showElement).removeClass('none');
    },
    //二级菜单长度与三级相等
    menuHeightAuto: function (element, containerElement) {

        var minHeight = (parseFloat($(window).height())-2)+'px';

        $(containerElement).css('min-height',minHeight);
        $(element).height($(containerElement).height());

    },
    //监听div大小变化自适应二级菜单与内容高度
    resizeDiv:function(element){
        $(element).resize(function(){

            eye.menuHeightAuto('.sencondMenu', '.thirdContainer')
        });
    }



});

//layer方法
global_load_index = -1;
eye.extend(eye, {
    //加载
    loading: function (msg) {
        var message = msg || '加载中，请稍候...';
        global_load_index = layer.load(2, message);
        return global_load_index;

    },
    //关闭加载
    closeLoading: function (i) {
        var index = i || global_load_index;
        return layer.close(index);

    },
    //信息确认
    confirm: function (msg, option, yes, no) {
        return layer.confirm(msg, option, yes, no);
    },
    //提示  style:1成功；2：失败
    msg: function (msg, style, time) {
        layer.msg(msg, {
            icon: style || 1,
            time: time || 2000
        })

    },
    //确认信息
    alert: function (msg, fn, yes) {
        layer.alert(msg, fn, yes);
    }

});

eye.extend(eye, {
    ajax: function (url, callback) {
        $.ajax({
            url: url,
            async: true,
            type: 'get',
            data: null,
            dataType: 'json',
            success: function (data) {
                callback(data);

            }

        })
    }

});

var verifyPasswordStrength = function (obj) {
    var that = obj;
    var ele_progress = $('.passwordStrengthProgress');
    var begin_classname = ele_progress.attr('class');
    begin_classname = begin_classname.replace('none', '');
    var regxs = [];
    regxs.push(/^(?=.*\d)[a-zA-Z\d_]{8,}$/g);
    regxs.push(/^(?=.*[a-z])[a-zA-Z\d_]{8,}$/g);
    regxs.push(/^(?=.*[A-Z])(?=.*_)[a-zA-Z\d_]{8,}$/g);
    var val = $(that).val();
    var len = val.length;
    var sec = 0;
    if (len >= 8) { // 至少八个字符
        $('.passwordStrengthProgress').removeClass('none');
        for (var i = 0; i < regxs.length; i++) {
            if (val.match(regxs[i])) {
                sec++;
            }
        }

        var result = (sec / regxs.length) * 100;
        ele_progress.css('width', result + "%");

        if (result > 0 && result <= 50) {
            ele_progress.addClass('error').removeClass('middle').removeClass('strong');


        } else if (result > 50 && result < 100) {
            ele_progress.addClass('middle').removeClass('error').removeClass('strong');


        } else if (result === 100) {
            ele_progress.addClass('strong').removeClass('error').removeClass('middle');


        }
    }

};


var verifyValidateCodeInfo = {
    phone: null,
    validateCode: null,
    invalid: null,
    type: null

};

var verifyValidateCode = function (obj) {
    var that = obj;
    verifyValidateCodeInfo.phone = $("#phone").val();
    verifyValidateCodeInfo.validateCode = $("#validateCode").val();
    verifyValidateCodeInfo.type = $(that).attr('data-id');
    verifyValidateCodeInfo.invalid = '0';
    var handleRequest = $.post(YHu.util.ctxPath('/validateCode/verifyValidateCode'), verifyValidateCodeInfo);
    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            $('.errorTip').removeClass('none').text(jsonResult.message);
            verifyValidateCodeInfo = {};
        } else {
            $('.errorTip').removeClass('none').text(jsonResult.message);
            verifyValidateCodeInfo = {};
        }
    });

};
var checkPhoneNumberInfo = {
    phone: null

};

var checkPhoneNumber = function (obj) {
    var that = obj;
    checkPhoneNumberInfo.phone = $("#phone").val();
    var handleRequest = $.post(YHu.util.ctxPath('/integrator/checkRegisterInfo'), checkPhoneNumberInfo);
    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            $('.phoneErrorTip').removeClass('none').text(jsonResult.message);
            checkPhoneNumberInfo = {};
        } else {
            $('.phoneErrorTip').removeClass('none').text(jsonResult.message);
            checkPhoneNumberInfo = {};
        }
    });

};
var checkUserNameInfo = {
    userName: null

};

var checkUserName = function (obj) {
    var that = obj;
    checkUserNameInfo.userName = $("#registerByUserNameUserName").val();
    var handleRequest = $.post(YHu.util.ctxPath('/integrator/checkRegisterInfo'), checkUserNameInfo);
    handleRequest.done(function (jsonResult) {
        YHu.ui.closeLoading();
        if (jsonResult.success) {
            $('.userNameErrorTip').removeClass('none').text(jsonResult.message);
            checkUserNameInfo = {};
        } else {
            $('.userNameErrorTip').removeClass('none').text(jsonResult.message);
            checkUserNameInfo = {};
        }
    });

};

var sendValidateCodeInfo = {
    type: null,
    phone: null
};

var sendValidateCode = function (obj) {

    var that = obj;
    var phoneNumber= $("#phone").val();
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    if(phoneNumber.match(mobile)&&phoneNumber.length==11 ){
        var count = 60;
        sendValidateCodeInfo.phone = phoneNumber;
        sendValidateCodeInfo.type = $(that).attr('data-id');
        var handleRequest = $.post(YHu.util.ctxPath('/validateCode/sendValidateCode'), sendValidateCodeInfo);
        handleRequest.done(function (jsonResult) {
            YHu.ui.closeLoading();
            if (jsonResult.success) {
                YHu.ui.closeAllLayer();
                showCount.call(that, count, that);
            } else {
                YHu.ui.closeAllLayer();
                YHu.ui.alert(jsonResult.message);
                sendValidateCodeInfo = {};
            }
        });
    }
    else{
        YHu.ui.closeAllLayer();
        YHu.ui.alert("手机号码格式不对，无法发送验证码");
        sendValidateCodeInfo = {};
    }


};

function showCount(count, obj) {
    var that = obj;
    if (count > 0) {
        $(this).addClass('disabled1');

        setTimeout(function () {

            count--;
            $(that).css({

                'padding':     ' 4px 26px',
                'tranzistion': 'all .3s ease-in'
            });
            $(that).html(count + 's');
            showCount(count, that);
        }.bind(that), 1000);

    } else {
        $(that).css({

            'padding':     ' 4px 7px',
            'tranzistion': 'all .3s ease-in'
        });
        $(that).html('获取验证码');

        $('#verify_xbox').css('width','54px').html('<div id="btn"><img src=' +YHu.util.ctxPath("/resources/theme/images/lllllll.png") +'/></div>');
        slide();


    }

}

//jquery扩展，resize监听div大小
(function($, h, c) {
    var a = $([]),
        e = $.resize = $.extend($.resize, {}),
        i,
        k = "setTimeout",
        j = "resize",
        d = j + "-special-event",
        b = "delay",
        f = "throttleWindow";
    e[b] = 250;
    e[f] = true;
    $.event.special[j] = {
        setup: function() {
            if (!e[f] && this[k]) {
                return false;
            }
            var l = $(this);
            a = a.add(l);
            $.data(this, d, {
                w: l.width(),
                h: l.height()
            });
            if (a.length === 1) {
                g();
            }
        },
        teardown: function() {
            if (!e[f] && this[k]) {
                return false;
            }
            var l = $(this);
            a = a.not(l);
            l.removeData(d);
            if (!a.length) {
                clearTimeout(i);
            }
        },
        add: function(l) {
            if (!e[f] && this[k]) {
                return false;
            }
            var n;
            function m(s, o, p) {
                var q = $(this),
                    r = $.data(this, d);
                r.w = o !== c ? o: q.width();
                r.h = p !== c ? p: q.height();
                n.apply(this, arguments);
            }
            if ($.isFunction(l)) {
                n = l;
                return m;
            } else {
                n = l.handler;
                l.handler = m;
            }
        }
    };
    function g() {
        i = h[k](function() {
                a.each(function() {
                    var n = $(this),
                        m = n.width(),
                        l = n.height(),
                        o = $.data(this, d);
                    if (m !== o.w || l !== o.h) {
                        n.trigger(j, [o.w = m, o.h = l]);
                    }
                });
                g();
            },
            e[b]);
    }
})(jQuery, this)


//rank函数接受一个成员名字符串做为参数
//并返回一个可以用来对包含该成员的对象数组进行排序的比较函数
//由小到大排序
var rank = function(name){
    return function(o, p){
        var a, b;
        if (typeof o === "object" && typeof p === "object" && o && p) {
            a = o[name];
            b = p[name];
            if (a === b) {
                return 0;
            }
            if (typeof a === typeof b) {
                return parseFloat(a) <parseFloat(b)  ? -1 : 1;
            }
            return typeof a < typeof b ? -1 : 1;
        }
        else {
            throw ("error");
        }
    }
};
function clone(obj){
    function F(){}
    F.prototype = obj;
    return new F;
}