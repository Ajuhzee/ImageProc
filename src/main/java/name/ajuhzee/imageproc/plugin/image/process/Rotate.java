package name.ajuhzee.imageproc.plugin.image.process;

import com.google.common.util.concurrent.AtomicDouble;
import javafx.beans.value.ChangeListener;
import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageEditing;
import name.ajuhzee.imageproc.view.RotateMenuController;

import java.io.IOException;

/**
 * Adds an image plugin, that provides a method to rotate an image.
 *
 * @author Ajuhzee
 */
public class Rotate extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Drehen", true);

	private static final double DEFAULT_ANGLE = 0;

	AtomicDouble curAngle = new AtomicDouble(DEFAULT_ANGLE);

	private final ChangeListener<Number> listener = (observable, oldValue, newValue) -> {

		curAngle.set(newValue.doubleValue());
		rotate(curAngle);
	};

	private Image oldImage;

	private final RotateMenuController sideMenu;

	Thread thread = new Thread();

	/**
	 * Creates the Rotate plugin and positions a Menu-button for it.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Rotate(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("rotate", INFO).get(), context);
		try {
			sideMenu = RotateMenuController.create();
		} catch (final IOException e) {
			throw new PluginLoadException("Couldn't load the side menu", e);
		}
		sideMenu.addOkButtonPressedCallback(this::clearSideMenu);
		sideMenu.addOkButtonPressedCallback(this::enablePlugins);

		sideMenu.addCancelButtonPressedCallback(this::clearSideMenu);
		sideMenu.addCancelButtonPressedCallback(this::enablePlugins);
		sideMenu.addCancelButtonPressedCallback(() -> {
			context.getImageControl().showImage(oldImage);
		});
	}

	private void rotate(AtomicDouble angle) {
		if (!thread.isAlive()) {
			thread = new Thread(() -> {
				final Image newImage =
						ImageEditing.rotate(oldImage, angle.get(), sideMenu.getColorPickerValue().getValue());
				context().getImageControl().showImage(newImage);
			});
			thread.start();
		}
		context().getMenuControl().disablePlugins();
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
		oldImage = context().getImageControl().getImage();

		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
		rotate(curAngle);

		sideMenu.getSliderValue().addListener(listener);
	}

}
