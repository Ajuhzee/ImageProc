package name.ajuhzee.imageproc.processing.ocr;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class CharacterSet {

	public static final CharacterSet BASE_CHARACTER_SET = loadByClassPath("baseCharacterSet");

	private final List<TemplateChar> characters;

	private final int spaceWidth;

	private final String name;

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

	public List<TemplateChar> getCharacters() {
		return characters;
	}

	public String getName() {
		return name;
	}
}
