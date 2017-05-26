
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


<b><spring:message code="award.danceschool.name"/>: </b>${danceschool}

<display:table name="awards" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	
	<spring:message code="award.winnerName" var="win"/>
	<display:column property="winnerName" title="${win}"/>
	
	<spring:message code="award.prize" var="prize"/>
	<display:column property="prize" title="${prize}"/>
	
	<spring:message code="award.competition.date" var="award"/>
	<display:column property="competition.startDate" title="${award}"/>
	
	<spring:message code="award.competition.description" var="award"/>
	<display:column property="competition.description" title="${award}"/>

	<spring:message code="award.competition.place.address" var="award"/>
	<display:column property="competition.place.address" title="${award}"/>
	
	<spring:message code="award.competition.place.province" var="award"/>
	<display:column property="competition.place.province" title="${award}"/>
	
	<spring:message code="award.competition.place.city" var="award"/>
	<display:column property="competition.place.city" title="${award}"/>
	
	
		
</display:table>