function displayTopNav(){
		jUserLoginIn.getLoginUserSessionName(function(data){
			if(data){
				jUserLoginIn.getLoginUserSessionRole(function(role){
					if(role){
						if(role == wrConst.UserRole_Administrator || role == wrConst.UserRole_SystemAdministrator){
							$("#adminWelcome").show();
							$("#adminHomePage").show();
							$("#adminUserLogout").show();
							$("#userWelcome").hide();
							$("#homePage").hide();
							$("#userLogout").hide();
							$("#userNameId").css({"color":"#00688B"});
						}else{
							/*$("#userIsLogin").show();*/
							$("#userWelcome").show();
							$("#homePage").show();
							$("#userLogout").show();
							$("#adminWelcome").hide();
							$("#adminHomePage").hide();
							$("#adminUserLogout").hide();
							$("#userNameId").css({"color":"#00688B"});
						}
					}
					$("#userNameId").show();
					$("#userNameId").html(data);
					$("#userIsNotLogin").hide();
					$("#toRegister").hide();
					
				});
				
			}else{
				$("#userNameId").html("");
				$("#userNameId").hide();
				$("#userWelcome").hide();
				$("#homePage").hide();
				$("#userLogout").hide();
				$("#adminWelcome").hide();
				$("#adminHomePage").hide();
				$("#adminUserLogout").hide();
			}
		});  
		$('li.mainlevel').mousemove(function(){
			$(this).find('ul').slideDown();//you can give it a speed
		});
		$('li.mainlevel').mouseleave(function(){
			$(this).find('ul').slideUp("fast");
		});

}

function userLogout(userName){
	//var b = $("#userName").text();
	jUserLoginIn.userLogout(userName,function(data) {
		$("#adminWelcome").hide();
		$("#adminHomePage").hide();
		$("#adminUserLogout").hide();
		$("#userNameId").html("");
		$("#userNameId").hide();
		$("#userIsNotLogin").show();
		$("#toRegister").show();
		$("#userWelcome").hide();
		$("#homePage").hide();
		$("#userLogout").hide();
		/*adminUserIsLogin*/
		$("#adminWelcome").hide();
		$("#adminHomePage").hide();
		$("#adminUserLogout").hide();
		
		window.location = "../pages/dailyEnglishPage.html";
	});
}

function goToDailyEnglishPage(){
	window.location = "../pages/dailyEnglishPage.html";
}

function goToEnglishTribePage(id){
	if(id == wrConst.TribeApp_Read){
		window.location = "../pages/dailyEnglishTribe.html/read";
	}else if(id == wrConst.TribeApp_Listen){
		window.location = "../pages/dailyEnglishTribe.html/listen";
	}else if(id == wrConst.TribeApp_Talk){
		window.location = "../pages/dailyEnglishTribe.html/talk";
	}else if(id == wrConst.TribeApp_Write){
		window.location = "../pages/dailyEnglishTribe.html/write";
	}else{
		window.location = "../pages/dailyEnglishTribe.html";
	}
	
}

function goToLoginPage(){
	window.location = "../include/login.html";
}

function goToRegisterPage(){
	window.location = "../include/register.html";
}

function goToAdminHomePage(){
	window.location = "../pages/userHomePage.html?adminId=" + $("#userNameId").value;
}

function goToHomePage(){
	window.location = "../pages/userHomePage.html?userId=" + $("#userNameId").value;
}

/*function showSubMenu(li){
	$("ul#subMenu").show();
} 

function hideSubMenu(li) { 
	$("ul#subMenu").hide();
}*/ 

