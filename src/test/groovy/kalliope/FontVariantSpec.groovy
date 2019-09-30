package kalliope

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification

class FontVariantSpec extends Specification implements DomainUnitTest<FontVariant> {

    void "Test font cannot be null"() {
		setup:
		domain.metaClass.getFontVariantService = { ->
			Mock(FontVariantService)
		}
		
        when:
		domain.font = null
		
		then:
		!domain.validate(["font"])
		domain.errors["font"].code == "nullable"
    }
	
}
