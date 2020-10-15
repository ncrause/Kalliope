package kalliope

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class PublicHitInterceptorSpec extends Specification implements InterceptorUnitTest<PublicHitInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test publicHit interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"publicHit")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
