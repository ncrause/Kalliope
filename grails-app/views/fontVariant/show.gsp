
<%@ page import="kalliope.FontVariant" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'fontVariant.label', default: 'FontVariant')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-fontVariant" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-fontVariant" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list fontVariant">
			
				<g:if test="${fontVariantInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="fontVariant.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${fontVariantInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.font}">
				<li class="fieldcontain">
					<span id="font-label" class="property-label"><g:message code="fontVariant.font.label" default="Font" /></span>
					
						<span class="property-value" aria-labelledby="font-label"><g:link controller="font" action="show" id="${fontVariantInstance?.font?.id}">${fontVariantInstance?.font?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.italic}">
				<li class="fieldcontain">
					<span id="italic-label" class="property-label"><g:message code="fontVariant.italic.label" default="Italic" /></span>
					
						<span class="property-value" aria-labelledby="italic-label"><g:formatBoolean boolean="${fontVariantInstance?.italic}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="fontVariant.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${fontVariantInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.originalFilename}">
				<li class="fieldcontain">
					<span id="originalFilename-label" class="property-label"><g:message code="fontVariant.originalFilename.label" default="Original Filename" /></span>
					
						<span class="property-value" aria-labelledby="originalFilename-label"><g:fieldValue bean="${fontVariantInstance}" field="originalFilename"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.stretch}">
				<li class="fieldcontain">
					<span id="stretch-label" class="property-label"><g:message code="fontVariant.stretch.label" default="Stretch" /></span>
					
						<span class="property-value" aria-labelledby="stretch-label"><g:fieldValue bean="${fontVariantInstance}" field="stretch"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${fontVariantInstance?.weight}">
				<li class="fieldcontain">
					<span id="weight-label" class="property-label"><g:message code="fontVariant.weight.label" default="Weight" /></span>
					
						<span class="property-value" aria-labelledby="weight-label"><g:fieldValue bean="${fontVariantInstance}" field="weight"/></span>
					
				</li>
				</g:if>
				
				<li class="fieldcontain">
					<span id="sample-label" class="property-label"><g:message code="fontVariant.sample.label" default="Sample" /></span>
					
					<span class="property-value">
						<style type="text/css">
							${raw(fontVariantInstance?.getFontFace())}
						</style>
						
						<textarea style="${raw(fontVariantInstance?.getInlineCSS())}; font-size: 150%; width: 100%; height: 200px">A mad boxer shot a quick, gloved jab to the jaw of his dizzy opponent.
Forsaking monastic tradition, twelve jovial friars gave up their vocation for a questionable existence on the flying trapeze.
An inspired calligrapher can create pages of beauty using stick ink, quill, brush, pick-axe, buzz saw, or even strawberry jam.</textarea>
					</span>
				</li>
			
			</ol>
			<g:form url="[resource:fontVariantInstance, action:'delete']" method="DELETE">
				<fieldset class="buttons">
					<g:link class="edit" action="edit" resource="${fontVariantInstance}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
