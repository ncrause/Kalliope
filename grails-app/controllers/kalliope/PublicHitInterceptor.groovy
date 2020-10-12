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

class PublicHitInterceptor {
	
	/**
	 * Used for constructing synchronously
	 */
	PublicHitService publicHitService
	
	/**
	 * Used for saving asynchronously
	 */
	AsyncPublicHitService asyncPublicHitService
	
	PublicHitInterceptor() {
		// TODO; with other way of loading fonts, use the regex pattern for
		// actions "~/(action1|actions)/"
		match(controller: "public", action: "css")
	}

    boolean before() {
		asyncPublicHitService.save(publicHitService.build(request))
		
		true 
	}

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
