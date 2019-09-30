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

class Font {
	
	enum Category {
		SERIF("serif"), SANS_SERIF("sans-serif"), MONOSPACE("monospace"), 
		CURSIVE("cursive"), FANTASY("fantasy")
		
		/**
		 * Holds the value of the CSS "failover" generic font-face to use
		 */
		private String failoverFontFace
		
		Category(failoverFontFace) {
			this.failoverFontFace = failoverFontFace
		}
		
		String failoverFontFace() {
			return failoverFontFace
		}
	}
	
	String name
	
	Category category
	
	static hasMany = [variants: FontVariant]
	
	Date dateCreated
	
	Date lastUpdated
	
	/**
	 * Since this font server can have a "permanent" library of re-usable fonts,
	 * as well as provide a "quick" conversion utility, this fields defines if
	 * the font is actually just "transitory" (short-lived)
	 */
	boolean transitory = true

    static constraints = {
		name(blank: false)
    }
	
	static mapping = {
		table(name: "fonts")
	}
	
	String toString() {
		return name
	}
	
	void beforeValidate() {
		// remove all single quotes, as they will interfere with inserting
		// the font's name into CSS
		if (name) {
			name = name.replaceAll("'", "")
		}
	}
	
}
