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


<form:form action="${requestUri}" modelAttribute="danceCertificate">
	<form:hidden path="id"/>

    <acme:textbox path="certificateDate" code="danceCertificate.certificateDate"/>
    <acme:textbox path="danceLevel" code="danceCertificate.danceLevel"/>

    <acme:submit code="danceCertificate.save" name="save"/>

    <acme:cancel code="danceCertificate.cancel" url="/alumn/teacher/list.do?danceTestId=${danceTestId}"/>

</form:form>