<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://www.toutatice.fr/jsp/taglib/toutatice" prefix="ttc" %>

<%@ page isELIgnored="false" %>

<div class="text-center no-ajax-link">
  	<a class="btn btn-default no-ajax-link" href="${launchProcedureUrl}">
  		<op:translate key="FRAGMENT_LAUNCH_SUPPORT"/>
  	</a>
</div>

<c:if test="${empty documents}">
    <p class="text-center">
        <span class="text-muted"><op:translate key="LIST_NO_ITEMS" /></span>
    </p>
</c:if>
