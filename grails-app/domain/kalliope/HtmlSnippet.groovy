/*
 * Copyright (C) 2019 Nathan Crause <nathan@crause.name>
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

/**
 * This domain class contains raw bits of HTML intended for site owners to be
 * able to embed bits of code into the main layout.
 *
 * @author Nathan Crause <nathan@crause.name>
 */
class HtmlSnippet {
	
	/**
	 * The general area on the layout
	 */
	PageLocation location
	
	/**
	 * Since more than one item can be defined in a given location, this is
	 * the general "position" (sequence / order) in which the HTML snippets
	 * should be rendered
	 */
	Integer position
	
	String content
	
	String description
	
	Date dateCreated
	
	Date lastUpdated
	
	static constraints = {
		location(nullable: false)
		position(nullable: false, min: 1, unique: "location")
		content(nullable: false, blank: false, maxSize: 4096)
		description(nullable: false, blank: false, maxSize: 256)
	}
	
	static mapping = {
		table(name: "html_snippets")
	}
	
}

