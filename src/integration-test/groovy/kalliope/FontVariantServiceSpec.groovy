package kalliope

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class FontVariantServiceSpec extends Specification {

    FontVariantService fontVariantService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new FontVariant(...).save(flush: true, failOnError: true)
        //new FontVariant(...).save(flush: true, failOnError: true)
        //FontVariant fontVariant = new FontVariant(...).save(flush: true, failOnError: true)
        //new FontVariant(...).save(flush: true, failOnError: true)
        //new FontVariant(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //fontVariant.id
    }

    void "test get"() {
        setupData()

        expect:
        fontVariantService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<FontVariant> fontVariantList = fontVariantService.list(max: 2, offset: 2)

        then:
        fontVariantList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        fontVariantService.count() == 5
    }

    void "test delete"() {
        Long fontVariantId = setupData()

        expect:
        fontVariantService.count() == 5

        when:
        fontVariantService.delete(fontVariantId)
        sessionFactory.currentSession.flush()

        then:
        fontVariantService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        FontVariant fontVariant = new FontVariant()
        fontVariantService.save(fontVariant)

        then:
        fontVariant.id != null
    }
}
