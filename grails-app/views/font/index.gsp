
<%@ page import="kalliope.Font" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
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
			<table>
			<thead>
					<tr>
					
						<g:sortableColumn property="name" title="${message(code: 'font.name.label', default: 'Name')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'font.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'font.lastUpdated.label', default: 'Last Updated')}" />
						
						<th>Variants</th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${fontInstanceList}" status="i" var="fontInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${fontInstance.id}">${fieldValue(bean: fontInstance, field: "name")}</g:link></td>
					
						<td><g:formatDate date="${fontInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${fontInstance.lastUpdated}" /></td>
					
						<td>
						<g:each in="${fontInstance.variants}" var="v">
						<style type="text/css">
							${raw(v.getFontFace())}
						</style>
						<span style="${raw(v.getInlineCSS())}">Sample</span>
						</g:each>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${fontInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
