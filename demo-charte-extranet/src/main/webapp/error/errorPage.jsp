<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<c:set var="brand"><op:translate key="BRAND" /></c:set>


<html>

<head>
    <title><op:translate key="ERROR" /> - ${brand}</title>
    
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

    <!-- Socle -->
    <link rel="stylesheet" href="/osivia-portal-custom-web-assets/css/osivia.min.css">
    
    <!-- Glyphicons -->
    <link rel="stylesheet" href="/osivia-portal-custom-web-assets/css/glyphicons.min.css">

    <meta http-equiv="default-style" content="Demo">
    <link rel="stylesheet" href="${contextPath}/css/demo-extranet.min.css" title="Demo" />
</head>


<body>
    <!-- Header -->
    <header class="inner">
        <!-- Toolbar -->
        <div class="header-top">
            <div class="container">
                <div class="row">
                    <div class="col-sm-4 col-md-3">
                        <a href="#">
                            <img src="${contextPath}/img/logo.png" alt="Digi Shoppe" class="img-responsive logo" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Navigation -->
        <nav class="navbar header-navbar" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="btn btn-navbar navbar-toggle" data-toggle="collapse" data-target=".navbar-cat-collapse">
                        <span class="sr-only">Toggle Navigation</span>
                        <i class="glyphicons glyphicons-menu-hamburger"></i>
                    </button>
                </div>
                
                <div class="collapse navbar-collapse navbar-cat-collapse">
                    <!-- Tabs -->
                    <ul class="nav navbar-nav">
                        <li role="presentation" class="active">
                            <a href="#">
                                <span><op:translate key="ERROR" /></span>
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </header>
    
    <!-- Breadcrumb & menubar -->
    <div class="breadcrumb-wrap">
        <div class="container">
            <!-- Breadcrumb -->
            <ol class="breadcrumb">
                <li class="active"><op:translate key="ERROR" /></li>
            </ol>
        </div>
    </div>
    
    <!-- Main -->
    <main class="inner">
        <div class="container">
            <div class="row">
                <div class="col-lg-offset-3 col-lg-6">
                    <div class="panel panel-danger">
                        <div class="panel-body">
                            <div class="page-header">
                                <h1 class="text-center text-danger">
                                    <span><op:translate key="ERROR" /></span>
                                    
                                    <c:choose>
                                        <c:when test="${(param['httpCode'] eq 401) || (param['httpCode'] eq 403) || (param['httpCode'] eq 404) || (param['httpCode'] eq 500)}">
                                            <span>${param['httpCode']}</span>
                                            <small><op:translate key="ERROR_${param['httpCode']}" /></small>
                                        </c:when>
                                        
                                        <c:otherwise>
                                            <small><op:translate key="ERROR_GENERIC_MESSAGE" /></small>
                                        </c:otherwise>
                                    </c:choose>
                                </h1>
                            </div>
                            
                            <c:if test="${(param['httpCode'] eq 401) || (param['httpCode'] eq 403) || (param['httpCode'] eq 404) || (param['httpCode'] eq 500)}">
                                <p class="lead text-center">
                                    <span><op:translate key="ERROR_${param['httpCode']}_MESSAGE" /></span>
                                </p>
                            </c:if>
                            
                            <div class="text-center">
                                <a href="/" class="btn btn-link">
                                    <span><op:translate key="BACK_TO_HOME" /></span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>    
            </div>
        </div> 
    </main>
    
    <!-- Footer -->
    <footer>
        <!-- Links -->
        <div class="footer-links">
            <div class="container">
            
            </div>
        </div>
        
        <!-- Copyright -->
        <div class="copyright">
            <div class="container">
                <p class="text-center">
                    <i class="glyphicons glyphicons-copyright-mark"></i>
                    <span>OSIVIA - 2018</span>
                </p>
            </div>
        </div>
    </footer>
</body>

</html>
