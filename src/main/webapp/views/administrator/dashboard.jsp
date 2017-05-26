<%@page language="java" contentType="text/html; charset=ISO-8859-1"
        pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
          uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('ADMIN')">

    <!-- a.	El empresario o empresarios con más escuelas aceptadas. -->
    <h2><spring:message code = "administrator.dashboard.1"/></h2>
    <jstl:forEach items="${managerMoreDanceSchoolAccepted}" var="manager">
        <jstl:if test="${manager!=null }">
            <li><jstl:out value="${manager.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>


     <!-- b. El ratio de escuelas aceptadas respecto a las que han sido rechazadas. -->
    <h2><spring:message code = "administrator.dashboard.2"/></h2>
    <jstl:out value="${acceptedDeniedRatio}" />

     <!-- c. El profesor o profesores con el mejor rating de opiniones. -->
    <h2><spring:message code = "administrator.dashboard.3"/></h2>
    <jstl:forEach items="${bestRating}" var="teacher">
        <jstl:if test="${teacher!=null}">
            <li><jstl:out value="${teacher.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- d.	El ratio de mensajes enviados por usuario. -->
    <h2><spring:message code = "administrator.dashboard.4"/></h2>
    <jstl:out value="${messagesUsersRatio}" />
	

     <!-- e.	El usuario o usuarios que más mensajes ha enviado. -->
    <h2><spring:message code = "administrator.dashboard.5"/></h2>
    <jstl:forEach items="${actorMoreMessageSend}" var="actor">
        <jstl:if test="${actor!=null }">
            <li><jstl:out value="${actor.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- f.	El alumno o alumnos que se han apuntado a más clases. -->
    <h2><spring:message code = "administrator.dashboard.6"/></h2>
    <jstl:forEach items="${alumnsMoreClasses}" var="alumn">
        <jstl:if test="${alumn!=null}">
            <li><jstl:out value="${alumn.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

   <!-- g.El empresario que ha propuesto más banners aceptados. -->
    <h2><spring:message code = "administrator.dashboard.7"/></h2>
    <jstl:forEach items="${managerMoreBannersAccepted}" var="manager">
        <jstl:if test="${manager!=null}">
            <li><jstl:out value="${manager.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- h.	Una lista de profesores, ordenada de mejor a peor ratio de opiniones. -->
    <h2><spring:message code = "administrator.dashboard.8"/></h2>
    <jstl:forEach items="${teachersOrderedByRatio}" var="teacher">
        <jstl:if test="${teacher!=null}">
            <li><jstl:out value="${teacher.userAccount.username}"/></li>
        </jstl:if>
    </jstl:forEach>

    <!-- i.	El mínimo, el máximo y la media de clases impartidas por profesores. -->
    <h2><spring:message code = "administrator.dashboard.9"/></h2>
    <jstl:forEach items="${minAvgMaxClassesPerTeacher}" var="x">
        <jstl:if test="${x!=null}">
            <li>${x}</li>
        </jstl:if>
    </jstl:forEach>
    
     <!-- j.La media de duración de todos los eventos. -->
    <h2><spring:message code = "administrator.dashboard.10"/></h2>
    <jstl:out value="${eventAverageDuration}" /> 
    

</security:authorize>