function commit() {
	var nickname = $("#nickname").attr("value");
	var username = $("#username").attr("value");
	var pw = $("#password").attr("value");
	var cpw = $("#cpassword").attr("value");
	var em = $("#email").attr("value");
	var sex = $("input[@type=radio][checked]").val();

	if (nickname == "") {
		alert("User name must not be null！");
	} else {
				 if (em != ""
							&& em.match("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
						if (pw != "") {
							if (cpw == "") {
								alert("Please verify your password！");
							} else {
								if (pw == cpw) {
									jUserRegister.createNewUser(nickname,username,pw,em,sex,function(data){
										if(data && data.message == "Error"){
												alert("User name or email already exists,try another please！");
												$("#email").attr("value","");
												$("#password").attr("value", "");
												$("#cpassword").attr("value", "");
										}else{
											alert("Register Success！");
											var answer = confirm("Login right now？ ");
											if (answer == true) {
												window.location = "login.html";
											}
										}
									});
								} else {
									alert("Your passwords don't identical,please try again！");
								}
							}
						} else {
							alert("Please input password！");
						}
					} else {
						alert("The format of email is wrong！");
					}
			}

}