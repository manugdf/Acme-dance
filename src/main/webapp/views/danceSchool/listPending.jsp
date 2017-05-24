
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


<display:table name="danceSchools" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="danceschool.name" var="nameColumn"/>
	<display:column property="name" title="${nameColumn}"/>
	
	<spring:message code="danceschool.description" var="desColumn"/>
	<display:column property="description" title="${desColumn}"/>
	
	<spring:message code="danceschool.location.province" var="provColumn"/>
	<display:column property="location.province" title="${provColumn}"/>
	
	<spring:message code="danceschool.location.city" var="cityColumn"/>
	<display:column property="location.city" title="${cityColumn}"/>
	
	<spring:message code="danceschool.location.address" var="addrColumn"/>
	<display:column property="location.address" title="${addrColumn}"/>
	
	<spring:message code="danceschool.phone" var="phoneColumn"/>
	<display:column property="phone" title="${phoneColumn}"/>
	
	<spring:message code="danceschool.picture" var="pictureColumn"/>
	<display:column  title="${pictureColumn}">
	<img src="${row.picture}" />
	</display:column>
	
		<display:column>
			<input
				onclick="javascript: window.location.replace('danceSchool/administrator/reject.do?id=${row.id}');"
				value="<spring:message code="danceschool.reject" />"
				type="button" />
		</display:column>
		<display:column>
			<input
				onclick="javascript: window.location.replace('danceSchool/administrator/accept.do?id=${row.id}');"
				value="<spring:message code="danceschool.accept" />"
				type="button" />
		</display:column>

</display:table>