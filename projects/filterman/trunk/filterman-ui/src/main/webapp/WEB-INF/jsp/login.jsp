<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
    <title>China Unicom Login</title>
</head>
<script type="text/javascript" src="${basePath}/statics/js/jquery/jquery-1.9.1.js"></script>
<script type="text/javascript" src="${basePath}/statics/js/login.js"></script>
<link href="${basePath}/statics/css/login.css" rel="stylesheet" type="text/css"/>
<body class="bodystyle">
<div class="loginAreaDiv">
    <div class="filtermanLoginDiv">
        <div align="center" class="title">应用商店防护过滤平台
        </div>
        <div class="banner"></div>

        <table class="tableStyle">
            <tbody>
            <tr>
                <td>
                    <div style="height:23px">
                    </div>

                </td>
            </tr>
            <form action="loginValidate" method="post" name="loginForm" id="loginForm">
                <tr>
                    <td height="28"><span class="spanStyle">用户名</span>
                        <input id="username" class="field" type="text" tabindex="1" autocomplete="off" value=""
                               name="username">
                        <span id="inputUserNameHint" class="validateLoginMsg">请输入用户名</span>
                    </td>
                </tr>
                <tr>
                    <td><span class="spanStyle">密&nbsp;&nbsp;码</span>
                        <input id="password" class="field" type="password" tabindex="2" autocomplete="off" value=""
                               name="password">
                        <span id="inputPasswordHint" class="validateLoginMsg">请输入密码</span></td>
                </tr>
                <br>
                <tr>
                    <td>

                        <c:if test="${result == false}">
                            <span id="errorMsg" class="validateLoginMsg">用户名或密码有错</span>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div align="center">
                            <input id="loginButton" class="filtermanButton" type="button" value="登录" onclick="checkLoginInfo();">
                            <input id="cancelButton" class="filtermanButton" type="reset" value="取消">
                        </div>
                    </td>
                </tr>
            </form>

            </tbody>
        </table>
    </div>
</div>

</body>

</html>