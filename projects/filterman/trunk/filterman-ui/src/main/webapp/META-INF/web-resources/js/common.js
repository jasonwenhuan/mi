$(function(){
   initDialog();
   bindCssEvent();
   bindEvents();
   bindNavigationEvents();
   cleanSelectedGroup();
   disableAllButtons();
});


var inputPattern = /[@#\$%\^&\*]/g;

var regEx = new RegExp(/[@#\$%\^&\*]/g);

Date.prototype.format = function (fmt) {  
    var o = {
        "M+": this.getMonth() + 1, 
        "d+": this.getDate(), 
        "h+": this.getHours(),  
        "m+": this.getMinutes(),  
        "s+": this.getSeconds(),  
        "q+": Math.floor((this.getMonth() + 3) / 3),  
        "S": this.getMilliseconds() 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var currentSelectedAppGroup;
var currentSelectedAppGroupName;

function initDialog(){
	
}

function bindCssEvent(){
	$(".fmsearchButton").mouseover(function(){
	    $(".fmsearchButton").css("background-color","#E9E9E4");
	});
	$(".fmsearchButton").mouseout(function(){
	    $(".fmsearchButton").css("background-color","#DBCE8F");
	});
	
	$(".fmresetButton").mouseover(function(){
	    $(".fmresetButton").css("background-color","#E9E9E4");
	});
	$(".fmresetButton").mouseout(function(){
	    $(".fmresetButton").css("background-color","#DBCE8F");
	});
	
	$(".changeButton").mouseover(function(){
	    $(".changeButton").css("background-color","#E9E9E4");
	});
	$(".changeButton").mouseout(function(){
	    $(".changeButton").css("background-color","#DBCE8F");
	});
	
	$(".addButton").mouseover(function(){
	    $(".addButton").css("background-color","#E9E9E4");
	});
	$(".addButton").mouseout(function(){
	    $(".addButton").css("background-color","#DBCE8F");
	});
	
	$(".removeButton").mouseover(function(){
	    $(".removeButton").css("background-color","#E9E9E4");
	});
	$(".removeButton").mouseout(function(){
	    $(".removeButton").css("background-color","#DBCE8F");
	});
}

function bindEvents(){
	$(".searchButton").click(function(){
		findAppByName();
	});
	
	$(".resetButton").click(function(){
		resetAppSearch();
	});
	
	$(".fmsearchButton").click(function(){
		searchTable();
	});
	
	$(".fmresetButton").click(function(){
		resetTable();
	});
	
	$(".changeButton").click(function(){
		crudChange();
	});
	
	$(".addButton").click(function(){
		add();
	});
	
	$(".removeButton").click(function(){
		remove();
	});
}

function bindNavigationEvents(){
	$(".payBindManager").click(function(){
		goToSearch();
	});
	
	$(".appGroupManager").click(function(){
		goToAppGroup();
	});
	
	$(".groupPolicyManager").click(function(){
		goToGroupPolicy();
	});
	
	$(".blacklistManager").click(function(){
		goToBlacklist();
	});
	
	$(".generalManager").click(function(){
		goToGeneral();
	});
	
	$(".relatedPhoneManager").click(function(){
		goToRelatedPhone();
	});
	
	$(".intervalManager").click(function(){
		goToInterval();
	});
}

function changeNavigationStyle(){
	$(".groupPolicyManager").removeClass("lowlight");
	$(".groupPolicyManager").addClass("highlight");
	$(".groupPolicyManager").addClass("lowlight");
	$(".groupPolicyManager").addClass("lowlight");
}

function getColumnIndexByName($grid, columnName) {
	var colModel = $grid.jqGrid("getGridParam", "colModel"), cmLength = colModel.length, i;
	for (i = 0; i < cmLength; i += 1) {
		if (colModel[i].name === columnName) {
			return i;
		}
	}
	return -1;
}

function moveTableColumn(rows, iCol, className) {
	var rowsCount = rows.length, iRow, row, $row;
	for (iRow = 0; iRow < rowsCount; iRow += 1) {
		row = rows[iRow];
		$row = $(row);
		if (!className || $row.hasClass(className)) {
			$row.append(row.cells[iCol]);
		}
	}
}

function cleanSelectedGroup(){
	var appGroupList = $("input:checkbox[name='appgroupcheckbox']:checked");
	for(var i=0;i<appGroupList.length;i++){
		var appGroupId = appGroupList[i].id;
		$("#" + appGroupId).prop("checked",false); 
	}
}

function disableAllButtons(){
	$("#addAppsBtn").attr("disabled", true);
	$("#removeAppsBtn").attr("disabled", true);
}
