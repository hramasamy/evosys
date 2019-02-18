<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


<c:set value="${param.mapName}" var="map"/>
<c:set value="${initParam.mapDocStylesheet}" var="xslFile"/>
<!--
<c:import url="file:///c:/jboss-3.0.3/wisdom/server/xstore/file/maps/ContactManager/RelationshipManagement.xml" varReader="xmlSource">
	<x:parse var="doc" doc="${xmlSource}" scope="application"/>
</c:import>
-->

<c:import url="http://meerkat.oreillynet.com/?&p=4999&_fl=xml&t=ALL" varReader="xmlSource">
    <x:parse var="doc" xml="${xmlSource}" scope="application" />
  </c:import>

<!--
<c:import url="${xslFile}" var="stylesheet"></c:import>

<x:transform xslt="${stylesheet}" docSystemId="file:///c:\jboss-3.0.3\wisdom\server\xstore\file\maps\ContactManager\RelationshipManagement.xml"/>-->

<html>
	<head></head>
	<body>
		<table>
			<tr>
				<td>Stylesheet: <c:out value="${stylesheet}"></c:out></td>
				<td>Map Name: <c:out value="${map}"></c:out></td>
			</tr>
		</table>
	</body>
</html>
