package name.ajuhzee.imageproc.processing.ocr;

import java.io.InputStream;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.ImageUtils;

public class TemplateChar {

	private final Image source;

	private final char representedChar;

	public TemplateChar(Image source, char representedChar) {
		this.source = source;
		this.representedChar = representedChar;
	}

	public Image getSourceImage() {
		return source;
	}

	public char getRepresentedChar() {
		return representedChar;
	}

	public static TemplateChar load(String templateName, String characterName) throws Exception {
		String resourcePath = templateName + "/" + characterName + ".png";

		InputStream resource = TemplateChar.class.getClassLoader().getResourceAsStream(resourcePath);
		Image charImage = ImageUtils.loadImage(resource);

		return new TemplateChar(charImage, OcrResources.CHARACTER_NAMES.get(characterName));
	}
}
