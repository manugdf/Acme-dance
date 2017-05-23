
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

<form:form action="${requestUri}" modelAttribute="danceClassAuxForm">

	<form:hidden path="danceClass"/>
	
	<acme:select items="${myTeachers}" itemLabel="userAccount.username" code="danceClass.teachers" path="teacher" />

	<acme:submit code="danceClass.save" name="save"/>
	
	<acme:cancel url="/danceClass/list.do?danceSchoolId=${danceSchoolId}" code="danceClass.cancel"/>
	
	</form:form>