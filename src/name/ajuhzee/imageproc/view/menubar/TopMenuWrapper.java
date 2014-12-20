/**
 * 
 */
package name.ajuhzee.imageproc.view.menubar;

import javafx.scene.control.MenuBar;
import name.ajuhzee.imageproc.plugin.MenuPosition;
import name.ajuhzee.imageproc.plugin.MenuPosition.MenuPositionComparator;
import name.ajuhzee.imageproc.util.SortedList;

/**
 * Wraps the top menu
 */
public class TopMenuWrapper {

	private final SortedList<MenuPosition> topMenu = new SortedList<>(new MenuPositionComparator());

	private final MenuItemPool itemPool;

	private final MenuBar menuBar;

	/**
	 * Creates a new wrapper for the top menu
	 * 
	 * @param menuBar
	 * @param itemPool
	 *            the item pool where menus are saved
	 */
	public TopMenuWrapper(MenuBar menuBar, MenuItemPool itemPool) {
		this.menuBar = menuBar;
		this.itemPool = itemPool;
	}

	/**
	 * Add a new menu to the top menu
	 * 
	 * @param position
	 */
	public void add(MenuPosition position) {
		final int insertAt = topMenu.insert(position);
		menuBar.getMenus().add(insertAt, itemPool.getMenu(position).getMenu());
	}

	/**
	 * @return the menu bar
	 */
	public MenuBar getMenuBar() {
		return menuBar;
	}
}