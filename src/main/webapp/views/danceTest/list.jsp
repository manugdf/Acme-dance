
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="danceTests" id="row" requestURI="${requestURI}"
	class="displaytag" keepStatus="true" pagesize="5">

	<spring:message code="dancetest.testDate" var="date" />
	<display:column property="testDate" title="${date}" />

	<spring:message code="dancetest.danceLevel" var="dancelevel" />
	<display:column property="danceLevel" title="${dancelevel}" />

	<spring:message code="dancetest.limitInscription"
		var="limitInscription" />
	<display:column property="limitInscription" title="${limitInscription}" />

	<security:authorize access="hasRole('TEACHER')">
		<spring:message code="teacher.selectAlum" var="selectAlum" />
		<display:column title="${selectAlum}">
			<jsp:useBean id="now" class="java.util.Date" />
			<jstl:if test="${row.testDate le now}">
				<input
					onclick="javascript: window.location.replace('alumn/teacher/list.do?danceTestId=${row.id}');"
					value="<spring:message code="teacher.select" />" type="button" />
			</jstl:if>
		</display:column>
	</security:authorize>

	<security:authorize access="hasRole('ALUMN')">
		<spring:message code="danceTest.joinin" var="joinin" />
		<display:column title="${joinin}">
			<jstl:choose>
				<jstl:when test="${!myDanceTests.contains(row)}">
					<input
						onclick="javascript: window.location.replace('danceTest/alumn/joinin.do?danceTestId=${row.id}');"
						value="<spring:message code="danceTest.joinin" />" type="button" />
				</jstl:when>
				<jstl:otherwise>
					<font color="red"><spring:message
							code="danceTest.testRepeat" /></font>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>

</display:table>