	<h1 style="color:darkred;font-family:arial">Enhancer Result Summary</h1>
	</table>
	<br></br>
	${completed}
	<br></br>
	<c:choose>
		<c:when test="${completed}='1'">
			<h3 style="color:darkred;font-family:arial">All units done.</h3>
		</c:when>
		<c:otherwise>
		
			<h3 style="color:darkred;font-family:arial"><c:out value="${fn:length(doneList)}"/> out of <c:out value="${fn:length(chromosomeList)}"/> done. <a href="<c:url value="/protected/enhancerRefresh"/>">More results awaited...</a></h3>
			
			<META HTTP-EQUIV=Refresh CONTENT="10"; URL="<c:url value="/protected/enhancerRefresh"/>">
		</c:otherwise>
	</c:choose>
	
    ${result}
    <br></br>
