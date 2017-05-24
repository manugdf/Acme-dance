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

<form:form action="${requestURI}" modelAttribute="material">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="teacher" />
	<form:hidden path="danceClass" />


	<acme:textbox code="material.title" path="title" />
	<acme:textbox code="material.link" path="link" />
	<acme:textarea code="material.description" path="description" />

	<br />
	<acme:submit name="save" code="material.save" />
	<acme:cancel url="danceClass/teacher/list.do" code="material.cancel" />
	<br />

</form:form>

