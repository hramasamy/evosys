<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<br></br>
<table cellspacing="0" cellpadding="0" border="0" width="770" bgcolor="#6D8EB7" align="center">
<tr>
	<td colspan='3'><img src="/images/shim.gif" height="8" width="1" border="0" alt=""></td>
</tr>
<tr>
	<td><img src="/images/shim.gif" height="1" width="8" border="0" alt=""></td>
	<td>
		<table cellspacing="0" cellpadding="0" border="0">
		<tr>
			<td><img src="/images/shim.gif" height="1" width="754" border="0"></td>
		</tr>
		<tr>
			<td align="right" valign="top"><a href="http://www.biocis.net" target="_blank">
				<img src="<c:url value="/views/htmls/images/newLogo.gif"/>" width="80" height="20"/></a>
			</td>
		</tr>
		<tr>
			<td>
				<table cellspacing='0' cellpadding='0' border='0'>
				</table>
			</td>
		</tr>
		<tr>
			<td bgcolor="#FFFFFF">
				<table cellspacing='0' cellpadding='0 border='0'>
				<tr>
					<td colspan='3'><img src="/images/shim.gif" height="22" width="1" border="0"></td>
				</tr>
				<tr>
					<td><img src="/images/shim.gif" height="1" width="11" border="0"></td>
					<td valign="top">
	<font id="sbsLabel">biocis :: Login</font>
	<hr></hr>
	<p>
	   To login to the system, enter your username (which is your email address) and your
	   password. If you don't have a username or password, please click on new user registration link.
	 </p>
	 <form name="loginForm" ID="sbsForm" action="<c:url value="/authenticate.do" />" method="post">
		 <input type="hidden" name="origURL" value="${fn:escapeXml(param.origURL)}">
	 	<table width='100%' border='0' cellspacing='0' cellpadding='1' bgcolor='#D2D2D2'><tr><td><table width='100%' border='0' cellspacing='0' cellpadding='2'><tr><td bgcolor='#F6F6F6'>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td><font color="red"><c:out value="${param.errorMsg}" /></font></td>
			</tr>
			<tr>
				<td align="left" valign="top" id="sbsSubLabel">
					Username:
				</td>
				<td align="left" valign="top">
					<input type="text" name="user_name" size="50" value="${fn:escapeXml(cookie.user_name.value)}">
				</td>
			</tr>
			<tr>
				<td align="left" valign="top" id="sbsSubLabel">
					Password:
				</td>
				<td align="left" valign="top">
					<input type="password" name="password" size="50" value="${fn:escapeXml(cookie.password.value)}">
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" name="submitbutton" value="Login &gt;&gt;"></td>
			</tr>
		</table>		
		</td></tr>
		
		</table></td> </tr>

		<tr><td bgcolor="white" align="right"><INPUT type="checkbox" name="remember" ${!empty cookie.user_name ? 'checked' : ''}>&nbsp;<font id="sbsSubLabel">Remember me</font></td></tr>
		
		</table> 	 	
	 </form>
<script type="text/javascript" language="JavaScript">
  <!--
  var focusControl = document.forms["loginForm"].elements["user_name"];

  if (focusControl.type != "hidden") {
     focusControl.focus();
  }
  // -->
</script>
					
					<br /><br /><br />
					</td>
					<td><img src="/images/shim.gif" height="1" width="11" border="0"></td>
				</tr>

				</table>
			
			</td>
		<tr>

		</table>
	</td>
	<td><img src="/images/shim.gif" height="1" width="8" border="0" alt=""></td>
</tr>
<tr>
	<td colspan='3'><img src="/images/shim.gif" height="8" width="1" border="0" alt=""></td>
</tr>
</table>
  <table align="center" width="770">
  									<tr>
										<td id="filledCell" width="5%" height="21" align="right"><font size="1" face="Arial">
										<a id="sbsLink" href="/BioScope/views/jsps/userReg.jsp">New 
													User Registration</a></font></td>
									</tr>

  </table>
  