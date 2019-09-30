<div class="fieldcontain form-group ${required ? "required" : ""}">
	<div class="custom-control custom-checkbox">
		<f:widget property="${property}"/>
		<label for="${prefix}${property}">${label}</label>
	</div>
	
	<g:if test="${help}">
		<small class="form-text text-muted">${help}</small>
	</g:if>
	<g:if test="${invalid}">
		<ul class="invalid-feedback">
			<g:each in="${errors}" var="error">
				<li><g:message error="${error}"/></li>
			</g:each>
		</ul>
	</g:if>
</div>
