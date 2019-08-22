package kalliope

import groovy.xml.MarkupBuilder

/**
 *
 * @see https://github.com/grails/grails-gsp/blob/af8bfebd63936fe29ef7abe833386b0ed00e01f3/grails-plugin-gsp/src/main/groovy/org/grails/plugins/web/taglib/UrlMappingTagLib.groovy
 */
class BootstrapPaginationTagLib {
	static namespace = "bootstrap"
	
    static defaultEncodeAs = [taglib: "html"]
	
	def paginate = { attrs, body ->
		if (!attrs.containsKey("total")) {
			out << raw(dangerAlert("Implementation Error", "Missing required attribute 'total' in bootstrap:paginate tag."))
			
			return
		}
		
		int useOffset = params.int("offset") ?: 0
		int useMax = params.int("max") ?: 10
		int useTotal = attrs.int("total")
		int pageCount = (int) Math.ceil(useTotal.toDouble() / useMax.toDouble())
		int currentPage = (int) Math.ceil(useOffset.toDouble() / useMax.toDouble()) + 1
		String useAction = attrs.action ?: actionName
		String useController = attrs.controller ?: controllerName
		String useNamespace = attrs.namespace ?: request.namespace
		String useId = attrs.id ?: params.id
		Map useParams = attrs.params ?: params
		StringWriter writer = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(writer)
		Map baselineParams = [namespace: useNamespace, controller: useController, action: useAction, id: useId]
		
		builder.ul(class: "pagination", "data-page-count": pageCount, "data-current-page": currentPage) {
			// first
			li(class: "page-item${useOffset > 0 ? "" : " disabled"}") {
				a(class: "page-link", href: createLink(baselineParams + [params: useParams + [max: useMax, offset: 0]]).decodeHTML()) {
					i(class: "fas fa-fast-backward", "")
				}
			}
			
			// previous
			li(class: "page-item${useOffset > 0 ? "" : " disabled"}") {
				a(class: "page-link", href: createLink(baselineParams + [params: useParams + [max: useMax, offset: Math.max(0, calculateOffset(currentPage - 1, useMax))]]).decodeHTML()) {
					i(class: "fas fa-backward", "")
				}
			}
			
			// page numbers
			(1..pageCount).each { pageNum ->
				li(class: "page-item${pageNum == currentPage ? " active" : ""}") {
					a(class: "page-link", href: createLink(baselineParams + [params: useParams + [max: useMax, offset: calculateOffset(pageNum, useMax)]]).decodeHTML(), pageNum)
				}
			}
			
			// next
			li(class: "page-item${currentPage == pageCount ? " disabled" : ""}") {
				a(class: "page-link", href: createLink(baselineParams + [params: useParams + [max: useMax, offset: Math.min(calculateOffset(pageCount, useMax), calculateOffset((currentPage + 1), useMax))]]).decodeHTML()) {
					i(class: "fas fa-forward", "")
				}
			}
			
			// last
			li(class: "page-item${currentPage == pageCount ? " disabled" : ""}") {
				a(class: "page-link", href: createLink(baselineParams + [params: useParams + [max: useMax, offset: calculateOffset(pageCount, useMax)]]).decodeHTML()) {
					i(class: "fas fa-fast-forward", "")
				}
			}
		}
		
		out << raw(writer.toString())
	}
	
	private String dangerAlert(String title, String message) {
		StringWriter writer = new StringWriter()
		MarkupBuilder builder = new MarkupBuilder(writer)
		
		builder.div(class: "alert alert-danger", role: "alert") {
			h4(class: "alert-heading", title)
			p(message)
		}
		
		return writer.toString()
	}
	
	private int calculateOffset(int pageNumber, int perPage) {
		return (pageNumber - 1) * perPage
	}
}
