
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


<b><spring:message code="partnerRequest.danceschool.name"/>: </b>${schoolName}

<display:table name="partnerRequests" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	
<jstl:if test="${viewing == true }">
<spring:message code="partnerRequest.alumn" var="alumn"/>
	<display:column title="${alumn}">
	${row.alumn.name } ${row.alumn.surname } 
	</display:column>
</jstl:if>
	
	<spring:message code="partnerRequest.description" var="desc"/>
	<display:column property="description" title="${desc}"/>
	
	<spring:message code="partnerRequest.danceStyle" var="dance"/>
	<display:column property="danceStyle" title="${dance}"/>
	
	<jstl:if test="${viewing == false}">
	<spring:message code="partnerRequest.delete" var="delete"/>
	<display:column title="${delete}">
	<input	onclick="javascript: window.location.replace('partnerRequest/alumn/delete.do?partnerRequestId=${row.id}');"
					value="<spring:message code="partnerRequest.delete" />" type="button" />
	</display:column>
	</jstl:if>
	
	<jstl:if test="${viewing == true}">
	<spring:message code="partnerRequest.invitation" var="inv"/>
	<display:column title="${inv}">
	<input	onclick="javascript: window.location.replace('partnerInvitation/alumn/create.do?alumnId=${row.alumn.id}');"
					value="<spring:message code="partnerRequest.invitation" />" type="button" />
	</display:column>
	</jstl:if>
		
</display:table>

<jstl:if test="${viewing == false }">
<input	onclick="javascript: window.location.replace('partnerRequest/alumn/create.do');"
					value="<spring:message code="partnerRequest.create" />" type="button" />
</jstl:if>