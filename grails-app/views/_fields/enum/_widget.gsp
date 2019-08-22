<g:select name="${prefix}${property}" from="${type?.values()}" 
		keys="${type?.values()*.name()}" value="${value}" 
		valueMessagePrefix="${type.name}"
		data-type-name="${type.name}"
		class="form-control" />