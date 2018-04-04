<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://www.toutatice.fr/jsp/taglib/toutatice" prefix="ttc" %>

<%@ page isELIgnored="false" %>


<ul class="list-unstyled">
    <c:forEach var="document" items="${documents}" varStatus="status">
        <c:set var="data" value="${document.properties['pi:data']}" />
    
        <li class="media">
            <div class="media-body">
                <h3 class="h5 media-heading">${data.titre}</h3>
                <div>
                    <span><ttc:user name="${data.intervenant}" hideAvatar="true" linkable="true" /></span>
                    <span>&ndash;</span>
                    <span><fmt:formatDate value="${data.date}" type="date" dateStyle="full" /></span>
                    <span>
                        <c:choose>
                            <c:when test="${data.plage eq 'AM'}"><op:translate key="LIST_TEMPLATE_INTERVENTIONS_AM" /></c:when>
                            <c:otherwise><op:translate key="LIST_TEMPLATE_INTERVENTIONS_PM" /></c:otherwise>
                        </c:choose>
                    </span>
                </div>
                <div>
                    <c:choose>
                        <c:when test="${data.accepted}">
                            <span class="text-success">
                                <i class="glyphicons glyphicons-ok"></i>
                                <span><op:translate key="LIST_TEMPLATE_INTERVENTIONS_ACCEPTED" /></span>
                            </span>
                        </c:when>
                        
                        <c:otherwise>
                            <span class="text-warning">
                                <i class="glyphicons glyphicons-hourglass"></i>
                                <span><op:translate key="LIST_TEMPLATE_INTERVENTIONS_PENDING" /></span>
                            </span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </li>
    </c:forEach>
    
    
    <c:if test="${empty documents}">
        <li>
            <p class="text-muted"><op:translate key="LIST_TEMPLATE_INTERVENTIONS_EMPTY" classLoader="${classloader}" /></p>
        </li>
    </c:if>
</ul>

<hr>

<div>
    <a href="${interventionRequestUrl}" class="btn btn-default no-ajax-link">
        <span><op:translate key="LIST_TEMPLATE_INTERVENTIONS_ADD_INTERVENTION" /></span>
    </a>
</div>
