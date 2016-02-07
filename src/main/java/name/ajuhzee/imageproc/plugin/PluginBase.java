package name.ajuhzee.imageproc.plugin;

import java.util.Objects;

import static com.google.common.base.Preconditions.*;

/**
 * Provides basic functionalities for plugins.
 *
 * @author Ajuhzee
 */
public abstract class PluginBase {

	private final MenuPosition menuPosition;

	/**
	 * Creates the basic part of the plugin.
	 *
	 * @param menuPosition the position of the plugin (may not be null)
	 */
	public PluginBase(MenuPosition menuPosition) {
		checkNotNull(menuPosition, "The menu position of a plugin may not be null");

		this.menuPosition = menuPosition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		final PluginBase other = (PluginBase) obj;
		return !Objects.equals(menuPosition, other.menuPosition);
	}


	/**
	 * @return the menu position
	 */
	public MenuPosition getMenuPosition() {
		return menuPosition;
	}

	@Override
	public int hashCode() {
		return menuPosition.hashCode();
	}

	/**
	 * Starts the plugin.
	 */
	public abstract void started();

}
