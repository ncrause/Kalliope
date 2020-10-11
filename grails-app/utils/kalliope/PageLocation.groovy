/*
 * Copyright (C) 2020 Nathan Crause <nathan@crause.name>
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

import groovy.transform.CompileStatic

@CompileStatic
enum PageLocation {
	
	/**
	 * Within the &lt;head&gt; element
	 */
	HEAD,
	/**
	 * Immediately after the opening &lt;body&gt; tag
	 */
	BODY_OPEN, 
	/**
	 * Immediately before the closing &lt;/body&gt; tag
	 */
	BODY_CLOSE,
	/**
	 * The generally defined "header" area of the visible site
	 */
	HEADER,
	/**
	 * The generally defined "footer" area of the visible site
	 */
	FOOTER,
	/**
	 * Within the main content area, the left-most "sidebar"
	 */
	LEFT,
	/**
	 * Within the main content area, the right-most "sidebar"
	 */
	RIGHT
	
}
