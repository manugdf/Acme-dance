
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

<script>
	function myFunction(id,t) {
	   
	    var r = confirm(t);
	    if (r == true) {
	    	document.location.href='danceClass/alumn/quit.do?classId='+id;
	    } else {
	        
	    }
	    
	}
	</script>

<b><spring:message code="danceclass.danceschool.name"/>: </b>${danceschool.name}

<display:table name="danceClasses" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	
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
	
	
	<jstl:if test="${myClasses==true}">
	<security:authorize access="hasRole('ALUMN')">
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
	
	<spring:message code="danceClass.quit" var="quit"/>
	<display:column title="${quit}">
	
	<spring:message code="danceClass.quit.sure" var="sure"/>
	<input	onclick="myFunction(${row.id},'${sure}')"
					value="<spring:message code="danceClass.quit.quit" />" type="button" />
	
	</display:column>
	
	</security:authorize>
</jstl:if>
	
	<security:authorize access="hasRole('MANAGER')">
	<spring:message code="danceclass.add" var="add"/>
	<display:column title="${add}">

	<input	onclick="javascript: window.location.replace('danceClass/manager/add.do?danceClassId=${row.id}&danceSchoolId=${danceSchoolId}');"
					value="<spring:message code="danceclass.add" />" type="button" />

	</display:column>

	<spring:message code="danceclass.remove" var="remove"/>
	<display:column title="${remove}">
	
	<input	onclick="javascript: window.location.replace('danceClass/manager/remove.do?classId=${row.id}');"
					value="<spring:message code="danceclass.remove" />" type="button" />
	
	</display:column>

	<spring:message code="danceclass.edit" var="edit"/>
	<display:column title="${edit}">
		<input	onclick="javascript: window.location.replace('danceClass/manager/edit.do?danceClassId=${row.id}');"
				  value="<spring:message code="danceclass.edit" />" type="button" />

	</display:column>
	</security:authorize>
		
</display:table>

<security:authorize access="hasRole('MANAGER')">
	<input	onclick="javascript: window.location.replace('danceClass/manager/create.do?id=${danceschool.id}');"
	value="<spring:message code="danceclass.create" />" type="button" />
</security:authorize>