var currentRule;
var originalTimeInterval;
var originalLimitFrequent;
var isAddInterval = false;

$(function(){
	$(".relatedPhoneManager").addClass("highlight");
	initRelatedPhonePolicyTable();
	bindRelatedPhoneEvents();
});

function bindRelatedPhoneEvents(){
	$(".action .submit").click(function(){
		saveChange();
	});
	
	$(".action .modify").click(function(){
		$(".dialog").hide();
	});
}

function initRelatedPhonePolicyTable(){
	$("#relatedRuleTable").jqGrid({ 
	    url:'getAllRelatedPhonePolicy', 
	    datatype: "json", 
	    mtype: 'GET',
	       colNames:["key","请选择", "间隔时间（小时）", "支付次数（次）"], 
	       colModel:[ 
	           {name:'key',index:'key', hidden:true },
	           {name:'',index:'', width:100, formatter: formatRPCheckBox, align:"left", sortable: false},
	           {name:'timeInterval',index:'timeInterval', width:100, align:"left", sortable: false},
	           {name:'totalNumber',index:'totalNumber', width:100, align:"left", sortable: false}
	       ], 
	       pager: '#relatedRulePager', 
	       rowNum: -1,
	       autowidth: true,
	       pginput: false,
	    viewrecords: true, 
	    recordpos: "left",
	    pagerpos: "right",
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    height: 380,
	    onSelectRow: function(rowid, status){
	    	currentRule = rowid;
    	}
	 }); 
}

function searchTable(){
	var interval = $(".fmsearchInput").val();
	
	if(interval == "" || interval == "undefined"){
		alert("请输入时间间隔");
		return;
	}
	
	if(interval.match(regEx)){
		alert("输入的时间间隔不合法，请重新输入");
		return;
	}
	
	$("#relatedRuleTable").jqGrid('setGridParam',{ 
	    url:'getRulesByInterval', 
	    datatype: "json", 
	    mtype: "POST",
	    postData: {"interval": interval},
	    page:1 
	 }).trigger("reloadGrid"); 
}

function resetTable(){
	$(".fmsearchInput").val("");
	$("#relatedRuleTable").jqGrid('setGridParam',{ 
	    url:'getAllRelatedPhonePolicy', 
	    datatype: "json", 
	    mtype: "GET",
	    page:1 
	 }).trigger("reloadGrid"); 
}

function crudChange(){
	isAddInterval = false;
	openRelatedPhoneRuleChangeDialog();
}

function saveChange(){
	if(isAddInterval){
		var currentIntervalTime = $("#intervalTime").val();
		var currentLimitFrequent =  $("#frequentLimit").val();
		if(!checkField(currentIntervalTime, currentLimitFrequent)){
			return;
		}else{
			$.ajax({
				url: "getRulesByInterval",
				type: 'POST',
				dataType: 'json',
				data: {"interval": currentIntervalTime},
				success: function(da){
					if(da.length > 0){
						alert("输入的时间间隔已存在，请重新输入！");
					}else{
						$.ajax({
							url: "createPhoneRule",
							type: 'POST',
							dataType: 'json',
							data: {"interval": currentIntervalTime, "limit": currentLimitFrequent},
							success: function(da){
								if(da){
									resetTable();
									$(".dialog").hide();
								}
							}
						});
					}
				},
				error: function(){
					alert("更新失败");
					$(".dialog").hide();
		        }
			});
		}
	}else{
		var record = $("#relatedRuleTable").getRowData(currentRule);
		var currentIntervalTime = $("#intervalTime").val();
		var currentLimitFrequent =  $("#frequentLimit").val();
		if(!checkField(currentIntervalTime, currentLimitFrequent)){
			return;
		}
		if(originalTimeInterval != currentIntervalTime ||
				originalLimitFrequent != currentLimitFrequent){
			if(confirm("确定修改限制条件吗？")){
				$.ajax({
					url: "getRulesByInterval",
					type: 'POST',
					dataType: 'json',
					data: {"interval": currentIntervalTime},
					success: function(da){
						if(da){
							$.ajax({
								url: "updateRelatedPhoneRule",
								type: 'POST',
								dataType: 'json',
								data: {"id": record.key, "interval": currentIntervalTime, 
									"limit" : currentLimitFrequent},
								success: function(da){
									if(da){
										resetTable();
										$(".dialog").hide();
									}
								},
								error: function(){
									alert("更新失败");
									$(".dialog").hide();
						        }
							});
						}
					}
				});
			}
		}else{
			$(".dialog").hide();
		}
	}
}

function openRelatedPhoneRuleChangeDialog(){
	if(currentRule == undefined){
		alert("请选择规则");
		return;
	}
	
	var height = $(".dialog").innerHeight();
    $(".dialog").css({marginTop:-height/2});
    $(".dialog").show();
    
    $(".dialog .close").click(function(){
        $(".dialog").hide();
    });
    $(".dialog .action a").click(function(){
        $(".dialog").hide();
    });
    
    fillContent();
}

function fillContent(){
	var record = $("#relatedRuleTable").getRowData(currentRule);
	originalTimeInterval = record.timeInterval;
	originalLimitFrequent = record.totalNumber;
	$("#intervalTime").val(originalTimeInterval);
	$("#frequentLimit").val(originalLimitFrequent);
}

function add(){
	isAddInterval = true;
	
	var height = $(".dialog").innerHeight();
    $(".dialog").css({marginTop:-height/2});
    $(".dialog").show();
    
    $(".dialog .close").click(function(){
        $(".dialog").hide();
    });
    $(".dialog .action a").click(function(){
        $(".dialog").hide();
    });
    
    clearContent();
}

function clearContent(){
	$("#intervalTime").val("");
	$("#frequentLimit").val("");
}

function checkField(currentIntervalTime, currentLimitFrequent){
	if(currentIntervalTime == "" || currentIntervalTime == "undefined" 
		||currentIntervalTime.match(regEx)){
		alert("非法的时间间隔，请重新输入！");
		return false;
	}
	
	if(currentLimitFrequent == "" || currentLimitFrequent == "undefined" 
		||currentLimitFrequent.match(regEx)){
		alert("非法的次数限制，请重新输入！");
		return false;
	}
	
	return true;
}

function formatRPCheckBox(cellvalue, options, rowObject){
	return "<input type='checkbox' name='relatedphonecheckbox' id='" + rowObject.key + "'></input>";
}

function remove(){
	var selectedObjs = $("input:checkbox[name='relatedphonecheckbox']:checked");
	if(selectedObjs.length == 0){
		alert("请选择");
		return;
	}
	if(confirm("确定删除这" + selectedObjs.length + "项吗？")){
		var rpIds = [];
		for(var i = 0; i < selectedObjs.length; i++){
			rpIds.push(selectedObjs[i].id);
		}
		$.ajax({
			url: "deleteRelatedPhoneRules",
			type: 'POST',
			dataType: 'json',
			data: {"ids":rpIds},
			traditional: true,
			success: function(da){
				if(da){
					alert("删除成功");
					resetTable();
				}
			},
			 error: function(){
	            alert("删除失败");
	        }
		});
	}else{
		return;
	}
}