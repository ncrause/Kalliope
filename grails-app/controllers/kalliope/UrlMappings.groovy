package kalliope

class UrlMappings {

    static mappings = {
		"/admin/$controller/$action?/$id?(.$format)?"(namespace: "admin")
		"/admin"(namespace: "admin", controller: "font", action: "index")
        "/public/$action?/$id?(.$format)?"(controller: "public")
//        "/upload"(controller: "public", action: "upload")
		"/"(controller: "public", action: "index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
