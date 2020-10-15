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

package kalliope.api


import grails.converters.JSON
import grails.gorm.transactions.Transactional
import kalliope.Font
import kalliope.FontService
import kalliope.FontVariant
import kalliope.FontVariantService

/**
 *
 * @author Nathan Crause <nathan@crause.name>
 */
@Transactional(readOnly = true)
class FontsController {
	
	static namespace = "api"
	
	static responseFormats = ["json"]
	
	static allowedMethods = [search: "POST", variantIdToSlug: "GET"]
	
	FontService fontService
	
	FontVariantService fontVariantService

	/**
	 * Example:
	 * curl -i -X POST -H "Content-Type: application/json" -d '{"category": "SANS_SERIF", "weight": "NORMAL", "stretch": "CONDENSED"}' http://localhost:8080/api/fonts/search.json
	 */
    def search() {
		[fontVariants: fontVariantService.search(request.JSON)]
	}
	
	/**
	 * Example:
	 * curl -i -X GET http://localhost:8080/api/fonts/variant-id-to-slug/21.json
	 */
	def variantIdToSlug(Long id) {
		render([slug: fontVariantService.get(id)?.font?.slug] as JSON)
	}
	
	/**
	 * Example:
	 * curl -i -X GET http://localhost:8080/api/fonts/font-id-to-slug/1.json
	 */
	def fontIdToSlug(Long id) {
		render([slug: fontService.get(id)?.slug] as JSON)
	}
	
	/**
	 * Example:
	 * curl -i -X GET http://localhost:8080/api/fonts/list-variants/1.json
	 */
	def listVariants(String id) {
		[fontVariants: (id.isLong() ? fontService.get(id as Long) : fontService.findBySlug(id))?.variants as List]
	}
	
	/**
	 * Example:
	 * curl -i -X GET http://localhost:8080/api/fonts/list.json
	 */
	def list() {
		// TODO: we need to exclude transitory fonts
		List<Font> list = fontService.list()
		
		render(list.collect { [id: it.id, slug: it.slug, name: it.name, category: it.category as String] } as JSON)
	}
	
}

