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
            <div id="page-content" class="container">
                <!-- Content navbar -->
                <jsp:include page="../includes/content-navbar.jsp" />
                
                <!-- Notifications -->
                <p:region regionName="notifications" />
                
                <p:region regionName="top" cms="true" />
                
                <div class="row">
                    <!-- Drawer -->
                    <div id="drawer">
                        <div class="col-sm-4 col-md-3">
                            <p:region regionName="col-1-before" cms="true" />
                            <p:region regionName="col-1" />
                            <p:region regionName="col-1-after" cms="true" />
                        </div>
                    </div>
                    
                    <div class="col-sm-8 col-md-9">
                        <div class="row">
                            <div class="col-sm-6">
                                <p:region regionName="col-2-before" cms="true" />
                                <p:region regionName="col-2" />
                                <p:region regionName="col-2-after" cms="true" />
                            </div>
                            
                            <div class="col-sm-6">
                                <p:region regionName="col-3-before" cms="true" />
                                <p:region regionName="col-3" />
                                <p:region regionName="col-3-after" cms="true" />
                            </div>
                        </div>
                    </div>
                </div>
                
                <p:region regionName="bottom" cms="true" />
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
