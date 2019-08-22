package kalliope

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class FontServiceSpec extends Specification {

    FontService fontService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Font(...).save(flush: true, failOnError: true)
        //new Font(...).save(flush: true, failOnError: true)
        //Font font = new Font(...).save(flush: true, failOnError: true)
        //new Font(...).save(flush: true, failOnError: true)
        //new Font(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //font.id
    }

    void "test get"() {
        setupData()

        expect:
        fontService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Font> fontList = fontService.list(max: 2, offset: 2)

        then:
        fontList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        fontService.count() == 5
    }

    void "test delete"() {
        Long fontId = setupData()

        expect:
        fontService.count() == 5

        when:
        fontService.delete(fontId)
        sessionFactory.currentSession.flush()

        then:
        fontService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Font font = new Font()
        fontService.save(font)

        then:
        font.id != null
    }
}
