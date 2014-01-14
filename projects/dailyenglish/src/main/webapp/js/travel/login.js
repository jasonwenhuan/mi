function login() {
	var name = $("#user").attr("value");
	var psw = $("#password").attr("value");
	if (name == "Input name or email") {
		alert("Please Input your name or mail！");
	} else {
		if (psw == "") {
			alert("The password must not be null！");
		} else {
					jUserLoginIn.userLoginIn(name, psw, false,function(data) {
						if (data) {
							alert("Login Success!");
							window.location = "./home.html";
						} else {
							alert("User does not exit or password is wrong,please try again!");
							$("#password").attr("value", "");
						}
					});
		}
	}
}