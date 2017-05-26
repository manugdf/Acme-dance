
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

<display:table name="competitions" id="row" requestURI="${requestURI}"
               class="displaytag" keepStatus="true" pagesize="5" >

    <spring:message code="competition.competitionPlanner" var="competitionPlanner"/>
    <display:column property="competitionPlanner.companyName" title="${competitionPlanner}"/>

    <spring:message code="competition.startDate" var="startDate"/>
    <display:column property="startDate" title="${startDate}"/>

    <spring:message code="competition.limitInscription" var="limitInscription"/>
    <display:column property="limitInscription" title="${limitInscription}"/>

    <spring:message code="competition.location.address" var="address"/>
    <display:column property="place.address" title="${address}"/>

    <spring:message code="competition.location.province" var="province"/>
    <display:column property="place.province" title="${province}"/>

    <spring:message code="competition.location.city" var="city"/>
    <display:column property="place.city" title="${city}"/>

    <spring:message code="competition.description" var="description"/>
    <display:column property="description" title="${description}"/>

    <spring:message code="competition.alumnPerSchool" var="alumnPerSchool"/>
    <display:column property="alumnPerSchool" title="${alumnPerSchool}"/>

    <spring:message code="competition.limitSchool" var="limitSchool"/>
    <display:column property="limitSchool" title="${limitSchool}"/>

    <spring:message code="competition.danceSchools" var="danceSchools"/>
    <display:column title="${danceSchools}">
        <input	onclick="javascript: window.location.replace('danceSchool/listByCompetition.do?competitionId=${row.id}');"
                  value="<spring:message code="competition.view" />" type="button" />
    </display:column>

    <spring:message code="competition.awards" var="awards"/>
    <display:column title="${awards}">
        <input	onclick="javascript: window.location.replace('award/listByCompetition.do?competitionId=${row.id}');"
                  value="<spring:message code="competition.view" />" type="button" />
    </display:column>

    <security:authorize access="hasRole('MANAGER')">
        <spring:message code="competition.signup" var="signup"/>
        <display:column title="${signup}">
            <jstl:choose>
                <jstl:when test="${actualDate<=row.limitInscription && fn:length(row.danceSchools) < row.limitSchool}">
                    <input	onclick="javascript: window.location.replace('competition/manager/signup.do?competitionId=${row.id}');"
                              value="<spring:message code="competition.signup" />" type="button" />
                </jstl:when>
                <jstl:otherwise>
                    <font color="red"><spring:message code="competition.cantSignup" /></font>
                </jstl:otherwise>
            </jstl:choose>
        </display:column>
    </security:authorize>


</display:table>

  <security:authorize access="hasRole('COMPETITIONPLANNER')">
	  <input	onclick="javascript: window.location.replace('competition/competitionPlanner/create.do');"
	                              value="<spring:message code="competition.create" />" type="button" />
  </security:authorize>