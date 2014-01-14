var hasPlatformGroup = [];
$(function(){
	$(".appGroupManager").addClass("highlight");
	initAppGroupsTable();
	initAppRefGroupTable();
	initApplicationTable();
});

function initAppGroupsTable(){
	$("#appGroupTable").jqGrid({ 
	    url:'groups', 
	    datatype: "json", 
	    mtype: 'GET',
	       colNames:["应用组ID", "应用组名", "创建日期", "创建用户", "组类型", "已选"], 
	       colModel:[ 
	           {name:'groupId',index:'groupId', hidden:true},
	           {name:'groupName',index:'groupName', width:80, align:"left", sortable: false}, 
	           {name:'createDate',index:'createDate', hidden: true}, 
	           {name:'createUser',index:'createUser', hidden: true}, 
	           {name:'type',index:'type', formatter: setGroupType, hidden: true}, 
	           {name:'',index:'', formatter: formatAppGroupCheckBox, width:40, sortable: false}
	       ], 
	       rowNum:-1, 
	       pager: '#appGroupPager', 
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
	    loadComplete: function () {
			checkPlatform();
	    },
	    onCellSelect: function(rowid,iCol,cellcontent,e){
	    	getApplications(rowid);
	    }
	 }); 
}

function setGroupType(cellvalue, options, rowObject){
	if(cellvalue == "0"){
		hasPlatformGroup.push(cellvalue);
	}
}

function resetPlatformGroup(){
	hasPlatformGroup = [];
	hasPlatformGroup.length = 0;
}

function checkPlatform(){
	if(hasPlatformGroup.length > 0){
		$("#groupType option[value='0']").each(function() {
		    $(this).remove();
		});
	}
}

function formatAppGroupCheckBox(cellvalue, options, rowObject){
	return "<input type='checkbox' name='appgroupcheckbox' id='" + rowObject.groupId + "'></input>";
}

function formatGroupName(cellvalue, options, rowObject){
	if(cellvalue == null || cellvalue == "-1"){
		return "无";
	}else{
		return cellvalue;
	}
}

function initAppRefGroupTable(){
	var iCheckbox;
	$("#list").jqGrid({ 
	    url:'appsByGroup', 
	    datatype: "json", 
	    mtype: 'POST',
	       colNames:["id", "应用ID", "应用名", "所在組ID", "所在組名", "加入日期", "創建用戶"], 
	       colModel:[ 
	           {name:'id',index:'id', hidden:true },
	           {name:'appId',index:'appId', width:180, sortable: true, align:"left", sortable: false},
	           {name:'appName',index:'appName', width:80, sortable: true, align:"left", sortable: false}, 
	           {name:'groupId',index:'groupId', hidden: true}, 
	           {name:'groupName',index:'groupName', hidden: true}, 
	           {name:'updateDate',index:'updateDate', width:80, sortable: true, align:"left", sortable: false},
	           {name:'updateUser',index:'updateUser', hidden: true}
	       ], 
	       pager: '#pager', 
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
	       postData: {"groupId" : ""},
	       autowidth: false,
	    height:280, 
	    viewrecords: true, 
	    recordpos: "left",
	    pagerpos: "right",
	    sortable:true, 
	    sortorder: "asc",
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    multiselect: true,
	    loadComplete: function () {
	    	   // for put checkbox in last column
	    	  /* if (iCheckbox === undefined) {
	    		   iCheckbox = getColumnIndexByName($(this), "cb");
	    	   }
	    	   if (iCheckbox >= 0) {
	    		   moveTableColumn(this.rows, iCheckbox, "jqgrow");
	    	   }*/
	    	   
	    },
	    onSelectRow: function(rowid, status){
	    	appRefSelect();
    	},
    	onSelectAll: function(rowid, status){
    		appRefSelect();
    	}
	 }); 
	 
	// for put checkbox in last column
	 /*if (iCheckbox === undefined) {
		   iCheckbox = getColumnIndexByName($("#list"), "cb");
	 }
	 
	 var $htable = $($("#list")[0].grid.hDiv).find("table.ui-jqgrid-htable");
     if ($htable.length > 0) {
   	     moveTableColumn($htable[0].rows, iCheckbox);
     } 
     
     moveTableColumn($("#list")[0].rows, iCheckbox, "jqgfirstrow");*/
}

function appRefSelect(){
	
	var grid = $("#list");
	var removeAppBtn = $("#removeAppsBtn");
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	if(selRowIds.length == 0){
		removeAppBtn.attr("disabled", true);
	}else{
		removeAppBtn.attr("disabled", false);
	}
}

function initApplicationTable(){
	$("#appList").jqGrid({ 
	    url:'allApps', 
	    datatype: "json", 
	    mtype: 'GET',
	       colNames:["id", "应用ID", "应用名", "所在組ID", "所在组", "更新日期", "更新用戶"], 
	       colModel:[ 
	           {name:'id',index:'id', hidden:true },
	           {name:'appId',index:'appId', width:180, sortable: false, align:"left"},
	           {name:'appName',index:'appName', width:80, sortable: false, align:"left"}, 
	           {name:'groupId',index:'groupId', hidden: true},
	           {name:'groupName',index:'groupName', formatter: formatGroupName, width:80, sortable: false, align:"left"},
	           {name:'updateDate',index:'updateDate', hidden: true},
	           {name:'updateUser',index:'updateUser', hidden: true},
	       ], 
	       pager: '#appListPager', 
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
	    autowidth: false,
	    height:280, 
	    viewrecords: true, 
	    recordpos: "left",
	    pagerpos: "right",
	    sortable:true, 
	    sortorder: "asc",
	    altRows:true,
	    altclass:"jqgridBackground",
	    scrollOffset: 0,
	    multiselect: true,
	    onSelectRow: function(rowid, status){
	    	appSelect();
    	},
    	onSelectAll: function(rowid, status){
    		appSelect();
    	}
	 }); 
}

function appSelect(){
	var grid = $("#appList");
	var addAppBtn = $("#addAppsBtn");
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	if(selRowIds.length == 0){
		addAppBtn.attr("disabled", true);
	}else{
		addAppBtn.attr("disabled", false);
	}
}

function createAppGroup(){
	var groupName = $("#groupName").val().trim();
	if(groupName == "" || groupName == "undefined"){
		alert("请输入应用组名");
		return;
	}
	if(groupName.match(regEx)){
		alert("输入的应用组不合法，请重新输入");
		return;
	}
	var groupType = $("#groupType").val().trim();
	if(groupType == "-1" || groupType == "" || groupType == "undefined"){
		alert("请输入应用组类型");
		return;
	}
	
	$.ajax({
		url: "addGroup",
		type: 'POST',
		dataType: 'json',
		data: {"groupType":groupType, "groupName": groupName},
		success: function(da){
			if(da){
				alert("创建应用组成功");
				reloadAppGroupTable();
			}
		},
		 error: function(){
            alert("创建应用组失败");
        }
	});
}

function deleteAppGroup(){
	var selectedObjs = $("input:checkbox[name='appgroupcheckbox']:checked");
	if(selectedObjs.length == 0){
		alert("请选择");
		return;
	}
	if(confirm("确定删除这" + selectedObjs.length + "项和它所对应的策略吗？")){
		var appGroupIds = [];
		for(var i = 0; i < selectedObjs.length; i++){
			appGroupIds.push(selectedObjs[i].id);
		}
		$.ajax({
			url: "deleteGroup",
			type: 'POST',
			dataType: 'json',
			data: {"groupIds":appGroupIds},
			traditional: true,
			success: function(da){
				if(da){
					alert("删除成功");
					goToAppGroup();
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

function getApplications(rowid){
	
	var groupId = $("#appGroupTable").getRowData(rowid).groupId;
	var groupName = $("#appGroupTable").getRowData(rowid).groupName;;
	
	currentSelectedAppGroup = groupId;
	currentSelectedAppGroupName = groupName;
	
	$("#list").jqGrid('setGridParam',{ 
        url:"appsByGroup", 
        datatype: "json",
        mtype: "POST",
        postData: {"groupId" : groupId},
        page:1 
    }).trigger("reloadGrid");
}

function addAppsToAppGroup(){
	var grid = $("#appList");
	
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	
	if(selRowIds.length == 0){
		alert("还没有选择应用;");
		return;
	}
	
	if(currentSelectedAppGroup == undefined || 
			currentSelectedAppGroupName == undefined){
		alert("还没有选择应用组");
		return;
	}
	
	var appData = [];
	for(var i = 0; i < selRowIds.length; i++){
		var application = grid.getRowData(selRowIds[i]);
		var object = new Object();
		object.id = application.id;
		object.appId = application.appId;
		object.appName = application.appName;
		object.groupId = currentSelectedAppGroup;
		object.groupName = currentSelectedAppGroupName;
		object.updateDate = application.updateDate;
		object.updateUser = application.updateUser;
		
		appData.push(object);
	}
	
	$.ajax({
		url: "appsUpdate",
		type: 'POST',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(appData),
		success: function(da){
			if(da){
				alert("添加app到app组成功");
				reloadListGrid(currentSelectedAppGroup);
				reloadAppListGrid("-1");
			}
		},
		 error: function(){
            alert("添加app到app组失败");
        }
	});
}


function dropAppsFromGroup(){
    var grid = $("#list");
	
	var selRowIds = grid.jqGrid('getGridParam','selarrrow');
	
	if(selRowIds.length == 0){
		alert("还没有选择应用;");
		return;
	}
	
	if(currentSelectedAppGroup == undefined || 
			currentSelectedAppGroupName == undefined){
		alert("还没有选择应用组");
		return;
	}
	
	var appData = [];
	for(var i = 0; i < selRowIds.length; i++){
		var application = grid.getRowData(selRowIds[i]);
		var object = new Object();
		object.id = application.id;
		object.appId = application.appId;
		object.appName = application.appName;
		object.groupId = "-1";
		object.groupName = "-1";
		object.updateDate = application.updateDate;
		object.updateUser = application.updateUser;
		
		appData.push(object);
	}
	
	$.ajax({
		url: "appsUpdate",
		type: 'POST',
		dataType: 'json',
		contentType: "application/json; charset=utf-8",
		data: JSON.stringify(appData),
		success: function(da){
			if(da){
				alert("移除APP成功");
				reloadListGrid(currentSelectedAppGroup);
				reloadAppListGrid("-1");
			}
		},
		 error: function(){
            alert("移除APP失败");
        }
	});
}

function findAppByName(){
	var appName = $("#searchField").val().trim();
	if(appName == ""){
		alert("输入的应用名");
		return;
	}
	
	if(appName.match(regEx)){
		alert("输入的应用名不合法，请重新输入");
		return;
	}
	
	reloadAppListGrid(appName);
}

function resetAppSearch(){
	$("#searchField").val("");
	reloadAppListGrid("-1");
}

function reloadAppListGrid(appName){
	$("#appList").jqGrid('setGridParam',{ 
        url:"appsByGroup", 
        datatype: "json",
        mtype: "POST",
        postData: {"groupId" : "-1", "appName" : appName},
        page:1 
    }).trigger("reloadGrid");
}

function reloadListGrid(groupId){
	$("#list").jqGrid('setGridParam',{ 
        url:"appsByGroup", 
        datatype: "json",
        mtype: "POST",
        postData: {"groupId" : groupId, "appId" : "-1"},
        page:1 
    }).trigger("reloadGrid");
}

function reloadAppGroupTable(){
	$("#appGroupTable").jqGrid('setGridParam',{ 
        url:"groups", 
        datatype: "json",
        mtype: "GET",
        page:1 
    }).trigger("reloadGrid");
}