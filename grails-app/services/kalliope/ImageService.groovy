package kalliope

import grails.gorm.transactions.Transactional
import java.awt.Color
import java.awt.image.BufferedImage
import javax.imageio.IIOImage
import javax.imageio.ImageIO
import javax.imageio.ImageWriteParam
import javax.imageio.ImageWriter
import javax.imageio.stream.MemoryCacheImageOutputStream

import java.awt.Font as AWTFont
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.Rectangle2D

/**
 * This service is used to produce graphic images of a font variant.
 */
class ImageService {
	
	byte[] generatePNG(FontVariant variant, int width, int height, String text, Color foreground, Color background) {
		BufferedImage image = generate(variant, width, height, text, foreground, background)
		ByteArrayOutputStream output = new ByteArrayOutputStream()
		MemoryCacheImageOutputStream stream = new MemoryCacheImageOutputStream(output)
		ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next()
		ImageWriteParam params = writer.getDefaultWriteParam()
		IIOImage source = new IIOImage(image, null, null)
		
		writer.setOutput(stream)
		writer.write(null, source, params)
		writer.dispose()
		
		return output.toByteArray()
	}

	/**
	 * Generates an image
	 * 
	 * The font size will automatically be adjusted such that the text will
	 * always fit within the bounds defined.
	 * 
	 * @param fontVariant - the source of the font data
	 * @param width - the target image width in pixels
	 * @param height - the target height in pixels
	 */
    BufferedImage generate(FontVariant variant, int width, int height, String text, Color foreground, Color background) {
		AWTFont sourceFont = load(variant)
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		Graphics2D graphics = image.createGraphics()
		def (float size, Rectangle2D bounds) = calculateFontSize(sourceFont, graphics, text, width, height)
		AWTFont font = sourceFont.deriveFont(size)
		FontMetrics metrics = graphics.getFontMetrics(font)
		
		// background fill
		graphics.setPaint(background)
		graphics.fillRect(0, 0, width, height)
		// write the text
		graphics.setPaint(foreground)
		graphics.setColor(foreground)
		graphics.setFont(font)
		// the Y position is based on a combination of the image height, and
		// max ascent (because the placement is based on the baseline)
		int y = (height - bounds.height) / 2 + metrics.ascent
		// we want some nice antialiasing
		graphics.setRenderingHint(
				RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_GASP)
		graphics.drawString(text, 0, y)
		
		graphics.dispose()
		
		return image
    }
	
	private AWTFont load(FontVariant variant) {
		InputStream stream = new ByteArrayInputStream(variant.ttf ?: variant.original)
		
		return AWTFont.createFont(AWTFont.TRUETYPE_FONT, stream)
	}
	
	private def calculateFontSize(AWTFont font, Graphics2D graphics, String text, int width, int height) {
		float current = 72.0
		Rectangle2D bounds
		
		while(current > 1.0) {
			FontMetrics metrics = graphics.getFontMetrics(font.deriveFont(current))
			bounds = metrics.getStringBounds(text, graphics)
			
			if (bounds.width <= width && bounds.height <= height) {
				break
			}
			
			current -= 0.25
		}
		
		return [current, bounds]
	}
	
}
