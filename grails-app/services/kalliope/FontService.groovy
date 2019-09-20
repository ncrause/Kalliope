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

//import grails.gorm.DetachedCriteria
//import org.hibernate.criterion.
import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import groovy.xml.MarkupBuilder
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

@Service(Font)
abstract class FontService {

    abstract Font get(Serializable id)

    abstract List<Font> list(Map args)

    abstract Long count()

	@Transactional
    abstract void delete(Serializable id)

	@Transactional
    abstract Font save(Font record)
	
	@Transactional @ReadOnly
	abstract Font findByName(String name)
	
	FontVariantService getFontVariantService() {
		return Beans.fontVariantService
	}
	
	/**
	 * Generates CSS <code>@font-face declaration</code>, including all the 
	 * variants
	 * 
	 * @return String
	 */
	String getFontFace(Font font, boolean remote) {
		StringBuilder builder = new StringBuilder()
		
		for (variant in font.variants) {
			builder << Beans.fontVariantService.getFontFace(variant, remote) << "\r\n"
		}
		
		return builder as String
	}
	
	String getFontFace(Font font) {
		getFontFace(font, true)
	}
	
	long getIntradayCount() {
		// instead of doing the below (mainly because it makes no sense when
		// taking into account time-zones), do a query based on the last 24
		// hours instead.
//		// Since GORM/Hibernate provides no usable way to access the
//		// CURRENT_DATE, nor any way of performing a pure date comparison
//		// on a timestamp field, we're left having to actually produce 2
//		// different date objects and perform a SQL "between"
//
//		Calendar calendar = Calendar.getInstance()
//		
//		calendar.set(Calendar.HOUR_OF_DAY, 0)
//		calendar.set(Calendar.MINUTE, 0)
//		calendar.set(Calendar.SECOND, 0)
//		calendar.set(Calendar.MILLISECOND, 0)
//		
//		Date startOfDay = calendar.getTime()
//		
//		calendar.set(Calendar.HOUR_OF_DAY, 23)
//		calendar.set(Calendar.MINUTE, 59)
//		calendar.set(Calendar.SECOND, 59)
//		calendar.set(Calendar.MILLISECOND, 59)
//		
//		Date endOfDay = calendar.getTime()

		Calendar calendar = Calendar.getInstance()
		Date endOfDay = calendar.getTime()
		
		calendar.add(Calendar.HOUR_OF_DAY, -24)
		
		Date startOfDay = calendar.getTime()

		Font.where {
			dateCreated in startOfDay..endOfDay
		}.count() ?: intraweekCount // dirty trick to boost display numbers - if we have 0, expand search criteria
	}
	
	long getIntraweekCount() {
		Calendar calendar = Calendar.getInstance()
		Date endDay = calendar.getTime()
		
		calendar.add(Calendar.DATE, -7)
		
		Date startDay = calendar.getTime()

		Font.where {
			dateCreated in startOfDay..endOfDay
		}.count() ?: intramonthCount
	}
	
	long getIntramonthCount() {
		Calendar calendar = Calendar.getInstance()
		Date endDay = calendar.getTime()
		
		calendar.add(Calendar.DATE, -30)
		
		Date startDay = calendar.getTime()

		Font.where {
			dateCreated in startOfDay..endOfDay
		}.count()
	}
	
//	List<Font> listByTransitory(boolean choice, Map params) {
//		def query = Font.where {
//			transitory == choice
//		}
//		
//		return query.list(params)
//	}
//	
//	List<Font> listRecent(boolean transitory, int count) {
//		listByTransitory(transitory, [max: count, sort: "dateCreated", order: "desc"])
//	}

	List<Font> listRecent(boolean transitory, int count) {
		def c = Font.createCriteria()
		
		return c.list(max: count) {
			eq("transitory", transitory)
			order("dateCreated", "desc")
		}
	}
	
	byte[] zip(Font font) {
		ByteArrayOutputStream bytes= new ByteArrayOutputStream()
		ZipOutputStream zip = new ZipOutputStream(bytes)
		
		zip.setMethod(ZipOutputStream.DEFLATED)
		zip.setLevel(9)
		
		zipCSS(zip, font)
		zipFontFiles(zip, font)
		zipExampleHTML(zip, font)
		
		zip.finish()
		
		return bytes.toByteArray()
	}
	
	String fileBasename(Font font) {
		return Beans.sanitizeService.sanitizeWithDashes(font.name)
	}
	
	private void zipCSS(ZipOutputStream zip, Font font) {
		String base = fileBasename(font)
		
		zip.putNextEntry(new ZipEntry("${base}.css"))
		
		zip << getFontFace(font, false)
		
		zip.closeEntry()
	}
	
	private void zipFontFiles(ZipOutputStream zip, Font font) {
		for (FontVariant fontVariant in font.variants) {
			zipVariantFiles(zip, fontVariant)
		}
	}
	
	private void zipVariantFiles(ZipOutputStream zip, FontVariant fontVariant) {
		String base = fontVariantService.fileBasename(fontVariant)
		
		zipVariantFile(zip, "${base}.eot", fontVariant.eot)
		zipVariantFile(zip, "${base}.woff", fontVariant.woff)
		zipVariantFile(zip, "${base}.ttf", fontVariant.ttf)
		zipVariantFile(zip, "${base}.svg", fontVariant.svg)
	}
	
	private void zipVariantFile(ZipOutputStream zip, String name, byte[] data) {
		zip.putNextEntry(new ZipEntry(name))
		
		zip << data
		
		zip.closeEntry()
	}
	
	private void zipExampleHTML(ZipOutputStream zip, Font font) {
		String base = fileBasename(font)
		PrintWriter out = new PrintWriter(zip)
		MarkupBuilder builder = new MarkupBuilder(out)
		
		zip.putNextEntry(new ZipEntry("${base}.html"))
		builder.doubleQuotes = true
		
		zip << "<!doctype html>\n"
		builder.html {
			head {
				link(type: "text/css", rel: "stylesheet", href: "${base}.css")
			}
			
			body {
				font.variants.each { variant ->
					div(style: variant.inlineCSS, "The quick brown fox jumps over the lazy dog")
				}
			}
		}
		
		out.flush()
		zip.closeEntry()
	}
	
}
