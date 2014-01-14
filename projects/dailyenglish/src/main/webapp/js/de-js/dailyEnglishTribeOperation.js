function display(){
	highlightLeftNav("englishTribe");
}
	
function tabColorChanged(id){
	if(id == wrConst.TribeApp_Read){
		$("#app_read").css({"background-color":"#F0F2F4","font-size":"150%"});
	}else if(id == wrConst.TribeApp_Listen){
		$("#app_listen").css({"background-color":"#F0F2F4","font-size":"150%"});
	}else if(id == wrConst.TribeApp_Talk){
		$("#app_talk").css({"background-color":"#F0F2F4","font-size":"150%"});
	}else if(id == wrConst.TribeApp_Write){
		$("#app_write").css({"background-color":"#F0F2F4","font-size":"150%"});
	}
}

function tabCoulorChangedBack(id){
	if(id == wrConst.TribeApp_Read){
		$("#app_read").css({"background-color":"#F7F7F7","font-size":"100%"});
	}else if(id == wrConst.TribeApp_Listen){
		$("#app_listen").css({"background-color":"#F7F7F7","font-size":"100%"});
	}else if(id == wrConst.TribeApp_Talk){
		$("#app_talk").css({"background-color":"#F7F7F7","font-size":"100%"});
	}else if(id == wrConst.TribeApp_Write){
		$("#app_write").css({"background-color":"#F7F7F7","font-size":"100%"});
	}
	
}

