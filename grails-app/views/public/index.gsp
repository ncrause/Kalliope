<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
		<meta name="layout" content="main" />
		<title><g:message code="browser.title"/></title>
		
		<link type="text/css" rel="stylesheet" href="${createLink(action: "css", id: scriptFont.id)}"/>
		<link type="text/css" rel="stylesheet" href="${createLink(action: "css", id: serifFont.id)}"/>
		
		<style>
			.text-pinyon {
				${raw(scriptFont.variants[0].inlineCSS)};
			}
			
			.text-bree {
				${raw(serifFont.variants[0].inlineCSS)};
			}
		</style>
	</head>
	
	<body>
		<div class="row align-items-center justify-content-center my-5">
			<div class="col-md-6">
				<div class="jumbotron">
					<h1 class="text-pinyon display-3">Kalliope</h1>
					<h5 class="text-bree text-right">Font Server</h5>
					<p class="lead text-justify" itemprop="description"><g:message code="description.message"/></p>
					<hr>
					<p class="text-muted text-small"><g:message code="disclaimer.message"/></p>
				</div>
			</div>
		</div>

		<div class="row align-items-center justify-content-center my-5">
			<div class="col-md-5">
				<g:uploadForm action="convert" method="POST" class="card text-center">
					<div class="card-header">
						<h5 class="card-title mb-0">Upload your font here</h5>
					</div>

					<div class="card-body">
						<p>Select a TTF or OTF file from your computer.</p>
						<p class="text-muted"><small>Converted ${intradayCount} fonts today!</small></p>

						<div class="form-group">
							<g:field name="file" type="file" class="form-control" required="true"/>
						</div>

						<div class="form-group mb-0">
							<label>Category:</label>
							<g:select name="category" from="${kalliope.Font.Category.values()}" keys="${kalliope.Font.Category.values()*.name()}" valueMessagePrefix="${kalliope.Font.Category.name}" class="form-control" />
							<small class="form-text text-muted">This is used during CSS generation as the fail-over font.</small>
						</div>
					</div>

					<div class="card-footer">
						<button type="submit" class="btn btn-primary">Convert Now</button>
					</div>
				</g:uploadForm>
			</div>
		</div>

		<hr>

		<p>Last converted:</p>
		
		<div class="row align-items-center justify-content-center">
			<g:each in="${transitoryFonts}" var="font">
				<div class="col-md-3">
					<g:each in="${font.variants}" var="variant">
						<style type="text/css">
							${raw(fontVariants.getFontFace(variant))}
						</style>

						<span style="${variant.inlineCSS}; font-size: 1.25rem" class="badge badge-light text-dark border m-1">${variant as String}</span>
					</g:each>
				</div>
			</g:each>
		</div>
		
		<hr>

		<p>Public:</p>
		
		<div class="row align-items-center justify-content-center">
			<g:each in="${permanentFonts}" var="font">
				<div class="col-md-3">
					<g:each in="${font.variants}" var="variant">
						<style type="text/css">
							${raw(fontVariants.getFontFace(variant))}
						</style>

						<span style="${variant.inlineCSS}; font-size: 1.25rem" class="badge badge-light text-dark border m-1">${variant as String}</span>
					</g:each>
				</div>
			</g:each>
		</div>
		
		<p class="text-right">
			<g:link action="browse">Browse public fonts.</g:link>
		</p>
	</body>
</html>
