function display(){
	$("#dailyEnglishPage").addClass("item currentItem"); // 追加样式
	highlightLeftNav("dailyEnglishPage");
	jNewsPres.displayTopNewsPresManager(function(data){
		if(data){
					var news = "<a href = './displayNews.html?newsID="
							+ data[0].id + "' target='_blank' class='nav'>"
							+ data[0].newsTitle + "</a></br>";
					for ( var i = 1; i < data.length; i++) {
						news += "<a href = './displayNews.html?newsID="
								+ data[i].id + "' target='_blank' class='nav'>"
								+ data[i].newsTitle + "</a></br>";
					}
					$("#newsDisplay").html(news + "</br>");
		}
		/*jUserLoginIn.getLoginUserSessionName(function(data){
			if(data){
				jUserLoginIn.getLoginUserSessionRole(function(role){
					if(role){
						if(role == wrConst.UserRole_Administrator || role == wrConst.UserRole_SystemAdministrator){
							$("#adminUserIsLogin").show();
							$("#userIsLogin").hide();
							$("#userNameId").css({"color":"#00688B"});
						}else{
							$("#userIsLogin").show();
							$("#adminUserIsLogin").hide();
							$("#userNameId").css({"color":"#00688B"});
						}
					}
					$("#userNameId").show();
					$("#userNameId").html(data);
					$("#userIsNotLogin").hide();
				});
				
			}else{
				$("#userNameId").html("");
				$("#userNameId").hide();
				$("#userIsLogin").hide();
				$("#adminUserIsLogin").hide();
			}
		});  */
	});
	$('.box a').mouseover(function(){
		$(this).stop().animate({"top":"-114px"}, 200); 
	});
	$('.box a').mouseout(function(){
		$(this).stop().animate({"top":"0"}, 200); 
	});

}

