
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

<form:form modelAttribute="competitionPlannerForm" action="${requestUri }">
	<fieldset>
		<acme:textbox code="cp.username" path="username"/>
		<acme:password code="cp.password" path="password"/>
		<acme:password code="cp.repeatPassword" path="repeatPassword"/>
	</fieldset>
	
	<fieldset>
		<acme:textbox code="cp.name" path="name"/>
		<acme:textbox code="cp.surname" path="surname"/>
		<acme:textbox code="cp.email" path="email"/>
		<acme:textbox code="cp.phone" path="phone"/>	
		<acme:textbox code="cp.companyName" path="companyName"/>
		<acme:textbox code="cp.picture" path="picture"/>
	</fieldset>	
	
	<form:label path="acceptTerms">
			<b><spring:message code="alumn.acceptTerms" /></b>
	</form:label>
	<form:select path="acceptTerms">
		<form:option value="true">Yes</form:option>
		<form:option value="false">No</form:option>
	</form:select>
	<br>	
	
	<acme:submit name="save" code="cp.save"/>
	
	<acme:cancel url="welcome/index.do" code="cp.cancel" />

</form:form>