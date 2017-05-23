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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestURI}" modelAttribute="partnerInvitation">
	<form:hidden path="id"/>
	<form:hidden path="invitationSender"/>
	<form:hidden path="invitationReceiver"/>
	<form:hidden path="state"/>
	
	<acme:textbox code="partnerInvitation.invitationReceiver" path="invitationReceiver.name" readonly="true"/>
	<acme:textbox code="partnerInvitation.comment" path="comment"/>
	<acme:textbox code="partnerInvitation.danceStyle" path="danceStyle"/>

	
	<acme:submit code="partnerInvitation.save" name="save"/>
	
	<acme:cancel url="danceSchool/alumn/list.do" code="partnerInvitation.cancel"/>

</form:form>