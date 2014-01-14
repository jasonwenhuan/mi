function commit() {
	var nickname = $("#nickname").attr("value");
	var username = $("#username").attr("value");
	var pw = $("#password").attr("value");
	var cpw = $("#cpassword").attr("value");
	var em = $("#email").attr("value");
	var sex = $("input[@type=radio][checked]").val();

	if (nickname == "") {
		alert("用户名不能为空！");
	} else {
				 if (em != ""
							&& em.match("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
						if (pw != "") {
							if (cpw == "") {
								alert("请输入密码！");
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
									alert("两次输入密码不相同，请重新输入！");
								}
							}
						} else {
							alert("请输入密码！");
						}
					} else {
						alert("邮件格式不正确！");
					}
			}

}