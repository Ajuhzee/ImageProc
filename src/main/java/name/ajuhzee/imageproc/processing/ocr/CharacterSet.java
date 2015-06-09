package name.ajuhzee.imageproc.processing.ocr;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * The character set that is needed to match characters, when using the ocr feature.
 * 
 * @author Ajuhzee
 *
 */
public class CharacterSet {

	/**
	 * Sets the Base Character Set, for the case that no extra set was loaded before.
	 */
	public static final CharacterSet BASE_CHARACTER_SET = loadByClassPath("baseCharacterSet");

	private final List<TemplateChar> characters;

	private final int spaceWidth;

	private final String name;

	/**
	 * Creates the character set.
	 * 
	 * @param name
	 *            the name of the character set
	 * @param characters
	 *            the template list of the character set
	 */
	public CharacterSet(String name, List<TemplateChar> characters) {
		this.name = name;
		this.characters = characters;
		spaceWidth = calculateSpaceWidth(characters);
	}

	private static CharacterSet loadByClassPath(String name) {
		try {
			String characterSetPath = name + "/";
			List<TemplateChar> characters = loadCharactersByClassPath(characterSetPath);
			return new CharacterSet(name, characters);
		} catch (Exception e) {
			// should never happen
			throw new RuntimeException(e);
		}

	}

	/**
	 * Loads a character set from a directory.
	 * 
	 * @param characterSetLocation
	 *            the location path
	 * @return the character set
	 */
	public static CharacterSet loadByFile(Path characterSetLocation) {
		try {
			if (!Files.isDirectory(characterSetLocation)) {
				throw new RuntimeException("character set has to be in a folder");
			}
			String name = characterSetLocation.getFileName().toString();
			List<TemplateChar> characters = loadCharactersByFolder(characterSetLocation);
			return new CharacterSet(name, characters);
		} catch (Exception e) {
			System.err.println("Fatal: OCR Template loading failed");
			throw new RuntimeException(e);
		}

	}

	private static List<TemplateChar> loadCharactersByFolder(Path characterSetLocation) throws Exception {
		List<TemplateChar> templateChars = new ArrayList<>();

		for (Entry<String, Character> characterName : OcrResources.CHARACTER_NAMES.entrySet()) {
			TemplateChar templateChar = TemplateChar.loadByFile(characterSetLocation, characterName.getKey());
			templateChars.add(templateChar);
		}

		return templateChars;
	}

	private static int calculateSpaceWidth(List<TemplateChar> characters) {
		return characters.stream().collect(Collectors.averagingDouble((character) -> {
			return character.getSourceImage().getWidth();
		})).intValue();
	}

	/**
	 * @return the space width
	 */
	public int getSpaceWidth() {
		return spaceWidth;
	}

	private static List<TemplateChar> loadCharactersByClassPath(String characterSetPath) throws Exception {
		List<TemplateChar> templateChars = new ArrayList<>();

		for (Entry<String, Character> characterName : OcrResources.CHARACTER_NAMES.entrySet()) {
			TemplateChar templateChar = TemplateChar.loadByClassPath(characterSetPath, characterName.getKey());
			templateChars.add(templateChar);
		}

		return templateChars;
	}

	/**
	 * @return the character list of the set
	 */
	public List<TemplateChar> getCharacters() {
		return characters;
	}

	/**
	 * @return the character set name
	 */
	public String getName() {
		return name;
	}
}
