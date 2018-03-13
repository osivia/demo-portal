<!DOCTYPE html>
<%@ taglib uri="portal-layout" prefix="p" %>


<html>

<head>
    <jsp:include page="../includes/head.jsp" />
</head>


<body>
    <!-- Header -->
    <jsp:include page="../includes/header.jsp" />
    
    <!-- Breadcrumb & menubar -->
    <jsp:include page="../includes/breadcrumb.jsp" />
    
    <!-- Main -->
    <main class="inner">
        <div class="container">
            <p:region regionName="top" cms="false" />
            <p:region regionName="top-cms" cms="true" />
            
            <div class="row">
                <div class="col-sm-6">
                    <p:region regionName="col-1" cms="false" />
                    <p:region regionName="col-1-cms" cms="true" />
                </div>
                
                <div class="col-sm-6">
                    <p:region regionName="col-2" cms="false" />
                    <p:region regionName="col-2-cms" cms="true" />
                </div>
            </div>
            
            <p:region regionName="bottom" cms="false" />
            <p:region regionName="bottom-cms" cms="true" />
        </div> 
    </main>
    
    <!-- Footer -->
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
