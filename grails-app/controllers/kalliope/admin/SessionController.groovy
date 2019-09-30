package kalliope.admin

import kalliope.AuthenticationService

class SessionController {
	
	static namespace = "admin"
	
	AuthenticationService authenticationService

    def index() {
		if (session.user) {
			return redirect(namespace: "admin", controller: "font", action: "index")
		}
	}
	
	def login() {
        try {
            session.user = authenticationService.login(params.username, params.password)
            
			if (session.redirectTo) {
				redirect(uri: session.redirectTo)
			}
			else {
				redirect(namespace: "admin", controller: "font", action: "index")
			}
        }
        catch(SecurityException ex) {
            flash.error = ex.message
            
            redirect(action: "index")
        }
	}
	
}
