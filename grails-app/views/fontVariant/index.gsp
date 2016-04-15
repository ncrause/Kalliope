
<%@ page import="kalliope.FontVariant" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fontVariant.label', default: 'FontVariant')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-fontVariant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-fontVariant" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>

						<th><g:message code="fontVariant.font.label" default="Font" /></th>

						<g:sortableColumn property="originalFilename" title="${message(code: 'fontVariant.originalFilename.label', default: 'Original Filename')}" />

						<g:sortableColumn property="weight" title="${message(code: 'fontVariant.weight.label', default: 'Weight')}" />

						<g:sortableColumn property="stretch" title="${message(code: 'fontVariant.stretch.label', default: 'Stretch')}" />

						<g:sortableColumn property="italic" title="${message(code: 'fontVariant.italic.label', default: 'Italic')}" />

						<th><g:message code="fontVariant.sample.label" default="Sample" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fontVariantInstanceList}" status="i" var="fontVariantInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					

						<td><g:link action="show" id="${fontVariantInstance.id}">${fieldValue(bean: fontVariantInstance, field: "font")}</g:link></td>

						<td>${fieldValue(bean: fontVariantInstance, field: 'originalFilename')}</td>

						<td>${fieldValue(bean: fontVariantInstance, field: "weight")}</td>

						<td>${fieldValue(bean: fontVariantInstance, field: "stretch")}</td>

						<td><g:formatBoolean boolean="${fontVariantInstance.italic}" /></td>

						<td>
							<style type="text/css">
								${raw(fontVariantInstance.getFontFace())}
							</style>
							<span style="${raw(fontVariantInstance.getInlineCSS())}">Pack my box with five dozen liquor jugs.</span>
						</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fontVariantInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
