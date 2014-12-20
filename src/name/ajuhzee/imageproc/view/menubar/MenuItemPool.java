package name.ajuhzee.imageproc.view.menubar;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import name.ajuhzee.imageproc.plugin.MenuPosition;

/**
 * Gives access to functionalities that are using menus and menu items.
 * 
 * @author Ajuhzee
 *
 */
public class MenuItemPool {

	private final Map<MenuPosition, MenuItem> menuItems = new HashMap<>();

	private final Map<MenuPosition, MenuWrapper> menus = new HashMap<>();

	/**
	 * @return the menuItems
	 */
	public Set<Entry<MenuPosition, MenuItem>> getMenuItems() {
		return menuItems.entrySet();
	}

	/**
	 * Requests if a menu position is already in use.
	 * 
	 * @param position
	 *            the menu position
	 * @return true if the menu position is already in use
	 */
	public boolean contains(MenuPosition position) {
		return menuItems.containsKey(position) || menus.containsKey(position);
	}

	/**
	 * 
	 * @param position
	 *            of the requested menu
	 * @return the menu
	 */
	public MenuWrapper getMenu(MenuPosition position) {
		final MenuWrapper menuWrapper = menus.get(position);
		if (menuWrapper != null) {
			return menuWrapper;
		}

		throw new IllegalArgumentException("No menu found at the given position.");
	}

	/**
	 * 
	 * @param position
	 *            the position of the requested menu item
	 * @return the menu item
	 */
	public MenuItem getMenuItem(MenuPosition position) {
		final MenuItem result = menuItems.get(position);
		if (result != null) {
			return result;
		}

		try {
			return getMenu(position).getMenu();
		} catch (final IllegalArgumentException e) {
			throw new IllegalArgumentException("No menu item found at the given position.");
		}
	}

	/**
	 * Places an menu into the menu wrapper, with a specific position.
	 * 
	 * @param position
	 *            the specific position
	 * @param menu
	 *            the name of the menu
	 */
	public void putMenu(MenuPosition position, MenuWrapper menu) {
		menus.put(position, menu);
	}

	/**
	 * Places an menu item into a menu, with a specific position.
	 * 
	 * @param position
	 *            the specific position
	 * @param menuItem
	 *            the name of the menu item
	 */
	public void putMenuItem(MenuPosition position, MenuItem menuItem) {
		if (menuItem instanceof Menu) {
			throw new IllegalArgumentException("Tried to add a menu with putMenuItem(). Use putMenu() instead.");
		}
		menuItems.put(position, menuItem);
	}
}
