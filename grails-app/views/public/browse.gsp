<%@ page contentType="text/html;charset=UTF-8" %>
<!doctype html>
<html>
	<head>
		<g:set var="fontVariantService" bean="fontVariantService" />
		
		<meta name="layout" content="main" />
		<title><g:message code="public.browse.title"/></title>
		
		<asset:javascript src="browse" />
	</head>
	
	<body>
		<div class="container-fluid">
			<form class="card" method="post">
				<h4 class="card-header"><g:message code="public.browse.search.label"/></h4>
				
				<div class="card-body">
					<div class="row">
						<div class="form-group col-4">
							<label><g:message code="public.browse.category.label"/></label>
							<g:select name="category" class="form-control" from="${categories}" value="${category}" noSelection="${['null': 'Select One...']}" valueMessagePrefix="kalliope.Font$Category"/>
						</div>
						
						<div class="form-group col-4">
							<label><g:message code="public.browse.weight.label"/></label>
							<g:select name="weight" class="form-control" from="${weights}" value="${weight}" valueMessagePrefix="kalliope.FontVariant$Weight"/>
							<small class="form-text text-muted"><g:message code="public.browse.weight.message"/></small>
						</div>
						
						<div class="form-group col-4">
							<label><g:message code="public.browse.stretch.label"/></label>
							<g:select name="stretch" class="form-control" from="${stretches}" value="${stretch}" valueMessagePrefix="kalliope.FontVariant$Stretch"/>
							<small class="form-text text-muted"><g:message code="public.browse.stretch.message"/></small>
						</div>
						
						<div class="form-group col-4">
							<label><g:message code="public.browse.italic.label"/></label>
							<g:select name="italic" class="form-control" from="${['Exclude italic variants': 0, 'Only search for italic variants': 1, 'No preference': -1]}" value="${italic}" optionKey="value" optionValue="key"/>
						</div>
						
						<div class="form-group col-3">
							<div class="form-check">
								<g:checkBox name="enableAdvance" value="true" checked="${enableAdvance}" class="form-check-input"/>
								<label class="form-check-label" for="enableAdvance"><g:message code="public.browse.enableAdvance.label"/></label>
							</div>
						</div>
						
						<div class="form-group col-5">
							<label><g:message code="public.browse.advance.label"/></label>
							<g:field name="advance" type="range" min="${minAdvance}" max="${maxAdvance}" value="${advance}" class="form-range" disabled="${!enableAdvance}"/>
							<small class="form-text text-muted"><g:message code="public.browse.advance.message"/></small>
						</div>
					</div>
				</div>
				
				<div class="card-footer text-right">
					<button type="submit" class="btn btn-primary"><g:message code="public.browse.search.label"/></button>
				</div>
			</form>
			
			<div class="table-responsive mt-4">
				<table class="table table-striped">
					<tbody>
						<g:each in="${fontVariants}" var="variant">
							<tr>
								<td>
									<div class="small text-muted">${variant as String}</div>
									
									<style type="text/css">
										${raw(fontVariantService.getFontFace(variant))}
									</style>
									
									<div class="display-6" style="${variant.inlineCSS}"><g:message code="public.pangram.message"/></div>
								</td>
								
								<td>
									<div class="input-group">
										<input id="url-${variant.id}" type="url" class="form-control" readonly value="${createLink(controller: 'public', action: 'css', id: variant.font.ident(), absolute: true)}">
										<div class="input-group-append">
											<button class="btn btn-secondary clipboardable" data-clipboard-target="#url-${variant.id}" type="button"><i class="fas fa-clipboard"></i></button>
										</div>
									</div>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>
		</div>
	</body>
</html>
