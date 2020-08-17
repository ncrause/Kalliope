package kalliope

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HtmlSnippetServiceSpec extends Specification {

    HtmlSnippetService htmlSnippetService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HtmlSnippet(...).save(flush: true, failOnError: true)
        //new HtmlSnippet(...).save(flush: true, failOnError: true)
        //HtmlSnippet htmlSnippet = new HtmlSnippet(...).save(flush: true, failOnError: true)
        //new HtmlSnippet(...).save(flush: true, failOnError: true)
        //new HtmlSnippet(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //htmlSnippet.id
    }

    void "test get"() {
        setupData()

        expect:
        htmlSnippetService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HtmlSnippet> htmlSnippetList = htmlSnippetService.list(max: 2, offset: 2)

        then:
        htmlSnippetList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        htmlSnippetService.count() == 5
    }

    void "test delete"() {
        Long htmlSnippetId = setupData()

        expect:
        htmlSnippetService.count() == 5

        when:
        htmlSnippetService.delete(htmlSnippetId)
        sessionFactory.currentSession.flush()

        then:
        htmlSnippetService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HtmlSnippet htmlSnippet = new HtmlSnippet()
        htmlSnippetService.save(htmlSnippet)

        then:
        htmlSnippet.id != null
    }
}
