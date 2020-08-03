<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
		
		<title><g:message code="browser.title.login"/></title>
	</head>
	
	<body>
		<div class="row align-items-center justify-content-center">
			<div class="col-md-5">
				<g:form name="login" action="login" useToken="true">
					<div class="card">
						<h5 class="card-header"><g:message code="session.login.label"/></h5>

						<div class="card-body">
							<div class="form-group">
								<label class="control-label"><g:message code="session.username.label"/>:</label>
								<g:textField name="username" class="form-control" required="required"/>
							</div>

							<div class="form-group">
								<label class="control-label"><g:message code="session.password.label"/>:</label>
								<g:passwordField name="password" class="form-control" required="required"/>
							</div>
						</div>

						<div class="card-footer text-right">
							<button type="submit" class="btn btn-primary"><g:message code="session.submit.label"/></button>
						</div>
					</div>
				</g:form>
			</div>
		</div>
	</body>
</html>
