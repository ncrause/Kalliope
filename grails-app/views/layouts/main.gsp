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

	<body itemscope itemtype="https://schema.org/WebApplication">
		<meta itemprop="browserRequirements" content="HTML5+CSS3" />
		<meta itemprop="applicationCategory" content="Font, Conversion" />
		<meta itemprop="operatingSystem" content="Any GUI-enabled operating system with an HTML5 capable browser." />
		
		<header id="site-header">
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
				<a class="navbar-brand" href="/">
					<asset:image src="kalliope.jpg" alt="Kalliope Logo" height="80" itemprop="image"/>
					<span itemprop="name"><g:message code="browser.title"/></span> <small itemprop="softwareVersion"><g:message code="version.label" /></small>
				</a>

				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#main-menu-contents">
					<span class="navbar-toggler-icon"></span>
				</button>

				<div class="collapse navbar-collapse" id="main-menu-contents">
					<ul class="navbar-nav mr-auto">
						<g:if test="${session.user}">
							<li class="nav-item">
								<a class="nav-link" href="${createLink(namespace: "admin", controller: "font", action: "index")}"><g:message code="navbar.fonts.label"/></a>
							</li>
							
							<li class="nav-item">
								<a class="nav-link" href="${createLink(namespace: "admin", controller: "user", action: "index")}"><g:message code="navbar.users.label"/></a>
							</li>
						</g:if>
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
		
		<footer id="site-footer" class="container-fluid bg-light py-4 border-top text-center">
			<div itemprop="author" itemscope itemtype="https://schema.org/Person">
				&copy; ${java.time.Year.now().value} <a href="http://nathan.crause.name" target="_blank" itemprop="url"><span itemprop="givenName">Nathan</span> <span itemprop="familyname">Crause</span></a>
				<meta itemprop="email" content="nathan@crause.name" />
			</div>
			
			<div>
					<a href="https://github.com/ncrause/Kalliope" target="_blank">This software</a> is released under the <a href="https://www.gnu.org/licenses/gpl-3.0.html" target="_blank" itemprop="license">GPLv3 license</a>.
			</div>
			
			<div id="credits" class="row small text-muted my-4">
				<div class="col">
					<div>Icons made by <a href="https://www.flaticon.com/authors/pixelmeetup" title="Pixelmeetup">Pixelmeetup</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
				</div>
			</div>
			
			<div id="powered-by">
				<h5>Powered By</h5>
				
				<div class="row align-items-center justify-content-center">
					<div class="col-6 col-sm-3 col-md-2">
						<a href="http://groovy-lang.org" target="_blank" class="clean" title="Apache Groovy Programming Language" data-toggle="tooltip">
							<img src="${resource(dir: "images", file: "Groovy-logo.svg")}" class="w-100">
						</a>
					</div>
					
					<div class="col-6 col-sm-3 col-md-2">
						<a href="https://grails.org" target="_blank" class="clean" title="Grails Web Framework" data-toggle="tooltip">
							<img src="${resource(dir: "images", file: "grails.svg")}" class="w-100">
						</a>
					</div>
					
					<%--
					Well, even though I started off using Derby with the full
					intention of using it as much as possible, in the end the
					need to explicitly define the SQL type for blobs makes it
					incompatible with being completely database neutral (since
					Hibernate generates VARCHAR for Derby, but "blob" doesn't
					work for PostgreSQL)
					<div class="col-6 col-sm-3 col-md-2">
						<a href="https://db.apache.org/derby/" target="_blank" class="clean" title="Apache Derby SQL Database" data-toggle="tooltip">
							<img src="${resource(dir: "images", file: "pb-derby_2.png")}" class="w-100">
						</a>
					</div>
					--%>
					
					<div class="col-6 col-sm-3 col-md-2">
						<a href="https://fontforge.github.io/en-US/" target="_blank" class="clean" title="FontForge Font Editor" data-toggle="tooltip">
							<img src="${resource(dir: "images", file: "FontForge_Logo_2015.svg")}" class="w-100">
						</a>
					</div>
					
					<div class="col-6 col-sm-3 col-md-2">
						<a href="http://tomcat.apache.org" target="_blank" class="clean" title="Apache Tomcat Java Servlet and HTTP Server" data-toggle="tooltip">
							<%--
							I've included "rounded-circle" below so the white
							background of the logo isn't so jarring.
							--%>
							<img src="${resource(dir: "images", file: "pb-tomcat.jpg")}" class="w-100 rounded-circle">
						</a>
					</div>
					
					<%--
					NOTE:
					=====
					We should ideally have Bootstap listed under here, but they
					do not have an actual logo image, but require you to
					construct it using HTML. The problem with that is they
					explicitly require Helvetica Neue Bold, which isn't a
					"free"/license-free font, but relies explicitly on the
					font being available to the operating system. This isn't
					something I could possibly guarantee, and since hosting
					such a font opens me up to legal liability, I simply
					cannot give credit.
					--%>
					
					<div class="col-6 col-sm-3 col-md-2">
						<a href="https://jquery.com" target="_blank" class="clean" title="jQuery JavaScript Library" data-toggle="tooltip">
							<%-- 
							note the inclusion of p-2 in the class - this 
							is required by jQuery's spacing guidelines:
							https://brand.jquery.org/logos/ 
							--%>
							<img src="${resource(dir: "images", file: "JQuery-Logo.svg")}" class="w-100 p-2">
						</a>
					</div>
				</div>
			</div>
		</footer>
		
		<div id="spinner">
			<i class="fas fa-spinner fa-spin fa-5x fa-fw"></i>
		</div>
	</body>
</html>
