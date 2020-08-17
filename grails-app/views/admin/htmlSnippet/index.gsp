<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'htmlSnippet.label', default: 'HTML Snippet')}" />
		
        <meta name="layout" content="main" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
	
    <body>
		<ul class="nav nav-pills">
			<li class="nav-item">
				<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
			</li>

			<li class="nav-item">
				<g:link action="create" class="nav-link"><i class="fas fa-plus-square"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
			</li>
		</ul>
		
		<g:each in="${kalliope.PageLocation.values()}" var="location">
			<g:set var="locationName" value="${message(code: "${location.getClass().name}.${location}", default: 'Unbeknownst')}" />
			<h2><g:message code="htmlSnippet.location.list.label" args="[locationName, entityName]" /></h2>
			
			<div class="table-responsive">
				<table class="table table-striped table-hover">
					<thead>
						<tr>
							<th><i class="fas fa-sort"></i></th>
							<th><g:message code="default.id.label" /></th>
							<th><g:message code="default.description.label" /></th>
							<th><g:message code="default.dateCreated.label" /></th>
							<th><g:message code="default.lastUpdated.label" /></th>
							<th><i class="fas fa-tools"></i></th>
						</tr>
					</thead>
					
					<tbody>
						<g:each in="${locationSnippets[location.name()]}" var="snippet">
							<tr id="snippet-${snippet.id}}" data-snippet-id="${snippet.id}">
								<td><i class="fas fa-ellipsis-v"></i></td>
								<td>${snippet.id}</td>
								<td>${snippet.description}</td>
								<td><g:formatDate date="${snippet.dateCreated}" /></td>
								<td><g:formatDate date="${snippet.lastUpdated}" /></td>
								<td>
									<nav class="btn-toolbar" role="toolbar">
										<g:link namespace="admin" action="show" resource="${snippet}" class="btn btn-sm btn-success"><i class="far fa-eye"></i></g:link>
										<g:link namespace="admin" action="edit" resource="${snippet}" class="btn btn-sm btn-primary ml-1"><i class="fas fa-pen"></i></g:link>
									</nav>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
			
			<hr>
		</g:each>
    </body>
</html>