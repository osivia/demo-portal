<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>

<%@ page isELIgnored="false" %>

<div class="row">
	<div class="col-sm-12">
		<h3 class="head-title">${title}</h3>
	</div>
</div>
<div class="row no-ajax-link">
	<div class="col-sm-12">
			<a class="thumbnail no-margin-bottom no-ajax-link" href="${visuelUrl}" data-fancybox="gallery" data-caption="${visuelFilename}" data-type="image">
			  <img src="${visuelUrl}" alt="${visuelFilename}">
			</a>
	</div>
	
	<div class="col-sm-12">
		<div class="tabs-panel">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab-description" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_DESCRIPTION" /></a>
				</li>
				<li><a href="#tab-news" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_NEWS" /></a></li>
				<li><a href="#tab-support" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT" /></a></li>
			</ul>
			<div class="tab-content clearfix">
				<div class="tab-pane active" id="tab-description">
					${description}
				</div>
				<div class="tab-pane" id="tab-news">
					news
				</div>
				<div class="tab-pane" id="tab-support">
					<div class="text-center no-ajax-link">
					  	<a class="btn btn-default no-ajax-link" href="${launchSupportUrl}">
					  		<op:translate key="FRAGMENT_LAUNCH_SUPPORT"/>
					  	</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
