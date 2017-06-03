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


<form:form action="${requestUri}" modelAttribute="award">
	<form:hidden path="id"/>
	<form:hidden path="competition"/>

	<acme:textbox path="prize" code="award.prize"/>
    <acme:textbox path="place" code="award.place"/>
    <acme:textbox path="winnerName" code="award.winnerName"/>
    <acme:select items="${danceSchools}" itemLabel="name" code="award.candidators" path="danceSchool"/>
 
    <acme:submit code="award.save" name="save"/>

    <acme:cancel code="award.cancel" url="/mngr/event/list?danceSchoolId=${danceSchoolId }"/>


</form:form>