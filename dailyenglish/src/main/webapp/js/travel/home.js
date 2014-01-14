$(document).ready(function() {
	$('li.mainlevel').mousemove(function(){
		$(this).find('ul').slideDown();//you can give it a speed
	});
	$('li.mainlevel').mouseleave(function(){
		$(this).find('ul').slideUp("slow");
	});
	dwr.util.useLoadingMessage("Please wait");
	changeTableObj("init");
	resetFilter();
	makeTable();
	applyCurrentUser();
});

var globleParams = {
		id : null,
		createDate : null
}

var globalRecordOffset = 0;

function makeTable(){
	if(currentTableType == "appoint"){
		jAppoint.getAppoints(currentTableMeta.state, function(data){
			var appointTable = new DataTable("mytable", currentTableMeta, data.data);
			currentTableMeta.state.page.totalRecords = data.state.page.totalRecords;
			renderPaginator();
		});
	}else if(currentTableType == "tourgroup"){
		jTourgroup.getTourgroups(currentTableMeta.state, function(data){
			var tourgroupTable = new DataTable("mytable", currentTableMeta, data.data);
			currentTableMeta.state.page.totalRecords = data.state.page.totalRecords;
			renderPaginator();
		});
	}else{
		jClient.getClients(currentTableMeta.state, function(data){
			var tourgroupTable = new DataTable("mytable", currentTableMeta, data.data);
			currentTableMeta.state.page.totalRecords = data.state.page.totalRecords;
			renderPaginator();
		});
	}
}

function renderPaginator(){
	var rowsPerPage = currentTableMeta.state.page.rowsPerPage;
	var currentPage = 1 + Math.ceil(globalRecordOffset / rowsPerPage);
	var paging = new Paginator(currentPage, currentTableMeta.state.page.totalRecords, rowsPerPage, "paging", "gotoPage");
	paging.render();
}

function gotoPage(pageNum){
	globalRecordOffset = (pageNum - 1) * currentTableMeta.state.page.rowsPerPage;
	currentTableMeta.state.page.recordOffset = globalRecordOffset;
	makeTable();
}

function fillAppointSelectList(){
	var createTourGroupTableMeta = {
		    state : {
		        page:{"rowsPerPage":"","totalRecords":"","visibility":null,"recordOffset":0, "local":false},
	            sort:{key:"name",dir:0},
	            filter:{"byUser" : true}
		    }
	};
	jTourgroup.getTourgroups(createTourGroupTableMeta.state, function(data){
		if(data != null){
			var tourgroups = data.data;
			for(var i=0;i<tourgroups.length;i++){
				var option = "<option>" + tourgroups[i].name+ "</option>"
				$("#tourgroupList").append(option);
			}
		}
	});
	var createClientTableMeta = {
		    state : {
		        page:{"rowsPerPage":"","totalRecords":"","visibility":null,"recordOffset":0, "local":false},
	            sort:{key:"name",dir:0},
	            filter:{"byUser" : true}
		    }
	};
	jClient.getClients(createClientTableMeta.state, function(data){
		if(data != null){
			var clients = data.data;
			for(var i=0;i<clients.length;i++){
				var option = "<option>" + clients[i].name+ "</option>"
				$("#clientList").append(option);
			}
		}
	});
}


function addAppoint(){
	var dialog = new Dialog.open(500,400,"addAppoint.html","添加预约");
	fillAppointSelectList();
}

function changeAppoint(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个预约，选择表格左边的选择框");
		return;
	}
	if(deleteTitles.length > 1){
		alert("更改不能多选，请选择至多一个");
		return;
	}
	
	var dialog = new Dialog.open(500,400,"changeAppoint.html","Change Appoint");
	jAppoint.getAppointById(deleteTitles[0], function(data){
		if(data == null){
			alert("change appoint 取数据失败，请联系开发者");
		}else{
			$("#changeAppointName").val(data.name);
			DWREngine.setAsync(false); 
			fillAppointSelectList();
			DWREngine.setAsync(true); 
			var tourgroupListStr = "#tourgroupList option[text='"+ data.tourgroup +"']";
			$("tourgroupListStr").attr("selected", "selected"); 
			var clientListStr = "#clientList option[text='"+ data.client +"']";
			$("clientListStr").attr("selected", "selected"); 
			$("#changeCreateDate").val(formatDate(data.createDate));
		    $("#changeCreateUser").val(data.creator);
			$("#changeAppointDetail").val(data.detail);
			globleParams.id = data.id;
			globleParams.createDate = data.createDate;
		}
	});
}

function submitAppoint(){
	var name = $("#appointName").val();
	var detail = $("#appointDetail").val();
	if(name == null){
		alert("名称不能为空");
		return;
	}
	var appointBean = new Object();
	appointBean.name = name;
	appointBean.detail = detail;
	appointBean.tourgroup = $("#tourgroupList").val();
	appointBean.client = $("#clientList").val();
	jAppoint.createAppoint(appointBean, function(data){
		if(data == "success"){
			alert("创建成功");
			$("#dialogShadow").remove();
			$("#dialog").remove();
			makeTable();
		}else{
			alert("创建失败");
		}
	});
}

function updateAppoint(){
	var name = $("#changeAppointName").val();
	var detail = $("#changeAppointDetail").val();
	var creator = $("#changeCreateUser").val();
	var tourgroup = $("#tourgroupList").val();
	var client = $("#clientList").val();
	var id = globleParams.id;
	var createDate = globleParams.createDate;
	if(name == null){
		alert("名称不能为空");
		return;
	}
	if(id == null){
		alert("id 不能为空");
		return;
	}
	if(createDate == null){
		alert("创建日期不能为空");
		return;
	}
	var appointBean = new Object();
	appointBean.name = name;
	appointBean.detail = detail;
	appointBean.creator = creator;
	appointBean.tourgroup = tourgroup;
	appointBean.client = client;
	appointBean.id = id;
	appointBean.createDate = createDate;
	jAppoint.updateAppoint(appointBean, function(){
		alert("更新成功");
		$("#dialogShadow").remove();
		$("#dialog").remove();
		makeTable();
	});
}

function deleteAppoint(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个预定，选择表格左边的选择框");
		return;
	}else{
		if(confirm("确定要删除您选择的预约吗?")){
			jAppoint.deleteAppoint(deleteTitles, function(data){
				if(data == "success"){
					alert("删除成功");
					makeTable();
				}else{
					alert("您没有权限删除某些预定,请检查是否登录，如果已经登录出现此错误，联系创建者");
				}
			});
		}
	}
}

function addTourgroup(){
	var dialog = new Dialog.open(500,400,"addTourgroup.html","添加旅游团");
}

function submitTourgroup(){
	var name = $("#tourgroupName").val();
	var detail = $("#tourgroupDetail").val();
	if(name == null){
		alert("名称不能为空");
		return;
	}
	var tourgroupBean = new Object();
	tourgroupBean.name = name;
	tourgroupBean.detail = detail;
	jTourgroup.createTourgroup(tourgroupBean, function(data){
		if(data == "success"){
			alert("创建成功");
			$("#dialogShadow").remove();
			$("#dialog").remove();
			makeTable();
		}else{
			alert("创建失败");
		}
	});
}

function updateTourgroup(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个旅游团，选择表格左边的选择框");
		return;
	}
	if(deleteTitles.length > 1){
		alert("旅游团不能多选，请选择至多一个");
		return;
	}
	
	var dialog = new Dialog.open(500,400,"changeTourgroup.html","修改旅游团");
	jTourgroup.getTourgroupById(deleteTitles[0], function(data){
		if(data != null){
			$("#tourgroupName").val(data.name);
			$("#tourgroupDetail").val(data.detail);
			$("#tourgroupCreator").val(data.creator);
			globleParams.id = data.id;
		}
	});
}

function submitUpdateTourgroup(){
	var name = $("#tourgroupName").val();
	var detail = $("#tourgroupDetail").val();
	var creator = $("#tourgroupCreator").val();
	var id = globleParams.id;
	if(name == null){
		alert("名称不能为空");
		return;
	}
	if(id == null){
		alert("id 不能为空");
		return;
	}
	var tourgroupBean = new Object();
	tourgroupBean.name = name;
	tourgroupBean.detail = detail;
	tourgroupBean.creator = creator;
	tourgroupBean.id = id;

	jTourgroup.updateTourgroup(tourgroupBean, function(data){
		if(data == "success"){
			alert("更新成功");
			$("#dialogShadow").remove();
			$("#dialog").remove();
			makeTable();
		}else{
		    alert("更新失败");	
		}
	});
}

function deleteTourgroup(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个旅游团，选择表格左边的选择框");
		return;
	}else{
		if(confirm("确定要删除您选择的旅游团吗?")){
			jTourgroup.deleteTourgroup(deleteTitles, function(data){
				if(data == "success"){
					alert("删除成功");
					makeTable();
				}else{
					alert("您没有权限删除某些预定,请检查是否登录，如果已经登录出现此错误，联系创建者");
				}
			});
		}
	}
}

function addClient(){
	var dialog = new Dialog.open(500,400,"addClient.html","添加客户");
}

function submitClient(){
	var name = $("#clientName").val();
	var age = $("#clientAge").val();
	var email = $("#clientEmail").val();
	var detail = $("#tourgroupDetail").val();
	if(name == null){
		alert("名称不能为空");
		return;
	}
	var clientBean = new Object();
	clientBean.name = name;
	if(age != null){
		clientBean.age = parseInt(age);
	}
	clientBean.email = email;
	clientBean.remark = detail;
	jClient.createClient(clientBean, function(data){
		if(data == "success"){
			alert("创建成功");
			$("#dialogShadow").remove();
			$("#dialog").remove();
			makeTable();
		}else{
			alert("创建失败");
		}
	});
}

function changeClient(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个客户，选择表格左边的选择框");
		return;
	}
	if(deleteTitles.length > 1){
		alert("客户不能多选，请选择至多一个");
		return;
	}
	
	var dialog = new Dialog.open(500,400,"changeClient.html","修改客户");
	jClient.getClientById(deleteTitles[0], function(data){
		if(data != null){
			$("#changeClientName").val(data.name);
			$("#changeClientAge").val(data.age);
			$("#changeClientEmail").val(data.email);
			$("#changeClientCreator").val(data.creator);
			$("#changeClientDetail").val(data.remark);
			globleParams.id = data.id;
		}
	});
}

function submitChangeClient(){
	var name = $("#changeClientName").val();
	var age = $("#changeClientAge").val();
	var email = $("#changeClientEmail").val();
	var remark = $("#changeClientDetail").val();
	var creator = $("#changeClientCreator").val();
	var id = globleParams.id;
	if(name == null){
		alert("名称不能为空");
		return;
	}
	if(id == null){
		alert("id 不能为空");
		return;
	}
	if(creator == null){
		alert("创建者不能为空");
		return;
	}
	var clientBean = new Object();
	clientBean.name = name;
	clientBean.age = age;
	clientBean.email = email;
	clientBean.remark = remark;
	clientBean.creator = creator;
	tourgroupBean.id = id;

	jClient.updateClient(clientBean, function(data){
		if(data == "success"){
			alert("更新成功");
			$("#dialogShadow").remove();
			$("#dialog").remove();
			makeTable();
		}else{
		    alert("更新失败");	
		}
	});
}

function deleteClient(){
	var tableObj = document.getElementsByName("chkBox");
	var deleteTitles = new Array();
	var j = 0;
	for(var i in tableObj){
		var obj = tableObj[i];
		if(obj.type == "checkbox" && obj.checked){
			var value = obj.value;
			deleteTitles[j++] = value;
		}
	}
	if(deleteTitles.length < 1){
		alert("请选择一个旅游团，选择表格左边的选择框");
		return;
	}else{
		if(confirm("确定要删除您选择的旅游团吗?")){
			jClient.deleteClient(deleteTitles, function(data){
				if(data == "success"){
					alert("删除成功");
					makeTable();
				}else{
					alert("您没有权限删除某些预定,请检查是否登录，如果已经登录出现此错误，联系创建者");
				}
			});
		}
	}
}

function searchTable(){
	if(currentTableType == "appoint"){
		currentTableMeta.state.filter.appointName = $("#filterName").val();
		currentTableMeta.state.filter.tourgroup = $("#filterTourgroup").val();
		currentTableMeta.state.filter.client = $("#filterClient").val();
		currentTableMeta.state.filter.creator = $("#filterCreator").val();
	}else if(currentTableType == "tourgroup"){
		currentTableMeta.state.filter.tourgroupName = $("#filterName").val();
		currentTableMeta.state.filter.creator = $("#filterCreator").val();
	}else if(currentTableType == "client"){
		currentTableMeta.state.filter.clientName = $("#filterName").val();
		currentTableMeta.state.filter.creator = $("#filterCreator").val();
	}
	makeTable();
}

function resetFilter(){
	$("#filterName").val("");
	$("#filterTourgroup").val("");
	$("#filterClient").val("");
	$("#filterCreator").val("");
	searchTable();
}

function changeTableObj(obj){
	if(obj == "init" || obj.id == "appoint"){
		currentTableMeta = appointTableMeta;
		currentTableType = "appoint";
		$("#appoint").addClass("selectOneItem");
		$("#tourgroup").removeClass("selectOneItem");
		$("#client").removeClass("selectOneItem");
		$("#fTourgroup").show();
		$("#fClient").show();
	}else if(obj.id == "tourgroup"){
		currentTableMeta = tourgroupTableMeta;
		currentTableType = "tourgroup";
		$("#appoint").removeClass("selectOneItem");
		$("#tourgroup").addClass("selectOneItem");
		$("#client").removeClass("selectOneItem");
		$("#fTourgroup").hide();
		$("#fClient").hide();
	}else{
		currentTableMeta = clientTableMeta;
		currentTableType = "client";
		$("#appoint").removeClass("selectOneItem");
		$("#tourgroup").removeClass("selectOneItem");
		$("#client").addClass("selectOneItem");
		$("#fTourgroup").hide();
		$("#fClient").hide();
	}
	makeTable();
}

function applyCurrentUser(){
	jUserLoginIn.getLoginUserSessionName(function(data1){
		jUserLoginIn.getLoginUserSessionRole(function(data2){
			var role = "普通用户";
			if(data2 == 1){
				role = "管理员"
			}
			var welcomeDetail = "当前登录用户：  " + data1 + "--" + role;
			$("#userNameAndRole").html(welcomeDetail);
		});
	});
}

function cancel(){
	$("#dialogShadow").remove();
	$("#dialog").remove();
}

function formatDate(date) {
    if (date != null) {
        if (date.getMonth() + 1 < 10 && date.getDate() > 9)
            var dateString = "0" + (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();
        else if (date.getMonth() + 1 < 10 && date.getDate() < 10)
            var dateString = "0" + (date.getMonth() + 1) + "/0" + date.getDate() + "/" + date.getFullYear();
        else if (date.getMonth() + 1 > 9 && date.getDate() < 10)
            var dateString = (date.getMonth() + 1) + "/0" + date.getDate() + "/" + date.getFullYear();
        else
            var dateString = (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getFullYear();

        return dateString;
    } else {
        return "";
    }
}