<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="basePath"  value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hello filterman</title>
</head>
<body>
    Hello Filterman!<br>
<c:forEach var="userVO" items="${users}">

    <h1><img src="<c:out value="${basePath}/statics/images/home-navIcons.png" />" /> <c:out value="${user.username}"/></h1>
</c:forEach>
</body>
</html>
