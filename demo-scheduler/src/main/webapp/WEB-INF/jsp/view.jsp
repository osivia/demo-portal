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
<form:form action="${loadSchedulerUrl}" method="post" modelAttribute="schedulerForm" role="form">

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
			<c:forEach var="contributor" items="${schedulerForm.technicians}">
				<form:option value="${contributor.id}" data-avatar="${contributor.avatarUrl}">${contributor.text}</form:option>
			</c:forEach>
		</form:select>
		
		<input type="submit" id="selectContributor" class="hidden"/>
	</div>
	<br/>
	
	<c:if test="${not empty schedulerForm.selectedContributor}">
		<div class="form-group required">
			<div class="table-responsive">
				<table class="table text-center">
					<thead>
						<tr>
							<th class="text-center">
							</th>
							<th class="text-center">
								<div class="media">
									<div class="media-left media-middle">
										<c:if test="${schedulerForm.showPreviousButton}">
											<portlet:actionURL name="previousWeek" var="previousWeekUrl">
											</portlet:actionURL>
											<h3><a href="${previousWeekUrl}" ><i class="glyphicons glyphicons-chevron-left"></i></a></h3>
										</c:if>
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
							<th class="vertical-center">
								<div class="media">
								<div class="media-body text-center">
								<op:translate key="DEMO_SCHEDULER_MORNING" />
								</div>
								</div>
							</th>
							<c:forEach items="${schedulerForm.timeSlots}" begin = "0" end = "4" var="timeSlot" varStatus="myIndex">
							<td class="vertical-center">
								<c:choose>
									<c:when test="${empty timeSlot}">
										<fmt:formatDate var="day" value="${((myIndex.index eq 0)? schedulerForm.monday : ((myIndex.index eq 1)? schedulerForm.tuesday : ((myIndex.index eq 2)? schedulerForm.wednesday : ((myIndex.index eq 3)? schedulerForm.thursday : schedulerForm.friday))))}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="AM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<div class="alert alert-info reservation">
											<a href="${url}" class="alert-link"><op:translate key="CHOOSE_TIME_SLOT" /></a>
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${timeSlot.reservation && timeSlot.accepted}">
												<div class="alert alert-success reservation">
													<b>${timeSlot.title}<br/></b>
													<small>
													<op:translate key="REQUEST_FOR_INTERVENTION" />
													<fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern="dd MMMM " /><op:translate key="REQUEST_FOR_INTERVENTION_AT" /><fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern=" HH:mm" /><br/>
													<op:translate key="REQUEST_FOR_INTERVENTION_BY" args="${timeSlot.creator}" />"<br/>
													<op:translate key="RESERVATION_ACCEPTED" />
													</small>
												</div>
											</c:when>
											<c:when test="${timeSlot.reservation && !timeSlot.accepted}">
												<div class="alert alert-warning reservation">
													<b>${timeSlot.title}<br/></b>
													<small>
													<op:translate key="REQUEST_FOR_INTERVENTION" />
													<fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern="dd MMMM " /><op:translate key="REQUEST_FOR_INTERVENTION_AT" /><fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern=" HH:mm" /><br/>
													<op:translate key="REQUEST_FOR_INTERVENTION_BY" args="${timeSlot.creator}" /><br/>
													<op:translate key="WAITING_RESERVATION" />
													</small>
												</div>
											</c:when>
											<c:otherwise>
												<i class="glyphicons glyphicons-minus time-slot-padding"></i>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							</c:forEach>
						</tr>
						<tr>
							<th class="vertical-center">
								<div class="media">
								<div class="media-body text-center">
								<op:translate key="DEMO_SCHEDULER_AFTERNOON" />
								</div>
								</div>
							</th>
							<c:forEach items="${schedulerForm.timeSlots}" begin = "5" end = "9" var="timeSlot" varStatus="myIndex">
							<td class="vertical-center">
								<c:choose>
									<c:when test="${empty timeSlot}">
										<fmt:formatDate var="day" value="${((myIndex.index eq 5)? schedulerForm.monday : ((myIndex.index eq 6)? schedulerForm.tuesday : ((myIndex.index eq 7)? schedulerForm.wednesday : ((myIndex.index eq 8)? schedulerForm.thursday : schedulerForm.friday))))}" type="date" pattern="dd/MM/yyyy" />
										<portlet:actionURL name="startContribution" var="url">
											<portlet:param name="halfDay" value="PM"/>
											<portlet:param name="day" value="${day}"/>
										</portlet:actionURL>
										<div class="alert alert-info reservation">
											<a class="alert-link" href="${url}"><op:translate key="CHOOSE_TIME_SLOT" /></a>
										</div>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${timeSlot.reservation && timeSlot.accepted}">
												<div class="alert alert-success reservation">
													<b>${timeSlot.title}<br/></b>
													<small>
													<op:translate key="REQUEST_FOR_INTERVENTION" />
													<fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern="dd MMMM " /><op:translate key="REQUEST_FOR_INTERVENTION_AT" /><fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern=" HH:mm" /><br/>
													<op:translate key="REQUEST_FOR_INTERVENTION_BY" args="${timeSlot.creator}" />"<br/>
													<op:translate key="RESERVATION_ACCEPTED" />
													</small>
												</div>
											</c:when>
											<c:when test="${timeSlot.reservation && !timeSlot.accepted}">
												<div class="alert alert-warning reservation">
													<b>${timeSlot.title}<br/></b>
													<small>
													<op:translate key="REQUEST_FOR_INTERVENTION" />
													<fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern="dd MMMM " /><op:translate key="REQUEST_FOR_INTERVENTION_AT" /><fmt:formatDate value="${timeSlot.dateCreationReservation}" type="date" pattern=" HH:mm" /><br/>
													<op:translate key="REQUEST_FOR_INTERVENTION_BY" args="${timeSlot.creator}" /><br/>
													<op:translate key="WAITING_RESERVATION" />
													</small>
												</div>
											</c:when>
											<c:otherwise>
												<i class="glyphicons glyphicons-minus time-slot-padding"></i>
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</td>
							</c:forEach>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</c:if>
</form:form>
</div>