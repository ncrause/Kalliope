package kalliope.admin

import grails.validation.ValidationException
import kalliope.HtmlSnippet
import kalliope.HtmlSnippetService
import kalliope.PageLocation

import static org.springframework.http.HttpStatus.*

class HtmlSnippetController {
	
	static namespace = "admin"

    HtmlSnippetService htmlSnippetService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
//        params.max = Math.min(max ?: 10, 100)
//        respond htmlSnippetService.list(params), model:[htmlSnippetCount: htmlSnippetService.count()]

		Map locations = [:]

		for (PageLocation location : kalliope.PageLocation.values()) {
			locations[location.name()] = htmlSnippetService.listByLocationOrderedByPosition(location)
		}
		
		respond locationSnippets: locations
    }

    def show(Long id) {
        respond htmlSnippetService.get(id)
    }

    def create() {
        respond new HtmlSnippet(params)
    }

    def save(HtmlSnippet htmlSnippet) {
        if (htmlSnippet == null) {
            notFound()
            return
        }

        try {
            htmlSnippetService.save(htmlSnippet)
        } catch (ValidationException e) {
            respond htmlSnippet.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'htmlSnippet.label', default: 'HtmlSnippet'), htmlSnippet.id])
                redirect htmlSnippet
            }
            '*' { respond htmlSnippet, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond htmlSnippetService.get(id)
    }

    def update(HtmlSnippet htmlSnippet) {
        if (htmlSnippet == null) {
            notFound()
            return
        }

        try {
            htmlSnippetService.save(htmlSnippet)
        } catch (ValidationException e) {
            respond htmlSnippet.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'htmlSnippet.label', default: 'HtmlSnippet'), htmlSnippet.id])
                redirect htmlSnippet
            }
            '*'{ respond htmlSnippet, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        htmlSnippetService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'htmlSnippet.label', default: 'HtmlSnippet'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'htmlSnippet.label', default: 'HtmlSnippet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
