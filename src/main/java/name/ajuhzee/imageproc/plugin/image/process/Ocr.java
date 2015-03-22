package name.ajuhzee.imageproc.plugin.image.process;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.BoundingBox;
import name.ajuhzee.imageproc.processing.ocr.ImageOcr;
import name.ajuhzee.imageproc.processing.ocr.RecognizedChar;
import name.ajuhzee.imageproc.processing.ocr.RecognizedLine;
import name.ajuhzee.imageproc.view.OcrMenuController;

/**
 * Adds an image plugin, that provides a menu for a step by step character recognition.
 * 
 * @author Ajuhzee
 *
 */
public class Ocr extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Ocr", true);

	private Optional<List<RecognizedChar>> recognizedCharacters = Optional.empty();

	private Optional<List<RecognizedLine>> recognizedLines = Optional.empty();

	private Optional<String> recognizedText = Optional.empty();

	private final OcrMenuController sideMenu;
	private Image srcImage;

	/**
	 * Positions a Menu-button for the plugin.
	 * 
	 * @param context
	 * @throws PluginLoadException
	 */
	public Ocr(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("ocr", INFO).get(), INFO, context);
		try {
			sideMenu = OcrMenuController.create();
		} catch (final IOException e) {
			throw new PluginLoadException("Couldn't load the side menu", e);
		}
		context().getImageControl().addImageChangedCallback(this::reset);
		sideMenu.getDoneButtonCallbacks().addCallback(this::clearSideMenu);
		sideMenu.getDoneButtonCallbacks().addCallback(this::enablePlugins);
		sideMenu.getRecognizeLineButtonCallbacks().addCallback(this::recognizeLines);
		sideMenu.getSeperateCharactersButtonCallbacks().addCallback(this::seperateCharacters);
		sideMenu.getMatchCharactersButtonCallbacks().addCallback(this::matchCharacters);
	}

	private void assertCharactersRecognized() {
		assertLinesRecognized();
		if (!recognizedCharacters.isPresent()) {
			recognizedCharacters = Optional.of(ImageOcr.recognizeChars(srcImage, recognizedLines.get()));
		}
	}

	private void assertLinesRecognized() {
		if (!recognizedLines.isPresent()) {
			recognizedLines = Optional.of(ImageOcr.recognizeLines(srcImage));
		}
	}

	private void reset(@SuppressWarnings("unused") Image img) {
		recognizedCharacters = Optional.empty();
		recognizedLines = Optional.empty();
		recognizedText = Optional.empty();
	}

	/**
	 * Clears the content of the sideMenu.
	 */
	private void clearSideMenu() {
		context().getSideMenuControl().clearContent();
	}

	private void matchCharacters() {
		assertCharactersMatched();

		showTextOutput();
	}

	private void showTextOutput() {
		assertCharactersMatched();

		sideMenu.setText(recognizedText.get());
	}

	private void assertCharactersMatched() {
		assertCharactersRecognized();
		if (!recognizedText.isPresent()) {
			recognizedText = Optional.of(ImageOcr.matchCharacters(srcImage, recognizedCharacters.get()));
		}
	}

	private void drawLine(PixelWriter writer, Color c, int startX, int endX, int startY, int endY) {
		double stepY;
		double stepX;
		double yLength = (double) endY - startY;
		double xLength = (double) endX - startX;
		if (xLength < yLength) {
			stepY = 1;
			stepX = yLength == 0 ? 0 : (xLength / yLength);
		} else {
			stepX = 1;
			stepY = xLength == 0 ? 0 : (yLength / xLength);
		}

		double y = startY;
		double x = startX;
		do {
			do {
				writer.setColor((int) x, (int) y, c);
				x += stepX;
			} while (x < endX);
			y += stepY;
		} while (y < endY);
	}

	/**
	 * Enables the menu items.
	 */
	private void enablePlugins() {
		context().getMenuControl().enablePlugins();
	}

	private void markCharacters() {
		int width = (int) srcImage.getWidth();
		int height = (int) srcImage.getHeight();
		WritableImage withMarkedLines = new WritableImage(srcImage.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

		int cur = 0;
		for (RecognizedChar recChar : recognizedCharacters.get()) {
			BoundingBox boundingBox = recChar.getBoundingBox();
			int topY = (int) boundingBox.getTopLeft().getY();
			int bottomY = (int) boundingBox.getBottomLeft().getY();
			int leftX = (int) boundingBox.getTopLeft().getX();
			int rightX = (int) boundingBox.getTopRight().getX();
			// String base = "P:\\code\\java\\ImageProc\\src\\main\\resources\\baseTemplate\\";
			// String charName = OcrResources.CHARACTER_LEARNING_ORDER.get(cur++);
			// PixelReader reader = srcImage.getPixelReader();
			// WritableImage charImage = new WritableImage(reader, leftX, topY, (int) boundingBox.getWidth(),
			// (int) boundingBox.getHeight());
			// try {
			// ImageUtils.saveImage(new File(base, charName), charImage);
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

			drawLine(writer, Color.VIOLET, leftX, leftX, topY, bottomY);
			drawLine(writer, Color.VIOLET, leftX, rightX, topY, topY);
			drawLine(writer, Color.RED, rightX, rightX, topY, bottomY);
			drawLine(writer, Color.RED, leftX, rightX, bottomY, bottomY);

		}
		context().getImageControl().showImage(withMarkedLines);
	}

	private void markLines() {
		int width = (int) srcImage.getWidth();
		int height = (int) srcImage.getHeight();
		WritableImage withMarkedLines = new WritableImage(srcImage.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

		recognizedLines.get().stream().forEach((line) -> {
			drawLine(writer, Color.VIOLET, 0, width, line.getTopY(), line.getTopY());
			drawLine(writer, Color.RED, 0, width, line.getBottomY(), line.getBottomY());
		});

		context().getImageControl().showImage(withMarkedLines);
	}

	private void recognizeLines() {
		assertLinesRecognized();
		markLines();
	}

	private void seperateCharacters() {
		assertCharactersRecognized();

		markCharacters();
	}

	@Override
	public void started() {
		srcImage = context().getImageControl().getImage();
		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
		context().getImageControl().showImage(srcImage);

	}
}
