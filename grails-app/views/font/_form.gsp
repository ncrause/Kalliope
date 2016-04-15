<%@ page import="kalliope.Font" %>



<div class="fieldcontain ${hasErrors(bean: fontInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="font.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${fontInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: fontInstance, field: 'variants', 'error')} ">
	<label for="variants">
		<g:message code="font.variants.label" default="Variants" />
		
	</label>
	
<ul class="one-to-many">
<g:each in="${fontInstance?.variants?}" var="v">
    <li><g:link controller="fontVariant" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
</g:each>
<li class="add">
<g:link controller="fontVariant" action="create" params="['font.id': fontInstance?.id]">${message(code: 'default.add.label', args: [message(code: 'fontVariant.label', default: 'FontVariant')])}</g:link>
</li>
</ul>


</div>

