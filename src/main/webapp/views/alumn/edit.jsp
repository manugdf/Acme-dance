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

<form:form action="${requestURI}" modelAttribute="alumnForm">
	<form:hidden path="alumnId"/>

	<jstl:if test="${edit==false}">
		<fieldset>
			<acme:textbox code="alumn.username" path="username" />
			<acme:password code="alumn.password" path="password" />
			<acme:password code="alumn.confirmPassword" path="confirmPassword" />
		</fieldset>
	</jstl:if>
	
	<jstl:if test="${edit==true}">
	<fieldset>
	<legend><spring:message code="alumn.account"/></legend>
	<acme:textbox readonly="true" code="alumn.username" path="username"/>
	<acme:password code="alumn.current.password" path="password"/><b><spring:message code="alumn.passwordinfo"/></b>
	<acme:password code="alumn.newpassword" path="newpassword"/>
	<acme:password code="alumn.confirmPassword" path="repeatnewpassword"/>
	<b><spring:message code="alumn.passwordinfo2"/></b>	
	
	</fieldset>
	</jstl:if>

	<fieldset>
		<acme:textbox code="alumn.name" path="name" />
		<acme:textbox code="alumn.surname" path="surname" />
		<acme:textbox code="alumn.email" path="email" />
		<acme:textbox code="alumn.phone" path="phone" />
	</fieldset>

	<fieldset>
		<legend>
			<spring:message code="alumn.creditcard" />
		</legend>
		<acme:textbox code="alumn.holderName" path="holderName" />

		<form:label path="brandName">
			<spring:message code="alumn.brandName" />
		</form:label>
		<form:select path="brandName">
			<form:option value="VISA">VISA</form:option>
			<form:option value="MASTERCARD">MASTERCARD</form:option>
			<form:option value="DISCOVER">DISCOVER</form:option>
			<form:option value="DINNERS">DINNERS</form:option>
			<form:option value="AMEX">AMEX</form:option>


		</form:select>
		<form:errors path="brandName" cssClass="error" />
		<br>


		<acme:textbox code="alumn.number" path="number" />
		<acme:textbox code="alumn.expirationMonth" path="expirationMonth" />
		<acme:textbox code="alumn.expirationYear" path="expirationYear" />
		<acme:textbox code="alumn.cvvCode" path="cvvCode" />
	</fieldset>


	<jstl:if test="${edit==false}">

		<form:label path="acceptTerms">
			<b><spring:message code="alumn.acceptTerms" /></b>
		</form:label>
		<form:select path="acceptTerms">
			<form:option value="true">Yes</form:option>
			<form:option value="false">No</form:option>


		</form:select>
	</jstl:if>

	<acme:submit code="alumn.save" name="save" />

	<acme:cancel url="welcome/index.do" code="alumn.cancel" />

</form:form>