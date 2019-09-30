/*
 * Copyright (C) 2019 Nathan Crause
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kalliope

import java.util.UUID
import static javax.servlet.http.HttpServletResponse.*

/**
 * Performs a super-simple bit of security using HTTP Authentication
 */
class AdminInterceptor {
	
	AdminInterceptor() {
		match(namespace: "admin").excludes(controller: "session")
	}

    boolean before() {
		// It would've been really great to be able to just use basic HTTP
		// authentication, but since fucking Grails developers decided to 
		// drop scripts/_Events.groovy and replace it with ... well ... 
		// FUCKING NOTHING ... it makes it incredibly difficult to have this
		// in a development environment using Grails' embedded Tomcat engine.
//		if (request.remoteUser == null) {
//			response.setStatus(SC_UNAUTHORIZED)
//			response.addHeader("WWW-Authenticate", "Digest realm=\"Kalliope Admin\",qop=\"auth\",nonce=\"${UUID.randomUUID() as String}\",opaque=\"10e855b2d4f702c7824229e01b0faa34\"")
//			
//			return false
//		}
//		
//		true

		if (!session.user) {
			redirect(namespace: "admin", controller: "session", action: "index")
			
			return false
		}
		
		true
	}

    boolean after() {
		// turn off all god damned browser caches - this effectively prevents
		// "back" button navigation to get back into a page
		header('Expires', 'on, 01 Jan 1970 00:00:00 GMT')
		header('Last-Modified', new Date())
		header('Cache-Control', 'no-store, no-cache, must-revalidate, post-check=0, pre-check=0')
		header('Pragma', 'no-cache')
		
		true 
	}

    void afterView() {
        // no-op
    }
}
