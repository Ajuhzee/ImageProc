package name.ajuhzee.imageproc.view;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;

import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import name.ajuhzee.imageproc.plugin.MenuPosition;
import name.ajuhzee.imageproc.plugin.PluginBase;
import name.ajuhzee.imageproc.plugin.PluginExecutor;

/**
 * A wrapper for a {@link MenuBar} to provide a simple interface for adding ImageProc plugins.
 */
public class PluginMenu implements NodeRepresentation {

	public static PluginMenu create() throws IOException {
		// can be changed to fxml loading
		return new PluginMenu();
	}

	private final MenuBarWrapper menuBar = new MenuBarWrapper();

	public void addPlugin(PluginBase newPlugin, PluginExecutor executor) {
		checkNotNull(newPlugin);
		final MenuPosition pluginPosition = newPlugin.getMenuPosition();
		checkArgument(pluginPosition.getParent().isPresent(), "The plugin must have a parent position.");

		menuBar.addMenuItem(pluginPosition, () -> executor.execute(newPlugin));
	}

	@Override
	public Node toNodeRepresentation() {
		return menuBar.getMenuBar();
	}
}
