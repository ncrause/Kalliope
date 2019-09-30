package kalliope

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FontSpec extends Specification implements DomainUnitTest<Font> {

    void "Test name cannot be null"() {
        when:
		domain.name = null
		
		then:
		!domain.validate(["name"])
		domain.errors["name"].code == "nullable"
    }

    void "Test name cannot be blank"() {
        when:
		domain.name = ""
		
		then:
		!domain.validate(["name"])
		domain.errors["name"].code == "blank"
    }
	
}
