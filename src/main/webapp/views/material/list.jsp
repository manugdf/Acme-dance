
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


<display:table name="materials" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5">

	<security:authorize access="hasRole('TEACHER')">
		<display:column>
			<input
				onclick="javascript: window.location.replace('material/teacher/edit.do?id=${row.id}');"
				value="<spring:message code="material.edit" />" type="button" />
		</display:column>
	</security:authorize>


	<spring:message code="material.title" var="title" />
	<display:column property="title" title="${title}" />

	<spring:message code="material.description" var="desc" />
	<display:column property="description" title="${desc}" />

	<spring:message code="material.link" var="link" />
	<display:column property="link" title="${link}" />

	<security:authorize access="hasRole('TEACHER')">
		<display:column>
			<input
				onclick="javascript:
			confirm('<spring:message code="material.confirm.delete" />');
			window.location.replace('material/teacher/delete.do?id=${row.id}');"
				value="<spring:message code="material.delete" />" type="button" />
		</display:column>
	</security:authorize>
</display:table>

<security:authorize access="hasRole('TEACHER')">
	<div>
		<input
			onclick="javascript: window.location.replace('material/teacher/create.do?danceClassId=${danceClassId}');"
			value="<spring:message code="material.create" />" type="button" /> <input
			onclick="javascript: window.location.replace('danceClass/teacher/list.do');"
			value="<spring:message code="material.back" />" type="button" />
	</div>
</security:authorize>