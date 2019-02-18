<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
	<head>
		<title>Search Result</title>
		<LINK rel="stylesheet" type="text/css" href="xsl/header.css" title="hs"/>
	</head>
	<body>
		<c:if test="${!empty param.searchStr}">
			<c:redirect url="http://www.google.com/search?hl=en&lr=&ie=UTF-8&oe=UTF-8&q=${fn:escapeXml(param.searchStr)}"/>
		</c:if>
	</body>
</html>
