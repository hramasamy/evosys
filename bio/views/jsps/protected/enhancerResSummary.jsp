	<h1 style="color:darkred;font-family:arial">Enhancer Result Summary</h1>
	</table>

	<c:choose>
		<c:when test="${stopSearch=='1'}">
			<h3 style="color:darkred;font-family:arial"><c:out value="${fn:length(doneList)}"/> out of <c:out value="${fn:length(chromosomeList)}"/> done. &nbsp;
			<h3 style="color:darkred;font-family:arial">Search stopped</h3>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${completed=='1'}">
					<h3 style="color:darkred;font-family:arial">All units done.</h3>
				</c:when>
				<c:otherwise>
					<h3 style="color:darkred;font-family:arial"><c:out value="${fn:length(doneList)}"/> out of <c:out value="${fn:length(chromosomeList)}"/> done. More results awaited...</h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="<c:url value="/enhancerRefresh?stopSearch=1"/>">Stop Search</a>&nbsp;&nbsp;
			
					<META HTTP-EQUIV=Refresh CONTENT="10"; URL="<c:url value="/protected/enhancerRefresh"/>">
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
		
    ${result}
    <br></br>
