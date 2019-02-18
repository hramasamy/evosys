<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

	<table id="sbsDefTable" width="100%" height="616" cellspacing="0">
			<tr>
				<td width="20%" height="612"></td>
				<td id="filledTable" width="60%" height="612" align="center" valign="middle">
					<form id="sbsForm" name="userRegForm" action="<c:url value="/register.do" />" onsubmit="return validateUserRegForm(this)" method="post"> 
						<table id="sbsDefTable" width="100%">
							<tr><td width="335">&nbsp;</td><td width="509"><font color="red"> <c:out value="${param.errorMsg}"/></font></td>
							<tr><td width="335">&nbsp;</td><td width="509"></td>
							<tr><td id="sbsSubSection" align="left" width="335">Basic Information</td><td width="509">&nbsp;</td>
							<tr><td>&nbsp;</td><td>&nbsp;</td>
							<TR>
								<TD id="sbsLabel" align="right" width="335">*Email Address&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="Text1" type="text" size="50" name="email" width="100%" onchange="if(isNotEmpty(this)) {isValidEmail(this)}" value="<c:out value="${param.email}"/>"></TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right" width="335">*Passcode&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="Text2" type="password" size="50" name="passwd" width="100%" onchange="isNotEmpty(this)"></TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right" width="335">*Retype passcode&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="Password2" type="password" size="50" name="repwd" width="100%" onchange="isNotEmpty(this)"></TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right" width="335">*First Name&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="user_text" type="text" size="50" name="fName" width="100%" onchange="isNotEmpty(this)" value="<c:out value="${param.fName}"/>"></TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right" width="335">*Last Name&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="Text3" type="text" size="50" name="lName" width="100%" onchange="isNotEmpty(this)" value="<c:out value="${param.lName}"/>"></TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right" width="335">Nick Name&nbsp;</TD>
								<TD align="left" width="509"><INPUT id="Text4" type="text" size="50" name="nName" width="100%" value="<c:out value="${param.nName}"/>"></TD>
							</TR>
							<tr><td>&nbsp;</td><td>&nbsp;</td>
							<tr><td id="sbsSubSection" align="left" width="335">Misc. Information</td><td>&nbsp;</td>
							<tr><td>&nbsp;</td><td>&nbsp;</td>
							<TR>
								<TD id="sbsLabel" align="right">*Industry&nbsp;</TD>
								<TD align="left"><SELECT id="userDropdown" name="industry" style="width:322">
										<OPTION selected><c:out value="${param.industry}"/></OPTION>
									</SELECT>
								</TD>
							</TR>
							<TR>
								<TD id="sbsLabel" align="right">How did you hear about us?&nbsp;</TD>
								<TD align="left"><INPUT type="text" size="50" name="howFound" value="<c:out value="${param.howFound}"/>"></TD>
							</TR>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td align="right"><INPUT type="checkbox" name="notifyProgress" checked="<c:out value="${param.notifyProgress}"/>">&nbsp;</td>
								<td id="sbsLabel">Notify me the progress on the site.</td>
							</tr>

							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
								<td><input id="sbsButton" type="submit" name="okBtn" value="OK"/>&nbsp;<input id="sbsButton" type=button name="cancelBtn" value="Cancel" onclick="return onBtnClick(this)"/>&nbsp;<input id="sbsButton" type="reset" name="resetBtn" value="Reset"/></td>
							</tr>
						</table>
					</form>
				</td>
				<td width="20%" height="612"></td>
			</tr>
		</table>      


