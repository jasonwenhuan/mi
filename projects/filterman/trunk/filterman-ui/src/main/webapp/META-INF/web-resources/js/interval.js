var currentRule;
var isAddInterval = false;
var originalTimeInterval;
var originalLimitAccount;
$(function(){
	$(".intervalManager").addClass("highlight");
	initIntervalPolicyTable();
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

function initIntervalPolicyTable(){
	$("#intervalTable").jqGrid({ 
	    url:'getAllIntervalPolicy', 
	    datatype: "json", 
	    mtype: 'GET',
	       colNames:["key","请选择", "间隔时间（分钟）", "支付金额（RMB元）"], 
	       colModel:[ 
	           {name:'key',index:'key', hidden:true },
	           {name:'',index:'', width:100, formatter: formatRPCheckBox, align:"left", sortable: false},
	           {name:'timeInterval',index:'timeInterval', width:100, align:"left", sortable: false},
	           {name:'totalAccount',index:'totalAccount', width:100, align:"left", sortable: false}
	       ], 
	       pager: '#intervalPager', 
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

function formatRPCheckBox(cellvalue, options, rowObject){
	return "<input type='checkbox' name='intervalcheckbox' id='" + rowObject.key + "'></input>";
}

function remove(){
	var selectedObjs = $("input:checkbox[name='intervalcheckbox']:checked");
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
			url: "deleteIntervalRules",
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

function crudChange(){
	isAddInterval = false;
	openRelatedPhoneRuleChangeDialog();
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
	var record = $("#intervalTable").getRowData(currentRule);
	originalTimeInterval = record.timeInterval;
	originalLimitAccount = record.totalAccount;
	$("#intervalTime").val(originalTimeInterval);
	$("#accountLimit").val(originalLimitAccount);
}

function resetTable(){
	$(".fmsearchInput").val("");
	$("#intervalTable").jqGrid('setGridParam',{ 
	    url:'getAllIntervalPolicy', 
	    datatype: "json", 
	    mtype: "GET",
	    page:1 
	 }).trigger("reloadGrid"); 
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
	
	$("#intervalTable").jqGrid('setGridParam',{ 
	    url:'getIntervalPoliciesByInterval', 
	    datatype: "json", 
	    mtype: "POST",
	    postData: {"interval": interval},
	    page:1 
	 }).trigger("reloadGrid"); 
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
	$("#accountLimit").val("");
}

function saveChange(){
	if(isAddInterval){
		var currentIntervalTime = $("#intervalTime").val();
		var currentLimitAccount =  $("#accountLimit").val();
		if(!checkField(currentIntervalTime, currentLimitAccount)){
			return;
		}else{
			$.ajax({
				url: "getIntervalPoliciesByInterval",
				type: 'POST',
				dataType: 'json',
				data: {"interval": currentIntervalTime},
				success: function(da){
					if(da.length > 0){
						alert("输入的时间间隔已存在，请重新输入！");
					}else{
						$.ajax({
							url: "createIntervalRule",
							type: 'POST',
							dataType: 'json',
							data: {"interval": currentIntervalTime, "limit": currentLimitAccount},
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
		var record = $("#intervalTable").getRowData(currentRule);
		var currentIntervalTime = $("#intervalTime").val();
		var currentLimitAccount =  $("#accountLimit").val();
		if(!checkField(currentIntervalTime, currentLimitAccount)){
			return;
		}
		if(originalTimeInterval != currentIntervalTime ||
				originalLimitAccount != currentLimitAccount){
			if(confirm("确定修改限制条件吗？")){
				$.ajax({
					url: "getIntervalPoliciesByInterval",
					type: 'POST',
					dataType: 'json',
					data: {"interval": currentIntervalTime},
					success: function(da){
						if(da){
							$.ajax({
								url: "updateIntervalRule",
								type: 'POST',
								dataType: 'json',
								data: {"id": record.key, "interval": currentIntervalTime, 
									"limit" : currentLimitAccount},
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

function checkField(currentIntervalTime, currentLimitAccount){
	if(currentIntervalTime == "" || currentIntervalTime == "undefined" 
		||currentIntervalTime.match(regEx)){
		alert("非法的时间间隔，请重新输入！");
		return false;
	}
	
	if(currentLimitAccount == "" || currentLimitAccount == "undefined" 
		||currentLimitAccount.match(regEx)){
		alert("非法的支付金额，请重新输入！");
		return false;
	}
	
	return true;
}