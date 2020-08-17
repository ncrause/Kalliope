<script>
	jQuery(function($) {
		var $elements = $("textarea.html-source");
		
		$elements.each(function(index, element) {
			let $element = $(element);
			
			// If there is no explicit ID, just create one
			if ($element.attr("id") == null) {
				$element.attr("id", UUID.getV4().hexString);
			}
			
			let editor = CodeMirror.fromTextArea(document.getElementById($element.attr("id")), {
				lineNumbers: true,
				mode: "htmlmixed",
				matchBrackets: true,
				autoCloseBrackets: true,
				tabsize: 4,
				indentUnit: 4,
				indentWithTabs: true
			});
			
			editor.setSize(null, 200);
		});
	});
</script>
