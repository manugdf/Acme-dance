
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


<display:table name="schedules" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	
	<spring:message code="schedule.dayOfWeek" var="week"/>
	<display:column property="dayOfWeek" title="${week}"/>
	
	<spring:message code="schedule.startDate" var="date"/>
	<display:column property="startDate" title="${date}"/>
	
	<spring:message code="schedule.endTime" var="end"/>
	<display:column property="endTime" title="${end}"/>
	
	<spring:message code="schedule.classroom" var="classroom"/>
	<display:column property="classroom" title="${classroom}"/>
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<input	onclick="javascript: window.location.replace('mngr/schedule/edit.do?scheduleId=${row.id}');"
							value="<spring:message code="schedule.edit" />" type="button" />
		</display:column>
	</security:authorize>

</display:table>

<security:authorize access="hasRole('MANAGER')">
	<input	onclick="javascript: window.location.replace('mngr/schedule/create.do?classId=${row.danceClass.id}');"
					value="<spring:message code="schedule.create" />" type="button" />
</security:authorize>