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

<spring:message code="message.actor.received" />
<display:table name="received" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5"
	style="background-color:green;">

	<spring:message code="message.sender" var="sender" />
	<display:column property="sender" title="${sender}" />

	<spring:message code="message.receiver" var="receiver" />
	<display:column property="receiver" title="${receiver}" />

	<spring:message code="message.moment" var="moment" />
	<display:column property="moment" title="${moment}" />

	<spring:message code="message.subject" var="subject" />
	<display:column property="subject" title="${subject}" />

	<spring:message code="message.body" var="body" />
	<display:column property="body" title="${body}" />

	<display:column>
		<input
			onclick="javascript: window.location.replace('message/actor/reply.do?id=${row.id}');"
			value="<spring:message code="message.reply" />" type="button" />
	</display:column>

	<display:column>
		<input
			onclick="javascript: window.location.replace('message/actor/delete.do?id=${row.id}');"
			value="<spring:message code="message.delete" />" type="button" />
	</display:column>

</display:table>

<spring:message code="message.actor.sent" />
<display:table name="sent" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5"
	style="background-color:red;">

	<spring:message code="message.sender" var="sender" />
	<display:column property="sender" title="${sender}" />

	<spring:message code="message.receiver" var="receiver" />
	<display:column property="receiver" title="${receiver}" />

	<spring:message code="message.moment" var="moment" />
	<display:column property="moment" title="${moment}" />

	<spring:message code="message.subject" var="subject" />
	<display:column property="subject" title="${subject}" />

	<spring:message code="message.body" var="body" />
	<display:column property="body" title="${body}" />

	<display:column>
		<input
			onclick="javascript: window.location.replace('message/actor/delete.do?id=${row.id}');"
			value="<spring:message code="message.delete" />" type="button" />
	</display:column>

</display:table>

<div>
	<display:column>
		<input
			onclick="javascript: window.location.replace('message/actor/create.do');"
			value="<spring:message code="message.create" />" type="button" />
	</display:column>
</div>
