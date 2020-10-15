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

import java.awt.Color
import kalliope.Font
import kalliope.FontService
import kalliope.FontVariant
import kalliope.FontVariantService
import kalliope.ImageService

/**
 *
 * @author Nathan Crause <nathan@crause.name>
 */
class ImagingController {
	
	static namespace = "api"
	
	FontService fontService
	
	FontVariantService fontVariantService
	
	ImageService imageService
	
	def variant(Long id) {
		renderVariant(fontVariantService.get(id))
		
		return null
	}
	
	def font(String id) {
		Font font = id.isLong() ? fontService.get(id as Long) : fontService.findBySlug(id)
		
		renderVariant(fontVariantService.defaultVariant(font))
		
		return null
	}
	
	private void renderVariant(FontVariant variant) {
		int width = (params.width ?: 400) as Integer
		int height = (params.height ?: 25) as Integer
		String text = params.text ?: variant.toString()
		Color foreground = params.foreground ? Color.decode("#${params.foreground}") : Color.BLACK
		Color background = params.background ? Color.decode("#${params.background}") : new Color(0, 0, 0, 0)
		
		response.addHeader("Access-Control-Allow-Origin", "*")
		response.addHeader("Vary", "Origin")
		response.contentType = "image/png"
		response.outputStream << imageService.generatePNG(variant, width, height, text, foreground, background)
		response.outputStream.flush()
	}
	
}

