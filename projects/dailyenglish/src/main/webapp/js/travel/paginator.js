var Paginator = jClass.extend({
	init : function(currPage, totalRecords, size, containerid, gotoFunction){
		this.currentPage = currPage;
	    this.rowsPerPage = size;
	    this.container = containerid;
	    this.totalRecords = totalRecords;
	    this.totalPages = Math.ceil(this.totalRecords / this.rowsPerPage);
	    this.gotoFunction = gotoFunction;
	},
	render: function(){
		var html = new Array();
		var previousPage = this.currentPage - 1;
		var nextPage = this.currentPage + 1;
		if(previousPage < 1){
			previousPage = 1;
		}
		if(nextPage > this.totalPages){
			nextPage = this.totalPages;
		}
		html[html.length] = "<a href='javascript:if(" + this.currentPage + " != 1 && "+ 
		                    this.totalPages +" != 0){" + this.gotoFunction + "(1)};'>" +
				            "<span>首页</span></a>";
		html[html.length] = "&nbsp;<a href='javascript:if(" + this.currentPage + " != 1 && "
		                    + this.totalPages + " != 0){" + this.gotoFunction + "(" + previousPage + ")};'>" +
		                    "<span>上一页</span></a>";
		html[html.length] = "&nbsp;<span class='paging'>Page</span>&nbsp;<select class='paging' id='paginatorSelect' " +
				            "onchange='" + this.gotoFunction + "(this.options[this.selectedIndex].value)'>";
		for(var i=1;i<=this.totalPages;i++){
			html[html.length] = "<option value='" + i + "'";
			html[html.length] = ((i == this.currentPage) ? "selected" : "") + ">";
			html[html.length] = i;
		    html[html.length] = "</option>";
		}
		html[html.length] = "</select><span class='paging'> of " + this.totalPages;
	    html[html.length] = "&nbsp;</span><a href='javascript:if(" + this.totalPages + "" +
	    		            " != " + this.currentPage + " && " + this.totalPages + " != 0){" + this.gotoFunction + "" +
	    		            "(" + nextPage + ")}'><span>下一页</span></a>";
	    html[html.length] = "&nbsp;<a href='javascript:if(" + this.totalPages + "" +
	    		            " != " + this.currentPage + " && " + this.totalPages + " != 0){" + this.gotoFunction + "(" + this.totalPages + "" +
	    		            ")};'><span>末页</span></a>";
	    if (this.container != null) {
	        document.getElementById(this.container).innerHTML = html.join("");
	    }
	}
});