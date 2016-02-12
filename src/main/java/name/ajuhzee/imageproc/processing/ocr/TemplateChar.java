package name.ajuhzee.imageproc.processing.ocr;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.ImageUtils;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Represents a template char.
 *
 * @author Ajuhzee
 */
public class TemplateChar {

	private final Image source;

	private final char representedChar;

	/**
	 * Creates a new template character
	 *
	 * @param source
	 * @param representedChar
	 */
	public TemplateChar(Image source, char representedChar) {
		this.source = source;
		this.representedChar = representedChar;
	}

	/**
	 *
	 * @return the source image
	 */
	public Image getSourceImage() {
		return source;
	}

	/**
	 *
	 * @return the represented char
	 */
	public char getRepresentedChar() {
		return representedChar;
	}

	/**
	 * Loads a Template by classPath.
	 *
	 * @param characterSetLocation the data location of the template char
	 * @param characterName the name of the template file
	 * @return the loaded template
	 * @throws Exception if the template could not be loaded
	 */
	public static TemplateChar loadByClassPath(String characterSetLocation, String characterName) throws Exception {
		String resourcePath = characterSetLocation + characterName + ".png";

		InputStream resource = TemplateChar.class.getClassLoader().getResourceAsStream(resourcePath);

		return load(resource, characterName);
	}

	/**
	 * Loads a Template by file.
	 *
	 * @param characterSetLocation the data location of the template char
	 * @param characterName the name of the template file
	 * @return the loaded template
	 * @throws Exception if the template could not be loaded
	 */
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
