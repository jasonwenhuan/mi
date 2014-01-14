<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>连号策略修改</title>
<c:import url="common/commonHead.jsp"></c:import>
<link rel="stylesheet" href="${basePath}/statics/css/relatedPhone.css"/>
<script type="text/javascript" src="${basePath}/statics/js/relatedPhone.js"></script>
</head>
<body>
    <t:genericpage>
   	 	<jsp:body>
   	 	   <c:import url="common/searchField.jsp"></c:import>
   	 	   <c:import url="common/crudField.jsp"></c:import>
   	 	   <c:import url="dialog/relatedPhoneRuleChange.jsp"></c:import>    
 	 	    <div class="tableField">
  	 	       <table id="relatedRuleTable"></table>
	           <div id="relatedRulePager"></div>
   	 	    </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>