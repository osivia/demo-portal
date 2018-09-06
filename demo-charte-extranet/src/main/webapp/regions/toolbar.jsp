<%@ taglib uri="portal-layout" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>

<%@ page contentType="text/html" isELIgnored="false"%>


<!-- Administration -->
<div class="col-md-3 col-lg-4  hidden-sm hidden-xs">
    <c:out value="${requestScope['osivia.toolbar.administrationContent']}" escapeXml="false" />
</div>


<!-- User links -->
<div class="col-sm-8 col-md-5 col-lg-3">
    <div class="text-right">
        <c:choose>
            <c:when test="${empty requestScope['osivia.toolbar.principal']}">
                <a href="${requestScope['osivia.toolbar.loginURL']}" class="btn btn-link">
                    <i class="halflings halflings-log-in"></i>
                    <span><op:translate key="LOGIN" /></span>
                </a>
            </c:when>

            <c:otherwise>
                <!-- Tasks -->
                <c:if test="${not empty requestScope['osivia.toolbar.tasks.url']}">
                    <c:set var="title"><op:translate key="NOTIFICATION_TASKS" /></c:set>
                    <button type="button" name="open-tasks" class="btn btn-link" data-target="#osivia-modal"
                        data-load-url="${requestScope['osivia.toolbar.tasks.url']}" data-load-callback-function="tasksModalCallback"
                        data-title="${title}" data-footer="true">
                        <i class="glyphicons glyphicons-bell"></i>
                        <span class="sr-only">${title}</span>
                        <span class="counter small">
                            <c:choose>
                                <c:when test="${requestScope['osivia.toolbar.tasks.count'] gt 0}">
                                    <span class="label label-danger">${requestScope['osivia.toolbar.tasks.count']}</span>
                                </c:when>

                                <c:otherwise>
                                    <span class="label label-default">0</span>
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </button>
                </c:if>
                
                <div class="btn-group dropdown">
                    <button type="button" class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                        <c:choose>
                            <c:when test="${empty requestScope['osivia.toolbar.person']}">
                                <i class="halflings halflings-user"></i>
                                <span>${requestScope['osivia.toolbar.principal']}</span>
                            </c:when>

                            <c:otherwise>
                                <img class="avatar" src="${requestScope['osivia.toolbar.person'].avatar.url}" alt="">
                                <span>${requestScope['osivia.toolbar.person'].displayName}</span>
                            </c:otherwise>
                        </c:choose>
                        <span class="caret"></span>
                    </button>

                    <ul class="dropdown-menu dropdown-menu-right" role="menu">
                        <c:set var="divider" value="false" />
                    
                        <!-- User profile -->
                        <c:if test="${not empty requestScope['osivia.toolbar.myprofile']}">
                            <c:set var="divider" value="true" />
                            <li role="presentation">
                                <a href="${requestScope['osivia.toolbar.myprofile']}" role="menuitem">
                                    <i class="glyphicons glyphicons-nameplate"></i>
                                    <span><op:translate key="USER_PROFILE" /></span>
                                </a>
                            </li>
                        </c:if>
                        
                        <!-- User workspace -->
                        <c:if test="${not empty requestScope['osivia.userWorkspace.url']}">
                            <c:set var="divider" value="true" />
                            <li role="presentation">
                                <a href="${requestScope['osivia.userWorkspace.url']}" role="menuitem">
                                    <i class="glyphicons glyphicons-wallet"></i>
                                    <span><op:translate key="USER_WORKSPACE"/></span>
                                </a>
                            </li>
                        </c:if>
                        
                        <!-- User settings -->
                        <c:if test="${not empty requestScope['osivia.toolbar.userSettings.url']}">
                            <c:set var="divider" value="true" />
                            <li role="presentation">
                                <a href="${requestScope['osivia.toolbar.userSettings.url']}" role="menuitem">
                                    <i class="glyphicons glyphicons-cogwheel"></i>
                                    <span><op:translate key="USER_SETTINGS"/></span>
                                </a>
                            </li>
                        </c:if>

                        <c:if test="${divider}">
                            <li class="divider" role="presentation"></li>
                        </c:if>

                        <!-- Logout -->
                        <li role="presentation">
                            <a href="#" onclick="logout()" role="menuitem">
                                <i class="halflings halflings-log-out"></i>
                                <span><op:translate key="LOGOUT" /></span>
                            </a>
                        </li>
                    </ul>
                    
                </div>
            </c:otherwise>
        </c:choose>
        
      
    </div>
   
 </div>
 
    <div class="col-md-1 col-lg-1  hidden-sm hidden-xs">
    <a href="/portal/web/welcome" class="btn btn-link">
           <span><op:translate key="HELP" /></span>
    </a>    
   </div> 


<!-- Disconnection modal -->
<div id="disconnection" class="modal fade" data-apps="${op:join(requestScope['osivia.sso.applications'], '|')}"
    data-redirection="${requestScope['osivia.toolbar.signOutURL']}">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-body">
                <i class="glyphicons glyphicons-exit"></i>
                <span><op:translate key="LOGOUT_MESSAGE" /></span>
            </div>
        </div>
    </div>

    <div class="apps-container hidden"></div>
</div>
