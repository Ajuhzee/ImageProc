package name.ajuhzee.imageproc.processing.ocr;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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

	public static TemplateChar loadByClassPath(String characterSetLocation, String characterName) throws Exception {
		String resourcePath = characterSetLocation + characterName + ".png";

		InputStream resource = TemplateChar.class.getClassLoader().getResourceAsStream(resourcePath);

		return load(resource, characterName);
	}

	public static TemplateChar loadByFile(Path characterSetLocation, String characterName) throws Exception {
		String charFileName = characterName + ".png";
		Path characterPath = characterSetLocation.resolve(charFileName);

		return load(Files.newInputStream(characterPath), characterName);
	}

	private static TemplateChar load(InputStream stream, String characterName) throws Exception {
		Image charImage = ImageUtils.loadImage(stream);

		return new TemplateChar(charImage, OcrResources.CHARACTER_NAMES.get(characterName));
	}
}
