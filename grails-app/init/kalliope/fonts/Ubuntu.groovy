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
class Ubuntu {
	
	void run() {
		Font font = new Font(name: "Ubuntu", category: Font.Category.SANS_SERIF, transitory: false)
		
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.NORMAL,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-Regular.ttf").bytes, 
				originalFilename: "Ubuntu-Regular.ttf"))
		font.addToVariants(new FontVariant(italic: true,
				weight: FontVariant.Weight.NORMAL,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-RegularItalic.ttf").bytes, 
				originalFilename: "Ubuntu-RegularItalic.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.BOLD,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-Bold.ttf").bytes, 
				originalFilename: "Ubuntu-Bold.ttf"))
		font.addToVariants(new FontVariant(italic: true,
				weight: FontVariant.Weight.BOLD,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-BoldItalic.ttf").bytes, 
				originalFilename: "Ubuntu-BoldItalic.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.LIGHT,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-Light.ttf").bytes, 
				originalFilename: "Ubuntu-Light.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.LIGHT,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-LightItalic.ttf").bytes, 
				originalFilename: "Ubuntu-LightItalic.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.MEDIUM,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-Medium.ttf").bytes, 
				originalFilename: "Ubuntu-Medium.ttf"))
		font.addToVariants(new FontVariant(italic: true,
				weight: FontVariant.Weight.MEDIUM,
				stretch: FontVariant.Stretch.NORMAL,
				original: new File("fixtures/assets/fonts/ubuntu/Ubuntu-MediumItalic.ttf").bytes, 
				originalFilename: "Ubuntu-MediumItalic.ttf"))

		font.save()
	}
	
}

