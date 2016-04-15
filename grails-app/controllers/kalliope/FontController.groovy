package kalliope



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FontController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Font.list(params), model:[fontInstanceCount: Font.count()]
    }

    def show(Font fontInstance) {
        respond fontInstance
    }

    def create() {
        respond new Font(params)
    }

    @Transactional
    def save(Font fontInstance) {
        if (fontInstance == null) {
            notFound()
            return
        }

        if (fontInstance.hasErrors()) {
            respond fontInstance.errors, view:'create'
            return
        }

        fontInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'font.label', default: 'Font'), fontInstance.id])
                redirect fontInstance
            }
            '*' { respond fontInstance, [status: CREATED] }
        }
    }

    def edit(Font fontInstance) {
        respond fontInstance
    }

    @Transactional
    def update(Font fontInstance) {
        if (fontInstance == null) {
            notFound()
            return
        }

        if (fontInstance.hasErrors()) {
            respond fontInstance.errors, view:'edit'
            return
        }

        fontInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Font.label', default: 'Font'), fontInstance.id])
                redirect fontInstance
            }
            '*'{ respond fontInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Font fontInstance) {

        if (fontInstance == null) {
            notFound()
            return
        }

        fontInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Font.label', default: 'Font'), fontInstance.id])
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
}
