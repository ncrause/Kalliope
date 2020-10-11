<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'htmlSnippet.label', default: 'HTML Snippet')}" />
		
        <meta name="layout" content="main" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
		
		<script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.18.3/highlight.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/9.18.3/styles/default.min.css" />
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

		<g:hasErrors bean="${htmlSnippet}">
			<ul class="alert alert-danger">
				<g:eachError bean="${htmlSnippet}" var="error">
					<li><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>
				
		<h1><g:message code="default.show.label" args="[entityName]" /></h1>
		
        <div id="show-htmlSnippet" role="main">
			<f:with bean="htmlSnippet">
				<f:display property="location"/>
				<f:display property="description"/>
				<f:display property="content"/>
			</f:with>
			
            <g:form namespace="admin" resource="${this.htmlSnippet}" method="DELETE">
                <fieldset class="buttons">
                    <g:link namespace="admin" action="edit" resource="${this.htmlSnippet}" class="btn btn-primary"><i class="fas fa-pen"></i> <g:message code="default.button.edit.label" default="Edit" /></g:link>
					<button class="btn btn-danger" type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><i class="far fa-trash-alt"></i> <g:message code="default.button.delete.label" default="Delete" /></button>
                </fieldset>
            </g:form>
        </div>
		
		<script>hljs.initHighlightingOnLoad();</script>
    </body>
</html>
