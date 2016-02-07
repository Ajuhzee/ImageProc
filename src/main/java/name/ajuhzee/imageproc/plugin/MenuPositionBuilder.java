package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

/**
 * Builds a menu position.
 *
 * @author Ajuhzee
 *
 */
public class MenuPositionBuilder {

	/**
	 * Generates the menu position for the main menu.
	 *
	 * @author Ajuhzee
	 *
	 */
	public static class MenuPositionCreator {

		private final MenuPosition menuPosition;

		private MenuPositionCreator(String key, String label, PluginInformation pInfo, Integer order,
				MenuPosition parent) {
			checkNotNull(key);
			if (label == null && pInfo == null) {
				throw new IllegalArgumentException("string and pInfo can not be null at the same time");
			}

			menuPosition = new MenuPosition(key, label, Optional.ofNullable(pInfo), Optional.ofNullable(order),
					Optional.ofNullable(parent));
		}

		/**
		 *
		 * @return the menu position of the plugin
		 */
		public MenuPosition get() {
			return menuPosition;
		}

		/**
		 * Adds another sub menu to the current builder.
		 *
		 * @param key
		 *            a unique menu identifier
		 * @param label
		 *            text that should be displayed on the menu
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, String label) {
			return new MenuPositionCreator(key, label, null, DEFAULT_ORDER, get());
		}

		/**
		 * Adds another sub menu to the current builder.
		 *
		 * @param key
		 *            a unique menu identifier
		 * @param pInfo
		 *            plugin information
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, PluginInformation pInfo) {
			return new MenuPositionCreator(key, pInfo.getName(), pInfo, DEFAULT_ORDER, get());
		}

		/**
		 * Adds another sub menu to the current builder.
		 *
		 * @param key
		 *            a unique menu identifier
		 * @param label
		 *            text that should be displayed on the menu
		 * @param order
		 *            the order of the menu items
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, String label, int order) {
			return new MenuPositionCreator(key, label, null, order, get());
		}

		/**
		 * Adds another sub menu to the current builder.
		 *
		 * @param key
		 *            a unique menu identifier
		 * @param label
		 *            text that should be displayed on the menu
		 * @param pInfo
		 *            plugin information
		 * @param order
		 *            the order of the menu items
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, PluginInformation pInfo, int order) {
			return new MenuPositionCreator(key, pInfo.getName(), pInfo, order, get());
		}
	}

	/**
	 * Adds another top menu to the current builder.
	 *
	 * @param key
	 *            a unique menu identifier
	 * @param label
	 *            text that should be displayed on the menu
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, String label) {
		return new MenuPositionCreator(key, label, null, DEFAULT_ORDER, null);
	}

	/**
	 * Adds another top menu to the current builder.
	 *
	 * @param key
	 *            a unique menu identifier
	 * @param pInfo
	 *            plugin information
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, PluginInformation pInfo) {
		return new MenuPositionCreator(key, pInfo.getName(), pInfo, DEFAULT_ORDER, null);
	}

	/**
	 * Adds another top menu to the current builder.
	 *
	 * @param key
	 *            a unique menu identifier
	 * @param label
	 *            text that should be displayed on the menu
	 * @param order
	 *            the order of the menu items
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, String label, int order) {
		return new MenuPositionCreator(key, label, null, order, null);
	}

	/**
	 * Adds another top menu to the current builder.
	 *
	 * @param key
	 *            a unique menu identifier
	 * @param label
	 *            text that should be displayed on the menu
	 * @param pInfo
	 *            plugin information
	 * @param order
	 *            the order of the menu items
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, PluginInformation pInfo, int order) {
		return new MenuPositionCreator(key, pInfo.getName(), pInfo, order, null);
	}

	private static final int DEFAULT_ORDER = 0;
}
