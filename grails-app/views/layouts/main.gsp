<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<%-- client-side work to disable caching of pages --%>
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<%-- General --%>
		<link rel="shortcut icon" href="${resource(dir: "images", file: "favicons/favicon.ico")}">
		<g:each in="[32, 57, 76, 96, 128, 192, 228]" var="size">
			<link rel="icon" href="${resource(dir: "images", file: "favicons/${size}.png")}" sizes="${size}x${size}">
		</g:each>
		<link rel="icon" type="image/svg+xml" href="${resource(dir: "images", file: "favicons/favicon.svg")}">
		<%-- Android --%>
		<link rel="shortcut icon" href="${resource(dir: "images", file: "favicons/196.png")}" sizes="196x196">
		<%-- iOS --%>
		<g:each in="[120, 152, 180]" var="size">
			<link rel="apple-touch-icon" href="${resource(dir: "images", file: "favicons/${size}.png")}" sizes="${size}x${size}">
		</g:each>
		
		<asset:stylesheet src="application" />
		
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
		
        <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
		
		<asset:javascript src="application" />
		
		<title><g:layoutTitle default="${g.message(code: "browser.title")}"/></title>

		<g:layoutHead/>
	</head>

	<body>
		<header id="site-header">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="/">
					<asset:image src="kalliope.jpg" alt="Kalliope Logo" height="80"/>
					<g:message code="browser.title"/>
				</a>

				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu-contents">
					<span class="navbar-toggler-icon"><i class="fas fa-bars"></i></span>
				</button>

				<div class="collapse navbar-collapse" id="main-menu-contents">
					<ul class="navbar-nav mr-auto">
					</ul>
					
					<ul class="navbar-nav ml-auto">
						<li class="nav-item">
							<a class="nav-link" href="${createLink(namespace: "admin", controller: "font", action: "index")}"><g:message code="navbar.admin.label"/></a>
						</li>
					</ul>
				</div>
			</nav>
		</header>
		
		<main id="site-content" class="my-4">
			<section class="container-fluid">
				<g:each var="flashType" in="${[message: "info", success: "success", warning: "warning", error: "danger"]}">
					<g:if test="${flash[flashType.key]}">
						<div class="alert alert-${flashType.value} mb-4" role="alert">${flash[flashType.key]}</div>
					</g:if>
				</g:each>
			</section>

			<g:layoutBody />
		</main>
		
		<footer id="site-footer" class="container-fluid bg-light py-4 border-top">
			<div class="text-center">
				&copy; ${java.time.Year.now().value} Nathan Crause
			</div>
			
			<div id="credits" class="row small text-muted my-4">
				<div class="col">
					<div>Icons made by <a href="https://www.flaticon.com/authors/pixelmeetup" title="Pixelmeetup">Pixelmeetup</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
				</div>
			</div>
		</footer>
		
		<div id="spinner">
			<i class="fas fa-spinner fa-spin fa-5x fa-fw"></i>
		</div>
	</body>
</html>
