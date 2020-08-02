<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		
        <meta name="layout" content="main" />
        <title><g:message code="browser.title"/>: <g:message code="default.list.label" args="[entityName]" /></title>
    </head>
	
    <body>
		<div class="container-fluid">
			<ul class="nav nav-pills">
				<li class="nav-item">
					<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
				</li>

				<li class="nav-item">
					<g:link action="create" class="nav-link"><i class="fas fa-plus-square"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
				</li>
			</ul>
			
			<div role="main">
				<h2><g:message code="default.list.label" args="[entityName]" /></h2>
				
				<div class="table-responsive">
					<table class="table table-striped">
						<thead>
							<tr>
								<g:sortableColumn namespace="admin" property="name" title="Name" />
								<th>Actions</th>
							</tr>
						</thead>
						
						<tbody>
							<g:each in="${userList}" var="user">
								<tr>
									<td>${user.name}</td>
									<td>
										<g:form resource="${user}" id="${user.ident()}" method="DELETE">
											<nav class="btn-toolbar" role="toolbar">
												<g:link action="edit" id="${user.ident()}" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i> <g:message code="default.button.edit.label" default="Edit" /></g:link>
												&emsp;
												<button type="submit" class="btn btn-danger btn-sm" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"><i class="fas fa-trash-alt"></i> <g:message code="default.button.delete.label" default="Delete" /></button>
											</nav>
										</g:form>
									</td>
								</tr>
							</g:each>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</body>
</html>
