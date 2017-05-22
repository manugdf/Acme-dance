
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


<display:table name="danceTests" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="dancetest.testDate" var="date"/>
	<display:column property="testDate" title="${date}"/>
	
	<spring:message code="dancetest.danceLevel" var="dancelevel"/>
	<display:column property="danceLevel" title="${dancelevel}"/>
	
	<spring:message code="dancetest.limitInscription" var="limitInscription"/>
	<display:column property="limitInscription" title="${limitInscription}"/>
	
</display:table>