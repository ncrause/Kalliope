<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'fontVariant.label', default: 'Font Variant')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
        <meta name="layout" content="main" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
	
    <body>
		<ul class="nav nav-pills">
			<li class="nav-item">
				<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
			</li>

			<li class="nav-item">
				<g:link action="create" params="['font.id': params.font.id]" class="nav-link"><i class="fas fa-plus-square"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
			</li>
		</ul>

		<div role="main">
			<h2><g:message code="font.variants.list.label" args="[font.name, entityName]" /></h2>

			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>Name</th>
							<th>Italic?</th>
							<th>Weight</th>
							<th>Stretch</th>
							<th>Sample</th>
						</tr>
					</thead>

					<tbody>
						<g:each in="${fontVariantList}" var="fontVariant">
							<tr>
								<td class="text-nowrap"><g:link action="show" id="${fontVariant.ident()}">${fontVariant}</g:link></td>
								<td class="text-center">
									<g:if test="${fontVariant.italic}">
										<i class="far fa-check-circle text-success"></i>
									</g:if>
									<g:else>
										<i class="far fa-times-circle text-danger"></i>
									</g:else>
								</td>
								<td>${fontVariant.weight}</td>
								<td>${fontVariant.stretch.cssKeyword()}</td>
								<td>
									<style type="text/css">
										${raw(fontVariants.getFontFace(fontVariant))}
									</style>

									<span style="${fontVariant.inlineCSS}">
										The quick brown fox jumps over the lazy dog.
									</span>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>

			<g:if test="${fontVariantCount > params.max}">
				<nav aria-label="Font pagination">
					<bootstrap:paginate total="${fontVariantCount}" params="['font.id': font.id]" />
				</nav>
			</g:if>
		</div>
    </body>
</html>