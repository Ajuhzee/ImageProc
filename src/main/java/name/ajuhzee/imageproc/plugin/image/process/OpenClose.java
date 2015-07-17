package name.ajuhzee.imageproc.plugin.image.process;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.view.OpenCloseMenuController;

import java.io.IOException;

public class OpenClose extends ImagePlugin {


	public static final PluginInformation PLUGIN_INFO = new PluginInformation("Opening/Closing", true);

	private final OpenCloseMenuController sideMenu;

	private Image srcImage;

	/**
	 * Creates an instance of an image plugin.
	 *
	 * @param context the context of the plugin
	 * @throws PluginLoadException if context == null
	 */
	public OpenClose(ImagePluginContext context) throws PluginLoadException {
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("openCLose",
				PLUGIN_INFO).get(), PLUGIN_INFO, context);

		try {
			sideMenu = OpenCloseMenuController.create();
		} catch (IOException e) {
			throw new PluginLoadException("failed to load fxml", e);
		}

		sideMenu.addOkButtonPressedCallback(this::clearSideMenu);
		sideMenu.addOkButtonPressedCallback(this::enablePlugins);

		sideMenu.addOpenPressedCallback(this::open);
		sideMenu.addClosePressedCallback(this::close);
	}


	private void close() {
		Image img = context().getImageControl().getImage();

		for (int i = 0; i != sideMenu.getRepeatCount(); ++i) {
			img = ImageEditing.dilate(img, sideMenu.getNeighborhoodStatus());
		}

		for (int i = 0; i != sideMenu.getRepeatCount(); ++i) {
			img = ImageEditing.erode(img, sideMenu.getNeighborhoodStatus());
		}


		context().getImageControl().showImage(img);
	}

	private void open() {
		Image img = context().getImageControl().getImage();


		for (int i = 0; i != sideMenu.getRepeatCount(); ++i) {
			img = ImageEditing.erode(img, sideMenu.getNeighborhoodStatus());
		}

		for (int i = 0; i != sideMenu.getRepeatCount(); ++i) {
			img = ImageEditing.dilate(img, sideMenu.getNeighborhoodStatus());
		}

		context().getImageControl().showImage(img);
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
