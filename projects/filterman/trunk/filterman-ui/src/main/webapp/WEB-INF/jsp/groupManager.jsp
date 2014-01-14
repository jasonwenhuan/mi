<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>组管理</title>

<c:import url="common/commonHead.jsp"></c:import>

<link rel="stylesheet" href="${basePath}/statics/css/appGroup.css"/>
<script type="text/javascript" src="${basePath}/statics/js/appGroup.js"></script>



</head>
<body>
    <t:genericpage>
   	 	<jsp:body>
        	<div class="groupColumn1">
	        <div class="addAppGroup">
	            <input id="groupName" type="text" maxlength="10" placeholder="应用组名" />&nbsp;&nbsp;
	            <select id="groupType">
	            	<option value="-1">请选择</option>
	            	<option value="0">平台型</option>
					<option value="1">应用型</option>
					<option value="2">渠道型</option>
	            </select>
	            	</div>
	       <div class="appGroupList">
	            <div class="result appGroupResult groupList">
	                <table id="appGroupTable"></table>
	                <div id="appGroupPager"></div>
	            </div>
	        </div>
        </div>
        
        <div class="groupColumn4">
        	<div class="addAppGroupBtn">
                <a href="javascript:createAppGroup();"><span>添加</span></a>
            </div>
            <div class="deleteAppGroupBtn">
                <a href="javascript:deleteAppGroup();"><span>删除</span></a>
            </div>
        </div>
        
        <div class="groupColumn2">
             <div class="tableTitle">
             	 <span>
                                                      包含的应用
                 </span>
             </div>
             <div class="result appGroupResult">
	               <table id="list"></table> 
				   <div id="pager"></div> 
			</div>
        </div>
        <div class="addAndRemoveAppColumn">
            <input id="addAppsBtn" class="addAppButton" type="button" onclick="addAppsToAppGroup();" value="< 添加" disabled=""/>
            <input id="removeAppsBtn" class="removeAppButton" type="button" onclick="dropAppsFromGroup();" value="> 移除" disabled=""/>
        </div>
        <div class="groupColumn3">
      	   <div class="appSearchField">
               <div class="searchField"><input id="searchField" type="text" value="" /></div>
               <div class="searchButton">
                   <a><span>搜索</span></a>
               </div>
               <div class="resetButton">
                   <a><span>复位</span></a>
               </div>
           </div>
	       <div class="result appGroupResult">
	             <table id="appList"></table> 
				 <div id="appListPager"></div> 
	       </div>
        </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>