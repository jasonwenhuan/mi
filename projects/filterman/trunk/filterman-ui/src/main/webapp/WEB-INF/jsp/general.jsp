<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>通用</title>

<c:import url="common/commonHead.jsp"></c:import>
<link rel="stylesheet" href="${basePath}/statics/css/general.css"/>
<script type="text/javascript" src="${basePath}/statics/js/general.js"></script>
</head>
<body>
	<c:import url="dialog/bindChange.jsp"></c:import>
    <t:genericpage>
   	 	<jsp:body>
   	 	    <div class="generalMain">
   	 	        <div class="lookField">
   	 	           <div class="lookInputField">
	   	 	           <span class="lookFieldTitle">
	   	 	             	  订单或手机号：
	   	 	           </span>
	   	 	           <input id="orderIdAndPhone" type="text" class="textField"></input>
   	 	           </div>
   	 	           <div class="geSearchBtn">
	   	 	           <a class="generalSearchButton" onclick="resetSearch();"><span>
	   	 	               	复位
	   	 	           </span></a>
   	 	           </div>
   	 	           <div class="geSearchBtn">
	   	 	           <a class="generalSearchButton" onclick="searchRequest();"><span>
	   	 	               	搜索
	   	 	           </span></a>
   	 	           </div>
   	 	        </div>
   	 	        <div class="geSearchBtn bindChange">
	   	 	           <a class="generalSearchButton" onclick="openBindChangeDialog();"><span>
	   	 	               	绑定调整
	   	 	           </span></a>
   	 	        </div>
   	 	        <div class="tableField">
   	 	           <table id="bindTable"></table>
	                <div id="bindPager"></div>
   	 	        </div>
   	 	    </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>