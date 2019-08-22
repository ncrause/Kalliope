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

import grails.util.Holders

/**
 * Since Grails doesn't seem capable of autowiring into abstract classes, this
 * causes a bit of an issue when creating data services which do more than
 * just get/save.
 * 
 * All those context beans are available, so we just need to fudge the idea of
 * autowiring, and this class helps by providing a quick reference to the
 * singe long line required to get those Spring beans.
 * 
 * This also helps with domain classes, which have autowiring disabled, and
 * despite all the documentation saying the contrary, into which you cannot
 * autowire.
 * 
 * That said, this might prove to be a faster solution for use within domain
 * classes anyway since we don't have to worry about autowiring happening on
 * every object instantiation, but rather we just have a reference to the
 * singular instance with in the main context.
 */
class Beans {
	
	/**
	 * Retrieves the names service bean from the Grails Application's main
	 * context.
	 */
	static def get(String name) {
		Holders.grailsApplication.mainContext.getBean(name)
	}
	
	/**
	 * This allows for direct name access to the service beans, by simply
	 * invoking (for example): <code>Beans.fontService.fontFace</code>
	 */
	static def $static_propertyMissing(String name) {
		get(name)
	}
	
}

