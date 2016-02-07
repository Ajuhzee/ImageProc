package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

/**
 * Provides basic functionalities for plugins.
 *
 * @author Ajuhzee
 *
 */
public abstract class PluginBase {

	private final MenuPosition menuPosition;
	private final PluginInformation pInfo;

	/**
	 * Creates the basic part of the plugin.
	 *
	 * @param menuPosition
	 *            the position of the plugin (may not be null)
	 * @param pInfo
	 *            plugin information of the plugin
	 */
	public PluginBase(MenuPosition menuPosition, PluginInformation pInfo) {
		checkNotNull(menuPosition, "The menu position of a plugin may not be null");

		this.menuPosition = menuPosition;
		this.pInfo = pInfo;
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
	 * @return the pInfo
	 */
	public PluginInformation getpInfo() {
		return pInfo;
	}

	/**
	 *
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
