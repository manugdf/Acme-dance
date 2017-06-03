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


<form:form action="${requestUri}" modelAttribute="event">
	<form:hidden path="id"/>
	<form:hidden path="danceSchool"/>

    <acme:textbox path="title" code="event.title"/>
    <acme:textbox path="description" code="event.description"/>
    <acme:textbox path="startDate" code="event.startDate"/>
    <acme:textbox path="maxAlumns" code="event.maxAlumns"/>
    <acme:textbox path="duration" code="event.duration"/>


    <acme:submit code="event.save" name="save"/>

    <acme:cancel code="event.cancel" url="/mngr/event/list.do?danceSchoolId=${danceSchoolId }"/>


</form:form>