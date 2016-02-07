package name.ajuhzee.imageproc.plugin.image.process;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.view.ErodeDilateMenuController;

import java.io.IOException;

public class ErodeDilate extends ImagePlugin {


	public static final PluginInformation PLUGIN_INFO = new PluginInformation("Erodieren/Dilatieren", true);

	private final ErodeDilateMenuController sideMenu;

	private Image srcImage;

	/**
	 * Creates an instance of an image plugin.
	 *
	 * @param context the context of the plugin
	 * @throws PluginLoadException if context == null
	 */
	public ErodeDilate(ImagePluginContext context) throws PluginLoadException {
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("erodeDilate",
				PLUGIN_INFO).get(), PLUGIN_INFO, context);

		try {
			sideMenu = ErodeDilateMenuController.create();
		} catch (IOException e) {
			throw new PluginLoadException("failed to load fxml", e);
		}

		sideMenu.addOkButtonPressedCallback(this::clearSideMenu);
		sideMenu.addOkButtonPressedCallback(this::enablePlugins);

		sideMenu.addDilatePressedCallback(this::dilate);
		sideMenu.addErodePressedCallback(this::erode);
	}


	private void erode() {
		Image erodedImage =
				ImageEditing.erode(context().getImageControl().getImage(), sideMenu.getNeighborhoodStatus());
		context().getImageControl().showImage(erodedImage);
	}

	private void dilate() {
		Image dilatedImage =
				ImageEditing.dilate(context().getImageControl().getImage(), sideMenu.getNeighborhoodStatus());
		context().getImageControl().showImage(dilatedImage);
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
