
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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	function myFunction(id,t) {
	   
	    var r = confirm(t);
	    if (r == true) {
	    	document.location.href='mngr/event/delete.do?eventId='+id;
	    }	    
	}
</script>

<b><spring:message code="event.danceschool.name" />: </b>${danceschool}
<br>
<display:table name="events" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5">


	<spring:message code="event.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="event.description" var="desc" />
	<display:column property="description" title="${desc}" />

	<spring:message code="event.startDate" var="date" />
	<display:column property="startDate" title="${date}" format="{0,date,dd/MM/yyyy HH:mm}"/>

	<spring:message code="event.duration" var="duration" />
	<display:column property="duration" title="${duration}" />

	<spring:message code="event.maxAlumns" var="max" />
	<display:column property="maxAlumns" title="${max}" />

	<spring:message code="event.alumns" var="alumns" />
	<display:column title="${alumns}">
	${fn:length(row.alumns)}
	</display:column>

	<security:authorize access="hasRole('MANAGER')">
		<jstl:if test="${row.danceSchool.manager == logged }">
			<display:column>
				<input
								onclick="javascript: window.location.replace('mngr/event/edit.do?eventId=${row.id}');"
								value="<spring:message code="event.edit" />" type="button"/>
			</display:column>
			
			<spring:message code="event.confirm.delete" var="sure"/>
			<display:column>
				<input
					onclick="myFunction(${row.id},'${sure}')"
					value="<spring:message code="event.delete" />" type="button" />
			</display:column>
			
		</jstl:if>
	
	</security:authorize>

		<jstl:if test="${inSchool==true}">
		
			<jstl:if test="${row.maxAlumns - fn:length(row.alumns)>0}">
				<display:column>		
					<input
							onclick="javascript: window.location.replace('event/alumn/assist.do?idEvent=${row.id}&idSchool=${idSchool}');"
							value="<spring:message code="event.assist" />" type="button"/>
				</display:column>
			</jstl:if>
		</jstl:if>

</display:table>

	<security:authorize access="hasRole('MANAGER')">

				<input
								onclick="javascript: window.location.replace('mngr/event/create.do?danceSchoolId=${danceSchool.id}');"
								value="<spring:message code="event.create" />" type="button"/>

	
	</security:authorize>
