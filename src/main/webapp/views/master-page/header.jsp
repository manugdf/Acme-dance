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
			<li><a class="fNiv" href="manager/administrator/list.do"><spring:message
						code="master.page.managers" /></a></li>
			<li><a class="fNiv" href="censoredWords/administrator/list.do"><spring:message
						code="master.page.censored" /></a></li>
			<li><a class="fNiv"
				href="danceSchool/administrator/listPending.do"><spring:message
						code="master.page.listPendingSchools" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.listBannersAdmin" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="banner/administrator/list.do"><spring:message
								code="master.page.listPendingBanners" /> </a></li>
					<li><a href="banner/administrator/listAll.do"><spring:message
								code="master.page.listAllBanners" /> </a></li>
				</ul></li>
			
			<li><a class="fNiv" href="dashboard/administrator.do"><spring:message
						code="master.page.dashboard" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('MANAGER')">
			<li><a class="fNiv" href="teacher/manager/list.do"><spring:message
						code="master.page.teachersByManager" /></a></li>
			<li><a class="fNiv" href="danceSchool/manager/list.do"><spring:message
						code="master.page.schoolsByManager" /></a></li>
			<li><a class="fNiv" href="competition/list.do"><spring:message
						code="master.page.competitions" /></a></li>
			<li><a class="fNiv" href="danceSchool/manager/create.do"><spring:message
						code="master.page.createDanceSchool" /></a></li>
			<li><a class="fNiv" href="banner/manager/list.do"><spring:message
						code="master.page.myBanners" /></a></li>
			<li><a class="fNiv" href="banner/manager/showMonthlyBill.do"><spring:message
						code="master.page.showMonthlyBill" /></a></li>
		</security:authorize>

		<security:authorize access="hasRole('TEACHER')">
			<li><a class="fNiv" href="danceClass/teacher/list.do"><spring:message
						code="master.page.danceClassessByTeacher" /></a></li>
			<li><a class="fNiv" href="material/teacher/list.do"><spring:message
						code="master.page.teacher.material" /></a></li>
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
					<li><a href="partnerInvitation/alumn/listReceived.do"><spring:message
								code="master.page.partners.received" /></a></li>
					<li><a href="partnerInvitation/alumn/listSended.do"><spring:message
								code="master.page.partners.sended" /></a></li>
				</ul></li>
			<li><a class="fNiv" href="danceClass/alumn/listMyClasses.do"><spring:message
						code="master.page.myClasses" /></a></li>
		</security:authorize>

		<security:authorize access="isAnonymous()">

			<li><a class="fNiv" href="security/login.do"><spring:message
						code="master.page.login" /></a></li>

			<li><a class="fNiv" href="danceSchool/listAll.do"><spring:message
						code="master.page.schools" /></a></li>

			<li><a class="fNiv"> <spring:message
						code="master.page.register" />
			</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="alumn/register.do"><spring:message
								code="master.page.register.alumn" /></a></li>
					<li><a href="manager/register.do"><spring:message
								code="master.page.register.manager" /></a></li>
					<li><a href="competitionPlanner/register.do"><spring:message
								code="master.page.register.competitionPlanner" /></a></li>
				</ul></li>

		</security:authorize>

		<security:authorize access="isAuthenticated()">
			<li><a class="fNiv" href="message/actor/list.do"><spring:message
						code="master.page.messages" /></a></li>
			<li><a class="fNiv"> <spring:message
						code="master.page.profile" /> (<security:authentication
						property="principal.username" />)
			</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('ALUMN')">
						<li><a href="alumn/edit.do"><spring:message
									code="master.page.profile.edit" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('MANAGER')">
						<li><a href="manager/edit.do"><spring:message
									code="master.page.profile.edit" /></a></li>
					</security:authorize>
					<li class="arrow"></li>
					<li><a href="j_spring_security_logout"><spring:message
								code="master.page.logout" /> </a></li>
				</ul></li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

