<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<security:authorize access="hasRole('ADMIN')">
	<form:form action="${requestURI}" modelAttribute="censoredWords">
		<form:hidden path="id" />
		<form:hidden path="version" />

		<spring:message code="cenwor.howtocreate" />
		<br />
		<br />


		<acme:textbox code="cenwor" path="words" />

		<br />
		<acme:submit name="save" code="cenwor.save" />
		<acme:cancel url="welcome/index.do" code="cenwor.cancel" />
		<br />

	</form:form>
</security:authorize>

