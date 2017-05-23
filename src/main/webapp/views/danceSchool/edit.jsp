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

<form:form action="${requestURI}" modelAttribute="danceSchool">
	<form:hidden path="id" />
	<form:hidden path="manager" />
	<form:hidden path="danceClasses" />
	<form:hidden path="events" />
	<form:hidden path="competitions" />
	<form:hidden path="awards" />

	<acme:textbox code="danceSchool.name" path="name" />
	<acme:textbox code="danceSchool.description" path="description" />
	<acme:textbox code="danceSchool.picture" path="picture" />
	<acme:textbox code="danceSchool.moment" path="moment" /> (yyyy/mm/dd)
	<acme:textbox code="danceSchool.seatsOffered" path="seatsOffered" />

	<acme:submit code="danceSchool.save" name="save" />

	<acme:cancel url="welcome/index.do" code="danceSchool.cancel" />

</form:form>