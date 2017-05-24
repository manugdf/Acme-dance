<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('MANAGER')">

	<spring:message code="manager.numBanners"/>
	<h2><jstl:out value="${numBanners}" /></h2>
	<spring:message code="numBannersAccepted"/>
	<h2><jstl:out value="${numBannersAccepted}"/></h2>
	<spring:message code="manager.monthlyBill"/>
	<h2><jstl:out value="${monthlyBill}" /></h2>
</security:authorize>