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
	var url = contextPath + '/login/check';
	var loginForm = $('loginForm');
	var name = loginForm.loginName.value;
	var password = loginForm.loginPassword.value;
	new Ajax.Request(url, {
		method :'post',
		parameters :'loginName=' + name + '&loginPassword=' + password,
		onSuccess : function(xmlhttp) {
			var result = eval('(' + xmlhttp.responseText + ')');
			var messages;
			if (result.error) {
				messages = result.messages;
			} else {
				messages = new Array();
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
