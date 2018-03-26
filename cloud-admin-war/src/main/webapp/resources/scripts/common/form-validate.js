//以下为修改jQuery Validation插件兼容Bootstrap的方法，没有直接写在插件中是为了便于插件升级
$.validator.setDefaults({
	highlight : function(element) {
		$(element).closest('.form-group').removeClass('has-success').addClass(
				'has-error');
	},
	success : function(element) {
		element.closest('.form-group').removeClass('has-error').addClass(
				'has-success');
	},
	errorElement : "span",
	errorPlacement : function(error, element) {
		if (element.is(":radio") || element.is(":checkbox")) {
			error.appendTo(element.parent().parent().parent());
		} else {
			error.appendTo(element.parent());
		}
	},
	errorClass : "help-block m-b-none",
	validClass : "help-block m-b-none"

});

// 手机号码验证
jQuery.validator
		.addMethod(
				"isMobile",
				function(value, element) {
					var length = value.length;
					var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
					return this.optional(element)
							|| (length == 11 && mobile.test(value));
				}, "请正确填写您的手机号码");

// 用户名验证
jQuery.validator.addMethod("isUserName", function(value, element) {
	var length = value.length;
	var userName = /^[A-Za-z]\w{4,19}$/;
	return this.optional(element) || (userName.test(value));
}, "用户名为5~20位字母数字下划线组合，不能以数字和下划线开头");

// 密码
jQuery.validator.addMethod("isPassword", function(value, element) {
	var length = value.length;
	var password = /^[A-Za-z]\w{7,19}$/;
	return this.optional(element) || (password.test(value));
}, "密码为8~20位字母数字下划线组合，不能以数字和下划线开头");

// 不相等
jQuery.validator.addMethod("notEqualTo", function(value, element, param) {
	return value != $(param).val();
}, $.validator.format("新密码不能和原密码相同"));

// 单价
jQuery.validator.addMethod("price", function(value, element) {
	var price = /^(([1-9]\d{0,9})|0)(\.\d{1,2})?$/;
	return this.optional(element) || (price.test(value));
}, "请输入正确的单价");

// 经度
jQuery.validator.addMethod("isLon", function(value, element, param) {
	var regex = /^-?((0|[1-9]\d?|1[1-7]\d)(\.\d{1,7})?|180(\.0{1,7})?)?$/;
	return this.optional(element) || (regex.test(value));
}, $.validator.format("请输入正确的经度值"));

// 纬度
jQuery.validator.addMethod("isLati", function(value, element, param) {
	var regex = /^-?((0|[1-8]?\d|)(\.\d{1,7})?|90(\.0{1,7})?)?$/;
	return this.optional(element) || (regex.test(value));
}, $.validator.format("请输入正确的纬度值"));

// 数据枚举
jQuery.validator.addMethod("isDataEnum", function(value, element, param) {
	var regex = /^\d{1,4}$/;
	return this.optional(element) || (regex.test(value));
}, $.validator.format("只能输入四位数字"));

// 坐标
jQuery.validator.addMethod("isPosition", function(value, element, param) {
	var regex = /^(\d|[1-9]\d)$/;
	return this.optional(element) || (regex.test(value));
}, $.validator.format("输入的坐标不符合"));

// // 用户名
// jQuery.validator.addMethod("isUserName", function(value, element, param) {
// 	var regex = /^[A-Za-z0-9]+$/;
// 	return this.optional(element) || (regex.test(value));
// }, $.validator.format("输入的用户名不符合"));

// ip验证
jQuery.validator
		.addMethod(
				'isIp',
				function(value, element, param) {
					var temp = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/;
					return this.optional(element) || (temp.test(value));
				}, '格式：255.255.255.1');
/* 端口校验 /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/ */
jQuery.validator.addMethod('isPort', function(value, element, param) {
	var temp = /^([0-9]|[1-9]\d{1,3}|[1-5]\d{4}|6[0-5]{2}[0-3][0-5])$/;
	return this.optional(element) || (temp.test(value));
}, '端口号：0-65535');

// 正整数验证
jQuery.validator.addMethod("isMaths", function(value, element) {
	var regex = /^[1-9]\d*$/;
	return this.optional(element) || (regex.test(value));
}, "请输入正整数");

// rtu  机器码  32位数字字母验证
jQuery.validator.addMethod("isMachineCode", function (value, element) {
    var regex = /^[a-zA-Z0-9]{32}$/;
    return this.optional(element) || (regex.test(value));
}, "请输入正确的32位数字或字母");

// rtu  序列码  20位数字
jQuery.validator.addMethod("isSnNumber", function (value, element) {
    var regex = /^[0-9]{20}$/;
    return this.optional(element) || (regex.test(value));
}, "请输入正确的20位数字或字母");

// 正整数验证
jQuery.validator
		.addMethod(
				"isMath",
				function(value, element) {
					var regex = /^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$/;
					return this.optional(element) || (regex.test(value));
				}, "请输入正确的数字");

$().ready(function() {

	var icon = "<i class='fa fa-times-circle'></i> ";

	$("#loginForm").validate({
		rules : {
			username : {
				required : true
				// ,
				// rangelength : [ 5, 16 ]
			},
			password : {
				required : true
				// ,
				// rangelength : [ 8, 20 ]
			}
		},
		messages : {
			username : {
				required : icon + "请输入您的用户名"
				// ,
				// rangelength : icon + "用户名是长度为 {0} 至 {1} 之间的字符串"
			},
			password : {
				required : icon + "请输入您的密码"
				// ,
				// rangelength : icon + "密码是长度为 {0} 至 {1} 之间的字符串"
			}
		}
	});

	$('#modifyPasswordForm').validate({
		rules : {
			oldPassword : {
				required : true,
				rangelength : [ 8, 20 ],
				isPassword : true
			},
			newPassword : {
				required : true,
				rangelength : [ 8, 20 ],
				isPassword : true,
				notEqualTo : "#oldPassword"
			},
			repeatPassword : {
				required : true,
				rangelength : [ 8, 20 ],
				equalTo : "#newPassword"
			}
		},
		messages : {
			oldPassword : {
				rangelength : '密码长度为 {0} 到 {1} 个字符之间'
			},
			newPassword : {
				rangelength : '密码长度为 {0} 到 {1} 个字符之间',
				notEqualTo : '新密码不能和原密码相同'
			},
			repeatPassword : {
				rangelength : '密码长度为 {0} 到 {1} 个字符之间',
				equalTo : '请保证两次输入的密码相同'
			}
		}
	});

	$('#addAdminUserForm').validate({
		rules : {
			addUserName : {
				required : true,
				isUserName : true
			},
			addPassword : {
				required : true,
				isPassword : true
			},
            addRepeatPassword:{
                required : true,
                equalTo : "#addPassword"
			},
			addPhone : {
                required : true,
				isMobile : true
			},
			addEmail : {
				maxlength : 64,
				email : true
			},
			// addJob : {
			// 	maxlength : 20
			// },
			addDesc : {
				maxlength : 40
			},
			addRoleInfo : {
				required : true
			},
			addRealName : {
				maxlength : 32
			}
		},
		messages : {
			addUserName : {
				required : '请输入登录名',
				isUserName : '用户名为5~20位字母数字下划线组合，不能以数字和下划线开头'
			},
			addPassword : {
				required : '请输入密码',
				isPassword : '密码为8~20位字母数字下划线组合，不能以数字和下划线开头'
			},
            addRepeatPassword:{
                required : '请输入验证密码',
                equalTo : '请保证两次输入的密码相同'
            },
			addPhone : {
                required : '请输入电话号码',
				isMobile : '请输入正确格式的电话号码'
			},
			addEmail : {
				maxlength : '输入内容过长',
				email : '请输入正确的email地址'
			},
			// addJob : {
			// 	maxlength : '输入内容过长'
			// },
			addDesc : {
				maxlength : '输入内容过长'
			},
			addRoleInfo : {
				required : '请选择角色'
			},
			addRealName : {
				maxlength : '姓名字符串长度必须小于{0}'
			}
		}
	});

	$('#modifyAdminUserForm').validate({
		rules : {
			modifyUserName : {
				required : true,
				isUserName : true
			},
			modifyPassword : {
				required : true,
				isPassword : true
			},
            modifyRepeatPassword : {
				required : true,
                equalTo : "#modifyPassword"
			},
			modifyPhone : {
                required : true,
				isMobile : true
			},
			modifyEmail : {
				maxlength : 64,
				email : true
			},
			// modifyJob : {
			// 	maxlength : 20
			// },
			modifyDesc : {
				maxlength : 40
			},
			modifyRoleInfo : {
				required : true
			},
			modifyRealName : {
				maxlength : 32
			}
		},
		messages : {
			modifyUserName : {
				required : '请输入登录名',
				isUserName : '用户名为5~20位字母数字下划线组合，不能以数字和下划线开头'
			},
			modifyPassword : {
				required : '请输入密码',
				isPassword : '密码为8~20位字母数字下划线组合，不能以数字和下划线开头'
			},
            modifyRepeatPassword : {
                required : '不能为空',
                equalTo : '请保证两次输入的密码相同'
            },
			modifyPhone : {
                required : '请输入电话号码',
                isMobile : '请输入正确格式的电话号码'
			},
			modifyEmail : {
				maxlength : '输入内容过长',
				email : '请输入正确形式的email地址'
			},
			// modifyJob : {
			// 	maxlength : '输入内容过长'
			// },
			modifyDesc : {
				maxlength : '输入内容过长'
			},
			modifyRoleInfo : {
				required : '请选择角色'
			},
			modifyRealName : {
				maxlength : '姓名字符串长度必须小于{0}'
			}
		}
	});

	$('#resetPasswordForm').validate({
		rules : {
			newPassword : {
				required : true,
				isPassword : true
			},
			repeatPassword : {
				required : true,
				equalTo : "#newPassword"
			}
		},
		messages : {
			newPassword : {
				required : '请输入新密码',
				isPassword : '密码为8~20位字母数字下划线组合，不能以数字和下划线开头'
			},
			repeatPassword : {
				required : '请输入确认密码',
				equalTo : '请保证两次输入的密码相同'
			}
		}
	});

	// $('#addMenuForm').validate({
	// 	rules : {
	// 		addMenuName : {
	// 			required : true,
	// 			maxlength : 32
	// 		},
	// 		addMenuUrl : {
	// 			required : true,
	// 			maxlength : 128
	// 		},
	// 		addMenuSort : {
	// 			required : true,
	// 			digits : true
	// 		},
	// 		addMenuIcon : {
	// 			maxlength : 32
	// 		}
	// 	},
	// 	messages : {
	// 		addMenuName : {
	// 			required : '菜单名称不能为空',
	// 			maxlength : '菜单名称字符串长度必须小于{0}'
	// 		},
	// 		addMenuUrl : {
	// 			required : '菜单地址不能为空',
	// 			maxlength : '菜单地址字符串长度必须小于{0}'
	// 		},
	// 		addMenuSort : {
	// 			required : '菜单排序不能为空',
	// 			digits : '只能填写数字'
	// 		},
	// 		addMenuIcon : {
	// 			maxlength : '菜单图标字符串长度必须小于{0}'
	// 		}
	// 	}
	// });

	// $('#modifyMenuForm').validate({
	// 	rules : {
	// 		modifyMenuName : {
	// 			required : true,
	// 			maxlength : 32
	// 		},
	// 		modifyMenuUrl : {
	// 			required : true,
	// 			maxlength : 128
	// 		},
	// 		modifySort : {
	// 			required : true,
	// 			digits : true
	// 		},
	// 		modifyMenuIcon : {
	// 			maxlength : 32
	// 		}
	// 	},
	// 	messages : {
	// 		modifyMenuName : {
	// 			required : '菜单名称不能为空',
	// 			maxlength : '菜单名称字符串长度必须小于{0}'
	// 		},
	// 		modifyMenuUrl : {
	// 			required : '菜单地址不能为空',
	// 			maxlength : '菜单地址字符串长度必须小于{0}'
	// 		},
	// 		modifySort : {
	// 			required : '菜单排序不能为空',
	// 			digits : '只能填写数字'
	// 		},
	// 		modifyMenuIcon : {
	// 			maxlength : '菜单图标字符串长度必须小于{0}'
	// 		}
	// 	}
	// });
    //
	// $("#addRoleForm").validate({
	// 	rules : {
	// 		addRoleName : {
	// 			required : true,
	// 			maxlength : 64
	// 		},
	// 		addDesc : {
	// 			maxlength : 128
	// 		}
	// 	},
	// 	messages : {
	// 		addRoleName : {
	// 			required : '角色名称不能为空',
	// 			maxlength : '角色名称字符串长度必须小于{0}'
	// 		},
	// 		addDesc : {
	// 			maxlength : '角色备注字符串长度必须小于{0}'
	// 		}
	// 	}
	// });
    //
	// $("#modifyRoleForm").validate({
	// 	rules : {
	// 		modifyRoleName : {
	// 			required : true,
	// 			maxlength : 64
	// 		},
	// 		modifyDesc : {
	// 			maxlength : 128
	// 		}
	// 	},
	// 	messages : {
	// 		modifyRoleName : {
	// 			required : '角色名称不能为空',
	// 			maxlength : '角色名称字符串长度必须小于{0}'
	// 		},
	// 		modifyDesc : {
	// 			maxlength : '角色备注字符串长度必须小于{0}'
	// 		}
	// 	}
	// });

	// /* 新增用户 */
	// $('#addManagerForm').validate({
	// 	rules : {
	// 		addManagerName : {
	// 			required : true,
	// 			maxlength : 32
	// 		},
	// 		addLoginName : {
	// 			required : true,
	// 			rangelength : [ 5, 16 ],
	// 			isUserName : true
	// 		},
	// 		addPassword : {
	// 			required : true,
	// 			rangelength : [ 8, 20 ],
	// 			isPassword : true
	// 		}
	//
	// 	},
	// 	messages : {
	// 		addManagerName : {
	// 			required : '用户姓名不能为空',
	// 			maxlength : '用户姓名字符串长度必须小于{0}'
	// 		},
	// 		addLoginName : {
	// 			required : '用户登录名不能为空',
	// 			rangelength : '登录名长度为 {0} 到 {1} 个字符之间',
	// 			isUserName : '用户名必须是字母、数字和下划线,字母开头的5~20位的组合'
	// 		},
	// 		addPassword : {
	// 			required : '登录密码不能为空',
	// 			rangelength : '密码长度为 {0} 到 {1} 个字符之间',
	// 			isPassword : '密码必须是字母、数字和下划线,字母开头的8~20位的组合'
	// 		},
	// 		addDepartment : {
	// 			required : '所在部门不能为空'
	// 		}
	// 	}
	// });
    //
	// $('#modifyManagerForm').validate({
	// 	rules : {
	// 		modifyManagerName : {
	// 			required : true,
	// 			maxlength : 32
	// 		},
	// 		modifyLoginName : {
	// 			required : true,
	// 			rangelength : [ 5, 16 ],
	// 			isUserName : true
	// 		},
	// 		modifyPassword : {
	// 			required : true,
	// 			rangelength : [ 8, 20 ],
	// 			isPassword : true
	// 		},
	// 		modifyDepartment : {
	// 			required : true
	// 		}
	// 	},
	// 	messages : {
	// 		modifyManagerName : {
	// 			required : '用户姓名不能为空',
	// 			maxlength : '用户姓名字符串长度必须小于{0}'
	// 		},
	// 		modifyLoginName : {
	// 			required : '用户登录名不能为空',
	// 			rangelength : '登录名长度为 {0} 到 {1} 个字符之间',
	// 			isUserName : '用户名必须是字母、数字和下划线,字母开头的5~20位的组合'
	// 		},
	// 		modifyPassword : {
	// 			required : '登录密码不能为空',
	// 			rangelength : '密码长度为 {0} 到 {1} 个字符之间',
	// 			isPassword : '密码必须是字母、数字和下划线,字母开头的8~20位的组合'
	// 		},
	// 		modifyDepartment : {
	// 			required : '所在部门不能为空'
	// 		}
	// 	}
	// });
	//

    //增加RTU设备校验
    $("#addRtuForm").validate({
        rules : {
            addRtuName : {
                required : true,
                maxlength: 32
            },
            addRtuModel : {
                required : true,
                maxlength: 16
            },
            addMachineCode:{
                required : true,
                isMachineCode:true
            },
            addSnNumber: {
                required: true,
                isSnNumber: true
            },
            addRtuAccessType: {
                required : true
            },
            addRtuTransType: {
                required : true
            }
        },
        messages : {
            addRtuName : {
                required : "网关名称不能为空",
                maxlength: "网关名称字符串长度必须小于{32}"
            },
            addRtuModel : {
                required : "网关型号不能为空",
                maxlength: "网关型号字符串长度必须小于{16}"
            },
            addMachineCode: {
                required : "网关机器码不能为空",
                isMachineCode: "请输入正确的32位数字或字母组合"
            },
            addSnNumber: {
                required : "网关序列码不能为空",
                isSnNumber:"请输入正确的20位数字"
            },
            addRtuAccessType: {
                required : "请选择网关接入类型"
            },
            addRtuTransType: {
                required : "请选择网关传输类型"
            }
        }

    });


//修改RTU设备校验
    $("#modifyRtuForm").validate({
        rules : {
            modifyRtuName : {
                required : true,
                maxlength: 32
            },
            modifyRtuModel : {
                required : true,
                maxlength: 16
            },
            modifyRtuAccessType: {
                required : true
            },
            modifyRtuTransType: {
                required : true
            },
            modifySnNumber: {
                required: true,
                isSnNumber:true
            }
        },
        messages : {
            modifyRtuName : {
                required : "RTU名称不能为空",
                maxlength: "RTU名称字符串长度必须小于{32}"
            },
            modifyRtuModel : {
                required : "RTU型号不能为空",
                maxlength: "RTU型号字符串长度必须小于{16}"
            },
            modifyRtuAccessType: {
                required : "请选择网关接入类型"
            },
            modifyRtuTransType: {
                required : "请选择网关传输类型"
            },
            modifySnNumber: {
                required : "网关序列码不能为空",
                isSnNumber:"请输入正确的20位数字"
            }
        }

    });


});
