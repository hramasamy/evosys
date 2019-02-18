<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
		<!--
		<table width="100%" cellspacing="2" cellpadding="0">
			<tr bgcolor="#ff0055">
				<td id="location"><font face="Arial" size="3" color="#eeeedd">&nbsp;You are here: <b>Home</b></font></td>
			</tr>
		</table>-->
		<table border="0" width="100%" height="80%" cellspacing="0" cellpadding="2">
			<tr>
								<td width="60%" valign="top">
					<table width="100%" cellspacing="0" cellpadding="0">
						<tr bgcolor="#707070">
							<td width="90%">&nbsp;&nbsp;<font color="#ff0000" face="Arial" size="2"><STRONG>Have a 
									question?</font> <font color="#ffffff" face="Arial" size="2">Ask&nbsp;Us 
									</STRONG></font></td>
							<td width="10%" align="right"><a href="<c:url value="/views/jsps/helpJsp"/>"><STRONG><font color="#ffffff" face="Arial" size="2">Help?</STRONG></FONT></a></td>
						</tr>
						<tr>
							<td width="100%" colspan="2" rowspan="6">
								<form action="<c:url value="/views/jsps/askExpert.jsp"/>" method="get">
									<input type="hidden" name="raw" value="1">
									<table width="100%" height="40%" bgcolor="lightgrey" cellspacing="0" cellpadding="0">
										<tr>
											<td valign="top" width="5%">
												&nbsp;
											</td>
											<td width="80%">
												&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
										<tr>
											<td>&nbsp;</td>
											<td valign="top"><font face="Arial" color="#da0e0a"><Strong>Question:</Strong></font>
											</td>
											<td>&nbsp;</td>
										<tr>
										<tr>
											<td>&nbsp;</td>
											<td valign="top">
												<input type="text" name="query" size="100">
											</td>
											<td>
												<input type="submit" name="queryBtn" value="Ask Us">
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>&nbsp;</td>
										</tr>
									</table>
							</td>
							</FORM>
						</tr>
					</table>
				</td>
				<td width="25%" valign="top">
					<table width="100%" bgcolor="lightgrey" cellspacing="0" cellpadding="0">
						<tr bgcolor="#707070">
							<td>&nbsp;&nbsp;<font color="#ffffff" face="Arial" size="3"><Strong>Solutions</Strong></font></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
