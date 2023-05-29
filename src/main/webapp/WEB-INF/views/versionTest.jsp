<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>개발환경 버전 체크</title>
    <style>
    	span {
    		display: inline-block;
    		font-weight: bold;
    		width: 80px;
    	}
    </style>
</head>
<body>
<h2>개발환경 버전</h2><hr>
<span>서버  </span>: 
<%=application.getServerInfo() %><br>
<span>서블릿  </span>: 
<%= application.getMajorVersion() %>.<%= application.getMinorVersion() %><br>
<span>JSP  </span>: 
<%= JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion() %><br>
</body>
</html>