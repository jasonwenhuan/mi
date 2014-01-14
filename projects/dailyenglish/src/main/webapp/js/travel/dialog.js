var Dialog = {
   "width" : "1000px",
   "height" : "800px",
   init : function(){
   },
   open : function(mywidth, myheight, dialogName, title){
       var client_width = document.documentElement.clientWidth;
       var client_height = document.documentElement.clientHeight;

	   if(typeof($("#dialogShadow")[0]) == "undefined"){
		   $("body").prepend("<div id='dialogShadow'></div>");
		   var shadow = $("#dialogShadow");
           shadow.css("width", client_width + "px");
		   shadow.css("height", client_height + "px");
	   }
       
	   $("body").prepend("<div id='dialog'></div>");
 
	   var dialog = $("#dialog");
       
	   dialog.css("position", "absolute");

	   var _left = (client_width - (mywidth + 5)) / 2;
	   dialog.css("left", (_left < 0 ? 0 : _left) + document.documentElement.scrollLeft + "px");

	   var _top = (client_height - (myheight + 30 + 50 + 5)) / 2;
	   dialog.css("top", (_top < 0 ? 0 : _top) + document.documentElement.scrollTop + "px");

	   dialog.css("width", mywidth + "px");

	   dialog.css("height", myheight + 30 + 50 + "px");

	   dialog.append("<div id='d_header'></div>");

	   var header = $("#d_header")
       header.css("width", mywidth+"px");
	   header.css("height", myheight / 10+"px");

       
	   var headerTitleText = "<span id='header_title' style='float:left;'>" + (title == undefined ? "": title) + "</span>";
	   header.append(headerTitleText);

	   var closeDialog = "<span id='close_title' onclick='Dialog.closeTheDialog();' style='cursor:pointer;float:right'>关闭</span>";
	   header.append(closeDialog);

	   var htmlobj=$.ajax({url:dialogName,async:false});
       
       dialog.append(htmlobj.responseText);
	   
   },
   closeTheDialog : function(){
       $("#dialogShadow").remove();
	   $("#dialog").remove();
   }
}


function submitPosition(){
    alert("test submit");
}

function cancelPosition(){
    $("#dialogShadow").remove();
	$("#dialog").remove();
}