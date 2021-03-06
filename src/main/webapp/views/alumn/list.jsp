
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

<display:table name="alumns" id="row" requestURI="${requestURI}">

	<spring:message code="alumn.name" var="nameColumn"/>
	<display:column property="name" title="${nameColumn}"/>
	
	<spring:message code="alumn.surname" var="surnameColumn"/>
	<display:column property="surname" title="${surnameColumn}"/>
	
	<spring:message code="alumn.email" var="emailColumn"/>
	<display:column property="email" title="${emailColumn}"/>
	
	<spring:message code="alumn.phone" var="phoneColumn"/>
	<display:column property="phone" title="${phoneColumn}"/>

	<spring:message code="alumn.viewDanceCertificate" var="danceCertificate" />
	<display:column title="${danceCertificate}">

		<input
			onclick="javascript: window.location.replace('danceCertificate/teacher/list.do?alumnId=${row.id}');"
			value="<spring:message code="alumn.danceCertificate.view" />"
			type="button" />
	</display:column>

	<security:authorize access="hasRole('TEACHER')">
	
	<spring:message code="alumn.danceCertificate" var="danceCertificate"/>
		<display:column title="${danceCertificate}">
	
			<input	onclick="javascript: window.location.replace('danceCertificate/teacher/create.do?alumnId=${row.id}&danceTestId=${danceTestId}');"
					value="<spring:message code="alumn.danceCertificate.add" />" type="button" />
		</display:column>
		
	</security:authorize>


</display:table>