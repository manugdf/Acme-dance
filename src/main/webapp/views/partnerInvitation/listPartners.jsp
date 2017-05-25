
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



<display:table name="partnerInvitations" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5" >
	
	<spring:message code="partnerInvitation.danceStyle" var="danceStyle"/>
	<display:column property="danceStyle" title="${danceStyle}"/>
	
	<spring:message code="partnerInvitation.invitationReceiver" var="invitationReceiver"/>
	<display:column property="invitationReceiver.name" title="${invitationReceiver}"/>
	
	<spring:message code="alumn.surname" var="sender"/>
	<display:column property="invitationSender.surname" title="${sender}"/>
	
</display:table>