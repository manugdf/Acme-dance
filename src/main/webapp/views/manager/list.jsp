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

<display:table name="managers" id="row" requestURI="${requestURI}">

	<spring:message code="manager.name" var="nameColumn"/>
	<display:column property="name" title="${nameColumn}"/>
	
	<spring:message code="manager.surname" var="surnameColumn"/>
	<display:column property="surname" title="${surnameColumn}"/>
	
	<spring:message code="manager.email" var="emailColumn"/>
	<display:column property="email" title="${emailColumn}"/>
	
	<spring:message code="manager.phone" var="phoneColumn"/>
	<display:column property="phone" title="${phoneColumn}"/>

	<spring:message code="manager.fee" var="feeColumn"/>
	<display:column property="fee" title="${feeColumn}"/>

	<security:authorize access="hasRole('ADMIN')">
	
	<spring:message code="manager.editFee" var="editFee"/>
		<display:column title="${editFee}">
	
			<input	onclick="javascript: window.location.replace('manager/administrator/editFee.do?managerId=${row.id}');"
					value="<spring:message code="manager.fee.edit" />" type="button" />
		</display:column>
		
	</security:authorize>


</display:table>