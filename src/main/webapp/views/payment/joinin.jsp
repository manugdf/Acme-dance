<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="${requestUri}" modelAttribute="payment">
    <form:hidden path="id"/>

    <div class="form-group">
        <form:label path="paymentType">
            <spring:message code="payment.type" />
        </form:label>
        <select id="paymentType" name="paymentType" ng-model="payment.paymentType">
            <option value="0"><spring:message code="payment.type.select" /> </option>
            <option value="MONTHLY"><spring:message code="payment.type.1" /></option>
            <option value="YEARLY"><spring:message code="payment.type.2" /></option>
        </select>
    </div>

    <acme:submit code="payment.joinin" name="save"/>

    <acme:cancel code="payment.cancel" url="/danceClass/list.do?danceSchoolId=${danceschool.id}"/>


</form:form>