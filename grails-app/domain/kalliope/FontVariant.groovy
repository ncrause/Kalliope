/*
 * Copyright (C) 2019 Nathan Crause
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kalliope

class FontVariant {
	
	enum Weight {
		THIN(100), EXTRA_LIGHT(200), LIGHT(300), NORMAL(400), MEDIUM(500),
		SEMI_BOLD(600), BOLD(700), EXTRA_BOLD(800), BLACK(900)
		/*
		 * In terms of font file names:
		 * THIN = "UltraLight"
		 * EXTRA_LIGHT = "Thin"
		 * LIGHT = "Light"
		 * NORMAL
		 * MEDIUM = "Medium"
		 * SEMI = "Demi"
		 * BOLD = "Bold"
		 * EXTRA_BOLD = "Heavy"
		 * BLACK = "Black"
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
		ULTRA_CONDENSED("ultra-condensed"), EXTRA_CONDENSED("extra-condensed"),
		CONDENSED("condensed"), SEMI_CONDENSED("semi-condensed"), 
		NORMAL("normal"), SEMI_EXPANDED("semi-expanded"), EXPANDED("expanded"),
		EXTRA_EXPANDED("extra-expanded"), ULTRA_EXPANDED("ultra-expanded")
		
		private final String cssKeyword
		
		Stretch(String cssKeyword) {
			this.cssKeyword = cssKeyword
		}
		
		String cssKeyword() {
			return cssKeyword
		}
	}
	
	static belongsTo = [font: Font]
	
	Weight weight //= Weight.NORMAL
	
	Stretch stretch //= Stretch.NORMAL
	
	boolean italic = false
	
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
	
	/**
	 * As retrieved from FontMetrics#getMaxAscent()
	 */
	Integer maxAscent
	
	/**
	 * As retrieved from FontMetrics#getMaxDescent()
	 */
	Integer maxDescent
	
	/**
	 * As retrieved from FontMetrics#getLeading()
	 */
	Integer leading
	
	/**
	 * As retrieved from FontMetrics#getMaxAdvance()
	 */
	Integer maxAdvance
	
	/**
	 * As specified in the admin
	 */
	Double angle
	
	/**
	 * As specified in the admin
	 */
	Integer width
	
	/**
	 * As retrieved from Font#getSize2D()
	 */
	Double pointSize
	
	Date dateCreated
	
	Date lastUpdated
	
	static constraints = {
		original(maxSize: 1024 * 1024 * 1024)
		ttf(maxSize: 1024 * 1024 * 1024)
		eot(maxSize: 1024 * 1024 * 1024)
		woff(maxSize: 1024 * 1024 * 1024)
		svg(maxSize: 1024 * 1024 * 1024)
		maxAscent(nullable: true)
		maxDescent(nullable: true)
		leading(nullable: true)
		maxAdvance(nullable: true)
		angle(nullable: true)
		width(nullable: true)
		pointSize(nullable: true)
	}
	
	static mapping = {
		table(name: "font_variants")
//		original(sqlType: "blob")
//		ttf(sqlType: "blob")
//		eot(sqlType: "blob")
//		woff(sqlType: "blob")
//		svg(sqlType: "blob")
	}
	
	static transients = ["fontsConverted"]
	
	String toString() {
		StringBuilder builder = new StringBuilder(String.valueOf(font?.name))
		
		if (weight != Weight.NORMAL) {
			builder << " ${weight}"
		}
		
		if (stretch != Stretch.NORMAL) {
			builder << " ${stretch}"
		}
		
		if (italic) {
			builder << " italic"
		}
		
		return builder as String
	}
	
	/**
	 * Generate inline-CSS for directly embedding in HTML element's
	 * <code>style</code> attribute.
	 */
	String getInlineCSS() {
		return [
			"font-family: '${font.name}', ${font.category.failoverFontFace()}",
			"font-weight: ${weight.number()}",
			"font-stretch: ${stretch.cssKeyword()}",
			"font-style: ${italic ? "italic" : "normal"}"
		].join(';')
	}
	
	/**
	 * Keeps track if the fonts have been converted or not
	 */
	protected boolean converted = false
	
	void beforeValidate() {
		// don't do jack if there is no filename
		if (!originalFilename) {
			return
		}
		// Convert the font only if the original font source has changed, or if
		// any of the font formats are null
		if (isDirty("original") || ttf == null || eot == null || woff == null || svg == null) {
			fontVariantService.convertFont(this)
		}
	}
	
	void beforeInsert() {
		// always make sure fonts have been converted
		if (!converted) {
			beforeValidate()
		}
	}
	
	void beforeUpdate() {
		// always make sure fonts have been converted
		if (!converted) {
			beforeValidate()
		}
	}
	
	FontVariantService getFontVariantService() {
		Beans.fontVariantService
	}
	
}
