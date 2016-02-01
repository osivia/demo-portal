<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://www.toutatice.fr/jsp/taglib/toutatice" prefix="ttc" %>

<%@ page isELIgnored="false" %>


<p class="text-muted">
    <c:choose>
        <c:when test="${totalSize eq 0}">
            <span><op:translate key="SEARCH_RESULTS_NO_RESULT" /></span>
        </c:when>
        
        <c:when test="${totalSize eq 1}">
            <span><op:translate key="SEARCH_RESULTS_ONE_RESULT" /></span>
        </c:when>
        
        <c:otherwise>
            <span><op:translate key="SEARCH_RESULTS_MULTIPLE_RESULTS" args="${totalSize}" /></span>
        </c:otherwise>
    </c:choose>
</p>

<ol>
    <c:forEach var="document" items="${documents}">
        <li>
            <p>
                <!-- Title -->
                <span><ttc:title document="${document}" icon="true" /></span>
                
                <!-- Description -->
                <c:if test="${not empty document.properties['dc:description']}">
                    <br>
                    <span>${document.properties['dc:description']}</span>
                </c:if>
            </p>
        </li>
    </c:forEach>
</ol>
