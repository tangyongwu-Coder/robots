
function registerSet(timeOut){
	Auth.vars.lowin_login.className += ' lowin-animated';
	Auth.vars.lowin_forgot.className += ' lowin-animated';
	if(timeOut){
		setTimeout(() => {
			Auth.vars.lowin_login.style.display = 'none';
			Auth.vars.lowin_forgot.style.display = 'none';
		}, 500);
	}
	Auth.vars.lowin_register.style.display = 'block';
	Auth.vars.lowin_register.className += ' lowin-animated-flip';
	Auth.setHeight(Auth.vars.lowin_register.offsetHeight);
	hideErrorMsg(Auth.vars.register_error);
}
function forgotSet(timeOut){
	Auth.vars.lowin_register.className += ' lowin-animated';
	Auth.vars.lowin_login.className += ' lowin-animated';
	if(timeOut){
		setTimeout(() => {
			Auth.vars.lowin_register.style.display = 'none';
			Auth.vars.lowin_login.style.display = 'none';
		}, 500);
		/*setTimeout(() => {
			Auth.vars.lowin_register.classList.remove('lowin-animated-flipback');
			Auth.vars.lowin_login.classList.remove('lowin-animatedback');
		},1000);*/
	}
	Auth.vars.lowin_forgot.style.display = 'block';
	Auth.vars.lowin_forgot.className += ' lowin-animated-flip';
	Auth.setHeight(Auth.vars.lowin_forgot.offsetHeight);
	hideErrorMsg(Auth.vars.forgot_error);
}

function loginSet(timeOut){
	Auth.vars.lowin_register.className += ' lowin-animated';
	Auth.vars.lowin_forgot.className += ' lowin-animated';
	if(timeOut){
		setTimeout(() => {
			Auth.vars.lowin_register.style.display = 'none';
			Auth.vars.lowin_forgot.style.display = 'none';
		}, 500);

		setTimeout(() => {
			Auth.vars.lowin_register.classList.remove('lowin-animated-flipback');
			Auth.vars.lowin_forgot.classList.remove('lowin-animatedback');
		},1000);
	}
	Auth.vars.lowin_login.style.display = 'block';
	Auth.vars.lowin_login.classList.remove('lowin-animated');
	Auth.vars.lowin_login.className += ' lowin-animatedback';
	Auth.setHeight(Auth.vars.lowin_login.offsetHeight);
	hideErrorMsg(Auth.vars.login_error);
}

var Auth = {
	vars: {
		lowin: document.querySelector('.lowin'),
		lowin_p: document.querySelector('.lowin-p'),
		lowin_brand: document.querySelector('.lowin-brand'),
		lowin_wrapper: document.querySelector('.lowin-wrapper'),

		lowin_wrapper_height: 0,
		login_back_link: document.querySelector('.login-back-link'),
		forgot_link: document.querySelector('.forgot-link'),
		login_link: document.querySelector('.login-link'),
		login_btn: document.querySelector('.login-btn'),
		login_register_link: document.querySelector('.login-register-link'),
		forgot_register_link: document.querySelector('.forgot-register-link'),
		password_group: document.querySelector('.password-group'),
		password_group_height: 0,


		box: document.getElementsByClassName('lowin-box'),


		loginFrom:document.getElementById("loginFrom"),
		registerFrom:document.getElementById("registerFrom"),
		//错误label
		login_error:document.querySelector('.login-error'),
		register_error:document.querySelector('.register-error'),
		forgot_error:document.querySelector('.forgot-error'),
		//页面
		lowin_login: document.querySelector('.lowin-login'),
		lowin_forgot: document.querySelector('.lowin-forgot'),
		lowin_register: document.querySelector('.lowin-register'),
		option: {}
	},

    setHeight(height) {
        Auth.vars.lowin_wrapper.style.minHeight = height + 'px';
    },
    brand() {
        Auth.vars.lowin_brand.classList += ' lowin-animated';
        setTimeout(() => {
            Auth.vars.lowin_brand.classList.remove('lowin-animated');
        }, 1000);
    },

	register(e) {
        registerSet(true);
		e.preventDefault();
	},
	login(e) {
		loginSet(true);
		e.preventDefault();
	},
    forgot(e) {
        forgotSet(true);
        e.preventDefault();
    },
	loginback(e) {
		loginSet(true);
		e.preventDefault();
	},


	init(option) {
		Auth.setHeight(Auth.vars.box[0].offsetHeight);

		Auth.vars.password_group.style.height = Auth.vars.password_group.offsetHeight + 'px';
		Auth.vars.password_group_height = Auth.vars.password_group.offsetHeight;
		Auth.vars.lowin_wrapper_height = Auth.vars.lowin_wrapper.offsetHeight;

		Auth.vars.login_error.style.display = 'none';
		Auth.vars.register_error.style.display = 'none';

		Auth.vars.option = option;
		var type = option.type;
        if("register"==type){
            registerSet(false);
        }else if("forgot"==type){
            forgotSet(false);
        }else if("login"==type){
			loginSet(false);
		}

		var len = Auth.vars.box.length - 1;

		for(var i = 0; i <= len; i++) {
			if(i !== 0) {
				Auth.vars.box[i].className += ' lowin-flip';
			}
		}

		Auth.vars.forgot_link.addEventListener("click", (e) => {
			Auth.forgot(e);
		});

		Auth.vars.login_register_link.addEventListener("click", (e) => {
			Auth.brand();
			Auth.register(e);
		});
		Auth.vars.forgot_register_link.addEventListener("click", (e) => {
			Auth.brand();
			Auth.register(e);
		});
		Auth.vars.login_link.addEventListener("click", (e) => {
			Auth.brand();
			Auth.login(e);
		});

		Auth.vars.login_back_link.addEventListener("click", (e) => {
			Auth.loginback(e);
		});
	}
}
function loginSub() {
	if(!loginValidate('loginFrom')){
		return;
	}
	ajaxFrom({
		url: '/user/login',
		fromId: 'loginFrom',
		successUrl:'/index',
		errorFun:function (data) {
			showErrorMsg(Auth.vars.login_error,data.errorMsg);
		}
	});
}


function loginValidate(fromId){
	var formData = fromDataArr(fromId);
	var loginName = formData.loginName;
	var errorLabel = Auth.vars.forgot_error;
	if(isNull(loginName)){
		showErrorMsg(errorLabel,"用户名不能为空");
		return false;
	}
	if(!ValidatePatten.USER_NAME_REGEX.test(loginName)){
		showErrorMsg(errorLabel,"用户名格式有误,请确认后再输入");
		return false;
	}
	var passWord = formData.passWord;
	if(isNull(passWord)){
		showErrorMsg(errorLabel,"密码不能为空");
		return false;
	}
	if(!ValidatePatten.PASS_WORD_REGEX.test(passWord)){
		showErrorMsg(errorLabel,"密码格式有误,请确认后再输入");
		return false;
	}
	return true;
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
			showErrorMsg(Auth.vars.login_error,data.errorMsg);
		}
	});
}

function forgotValidate(fromId){
	var formData = fromDataArr(fromId);
	var errorLabel = Auth.vars.login_error;

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