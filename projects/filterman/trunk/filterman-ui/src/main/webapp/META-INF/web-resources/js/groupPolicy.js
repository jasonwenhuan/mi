var currentSelAppGroup;
var currentSelAppGroupName;
var currentSelAppGroupLevel = "";
var currentSelAppGroupType;
var currentSelPolicy;
var currentSelPolicyGroupId;
var currentSelAllPolicy;
var currentGroupLevel = "";
var currentPolicy = [];

$(function(){
	$(".groupPolicyManager").addClass("highlight");
	$(".groupPolicyColumn2").hide();
	
	initGroupTable();
	initCurrentPolicyTable();
	initAllPolicyTable();
	disableAddRemoveButton();
});

function initGroupTable(){
	$("#groupTable").jqGrid({ 
	    url:'groups', 
	    datatype: "json", 
	    mtype: 'GET',
	       colNames:["应用组ID", "应用组名", "创建日期", "创建用户", "类型"], 
	       colModel:[ 
	           {name:'groupId',index:'groupId', hidden:true},
	           {name:'groupName',index:'groupName', width:150, align:"left", sortable: false}, 
	           {name:'createDate',index:'createDate', hidden: true}, 
	           {name:'createUser',index:'createUser', hidden: true},
	           {name:'type',index:'type', hidden: true}
	       ], 
	       rowNum:-1, 
	       pager: '#groupPager', 
	       autowidth: false,
	       pginput: false,
	       recordpos: "left",
	       pgbuttons: false,
	    height:280, 
	    viewrecords: true, 
        sortable: false,
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    onCellSelect: function(rowid,iCol,cellcontent,e){
	    	var rowObj = $(this).getRowData(rowid);
	    	currentSelAppGroup = rowObj.groupId;
	    	
	    	currentSelAppGroupName = rowObj.groupName;
	    	currentSelAppGroupType = rowObj.type;
	    	
	    	getCurrentPolicy(rowid);
	    	
	    	disableAddRemoveButton();
	    	
	    	//reloadAllPolicyTable();
	    	$(".groupPolicyColumn5").show();
	    	$(".groupPolicyColumn4").show();
	    }
	 }); 
}

function initCurrentPolicyTable(){
	$("#currentPolicyTable").jqGrid({ 
	    url:'currentPolicy', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["组策略ID", "应用组ID", "应用组名", "策略ID", "当前策略", "等级", "更新日期", "更新用户", "组类型"], 
	       colModel:[ 
               {name:'groupPolicyKey',index:'groupPolicyKey', hidden:true},	
	           {name:'groupId',index:'groupId', hidden:true},
	           {name:'groupName',index:'groupName', hidden:true}, 
	           {name:'policyId',index:'policyId', formatter: fillCurPolicyArray, hidden:true},
	           {name:'policyName',index:'policyName'},
	           {name:'level',index:'level', hidden:true},
	           {name:'updateDate',index:'updateDate', hidden: true}, 
	           {name:'updateUser',index:'updateUser', hidden: true},
	           {name:'groupType',index:'groupType', hidden: true}
	       ], 
	       rowNum:-1, 
	       pager: '#currentPolicyPager', 
	       autowidth: true,
	       pginput: false,
	       recordpos: "left",
	       pgbuttons: false,
	    height:280, 
	    viewrecords: true, 
        sortable: false,
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    onCellSelect: function(rowid,iCol,cellcontent,e){
	    	currentSelPolicy = $(this).getRowData(rowid).policyId;
	    	currentSelPolicyGroupId = $(this).getRowData(rowid).groupPolicyKey;
	    	$("#addPolicy").attr("disabled", true);
	    	$("#removePolicy").attr("disabled", false);
	    }
	 }); 
}

function refreshCurrentPolicyArray(){
	currentPolicy = [];
	currentPolicy.length = 0;
}

function fillCurPolicyArray(cellvalue, options, rowObject){
	currentPolicy.push(cellvalue);
	return cellvalue;
}

function getCurrentPolicy(rowid){
	var rowObj = $("#groupTable").getRowData(rowid);
	var groupId = rowObj.groupId;
	var groupName = rowObj.groupName;
	var groupType = rowObj.type;
    
	refreshCurrentPolicyArray();
	
	//0:平台组 1：应用组 2：渠道组
    if(groupType != 0){
    	$(".groupPolicyColumn2").hide();
    }else{
    	$(".groupPolicyColumn2").show();
    }
	
	$.ajax({
		url: "currentPolicy",
		type: 'POST',
		dataType: 'json',
		data: {"groupId" : groupId},
		success: function(da){
			if(da && da.length > 0){
				currentGroupLevel = da[0].level;
				currentSelAppGroupLevel = da[0].level;
				if(currentGroupLevel == "0"){
					$("input[id='noLevelRa']").prop("checked",true);
				}else if(currentGroupLevel == "2"){
					$("input[id='lowLevelRa']").prop("checked",true);
				}else if(currentGroupLevel == "4"){
					$("input[id='middleLevelRa']").prop("checked",true);
				}else if(currentGroupLevel == "8"){
					$("input[id='highLevelRa']").prop("checked",true);
				}
			}else{
				$("input[id='noLevelRa']").prop("checked",true);
				if(groupType == 0){
					$(".groupPolicyColumn5").hide();
					$(".groupPolicyColumn4").hide();
				}
			}
			
			if(groupType != 0){
				currentSelAppGroupLevel = "";
			}
			
			$("#currentPolicyTable").jqGrid('setGridParam',{ 
			    url:"currentPolicy", 
			    datatype: "json",
			    mtype: "POST",
			    postData: {"groupId" : groupId, "groupLevel": currentSelAppGroupLevel},
			    page:1 
			}).trigger("reloadGrid");
			
			$("#allPolicyTable").jqGrid('setGridParam',{ 
			    url:"allPolicy", 
			    datatype: "json",
			    mtype: "POST",
			    page:1 
			}).trigger("reloadGrid");
		}
	});
	
}

function initAllPolicyTable(){
	$("#allPolicyTable").jqGrid({ 
	    url:'allPolicy', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["组策略ID", "策略ID", "所有策略", "更新日期", "更新用户", "状态"], 
	       colModel:[ 
	           {name:'groupPolicyKey',index:'groupPolicyKey', hidden:true},
	           {name:'policyId',index:'policyId', hidden:true},
	           {name:'policyName',index:'policyName', sortable: false},
	           {name:'updateDate',index:'updateDate', hidden: true}, 
	           {name:'updateUser',index:'updateUser', hidden: true},
	           {name:'status',index:'status', hidden: true}
	       ], 
	       rowNum:-1, 
	       pager: '#allPolicyPager', 
	       autowidth: true,
	       pginput: false,
	       recordpos: "left",
	       pgbuttons: false,
	    height:280, 
	    viewrecords: true, 
        sortable: false,
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    onCellSelect: function(rowid,iCol,cellcontent,e){
	    	currentSelAllPolicy = $(this).getRowData(rowid).policyId;
	    	$("#removePolicy").attr("disabled", true);
	    	$("#addPolicy").attr("disabled", false);
	    }
	 }); 
}

function disableAddRemoveButton(){
	if(currentSelPolicy == undefined){
		$("#addPolicy").attr("disabled", true);
	}
	if(currentSelAllPolicy == undefined){
		$("#removePolicy").attr("disabled", true);
	}
}

function removePolicyFromGroup(){
	$.ajax({
		url: "removePolicyFromGroup",
		type: 'POST',
		dataType: 'json',
		data: {"groupPolicyId":currentSelPolicyGroupId, "groupId":currentSelAppGroup , "policyId": currentSelPolicy, "policyLevel": currentSelAppGroupLevel},
		success: function(){
			reloadCurrentPolicyTable();
			reloadAllPolicyTable();
		},
		error: function(){
            alert("删除策略失败");
        }
	});
}

function addPolicyFromPool(){
	if(!checkPolicyConflict()){
		alert("策略冲突，不能添加，请重新选择策略。");
		return;
	}
	if(currentSelAppGroup == undefined){
		alert("请选择应用组.");
		return;
	}
	var validateSelAllPolicy = checkCurSelAllPolicy();
	if(!validateSelAllPolicy){
		alert("该策略已存在,请重新选择");
		return;
	}
	$.ajax({
		url: "addPolicyToGroup",
		type: 'POST',
		dataType: 'json',
		data: {"groupId":currentSelAppGroup, "groupName":currentSelAppGroupName,
		    "groupLevel":currentSelAppGroupLevel,
		    "groupType":currentSelAppGroupType, "policyId": currentSelAllPolicy},
		success: function(){
			reloadCurrentPolicyTable();
			reloadAllPolicyTable();
		},
		error: function(){
            alert("添加策略失败");
        }
	});
}

function reloadCurrentPolicyTable(){
	refreshCurrentPolicyArray();
	$("#currentPolicyTable").jqGrid('setGridParam',{ 
	    url:"currentPolicy", 
	    datatype: "json",
	    mtype: "POST",
	    postData: {"groupId" : currentSelAppGroup, "groupLevel":currentSelAppGroupLevel},
	    page:1 
	}).trigger("reloadGrid");
}

function reloadAllPolicyTable(){
	$("#allPolicyTable").jqGrid('setGridParam',{ 
	    url:"allPolicy", 
	    datatype: "json",
	    mtype: "POST",
	    page:1 
	}).trigger("reloadGrid");
}

function checkCurSelAllPolicy(){
	for(var i=0;i<currentPolicy.length;i++){
		if(currentPolicy[i] == currentSelAllPolicy){
			return false;
			break;
		}
	}
	return true;
}

function updateGroupLevel(level){
	currentSelAppGroupLevel = level;
	if(level == "0"){
		$(".groupPolicyColumn5").hide();
		$(".groupPolicyColumn4").hide();
	}else{
		$(".groupPolicyColumn5").show();
		$(".groupPolicyColumn4").show();
	}
	
	$.ajax({
		url: "updateGroupLevel",
		type: 'POST',
		dataType: 'json',
		data: {"groupId":currentSelAppGroup, "groupLevel":level},
		success: function(){
			reloadCurrentPolicyTable();
			reloadAllPolicyTable();
		},
		error: function(){
            alert("更新策略等级失败");
        }
	});
}

function checkPolicyConflict(){
	// avoid 111,222 policy conflict 
	var canAccess = true;
	$.each(currentPolicy, function(key, value){
		if(value == "10"){
			if(currentSelAllPolicy == "20"){
				canAccess = false;
			}
		}
		if(value == "20"){
			if(currentSelAllPolicy == "10"){
				canAccess = false;
			}
		}
	});
	return canAccess;
}
