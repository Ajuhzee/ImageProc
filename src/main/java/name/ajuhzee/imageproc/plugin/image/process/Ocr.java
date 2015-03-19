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
import name.ajuhzee.imageproc.processing.ImageOcr;
import name.ajuhzee.imageproc.processing.RecognizedChar;
import name.ajuhzee.imageproc.processing.RecognizedLine;
import name.ajuhzee.imageproc.view.OcrMenuController;

/**
 * Adds an image plugin, that provides a menu for a step by step character recognition.
 * 
 * @author Ajuhzee
 *
 */
public class Ocr extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Ocr", true);

	private Image srcImage;

	private final OcrMenuController sideMenu;

	private Optional<List<RecognizedLine>> recognizedLines = Optional.empty();

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
		sideMenu.getDoneButtonCallbacks().addCallback(this::clearSideMenu);
		sideMenu.getDoneButtonCallbacks().addCallback(this::enablePlugins);
		sideMenu.getRecognizeLineButtonCallbacks().addCallback(this::recognizeLines);
		sideMenu.getSeperateCharactersButtonCallbacks().addCallback(this::seperateCharacters);
	}

	/**
	 * Enables the menu items.
	 */
	private void enablePlugins() {
		context().getMenuControl().enablePlugins();
	}

	/**
	 * Clears the content of the sideMenu.
	 */
	private void clearSideMenu() {
		context().getSideMenuControl().clearContent();
	}

	@Override
	public void started() {
		srcImage = context().getImageControl().getImage();
		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
		context().getImageControl().showImage(srcImage);

	}

	private void seperateCharacters() {
		assertLinesRecognized();
		List<RecognizedChar> recognizedChars = ImageOcr.recognizeChars(srcImage, recognizedLines.get());

		int width = (int) srcImage.getWidth();
		int height = (int) srcImage.getHeight();
		WritableImage withMarkedLines = new WritableImage(srcImage.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

		for (RecognizedChar recChar : recognizedChars) {
			int topY = (int) recChar.getBoundingBox().getTopLeft().getY();
			int bottomY = (int) recChar.getBoundingBox().getBottomLeft().getY();
			int leftX = (int) recChar.getBoundingBox().getTopLeft().getX();
			int rightX = (int) recChar.getBoundingBox().getTopRight().getX();

			for (int y = topY; y < bottomY; ++y) {
				writer.setColor(leftX, y, Color.VIOLET);
			}
			for (int y = topY; y < bottomY; ++y) {
				writer.setColor(rightX, y, Color.RED);
			}
		}
		context().getImageControl().showImage(withMarkedLines);
	}

	private void recognizeLines() {
		assertLinesRecognized();
		int width = (int) srcImage.getWidth();
		int height = (int) srcImage.getHeight();
		WritableImage withMarkedLines = new WritableImage(srcImage.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

		recognizedLines.get().stream().forEach((line) -> {
			for (int x = 0; x != width; ++x) {
				writer.setColor(x, line.getTopY(), Color.VIOLET);
			}
			for (int x = 0; x != width; ++x) {
				writer.setColor(x, line.getBottomY(), Color.RED);
			}
		});

		context().getImageControl().showImage(withMarkedLines);
	}

	private void assertLinesRecognized() {
		if (!recognizedLines.isPresent()) {
			recognizedLines = Optional.of(ImageOcr.recognizeLines(srcImage));
		}
	}
}
