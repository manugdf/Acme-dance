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

<form:form action="${requestURI}" modelAttribute="messageForm">


	<jstl:if test="${reply == false}">
		<acme:select items="${users}" itemLabel="userAccount.username"
			code="message.receiver" path="receiver" />
	</jstl:if>

	<jstl:if test="${reply == true}">
		<form:hidden path="receiver" />
		<form:label path="receiver">
			<spring:message code="message.receiver" />
		</form:label>
		<form:input disabled="true" path="receiver.userAccount.username"
			readonly="true" />
		<br>
	</jstl:if>

	<acme:textbox code="message.subject" path="subject" />
	<acme:textarea code="message.body" path="body" />

	<br />
	<acme:submit name="send" code="message.send" />
	<acme:cancel url="welcome/index.do" code="message.cancel" />
	<br />

</form:form>

