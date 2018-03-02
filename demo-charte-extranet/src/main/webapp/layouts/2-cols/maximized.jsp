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
            <p:region regionName="maximized" />
        </div> 
    </main>
    
    <!-- Footer -->
    <jsp:include page="../includes/footer.jsp" />
</body>

</html>
