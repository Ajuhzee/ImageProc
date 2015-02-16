package name.ajuhzee.imageproc.plugin.image.process;

import java.io.IOException;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageProcessing;
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
		sideMenu.addDoneButtonPressedCallback(this::clearSideMenu);
		sideMenu.addDoneButtonPressedCallback(this::enablePlugins);
	}

	/**
	 * Enables the menu items.
	 */
	public void enablePlugins() {
		context().getMenuControl().enablePlugins();
	}

	/**
	 * Clears the content of the sideMenu.
	 */
	public void clearSideMenu() {
		context().getSideMenuControl().clearContent();
	}

	private void ocr() {
		ImageProcessing.ocr(srcImage);
		context().getImageControl().showImage(srcImage);
	}

	@Override
	public void started() {
		srcImage = context().getImageControl().getImage();
		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
		ocr();
	}

}
