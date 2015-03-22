package name.ajuhzee.imageproc.processing.ocr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

public enum Templates {
	BASE_TEMPLATE("baseTemplate");

	private final List<TemplateChar> characters;

	private Templates(String name) {
		try {
			characters = loadCharacters(name);
		} catch (Exception e) {
			System.err.println("Fatal: OCR Template loading failed");
			throw new RuntimeException(e);
		}
	}

	private static List<TemplateChar> loadCharacters(String name) throws Exception {
		List<TemplateChar> templateChars = new ArrayList<>();

		for (Entry<String, Character> characterName : OcrResources.CHARACTER_NAMES.entrySet()) {
			TemplateChar templateChar = TemplateChar.load(name, characterName.getKey());
			templateChars.add(templateChar);
		}

		return templateChars;
	}

	public List<TemplateChar> getCharacters() {
		return characters;
	}
}
