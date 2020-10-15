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

/**
 * This domain class stores information pertaining to HTTP requests made against
 * trackable public end-points, such as FontVariant CSS requests. It is
 * intentionally non-specific, but stores as much generic information as
 * possible about the request, so that external processes can generate
 * analytical data at some later stage. Doing so speeds up the process of
 * saving the hit, since no additional work will need to be done against it.
 */
class PublicHit {
	
	/**
	 * The public endpoint that was hit, including query parameters
	 */
	String requestURL
	
	/**
	 * The remote address that made the request
	 */
	String remoteAddress
	
	/**
	 * Reconstructed request head section, thusly:
	 * 
	 * request.getMethod() request.getRequestURI() request.getQueryString() request.getProtocol()
	 * all the headers via request.getHeaderNames() and request.getHeaders() 
	 * 
	 * note the getHeaders PLURAL! Each header name may very well have multiple
	 * instances, and we need to dump out each one.
	 */
	String requestHead
	
	/**
	 * Store the entire request body, if any - this could allow in-depth
	 * analysis of possible attack attempts.
	 */
	String requestBody
	
	Date dateCreated
	
	Date lastUpdated

    static constraints = {
		requestURL(blank: false)
		remoteAddress(blank: false)
		// some reference state a maximum size of 48K is possible, but we'll
		// up it to 64K to be certain
		requestHead(blank: false, maxSize: 64 * 1024)
		// We'll first need to figure out how on earth we can actually
		// intercept the request, and provide a wrapped InputStream for
		// subsequent requests. Servlet filters can do this, but how do
		// interceptors?
//		requestBody(blank: false)
		requestBody(nullable: true)
    }
}
