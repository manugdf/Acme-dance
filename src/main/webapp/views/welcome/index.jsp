<%--
 * index.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${!empty url}">
<img src="${url}" height="210" width="728"></jstl:if>

<p><spring:message code="welcome.greeting.prefix" /> 
<jstl:if test="${name=='welcome.greeting.stranger'}">
<spring:message code="${name }" /><spring:message code="welcome.greeting.suffix" />
</jstl:if>


<jstl:if test="${name!='welcome.greeting.stranger'}">
${name}<spring:message code="welcome.greeting.suffix" /></p>
</jstl:if>


<p><spring:message code="welcome.greeting.current.time" /> ${moment}</p> 
