<%@ taglib uri="portal-layout" prefix="p" %>


<div class="${editorial ? 'simple-content-navbar' : 'content-navbar'}">
    <!-- Breadcrumb -->
    <div class="${editorial ? '' : 'content-navbar-breadcrumb'}">
        <p:region regionName="breadcrumb" />
    </div>
    
    <!-- Menubar -->
    <div class="${editorial ? 'simple-menubar' : 'content-navbar-actions'}">
        <p:region regionName="menubar" />
    </div>
</div>
