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
                <!-- Notifications -->
                <p:region regionName="notifications" />
                
                <div class="row">
                    <!-- Drawer -->
                    <div id="drawer">
                        <div class="col-sm-4 col-md-3">
                            <p:region regionName="col-1" />
                        </div>
                    </div>
                    
                    <div class="col-sm-8 col-md-9">
                        <p:region regionName="maximized" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
