<!DOCTYPE html>
<html>
    <head>
        <g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<g:set var="fontVariants" bean="fontVariantService" />
		
        <meta name="layout" content="main" />
        <title><g:message code="browser.title"/>: <g:message code="default.list.label" args="[entityName]" /></title>
    </head>
	
    <body>
		<%-- 
        <a href="#list-font" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
        <div class="nav" role="navigation">
            <ul>
                <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="list-font" class="content scaffold-list" role="main">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
                <div class="message" role="status">${flash.message}</div>
            </g:if>
            <f:table collection="${fontList}" />

            <div class="pagination">
                <g:paginate total="${fontCount ?: 0}" />
            </div>
        </div>
		--%>
		
		<ul class="nav nav-pills">
			<li class="nav-item">
				<g:link controller="public" action="index" class="nav-link"><i class="fas fa-home"></i> <g:message code="default.home.label"/></g:link>
			</li>

			<li class="nav-item">
				<g:link action="create" class="nav-link"><i class="fas fa-plus-square"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
			</li>

			<li class="nav-item">
				<g:link action="quick" class="nav-link" data-toggle="tooltip" data-placement="bottom" title="${message(code: 'quick-upload.title')}"><i class="fas fa-fighter-jet"></i> <g:message code="quick-upload.label" args="[entityName]" /></g:link>
			</li>
		</ul>

		<div role="main">
			<h2><g:message code="default.list.label" args="[entityName]" /></h2>

			<div class="table-responsive">
				<table class="table table-striped">
					<thead>
						<tr>
							<g:sortableColumn namespace="admin" property="name" title="Name" />
							<th>Category</th>
							<th>Variants</th>
						</tr>
					</thead>

					<tbody>
						<g:each in="${fontList}" var="font">
							<tr>
								<td class="text-nowrap"><g:link action="show" id="${font.ident()}">${font.name}</g:link></td>
								<td class="text-nowrap">${font.category.failoverFontFace()}</td>
								<td><g:render template="/shared/fontVariants" model="[font: font]"/>
								</td>
							</tr>
						</g:each>
					</tbody>
				</table>
			</div>

			<g:if test="${fontCount > params.max}">
				<nav aria-label="Font pagination">
					<bootstrap:paginate total="${fontCount}" />
				</nav>
			</g:if>

		</div>
    </body>
</html>