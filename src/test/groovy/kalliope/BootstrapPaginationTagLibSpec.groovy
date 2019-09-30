package kalliope

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class BootstrapPaginationTagLibSpec extends Specification implements TagLibUnitTest<BootstrapPaginationTagLib> {

    def setup() {
    }

    def cleanup() {
    }

    void "Test total attribute is required"() {
        when: "invoking without the require parameter"
		def output = applyTemplate('<bootstrap:paginate />');
		
		then: "we should get an alert dialog"
		output.contains("Missing required attribute")
    }
	
	void "Test multipage with first page active should render appropriate UL"() {
		given:
		def output = tagLib.paginate(total: "25")
		
		expect:
		output.contains("<ul class='pagination' data-page-count='3' data-current-page='1'>")
	}
	
	void "Test multipage with second page active should render appropriate UL"() {
		given:
		tagLib.params.offset = "10"
		def output = tagLib.paginate(total: "25")
		
		expect:
		output.contains("<ul class='pagination' data-page-count='3' data-current-page='2'>")
	}
}
