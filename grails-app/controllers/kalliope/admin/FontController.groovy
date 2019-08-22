package kalliope.admin

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import kalliope.Font
import kalliope.FontService
import kalliope.FontVariant

class FontController {
	
	static namespace = "admin"

    FontService fontService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond fontService.list(params), model:[fontCount: fontService.count()]
    }

    def show(Long id) {
        respond fontService.get(id)
    }

    def create() {
        respond new Font(params)
    }

    def save(Font font) {
        if (font == null) {
            notFound()
            return
        }

        try {
			// any fonts being added here are explicitly *NOT* transitory
			font.transitory = false
			
            fontService.save(font)
        } catch (ValidationException e) {
            respond font.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'font.label', default: 'Font'), font.id])
                redirect font
            }
            '*' { respond font, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond fontService.get(id)
    }

    def update(Font font) {
        if (font == null) {
            notFound()
            return
        }

        try {
            fontService.save(font)
        } catch (ValidationException e) {
            respond font.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'font.label', default: 'Font'), font.id])
                redirect font
            }
            '*'{ respond font, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        fontService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'font.label', default: 'Font'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'font.label', default: 'Font'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def quick() {
		respond(new Font(params.font), model: [fontVariant: new FontVariant(params.fontVariant ?: [weight: FontVariant.Weight.NORMAL, stretch: FontVariant.Stretch.NORMAL])])
	}
	
	def build() {
		String extension = org.apache.commons.io.FilenameUtils.getExtension(params.fontVariant.original.originalFilename)
		
		if (!["ttf", "otf"].contains(extension.toLowerCase())) {
			flash.error = "You must upload a TrueType font"
			return redirect(action: "quick")
		}
		
		java.awt.Font ttf = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, params.fontVariant.original.inputStream)

		Font font = new Font(name: ttf.family, category: Font.Category.valueOf(params.font.category), transitory: false)
		
		font.addToVariants(new FontVariant(
				weight: FontVariant.Weight.valueOf(params.fontVariant.weight),
				stretch: FontVariant.Stretch.valueOf(params.fontVariant.stretch),
				italic: params.fontVariant.italic ? (params.fontVariant.italic == 'on') : false,
				original: params.fontVariant.original.bytes,
				originalFilename: params.fontVariant.original.originalFilename))
		
        try {
            fontService.save(font)
        } catch (ValidationException e) {
            respond font.errors, view: "quick"
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'font.label', default: 'Font'), font.id])
                redirect font
            }
            '*' { respond font, [status: CREATED] }
        }
	}
}
