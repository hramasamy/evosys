<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<jsp:useBean id="info_bus_bean" class="com.biocis.infobus.beans.InfoBusBean" scope="session"/>

<c:choose>
	<c:when test="${param.html==1}">
		<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
		<?xml-stylesheet type="text/xsl" href="<c:url value="/htmls/xsl/giga.xsl"/>"?>
		<table border="0" bgcolor="lightyellow">
			<tr>
				<td>
					 <c:out value="${info_bus_bean.result}"/>
				</td>
			</tr>
		</table>
	</c:when>
	<c:otherwise>
		${info_bus_bean.result}
	</c:otherwise>
</c:choose>
