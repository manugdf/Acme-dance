
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<b><spring:message code="teacher.danceschool.name"/>: </b>${danceschool}
<display:table name="teachers" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="teacher.picture" var="pictureColumn"/>
	<display:column title="${pictureColumn}">
	<img src="${row.picture}">
	</display:column>
	
	<spring:message code="teacher.avgscore" var="avgColumn"/>
	<display:column property="averageScore" title="${avgColumn}"/>
	
	<spring:message code="teacher.name" var="nameColumn"/>
	<display:column property="name" title="${nameColumn}"/>
	
	<spring:message code="teacher.surname" var="surnameColumn"/>
	<display:column property="surname" title="${surnameColumn}"/>
	
	<spring:message code="teacher.email" var="emailColumn"/>
	<display:column property="email" title="${emailColumn}"/>
	
	<spring:message code="teacher.phone" var="phoneColumn"/>
	<display:column property="phone" title="${phoneColumn}"/>
	
	<spring:message code="teacher.video" var="videoColumn"/>
	<display:column property="presentationVideo" title="${videoColumn}"/>
	
	
		
</display:table>