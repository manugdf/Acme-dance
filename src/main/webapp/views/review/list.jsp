
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="reviews" id="row" requestURI="${requestURI}"
               class="displaytag" keepStatus="true" pagesize="5" >

    <spring:message code="review.alumn" var="alumn"/>
    <display:column property="alumn.name" title="${alumn}"/>

    <spring:message code="review.score" var="score"/>
    <display:column property="score" title="${score}"/>

    <spring:message code="review.comment" var="comment"/>
    <display:column property="comment" title="${comment}"/>

</display:table>

<security:authorize access="hasRole('ALUMN')">
    <input	onclick="javascript: window.location.replace('review/alumn/create.do?teacherId=${teacher.id}');"
              value="<spring:message code="review.create" />" type="button" />
</security:authorize>