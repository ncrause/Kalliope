<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
	<head>
		<g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
		<meta name="layout" content="main" />
		<title><g:message code="browser.title"/></title>
	</head>
	
	<body>
		<div class="container-fluid">
			<div class="row">
				<g:each in="${fontList}" var="font">
					<div class="col">
						<g:each in="${font.variants}" var="variant">
							<style type="text/css">
								${raw(fontVariants.getFontFace(variant))}
							</style>

							<span style="${variant.inlineCSS}; font-size: 1.25rem" class="badge badge-light text-dark border m-1">${variant as String}</span>
						</g:each>
					</div>
				</g:each>
			</div>
		</div>
	</body>
</html>
