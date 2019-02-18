<%@ page import="java.util.Set" %>

<%@ taglib uri="/tags/struts-logic" prefix="logic"%>

<logic:notPresent name="includedJsFiles">
    <jsp:useBean id="includedJsFiles" class="java.util.HashSet" scope="request"/>
</logic:notPresent>

<%
    String jsFile = request.getParameter("file");
    Set includedJSFiles = (Set)request.getAttribute("includedJsFiles");
    if (includedJSFiles.contains(jsFile)) {
        return;
    }

    includedJSFiles.add(jsFile);
%>

<logic:equal parameter="cache" value="true">
    <script>
        <jsp:include page="<%= jsFile %>"/>
    </script>
</logic:equal>

<logic:notEqual parameter="cache" value="true">
    <script src="<%= request.getContextPath() + jsFile %>"></script>
</logic:notEqual>