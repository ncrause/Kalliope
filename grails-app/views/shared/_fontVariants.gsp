<g:each in="${font.variants}" var="variant">
	<style type="text/css">
		${raw(fontVariants.getFontFace(variant))}
	</style>

	<span style="${variant.inlineCSS}" class="badge badge-light text-dark border m-1">
		<g:link namespace="admin" controller="font-variant" action="show" id="${variant.id}">${variant as String}</g:link>
	</span>
</g:each>
