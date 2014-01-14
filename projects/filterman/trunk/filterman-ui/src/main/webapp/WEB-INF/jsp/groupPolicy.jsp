<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>组策略配置</title>

<c:import url="common/commonHead.jsp"></c:import>
<link rel="stylesheet" href="${basePath}/statics/css/groupPolicy.css"/>
<script type="text/javascript" src="${basePath}/statics/js/groupPolicy.js"></script>



</head>
<body>
    <t:genericpage>
   	 	<jsp:body>
        	<div class="groupPolicyColumn1">
		        <div class="groupPlicyList">
	                <table id="groupTable"></table>
	                <div id="groupPager"></div>
		        </div>
        	</div>
        	
        	<div class="groupPolicyColumn2">
		        <div class="policyLevel">
		           <div class="highLevel">
		               <span>高防护策略</span>
		               <input id="highLevelRa" type="radio" name="policyLevel" onchange="updateGroupLevel('8');"></input>
		           </div>
		           
		           <div class="middleLevel">
		               <span>中防护策略</span>
		               <input id="middleLevelRa" type="radio" name="policyLevel" onchange="updateGroupLevel('4');"></input>
		           </div>
		           
		           <div class="lowLevel">
		               <span>低防护策略</span>
		               <input id="lowLevelRa" type="radio" name="policyLevel" onchange="updateGroupLevel('2');"></input>
		           </div>
		           
		           <div class="noLevel">
		               <span>无防护策略</span>
		               <input id="noLevelRa" type="radio" name="policyLevel" onchange="updateGroupLevel('0');"></input>
		           </div>
		        </div>
        	</div>
        	
        	<div class="groupPolicyColumn3">
		        <div class="currentPolicy">
	                <table id="currentPolicyTable"></table>
	                <div id="currentPolicyPager"></div>
		        </div>
        	</div>
        	
        	<div class="groupPolicyColumn4">
		        <div class="policyControl">
		            <div class="addPolicyBtn">
		                <input id="removePolicy" type="button" value="去除   >" onclick="removePolicyFromGroup();"></input>
		            </div>
		            <div class="removePolicyBtn">
		            	<input id="addPolicy" type="button" value="<  添加" onclick="addPolicyFromPool();"></input>
		            </div>
		        </div>
        	</div>
        	
        	<div class="groupPolicyColumn5">
		        <div class="allPolicy">
	                <table id="allPolicyTable"></table>
	                <div id="allPolicyPager"></div>
		        </div>
        	</div>
        
    	</jsp:body>
    </t:genericpage>
</body>
</html>