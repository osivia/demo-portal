<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>

<%@ page isELIgnored="false" %>

<portlet:actionURL var="submitUrl" name="submit" />

<div class="row">
	<div class="col-sm-12">
		<h3 class="head-title">${title}</h3>
	</div>
</div>
<div class="row no-ajax-link">
    <c:if test="${not empty visuelUrl}">
    	<div class="col-sm-12">
    		<a class="thumbnail no-margin-bottom no-ajax-link" href="${visuelUrl}" data-fancybox="gallery" data-caption="${visuelFilename}" data-type="image">
    		  <img src="${visuelUrl}" alt="${visuelFilename}">
    		</a>
    	</div>
    </c:if>
	
	<div class="col-sm-12">
		<div class="tabs-panel">
			<ul class="nav nav-tabs">
				<li class="active">
					<a href="#tab-description" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_DESCRIPTION" /></a>
				</li>
				<c:if test="${not empty files}">
					<li><a href="#tab-docs" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_DOCUMENTS" /></a></li>
				</c:if>
				<li><a href="#tab-support" data-toggle="tab" class="no-ajax-link"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT" /></a></li>
			</ul>
			<div class="tab-content clearfix">
				<div class="tab-pane active" id="tab-description">
					${description}
				</div>
				<c:if test="${not empty files}">
					<div class="tab-pane" id="tab-docs">
						<div class="table">
							<ul class="list-unstyled">
								<c:forEach items="${files}" var="file">
									<li class="table-row">
										<div class="row"> 
											<div class="col-sm-6">
												${file.documentTitle}
											</div>
											<div class="col-sm-6">
												<i class="${file.icon}"></i>
												<a href="${file.url}" class="no-ajax-link">${file.fileName}</a>
											</div>
										</div>
									</li>
								</c:forEach>
							</ul>
						</div>
					</div>
				</c:if>
				<div class="tab-pane" id="tab-support">
					<form action="${submitUrl}" class="form-horizontal" method="post" enctype="multipart/form-data" role="form" >
						<ul class="list-unstyled">
							<li class="form-group required">
								<label for="supportText" class="col-sm-3 col-lg-2 control-label"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT_TEXT" /></label>
								<div class="col-sm-9 col-lg-10">
									<textarea class="form-control tinymce tinymce-default" name="supportText">
									</textarea>
								</div>
							</li>
							<li class="form-group">
								<label for="supportText" class="col-sm-3 col-lg-2 control-label"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT_FILE" /></label>
								<div class="col-sm-9 col-lg-10">
									<div class="media-body">
										<p class="form-control-static">
											<span class="text-muted"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT_NO_FILE" /></span>
										</p>
									</div>
									<div class="media-right media-middle">
										<div class="text-nowrap">
			                                <label class="btn btn-default btn-sm btn-file">
			                                    <i class="halflings halflings-folder-open"></i>
			                                    <span class="hidden-xs">&nbsp;<op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT_FILE_UPLOAD" /></span>
			                                    <input type="file" name="supportFile">
			                                </label>
			                            </div>
									</div>
								</div>
							</li>
						</ul>
						<input type="hidden" name="produit" value="${produitWebid}">
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9 col-lg-offset-2 col-lg-10">
								<button type="submit" class="btn btn-primary"><op:translate key="FRAGMENT_PRODUCT_RECORD_SUPPORT_SEND" /></button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
