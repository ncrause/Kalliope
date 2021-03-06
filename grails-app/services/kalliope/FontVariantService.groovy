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

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import java.awt.Canvas
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

import java.awt.Font as AWTFont
import kalliope.utils.Values

@Service(FontVariant)
abstract class FontVariantService {

    abstract FontVariant get(Serializable id)

    abstract List<FontVariant> list(Map args)

    abstract Long count()

	@Transactional
    abstract void delete(Serializable id)

	@Transactional
    abstract FontVariant save(FontVariant record)
	
	/**
	 * Generates CSS @font-face declaration for this single variant
	 * 
	 * @param baseURL the URL which contains the controller + action which will
	 * be returned to handle retrieval of the font
	 */
	String getFontFace(FontVariant variant, boolean remote) {
		String baseURL = Beans.grailsLinkGenerator.link(controller: "public", action: "get")
		String pathBase = remote ? "${baseURL}/${variant.id}" : fileBasename(variant)
		
		return """
@font-face {
	font-family: "${variant.font.name}";
	src: url("${pathBase}.eot");
	src: local("☺"),
		url("${pathBase}.woff") format("woff"),
		url("${pathBase}.ttf") format("truetype"),
		url("${pathBase}.svg") format("svg");
	font-weight: ${variant.weight.number()};
	font-style: ${variant.italic ? "italic" : "normal"};
	font-stretch: ${variant.stretch.cssKeyword()};
}
		"""
	}
	
	String getFontFace(FontVariant variant) {
		getFontFace(variant, true)
	}
	
	void convertFont(FontVariant variant) {
		variant.converted = true
		
		Map results = convertFont(variant.original, variant.originalFilename)
		
		variant.ttf = results.ttf
		variant.eot = results.eot
		variant.woff = results.woff
		variant.svg = results.svg
		
		// @see https://stackoverflow.com/a/18123024/251930
		InputStream fontStream = new ByteArrayInputStream(variant.ttf ?: variant.original)
		
		try {
			AWTFont sourceFont = AWTFont.createFont(AWTFont.TRUETYPE_FONT, fontStream)
			// we set size to 72 since "points" are 1/72th of an inch, so let's
			// make it a full and even 1 inch
			AWTFont awtFont = sourceFont.deriveFont(72.0f)
			Canvas canvas = new Canvas()
			FontMetrics metrics = canvas.getFontMetrics(awtFont)
//			BufferedImage offscreenBuffer = new BufferedImage(100, 100, BufferedImage.TYPE_4BYTE_ABGR)
//			Graphics2D offscreenGraphics = offscreenBuffer.createGraphics()
//			FontMetrics metrics = offscreenGraphics.getFontMetrics(awtFont)
			
			variant.maxAscent = metrics.maxAscent
			variant.maxDescent = metrics.maxDescent
			variant.leading = metrics.leading
			variant.maxAdvance = metrics.maxAdvance
			variant.pointSize = awtFont.size2D
		}
		finally {
			fontStream.close()
		}
	}
	
	/**
	 * This is the primary conversion worker. Given a byte array of raw
	 * file data and the original file's name (which is relevant because it
	 * might be needed by FontForge), this method will generate 4 font formats
	 * (ttf, eot, woff and svg) and return them out in a Map.
	 */
	Map convertFont(byte[] source, String originalFilename, boolean ttf, boolean eot, boolean woff, boolean svg) {
		File tmpDir = getTemporaryDirectory()
		File script = getScriptFile(tmpDir)
		File tmp = new File(tmpDir, originalFilename)
		
		if (!tmp.exists()) {
			tmp << source
		}
		
		// TODO: once the TODO in getScriptFile() is completed, pass in arguments
		// to control which conversions are to actually be done
		ProcessBuilder processBuilder = new ProcessBuilder(locateFontforgeExecutable(), "-script", script.absolutePath, tmp.absolutePath)
		Process process = processBuilder.start()
		StringBuffer stdOut = new StringBuffer()
		StringBuffer stdErr = new StringBuffer()

		// halt the thread until the process is finished
		process.waitForProcessOutput(stdOut, stdErr)

		if (process.exitValue() != 0) {
			throw new ConversionException("Failed to convert font!\n\nOutput:\n${stdOut}\n\nError:\n${stdErr}")
		}

		String baseName = org.apache.commons.io.FilenameUtils.getBaseName(tmp.absolutePath)

		Map results = [:]
		
		if (ttf) {
			results.ttf = readFontFile("${baseName}.ttf")
		}
		if (eot) {
			results.eot = readFontFile("${baseName}.eot")
		}
		if (woff) {
			results.woff = readFontFile("${baseName}.woff")
		}
		if (svg) {
			results.svg = readFontFile("${baseName}.svg")
		}
		
		return results
	}
	
	Map convertFont(byte[] source, String originalFilename) {
		return convertFont(source, originalFilename, true, true, true, true)
	}
	
	/**
	 * The below is the list of directories that will be checked for the
	 * fontforge binary file. This list was determined based on the location
	 * into which it is installed on Ubuntu, FreeBSD and MacOS X
	 */
	private static String[] FONTFORGE_DIRECTORIES = [
		"/usr/local/bin",
		"/usr/bin"
	]
	
	private String fontforgeExecutable
	
	private String locateFontforgeExecutable() {
		if (fontforgeExecutable) {
			return fontforgeExecutable
		}
		
		// if we don't have it set as yet, try to find it!
		for (String dir in FONTFORGE_DIRECTORIES) {
			File test = new File(dir, "fontforge")
			
			if (test.exists() && test.canExecute()) {
				return (fontforgeExecutable = test.absolutePath)
			}
		}
		
		// if it wasn't found at all, throw an exception
		throw new FileNotFoundException("Unable to locate the 'fontforge' executable")
	}
	
	private byte[] readFontFile(filename) {
		File file = new File(getTemporaryDirectory(), filename)
		
		return file.bytes
	}
	
	private File getTemporaryDirectory() {
		File dir = new File(System.getProperty("java.io.tmpdir"))
		
		// Inability to write to tmp directory is a critical condition
		if (!dir.canWrite()) {
			throw new ConversionException("Unable to write to tmp directory ${System.getProperty("java.io.tmpdir")}")
		}
		
		return dir
	}
	
	private File getScriptFile(File inDirectory) {
		File file = new File(inDirectory, "ffconvert.pe")
		
		// if the script file doesn't exist, create it!
		if (!file.exists()) {
			def input = """
				Open(\$1)
				Reencode("unicode")
				SelectWorthOutputting()
				SelectInvert()
				BuildAccented()
				Generate(\$1:r + ".ttf")
				Generate(\$1:r + ".eot")
				Generate(\$1:r + ".woff")
				Generate(\$1:r + ".svg")
			""".stripIndent()

			file << input
		}
		
		return file
	}
	
	String fileBasename(FontVariant fontVariant) {
		return Beans.sanitizeService.sanitizeWithDashes(fontVariant as String)
	}

	List<FontVariant> search(Map args) {
		FontVariant.withCriteria {
			font {
				eq("transitory", false)
			}
			
			if (args.containsKey("category") && !Values.isBlank(args.category)) {
				Font.Category category = args.category instanceof String ? Font.Category.valueOf(args.category) : args.category
				
				font {
					eq("category", category)
				}
			}
			
			if (args.containsKey("weight") && !Values.isBlank(args.weight)) {
				FontVariant.Weight weight = args.weight instanceof String ? FontVariant.Weight.valueOf(args.weight) : args.weight
				
				eq("weight", weight)
			}
			
			if (args.containsKey("stretch") && !Values.isBlank(args.stretch)) {
				FontVariant.Stretch stretch = args.stretch instanceof String ? FontVariant.Stretch.valueOf(args.stretch) : args.stretch
				
				eq("stretch", stretch)
			}
			
			if (args.containsKey("italic") && !Values.isBlank(args.italic)) {
				eq("italic", args.italic)
			}
			
			if (args.containsKey("advance") && !Values.isBlank(args.advance)) {
				double advance = args.advance as Double
				
				// whatever width/advance we're looking for, create a 10% envelope around it
				between("maxAdvance", Math.round(advance / 1.1) as Integer, Math.round(advance * 1.1) as Integer)
			}
			
			font {
				order("name")
			}
			order("italic")
		}
	}
	
	int[] advanceLimits() {
		FontVariant.withCriteria(uniqueResult: true) {
			font {
				eq("transitory", false)
			}
			
			projections {
				min("maxAdvance")
				max("maxAdvance")
			}
		}
	}
	
	/**
	 * Use this to try to determine the "default" variant of a particular font
	 */
	FontVariant defaultVariant(Font font) {
		FontVariant variant = font.variants.find { it.weight == FontVariant.Weight.NORMAL && it.stretch == FontVariant.Stretch.NORMAL && !it.italic }
		
		return variant ?: font.variants.first
	}
	
}
