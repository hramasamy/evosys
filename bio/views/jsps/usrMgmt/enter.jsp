    <font size="5" color="blue"> Please provide following information:</font>
    <form action="validate.jsp" method="post">
      <table>
        <tr>
          <td>User Name:</td>
          <td><input type="text" name="userName"
            value="${fn:escapeXml(param.userName)}">
          </td>
          <td><font color="red">${fn:escapeXml(userNameError)}</font></td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><input type="text" name="password"
            value="${fn:escapeXml(param.password)}">
          </td>
          <td>${fn:escapeXml(passwordError)}</td>
        </tr>
        <tr>
          <td>First Name:</td>
          <td><input type="text" name="firstName"
            value="${fn:escapeXml(param.firstName)}">
          </td>
          <td><font color="red">${fn:escapeXml(firstNameError)}</font> </td>
        </tr>
        <tr>
          <td>Last Name:</td>
          <td><input type="text" name="lastName"
            value="${fn:escapeXml(param.lastName)}">
          </td>
          <td><font color="red">${fn:escapeXml(lastNameError)}</font></td>
        </tr>
        <tr>
          <td>Department:</td>
          <td><input type="text" name="dept"
            value="${fn:escapeXml(param.dept)}">
          </td>
          <td><font color="red">${fn:escapeXml(deptError)}</font></td>
        </tr>
        <tr>
          <td>Employment Date:</td>
          <td><input type="text" name="empDate"
            value="${fn:escapeXml(param.empDate)}">
          </td>
          <td><font color="red">${fn:escapeXml(empDateError)}</font></td>
          <td>(Use format yyyy-mm-dd)</td>
        </tr>
        <tr>
          <td>Email Address:</td>
          <td><input type="text" name="emailAddr"
            value="${fn:escapeXml(param.emailAddr)}">
          </td>
          <td><font color="red">${fn:escapeXml(emailAddrError)}</font></td>
          <td>(Use format name@company.com)</td>
        </tr>
        <tr>
          <td colspan=2><input type="submit" value="Submit"></td>
        </tr>
      </table>
    </form>
