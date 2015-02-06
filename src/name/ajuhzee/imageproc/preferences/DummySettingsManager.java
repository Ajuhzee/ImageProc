package name.ajuhzee.imageproc.preferences;

import java.util.HashSet;
import java.util.Set;

import name.ajuhzee.imageproc.plugin.CorePlugin;
import name.ajuhzee.imageproc.plugin.ImagePlugin;
import name.ajuhzee.imageproc.plugin.core.CloseProgram;
import name.ajuhzee.imageproc.plugin.core.Configuration;
import name.ajuhzee.imageproc.plugin.image.LoadImage;
import name.ajuhzee.imageproc.plugin.image.SaveImage;
import name.ajuhzee.imageproc.plugin.image.generate.TestImage;
import name.ajuhzee.imageproc.plugin.image.process.Binarize;
import name.ajuhzee.imageproc.plugin.image.process.Invert;
import name.ajuhzee.imageproc.plugin.image.process.filter.Laplace;
import name.ajuhzee.imageproc.plugin.image.process.filter.Mean3x3;
import name.ajuhzee.imageproc.plugin.image.process.filter.Mean3x3Seperated;
import name.ajuhzee.imageproc.plugin.image.process.filter.Mean3x3Threaded;

/**
 * Temporary manager
 * 
 * @author Ajuhzee
 */
public class DummySettingsManager implements SettingsManager {

	@Override
	public Set<Class<? extends CorePlugin>> getCorePlugins() {
		// should be loaded from config
		final Set<Class<? extends CorePlugin>> corePlugins = new HashSet<>();
		corePlugins.add(CloseProgram.class);
		corePlugins.add(Configuration.class);
		return corePlugins;
	}

	@Override
	public Set<Class<? extends ImagePlugin>> getImagePlugins() {
		// should be loaded from config
		final Set<Class<? extends ImagePlugin>> imagePlugins = new HashSet<>();
		imagePlugins.add(LoadImage.class);
		imagePlugins.add(SaveImage.class);
		imagePlugins.add(Binarize.class);
		imagePlugins.add(Invert.class);
		imagePlugins.add(Laplace.class);
		imagePlugins.add(Mean3x3.class);
		imagePlugins.add(Mean3x3Seperated.class);
		imagePlugins.add(Mean3x3Threaded.class);
		imagePlugins.add(TestImage.class);
		return imagePlugins;
	}
}
