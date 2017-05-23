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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI}" modelAttribute="danceSchoolForm">
	<form:hidden path="danceSchoolId" />
	<form:hidden path="state" />

	<jstl:if test="${edit==false}">
	<fieldset>
	<legend><spring:message code="danceschool.account"/></legend>
	<acme:textbox code="danceschool.name" path="name"/>
	</fieldset>
	</jstl:if>
	
	<fieldset>
	<legend><spring:message code="danceschool.basics"/></legend>
	<acme:textbox code="danceschool.description" path="description"/>
	<acme:textbox code="danceschool.phone" path="phone"/>
	<acme:textbox code="danceschool.picture" path="picture"/>
	<acme:textbox code="danceschool.adress" path="adress"/>
	<acme:textbox code="danceschool.city" path="city"/>
	<acme:textbox code="danceschool.province" path="province"/>
	</fieldset>
	
	<acme:submit code="manager.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="manager.cancel"/>

</form:form>