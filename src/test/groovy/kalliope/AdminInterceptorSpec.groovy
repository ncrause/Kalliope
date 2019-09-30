package kalliope

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class AdminInterceptorSpec extends Specification implements InterceptorUnitTest<AdminInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test admin interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(namespace: "admin", controller: "font")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }

//    void "Test admin interceptor not matching session"() {
//        when:"A request again session"
//            withRequest(namespace: "admin", controller: "session")
//
//        then:"The interceptor should not match"
//            interceptor.doesNotMatch()
//    }
}
