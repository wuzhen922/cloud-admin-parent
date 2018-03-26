(function($) {
	$.fn.YHu = function(options) {
		var defaults = {};
		var options = $.extend(defaults, options);
	};
})(jQuery);

;
!function(window, undefined) {
	window.YHu = {};
	YHu.util = YHu.util || {};
	YHu.ui = YHu.ui || {};

	YHu.ui = {
		// 显示错误信息提示
		errorTip : function(id, msg, direction) {
			var _$error = $(id);
			_$error.parent().css("z-index", 999).parents("div.input").addClass(
					"error");
			if (direction == "" || typeof direction == "undefined") {
				if (_$error.next("em").length == 0) {
					_$error.after("<em><i></i>" + msg + "</em>");
				}
			} else {
				_$error.after("<em class=" + direction + "><i></i>" + msg
						+ "</em>");
			}
		},
		removeErrorTip : function(id) {
			var _$error = $(id);
			_$error.parents("div.input").removeClass("error");
			_$error.next("em").remove();
		},
		// iframe 自适应高度
		iFrameHeight : function(id) {
			var ifm = document.getElementById(id);
			var subWeb = document.frames ? document.frames[id].document
					: ifm.contentDocument;
			if (ifm != null && subWeb != null) {
				ifm.height = subWeb.body.scrollHeight;
			}
		},
		global_load_index : -1, // 全局控制一个弹出等待层的index
		loading : function(msg) {
			if (null == msg || "" == msg) {
				msg = "处理中，请稍后……";
			}
			global_load_index = layer.load(2, msg);
			return global_load_index;
		},
		closeLoading : function(i) {
			if (null == i || typeof i == "undefined") {
				return layer.close(global_load_index);
			}
			return layer.close(i);
		},
		alert : function(msg, fn, yes) {
			layer.alert(msg, fn, yes);
		},
		confirm : function(msg, opts, yes, no) {
			return layer.confirm(msg, opts, yes, no);
		},
		tableTrColor : function(obj) {
			// 表格效果
			obj.find("tr:even").addClass("odd");
			obj.find("tr").on({
				mouseover : function() {
					$(this).addClass("hover");
				},
				mouseout : function() {
					$(this).removeClass("hover");
				}
			});
		},
		tableLayer : function(element, url, data, callback, title) {
			YHu.ui.tableLoad(element, url, data, callback);
			var $table = $(element);
			if (null == title || typeof title == "undefined")
				title = "温馨提示";
			$table.css("width", "1000px"); // 固定宽度1000px
			$.layer({
				type : 1,
				title : title,
				area : [ "1050px", "480px" ],
				offset : [ "12px", "68px" ],
				page : {
					dom : $table
				}
			});
		},
		disableButton : function(element) {
			$(element).addClass("disgrey").attr("disabled", "disabled");
		},
		ableButton : function(element) {
			$(element).removeClass("disgrey").removeAttr("disabled");
		},
		tableLoad : function(element, url, data, callback) {
			var $table = $(element);
			var table_height = $table.height();
			if (table_height < 60) {
				table_height = 60;
				$table.css("min-height", table_height + "px");
			}
			// 查询loading画面
			$table.append("<div class='dataLoading' height=" + table_height
					+ "></div><div class='dataLoadingImg'></div>");
			callback = (typeof data == "function") ? data : callback;
			$table.load(url, data, function() {
				if ($table.find("tr").length <= 1) {
					var td = $table.find("th").length;
					$table.find("table").append(
							'<tr><td colspan=' + td + '>暂无数据</td></tr>');
				}
				// 表格效果
				YHu.ui.tableTrColor($table);
				// 表单数据格式
				initFormat();
				// 自定义回调函数
				if ("function" == typeof callback)
					callback();
			});
		},
		layer : function(layerData) {
			var i = layer.msg(layerData);
			initFormat();
			return i;
		},
		closeAllLayer : function() {
			layer.closeAll();
		}
	};

	YHu.util = {
		ctxPath : function(src) {
			return YHu_Config.bathPath + src;
		},
		turnPage : function(urls) {
			$('#html-content').load(urls, function() {
				$('#html-content').fadeIn('show');
			});
		},
		containSpecChar : function(s) {
			var containSpecial = RegExp(/%0d%0a|SCRIPT|EVAL|ALERT|EXEC|DROP|SELECT|ALTER|INSERT|UPDATE|DELETE|EXISTS|\bOR\b|\bXOR\b|EXECUTE|XP_CMDSHELL|DECLARE|SP_OACREATE|WHERE|WSCRIPT.SHELL|XP_REGWRITE|\'|\<|\>|\$|\&|\\|\||\\"|\~|\`|\*/gi);
			return (containSpecial.test(s));
		},
		// 时间控件拆分时间,第二个字段是返回的日期是否为日期值
		divideDateRange : function(dateRange, isDate) {
			if ("" == dateRange)
				return "";
			var dates = dateRange.split(" 至 ");
			if (!isDate) {
				dates[0] = dates[0] + ' 00:00:00';
				dates[1] = dates[1] + ' 23:59:59';
			}
			return dates;
		},
		moneyUppercase : function(num) {
			if (!/^\d*(\.\d*)?$/.test(num)) {
				return "";
			}
			if (num.length > 9) {
				return "";
			}
			var AA = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖");
			var BB = new Array("元", "拾", "佰", "仟", "万", "亿", "点", "");
			var CC = new Array("", "拾", "佰", "仟", "万", "亿", "点", "");
			var a = ("" + num).replace(/(^0*)/g, "").split("."), k = 0, re = "";

			for (var i = a[0].length - 1; i >= 0; i--) {
				switch (k) {
				case 0:
					re = BB[7] + re;
					break;
				case 4:
					if (!new RegExp("0{4}\\d{" + (a[0].length - i - 1) + "}$")
							.test(a[0]))
						re = BB[4] + re;
					break;
				case 8:
					re = BB[5] + re;
					BB[7] = BB[5];
					k = 0;
					break;
				}
				if (k % 4 == 2 && a[0].charAt(i + 2) != 0
						&& a[0].charAt(i + 1) == 0)
					re = AA[0] + re;
				if (a[0].charAt(i) != 0) {
					if (k % 4 == 0) {
						re = AA[a[0].charAt(i)] + CC[k % 4] + re;
					} else {
						re = AA[a[0].charAt(i)] + BB[k % 4] + re;
					}
				}
				k++;
			}
			var s = ("" + num).replace(/(^0*)/g, "").split(".");
			if (("" + s).substring(0, 1) == "0"
					|| ("" + s).substring(0, 1) == ",") {
				re = AA[0];
			}
			if (a.length > 1) { // 加上小数部分(如果有小数部分)
				// re += re.substring(0,re.length-1);
				re += BB[6];
				for (var i = 0; i < a[1].length; i++)
					re += AA[a[1].charAt(i)];
				// re += BB[0];
			}
			if (re != "") {
				re += BB[0];
			}
			return re;
		},
		money2ThousandsComma : function(value) {
			var num = $.trim(value);
			if ("" == num) {
				return num;
			}
			var r = "";
			if (num.indexOf('￥') >= 0) {
				num = num.substr(1);
				r += "￥";
			}

			if (isNaN(num)) {
				return num;
			}

			var intNum = num;
			var pointNum = "";
			var idx = num.indexOf('.');
			if (idx >= 0) {
				intNum = num.substring(0, idx);
				pointNum = num.substr(idx);
			}
			var regexStr = /(\d{1,3})(?=(\d{3})+(?:$|\.))/g;
			return r + intNum.replace(regexStr, "$1,") + pointNum;
		},
		money_point_sub : function(element) {
			var value = $.trim(element.html()).replace(/\s|\r/g, "");
			var rv = YHu.util.money2ThousandsComma(value);
			var integer = rv.split('.')[0];
			var pointer = rv.split('.')[1];
			var html = integer;
			if (pointer == null || typeof pointer == 'undefined') {
				pointer = '00';
			} else {
				var length = pointer.length;
				for (var i = 2; i > length; i--) {
					pointer = pointer + '0';
				}
			}
			html += "." + pointer;
			element.html(html);
		},
		bankAuthorize : function(callback) {
			var bocNo = '';
			var jsebNo = '';
			var checkPw = function(i) {
				YHu.ui.loading();
				var boc_pw = $("#bocPw").val();
				var jseb_pw = $("#jsebPw").val();
				if ("" == boc_pw || "" == jseb_pw) {
					YHu.ui.alert("密码不能为空");
					return false;
				}
				var postdata = {
					bocPw : boc_pw,
					bocUserName : bocNo,
					jsebPw : jseb_pw,
					jsebUserName : jsebNo
				};
				$.post(YHu.util.ctxPath("/checkBankPw"), postdata, function(
						result) {
					YHu.ui.closeLoading();
					if (!result.success) {
						YHu.ui.alert(result.message);
					} else {
						layer.close(i);
						YHu.ui.closeLoading();
						callback();
					}
				});
			};

			var showBankInfo = function(show_html) {
				YHu.ui
						.layer({
							type : 1,
							area : [ "700px", "200px" ],
							page : {
								html : "<div class='info-list-view' style='display:block;'><ul class='pay-wrong-list clearfix'>"
										+ show_html + "</ul></div>"
							},
							btns : 2,
							yes : checkPw
						});
			};
			YHu.ui.loading();
			$.ajax({
				type : "POST",
				async : false,
				success : function(result) {
					YHu.ui.closeLoading();
				}
			});
		},
		getStringBytes : function(s) {
			var totalLength = 0;
			var i;
			var charCode;
			for (i = 0; i < s.length; i++) {
				charCode = s.charCodeAt(i);
				if (charCode < 0x007f) {
					totalLength = totalLength + 1;
				} else if ((0x0080 <= charCode) && (charCode <= 0x07ff)) {
					totalLength += 2;
				} else if ((0x0800 <= charCode) && (charCode <= 0xffff)) {
					totalLength += 3;
				}
			}
			return totalLength;
		},
		imageUpload : function(imageElementId, postUrl, callback) {

			var reg = /\.jpg$|\.png$/i;

			if ($("#" + imageElementId).val() == "undefine"
					|| $("#" + imageElementId).val() == "") {
				callback("");
				return;
			}

			if (reg.test($("#" + imageElementId).val())) {

				$.ajaxFileUpload({
					url : postUrl, // 需要链接到服务器地址
					secureuri : false,
					type : "POST",
					fileElementId : imageElementId, // 文件选择框的id属性
					data : {
						filePath : imageElementId
					},
					dataType : 'json',
					success : function(result, status) {
						callback(result.data);
					},
					error : function(data, status, e) {
						YHu.ui.alert("图片上传失败");
					}
				});
			} else {
				YHu.ui.alert("选择jpg或者png格式的图片");
			}
		},
		fileUpload : function(fileId, postUrl, callback) {


			if ($("#" + fileId).val() == "undefine"
					|| $("#" + fileId).val() == "") {
				callback("");
				return;
			}

			$.ajaxFileUpload({
				url : postUrl, // 需要链接到服务器地址
				secureuri : false,
				type : "POST",
				fileElementId : fileId, // 文件选择框的id属性
				data : {
					filePath : fileId
				},
				dataType : 'json',
				success : function(result, status) {
					callback(result.data);
				},
				error : function(data, status, e) {
					YHu.ui.alert("文件上传失败");
				}
			});
		}
	};

	/**
	 * String：startWith
	 * 
	 * @param s
	 * @returns
	 */
	String.prototype.startWith = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substr(0, s.length) == s)
			return true;
		else
			return false;
		return true;
	};

	/**
	 * String：endWith
	 * 
	 * @param s
	 * @returns
	 */
	String.prototype.endWith = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substring(this.length - s.length) == s)
			return true;
		else
			return false;
		return true;
	};

	/**
	 * 根据制定格式转换Date为字符串
	 * 
	 * @param format
	 * @returns
	 */
	Date.prototype.format = function(format) {
		var date = {
			"M+" : this.getMonth() + 1,
			"d+" : this.getDate(),
			"h+" : this.getHours(),
			"m+" : this.getMinutes(),
			"s+" : this.getSeconds(),
			"q+" : Math.floor((this.getMonth() + 3) / 3),
			"S+" : this.getMilliseconds()
		};
		if (/(y+)/i.test(format)) {
			format = format.replace(RegExp.$1, (this.getFullYear() + '')
					.substr(4 - RegExp.$1.length));
		}
		for ( var k in date) {
			if (new RegExp("(" + k + ")").test(format)) {
				format = format.replace(RegExp.$1,
						RegExp.$1.length == 1 ? date[k] : ("00" + date[k])
								.substr(("" + date[k]).length));
			}
		}
		return format;
	};

	/**
	 * ajax全局设置
	 */
	$.ajaxSetup({
		dataFilter : function(data, type) {
			try {
				var d = $.parseJSON(data);
				var code = d.code;
				if (undefined == code || null == code
						|| !code.startWith("_GE_")) {// _GE_表示返回的是全局错误信息
					return data;
				} else {
					YHu.ui.alert(d.message);
					return d;
				}
			} catch (e) {
				return data;
			}// 异常不截取，交由业务处理
		}
	});

	// 页面格式化的一些
	showJs = {
		"amount" : YHu.util.money_point_sub
	};

	initFormat = function() {
		// 页面数据格式加载
		(function(element) {
			element.each(function() {
				if (undefined != $(this).attr("format")) {
					showJs[$(this).attr("format")]($(this));
				}
			});
		})($("*[format]"));
	};
}(window);

$(function() {
	// 输入框获取焦点移除错误信息
	$(".condis").find(':text,:password').focus(function() {
		YHu.ui.removeErrorTip($(this));
	});
	// 表格变色效果
	YHu.ui.tableTrColor($("div.data"));
	initFormat();
});
