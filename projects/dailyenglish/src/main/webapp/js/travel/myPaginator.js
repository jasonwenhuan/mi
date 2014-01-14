var tableMeta = {
	    state : {
	        page:{"rowsPerPage":10,"totalRecords":"50","visibility":null,"recordOffset":0, "local":false},
            sort:{key:"createtime",dir:0},
            filter:{}
	    }
};


function display(){
	var currentPage = 1 + Math.ceil(tableMeta.state.page.recordOffset / tableMeta.state.page.rowsPerPage);
	var paging = new Paginator(currentPage, tableMeta.state.page.totalRecords, tableMeta.state.page.rowsPerPage, "paging", "gotoPage");
    paging.render();
	alert(tableMeta.state.page.recordOffset);
}


function gotoPage(pageNum){
    tableMeta.state.page.recordOffset = (pageNum-1)*tableMeta.state.page.rowsPerPage;
	display();
}