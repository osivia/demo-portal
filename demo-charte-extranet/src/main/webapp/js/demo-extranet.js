$JQry(function() {
	$JQry(".btn-file input[type='file']").change(function() {
		var fileName = this.files[0].name;

		var spanFileName = this.closest(".media-middle").previousElementSibling.querySelector("span.text-muted");

		spanFileName.innerHTML = fileName;
	});
});