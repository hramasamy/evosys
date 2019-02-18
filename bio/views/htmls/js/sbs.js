// JScript source code
function onBtnClick(type) {
	var value = type.value.toLowerCase();
	if (value == "ok") {
		var msg = "OK" + " " + document.userRegForm.email.value;
		return alert(msg);
	}
	else if (value == "cancel") {
		//location.href= window.history.back();
	}
	return false;	
}

function isNotEmpty(elem) {
	var str = elem.value;
	var re = /.+/;
	if(!str.match(re)) {
		alert("Please fill in the required field.");
		return false;
	}
	else {
		return true;
	}
}

function isValidEmail(elem) {
	var str =  elem.value.toLowerCase();
	var index = parseInt(str.indexOf("@"));
	if (!isNaN(index)) {
		var addr = str.substring(0, index);
		var domain = str.substring(index +1, str.length);
		
		if (domain.indexOf(".") == -1) {
			alert("Verify the domain portion of the email address.");
			return false;
		}
		
		for (var i=0; i<addr.length; i++) {
			oneChar = addr.charAt(i).charCodeAt(0);
			//dot or hyphen not allowed as the first char; and dot as last char
			if ((i ==0 && (oneChar == 45 || oneChar == 46)) || 
			    (i == addr.length -1 && oneChar == 46)) 
			{
				alert("Verify the user name portion of the email address.");
				return false;
			}
			
			//acceptable characters (- . _ 0-9 a-z)
			if (oneChar ==45 || oneChar ==46 || oneChar ==95 ||
			    (oneChar > 47 && oneChar < 58) || (oneChar > 96 && oneChar < 123)) 
			{
			    continue;
			}
			else {
				alert ("Verify the user name portion of the email address.");
				return false;
			}
		}
		
		for (var i=0; i<domain.length; i++) {
			oneChar = domain.charAt(i).charCodeAt(0);
			//dot or hyphen not allowed as the first char; and dot as last char
			if ((i ==0 && (oneChar == 45 || oneChar == 46)) || 
			    (i == domain.length -1 && oneChar == 46)) 
			{
				alert("Verify the domain portion of the email address.");
				return false;
			}
			
			//acceptable characters (- . _ 0-9 a-z)
			if (oneChar ==45 || oneChar ==46 || oneChar ==95 ||
			    (oneChar > 47 && oneChar < 58) || (oneChar > 96 && oneChar < 123)) 
			{
			    continue;
			}
			else {
				alert ("Verify the domain portion of the email address.");
				return false;
			}
		}
		return true;
	}
	
	alert("Provided email address is not valid, please provide a valid email address!")
	return false;
}

function submitUserRegFrom(form, link) {
	if (validateUserRegForm(form)) {
		location.href = link;
	}
	return false;
}

function validateUserRegForm(form) {
	if (isNotEmpty(form.email)) {
		if (isValidEmail(form.email)) {	
			if (isNotEmpty(form.passwd)) {
				if (isNotEmpty(form.repwd)) {
					if (form.passwd.value!=form.repwd.value) {
						alert("Passcode and retyped passcode do not match, please try again.");
						form.passwd.value ="";
						form.repwd.value ="";
						return focusElement(form.name, form.passwd);
					}
					if (isNotEmpty(form.fName)) {
						if (isNotEmpty(form.lName)) {
							return true;
						}
						else {
							return focusElement(form.name, form.lName);
						}
					}
					else {
						return focusElement(form.name, form.fName);
					}
				}
				else {
					return focusElement(form.name, form.repwd);
				}
			}
			else {
				return focusElement(form.name, form.passwd);
			}
		}
		else {
			return focusElement(form.name, form.email);
		}
	}
	else {
		return focusElement(form.name, form.email);
	}
	return true;
}



function focusElement(formName, elem) {
	//var elem = document.forms[formName].elements[elemName];
	elem.focus();
	elem.select();
	return false;
}
