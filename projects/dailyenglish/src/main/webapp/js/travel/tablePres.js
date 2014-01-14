var currentTableMeta;
var currentTableType;

var appointTableMeta = {
	    columnDefs: [
            {key:"checkbox", label:"", formatter:"",width:150 , sortable:false},
            {key:"name", label:"名称", formatter:"",width:150 , sortable:false},
            {key:"tourgroup", label:"旅游团", formatter:"",width:150 , sortable:false},
            {key:"client", label:"客户", formatter:"",width:150 , sortable:false},
            {key:"creator", label:"创建者", formatter:"",width:150 , sortable:false},
            {key:"detail", label:"详细信息", formatter:"",width:200 , sortable:false}
	    ],
	    state : {
	        page:{"rowsPerPage":10,"totalRecords":"","visibility":null,"recordOffset":0, "local":false},
            sort:{key:"name",dir:0},
            filter:{"byUser":false,"appointName":"","tourgroup":"","client":"","creator":""}
	    }
};

var tourgroupTableMeta = {
		columnDefs: [
		    {key:"checkbox", label:"", formatter:"",width:150 , sortable:false},
            {key:"name", label:"名称", formatter:"",width:150 , sortable:false},
            {key:"creator", label:"创建者", formatter:"",width:150 , sortable:false},
            {key:"detail", label:"详细信息", formatter:"",width:150 , sortable:false}
        ],
 	    state : {
 	         page:{"rowsPerPage":10,"totalRecords":"","visibility":null,"recordOffset":0, "local":false},
             sort:{key:"name",dir:0},
             filter:{"byUser":false,"tourgroupName":"","creator":""}
 	    }
};

var clientTableMeta = {
		columnDefs: [
		    {key:"checkbox", label:"", formatter:"",width:150 , sortable:false},
            {key:"name", label:"名称", formatter:"",width:150 , sortable:false},
            {key:"age", label:"年龄", formatter:"",width:150 , sortable:false},
            {key:"email", label:"邮箱", formatter:"",width:150 , sortable:false},
            {key:"creator", label:"创建者", formatter:"",width:150 , sortable:false},
            {key:"remark", label:"标记", formatter:"",width:150 , sortable:false}
        ],
 	    state : {
 	         page:{"rowsPerPage":10,"totalRecords":"","visibility":null,"recordOffset":0, "local":false},
             sort:{key:"name",dir:0},
             filter:{"byUser":false,"clientName":"","creator":""}
 	    }
};
