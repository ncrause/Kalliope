package kalliope



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FontVariantController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FontVariant.list(params), model:[fontVariantInstanceCount: FontVariant.count()]
    }

    def show(FontVariant fontVariantInstance) {
        respond fontVariantInstance
    }

    def create() {
        respond new FontVariant(params)
    }

    @Transactional
    def save() {
		FontVariant fontVariantInstance = new FontVariant(
				font: Font.get(params.font.id),
				weight: FontVariant.Weight.valueOf(params.weight),
				stretch: FontVariant.Stretch.valueOf(params.stretch),
				italic: params.italic ? (params.italic == 'on') : false,
				original: params.original.bytes,
				originalFilename: params.original.originalFilename)

        if (fontVariantInstance.hasErrors()) {
            respond fontVariantInstance.errors, view:'create'
            return
        }

        fontVariantInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fontVariant.label', default: 'FontVariant'), fontVariantInstance.id])
                redirect fontVariantInstance
            }
            '*' { respond fontVariantInstance, [status: CREATED] }
        }
    }

    def edit(FontVariant fontVariantInstance) {
        respond fontVariantInstance
    }

    @Transactional
    def update(FontVariant fontVariantInstance) {
        if (fontVariantInstance == null) {
            notFound()
            return
        }

        if (fontVariantInstance.hasErrors()) {
            respond fontVariantInstance.errors, view:'edit'
            return
        }

        fontVariantInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'FontVariant.label', default: 'FontVariant'), fontVariantInstance.id])
                redirect fontVariantInstance
            }
            '*'{ respond fontVariantInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(FontVariant fontVariantInstance) {

        if (fontVariantInstance == null) {
            notFound()
            return
        }

        fontVariantInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'FontVariant.label', default: 'FontVariant'), fontVariantInstance.id])
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
