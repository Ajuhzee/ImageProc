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
import name.ajuhzee.imageproc.view.BinarizeMenuController;

import java.io.IOException;

/**
 * Adds an image plugin, that provides a method to invert an image for image processing purposes.
 *
 * @author Ajuhzee
 *
 */
public class Binarize extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Binarisieren", true);

	private static final double DEFAULT_THRESHOLD = 127;

	AtomicDouble curThreshold = new AtomicDouble(DEFAULT_THRESHOLD);

	private final ChangeListener<Number> listener = (observable, oldValue, newValue) -> {

		curThreshold.set(newValue.doubleValue());
		binarize(curThreshold);
	};

	private Image oldImage;

	private final BinarizeMenuController sideMenu;

	Thread thread = new Thread();

	/**
	 * Positions a Menu-button for the plugin.
	 *
	 * @param context
	 * @throws PluginLoadException
	 */
	public Binarize(ImagePluginContext context) throws PluginLoadException {
		// positions/position names should be in a config file
		super(MenuPositionBuilder.topMenu("process", "Bearbeiten", 100).subMenu("binarize", INFO).get(), INFO, context);
		try {
			sideMenu = BinarizeMenuController.create();
		} catch (final IOException e) {
			throw new PluginLoadException("Couldn't load the side menu", e);
		}
		sideMenu.addOkButtonPressedCallback(this::clearSideMenu);
		sideMenu.addOkButtonPressedCallback(this::enablePlugins);
		sideMenu.addCancelButtonPressedCallback(() -> context.getImageControl().showImage(oldImage));
		sideMenu.addCancelButtonPressedCallback(this::enablePlugins);
		sideMenu.addCancelButtonPressedCallback(this::clearSideMenu);
	}

	private void binarize(AtomicDouble threshold) {
		if (!thread.isAlive()) {
			thread = new Thread(() -> {
				final Image newImage = ImageEditing.binarizeDynamic(oldImage, threshold);
				context().getImageControl().showImage(newImage);
			});
			thread.start();
		}
		context().getMenuControl().disablePlugins();
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

	@Override
	public void started() {
		oldImage = context().getImageControl().getImage();

		context().getSideMenuControl().setContent(sideMenu.toNodeRepresentation());
		binarize(curThreshold);

		sideMenu.getSliderValue().addListener(listener);
	}

}
