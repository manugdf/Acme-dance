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
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<security:authorize access="hasRole('MANAGER')">
<form:form action="${requestUri}" modelAttribute="schedule">

	<form:hidden path="id"/>		
	<form:hidden path="danceClass"/>
	
	<form:label path="dayOfWeek">
	<spring:message code="schedule.dayOfWeek"/>
	</form:label>
	<form:select path="dayOfWeek">
		<form:option value="MONDAY"><spring:message code="schedule.monday"/></form:option>
		<form:option value="TUESDAY"><spring:message code="schedule.tuesday"/></form:option>
		<form:option value="WEDNESDAY"><spring:message code="schedule.wednesday"/></form:option>
		<form:option value="THURSDAY"><spring:message code="schedule.thursday"/></form:option>
		<form:option value="FRIDAY"><spring:message code="schedule.friday"/></form:option>
		<form:option value="SATURDAY"><spring:message code="schedule.saturday"/></form:option>
		<form:option value="SUNDAY"><spring:message code="schedule.sunday"/></form:option>	
	</form:select>
	<form:errors path="dayOfWeek" cssClass="error" />
	
	
    <acme:textbox path="startDate" code="schedule.startDate"/>
    <acme:textbox path="endTime" code="schedule.endTime"/>
    <acme:textbox path="classroom" code="schedule.classroom"/>
 
    <acme:submit code="schedule.save" name="save"/>
    
    <acme:cancel code="schedule.cancel" url="/schedule/list.do?classId=${danceClassId }"/>
    


</form:form>
</security:authorize>