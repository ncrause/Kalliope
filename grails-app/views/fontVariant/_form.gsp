<%@ page import="kalliope.FontVariant" %>




<div class="fieldcontain ${hasErrors(bean: fontVariantInstance, field: 'font', 'error')} required">
	<label for="font">
		<g:message code="fontVariant.font.label" default="Font" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="font" name="font.id" from="${kalliope.Font.list()}" optionKey="id" required="" value="${fontVariantInstance?.font?.id}" class="many-to-one"/>

</div>

<div class="fieldcontain ${hasErrors(bean: fontVariantInstance, field: 'italic', 'error')} ">
	<label for="italic">
		<g:message code="fontVariant.italic.label" default="Italic" />
		
	</label>
	<g:checkBox name="italic" value="${fontVariantInstance?.italic}" />

</div>

<div class="fieldcontain ${hasErrors(bean: fontVariantInstance, field: 'stretch', 'error')} required">
	<label for="stretch">
		<g:message code="fontVariant.stretch.label" default="Stretch" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="stretch" from="${kalliope.FontVariant$Stretch?.values()}" keys="${kalliope.FontVariant$Stretch.values()*.name()}" required="" value="${fontVariantInstance?.stretch?.name()}" />

</div>

<div class="fieldcontain ${hasErrors(bean: fontVariantInstance, field: 'weight', 'error')} required">
	<label for="weight">
		<g:message code="fontVariant.weight.label" default="Weight" />
		<span class="required-indicator">*</span>
	</label>
	<g:select name="weight" from="${kalliope.FontVariant$Weight?.values()}" keys="${kalliope.FontVariant$Weight.values()*.name()}" required="" value="${fontVariantInstance?.weight?.name()}" />

</div>


<div class="fieldcontain ${hasErrors(bean: fontVariantInstance, field: 'original', 'error')} required">
	<label for="original">
		<g:message code="fontVariant.original.label" default="Original" />
		<span class="required-indicator">*</span>
	</label>
	<input type="file" id="original" name="original" />

</div>
