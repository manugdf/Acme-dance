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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="banner">
	<form:hidden path="id" />
	<form:hidden path="manager" />
	<security:authorize access="hasRole('MANAGER')">
	<form:hidden path="state" />
	</security:authorize>

	<acme:textbox code="banner.url" path="url" />
	
	<security:authorize access="hasRole('ADMIN')">
	<form:select path="state">
			<form:option value="ACCEPTED">ACCEPTED</form:option>
			<form:option value="REJECTED">REJECTED</form:option>
		</form:select>
	</security:authorize>
	
	<acme:submit code="danceschool.save" name="save" />

	<acme:cancel url="welcome/index.do" code="danceschool.cancel" />

</form:form>