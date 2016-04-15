class UrlMappings {

	static mappings = {
		"/$namespace/$controller/$action?/$id?(.$format)?"()
		
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
		
		// You'd think the "/$namespace" definition above would work, but it
		// doesn't do shit-all for redirect and createLink calls
		"/admin/$controller/$action?/$id?(.$format)?"(namespace: "admin")

        "/"(view:"/index")
        "500"(view:'/error')
	}
}
