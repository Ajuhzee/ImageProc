package name.ajuhzee.imageproc.plugin.image.ocr;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.Area;
import name.ajuhzee.imageproc.processing.ocr.ImageOcr;
import name.ajuhzee.imageproc.processing.ocr.OcrResources;
import name.ajuhzee.imageproc.processing.ocr.RecognizedChar;
import name.ajuhzee.imageproc.processing.ocr.RecognizedLine;
import name.ajuhzee.imageproc.util.ImageUtils;
import name.ajuhzee.imageproc.view.LearnCharacterSetController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adds an image plugin, which generates a Testimage.
 *
 * @author Ajuhzee
 */
public class LearnCharacterSet extends ImagePlugin {

	private static final boolean REQUIRES_IMAGE = true;

	private static final PluginInformation INFO = new PluginInformation("OCR-Schriftart einlernen", REQUIRES_IMAGE);

	private LearnCharacterSetController sideMenu;

	private Image srcImage;

	/**
	 * Positions a Menu-button for the plugin.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public LearnCharacterSet(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("ocr", "OCR", 100).subMenu("characterSet", INFO).get(), context);

		try {
			sideMenu = LearnCharacterSetController.create();
		} catch (final IOException e) {
			throw new PluginLoadException("Couldn't load the side menu", e);
		}

		sideMenu.getLearnCharacterSetPressedCallbacks().addCallback(this::findCharacterSetDirectory);
		sideMenu.getCancelCallbacks().addCallback(this::unload);
		sideMenu.getTestCharSeparationPressedCallbacks().addCallback(this::seperateCharacters);
	}

	private void seperateCharacters() {
		List<RecognizedLine> recognizedLines = ImageOcr.recognizeLines(srcImage);

		List<List<RecognizedChar>> recognizedCharsInLines =
				ImageOcr.recognizeChars(srcImage, recognizedLines, sideMenu.getMinimumCharacterGapPx());

		List<RecognizedChar> recognizedChars =
				recognizedCharsInLines.stream().flatMap(List::stream).collect(Collectors.toList());

		context().getImageControl().showImage(ImageUtils.markCharactersOnImage(srcImage, recognizedChars));
	}

	private void unload() {
		context().getImageControl().showImage(srcImage);
		context().getSideMenuControl().clearContent();
		context().getMenuControl().enablePlugins();
	}

	@Override
	public void started() {
		context().getMenuControl().disablePlugins();
		srcImage = context().getImageControl().getImage();
		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
	}

	private void findCharacterSetDirectory() {
		context().getGeneralControl().specifyDirectoryDialog(this::createCharacterSet);
	}

	private void createCharacterSet(Path characterSetPath) {
		try {
			List<RecognizedLine> recognizedLines = ImageOcr.recognizeLines(srcImage);
			if (!lineCountValid(recognizedLines)) {
				return;
			}

			List<List<RecognizedChar>> charsPerLine =
					ImageOcr.recognizeChars(srcImage, recognizedLines, sideMenu.getMinimumCharacterGapPx());
			if (!linesValid(charsPerLine)) {
				return;
			}

			List<RecognizedChar> recognizedChars =
					charsPerLine.stream().flatMap(Collection::stream).collect(Collectors.toList());

			int cur = 0;
			for (RecognizedChar recChar : recognizedChars) {
				Area boundingBox = recChar.getBoundingBox();
				int topY = (int) boundingBox.getTopLeft().getY();
				int leftX = (int) boundingBox.getTopLeft().getX();

				String charName = OcrResources.CHARACTER_LEARNING_ORDER.get(cur++);
				PixelReader reader = srcImage.getPixelReader();
				WritableImage charImage = new WritableImage(reader, leftX, topY, (int) boundingBox.getWidth(),
						(int) boundingBox.getHeight());
				ImageUtils.saveImage(characterSetPath.resolve(charName), charImage);

			}

			context().getGeneralControl()
					.showPopup("Erfolg", "Die Schriftart wurde erfolgreich gespeichert.", this::unload);
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
