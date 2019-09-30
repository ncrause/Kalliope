<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
        <g:set var="variantName" value="${message(code: 'fontVariant.label', default: 'Font Variant')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
        <meta name="layout" content="main" />
        <title><g:message code="browser.title"/>: <g:message code="default.show.label" args="[entityName]" /></title>
    </head>
	
	<body>
		<div class="container-fluid">
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
					
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			
			<div class="row">
				<div class="col-md-4">
					<f:with bean="font">
						<f:display property="name"/>
						<f:display property="category"/>
					</f:with>
				</div>
				
				<div class="col-md-8">
					<label><g:message code="fontVariants.label"/></label>
					<div><g:render template="/shared/fontVariants" bean="${font}"/></div>
					<g:link namespace="admin" controller="font-variant" action="create" params="['font.id': font.id]" class="btn btn-primary"><i class="fas fa-plus-circle"></i> <g:message code="default.add.label" args="[variantName]" /></g:link>
				</div>
			</div>
			
			<g:form resource="${font}" method="DELETE">
				<nav class="btn-toolbar" role="toolbar">
					<g:link action="edit" resource="${font}" class="btn btn-primary"><i class="fas fa-pencil-alt"></i> <g:message code="default.button.edit.label" default="Edit" /></g:link>
					&emsp;
					<button type="submit" class="btn btn-danger" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><i class="fas fa-trash-alt"></i> <g:message code="default.button.delete.label" default="Delete" /></button>
				</nav>
			</g:form>
		</div>
	</body>
</html>
