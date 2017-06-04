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


<form:form action="${requestUri}" modelAttribute="competition">
	<form:hidden path="id"/>

	<fieldset>
    	<acme:textbox path="place.address" code="competition.location.address"/>
       	<acme:textbox path="place.province" code="competition.location.province"/>
       	<acme:textbox path="place.city" code="competition.location.city"/>
   </fieldset>
    <acme:textbox path="alumnPerSchool" code="competition.alumnPerSchool"/>
    <acme:textbox path="description" code="competition.description"/>
    <acme:textbox path="limitInscription" code="competition.limitInscription"/>
    <acme:textbox path="limitSchool" code="competition.limitSchool"/>
    <acme:textbox path="startDate" code="competition.startDate"/>


    <acme:submit code="competition.save" name="save"/>

    <acme:cancel code="competition.cancel" url="/competition/competitionPlanner/list.do"/>


</form:form>