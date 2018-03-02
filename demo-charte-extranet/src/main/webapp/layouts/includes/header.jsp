<%@ taglib uri="portal-layout" prefix="p" %>


<header class="inner">
    <!-- Toolbar -->
    <p:region regionName="toolbar" />
    
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
                <p:region regionName="tabs" />
                
                <!-- Search -->
                <p:region regionName="search" />
            </div>
        </div>
    </nav>
</header>
