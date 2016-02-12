package name.ajuhzee.imageproc.plugin.image.ocr;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.processing.ocr.CharacterSet;
import name.ajuhzee.imageproc.processing.ocr.ImageOcr;
import name.ajuhzee.imageproc.processing.ocr.RecognizedChar;
import name.ajuhzee.imageproc.processing.ocr.RecognizedLine;
import name.ajuhzee.imageproc.util.ImageUtils;
import name.ajuhzee.imageproc.view.OcrMenuController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adds an image plugin, that provides a menu for a step by step character recognition.
 *
 * @author Ajuhzee
 */
public class Ocr extends ImagePlugin {

	public static final Color START_COLOR = Color.LIMEGREEN;

	public static final Color END_COLOR = Color.ORANGERED;

	private static final PluginInformation INFO = new PluginInformation("Texterkennung", true);

	private final OcrMenuController sideMenu;

	private Optional<List<List<RecognizedChar>>> recognizedLineCharacters = Optional.empty();

	private Optional<List<RecognizedLine>> recognizedLines = Optional.empty();

	private Optional<String> recognizedText = Optional.empty();

	private CharacterSet currentCharacterSet = CharacterSet.BASE_CHARACTER_SET;

	private boolean imageAdjusted = false;

	private Image srcImage;


	/**
	 * Creates the Ocr plugin and positions a Menu-button for it.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Ocr(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("ocr", "OCR", 100).subMenu("ocr", INFO).get(), context);
		try {
			sideMenu = OcrMenuController.create();
		} catch (final IOException e) {
			throw new PluginLoadException("Couldn't load the side menu", e);
		}

		sideMenu.getDoneButtonCallbacks().addCallback(this::clearSideMenu);
		sideMenu.getDoneButtonCallbacks().addCallback(this::restoreSrcImage);
		sideMenu.getDoneButtonCallbacks().addCallback(this::enablePlugins);
		sideMenu.getAdjustImageButtonCallbacks().addCallback(this::assertImageAdjusted);
		sideMenu.getRecognizeLineButtonCallbacks().addCallback(this::recognizeLines);
		sideMenu.getSeperateCharactersButtonCallbacks().addCallback(this::seperateCharacters);
		sideMenu.getMatchCharactersButtonCallbacks().addCallback(this::matchCharacters);
		sideMenu.getSelectCharacterSetButtonCallbacks().addCallback(this::selectCharacterSet);
	}

	private void restoreSrcImage() {
		context().getImageControl().showImage(srcImage);
	}

	private void assertImageAdjusted() {
		if (!imageAdjusted && sideMenu.shouldAdjustImage()) {
			reset();
			srcImage = ImageOcr.adjustImage(srcImage);
			context().getImageControl().showImage(srcImage);
			imageAdjusted = true;
		}
	}

	private void selectCharacterSet() {
		context().getGeneralControl().specifyDirectoryDialog(
				(path) -> currentCharacterSet = CharacterSet.loadByFile(path));
	}

	private void assertCharactersRecognized() {
		assertLinesRecognized();
		if (!recognizedLineCharacters.isPresent()) {
			recognizedLineCharacters = Optional.of(
					ImageOcr.recognizeChars(srcImage, recognizedLines.get(), sideMenu.getMinimumCharacterGapPx()));
		}
	}

	private void assertLinesRecognized() {
		assertImageAdjusted();

		if (!recognizedLines.isPresent()) {
			recognizedLines = Optional.of(ImageOcr.recognizeLines(srcImage));
		}
	}

	private void reset() {
		imageAdjusted = false;
		recognizedLineCharacters = Optional.empty();
		recognizedLines = Optional.empty();
		recognizedText = Optional.empty();
		sideMenu.setText("");
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
		boolean pixelDeviationEnabled = sideMenu.isPixelDeviationEnabled();
		double pixelDeviationPercent = sideMenu.getPixelDeviationPercent();
		int pixelDeviationAllowed = sideMenu.getPixelDeviationAllowed();
		boolean dimensionDeviationEnabled = sideMenu.isDimensionDeviationEnabled();
		double dimensionDeviationPercent = sideMenu.getDimensionDeviationPercent();
		int dimensionDeviationAllowed = sideMenu.getDimensionDeviationAllowed();
		boolean eulerNumberEnabled = sideMenu.isEulerNumberEnabled();
		recognizedText = Optional.of(ImageOcr.matchCharacters(srcImage, recognizedLineCharacters.get(),
				currentCharacterSet, pixelDeviationEnabled,
				pixelDeviationPercent,
				pixelDeviationAllowed,
				dimensionDeviationEnabled,
				dimensionDeviationPercent,
				dimensionDeviationAllowed,
				eulerNumberEnabled));
	}

	/**
	 * Enables the menu items.
	 */
	private void enablePlugins() {
		context().getMenuControl().enablePlugins();
	}


	private void markLines() {
		int width = (int) srcImage.getWidth();
		int height = (int) srcImage.getHeight();
		WritableImage withMarkedLines = new WritableImage(srcImage.getPixelReader(), width, height);
		PixelWriter writer = withMarkedLines.getPixelWriter();

		recognizedLines.get().stream().forEach((line) -> {
			ImageUtils.drawLine(writer, START_COLOR, 0, width, line.getTopY(), line.getTopY());
			ImageUtils.drawLine(writer, END_COLOR, 0, width, line.getBottomY(), line.getBottomY());
		});

		context().getImageControl().showImage(withMarkedLines);
	}

	private void recognizeLines() {
		assertLinesRecognized();
		markLines();
	}

	private void seperateCharacters() {
		assertCharactersRecognized();

		List<RecognizedChar> recognizedChars =
				recognizedLineCharacters.get().stream().flatMap(List::stream).collect(Collectors.toList());

		context().getImageControl().showImage(ImageUtils.markCharactersOnImage(srcImage, recognizedChars));
	}

	@Override
	public void started() {
		reset();
		context().getMenuControl().disablePlugins();
		srcImage = context().getImageControl().getImage();
		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
	}
}
