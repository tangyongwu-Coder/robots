var Script = function () {

	$.validator.setDefaults({
		submitHandler: function() {

		}
	});

    $().ready(function() {
		$("#loginFrom").validate({
			rules: {
				loginName: {
					required: true,
					userNameCheck: true
				},
				passWord: {
					required: true,
					passWordCheck: true
				}
			},
			messages: {
				loginName: {
					required: "用户名不能为空",
				},
				passWord: {
					required: "密码不能为空",
				}
			}
		});
	});


}();






function loginSub() {
	var validRet = $("#loginFrom").valid();
	if(!validRet){
		return;
	}
	ajaxFrom({
		url: '/user/login',
		fromId: 'loginFrom',
		successUrl:'/index',
		errorFun:function (data) {
			SweetAlert.warning('',data.errorMsg);
		}
	});
}

function forgotSub() {
	if(!forgotValidate('forgotFrom')) {
		return;
	}
	ajaxFrom({
		url: '/user/forgot',
		fromId: 'forgotFrom',
		successUrl:'/login',
		errorFun:function (data) {
			showErrorMsg(Auth.vars.forgot_error,data.errorMsg);
		}
	});
}

function forgotValidate(fromId){
	var formData = fromDataArr(fromId);
	var errorLabel = Auth.vars.forgot_error;

	var email = formData.email;
	if(isNull(email)){
		showErrorMsg(errorLabel,"邮箱不能为空");
		return false;
	}
	if(!ValidatePatten.EMAIL_REGEX.test(email)){
		showErrorMsg(errorLabel,"邮箱格式不正确,请重新输入");
		return false;
	}
	var passWord = formData.passWord;
	if(isNull(passWord)){
		showErrorMsg(errorLabel,"请输入邮箱验证码");
		return false;
	}
	if(passWord.length != 6){
		showErrorMsg(errorLabel,"验证码格式有误,请确认后再输入");
		return false;
	}
	return true;
}

function registerSub() {
	if(!registerValidate('registerFrom')) {
		return;
	}
	ajaxFrom({
		url: '/user/register',
		fromId: 'registerFrom',
		successUrl:'/login',
		errorFun:function (data) {
			showErrorMsg(Auth.vars.register_error,data.errorMsg);
		}
	});
}

function registerValidate(fromId){
	var formData = fromDataArr(fromId);
	var loginName = formData.loginName;
	var errorLabel = Auth.vars.register_error;
	if(isNull(loginName)){
		showErrorMsg(errorLabel,"用户名不能为空");
		return false;
	}
	if(!ValidatePatten.USER_NAME_REGEX.test(loginName)){
		showErrorMsg(errorLabel,"用户名必须是6-16位以字母开头且由字母数字下划线组成");
		return false;
	}

	var email = formData.email;
	if(isNull(email)){
		showErrorMsg(errorLabel,"邮箱不能为空");
		return false;
	}
	if(!ValidatePatten.EMAIL_REGEX.test(email)){
		showErrorMsg(errorLabel,"邮箱格式不正确,请重新输入");
		return false;
	}

	var passWord = formData.passWord;
	if(isNull(passWord)){
		showErrorMsg(errorLabel,"密码不能为空");
		return false;
	}
	if(!ValidatePatten.PASS_WORD_REGEX.test(passWord)){
		showErrorMsg(errorLabel,"密码必须由字母+数字组合，长度大于8位");
		return false;
	}

	var passWord2 = formData.passWord2;
	if(isNull(passWord2)){
		showErrorMsg(errorLabel,"确认密码密码不能为空");
		return false;
	}
	if(passWord != passWord2){
		showErrorMsg(errorLabel,"两次输入密码不一致,请确认后重新输入");
		return false;
	}
	return true;
}