$(function(){
	$(".policyConfig").addClass("highlight");
});

var levelBack = 0;
var lowPolicyPool = new Array();
var highPolicyPool = new Array();
var middlePolicyPool = new Array();
var policyLevelRadioSelected;
var policyApplication;
var errorMessage ={
      existingPoliciesConflict: "当前策略同已有策略有冲突,请重新设置.",
      existingAppPolicyID:"该APP ID已经存在",
      inputAppIDReminder:"请输入新的APP ID"
}
$().ready(function() {
    levelClick();
    //applyPolicyLevel();
    policyApplication = $("input[name='policyApplication'][type='radio']:checked").val();
    if(!policyApplication ||policyApplication == 1){
        $("#APIPolicySelect").attr("disabled", true);
    }else{
        $("#APIPolicySelect").attr("disabled", false);
    }
    levelBack = $("input[name='policyLevel'][type='radio']:checked").val();
    copySelect(getCurrentStrategiesSelect(),document.getElementById("policySelectBack"));

    $("#strategiesPool").click(function(){
        document.getElementById("goLeft").disabled = false;
        document.getElementById("goRight").disabled = true;
        cleanSelected(document.getElementById("lowPresentPolicySelect"));
        cleanSelected(document.getElementById("middlePresentPolicySelect"));
        cleanSelected(document.getElementById("highPresentPolicySelect"));
    });
    $("#highPresentPolicySelect").click(function(){
        document.getElementById("goRight").disabled = false;
        document.getElementById("goLeft").disabled = true;
        cleanSelected(document.getElementById("strategiesPool"));
    });
    $("#middlePresentPolicySelect").click(function(){
        document.getElementById("goRight").disabled = false;
        document.getElementById("goLeft").disabled = true;
        cleanSelected(document.getElementById("strategiesPool"));
    });
    $("#lowPresentPolicySelect").click(function(){
        document.getElementById("goRight").disabled = false;
        document.getElementById("goLeft").disabled = true;
        cleanSelected(document.getElementById("strategiesPool"));
    });

    $("#APIPolicySelect").change(function(){
        changePolicySelect();
    });

    savePlatPolicySelectPool();

});

function savePlatPolicySelectPool(){
    for(var i=0;i< $("#highPresentPolicySelect").get(0).length;i++){
        highPolicyPool[i] =  $("#highPresentPolicySelect").get(0).options[i].value;
    }
    for(var i=0;i< $("#middlePresentPolicySelect").get(0).length;i++){
        middlePolicyPool[i] =  $("#middlePresentPolicySelect").get(0).options[i].value;
    }
    for(var i=0;i< $("#lowPresentPolicySelect").get(0).length;i++){
        lowPolicyPool[i] =  $("#lowPresentPolicySelect").get(0).options[i].value;
    }
    policyLevelRadioSelected = $("input[name='policyLevel'][type='radio']:checked").val();
}

function copySelect(fromSelect,toSelect){
    if(!fromSelect){
        return;
    }
    if(!toSelect){
        return;
    }
    for(var i=0;toSelect.length>0;){
        toSelect.options.remove(i);
    }
    for(var i=0;i<fromSelect.length;i++){
        var oOption = document.createElement("option");
        oOption.text=fromSelect.options[i].text;
        oOption.value=fromSelect.options[i].value;
        toSelect.options.add(oOption);
    }
}

function goleft(){
    var strategiesSelect = getCurrentStrategiesSelect();
    var pool = document.getElementById("strategiesPool");

    if(levelBack != $("input[name='policyLevel'][type='radio']:checked").val()){
        levelBack = $("input[name='policyLevel'][type='radio']:checked").val();
        copySelect(strategiesSelect,document.getElementById("policySelectBack"));
    }

    var sumNumber = "";
    for(var i=0;i<strategiesSelect.length;i++){
        sumNumber += strategiesSelect.options[i].value+",";
    }
    for(var i=0;i<pool.length;i++){
        if(pool.options[i].selected){
            sumNumber += pool.options[i].value+",";
        }
    }

    if(checkPolicyId(sumNumber)){
        for(var i=0;i<pool.length;i++){
            if(pool.options[i].selected){
                var oOption = document.createElement("option");
                if(!checkRepeat(pool.options[i].value,strategiesSelect)){
                    oOption.text=pool.options[i].text;
                    oOption.value=pool.options[i].value;
                    addOptions(oOption);
                }
            }
        }
    }else{
        alert(errorMessage.existingPoliciesConflict);
    }

    displayBlockRight();
    displayNoneRight();

    cleanSelected(document.getElementById("strategiesPool"));

    document.getElementById("goLeft").disabled = true;
    document.getElementById("goRight").disabled = true;

}

function checkRepeat(checkValue,strategiesSelect){
    if(strategiesSelect !=null){
        for(var i=0;i<strategiesSelect.length;i++){
            if(strategiesSelect.options[i].value == checkValue){
                return true;
            }
        }
    }
    return false;
}

function addOptions(oOption){
    var strategiesLevel = $("input[name='policyLevel'][type='radio']:checked").val();
    if(strategiesLevel == 1){
        document.getElementById("lowPresentPolicySelect").options.add(oOption);
    }else if(strategiesLevel == 2){
        document.getElementById("middlePresentPolicySelect").options.add(oOption);
    }else if(strategiesLevel == 3){
        document.getElementById("highPresentPolicySelect").options.add(oOption);
    }
}

function displayNoneRight(){
    var low = document.getElementById("lowPresentPolicySelect");
    var middle = document.getElementById("middlePresentPolicySelect");
    var high = document.getElementById("highPresentPolicySelect");
    var pool = document.getElementById("strategiesPool");
    if(low.length>3){
        $("#lowRight").css("display","none");
    }
    if(middle.length>3){
        $("#middleRight").css("display","none");
    }
    if(high.length>3){
        $("#highRight").css("display","none");
    }
    if(pool.length>12){
        $("#poolRight").css("display","none");
    }
}

function displayBlockRight(){
    $("#lowRight").css("display","block");
    $("#middleRight").css("display","block");
    $("#highRight").css("display","block");
    $("#poolRight").css("display","block");
}

function goright(){
    var strategiesSelect = getCurrentStrategiesSelect();

    if(levelBack != $("input[name='policyLevel'][type='radio']:checked").val()){
        levelBack = $("input[name='policyLevel'][type='radio']:checked").val();
        copySelect(strategiesSelect,document.getElementById("policySelectBack"));
    }

    if(strategiesSelect != ""){
        var num =strategiesSelect.length;
        for(var i=0;i<num&&strategiesSelect.length>=0&&strategiesSelect.length>i;i++){
            if(strategiesSelect.options[i].selected){
                strategiesSelect.options.remove(i);
                i=-1;
            }
        }
    }

    displayBlockRight();
    displayNoneRight();

    document.getElementById("goLeft").disabled = true;
    document.getElementById("goRight").disabled = true;
}

function validateAppId() {
    $("#errorMsg").hide();
    var inputAppId = $("#apiIdText").val();
    $("#appId").attr("value", inputAppId);

    if (inputAppId) {
        for (var i = 0; i < $("#APIPolicySelect").get(0).length; i++) {
            if (inputAppId == $("#APIPolicySelect").get(0).options[i].value) {
                $("#errorMsg").text(errorMessage.existingAppPolicyID);
                $("#errorMsg").show();
                return false;
            }
        }
    }

    return true;
}

function getAppId() {
    var inputAppId = $("#apiIdText").val();
    var selectedAppID = $("#APIPolicySelect").val();
    $("#appId").attr("value", inputAppId);
     if (selectedAppID != "default") {
        $("#appId").attr("value", selectedAppID);
    }

    policyApplication = $("input[name='policyApplication'][type='radio']:checked").val();
    if (policyApplication == 2) {
        if (!$("#appId").val()) {
            $("#errorMsg").text(errorMessage.inputAppIDReminder);
            $("#errorMsg").show();
            return false;
        }
    }
    return true;
}

function insertSelectedId(){
/*
    if (!validateAppId()) {
        return;
    }
    if (!getAppId()) {
        return;
    }
 */
    policyApplication = $("input[name='policyApplication'][type='radio']:checked").val();
    if (policyApplication == 2) {
        var selectedAppID = $("#APIPolicySelect").val();
        if (selectedAppID != "default") {
            $("#appId").attr("value", selectedAppID);
        }
    }

    var strategiesSelect = getCurrentStrategiesSelect();
    if(strategiesSelect == ""){
        $("#policySettingForm").submit();
    }else{
        var sumNumber = "";
        for(var i=0;i<strategiesSelect.length;i++){
            sumNumber += strategiesSelect.options[i].value+",";
        }
        if(checkPolicyId(sumNumber)){
            $("#policyIds").attr("value",sumNumber);
            $("#policySettingForm").submit();
        }else{
            alert(errorMessage.existingPoliciesConflict);
        }
    }
}

function checkPolicyId(checkingIds) {
    var permitMark = true;
    permitMark = forbiddenRule(checkingIds);
    return permitMark;
}

function forbiddenRule(checkingIds) {
    if (checkingIds.indexOf("10") >= 0) {
        if (checkingIds.indexOf("20") >= 0 || checkingIds.indexOf("30") >= 0 || checkingIds.indexOf("90") >= 0) {
            return false;
        }
    }
/*
    if (checkingIds.indexOf("30") >= 0) {
        if (checkingIds.indexOf("90") >= 0) {
            return false;
        }
    }
*/
    return true;
}

function getCurrentStrategiesSelect() {
    var strategiesLevel = $("input[name='policyLevel'][type='radio']:checked").val();
    document.getElementById("strategiesPool").disabled = false;
    $("#poolRight").css("background","#FFFFFF");
    var strategiesSelect = null;
    if(strategiesLevel == 1){
        strategiesSelect = document.getElementById("lowPresentPolicySelect");
    }else if(strategiesLevel == 2){
        strategiesSelect = document.getElementById("middlePresentPolicySelect");
    }else if(strategiesLevel == 3){
        strategiesSelect = document.getElementById("highPresentPolicySelect");
    }else if(strategiesLevel == 0){
        document.getElementById("strategiesPool").disabled = true;
        $("#poolRight").css("background","#DBDBDB");
        strategiesSelect = "";
    }
    return strategiesSelect;
}

function levelClick(){
    var oldStrategiesSelect;
    if(levelBack == 1){
        oldStrategiesSelect = document.getElementById("lowPresentPolicySelect");
    }else if(levelBack == 2){
        oldStrategiesSelect = document.getElementById("middlePresentPolicySelect");
    }else if(levelBack == 3){
        oldStrategiesSelect = document.getElementById("highPresentPolicySelect");
    }else if(levelBack == 0){
        oldStrategiesSelect = "";
    }
    copySelect(document.getElementById("policySelectBack"),oldStrategiesSelect);

    displayBlockRight();
    displayNoneRight();

    cleanAll();
    disabledAll();
    var strategiesSelect = getCurrentStrategiesSelect();
    if(strategiesSelect != null && strategiesSelect != ""){
        strategiesSelect.disabled = false;
        var strategiesLevel = $("input[name='policyLevel'][type='radio']:checked").val();
        if(strategiesLevel == 1){
            $("#lowRight").css("background","#FFFFFF");
        }else if(strategiesLevel == 2){
            $("#middleRight").css("background","#FFFFFF");
        }else if(strategiesLevel == 3){
            $("#highRight").css("background","#FFFFFF");
        }
    }
}

function cleanAll(){
    cleanSelected(document.getElementById("lowPresentPolicySelect"));
    cleanSelected(document.getElementById("middlePresentPolicySelect"));
    cleanSelected(document.getElementById("highPresentPolicySelect"));
    cleanSelected(document.getElementById("strategiesPool"));
}

function cleanSelected(strategiesSelect){
    if(strategiesSelect != null){
        for(var i=0;i<strategiesSelect.length>0;i++){
            strategiesSelect.options[i].selected = false;
        }
    }

}

function disabledAll(){
    document.getElementById("lowPresentPolicySelect").disabled = true;
    $("#lowRight").css("background","#DBDBDB");
    document.getElementById("middlePresentPolicySelect").disabled = true;
    $("#middleRight").css("background","#DBDBDB");
    document.getElementById("highPresentPolicySelect").disabled = true;
    $("#highRight").css("background","#DBDBDB");
    document.getElementById("goLeft").disabled = true;
    document.getElementById("goRight").disabled = true;
}

function applyPolicyLevel(presentAppId){
    policyApplication = $("input[name='policyApplication'][type='radio']:checked").val();
    if(policyApplication == 1){
        $("#apply").attr("disabled", false);
        //$("#apiIdTextArea").hide();
        //retrievePolicySelectPool();
        $("#platForm").submit();
    }else{
    	changePolicySelect();
        //$("#apply").attr("disabled", true);
        $("#APIPolicySelect").attr("disabled", false);
        if($("#APIPolicySelect").val() == "default")   {
            disabledAll();
        }
        $("#policySelectBack").empty();
        initPolicySelectPool();
        cleanAll();
        disabledAll();
        document.getElementById("strategiesPool").disabled = true;
        $("#poolRight").css("background","#DBDBDB");
        //savePlatPolicySelectPool();
        return;
    }
}

function initPolicySelectPool(){

    policyLevelRadioSelected = $("input[name='policyLevel'][type='radio']:checked").val();
    $("input[name='policyLevel']").val([0]);
    $("#lowPresentPolicySelect").empty();
    $("#middlePresentPolicySelect").empty();
    $("#highPresentPolicySelect").empty();
}

function retrievePolicySelectPool(){
     for(var i=0;i< highPolicyPool.length;i++){
    	 $("#highPresentPolicySelect").append("<option>" +highPolicyPool[i] +"</option>");
     }
     for(var i=0;i< middlePolicyPool.length;i++){
    	 $("#middlePresentPolicySelect").append("<option>" +middlePolicyPool[i] + "</option>");
     }
     for(var i=0;i< lowPolicyPool.length;i++){
    	 $("#lowPresentPolicySelect").append("<option>" +lowPolicyPool[i].substring(-1, lowPolicyPool[i]-1) + "</option>");
     }
     //$("input[name='policyLevel']").val([policyLevelRadioSelected]);       
}

function changePolicySelect(){
	    
    if($("#APIPolicySelect").val() == "default"){
    	$("#policySelectBack").empty();
        //$("#apiIdTextArea").show();
        $("#errorMsg").hide();
        initPolicySelectPool();
        disabledAll();
    } else{
        //$("#apiIdTextArea").hide();
        $("#policyAppID").attr("value",$("#APIPolicySelect").val());
        $("#appId").attr("value", $("#APIPolicySelect").val());
        $("#platForm").submit();
    }
}

function goToPolicySetting(){
	$("#platForm").submit();
}

function goToSearch(){
	$("#searchMessage").submit();
}