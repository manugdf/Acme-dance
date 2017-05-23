<%--
 * header.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

<div>
	<img src="images/logo.png" alt="Acme-Dance Co., Inc." />
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv" href="censoredWords/administrator/list.do"><spring:message
						code="master.page.censored" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/action-1.do"><spring:message
								code="master.page.administrator.action.1" /></a></li>
					<li><a href="administrator/action-2.do"><spring:message
								code="master.page.administrator.action.2" /></a></li>
				</ul></li>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv" href="teacher/manager/list.do"><spring:message
						code="master.page.teachersByManager" /></a></li>
			<li><a class="fNiv" href="danceSchool/manager/list.do"><spring:message
						code="master.page.schoolsByManager" /></a></li>
			<li><a class="fNiv" href="competition/list.do"><spring:message
						code="master.page.competitions" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('TEACHER')">
			<li><a class="fNiv" href="danceClass/teacher/list.do"><spring:message
						code="master.page.danceClassessByTeacher" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('ALUMN')">
			<li><a class="fNiv" href="danceSchool/listAll.do"><spring:message
						code="master.page.schools" /></a></li>
			<li><a class="fNiv"><spring:message
						code="master.page.partners" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="partnerRequest/alumn/list.do"><spring:message
								code="master.page.partners.mypartners" /></a></li>
					<li><a href="danceSchool/alumn/list.do"><spring:message
								code="master.page.partners.find" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="danceClass/alumn/listMyClasses.do"><spring:message
						code="master.page.myClasses" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv" href="danceSchool/listAll.do"><spring:message
						code="master.page.schools" /></a></li>

			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<li><a class="fNiv"> <spring:message
						code="master.page.register" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="alumn/register.do"><spring:message
								code="master.page.register.alumn" /></a></li>
					<li><a href="manager/register.do"><spring:message
								code="master.page.register.manager" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="profile/action-1.do"><spring:message
								code="master.page.profile.action.1" /></a></li>
					<li><a href="profile/action-2.do"><spring:message
								code="master.page.profile.action.2" /></a></li>
					<li><a href="profile/action-3.do"><spring:message
								code="master.page.profile.action.3" /></a></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

