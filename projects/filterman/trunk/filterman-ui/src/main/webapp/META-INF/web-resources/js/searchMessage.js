$(function(){
	$(".payBindManager").addClass("highlight");
});

var originalRecord = new Array();
var changedRecord = new Array();
var hasChanged = false;

$().ready(function() {
});

function searchMessage(){
	if(!check()){
		return;
	}
	var appID = $("#addIdSelect").val();
	$("#selectedAppID").attr("value", appID);
	$("#searchResultForm").submit();
}

function modify(key){
	clearDialogElements();
	
	var height = $(".dialog").innerHeight();
    $(".dialog").css({marginTop:-height/2});
    $(".dialog").show();
    
    $(".dialog .close").click(function(){
        $(".dialog").hide();
    })
    $(".dialog .action a").click(function(){
        $(".dialog").hide();
    })
    
    
    
	//saveOriginalRecord(key);
    
    //enable all fields
    $("#modAccounts").attr("disabled", false);
    $("#modPhones").attr("disabled", false);
    $("#modDevices").attr("disabled", false);
    
    $("#modAppId").text($("#recordAppId" + key).text());
    $("#modKey").val($("#recordKey" + key).text());
    $("#modAccounts").val($("#recordAccounts" + key).text());
    $("#modPhones").val($("#recordPhones" + key).text());
    $("#modDevices").val($("#recordDevices" + key).text());
    //禁用修改关键字field
    disableKeyField();
    
}

function disableKeyField(){
	if($("#modKey").val() == $("#modAccounts").val()){
		$("#modAccounts").attr("disabled", true);
	}
	if($("#modKey").val() == $("#modPhones").val()){
		$("#modPhones").attr("disabled", true);
	}
	if($("#modKey").val() == $("#modDevices").val()){
		$("#modDevices").attr("disabled", true);
	}
}

function saveChangedMessage(){
	var messages = getChangedRecordMessage();
	var key = messages["key"];
	var originalMessage = originalRecord[key];
	var currentMessage = messages["modifiedMessage"];
	if(originalMessage != null && originalMessage != "undefined"){
		if(currentMessage != null && currentMessage != "undefined"){
			if(currentMessage != originalMessage){
				hasChanged = true;
				changedRecord[key] = currentMessage;
			}
		}
	}
}

function clearDialogElements(){
	$("#modAppId").text("");
	$("#modKey").val("");
	$("#modAccounts").val("");
	$("#modPhones").val("");
	$("#modDevices").val("");
}

function saveOriginalRecord(){
	var keys = $('[id^=recordKey]');
	for(var i=0;i<keys.length;i++){
		var key = keys[i].innerHTML;
		originalRecord[key] = getRecordMessage(key);
	}
} 

function resetMessage(){
	$("#account").attr("value", '');
	$("#phone").attr("value", '');
	$("#device").attr("value", '');
	$("#addIdSelect").attr("value", 'default');
}

function resetAllChange(){
	for(var k in originalRecord){
		var recordArray = originalRecord[k].split("&&");
		var key = recordArray[0].split(":")[1];
		var account = recordArray[2].split(":")[1];
		var phone = recordArray[4].split(":")[1];
		var device = recordArray[3].split(":")[1];
		
		$("#recordAccounts" + key).text(account);
		$("#recordPhones" + key).text(phone);
		$("#recordDevices" + key).text(device);
		
		$("#recordAccounts" + key).removeClass("changedRecord");
		$("#recordPhones" + key).removeClass("changedRecord");
		$("#recordDevices" + key).removeClass("changedRecord");
	}
}

function updateRecords(){
	if(!check()){
		return;
	}
	
	var changedStr = "";
	for(var s in changedRecord){
		changedStr += changedRecord[s];
	}
	//$("#changeKeyStr").val(changedStr);
	
	var appID = $("#addIdSelect").val();
	$("#selectedAppID").attr("value", appID);
	
	//$("#searchResultForm").submit();
	$.ajax({
		url: "updateApd",
		type: 'POST',
		dataType: 'json',
		data: {"changeKeyStr":changedStr, "appId": appID},
		success: function(da){
			if(da){
				alert("更新成功");
				searchMessage();
			}
		},
		 error: function(){
            alert("更新失败");
        }
	});
}

function getRecordMessage(keyStr){
	var messageStr = "";
	if(keyStr.length>0){
		var key = $("#recordKey" + keyStr).text();
		var appid = $("#recordAppId" + keyStr).text();
		var account = $("#recordAccounts" + keyStr).text();
		var device = $("#recordDevices" + keyStr).text();
		var phone = $("#recordPhones" + keyStr).text();
		var uid = $("#recordUids" + keyStr).text();
		messageStr = "key:" + key + "&&" + "appId:" + appid + "&&"
		+ "accounts:" + account + "&&" + "devices:" + device + "&&"
		+ "phones:" + phone + "&&" + "uid:" + uid + ";";
	}
	return messageStr;
}

function getChangedRecordMessage(){
	
	var messages = new Array();
	var key = $("#modKey").val();
	var appid = $("#modAppId").text();
	var account = $("#modAccounts").val();
	var device = $("#modDevices").val();
	var phone = $("#modPhones").val();
	var uid = "--";
	//if change some field, the field will be red color
	mergeAndFillTableContent(key, account, phone, device);
	showOrHideSubmitButton();
	
	var messageStr = "key:" + key + "&&" + "appId:" + appid + "&&"
	+ "accounts:" + account + "&&" + "devices:" + device + "&&"
	+ "phones:" + phone + "&&" + "uid:" + uid + ";";
	messages["modifiedMessage"] = messageStr;
	messages["key"] = key;
	return messages;
}

function mergeAndFillTableContent(key, account, phone, device){
	var originalData = originalRecord[key];
	if(originalData == null || originalData == "undefined"){
		alert("不存在这条数据，修改失败");
		return;
	}
	var originalDataArray = originalData.split("&&");
	var originalAccount = originalDataArray[2].split(":")[1];
	var originalDevice = originalDataArray[3].split(":")[1];
	var originalPhone = originalDataArray[4].split(":")[1];
	
	$("#recordAccounts" + key).text(account);
	$("#recordPhones" + key).text(phone);
	$("#recordDevices" + key).text(device);
	
	if(account != originalAccount){
		$("#recordAccounts" + key).addClass("changedRecord");
	}else{
		$("#recordAccounts" + key).removeClass("changedRecord");
	}
	if(phone != originalPhone){
		$("#recordPhones" + key).addClass("changedRecord");
	}else{
		$("#recordPhones" + key).removeClass("changedRecord");
	}
	if(device != originalDevice){
		$("#recordDevices" + key).addClass("changedRecord");
	}else{
		$("#recordDevices" + key).removeClass("changedRecord");
	}
}

function showOrHideSubmitButton(){
	var hasChangedRecord = $(".changedRecord");
	if(hasChangedRecord.length == 0){
		$("#saveButton").hide();
		$("#resetButton").hide();
	}else{
		$("#saveButton").show();
		$("#resetButton").show();
	}
}

function check(){
	if($("#addIdSelect").val()=='default'){
		alert("您还未选择appId!");
		return false;
	}
	var account = $("#account").val();
	var phone = $("#phone").val();
	var device = $("#device").val();
	
	if(account.match(regEx) || phone.match(regEx) || device.match(regEx)){
		alert("输入的字符不合法");
		return false;
	}
	
	if(!(account!= null || $.trim(account)!=''||phone!= null || $.trim(phone)!=''||device!= null || $.trim(device)!='')){
		alert("Account,Phone,Device 不能同时为空!");
		return false;
	}
	return true;
} 

function checkUnsaved(){
	var hasChangedRecord = $(".changedRecord");
	if(hasChangedRecord.length > 0){
		if(confirm("有未保存的数据，确定离开当前页面吗?")){
			return true;
		}else{
			return false;
		}
	}else{
		return true;
	}
	
}

function goToPolicySetting(){
	if(checkUnsaved()){
		$("#platForm").submit();
	}
}

function goToSearch(){
	$("#search").submit();
}

function goToAppGroup(){
	$("#appGroupManager").submit();
}