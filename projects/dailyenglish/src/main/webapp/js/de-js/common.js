var wrConst = {
		Status_UserIsNotLogin : 0,
		Status_UserIsLogin : 1,
		
		UserRole_Administrator : 1,
		UserRole_SystemAdministrator : 2,
		UserRole_Member : 3,
		
		TribeApp_Read:0,
		TribeApp_Listen:1,
		TribeApp_Talk:2,
		TribeApp_Write:3
};
//jason
var pageState = new Object();



pageState.initialPost = {
		title : "",
		content : "",
		totalRecords : 0
};

var globalRecordOffset = 0;

function formatPostDate(date){
	if(date != null){
	    var dateStr = "";
	    if(date.getYear() > 100){
		    dateStr += "20" + (date.getYear())%100;
	    }else{
		    dataStr += "19" + (date.getYear())%100;
	    }
	    dateStr += "/" + String(date.getMonth() + 1);
	    dateStr += "/" + date.getDate();
	    var hour = date.getHours();
	    var minute = date.getMinutes();
	    if(hour == 0){
	     	dateStr += " 00";
	    }else if(hour>0 && hour<10){
	    	dateStr += " 0" + hour;
	    }else{
	    	dateStr += " " + hour;
	    }
	    if(minute == 0){
	     	dateStr += ":00";
	    }else if(minute>0 && minute<10){
	     	dateStr += ":0" + minute;
    	}else{
    		dateStr += ":" + minute;
    	}
    	return dateStr;
	}else{
		return "";
	}
}

function formatJavaDate(date){
	if(date != null){
	    var dateStr = "";
	    if(date.getYear() > 100){
		    dateStr += "20" + (date.getYear())%100;
	    }else{
		    dataStr += "19" + (date.getYear())%100;
	    }
	    dateStr += "/" + String(date.getMonth() + 1);
	    dateStr += "/" + date.getDate();
	    var hour = date.getHours();
	    var minute = date.getMinutes();
	    if(hour == 0){
	     	dateStr += " 00";
	    }else if(hour>0 && hour<10){
	    	dateStr += " 0" + hour;
	    }else{
	    	dateStr += " " + hour;
	    }
	    if(minute == 0){
	     	dateStr += ":00";
	    }else if(minute>0 && minute<10){
	     	dateStr += ":0" + minute;
    	}else{
    		dateStr += ":" + minute;
    	}
    	return dateStr;
	}else{
		return "";
	}
}

function clickTable(text){
	var id = text.attr('id');
	if(id == 'htitle' || id == 'hauthor' || id == 'htime'){
		if(id == 'htitle'){
			id = id.substr(1, id.length-1);
		}else if(id == 'hauthor'){
			id = "userid";
		}else if(id == 'htime'){
			id = "createtime";
		}
		sort(id);
	}else if(id == 'title'){
		showContent(text);
	}else if(id == 'author'){
		showAuthor();
	}
}

function tableSort(id){
	if(tableMeta.state.sort.key == id){
		tableMeta.state.sort.dir = Number(!tableMeta.state.sort.dir)
	}else{
		tableMeta.state.sort.key = id;
		tableMeta.state.sort.dir = 1;
	}
	makeTable(tableMeta);
	renderPaginator();
}

function showContent(text){
	alert("this is title");
}

function showAuthor(){
	alert("this is author");
}

function highlightLeftNav(li){
	document.getElementById(li).className = "active";
}