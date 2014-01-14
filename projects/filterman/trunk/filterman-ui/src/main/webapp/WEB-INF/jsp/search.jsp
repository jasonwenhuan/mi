<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>支付绑定管理</title>

<c:import url="common/commonHead.jsp"></c:import>
<script type="text/javascript" src="${basePath}/statics/js/searchMessage.js"></script>
<link href="${basePath}/statics/css/searchMessage.css" rel="stylesheet" type="text/css" />
</head>
<body onload="saveOriginalRecord()">
	<t:genericpage>
   	 	<jsp:body>
   	 	<div class="search">
                <h2><i></i>支付绑定管理</h2>
                <div class="search-form cf">
                    <form action="search" method="post" id="searchResultForm">
	                    <input type="hidden" value="" id="changeKeyStr" name="changedRecords"/>
	                    <div class="search-item">
	                        <p>
	                            <label for="game_id">游戏账号:</label><input type="text" name="account" value="${inputMessage.account}" id="account"/>
	                            <label for="phone">手机号:</label><input type="text" name="phone" value="${inputMessage.phone}" id="phone"/>
	                            <label for="equipment">设备号:</label><input type="text" name="device" value="${inputMessage.device}" id="device"/>
	                        </p>
	                        <p>
	                            <label for="app_id">应用名称:</label>
	                            <select id="addIdSelect">
	                                <option id="selectappId" 
										<c:if test="${inputMessage.appId == null}">selected="selected"</c:if>
	                                    	value="default">
												应用选择
									</option>
									<c:forEach var="appID" items="${appIdList}">
										<option value="${appID.id}"
										<c:if test="${inputMessage.appId == appID.id}">selected="selected"</c:if>>
										&nbsp;&nbsp;&nbsp;&nbsp;${appID.name}</option>
									</c:forEach>
	                            </select>
	                            <input type="hidden"
							        value="" id="selectedAppID" name="appId" />
	                        </p>
	                    </div>
	                    <div class="search-button">
	                        <input type="button" onclick="searchMessage()"/>
	                    </div>
                    </form>
                </div>
                </div>
		<div class="result">
			<table cellpadding="0" cellspacing="0">
				<thead>
				    <c:if test="${tableDataList!=null}">
						<tr>
					    	<th>搜索关键字</th>
							<th>应用ID</th>
							<th>游戏账号</th>
							<th>手机号</th>
							<th>设备号</th>
							<th>黑名单</th>
							<th></th>
						</tr>
					</c:if>
				</thead>
				<tbody>
					<c:forEach var="tableData" items="${tableDataList}" varStatus="status">
						<tr <c:choose><c:when test="${status.count%2==0}">class="odd"</c:when><c:otherwise>class="even"</c:otherwise></c:choose>>
							<td><a id="recordKey${tableData.key}">${tableData.key}</a></td>
							<td><a id="recordAppId${tableData.key}">${tableData.appId}</a></td>
							<td><a id="recordAccounts${tableData.key}">${tableData.accounts}</a></td>
							<td><a id="recordPhones${tableData.key}">${tableData.phones}</a></td>
							<td><a id="recordDevices${tableData.key}">${tableData.devices}</a></td>
							<td><a id="recordUids${tableData.key}">${tableData.uid}</a></td>
							<td>
							    <c:if test="${tableData.uid == '--'}">
									<a class="modify" onclick="modify('${tableData.key}');"></a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		 <div class="action">
               <a style="display:none" id="saveButton" class="submit" onclick="updateRecords();"></a>
               <a style="display:none" id="resetButton" class="reset" onclick="resetAllChange()"></a>
         </div>
         
          <div class="dialog">
                <div class="close"></div>
                
                <div class="diaTitle"><span>绑定修改</span></div>
                <div class="diaContent">
                    <div class="modField">
                        <div class="modAppIdLabel">应用ID : <span id="modAppId"></span></div>
                    </div>
                    <div class="modField">
                      	<div class="modAppIdLabel">搜索关键字 : <input disabled="disabled" id="modKey" type="text"></input></div>
                    </div>
                    <div class="modField">
                       	<div class="modAppIdLabel">账号 ： <input id="modAccounts" type="text"></input></div> 
                    </div>
                    <div class="modField">
                       	 <div class="modAppIdLabel">手机号 : <input id="modPhones" type="text"></input></div>
                    </div>
                    <div class="modField">
                    	<div class="modAppIdLabel">设备号 : <input id="modDevices" type="text"></input></div>
                    </div>
                </div>
                
                <div class="action">
                    <a class="submit" onclick="saveChangedMessage()"></a>
                    <a class="modify"></a>
                </div>
          </div>
    	</jsp:body>
    </t:genericpage>
</body>
</html>