<%@ taglib uri="portal-layout" prefix="p" %>


<!-- Toolbar -->
<p:region regionName="toolbar" />


<header class="hidden-xs">
    <div class="container-fluid">
        <!-- Banner -->
        <div class="banner">
            <div class="clearfix">
                <div class="pull-left">
                    <h1>
                        <a href="/">
                            <img src="/demo-charte/img/logo.png" alt="OSIVIA">
                        </a>
                    </h1>
                </div>
                
                <div class="pull-right">
                    <p:region regionName="search" />
                </div>
            </div>
        </div>
    </div>
</header>


<!-- Tabs -->
<div class="tabs-container hidden-xs">
    <div class="container-fluid">
        <p:region regionName="tabs" />
    </div>
</div>
