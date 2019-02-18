		<sql:query dataSource="jdbc/BugTrack" var="projects">
			select ProjectFK ProjectID, Name ProjectName from mirror_env, projects
			where projects.ID=mirror_env.ProjectFK group by projects.ID
		</sql:query>
		
			<table width="100%">
				<tr bgcolor="#4682b4">
					<td><input type="button" onclick="actionPerformed('delete')" value="Delete"/></td>
					<td><input type="button" onclick="actionPerformed('update')" value="Update"/></td>
					<td width="100%"><input type="hidden" name="userAction"/></td>
					<td><input type="button" onclick="actionPerformed('request')" value="Request New Hardware"/></td>
				</tr>			
			</table>

		<c:forEach items="${projects.rows}" var="row">
			<font size="5">${row.ProjectName}</font>

			<table width="100%" cellpadding=2 cellspacing=0 border=0>
				<tr>
					<th align="center" id="headercell"><input type="checkbox"></input></th>
					<th id="headercell" align="left">Software</th>
					<th id="headercell">Version</th>
					<th id="headercell">IPAddress</th>
					<th id="headercell">Status</th>
					<th id="headercell">URL</th>
					<th id="headercell">LastUpdate</th>
					<th id="headercell" width="30%">Misc</th>
				</tr>
				<font size="smaller">
			
			<sql:query dataSource="jdbc/BugTrack" var="Envs">
				Select * from mirror_env where mirror_env.ProjectFK=${row.ProjectID} 
			</sql:query>
					<c:forEach items="${Envs.rows}" var="current">
						<tr bgcolor="#dcdcec">
							<td align="center"><input type="checkbox"></input></td>
							<td width="20%" align="left">${current.purpose}</td>
							<td>${current.version}</td> 
							<td>${current.machineFK}</td>
							<td>${current.status}</td>
							<td>${current.url}</td>
							<td>${current.lastUpdate}</td>
							<td>${current.misc}</td>
						</tr>
					</c:forEach>					
				</font>			
			</table>
			<hr></hr>
			<br></br>
		</c:forEach>
			<table width="100%">
				<tr bgcolor="#4682b4">
					<td><input type="button" onclick="actionPerformed('delete')" value="Delete"/></td>
					<td><input type="button" onclick="actionPerformed('update')" value="Update"/></td>
					<td width="100%"><input type="hidden" name="userAction"/></td>
					<td><input type="button" onclick="actionPerformed('request')" value="Request New Hardware"/></td>
				</tr>			
			</table>			
		
		<table width="100%">
			<tr>
				<td width="100%"><input type="hidden" name="userAction"/></td>
				<td nowrap="nowrap">Messages 1 - 3 of 3 </td><td>|</td><td>First</td><td>|</td><td>Previous</td><td>|</td><td>Next</td><td>|</td><td>Last</td>
			</tr>			
		</table>
	
