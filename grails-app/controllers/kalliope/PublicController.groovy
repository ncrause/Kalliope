package kalliope

class PublicController {

    FontService fontService
	
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
	
	private byte[] content(FontVariant instance, String format) {
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

    def index(Integer max) {
		params.max = Math.min(max ?: 10, 100)
		params.sort = "dateCreated"
		params.order = "desc"
		
		respond(fontService.list(params))
	}
	
	def get(FontVariant instance) {
		response.addHeader('Access-Control-Allow-Origin', '*')
		response.addHeader('Vary', 'Origin')
		response.contentType = mimeType(params.format)
		response.outputStream << content(instance, params.format)
		response.outputStream.flush()
		
		return null
	}
	
	def css(Font instance) {
		render(text: fontService.getFontFace(instance), contentType: "text/css")
	}
	
	def convert() {
		respond(new Font(params.font), model: [fontVariant: new FontVariant(params.fontVariant)])
	}
	
	def upload() {
		String extension = org.apache.commons.io.FilenameUtils.getExtension(params.fontVariant.original.originalFilename)
		
		if (!["ttf", "otf"].contains(extension.toLowerCase())) {
			flash.error = "You must upload a TrueType font"
			return redirect(action: "convert")
		}
		
		java.awt.Font ttf = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, params.fontVariant.original.inputStream)
	}
	
}
