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

import java.text.Normalizer

/**
 * This service provides a method where any string passed in is converted to a
 * "safe" value, which can be used as an ID or for URLs.
 * 
 * Actually comes from http://guides.grails.org/gorm-event-listeners/guide/index.html
 */
class SanitizeService {
	
    /**
	 * This method transforms the text passed as an argument to a text without spaces,
	 * html entities, accents, dots and extranges characters (only %,a-z,A-Z,0-9, ,_ and - are allowed).
	 *
	 * Borrowed from Wordpress: file wp-includes/formatting.php, function sanitize_title_with_dashes
	 * http://core.svn.wordpress.org/trunk/wp-includes/formatting.php
	 */
	String sanitizeWithDashes(String text) {

		if ( !text ) {
			return ''
		}

		// Preserve escaped octets
		text = text.replaceAll('%([a-fA-F0-9][a-fA-F0-9])','---$1---')
		text = text.replaceAll('%','')
		text = text.replaceAll('---([a-fA-F0-9][a-fA-F0-9])---','%$1')

		// Remove accents
		text = removeAccents(text)

		// To lower case
		text = text.toLowerCase()

		// Kill entities
		text = text.replaceAll('&.+?;','')

		// Dots -> ''
		text = text.replaceAll('\\.','')

		// Remove any character except %a-zA-Z0-9 _-
		text = text.replaceAll('[^%a-zA-Z0-9 _-]', '')

		// Trim
		text = text.trim()

		// Spaces -> dashes
		text = text.replaceAll('\\s+', '-')

		// Dashes -> dash
		text = text.replaceAll('-+', '-')

		// It must end in a letter or digit, otherwise we strip the last char
		if (!text[-1].charAt(0).isLetterOrDigit()) text = text[0..-2]

		return text
	}
	
	/**
	 * Converts all accent characters to ASCII characters.
	 *
	 * If there are no accent characters, then the string given is just returned.
	 *
	 */
	private String removeAccents(String  text) {
		Normalizer.normalize(text, Normalizer.Form.NFD)
				.replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
	}
	
}
