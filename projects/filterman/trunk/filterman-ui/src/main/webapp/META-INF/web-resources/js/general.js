var currentRequest;
var changedField;
var changedTable;
var currentAppId;
var currentAppName;
var originalAccount;
var originalPhone;
var originalDevice;
var canChange = true;

$(function(){
	$(".generalManager").addClass("highlight");
	initBindTable();
	bindEvents();
});

function bindEvents(){
	$(".action .submit").click(function(){
		saveChange();
	});
	
	$(".action .modify").click(function(){
		$(".dialog").hide();
	});
}

function initBindTable(){
	$("#bindTable").jqGrid({ 
	    url:'allRequests', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["id", "影响策略", "日期", "订单ID", "应用ID", "应用名称", "账号", "设备号", "手机号", "通过", "阻止原因"], 
	       colModel:[ 
	           {name:'id',index:'id', hidden:true},
	           {name:'enabledPolicy',index:'enabledPolicy', hidden:true},
	           {name:'date',index:'date', formatter: formatDate, width:100, align:"left", sortable: false}, 
	           {name:'orderId',index:'orderId', width:100, align:"left", sortable: false},
	           {name:'appId',index:'appId', hidden:true},
	           {name:'appName',index:'appName', width:100, align:"left", sortable: false},
	           {name:'account',index:'account', width:100, align:"left", sortable: false},
	           {name:'device',index:'device', width:100, align:"left", sortable: false},
	           {name:'phone',index:'phone', width:100, align:"left", sortable: false}, 
	           {name:'isAccess',index:'isAccess', formatter: formatAccessCheckBox, width:60, align:"left", sortable: false}, 
	           {name:'blockReason',index:'blockReason', width:180, align:"left", sortable: false}
	       ], 
	       rowNum:12,
	       rowList:[10,20,30],
	       pager: '#bindPager', 
	       autowidth: true,
	       height: 500,
	       pginput: true,
	       recordpos: "left",
	    height:280, 
	    viewrecords: true, 
        sortable: false,
	    altRows:true,
	    jsonReader :
	    {
		    root: "rows",
		    page: "page",
		    total: "total",
		    records: "records",
		    repeatitems: false
	    },
	    altclass:"jqgridBackground",
	    scrollOffset: 18,
	    onSelectRow: function(rowid, status){
	    	currentRequest = rowid;
    	}
	 }); 
	
	$("#bindTable").closest(".ui-jqgrid-bdiv").height(380);
}

function formatDate(cellvalue, options, rowObject){
	var newTime = new Date(cellvalue).format("yyyy-MM-dd hh:mm:ss");
	return newTime;
}

function formatAccessCheckBox(cellvalue, options, rowObject){
	var isChecked = cellvalue ? "checked='checked'" : '';
	return "<input type='checkbox' name='accesscb' " + isChecked + " disabled='disabled'/>";
}

function searchRequest(){
	var orderIdOrPhone = $("#orderIdAndPhone").val();
	
	if(orderIdOrPhone == "" || orderIdOrPhone == "undefined"){
		alert("请输入订单ID或者电话号码");
		return;
	}
	if(orderIdOrPhone.match(regEx)){
		alert("输入的订单ID或者电话号码不合法，请重新输入");
		return;
	}
	
	$("#bindTable").jqGrid('setGridParam',{ 
        url:"allRequests", 
        datatype: "json",
        mtype: "POST",
        postData: {"orderIdOrPhone":orderIdOrPhone},
        page:1 
    }).trigger("reloadGrid");
}

function resetSearch(){
	$("#orderIdAndPhone").val("");
	$("#bindTable").jqGrid('setGridParam',{ 
        url:"allRequests", 
        datatype: "json",
        mtype: "POST",
        postData: {"orderIdOrPhone":"-1"},
        page:1 
    }).trigger("reloadGrid");
}

function openBindChangeDialog(){
	if(currentRequest == undefined){
		alert("请选择request");
		return;
	}
	
	var height = $(".dialog").innerHeight();
    $(".dialog").css({marginTop:-height/2});
    $(".dialog").show();
    
    $(".dialog .close").click(function(){
        $(".dialog").hide();
    })
    $(".dialog .action a").click(function(){
        $(".dialog").hide();
    })
    
    initDialogContent();
}

function initDialogContent(){
	canChange = true;
	
	enableAllField();
	
	var request = $("#bindTable").getRowData(currentRequest);
	
	var tableAndField = getChangeTableAndChangeField();
	
	changedTable = tableAndField;
	
	currentAppId = request.appId;
	currentAppName = request.appName;
	
	$("#appNameField").text(request.appName);
	
	fillContent(request);
	
	if(changedField.indexOf("account") != -1){
		$("#deviceField").prop('disabled', true);
		$("#phoneField").prop('disabled', true);
	}else if(changedField.indexOf("device") != -1){
		$("#accountField").prop('disabled', true);
		$("#phoneField").prop('disabled', true);
	}else if(changedField.indexOf("phone") != -1){
		$("#accountField").prop('disabled', true);
		$("#deviceField").prop('disabled', true);
	}else{
		disableAllField();
		canChange = false;
	}
}

function enableAllField(){
	$("#accountField").prop('disabled', false);
	$("#deviceField").prop('disabled', false);
	$("#phoneField").prop('disabled', false);
}

function disableAllField(){
	$("#accountField").prop('disabled', true);
	$("#deviceField").prop('disabled', true);
	$("#phoneField").prop('disabled', true);
}

function fillContent(request){
	$.ajax({
		url: "getDetailByKey",
		type: 'POST',
		dataType: 'json',
		data: {"account": request.account, "device": request.device, "phone": request.phone, 
		   "appId": request.appId, "appName": request.appName, "changedField": changedField, "changedTable": changedTable},
		success: function(da){
			if(da){
				$("#accountField").val(da.account);
				$("#deviceField").val(da.device);
				$("#phoneField").val(da.phone);
				originalAccount = da.account;
				originalDevice = da.device;
				originalPhone = da.phone;
			}
		},
		error: function(){
			$(".dialog").hide();
            //alert("获取数据失败");
        }
	});
}

function saveChange(){
	
	if(changedField == "account"){
		if($("#accountField").val() == originalAccount){
			$(".dialog").hide();
			//alert("不能修改");
			return;
		}
	}
	
	if(changedField == "device"){
		if($("#deviceField").val() == originalDevice){
			$(".dialog").hide();
			//alert("不能修改");
			return;
		}
	}
	
	if(changedField == "phone"){
		if($("#phoneField").val() == originalPhone){
			$(".dialog").hide();
			//alert("不能修改");
			return;
		}
	}
	
	if(!canChange || changedField == undefined){
		$(".dialog").hide();
		//alert("不能修改");
		return;
	}
	
	var account = $("#accountField").val();
	var device = $("#deviceField").val();
	var phone= $("#phoneField").val();
	$.ajax({
		url: "fieldsUpdate",
		type: 'POST',
		dataType: 'json',
		data: {"account": account, "device": device, "phone": phone, 
		    "appId": currentAppId, "appName":currentAppName, "changedField": changedField, "changedTable": changedTable},
		success: function(da){
			if(da){
				$(".dialog").hide();
				alert("更新成功");
			}
		},
		error: function(){
			$(".dialog").hide();
            //alert("更新失败");
        }
	});
}

function getChangeTableAndChangeField(){
	var tableAndField;
	var request = $("#bindTable").getRowData(currentRequest);
	var blockReason = request.blockReason;
	var tableName;
	var changedTable;
	if(blockReason.indexOf("2-2-2") != -1){
		tableName = "222";
	}else if(blockReason.indexOf("1-1-1") != -1){
		tableName = "111";
	}else{
		$(".dialog").hide();
		//alert("不能修改");
		return;
	}
	
	if(blockReason.indexOf("on account") != -1){
		changedTable = "account";
	}else if(blockReason.indexOf("on device") != -1){
		changedTable = "device";
	}else if(blockReason.indexOf("on phone") != -1){
		changedTable = "phone";
	}else{
		$(".dialog").hide();
		//alert("不能修改");
		return;
	}
	
	if(blockReason.indexOf("The account") != -1){
		changedField = "account";
	}else if(blockReason.indexOf("The device") != -1){
		changedField = "device";
	}else if(blockReason.indexOf("The phone") != -1){
		changedField = "phone";
	}else{
		$(".dialog").hide();
		//alert("不能修改");
		return;
	}
	
	tableAndField = changedTable + tableName;
	return tableAndField;
}

