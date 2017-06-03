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

<security:authorize access="hasRole('MANAGER')">
<form:form action="${requestUri}" modelAttribute="schedule">

	<form:hidden path="id"/>		
	<form:hidden path="danceClass"/>
	
    <acme:textbox path="dayOfWeek" code="schedule.dayOfWeek"/>
    <acme:textbox path="startDate" code="schedule.startDate"/>
    <acme:textbox path="endTime" code="schedule.endTime"/>
    <acme:textbox path="classroom" code="schedule.classroom"/>
 
    <acme:submit code="schedule.save" name="save"/>


</form:form>
</security:authorize>