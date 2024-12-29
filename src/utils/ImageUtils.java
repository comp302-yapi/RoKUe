package utils;

import java.awt.Graphics2D;

import java.awt.image.BufferedImage;
import java.io.Serial;
import java.io.Serializable;

public class ImageUtils implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L; // Add this for versioning
	
	public BufferedImage scaleImage(BufferedImage original, int width, int height) {
		BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
		Graphics2D g2 = scaledImage.createGraphics();
		g2.drawImage(original, 0, 0, width, height, null);
		g2.dispose();
		
		return scaledImage;
	}
}
