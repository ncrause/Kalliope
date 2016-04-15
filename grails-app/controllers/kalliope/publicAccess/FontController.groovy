package kalliope.publicAccess

import kalliope.Font
import kalliope.FontVariant

class FontController {
	
	static namespace = 'publicAccess'
	
	private String mimeType(String format) {
		switch (format) {
			case 'woff':
				return 'application/font-woff'
			case 'svg':
				return 'image/svg+xml'
			case 'eot':
				return 'application/vnd.ms-fontobject'
			default:
				return 'application/octet-stream'
		}
	}
	
	private def content(FontVariant instance, String format) {
		switch (format) {
			case 'woff':
				return instance.woff
			case 'svg':
				return instance.svg
			case 'eot':
				return instance.eot
			case 'ttf':
				return instance.ttf
			default:
				return instance.original
		}
	}

    def get(FontVariant instance) {
		response.contentType = mimeType(params.format)
		response.outputStream << content(instance, params.format)
		response.outputStream.flush()
		
		return null
	}
	
	def css(Font instance) {
		render text: instance.fontFace, contentType: 'text/css'
	}
	
}
