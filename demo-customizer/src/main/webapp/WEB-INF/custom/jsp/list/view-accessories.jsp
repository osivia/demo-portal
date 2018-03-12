<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://www.toutatice.fr/jsp/taglib/toutatice" prefix="ttc" %>

<%@ page isELIgnored="false" %>


<div class="bxslider-container">
	<ol class="bxslider bxslider-default">
	    <c:forEach var="document" items="${documents}">
	        <li class="bxslider-slide">
	            <p>
	                <span><ttc:title document="${document}" icon="true" /></span>
	                
	                <h3>${document.properties['title']}</h3>
	                
	                <a class="thumbnail no-margin-bottom no-ajax-link" href=" ${document.properties['visuelUrl']}" data-fancybox="gallery" data-caption="${document.properties['visuelFilename']}" data-type="image">
					  <img src=" ${document.properties['visuelUrl']}" alt="${document.properties['visuelFilename']}">
					</a>
	                
	                <c:if test="${not empty document.properties['description']}">
	                    <br>
	                    <span>${document.properties['description']}</span>
	                </c:if>
	               
					<c:if test="${not empty document.properties['prixht']}">
	                    <br>
	                    <span>${document.properties['prixht']}</span>
	                </c:if>

	                <div class="text-center no-ajax-link">
					  	<a class="btn btn-default no-ajax-link" href="${document.properties['orderUrl']}">
					  		<op:translate key="LIST_TEMPLATE_ACCESSORIES_ORDER"/>
					  	</a>
					</div>
	            </p>
	        </li>
	    </c:forEach>
	</ol>
</div>
