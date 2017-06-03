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

	
	<fieldset>
	<legend><spring:message code="danceschool.basics"/></legend>
	<jstl:if test="${edit==false}">
	<acme:textbox code="danceschool.name" path="name"/>
	</jstl:if>
	<jstl:if test="${edit==true}">
	<acme:textbox code="danceschool.name" path="name"  readonly="true"/>
	</jstl:if>
	<acme:textarea code="danceschool.description" path="description"/>
	</fieldset>
	
	<fieldset>
	<legend><spring:message code="danceschool.others"/></legend>
	<acme:textbox code="danceschool.phone" path="phone"/>
	<acme:textbox code="danceschool.location.address" path="address"/>
	<acme:textbox code="danceschool.location.city" path="city"/>
	<acme:textbox code="danceschool.location.province" path="province"/>
	<acme:textbox code="danceschool.picture" path="picture"/>
	</fieldset>
	
	<acme:submit code="danceschool.save" name="save"/>
	
	<acme:cancel url="welcome/index.do" code="manager.cancel"/>

</form:form>