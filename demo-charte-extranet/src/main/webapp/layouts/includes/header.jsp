<%@ taglib uri="portal-layout" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<header class="${home ? '' : 'inner'}">
    <c:if test="${home}">
        <div class="bandeau">
            <p:region regionName="bandeau" cms="true" />
        </div>
    </c:if>


    <div class="header-top">
        <div class="container">
            <div class="row">
                <div class="col-sm-4 col-md-3 col-lg-4 logo">
                    <p:region regionName="logo" cms="true" />
                </div>
                
                <!-- Toolbar -->
                <p:region regionName="toolbar" />
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
                <div class="navbar-container">
                    <!-- Tabs -->
                    <p:region regionName="navigation-menu" cms="true" />
                    
                    <!-- Search -->
                    <p:region regionName="search" />
                </div>
            </div>
        </div>
    </nav>
</header>
