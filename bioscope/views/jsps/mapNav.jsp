<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>


	<%-- 
	<c:forEach items="${pageContext.servletContext.param}" var="s">
		<c:out value="${s}"></c:out>
	</c:forEach>
	--%>
	<c:out value="${initParam}"></c:out>
		<table>
			<tr><td>	<c:out value="${pageContext.servletContext.spider}"></c:out></td></tr>
			<tr>
				<td>Hello</td><td>world</td>
			</tr>
		</table>
		<table>
			<c:forEach items="${initParam}" var="p">
			<tr>				
				<td><c:out value="${p.key}"/></td>
				<c:forEach items="{p.key}" var="value">
					<td><c:out value="${p.value}"/></td>
				</c:forEach>

							</tr>
			</c:forEach>
		</table>	
