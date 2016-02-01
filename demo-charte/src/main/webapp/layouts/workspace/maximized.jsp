<!DOCTYPE html>
<%@ taglib uri="portal-layout" prefix="p" %>


<html>

<head>
    <jsp:include page="../includes/head.jsp" />
</head>


<body>
    <jsp:include page="../includes/header.jsp" />
    
    <div class="wrapper-outer">
        <div class="wrapper-inner">
            <div id="page-content" class="container-fluid">
                <!-- Content navbar -->
                <jsp:include page="../includes/content-navbar.jsp" />
            
                <!-- Notifications -->
                <p:region regionName="notifications" />
                
                <div class="row">
                    <!-- Drawer -->
                    <div id="drawer">
                        <p:region regionName="drawer-toolbar" />
                        
                        <div class="col-auto">
                            <div class="row">
                                <div class="col-auto">
                                    <p:region regionName="col-1" />
                                </div>
                                
                                <div
                                    <c:choose>
                                        <c:when test="${requestScope['osivia.panels.navigation-panel.closed']}">class="hidden"</c:when>
                                        <c:otherwise>class="col-auto"</c:otherwise>
                                    </c:choose>
                                >
                                    <p:region regionName="navigation-panel" />
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div>
                        <div class="col-sm-12">
                            <p:region regionName="maximized" />
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
