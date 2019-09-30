package kalliope.admin

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import kalliope.FontService
import kalliope.FontVariant
import kalliope.FontVariantService

class FontVariantController {
	
	static namespace = "admin"

	FontService fontService
    FontVariantService fontVariantService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond fontVariantService.list(params), model: [font: fontService.get(params.font.id), fontVariantCount: fontVariantService.count()]
    }

    def show(Long id) {
        respond fontVariantService.get(id)
    }

    def create() {
        respond new FontVariant(params)
    }

	def save() {
		FontVariant fontVariant = new FontVariant(
				font: fontService.get(params.font.id),
				weight: FontVariant.Weight.valueOf(params.weight),
				stretch: FontVariant.Stretch.valueOf(params.stretch),
				italic: params.italic ? (params.italic == 'on') : false,
				original: params.original.bytes,
				originalFilename: params.original.originalFilename)
		
		try {
			fontVariantService.save(fontVariant)
		} catch (ValidationException e) {
            respond fontVariant.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fontVariant.label', default: 'FontVariant'), fontVariant.id])
                redirect fontVariant
            }
            '*' { respond fontVariant, [status: CREATED] }
        }
	}

    def edit(Long id) {
        respond fontVariantService.get(id)
    }

    def update(FontVariant fontVariant) {
        if (fontVariant == null) {
            notFound()
            return
        }

        try {
            fontVariantService.save(fontVariant)
        } catch (ValidationException e) {
            respond fontVariant.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fontVariant.label', default: 'FontVariant'), fontVariant.id])
                redirect fontVariant
            }
            '*'{ respond fontVariant, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        fontVariantService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'fontVariant.label', default: 'FontVariant'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fontVariant.label', default: 'FontVariant'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
