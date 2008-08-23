Event.observe(window, "load", addLoginEvent, false);

var loginable = false;

function addLoginEvent() {
	var loginForm = $('loginForm');
	$('loginButton').disable();

	Event.observe(loginForm, 'submit', function() {
		if (!loginable) {
			return false;
		}
	});
	new Form.Observer(loginForm, 1, function(element, value) {
		validate();
	});
}

function validate() {
	var form = $('loginForm');
	var name = form.loginName.value;
	var password = form.loginPassword.value;

	var messages = new Array();
	if (name.length == 0) {
		messages.push('ユーザIDを入力してください。');
	}
	if (password.length == 0) {
		messages.push('パスワードを入力してください。');
	}
	if (messages.length > 0) {
		showLoginErrorMessages(messages);
		return;
	}

	var url = contextPath + '/login/check';
	new Ajax.Request(url, {
		method :'post',
		parameters :'loginName=' + name + '&loginPassword=' + password,
		onSuccess : function(xmlhttp) {
			var result = eval('(' + xmlhttp.responseText + ')');
			var messages = new Array();
			if (result.isError) {
				messages.push(result.errorMessage);
			}
			showLoginErrorMessages(messages);
		},
		onFailure :errorFunc
	});
}

function showLoginErrorMessages(messages) {
	var loginButton = $('loginButton');
	if (messages.length > 0) {
		loginable = false;
		if (!loginButton.disabled) {
			loginButton.disable();
		}
	} else {
		loginable = true;
		if (loginButton.disabled) {
			loginButton.enable();
		}
	}

	var messageList = $('loginErrorMessageList');
	while (messageList.firstChild != null) {
		messageList.removeChild(messageList.firstChild);
	}
	messages.each( function(message) {
		var element = new Element('li', {
			class :'loginErrorMessage'
		}).update(message);
		messageList.insert(element);
	});

}
