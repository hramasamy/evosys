<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<jsp:useBean id="env" class="com.sbs.zerobugs.utils.Environment"/>
<jsp:useBean id="cal" class="com.sbs.zerobugs.utils.CalendarBean" scope="session"/>
<script>
	function go(where, what) {
		var monthIndex;
	}
	
</script>
	<c:set var="origURL" value="${pageContext.request.requestURL}"/>
		<c:if test="${!empty (param.month)}">
			<c:set target="${cal}" property="monthIndex" value="${param.month}"/>
		</c:if>

		<c:if test="${!empty (param.year)}">
			<c:set target="${cal}" property="year" value="${param.year}"/>
		</c:if>
		
		<table>
			<tr>
			<td bgcolor="lightblue"><a href="day">Day</a></td>
			<td>&nbsp;&nbsp</td>
			<td bgcolor="lightblue"><a href="week">Week</a></td>
			<td>&nbsp;&nbsp</td>
			<td bgcolor="lightblue">Month</td>
			<td>&nbsp;&nbsp</td>
			<td bgcolor="lightblue"><a href="year">Year</a></td>
			<td>&nbsp;&nbsp</td>
			<td bgcolor="lightblue"><a href="eventList.jsp">Event List</a></td>
			</tr>
		</table>
		<table bgcolor="lightyellow">
			<tr>
				<td><img src="../htmls/images/leftB.gif" alt="Prev" onclick="go(-1, 'month')"/></td>
				
				<td><input type="hidden" name="monthIndex" value="${cal.monthIndex}"</td>
				
				<td><font size="6"/>${cal.month}</td>
				
				<td><img src="../htmls/images/rightB.gif" alt="Next" onclick="go(1, 'month')"/></td>
				
				<td>&nbsp;&nbsp;&nbsp;&nbsp</td>
				
				<td><img src="../htmls/images/leftB.gif" alt="Prev" onclick="go(-1, 'year')"/></td>
				<td><input type="hidden" name="year" value="${cal.year}"</td>
				<td><font size="6"/>${cal.year}</td>
				<td><img src="../htmls/images/rightB.gif" alt="Next" onclick="go(1, 'year')"/></td>
			</tr>
		</table>
		${cal.cal}
		
