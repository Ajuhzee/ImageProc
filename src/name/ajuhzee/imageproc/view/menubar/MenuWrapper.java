/**
 * 
 */
package name.ajuhzee.imageproc.view.menubar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import name.ajuhzee.imageproc.plugin.MenuPosition;
import name.ajuhzee.imageproc.plugin.MenuPosition.MenuPositionComparator;
import name.ajuhzee.imageproc.util.SortedList;

/**
 * Wraps a {@link Menu} to make it possible to maintain an ordering on the {@linkplain MenuItem}s in the menu.
 */
public class MenuWrapper {

	private final Menu menu;

	private final MenuItemPool itemPool;

	private final SortedList<MenuPosition> children = new SortedList<>(new MenuPositionComparator());

	/**
	 * Wraps a MenuWrapper around a Menu.
	 *
	 * @param menu
	 * @param itemPool
	 *            the item pool where menus are saved
	 */
	public MenuWrapper(Menu menu, MenuItemPool itemPool) {
		this.menu = menu;
		this.itemPool = itemPool;
	}

	/**
	 * Adds a child to the menu. Gets the menu item from the menu item pool.
	 *
	 * @param menuItemPosition
	 */
	public void addChild(MenuPosition menuItemPosition) {
		if (children.contains(menuItemPosition)) {
			return;
		}
		final int insertedAt = children.insert(menuItemPosition);
		menu.getItems().add(insertedAt, itemPool.getMenuItem(menuItemPosition));
	}

	/**
	 * 
	 * @return the menu
	 */
	public Menu getMenu() {
		return menu;
	}
}
