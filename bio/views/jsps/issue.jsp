<jsp:useBean id="env" class="com.sbs.zerobugs.utils.Environment"/>

   	<font color="#gg99ff" size="6"><strong>Incidence Report</strong></font>
   	<hr></hr>
   	<form action="../jspSource/issueHandler.jsp">
             <table bgcolor="lightyellow" border="2">
			<tbody>
				<h2 align="center">General Information</h2>
			</tbody>
			<tr>
				<td valign="middle">
					<table>
					<tr>
						<td>Incident#</td> <td><input size="50" type="text" name="incidentID" value="Call(Auto)" disabled="disabled"></input></td>
					</tr>
					<tr>
						<td>Customer Name</td>
						<sql:query dataSource="jdbc/BugTrack" var="custList">
							Select Name from Projects order by Name
						</sql:query>
						<sql:query var="sevList">
							Select Type from Priority
						</sql:query>
						
						<td>
							<select name="customerNm">
								<c:forEach items="${custList.rows}" var="row">
									<c:forEach items="${row}" var="col">
										<option>${fn:escapeXml(col.value)}</option>
									</c:forEach>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>	
						<td>Reported Date</td><td><input size="50" type="text" name="repDate" value="${env.currentDateTime}" disabled="disabled"></input></td>
					</tr>
					<tr>
						<td>Need by date</td><td><input size="50" type="text" name="needBy"></input></td>
					</tr>
					<tr>
						<td>Contact name</td><td><input size="50" type="text" name="contactNm"></input></td>
					</tr>
					<tr>
						<td>Contact Telephone</td><td><input  size="50" size="50" type="text" name="contactTel"></input></td>
					</tr>
					<tr>
						<td>Contact Fax</td><td><input size="50" type="text" name="contactFax"></input></td>
					</tr>
					<tr>
						<td>Contact Email</td><td><input size="50" type="text" name="contactEmail"></input></td>
					</tr>
					<tr>
						<td>Severity</td>
						<td>
						<select name="Severity">
								<c:forEach items="${sevList.rows}" var="row">
									<c:forEach items="${row}" var="col">
										<option>${fn:escapeXml(col.value)}</option>
									</c:forEach>
								</c:forEach>
							</select>
						</td>
					</tr>
					</table>
				</td>
			</tr>
		</table>
		<table bgcolor="lightyellow" border="2">
			<tbody>
			<br></br>
				<h2>Detailed Information</h2>
			</tbody>
			<tr>
				<td>Title</td><td><input size="55" type="text"></input></td>
			</tr>
			<tr>
				<td valign="top">Description&nbsp&nbsp&nbsp&nbsp</td><td><textarea rows="5" cols="39" name="description"></textarea></td>
			</tr>
			<tr>
				<td valign="top">Work-around&nbsp&nbsp&nbsp&nbsp</td><td><textarea rows="5" cols="39" name="workArnd"></textarea></td>
			</tr>
		</table>
		<br></br>
		<table>
			<tr align="justify">
				<td><input type="hidden" name="userAction"></td>
				<td><input type="button" value="Submit" onclick="actionPerformed('submit')"/></td>
				<td><input type="button" value="Assign" onclick="actionPerformed('assign')"/></td>
				<td><input type="button" value="Reset" onclick="actionPerformed('reset')"/></td>
				<td><input type="button" value="Cancel" onclick="actionPerformed('cancel')"/></td>
			</tr>
		</table>
	</form>
