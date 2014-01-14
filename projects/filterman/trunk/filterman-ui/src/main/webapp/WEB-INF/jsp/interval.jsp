<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>支付间隔修改</title>
<c:import url="common/commonHead.jsp"></c:import>
<link rel="stylesheet" href="${basePath}/statics/css/interval.css"/>
<script type="text/javascript" src="${basePath}/statics/js/interval.js"></script>
</head>
<body>
    <t:genericpage>
   	 	<jsp:body>
   	 	    <c:import url="common/searchField.jsp"></c:import>
   	 	    <c:import url="common/crudField.jsp"></c:import>
   	 	    <c:import url="dialog/intervalRuleChange.jsp"></c:import>    
 	 	    <div class="tableField">
  	 	       <table id="intervalTable"></table>
	           <div id="intervalPager"></div>
   	 	    </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>