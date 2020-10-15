package kalliope

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import javax.servlet.http.HttpServletRequest

@Service(PublicHit)
abstract class PublicHitService {
	
	@Transactional
	abstract PublicHit save(PublicHit record)
	
	String rebuildRequestHead(HttpServletRequest request) {
		StringWriter writer = new StringWriter()
		PrintWriter print = new PrintWriter(writer)
		
		print.println(rebuildRequestLine(request))
		
		request.headerNames.each { name -> 
			request.getHeaders(name).each { value ->
				print.println(String.format("%s: %s", name, value))
			}
		}
		
		return writer.toString()
	}
	
	String rebuildRequestURL(HttpServletRequest request) {
		StringBuffer result = request.requestURL
		
		if (request.queryString) {
			result << "?${request.queryString}"
		}
		
		return result.toString()
	}
	
	PublicHit build(HttpServletRequest request) {
		new PublicHit(requestURL: rebuildRequestURL(request),
				remoteAddress: request.remoteAddr,
				requestHead: rebuildRequestHead(request))
	}
	
	private String rebuildRequestLine(HttpServletRequest request) {
		String.format("%s %s%s %s", request.method, request.requestURI, 
			request.queryString ? "?${request.queryString}" : "",
			request.protocol)
	}
	
}
