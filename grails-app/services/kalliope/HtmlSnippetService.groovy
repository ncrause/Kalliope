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

import grails.gorm.DetachedCriteria
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional

/**
 *
 * @author Nathan Crause <nathan@crause.name>
 */
@Service(HtmlSnippet)
abstract class HtmlSnippetService {

    abstract HtmlSnippet get(Serializable id)

    abstract List<HtmlSnippet> list(Map args)

    abstract Long count()

	@Transactional
    abstract void delete(Serializable id)

	@Transactional
    HtmlSnippet save(HtmlSnippet record) {
		if (!record.position && record.location) {
			record.position = maximumPositionByLocation(record.location) + 1
		}
		
		record.save(failOnError: true)
		
		return record.refresh()
	}
	
	DetachedCriteria byLocation(PageLocation targetLocation) {
		HtmlSnippet.where {
			location == targetLocation
		}
	}
	
	List<HtmlSnippet> listByLocationOrderedByPosition(PageLocation targetLocation) {
		byLocation(targetLocation).list(sort: "position", order: "asc")
	}
	
	int maximumPositionByLocation(PageLocation targetLocation) {
		byLocation(targetLocation).max("position").get() ?: 0
	}
	
	boolean locationExists(PageLocation targetLocation) {
		byLocation(targetLocation).count() > 0
	}
	
	/**
	 * Calculated the col-md-X that the layout body should be given the
	 * potential presence of left and right snippets.
	 */
	int getBodyColumnWidth() {
		12 - (locationExists(PageLocation.LEFT) ? 4 : 0) - (locationExists(PageLocation.RIGHT) ? 4 : 0)
	}
	
}

