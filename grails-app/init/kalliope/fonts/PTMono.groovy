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

package kalliope.fonts

import kalliope.Font
import kalliope.FontVariant

/**
 *
 * @author Nathan Crause <nathan@crause.name>
 */
class PTMono {
	
	void run() {
		Font font = new Font(name: "PT Mono", category: Font.Category.MONOSPACE, transitory: false)
		
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.NORMAL,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/pt-mono/PTMono-Regular.ttf").bytes, 
				originalFilename: "PTMono-Regular.ttf"))

		font.save()
	}
	
}

