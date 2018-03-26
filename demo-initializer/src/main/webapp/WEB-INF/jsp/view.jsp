<%@ page contentType="text/html" isELIgnored="false" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/ajaxcall.js"></script>

<portlet:defineObjects />
<portlet:actionURL var="checkInitialized">
	<portlet:param name="action" value="checkInit" />
</portlet:actionURL>

<div data-url="${checkInitialized}" data-service="waitUntilInitialized">
	<center>
		<h1>
			<img src="${pageContext.request.contextPath}/images/spinner.gif" alt="" > <op:translate key="WAIT_TITLE" />

		</h1>
	
		<p>
			<op:translate key="WAIT" />
		</p>

	
	</center>
</div>