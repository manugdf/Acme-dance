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


<form:form action="${requestURI}" modelAttribute="danceTest">
	<form:hidden path="id" />

	<acme:textbox path="testDate" code="dancetest.testDate" />
	<acme:textbox path="danceLevel" code="dancetest.danceLevel" />
	<acme:textbox path="limitInscription" code="dancetest.limitInscription" />

	<acme:submit code="danceTest.save" name="save" />

	<acme:cancel code="danceTest.cancel" url="/danceClass/teacher/list.do" />


</form:form>