
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

<display:table name="danceClasses" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="danceclass.school" var="sch"/>
	<display:column title="${sch}">
	${row.danceSchool.name}
	</display:column>
	
	<spring:message code="danceclass.description" var="desc"/>
	<display:column property="description" title="${desc}"/>
	
	<spring:message code="danceclass.style" var="style"/>
	<display:column property="style" title="${style}"/>
	
	<spring:message code="danceclass.maxAlumns" var="max"/>
	<display:column property="maxAlumns" title="${max}"/>
	
	<spring:message code="danceclass.monthly" var="month"/>
	<display:column property="monthlyPrice" title="${month}"/>
	
	<spring:message code="danceclass.yearly" var="year"/>
	<display:column property="yearlyPrice" title="${year}"/>
	
	<spring:message code="danceclass.schedule" var="schedule"/>
	<display:column title="${schedule}">
	
	<input	onclick="javascript: window.location.replace('schedule/list.do?classId=${row.id}');"
					value="<spring:message code="danceclass.schedule.view" />" type="button" />
	
	</display:column>
	
	<spring:message code="danceClass.teachers" var="teach"/>
	<display:column title="${teach}">
	
	<input	onclick="javascript: window.location.replace('teacher/listByClass.do?classId=${row.id}');"
					value="<spring:message code="danceclass.schedule.view" />" type="button" />
	
	</display:column>

<spring:message code="danceClass.danceTests" var="tests"/>
	<display:column title="${tests}">
	
	<input	onclick="javascript: window.location.replace('danceTest/alumn/list.do?classId=${row.id}');"
					value="<spring:message code="danceclass.schedule.view" />" type="button" />
	
	</display:column>
	
	<spring:message code="danceClass.materials" var="mat"/>
	<display:column title="${mat}">
	
	<input	onclick="javascript: window.location.replace('material/alumn/list.do?classId=${row.id}');"
					value="<spring:message code="danceclass.schedule.view" />" type="button" />
	
	</display:column>
	
		
</display:table>
