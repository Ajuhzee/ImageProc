package name.ajuhzee.imageproc.plugin.image.process;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.MenuPositionBuilder;
import name.ajuhzee.imageproc.plugin.PluginLoadException;
import name.ajuhzee.imageproc.plugin.control.ImagePluginContext;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;
import name.ajuhzee.imageproc.processing.ImageProcessing;

/**
 * Adds an image plugin, that provides a menu for a step by step character recognition.
 * 
 * @author Ajuhzee
 *
 */
public class Ocr extends ImagePlugin {

	private static final PluginInformation INFO = new PluginInformation("Ocr", true);

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
		// TODO Auto-generated constructor stub
	}

	private void ocr() {
		ImageProcessing.ocr(srcImage);
	}

	@Override
	public void started() {
		srcImage = context().getImageControl().getImage();
		ocr();
	}

}
