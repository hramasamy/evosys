<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@ page language="java" %>
<%@ page contentType="text/html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>

  <%-- The map name: <c:out value="${param.mapName}"></c:out> --%>
  
  <jsp:useBean id="mapInfo" class="com.sbs.zerobugs.utils.beans.MapInfoBean"/>
  <jsp:setProperty name="mapInfo" property="XStoreLocation" value="${initParam.XStoreLocation}"/>
  <jsp:setProperty name="mapInfo" property="mapName" value="${param.mapName}"/>
  
  <x:parse var="doc" xml="${mapInfo.reader}" scope="request" />
   
  <c:import url="${initParam.mapDocStylesheet}" var="stylesheet"></c:import>
    
  <x:transform doc="${doc}" xslt="${stylesheet}"></x:transform>
  
