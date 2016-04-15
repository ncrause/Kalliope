package kalliope

class Font {
	
	String name
	
	static hasMany = [variants: FontVariant]
	
	Date dateCreated
	
	Date lastUpdated

    static constraints = {
		name blank: false
    }
	
	static mapping = {
		table name: 'fonts', schema: 'public'
	}
	
	String toString() {
		return name
	}
	
	/**
	 * Generates CSS @font-face declaration, including all the variants
	 * 
	 * @param baseURL the URL which contains the controller + action which will
	 * be returned to handle retrieval of the font
	 */
	String getFontFace(String baseURL = '/publicAccess/font/get') {
		def builder = StringBuilder.newInstance()
		
		for (variant in variants) {
			builder << variant.getFontFace(baseURL) << "\r\n"
		}
		
		return builder.toString()
	}
	
	String getFontFaceInline() {
//		println '-----'
//		println variants
//		print FontVariant.list()
//		println '-----'
		def builder = StringBuilder.newInstance()
		
		for (variant in variants) {
			builder << variant.fontFaceInline << "\r\n"
		}
		
		return builder as String
	}
	
	FontVariant getVariant(FontVariant.Weight weight, FontVariant.Stretch stretch, boolean italic) {
		variants.find { it.weight == weight && it.stretch == stretch &&  it.italic == italic }
	}
	
	FontVariant getNormal() {
		getVariant(FontVariant.Weight.NORMAL, FontVariant.Stretch.NORMAL, false)
	}
	
	FontVariant getItalic() {
		getVariant(FontVariant.Weight.NORMAL, FontVariant.Stretch.NORMAL, true)
	}
	
	FontVariant getBold() {
		getVariant(FontVariant.Weight.BOLD, FontVariant.Stretch.NORMAL, false)
	}
	
	FontVariant getBoldItalic() {
		getVariant(FontVariant.Weight.BOLD, FontVariant.Stretch.NORMAL, true)
	}
	
}
