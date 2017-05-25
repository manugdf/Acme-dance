
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
	
	
	<spring:message code="partnerInvitation.state" var="state"/>
	<display:column property="state" title="${state}"/>
	
	<spring:message code="partnerInvitation.comment" var="comment"/>
	<display:column property="comment" title="${comment}"/>
	
	<spring:message code="partnerInvitation.danceStyle" var="danceStyle"/>
	<display:column property="danceStyle" title="${danceStyle}"/>
	
	<spring:message code="partnerInvitation.invitationReceiver" var="invitationReceiver"/>
	<display:column property="invitationReceiver.name + invitationReceiver.surname" title="${invitationReceiver}"/>
	
	<spring:message code="partnerInvitation.invitationSender" var="sender"/>
	<display:column property="invitationSender.name" title="${sender}"/>
	
	<jstl:if test="${received==true }">
	<spring:message code="partnerInvitation.acceptordeny" var="ac"/>
	<display:column title="${ac}">
	<jstl:if test="${row.state=='PENDING' }">
	<input	onclick="javascript: window.location.replace('partnerInvitation/alumn/accept.do?invitationId=${row.id}');"
					value="<spring:message code="partnerInvitation.accept" />" type="button" />
	<input	onclick="javascript: window.location.replace('partnerInvitation/alumn/reject.do?invitationId=${row.id}');"
					value="<spring:message code="partnerInvitation.deny" />" type="button" />
	</jstl:if>
	</display:column>
		</jstl:if>
</display:table>