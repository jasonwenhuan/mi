/**
 * wenhuan
 */
var DataTable = jClass.extend({
	init : function(container,aDefines,dataSource){
		this.container = container;
		this.aDefines = aDefines;
		this.dataSource = dataSource;
		this.html = new Array();
		this.generateTable();
	},
	generateTable : function(){
		var width = 0;
		var headInformation = this.aDefines.columnDefs;
		for(var i=0;i<this.aDefines.columnDefs.length;i++){
	    	width += headInformation[i].width;
	    }
		this.html[this.html.length] = "<table class='mytable' style='width:" + width + "px'>";
		this.generateHead(this.html);
		this.html[this.html.length] = "<tbody>";
	    this.generateRow(this.html);
	    this.html[this.html.length] = "</tbody>";
	    this.html[this.html.length] = "</table>";
	    document.getElementById(this.container).innerHTML = this.html.join("");;
	},
	generateHead : function(){
		this.html[this.html.length] = "<thead class='thead'><tr>"
	    var headInformation = this.aDefines.columnDefs;
		var headStr = "";
	    for(var i=0;i<this.aDefines.columnDefs.length;i++){
	    	if(headInformation[i].sortable){
	    		headStr += "<th width='" + headInformation[i].width + "px' class='myhth'><a href='javascript:tableSort(\""+headInformation[i].key+"\");'>";
	    	}else{
	    		headStr += "<th width='" + headInformation[i].width + "px' class='myhth'><a>";
	    	}
	    	headStr += headInformation[i].label;
	    	headStr += "</a></th>";
	    }
	    this.html[this.html.length] = headStr;
	    this.html[this.html.length] = "</tr></thead>";
	},
	generateRow : function(){
		var rowinformation = this.aDefines.columnDefs;
		var dataSource = this.dataSource;
		var rowStr = "";
		for(var j=0;j<this.dataSource.length;j++){
			rowStr += "<tr>";
			for(var k=0;k<this.aDefines.columnDefs.length;k++){
				rowStr += "<td class='myttd'>";
				if(rowinformation[k].key == "checkbox"){
					//rowStr += "<input type='checkbox' name='chkBox' value='" + dataSource[j][rowinformation[k+1].key] + "'></input>";
					rowStr += "<input type='checkbox' name='chkBox' value='" + dataSource[j]["id"] + "'></input>";
				}else{
					if(rowinformation[k].formatter != null && rowinformation[k].formatter != "" && rowinformation[k].formatter != "undefined"){
						this.formatFunction = rowinformation[k].formatter;
						rowStr += eval(this.formatFunction + "(dataSource[j][rowinformation[k].key])");
					}else{
						rowStr += dataSource[j][rowinformation[k].key];
					}
				}
				rowStr += "</td>";
			}
			rowStr += "</tr>";
		}
		this.html[this.html.length] = rowStr;
	}
});

