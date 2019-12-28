
function registerSet(timeOut){
	Auth.vars.lowin_login.className += ' lowin-animated';
	if(timeOut){
		setTimeout(() => {
			Auth.vars.lowin_login.style.display = 'none';
		}, 500);
	}
	Auth.vars.lowin_register.style.display = 'block';
	Auth.vars.lowin_register.className += ' lowin-animated-flip';
	Auth.setHeight(Auth.vars.lowin_register.offsetHeight);
	hideErrorMsg(Auth.vars.register_error);
}
function forgotSet(timeOut){
	Auth.vars.password_group.classList += ' lowin-animated';
	Auth.vars.login_back_link.style.display = 'block';
	if(timeOut){
		setTimeout(() => {
			Auth.vars.login_back_link.style.opacity = 1;
			Auth.vars.password_group.style.height = 0;
			Auth.vars.password_group.style.margin = 0;
		}, 100);
	}
	Auth.vars.login_btn.innerText = '忘记密码';
	Auth.vars.lowin_p.innerText = '忘记密码';
	hideErrorMsg(Auth.vars.login_error);
	Auth.setHeight(Auth.vars.lowin_wrapper_height - Auth.vars.password_group_height);
	Auth.vars.login_btn.addEventListener("click", (e) => {
		fogotSub(e);
	});
}

var Auth = {
	vars: {
		lowin: document.querySelector('.lowin'),
		lowin_p: document.querySelector('.lowin-p'),
		lowin_brand: document.querySelector('.lowin-brand'),
		lowin_wrapper: document.querySelector('.lowin-wrapper'),
		lowin_login: document.querySelector('.lowin-login'),
		lowin_wrapper_height: 0,
		login_back_link: document.querySelector('.login-back-link'),
		forgot_link: document.querySelector('.forgot-link'),
		login_link: document.querySelector('.login-link'),
		login_btn: document.querySelector('.login-btn'),
		register_link: document.querySelector('.register-link'),
		password_group: document.querySelector('.password-group'),
		password_group_height: 0,
		lowin_register: document.querySelector('.lowin-register'),
		box: document.getElementsByClassName('lowin-box'),
		login_error:document.querySelector('.login-error'),
		register_error:document.querySelector('.register-error'),
		loginFrom:document.getElementById("loginFrom"),
		registerFrom:document.getElementById("registerFrom"),
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
		Auth.vars.lowin_register.classList.remove('lowin-animated-flip');
		Auth.vars.lowin_register.className += ' lowin-animated-flipback';
		Auth.vars.lowin_login.style.display = 'block';
		Auth.vars.lowin_login.classList.remove('lowin-animated');
		Auth.vars.lowin_login.className += ' lowin-animatedback';
		setTimeout(() => {
			Auth.vars.lowin_register.style.display = 'none';
		}, 500);
		
		setTimeout(() => {
			Auth.vars.lowin_register.classList.remove('lowin-animated-flipback');
			Auth.vars.lowin_login.classList.remove('lowin-animatedback');
		},1000);

		Auth.setHeight(Auth.vars.lowin_login.offsetHeight);
		hideErrorMsg(Auth.vars.login_error);
		e.preventDefault();
	},
    forgot(e) {
        forgotSet(true);
        e.preventDefault();
    },
	loginback(e) {
		Auth.vars.password_group.classList.remove('lowin-animated');
		Auth.vars.password_group.classList += ' lowin-animated-back';
		Auth.vars.password_group.style.display = 'block';

		setTimeout(() => {
			Auth.vars.login_back_link.style.opacity = 0;
			Auth.vars.password_group.style.height = Auth.vars.password_group_height + 'px';
			Auth.vars.password_group.style.marginBottom = 30 + 'px';
		}, 100);

		setTimeout(() => {
			Auth.vars.login_back_link.style.display = 'none';
			Auth.vars.password_group.classList.remove('lowin-animated-back');
		}, 1000);

        Auth.vars.login_btn.innerText = '登录';
		Auth.vars.login_btn.addEventListener("click", (e) => {
			loginSub(e);
		});
        Auth.vars.lowin_p.innerText = '登录';
        Auth.setHeight(Auth.vars.lowin_wrapper_height);
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
        }else{
			Auth.vars.login_btn.addEventListener("click", (e) => {
				loginSub(e);
			});
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

		Auth.vars.register_link.addEventListener("click", (e) => {
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

	if(isNull(loginName)){
		showErrorMsg(Auth.vars.login_error,"用户名不能为空");
		return false;
	}
	if(!USER_NAME_REGEX.test(loginName)){
		showErrorMsg(Auth.vars.login_error,"用户名格式有误,请确认后再输入");
		return false;
	}
	var passWord = formData.passWord;
	if(isNull(passWord)){
		showErrorMsg(Auth.vars.login_error,"密码不能为空");
		return false;
	}
	if(!PASS_WORD_REGEX.test(passWord)){
		showErrorMsg(Auth.vars.login_error,"密码格式有误,请确认后再输入");
		return false;
	}
	return true;
}

function fogotSub() {
	ajaxFrom({
		url: '/user/forgot',
		fromId: 'loginFrom',
		successUrl:'/login',
		errorFun:function (data) {
			showErrorMsg(Auth.vars.login_error,data.errorMsg);
		}
	});
}

function registerSub() {
	ajaxFrom({
		url: '/user/register',
		fromId: 'registerFrom',
		successUrl:'/login',
		errorFun:function (data) {
			showErrorMsg(Auth.vars.register_error,data.errorMsg);
		}
	});
}