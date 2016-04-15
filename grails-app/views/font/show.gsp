
<%@ page import="kalliope.Font" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'font.label', default: 'Font')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-font" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-font" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list font">
			
				<g:if test="${fontInstance?.name}">
				<li class="fieldcontain">
					<span id="name-label" class="property-label"><g:message code="font.name.label" default="Name" /></span>
					
						<span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${fontInstance}" field="name"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="font.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${fontInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="font.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${fontInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<%-- g:if test="${fontInstance?.variants}">
				<li class="fieldcontain">
					<span id="variants-label" class="property-label"><g:message code="font.variants.label" default="Variants" /></span>
					
						<g:each in="${fontInstance.variants}" var="v">
						<span class="property-value" aria-labelledby="variants-label"><g:link controller="fontVariant" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if --%>
			
				<g:if test="${fontInstance?.variants}">
				<li class="fieldcontain">
					<span id="variants-label" class="property-label"><g:message code="font.variants.label" default="Variants" /></span>
					
					<span class="property-value" aria-labelledby="variants-label">
						<div class="list-group">
							<g:each in="${fontInstance.variants}" var="v">
							<style type="text/css">
								${raw(v.getFontFace())}
							</style>
							<g:link controller="fontVariant" action="show" id="${v.id}" class="list-group-item" style="${raw(v.getInlineCSS())}">${v?.encodeAsHTML()}</g:link>
							</g:each>
						</div>
					</span>
				</li>
				</g:if>
			
			</ol>
			<g:form url="[resource:fontInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${fontInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
