<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<jsp:useBean id="cal" class="com.sbs.zerobugs.utils.CalendarBean"/>

<html>
	<head>
		<script src="js/submit.js"></script>
		<title>Event Detail</title>
	</head>
	<body>
	<form action="eventHandler.jsp" method="post">
		<table>
			<input type="button" onclick="actionPerformed(save)" value="Save"/>
			<input type="button" onclick="actionPerformed(saveAndAddAnother)" value="Save and Add Another"/>
			<input type="button" onclick="actionPerformed(cancel)" value="Cancel"/>
		</table>
		<hr></hr>
		<table>
			<tr>
			<td>Title:&nbsp;&nbsp;</td><td width="100%"><input type="text" name="title" maxlength="80"/></td>
			</tr>
			<tr><td nowrap="nowrap">Event Type:</td>
			
			<sql:query var="evTypes">
				Select Type from EventType Order by ID desc
			</sql:query>

			<td width="100%">				
				<select name="eventType">
					<c:forEach items="${evTypes.rows}" var="row">
						<c:forEach items="${row}" var="col">
							<option>${fn:escapeXml(col.value)}</option>
						</c:forEach>
					</c:forEach>
				</select>
			</td>
			</tr>
			<tr>
				<td>Date:</td> 
				<sql:query var="months">
					Select Name from Month
				</sql:query>				
				<td>
					<table>
						<tr>
							<td>
								<select name="month">
									<c:forEach items="${months.rows}" var="row">
										<c:forEach items="${row}" var="col">
											<option>${fn:escapeXml(col.value)}</option>
										</c:forEach>
									</c:forEach>
								</select>
							</td>
							<td>
								<select>
									<c:forEach items="${cal.daysInMonth}" var="number">
										<option>${number}</option>
									</c:forEach>		
								</select>
							</td>			
						</tr>
					</table>
					
				</td>
								
			</tr>
		</table>
	</form>
	</body>
</html>
