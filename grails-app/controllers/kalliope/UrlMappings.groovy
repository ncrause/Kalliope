package kalliope

class UrlMappings {

    static mappings = {
		"/admin/$controller/$action?/$id?(.$format)?"(namespace: "admin")
        "/public/$action?/$id?(.$format)?"(controller: "public")
		"/"(controller: "public", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
