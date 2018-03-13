<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://www.toutatice.fr/jsp/taglib/toutatice" prefix="ttc" %>

<%@ page isELIgnored="false" %>


<div class="row">
    <c:forEach var="document" items="${documents}">
		<div class="col-md-6 col-sm-12">
			<div class="thumbnail">
				<div class="caption">
					<c:set value="${document.properties['rcd:data']}" var="rcdData"/>
					
					<c:set value="${rcdData['_title']}" var="title"/>
					<c:if test="${not empty title}">
						<h3>${title}</h3>
					</c:if>
					
					<c:set value="${rcdData['description0']}" var="description"/>
					<c:if test="${not empty description}">
	                    <div>${description}</div>
	                </c:if>
					
					<c:set value="${rcdData['note']}" var="note"/>
					<c:if test="${not empty note}">
	                    <p class="lead">${note}</p>
	                </c:if>
				</div>
			</div>
		</div>
	</c:forEach>
</div>
