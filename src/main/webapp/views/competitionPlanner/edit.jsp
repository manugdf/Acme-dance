
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

<form:form modelAttribute="CompetitionPlannerForm" action="${requestUri }">

	<acme:textbox code="cp.name" path="name"/>
	<acme:textbox code="cp.surname" path="surname"/>
	<acme:textbox code="cp.email" path="email"/>
	<acme:textbox code="cp.phone" path="phone"/>
	<acme:textbox code="cp.username" path="username"/>
	<acme:textbox code="cp.password" path="password"/>
	<acme:textbox code="cp.repeatPassword" path="repeatPassword"/>
	<acme:textbox code="cp.companyName" path="companyName"/>
	<acme:textbox code="cp.picture" path="picture"/>
	
	<acme:submit name="save" code="cp.save"/>

</form:form>