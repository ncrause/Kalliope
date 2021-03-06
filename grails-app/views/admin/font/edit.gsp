<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
        <meta name="layout" content="main" />
        <title><g:message code="browser.title"/>: <g:message code="default.create.label" args="[entityName]" /></title>
    </head>
	
	<body>
		<ul class="nav nav-pills">
			<li class="nav-item">
				<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
			</li>

			<li class="nav-item">
				<g:link action="index" class="nav-link"><i class="fas fa-list"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
			</li>
		</ul>

		<g:hasErrors bean="${font}">
			<ul class="alert alert-danger">
				<g:eachError bean="${font}" var="error">
					<li><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>

		<h1><g:message code="default.edit.label" args="[entityName]" /></h1>

		<div class="row justify-content-center">
			<div class="col-md-4">
				<g:form resource="${font}" method="PUT">
					<fieldset>
						<f:all bean="font" order="name, category" />
					</fieldset>

					<fieldset>
						<button class="btn btn-primary"><g:message code="default.button.update.label" args="[entityName]" /></button>
					</fieldset>
				</g:form>
			</div>
		</div>
	</body>
</html>
