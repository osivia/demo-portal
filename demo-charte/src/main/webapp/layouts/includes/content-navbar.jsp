<%@ taglib uri="portal-layout" prefix="p" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:choose>
    <c:when test="${home}">
        <!-- Simple menubar -->
        <div class="simple-menubar">
            <p:region regionName="menubar" />
        </div>
    </c:when>
    
    <c:otherwise>
        <!-- Content navbar -->
        <div class="content-navbar">
            <!-- Breadcrumb -->
            <div class="content-navbar-breadcrumb">
                <p:region regionName="breadcrumb" />
            </div>
            
            <!-- Actions -->
            <div class="content-navbar-actions">
                <p:region regionName="menubar" />
            </div>
        </div>
    </c:otherwise>
</c:choose>
