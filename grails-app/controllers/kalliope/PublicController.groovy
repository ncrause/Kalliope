package kalliope

import grails.validation.ValidationException

class PublicController {

    FontService fontService
	
	FontVariantService fontVariantService
	
	static allowedMethods = [upload: "POST"]
	
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

//    def index(Integer max) {
//		params.max = Math.min(max ?: 10, 100)
//		params.sort = "dateCreated"
//		params.order = "desc"
//		
//		respond(fontService.list(params))
//	}

	def index() {
		render(view: "index", model: [
				permanentFonts: fontService.listRecent(false, 4), 
				transitoryFonts: fontService.listRecent(true, 4),
				intradayCount: fontService.intradayCount,
				// the following 2 fonts are just for rendering the "Kalliope" and "Font Server" texts, respectively
				scriptFont: fontService.findByName("Pinyon Script"),
				serifFont: fontService.findByName("Bree Serif")
		])
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
		String extension = org.apache.commons.io.FilenameUtils.getExtension(params.file.originalFilename)
		
		if (!["ttf", "otf"].contains(extension.toLowerCase())) {
			flash.error = "You must upload a TrueType font"
			return redirect(action: "index")
		}
		
		java.awt.Font ttf = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, params.file.inputStream)
		Font font = new Font(name: ttf.fontName, category: Font.Category.valueOf(params.category), transitory: true)
		
		font.addToVariants(new FontVariant(
				weight: FontVariant.Weight.NORMAL,
				stretch: FontVariant.Stretch.NORMAL,
				italic: false,
				original: params.file.bytes,
				originalFilename: params.file.originalFilename))
		
        try {
            fontService.save(font)
        } catch (ValidationException e) {
            respond(font.errors, view: "index", model: [
					permanentFonts: fontService.listRecent(false, 4), 
					transitoryFonts: fontService.listRecent(true, 4),
					intradayCount: fontService.intradayCount,
					scriptFont: fontService.findByName("Pinyon Script"),
					serifFont: fontService.findByName("Bree Serif")
			])
		
            return
        }
		
		render(file: fontService.zip(font), fileName: "${fontService.fileBasename(font)}.zip", contentType: "application/zip")
////		response.addHeader('Access-Control-Allow-Origin', '*')
////		response.addHeader('Vary', 'Origin')
////		response.contentType = mimeType(params.format)
////		response.outputStream << content(instance, params.format)
////		response.outputStream.flush()
//		
//		return null
	}
	
	def browse() {
		def category = params.containsKey("category") && params.category != "null" ? Font.Category.valueOf(params.category) : null;
		def weight = params.containsKey("weight") ? FontVariant.Weight.valueOf(params.weight) : FontVariant.Weight.NORMAL
		def stretch = params.containsKey("stretch") ? FontVariant.Stretch.valueOf(params.stretch) : FontVariant.Stretch.NORMAL
		def italic = params.containsKey("italic") ? Integer.valueOf(params.italic) : 0
		def enableAdvance = params.containsKey("enableAdvance") ? Boolean.valueOf(params.enableAdvance) : false
		def (int minAdvance, int maxAdvance) = fontVariantService.advanceLimits()
		def advance = params.containsKey("advance") ? Integer.valueOf(params.advance) : Math.round((minAdvance.toDouble() + maxAdvance.toDouble()) / 2)
		def fontVariants = fontVariantService.search(category: category,
				weight: weight, stretch: stretch, 
				italic: italic == 1 ? true : italic == 0 ? false : null,
				advance: enableAdvance ? advance : null)
		
		render(view: "browse", model: [
				categories: Font.Category.values(),
				category: category,
				weights: FontVariant.Weight.values(),
				weight: weight,
				stretches: FontVariant.Stretch.values(),
				stretch: stretch,
				italic: italic,
				enableAdvance: enableAdvance,
				minAdvance: minAdvance,
				maxAdvance: maxAdvance,
				advance: advance,
				fontVariants: fontVariants
		])
	}
	
}
