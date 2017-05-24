<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<security:authorize access="hasRole('MANAGER')">
<spring:message code="banner.fee" />
<jstl:out value="${fee.managerAmount}" /> Euros
</security:authorize>
<br />

<display:table name="banners" id="row" requestURI="${requestURI}"
	pagesize="10" class="displaytag" >

	<spring:message code="banner.url" var="url"/>
	<display:column property="url" title="${url}"/>

	<spring:message code="banner.state" var="state" />
	<display:column property="state" title="${state}"/>
	
	<security:authorize access="hasRole('ADMIN')">
	
		<display:column>
			<input
				onclick="javascript: window.location.replace('banner/administrator/reject.do?id=${row.id}');"
				value="<spring:message code="danceschool.reject" />"
				type="button" />
		</display:column>
		<display:column>
			<input
				onclick="javascript: window.location.replace('banner/administrator/accept.do?id=${row.id}');"
				value="<spring:message code="danceschool.accept" />"
				type="button" />
		</display:column>
		</security:authorize>
	
	
</display:table>
<security:authorize access="hasRole('MANAGER')">
			<input
				onclick="javascript: window.location.replace('banner/manager/create.do');"
				value="<spring:message code="banner.create" />"
				type="button" />
</security:authorize>
	