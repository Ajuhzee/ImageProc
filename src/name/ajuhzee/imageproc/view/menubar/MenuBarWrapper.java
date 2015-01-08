package name.ajuhzee.imageproc.view.menubar;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Map.Entry;
import java.util.Optional;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import name.ajuhzee.imageproc.plugin.MenuPosition;
import name.ajuhzee.imageproc.plugin.control.MenuControl;
import name.ajuhzee.imageproc.plugin.core.PluginInformation;

/**
 * "Wraps" the functionality of the {@link MenuBar}, to make it possible to control it easier.
 * 
 * @author Ajuhzee
 *
 */
public class MenuBarWrapper implements MenuControl {

	private final MenuItemPool itemPool;

	private final TopMenuWrapper topMenu;

	/**
	 * Creates the Menu Bar wrapper.
	 */
	public MenuBarWrapper() {
		itemPool = new MenuItemPool();
		topMenu = new TopMenuWrapper(new MenuBar(), itemPool);
	}

	@Override
	public void enablePlugins() {
		Platform.runLater(() -> {
			for (Entry<MenuPosition, MenuItem> entry : itemPool.getMenuItems()) {
				if (entry.getKey().getPluginInformation().get().doesRequireImage() == true) {
					entry.getValue().setDisable(false);
				}
			}
		});
	}

	@Override
	public void disablePlugins() {
		Platform.runLater(() -> {
			for (Entry<MenuPosition, MenuItem> entry : itemPool.getMenuItems()) {
				if (entry.getKey().getPluginInformation().get().doesRequireImage() == true) {
					entry.getValue().setDisable(true);
				}
			}
		});
	}

	/**
	 * Adds a menu item to the menu bar.
	 * 
	 * @param position
	 *            the position where the menu item is placed
	 * @param action
	 *            the action the menu item triggers
	 */
	public void addMenuItem(MenuPosition position, Runnable action) {
		checkArgument(!itemPool.contains(position), "There can not be another menu item at the same position.");
		checkArgument(position.getParent().isPresent(), "The menu item needs a parent position.");

		Optional<PluginInformation> info = position.getPluginInformation();
		final MenuItem menuItem = new MenuItem(position.getLabel());
		menuItem.setOnAction(event -> action.run());

		if (info.isPresent()) {
			setIntitialDisabledState(menuItem, position);
		}

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
				parentMenuItem = new MenuWrapper(newMenu, itemPool);
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

	private static void setIntitialDisabledState(MenuItem menuItem, MenuPosition position) {
		menuItem.setDisable(position.getPluginInformation().get().doesRequireImage());
	}

	/**
	 * @return the menu bar
	 */
	public MenuBar getMenuBar() {
		return topMenu.getMenuBar();
	}

}
