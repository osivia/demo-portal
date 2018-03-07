<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page isELIgnored="false" %>

<div class="row no-ajax-link">
	<div class="col-sm-12">
		<h3>${title}</h3>
	</div>
	<div class="col-sm-12">
		<a class="thumbnail no-margin-bottom no-ajax-link" href="${visuelUrl}" data-fancybox="gallery" data-caption="${visuelFilename}" data-type="image">
		  <img src="${visuelUrl}" alt="${visuelFilename}">
		</a>
	</div>
	<div class="col-sm-12">
		<c:out value="${description}"/>
	</div>
</div>
