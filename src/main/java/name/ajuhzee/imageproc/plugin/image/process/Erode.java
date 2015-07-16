package name.ajuhzee.imageproc.plugin.image.process;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.processing.Neighborhood;
import name.ajuhzee.imageproc.view.NeighborhoodMenuController;

import java.io.IOException;

public class Erode extends ImagePlugin {


	public static final PluginInformation PLUGIN_INFO = new PluginInformation("Erode", true);

	private final NeighborhoodMenuController sideMenu;

	private Image srcImage;

	/**
	 * Creates an instance of an image plugin.
	 *
	 * @param context the context of the plugin
	 * @throws PluginLoadException if context == null
	 */
	public Erode(ImagePluginContext context) throws PluginLoadException {
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("erode",
				PLUGIN_INFO).get(), PLUGIN_INFO, context);

		try {
			sideMenu = NeighborhoodMenuController.create();
		} catch (IOException e) {
			throw new PluginLoadException("failed to load fxml", e);
		}

		sideMenu.addOkButtonPressedCallback(this::clearSideMenu);
		sideMenu.addOkButtonPressedCallback(this::enablePlugins);

		sideMenu.addRadio4PressedCallback(this::run4Erode);
		sideMenu.addRadio8PressedCallback(this::run8Erode);
	}


	private void run4Erode() {
		Image erodedImage = ImageEditing.erode(srcImage, Neighborhood.NEIGHBORHOOD4);
		context().getImageControl().showImage(erodedImage);
	}

	private void run8Erode() {
		Image erodedImage = ImageEditing.erode(srcImage, Neighborhood.NEIGHBORHOOD8);
		context().getImageControl().showImage(erodedImage);
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
	}
}
