<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal"	prefix="op"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%@ page contentType="text/html" isELIgnored="false" %>

<portlet:defineObjects />

<portlet:actionURL name="loadScheduler" var="loadSchedulerUrl" copyCurrentRenderParameters="true" />

<portlet:resourceURL id="searchContributor" var="searchContributorUrl"/>

<div class="demo-scheduler-contribution" >
<form:form action="${loadSchedulerUrl}" method="post" modelAttribute="schedulerForm" cssClass="form-horizontal" role="form">

	<div class="form-group required">
	
		<c:set var="placeholder"><op:translate key="SEARCH_PERSON_PLACEHOLDER" /></c:set>
        <c:set var="noResults"><op:translate key="SELECT2_NO_RESULTS" /></c:set>
		<form:label path="selectedContributor"
			cssClass="control-label">
			<op:translate key="SELECT_CONTRIBUTOR" />
		</form:label>
		<form:select  cssClass="form-control select2 select2-default" path="selectedContributor" class="form-control"
		data-placeholder="${placeholder}" data-no-results="${noResults}">
			<form:option value=""></form:option>
			<c:forEach var="contributor" items="${schedulerForm.contributorsList}">
				<form:option value="${contributor.id}" data-avatar="${contributor.avatarUrl}">${contributor.text}</form:option>
			</c:forEach>
		</form:select>
		
		<input type="submit" id="selectContributor" class="hidden"/>
	</div>
	<br/>
	
	<c:if test="${not empty schedulerForm.selectedContributor}">
		<div class="form-group required">
			<div class="table-responsive">
				<table class="table table-bordered text-center">
					<thead>
						<tr>
							<th class="text-center">
							</th>
							<th class="text-center">
								<div class="media">
									<div class="media-left media-middle">
										<portlet:actionURL name="previousWeek" var="previousWeekUrl">
										</portlet:actionURL>
										<h3><a href="${previousWeekUrl}" ><i class="glyphicons glyphicons-chevron-left"></i></a></h3>
									</div>
									<div class="media-body media-middle">
										<div><fmt:formatDate value="${schedulerForm.monday}" type="date" pattern="EEEE" /></div>
										<div class="text-muted"><fmt:formatDate value="${schedulerForm.monday}" type="date" pattern="dd MMMM" /></div>
									</div>
								</div>
							</th>
							<th class="text-center vertical-center">
								<div class="media">
								<div class="media-body">
								<div><fmt:formatDate value="${schedulerForm.tuesday}" type="date" pattern="EEEE" /></div>
								<div class="text-muted"><fmt:formatDate value="${schedulerForm.tuesday}" type="date" pattern="dd MMMM" /></div>
								</div>
								</div>
							</th>
							<th class="text-center vertical-center">
								<div class="media">
								<div class="media-body media-middle">
								<div><fmt:formatDate value="${schedulerForm.wednesday}" type="date" pattern="EEEE" /></div>
								<div class="text-muted"><fmt:formatDate value="${schedulerForm.wednesday}" type="date" pattern="dd MMMM" /></div>
								</div>
								</div>
							</th>
							<th class="text-center vertical-center">
								<div class="media">
								<div class="media-body media-middle">
								<div><fmt:formatDate value="${schedulerForm.thursday}" type="date" pattern="EEEE" /></div>
								<div class="text-muted"><fmt:formatDate value="${schedulerForm.thursday}" type="date" pattern="dd MMMM" /></div>
								</div>
								</div>
							</th>
							<th class="text-center">
								<div class="media">
									<div class="media-body media-middle">
										<div><fmt:formatDate value="${schedulerForm.friday}" type="date" pattern="EEEE" /></div>
										<div class="text-muted"><fmt:formatDate value="${schedulerForm.friday}" type="date" pattern="dd MMMM" /></div>
										<portlet:actionURL name="nextWeek" var="nextWeekUrl">
										</portlet:actionURL>
									</div>
									<div class="media-right media-middle">
										<h3><a href="${nextWeekUrl}" ><i class="glyphicons glyphicons-chevron-right"></i></a></h3>
									</div>
								</div>
							</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>
								<div class="media">
								<div class="media-body time-slot-padding">
								<op:translate key="DEMO_SCHEDULER_MORNING" />
								</div>
								</div>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyMondayMorning}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.monday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyTuesdayMorning}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.tuesday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyWednesdayMorning}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.wednesday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyThursdayMorning}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.thursday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyFridayMorning}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.friday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td>
								<div class="media">
								<div class="media-body time-slot-padding">
								<op:translate key="DEMO_SCHEDULER_AFTERNOON" />
								</div>
								</div>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyMondayAfternoon}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.monday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyTuesdayAfternoon}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.tuesday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyWednesdayAfternoon}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.wednesday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyThursdayAfternoon}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.thursday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td class="vertical-center">
								<c:choose>
									<c:when test="${schedulerForm.busyFridayAfternoon}">
										<i class="glyphicons glyphicons-minus time-slot-padding"></i>
									</c:when>
									<c:otherwise>
										<fmt:formatDate var="day" value="${schedulerForm.friday}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<a href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
</form:form>
</div>