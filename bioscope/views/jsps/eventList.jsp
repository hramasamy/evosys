		<table width="100%">
			<tr bgcolor="#bbffff">
				<td align="left"><a href="eventDetail.jsp">Add Event</a></td>
			</tr>
		</table>
		<table>
			<tr bgcolor="lightyellow">
				<td>View:</td>
				<td><a href="eventList.jsp">All</a>&nbsp;&nbsp;</td>
				<td>|</td>
				<td><a href="eventList.jsp">Upcoming Events</a>&nbsp;&nbsp;</td>
				<td>|</td>
				<td><a href="eventList.jsp">Past</a>&nbsp;&nbsp;</td>
			</tr>
		</table>
		<table>
			<tr bgcolor="lightgreen">
				<td><input type="hidden" name="userAction"/></td>
				<td><input type="button" onclick="actionPerformed('delete')" value="Delete"/></td>
				<td align="right" width="100%">
					<table>
						<tr>
							<sql:query var="evType">
								Select Type from EventType order by ID desc
							</sql:query>			
							<td align="right">Show type:</td>
							<td>
								<select>
									<c:forEach items="${evType.rows}" var="row">
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
		<table>
			<tr bgcolor="#ffffaa">
				<th align="center"><input type="checkbox"></input></th>
				<th align="center" width="15%">&nbsp;&nbsp;&nbsp<a href="eventList.jsp">Date</a></th>
				<th align="center" width="50%">Event</th>
				<th align="center" width="20%"><a href="eventList.jsp">Organizer</a>&nbsp;&nbsp;&nbsp</th>
				<th align="center" width="15%"><a href="eventList.jsp">Project</a></th>
			</tr>
		</table>
