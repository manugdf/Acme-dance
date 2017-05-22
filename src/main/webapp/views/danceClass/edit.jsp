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

<form:form action="${requestUri}" modelAttribute="danceClassForm">

    <acme:textbox readonly="true" code="danceclass.danceschool.name" path="danceSchool.name"/>

    <acme:textbox path="style" code="danceclass.style"/>
    <acme:textbox path="maxAlumns" code="danceclass.maxAlumns"/>
    <acme:textbox path="monthlyPrice" code="danceclass.monthly"/>
    <acme:textbox path="yearlyPrice" code="danceclass.yearly"/>
    <acme:textbox path="description" code="danceclass.description"/>

    <acme:submit code="danceClass.save" name="save"/>

    <acme:cancel code="danceClass.cancel" url="/danceClass/list.do?danceSchoolId=${danceschool.id}"/>


</form:form>