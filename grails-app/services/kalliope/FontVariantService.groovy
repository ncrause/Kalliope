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
		ProcessBuilder processBuilder = new ProcessBuilder("fontforge", "-script", script.absolutePath, tmp.absolutePath)
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
	
}
