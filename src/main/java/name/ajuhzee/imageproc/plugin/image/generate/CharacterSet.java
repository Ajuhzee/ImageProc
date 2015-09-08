package name.ajuhzee.imageproc.plugin.image.generate;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.BoundingBox;
import name.ajuhzee.imageproc.processing.ocr.ImageOcr;
import name.ajuhzee.imageproc.processing.ocr.OcrResources;
import name.ajuhzee.imageproc.processing.ocr.RecognizedChar;
import name.ajuhzee.imageproc.processing.ocr.RecognizedLine;
import name.ajuhzee.imageproc.util.ImageUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adds an image plugin, which generates a Testimage.
 *
 * @author Ajuhzee
 *
 */
public class CharacterSet extends ImagePlugin {

	private static final boolean REQUIRES_IMAGE = true;
	private static final PluginInformation INFO = new PluginInformation("OCR-Schriftart einlernen", REQUIRES_IMAGE);

	/**
	 * Positions a Menu-button for the plugin.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public CharacterSet(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("generate", "Generieren", 100).subMenu("characterSet", INFO).get(), INFO,
				context);
	}

	@Override
	public void started() {
		context().getGeneralControl().specifyDirectoryDialog(this::createCharacterSet);

	}

	private void createCharacterSet(Path characterSetPath) {
		try {
			Image srcImage = context().getImageControl().getImage();
			List<RecognizedLine> recognizedLines = ImageOcr.recognizeLines(srcImage);
			if (!lineCountValid(recognizedLines)) {
				return;
			}

			List<List<RecognizedChar>> charsPerLine = ImageOcr.recognizeChars(srcImage, recognizedLines);
			if (!linesValid(charsPerLine)) {
				return;
			}

			List<RecognizedChar> recognizedChars = charsPerLine.stream().flatMap((list) -> {
				return list.stream();
			}).collect(Collectors.toList());

			int cur = 0;
			for (RecognizedChar recChar : recognizedChars) {
				BoundingBox boundingBox = recChar.getBoundingBox();
				int topY = (int) boundingBox.getTopLeft().getY();
				int leftX = (int) boundingBox.getTopLeft().getX();

				String charName = OcrResources.CHARACTER_LEARNING_ORDER.get(cur++);
				PixelReader reader = srcImage.getPixelReader();
				WritableImage charImage = new WritableImage(reader, leftX, topY, (int) boundingBox.getWidth(),
						(int) boundingBox.getHeight());
				ImageUtils.saveImage(characterSetPath.resolve(charName), charImage);

			}

			context().getGeneralControl().showPopup("Erfolg", "Die Schriftart wurde erfolgreich gespeichert.");
		} catch (IOException e) {
			context().getGeneralControl().showPopup("Fehler", "Das Speichern ist fehlgeschlagen.");
		}
	}

	private boolean lineCountValid(List<RecognizedLine> recognizedLines) {
		if (recognizedLines.size() != 5) {
			context().getGeneralControl().showPopup("Fehler", "Das Lernbild beinhaltet nicht 5 Zeilen.");
			return false;
		}
		return true;
	}

	private boolean linesValid(List<List<RecognizedChar>> charsPerLine) {
		if (charsPerLine.get(0).size() != 26) {
			context().getGeneralControl().showPopup(
					"Fehler",
					"In der ersten Zeile konnten die 26 Großbuchstaben des Alphabets nicht "
							+ "erkannt werden. Es wurden " + charsPerLine.get(0).size() + " Zeichen erkannt.");
			return false;
		}
		if (charsPerLine.get(1).size() != 26) {
			context().getGeneralControl().showPopup(
					"Fehler",
					"In der zweiten Zeile konnten die 26 Kleinbuchstaben des Alphabets nicht "
							+ "erkannt werden. Es wurden " + charsPerLine.get(1).size() + " Zeichen erkannt.");
			return false;
		}
		if (charsPerLine.get(2).size() != 10) {
			context().getGeneralControl().showPopup(
					"Fehler",
					"In der dritten Zeile konnten die Zahlen 0-9 nicht erkannt werden. Es wurden "
							+ charsPerLine.get(2).size() + " Zeichen erkannt.");
			return false;
		}
		if (charsPerLine.get(3).size() != 17) {
			context().getGeneralControl().showPopup(
					"Fehler",
					"In der vierten Zeile konnten die Sonderzeichen nicht erkannt werden. Es wurden "
							+ charsPerLine.get(3).size() + " Zeichen erkannt.");
			return false;
		}
		if (charsPerLine.get(4).size() != 14) {
			context().getGeneralControl().showPopup(
					"Fehler",
					"In der fünften Zeile konnten die Sonderzeichen nicht erkannt werden. Es wurden "
							+ charsPerLine.get(4).size() + " Zeichen erkannt.");
			return false;
		}
		return true;
	}
}
