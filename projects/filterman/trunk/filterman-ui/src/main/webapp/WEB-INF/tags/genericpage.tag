<%@tag description="Overall Page template" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
  <body>
   <header>
   <div class="logo">
		<a><img src="${basePath}/statics/images/logo.png">
		</a>
	</div>
	<div class="top-info">
			<img src="${basePath}/statics/images/logo-title.png">
	</div>
	</header>
	
	<div class="container">
		<aside>
		<ul>
			<li class="appGroupManager navigationTab"><span>组管理</span>
			</li>
			<li class="groupPolicyManager navigationTab"><span>组策略配置</span>
			</li>
			<li class="blacklistManager navigationTab"><span>黑白名单管理</span>
			</li>
			<li class="payBindManager navigationTab"><span>支付绑定修改</span>
			</li>
			<li class="generalManager navigationTab"><span>日志绑定修改</span>
			</li>
			<li class="relatedPhoneManager navigationTab"><span>连号策略修改</span>
			</li>
			<li class="intervalManager navigationTab"><span>支付间隔修改</span>
			</li>
		</ul>
		</aside>
		<section>
		<form action="search" method="get" id="searchMessage"></form>
		<form action="policyGetting" method="post" id="platForm"></form>
		<form action="groupManager" method="get" id="appGroupManager"></form>
		<form action="groupPolicy" method="get" id="groupPolicyManager"></form>
		<form action="wbList" method="get" id="blacklistManager"></form>
		<form action="general" method="get" id="generalManager"></form>
		<form action="relatedPhone" method="get" id="relatedPhoneManager"></form>
		<form action="interval" method="get" id="intervalManager"></form>
    	  <div id="body">
      		<jsp:doBody/>
    	  </div>
		</section>
	</div>
  </body>
</html>