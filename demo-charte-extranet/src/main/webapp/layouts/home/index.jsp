<!DOCTYPE html>
<%@ taglib uri="portal-layout" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="home" value="true" scope="request" />


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
    <main>
        <div class="container">
            <p:region regionName="top-before" cms="true" />
            <p:region regionName="top" cms="false" />
            <p:region regionName="top-after" cms="true" />
            
            <div class="row">
                <div class="col-sm-6">
                    <p:region regionName="col-1-before" cms="true" />
                    <p:region regionName="col-1" cms="false" />
                    <p:region regionName="col-1-after" cms="true" />
                </div>
                
                <div class="col-sm-6">
                    <p:region regionName="col-2-before" cms="true" />
                    <p:region regionName="col-2" cms="false" />
                    <p:region regionName="col-2-after" cms="true" />
                </div>
            </div>
            
            <p:region regionName="bottom-before" cms="true" />
            <p:region regionName="bottom" cms="false" />
            <p:region regionName="bottom-after" cms="true" />
        </div> 
    </main>
    
    <!-- Footer -->
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
