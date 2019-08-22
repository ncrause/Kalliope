package kalliope

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import spock.lang.Specification

@Integration
@Rollback
class AuthenticationServiceSpec extends Specification {
	
	@Autowired AuthenticationService service

    def setup() {
    }

    def cleanup() {
    }

    void "Test non-existant username"() {
        when: "logging in with non-existant username"
		service.login("nobody", "mypassword")
		
		then: "we should get a security exception"
		thrown(SecurityException)
    }
	
	void "Test bad password"() {
		setup:
		User u = new User(name: "test")
		u.password = "changeme"
		u.save()
		
		when: "logging in with a bad password"
		service.login("test", "nurp")
		
		then: "we should get a security exception"
		thrown(SecurityException)
	}
	
	void "Test success"() {
		setup:
		User u = new User(name: "test")
		u.password = "changeme"
		u.save()
		
		when: "logging in with a bad password"
		service.login("test", "changeme")
		
		then: "we should get a security exception"
		notThrown(SecurityException)
	}
	
}
