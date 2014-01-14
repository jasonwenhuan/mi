function generatePDF(){
	//alert("test generatePDF");
	jPDFGenerater.generatePDF(function(data){
		alert(data);
	});
	/*jPDFGenerater.getRootPath(function(data){
		alert(data);
	});*/
}