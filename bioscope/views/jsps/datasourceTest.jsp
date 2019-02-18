	<c:import url="http://localhost:8080/BugTrack/resources/connectors.xml" varReader="xmlSource">
		<x:parse var="doc" doc="${xmlSource}" scope="application"></x:parse>
	</c:import>
		<hr></hr>
		<font size="5"><x:out select="$doc/connectors/description"></x:out></font>
		<hr></hr>
		<table width="100%">
			<tr bgcolor="#4682b4">
				<td><input type="button" onclick="actionPerformed('delete')" value="Delete"/></td>
				<td><input type="button" onclick="actionPerformed('update')" value="Update"/></td>
				<td width="100%"><input type="hidden" name="userAction"/></td>
				<td><input type="button" onclick="actionPerformed('update')" value="Add Data Source"/></td>
			</tr>			
		</table>
		<table width="100%" cellpadding=2 cellspacing=0 border=0>
			<tr>
				<th align="center" id="headercell"><input type="checkbox"></input></th>
				<th id="headercell">Name</th>
				<th width="30%" id="headercell">Description</th>
				<th id="headercell">URL</th>
				<th id="headercell">Driver</th>
				<th id="headercell">DBFactory</th>
				<th id="headercell">User</th>
				<th id="headercell">Password</th>
			</tr>
			<font size="smaller">
			<x:forEach select="$doc/connectors/data-source" var="ds">
				<tr bgcolor="#dcdcec">
					<td align="center"><input type="checkbox"></input></td>
					<td><x:out select="$ds/name"/></td>
					<td width="30%"><x:out select="$ds/description"/></td> 
					<td><x:out select="$ds/url"/></td>
					<td><x:out select="$ds/driver"/></td>
					<td><x:out select="$ds/dbfactory"/></td>
					<td><x:out select="$ds/user"/></td>
					<x:set var="pwd" select="$ds/password"/>
					<td><input type="password" value="${pwd}" disabled="disabled"></input></td>
				</tr>
				<tr></tr>
			</x:forEach>
			</font>			
		</table>
		<table width="100%">
			<tr bgcolor="#4682b4">
				<td><input type="button" onclick="actionPerformed('delete')" value="Delete"/></td>
				<td><input type="button" onclick="actionPerformed('update')" value="Update"/></td>
				<td width="100%"><input type="hidden" name="userAction"/></td>
				<td><input type="button" onclick="actionPerformed('update')" value="Add Data Source"/></td>
			</tr>			
		</table>
		<table width="100%">
			<tr>
				<td width="100%"><input type="hidden" name="userAction"/></td>
				<td nowrap="nowrap">Messages 1 - 3 of 3 </td><td>|</td><td>First</td><td>|</td><td>Previous</td><td>|</td><td>Next</td><td>|</td><td>Last</td>
			</tr>			
		</table>
	
