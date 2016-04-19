package kalliope

class FontVariant {
	
	enum Weight {
		THIN(100), EXTRA_LIGHT(200), LIGHT(300), NORMAL(400), MEDIUM(500),
		SEMI_BOLD(600), BOLD(700), EXTRA_BOLD(800), BLACK(900)
		/*
		 * In terms of font file names:
		 * THIN = 'UltraLight'
		 * EXTRA_LIGHT = 'Thin'
		 * LIGHT = 'Light'
		 * NORMAL
		 * MEDIUM = 'Medium'
		 * SEMI = 'Demi'
		 * BOLD = 'Bold'
		 * EXTRA_BOLD = 'Heavy'
		 * BLACK = 'Black'
		 */
		
		private final int number
		
		Weight(int number) {
			this.number = number
		}
		
		int number() {
			return number
		}
	}
	
	enum Stretch {
		ULTRA_CONDENSED('ultra-condensed'), EXTRA_CONDENSED('extra-condensed'),
		CONDENSED('condensed'), SEMI_CONDENSED('semi-condensed'), 
		NORMAL('normal'), SEMI_EXPANDED('semi-expanded'), EXPANDED('expanded'),
		EXTRA_EXPANDED('extra-expanded'), ULTRA_EXPANDED('ultra-expanded')
		
		private final String cssKeyword
		
		Stretch(String cssKeyword) {
			this.cssKeyword = cssKeyword
		}
		
		String cssKeyword() {
			return cssKeyword
		}
	}
	
//	Font font
	static belongsTo = [font: Font]
	
	Weight weight
	
	Stretch stretch
	
	boolean italic
	
	/**
	 * This is the original file which was uploaded from which all the other
	 * files have been derived
	 */
	byte[] original
	
	String originalFilename
	
	byte[] ttf
	
	byte[] eot
	
	byte[] woff
	
	byte[] svg
	
	Date dateCreated
	
	Date lastUpdated
	
	static constraints = {
		original maxSize: 1024 * 1024 * 1024
		ttf maxSize: 1024 * 1024 * 1024
		eot maxSize: 1024 * 1024 * 1024
		woff maxSize: 1024 * 1024 * 1024
		svg maxSize: 1024 * 1024 * 1024
	}
	
	static mapping = {
		table name: 'font_variants', schema: 'public'
	}
	
	private fontsConverted = false
	
	def beforeValidate() {
		// Convert the font only if the original font source has changed, or if
		// any of the font formats are null
		if (isDirty('original') || ttf == null || eot == null || woff == null || svg == null) {
			convertFont()
		}
	}
	
	private convertFont() {
		fontsConverted = true
		println '--------------------------------'

		// convert original into ttf, eot, woff and svg
		def tmpDir = new File(System.getProperty('java.io.tmpdir'))

		// Unable to write to tmp directory is a critical condition
		if (!tmpDir.canWrite()) {
			throw new RuntimeException("Unable to write to tmp directory ${System.getProperty('java.io.tmpdir')}")
		}

		def script = new File(tmpDir, 'ffconvert.pe')

		println "Let's check the script file ${script.absolutePath}"

		// if the script file doesn't exist, create it!
		if (!script.exists()) {
			def input = """
				Open(\$1)
				Reencode("unicode")
				SelectWorthOutputting()
				SelectInvert()
				BuildAccented()
				Generate(\$1:r + ".ttf")
				Generate(\$1:r + ".eot")
				Generate(\$1:r + ".woff")
				Generate(\$1:r + ".svg")
			""".stripIndent()

			script << input
		}

		def tmp = new File(tmpDir, originalFilename)

		if (!tmp.exists()) {
			def output = new FileOutputStream(tmp)

			try {
				output << original
			}
			finally {
				output.close()
			}
		}

//		def process = "fontforge -c 'Open(\$1); Generate(\$1:r + \".ttf\")'".execute()
//		def process = "fontforge -script ${script.getAbsolutePath()} \"${tmp.getAbsolutePath()}\"".execute()
		def processBuilder = new ProcessBuilder('fontforge', '-script', script.absolutePath, tmp.absolutePath)
		def process = processBuilder.start()
		def stdOut = new StringBuffer()
		def stdErr = new StringBuffer()

		// halt the thread until the process is finished
		process.waitForProcessOutput(stdOut, stdErr)

		if (process.exitValue() != 0) {
			throw new RuntimeException("Failed to convert font!\n\nOutput:\n${stdOut}\n\nError:\n${stdErr}")
		}

		def baseName = org.apache.commons.io.FilenameUtils.getBaseName(tmp.absolutePath)
		def ttf = new File(tmpDir, baseName + '.ttf')
		def eot = new File(tmpDir, baseName + '.eot')
		def woff = new File(tmpDir, baseName + '.woff')
		def svg = new File(tmpDir, baseName + '.svg')

		this.ttf = ttf.bytes
		this.eot = eot.bytes
		this.woff = woff.bytes
		this.svg = svg.bytes
	}
	
	def beforeInsert() {
		// always make sure fonts have been converted
		if (!fontsConverted) {
			beforeValidate()
		}
	}
	
	def beforeUpdate() {
		// always make sure fonts have been converted
		if (!fontsConverted) {
			beforeValidate()
		}
	}
	
	/**
	 * Generates CSS @font-face declaration for this single variant
	 * 
	 * @param baseURL the URL which contains the controller + action which will
	 * be returned to handle retrieval of the font
	 */
	String getFontFace(String baseURL = '/publicAccess/font/get') {
		return """
@font-face {
	font-family: "${font.name}";
	src: url("${baseURL}/${id}.eot");
	src: local("☺"),
		url("${baseURL}/${id}.woff") format("woff"),
		url("${baseURL}/${id}.ttf") format("truetype"),
		url("${baseURL}/${id}.svg") format("svg");
	font-weight: ${weight.number()};
	font-style: ${italic ? 'italic' : 'normal'};
	font-stretch: ${stretch.cssKeyword()};
}
		"""
	}
	
	/**
	 * Generates CSS @typeface declaration for this single variant. This is
	 * similar to getFontFace, except that the font data is encoded directly
	 * 
	 * @param baseURL the URL which contains the controller + action which will
	 * be returned to handle retrieval of the font
	 */
	String getFontFaceInline() {
		return """
@font-face {
	font-family: "${font.name}";
	src: url("data:application/vnd.ms-fontobject;base64,${Base64.encodeBase64String(eot)}");
	src: local("☺"),
		url("data:application/font-woff;base64,${Base64.encodeBase64String(woff)}") format("woff"),
		url("data:application/font-sfnt;base64,${Base64.encodeBase64String(ttf)}") format("truetype"),
		url("data:image/svg+xml;base64,${Base64.encodeBase64String(svg)}") format("svg");
	font-weight: ${weight.number()};
	font-style: ${italic ? 'italic' : 'normal'};
	font-stretch: ${stretch.cssKeyword()};
}
		"""
	}
	
	/**
	 * Generate inline-CSS for directly embedding in HTML
	 */
	String getInlineCSS() {
		return [
			"font-family: '${font.name}'",
			"font-weight: ${weight.number()}",
			"font-stretch: ${stretch.cssKeyword()}",
			"font-style: ${italic ? 'italic' : 'normal'}"
		].join(';')
	}
	
	String toString() {
		def result = String.valueOf(font?.name)
		
		if (weight != Weight.NORMAL) {
			result <<= " ${weight}"
		}
		
		if (stretch != Stretch.NORMAL) {
			result <<= " ${stretch}"
		}
		
		if (italic) {
			result <<= ' italic'
		}
		
		return result as String
	}
}
