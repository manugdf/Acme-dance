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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestUri}" modelAttribute="teacherForm">
	<form:hidden path="danceClass"/>
	
	<jstl:if test="${edit==false}">
	<fieldset>
	<acme:textbox code="teacher.username" path="username"/>
	<acme:password code="teacher.password" path="password"/>
	<acme:password code="teacher.confirmPassword" path="confirmPassword"/>
	</fieldset>
	</jstl:if>
	
	<fieldset>
	<acme:textbox code="teacher.name" path="name"/>
	<acme:textbox code="teacher.surname" path="surname"/>
	<acme:textbox code="teacher.email" path="email"/>
	<acme:textbox code="teacher.phone" path="phone"/>
	<acme:textbox code="teacher.picture" path="picture"/>
	<acme:textbox code="teacher.video" path="presentationVideo"/>
	</fieldset>
	
	
	<jstl:if test="${edit==false}">
	
	<form:label path="acceptTerms">
		<b><spring:message code="teacher.acceptTerms" /></b>
	</form:label>	
	<form:select path="acceptTerms">
		<form:option value="true">Yes</form:option>
		<form:option value="false">No</form:option>
		
		
	</form:select>
	</jstl:if>
	
	<acme:submit code="teacher.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="teacher.cancel"/>

</form:form>