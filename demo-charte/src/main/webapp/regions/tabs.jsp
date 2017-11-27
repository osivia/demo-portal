<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>

<%@ page contentType="text/html" isELIgnored="false"%>


<c:set var="userPortal" value="${requestScope['osivia.userPortal']}" />
<c:set var="userPages" value="${userPortal.userPages}" />
<c:set var="currentId" value="${requestScope['osivia.currentPageId']}" />


<nav class="tabs" role="navigation">
    <!-- Title -->
    <h2 class="sr-only"><op:translate key="TABS_TITLE" /></h2>
    
    <div class="primary-tabs clearfix">
        <!-- Home -->
        <c:if test="${not empty userPortal.defaultPage}">
            <div class="pull-left">
                <ul class="home">
                    <li role="presentation"
                        <c:if test="${userPortal.defaultPage.id eq currentId}">class="active"</c:if>
                    >
                        <a href="${userPortal.defaultPage.url}" title="${userPortal.defaultPage.name}" data-toggle="tooltip" data-placement="bottom">
                            <i class="halflings halflings-home"></i>
                            <span class="sr-only">${userPortal.defaultPage.name}</span>
                        </a>
                    </li>
                </ul>
            </div>
        </c:if>
        
        
        <!-- Tabs -->
        <div class="fixed-tabs-container">
            <ul>
                <c:forEach var="userPage" items="${userPages}">
                    <c:if test="${not userPage.defaultPage}">
                        <li role="presentation"
                            <c:if test="${userPage.id eq currentId}">class="active"</c:if>
                        >
                            <a href="${userPage.url}"
                                <c:if test="${not empty fixed}">title="${userPage.name}" data-toggle="tooltip" data-placement="bottom"</c:if>
                            >
                                <span>${userPage.name}</span>
                            </a>
                            
                            <!-- Close -->
                            <c:if test="${not empty userPage.closePageUrl}">
                                <a href="${userPage.closePageUrl}" class="page-close">
                                    <i class="glyphicons glyphicons-remove"></i>
                                    <span class="sr-only"><op:translate key="CLOSE" /></span>
                                </a>
                            </c:if>
                        </li>
                    </c:if>
                </c:forEach>
            </ul>
        </div>
    </div>
</nav>
