/**
 * Onsubmit search.
 * 
 * @param form search form
 */
function onsubmitSearch(form) {
	var $form = $JQry(form),
		url = $form.attr("action");
	
	form.action = url.replace("__REPLACE_KEYWORDS__", form.keywords.value);
}
