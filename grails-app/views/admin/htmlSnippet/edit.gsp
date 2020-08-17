<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'htmlSnippet.label', default: 'HtmlSnippet')}" />
		
        <meta name="layout" content="main" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
		
		<g:render template="/shared/codeEditorHead"/>
    </head>
	
    <body>
		<ul class="nav nav-pills">
			<li class="nav-item">
				<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
			</li>

			<li class="nav-item">
				<g:link action="index" class="nav-link"><i class="fas fa-list"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
			</li>

			<li class="nav-item">
				<g:link namespace="admin" action="show" resource="${htmlSnippet}" class="nav-link"><i class="far fa-eye"></i> <g:message code="default.show.label" args="[entityName]" /></g:link>
			</li>
		</ul>

		<g:hasErrors bean="${htmlSnippet}">
			<ul class="alert alert-danger">
				<g:eachError bean="${htmlSnippet}" var="error">
					<li><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
				
		<h1><g:message code="default.edit.label" args="[entityName]" /></h1>
		
        <div id="edit-htmlSnippet" role="main">
            <g:form namespace="admin" resource="${this.htmlSnippet}" method="PUT">
                <g:hiddenField name="version" value="${this.htmlSnippet?.version}" />
				<g:hiddenField name="position" value="${htmlSnippet?.position}"/>
				
                <fieldset class="form">
                    <f:all bean="htmlSnippet" order="location, description, content"/>
                </fieldset>
				
                <fieldset class="buttons">
                    <input type="submit" class="btn btn-primary" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
		
		<g:render template="/shared/codeEditorFoot"/>
    </body>
</html>
