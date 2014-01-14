<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>黑白名单管理</title>

<c:import url="common/commonHead.jsp"></c:import>
<link rel="stylesheet" href="${basePath}/statics/css/blacklist.css"/>
<script type="text/javascript" src="${basePath}/statics/js/blacklist.js"></script>

</head>
<body>
    <t:genericpage>
   	 	<jsp:body>
			<c:import url="common/searchField.jsp"></c:import>   	 		
   	 	    <div class="blackListColumn1">
   	 	        <span>黑名单</span><input id="blacklistRad" type="radio" name="blackListTypeRad" onchange="showBlacklist();"></input>
   	 	        &nbsp;&nbsp;&nbsp;&nbsp;
   	 	        <span>白名单</span><input id="whitelistRad" type="radio" name="blackListTypeRad" onchange="showWhitelist();"></input>
   	 	    </div>
   	 	    <div class="blackListColumn2">
   	 	        <div class="blackListKeyId"><span>黑名单账号</span><input id="blackListKeyId" type="text"></input></div>
   	 	        <div class="blackListComment"><span>设置原因</span><input id="blackListComment" type="text"></input></div>
   	 	        
   	 	        <div class="blackListAddButton">
   	 	             <a href="javascript:addBlacklist();"><span>添加黑名单</span></a>
   	 	        </div>
   	 	    </div>
   	 	    <div class="blackListColumn3">
 	            <table id="blacklistTable"></table>
                <div id="blacklistPager"></div>
                 <div class="whiteListDeleteButton">
   	 	             <a href="javascript:deleteBlacklist();"><span>删除黑名单</span></a>
   	 	        </div>
   	 	    </div>
   	 	    
   	 	    <div class="blackListColumn4">
   	 	        <div class="whiteListKeyId"><span>白名单账号</span><input id="whiteListKeyId" type="text"></input></div>
   	 	        <div class="whiteListComment"><span>设置原因</span><input id="whiteListComment" type="text"></input></div>
   	 	        
   	 	        <div class="whiteListAddButton">
   	 	             <a href="javascript:addWhitelist();"><span>添加白名单</span></a>
   	 	        </div>
   	 	    </div>
   	 	    <div class="blackListColumn5">
 	            <table id="whitelistTable"></table>
                <div id="whitelistPager"></div>
                <div class="whiteListDeleteButton">
   	 	             <a href="javascript:deleteWhitelist();"><span>删除白名单</span></a>
   	 	        </div>
   	 	    </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>