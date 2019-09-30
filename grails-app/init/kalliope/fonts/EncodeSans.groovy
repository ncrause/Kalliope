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
class EncodeSans {
	
	void run() {
		Font font = new Font(name: "Encode Sans", category: Font.Category.SANS_SERIF, transitory: false)
		// ----------------------------------------------------------------
		// Normal Stretch
		// ----------------------------------------------------------------
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.THIN,
				stretch: FontVariant.Stretch.NORMAL, 
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Thin.ttf").bytes,
				originalFilename: "EncodeSans-Thin.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.EXTRA_LIGHT,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-ExtraLight.ttf").bytes, 
				originalFilename: "EncodeSans-ExtraLight.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.LIGHT,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Light.ttf").bytes, 
				originalFilename: "EncodeSans-Light.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.NORMAL,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Regular.ttf").bytes, 
				originalFilename: "EncodeSans-Regular.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.MEDIUM,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Medium.ttf").bytes, 
				originalFilename: "EncodeSans-Medium.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.SEMI_BOLD,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-SemiBold.ttf").bytes, 
				originalFilename: "EncodeSans-SemiBold.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.BOLD,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Bold.ttf").bytes, 
				originalFilename: "EncodeSans-Bold.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.EXTRA_BOLD,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-ExtraBold.ttf").bytes, 
				originalFilename: "EncodeSans-ExtraBold.ttf"))
		font.addToVariants(new FontVariant(italic: false,
				weight: FontVariant.Weight.BLACK,
				stretch: FontVariant.Stretch.NORMAL,
				original: getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/EncodeSans-Black.ttf").bytes, 
				originalFilename: "EncodeSans-Black.ttf"))
		// ----------------------------------------------------------------
		// Stretches
		// ----------------------------------------------------------------
		["SemiCondensed": FontVariant.Stretch.SEMI_CONDENSED,
				"Condensed": FontVariant.Stretch.CONDENSED,
				// there appears to be something wrong with the expanded
				// fonts ... FontForge keeps saying they're corrupt
				/*"SemiExpanded": FontVariant.Stretch.SEMI_EXPANDED,
				"Expanded": FontVariant.Stretch.EXPANDED*/].each { stretch ->
			["Thin": FontVariant.Weight.THIN,
					"ExtraLight": FontVariant.Weight.EXTRA_LIGHT,
					"Light": FontVariant.Weight.LIGHT,
					"Regular": FontVariant.Weight.NORMAL,
					"Medium": FontVariant.Weight.MEDIUM,
					"SemiBold": FontVariant.Weight.SEMI_BOLD,
					"Bold": FontVariant.Weight.BOLD,
					"Black": FontVariant.Weight.BLACK].each { weight ->
				String filename = "EncodeSans${stretch.key}-${weight.key}.ttf"
				URL resource = getClass().getClassLoader().getResource("fixtures/assets/fonts/encode-sans/${filename}")

//				if (resource.exists()) {
				if (resource) {
					font.addToVariants(new FontVariant(italic: false,
							weight: weight.value, stretch: stretch.value,
							original: resource.bytes, originalFilename: filename))
				}
			}
		}

		font.save()
	}
	
}

