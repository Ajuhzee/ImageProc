package name.ajuhzee.imageproc.view;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import name.ajuhzee.imageproc.plugin.MenuPosition;
import name.ajuhzee.imageproc.plugin.MenuPosition.MenuPositionComparator;
import name.ajuhzee.imageproc.util.SortedList;

public class MenuBarWrapper {

	protected class MenuItemPool {

		private final Map<MenuPosition, MenuItem> menuItems = new HashMap<>();

		private final Map<MenuPosition, MenuWrapper> menus = new HashMap<>();

		public boolean contains(MenuPosition position) {
			return menuItems.containsKey(position) || menus.containsKey(position);
		}

		public MenuWrapper getMenu(MenuPosition position) {
			final MenuWrapper menuWrapper = menus.get(position);
			if (menuWrapper != null) {
				return menuWrapper;
			}

			throw new IllegalArgumentException("No menu found at the given position.");
		}

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

		public void putMenu(MenuPosition position, MenuWrapper menu) {
			menus.put(position, menu);
		}

		public void putMenuItem(MenuPosition position, MenuItem menuItem) {
			if (menuItem instanceof Menu) {
				throw new IllegalArgumentException("Tried to add a menu with putMenuItem(). Use putMenu() instead.");
			}
			menuItems.put(position, menuItem);
		}
	}

	/**
	 * Wraps a {@link Menu} to make it possible to maintain an ordering on the {@linkplain MenuItem}s in the menu.
	 */
	protected class MenuWrapper {

		private final Menu menu;

		private final SortedList<MenuPosition> children = new SortedList<>(new MenuPositionComparator());

		/**
		 * Wraps a MenuWrapper around a Menu.
		 *
		 * @param menu
		 */
		public MenuWrapper(Menu menu) {
			this.menu = menu;
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

		public Menu getMenu() {
			return menu;
		}
	}

	protected class TopMenu {

		private final SortedList<MenuPosition> topMenu = new SortedList<>(new MenuPositionComparator());

		private final MenuItemPool itemPool;

		private final MenuBar menuBar;

		public TopMenu(MenuBar menuBar, MenuItemPool itemPool) {
			this.menuBar = menuBar;
			this.itemPool = itemPool;
		}

		public void add(MenuPosition position) {
			final int insertAt = topMenu.insert(position);
			menuBar.getMenus().add(insertAt, itemPool.getMenu(position).getMenu());
		}
	}

	private final MenuBar menuBar;

	private final MenuItemPool itemPool;

	private final TopMenu topMenu;

	public MenuBarWrapper() {
		menuBar = new MenuBar();
		itemPool = new MenuItemPool();
		topMenu = new TopMenu(menuBar, itemPool);
	}

	// public MenuBarWrapper(MenuBar menuBar) {
	// TODO load existing MenuBar structure
	// }

	public void addMenuItem(MenuPosition position, Runnable action) {
		checkArgument(!itemPool.contains(position), "There can not be another menu item at the same position.");
		checkArgument(position.getParent().isPresent(), "The menu item needs a parent position.");

		final MenuItem menuItem = new MenuItem(position.getLabel());
		menuItem.setOnAction(event -> action.run());
		itemPool.putMenuItem(position, menuItem);

		MenuPosition prev = position;
		Optional<MenuPosition> parent = position.getParent();
		while (parent.isPresent()) {
			final MenuPosition parentPosition = parent.get();

			MenuWrapper parentMenuItem;
			if (itemPool.contains(parentPosition)) {
				parentMenuItem = itemPool.getMenu(parentPosition);
			} else {
				final Menu newMenu = new Menu(parentPosition.getLabel());
				parentMenuItem = new MenuWrapper(newMenu);
				addNewMenu(parentPosition, parentMenuItem);
			}

			parentMenuItem.addChild(prev);

			prev = parentPosition;
			parent = parentPosition.getParent();
		}
	}

	private void addNewMenu(MenuPosition position, MenuWrapper menu) {
		itemPool.putMenu(position, menu);

		final boolean isTopMenu = !position.getParent().isPresent();
		if (isTopMenu) {
			topMenu.add(position);
		}
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

}
