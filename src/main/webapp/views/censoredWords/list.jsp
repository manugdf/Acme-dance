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


<security:authorize access="hasRole('ADMIN')">
	<display:table name="censoredWords" id="row" requestURI="${requestURI}"
		class="displaytag" keepStatus="true" pagesize="5">

		<spring:message code="cenwor.words" var="words" />
		<display:column property="words" title="${words}">
			<ul>
				<jstl:forEach items="${row.words}" var="word">
					<li>${word}</li>
				</jstl:forEach>
			</ul>
		</display:column>

	</display:table>
</security:authorize>