<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.osivia.org/jsp/taglib/osivia-portal" prefix="op" %>


<c:set var="title"><op:translate key="SEARCH_TITLE" /></c:set>
<c:set var="placeholder"><op:translate key="SEARCH_PLACEHOLDER" /></c:set>


<form action="${requestScope['osivia.search.url']}" method="post" onsubmit="onsubmitSearch(this)" class="form-inline" role="search">
    <div class="form-group">
        <label for="search-input" class="sr-only">${placeholder}</label>
        <div class="input-group input-group-sm">
            <input id="search-input" type="text" name="keywords" class="form-control" placeholder="${placeholder}">
            <span class="input-group-btn">
                <button type="submit" class="btn btn-default" title="${title}" data-toggle="tooltip" data-placement="bottom">
                    <span class="glyphicons halflings search"></span>
                </button>
            </span>
        </div>
    </div>
           
    <div class="form-group">
        <a href="${requestScope['demo.advancedSearch.url']}" class="btn btn-default btn-sm"><op:translate key="ADVANCED_SEARCH" /></a>
    </div>
</form>
