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

		private MenuPositionCreator(String key, String name, Integer order, MenuPosition parent) {
			checkNotNull(key);
			menuPosition = new MenuPosition(key, Optional.ofNullable(name), Optional.ofNullable(order),
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
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key) {
			return subMenu(key, DEFAULT_NAME);
		}

		/**
		 * Adds another sub menu to the current builder.
		 * 
		 * @param key
		 *            a unique menu identifier
		 * @param name
		 *            a unique menu identifier
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, String name) {
			return subMenu(key, name, DEFAULT_ORDER);
		}

		/**
		 * Adds another sub menu to the current builder.
		 * 
		 * @param key
		 *            a unique menu identifier
		 * @param name
		 *            a unique menu identifier
		 * @param order
		 *            a unique menu identifier
		 * @return the new menu position
		 */
		public MenuPositionCreator subMenu(String key, String name, int order) {
			return new MenuPositionCreator(key, name, order, get());
		}
	}

	/**
	 * Adds another top menu to the current builder.
	 * 
	 * @param key
	 *            a unique menu identifier
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key) {
		return topMenu(key, DEFAULT_NAME);
	}

	/**
	 * Adds another top menu to the current builder.
	 * 
	 * @param key
	 *            a unique menu identifier
	 * @param name
	 *            a unique menu identifier
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, String name) {
		return topMenu(key, name, DEFAULT_ORDER);
	}

	/**
	 * Adds another top menu to the current builder.
	 * 
	 * @param key
	 *            a unique menu identifier
	 * @param name
	 *            a unique menu identifier
	 * @param order
	 *            a unique menu identifier
	 * @return the new menu position
	 */
	public static MenuPositionCreator topMenu(String key, String name, int order) {
		return new MenuPositionCreator(key, name, order, null);
	}

	private static final String DEFAULT_NAME = null;

	private static final int DEFAULT_ORDER = 0;
}