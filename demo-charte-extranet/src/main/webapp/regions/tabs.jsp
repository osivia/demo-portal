<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>

<%@ page contentType="text/html" isELIgnored="false"%>


<c:set var="userPortal" value="${requestScope['osivia.userPortal']}" />
<c:set var="userPages" value="${userPortal.userPages}" />


<ul class="nav navbar-nav">
    <c:forEach var="userPage" items="${userPages}">
        <li role="presentation" ${userPage.id eq currentId ? 'class="active"' : ''}>
            <a href="${userPage.url}">
                <span>${userPage.name}</span>
            </a>
        </li>
    </c:forEach>
</ul>
