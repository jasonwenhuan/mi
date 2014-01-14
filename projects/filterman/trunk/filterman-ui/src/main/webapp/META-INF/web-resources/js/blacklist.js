$(function(){
	$(".blacklistManager").addClass("highlight");
	$(".blackListColumn4").hide();
	$(".blackListColumn5").hide();
	$("input[id='blacklistRad']").prop("checked",true);
	initBlacklistTable();
	initWhitelistTable();
});

function initBlacklistTable(){
	$("#blacklistTable").jqGrid({ 
	    url:'getBlacklist', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["key", "黑名单号", "加黑理由"], 
	       colModel:[ 
	           {name:'key',index:'key', hidden:true },
	           {name:'keyId',index:'keyId', width:100, align:"left", sortable: false},
	           {name:'comment',index:'comment', width:100, align:"left", sortable: false}
	       ], 
	       pager: '#blacklistPager', 
	       rowNum:10,
	       rowList:[10,20,30],
	       pginput: true,
	       jsonReader :
		    {
			    root: "rows",
			    page: "page",
			    total: "total",
			    records: "records",
			    repeatitems: false
		    }, 
	       autowidth: true,
	       pginput: true,
	    height:320, 
	    viewrecords: true, 
	    recordpos: "left",
	    pagerpos: "right",
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    multiselect: true
	 }); 
}

function initWhitelistTable(){
	$("#whitelistTable").jqGrid({ 
	    url:'getWhitelist', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["key", "白名单号", "漂白理由"], 
	       colModel:[ 
	           {name:'key',index:'key', hidden:true },
	           {name:'keyId',index:'keyId', width:210, align:"left", sortable: false},
	           {name:'comment',index:'comment', width:200, align:"left", sortable: false}
	       ], 
	       pager: '#whitelistPager', 
	       rowNum:10,
	       rowList:[10,20,30],
	       pginput: true,
	       jsonReader :
		    {
			    root: "rows",
			    page: "page",
			    total: "total",
			    records: "records",
			    repeatitems: false
		    }, 
	       autowidth: true,
	       pginput: true,
	    height:320, 
	    viewrecords: true, 
	    recordpos: "left",
	    pagerpos: "right",
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    multiselect: true,
	    loadComplete: function () {
	    	   
	    },
	    onSelectRow: function(rowid, status){
	    	
    	},
    	onSelectAll: function(rowid, status){
    		
    	}
	 }); 
}

function showBlacklist(){
	$(".blackListColumn4").hide();
	$(".blackListColumn5").hide();
	$(".blackListColumn2").show();
	$(".blackListColumn3").show();
}

function showWhitelist(){
	$(".blackListColumn2").hide();
	$(".blackListColumn3").hide();
	$(".blackListColumn4").show();
	$(".blackListColumn5").show();
}

function reloadBlacklistTable(blacklistId){
	var id = "-1";
	if(blacklistId != undefined){
		id = blacklistId;
	}
	$("#blacklistTable").jqGrid('setGridParam',{ 
        url:"getBlacklist", 
        datatype: "json",
        mtype: "POST",
        postData: {"blacklistId" : id},
        page:1 
    }).trigger("reloadGrid");
}

function reloadWhitelistTable(whitelistId){
	var id = "-1";
	if(whitelistId != undefined){
		id = whitelistId;
	}
	$("#whitelistTable").jqGrid('setGridParam',{ 
        url:"getWhitelist", 
        datatype: "json",
        mtype: "POST",
        postData: {"whitelistId" : id},
        page:1 
    }).trigger("reloadGrid");
}

function addBlacklist(){
	var keyId = $("#blackListKeyId").val().trim();
	var comment = $("#blackListComment").val().trim();
	
	if(keyId == "" || keyId == "undefined"){
		alert("请输入黑名单关键字");
		return;
	}
	
	if(keyId.match(regEx)){
		alert("输入的关键字不合法，请重新输入");
		return;
	}
	
	if(comment == "" || comment == "undefined"){
		alert("请输入加黑理由");
		return;
	}
	
	if(comment.match(regEx)){
		alert("输入的加黑理由不合法，请重新输入");
		return;
	}
	
	$.ajax({
		url: "createBlacklist",
		type: 'POST',
		dataType: 'json',
		data: {"keyId":keyId, "comment":comment,},
		success: function(da){
			if(da){
				reloadBlacklistTable();
			}else{
				alert("输入的黑名单已存在");
			}
		}
	});
}

function addWhitelist(){
	var keyId = $("#whiteListKeyId").val().trim();
	var comment = $("#whiteListComment").val().trim();
	
	if(keyId == "" || keyId == "undefined"){
		alert("请输入白名单关键字");
		return;
	}
	
	if(keyId.match(regEx)){
		alert("输入的关键字不合法，请重新输入");
		return;
	}
	
	if(comment == "" || comment == "undefined"){
		alert("请输入漂白理由");
		return;
	}
	
	if(comment.match(regEx)){
		alert("输入的漂白理由不合法，请重新输入");
		return;
	}
	
	$.ajax({
		url: "isInBlacklist",
		type: 'POST',
		dataType: 'json',
		data: {"whitelistKeyId":keyId},
		success: function(da){
			if(da){
				alert("添加的白名单存在于黑名单中，请先删除对应的黑名单信息！");
			}else{
				$.ajax({
					url: "createWhitelist",
					type: 'POST',
					dataType: 'json',
					data: {"keyId":keyId, "comment":comment,},
					success: function(da){
						if(da){
							reloadWhitelistTable();
						}else{
							alert("输入的白名单已存在");
						}
					}
				});
			}
		}
	});
}

function deleteBlacklist(){
	var grid = $("#blacklistTable");
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	var selIds = [];
	for(var i=0;i<selRowIds.length;i++){
		var rowObj = grid.getRowData(selRowIds[i]);
		selIds.push(rowObj.key);
	}
	
	$.ajax({
		url: "removeBlacklist",
		type: 'POST',
		dataType: 'json',
		traditional: true,
		data: {"blacklistIds":selIds},
		success: function(){
			reloadBlacklistTable();
		}
	});
}

function deleteWhitelist(){
	var grid = $("#whitelistTable");
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	var selIds = [];
	for(var i=0;i<selRowIds.length;i++){
		var rowObj = grid.getRowData(selRowIds[i]);
		selIds.push(rowObj.key);
	}
	
	$.ajax({
		url: "removeWhitelist",
		type: 'POST',
		dataType: 'json',
		traditional: true,
		data: {"whitelistIds":selIds},
		success: function(){
			reloadWhitelistTable();
		}
	});
}

function searchTable(){
	var isBlacklist = $("input[id='blacklistRad']").prop("checked");
	var id = $(".fmsearchInput").val();
	
	if(id == "" || id == "undefined"){
		alert("请输入搜索条件");
		return;
	}
	
	if(id.match(regEx)){
		alert("输入的搜索条件不合法，请重新输入");
		return;
	}
	
	if(isBlacklist){
		reloadBlacklistTable(id);
	}else{
		reloadWhitelistTable(id);
	}
}

function resetTable(){
	$(".fmsearchInput").val("");
	var isBlacklist = $("input[id='blacklistRad']").prop("checked");
	if(isBlacklist){
		reloadBlacklistTable();
	}else{
		reloadWhitelistTable();
	}
}
