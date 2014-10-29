package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Optional;

public class MenuPositionBuilder {

	public static class MenuPositionCreator {

		private final MenuPosition menuPosition;

		private MenuPositionCreator(String key, String name, Integer order, MenuPosition parent) {
			checkNotNull(key);
			menuPosition = new MenuPosition(key, Optional.ofNullable(name), Optional.ofNullable(order),
					Optional.ofNullable(parent));
		}

		public MenuPosition get() {
			return menuPosition;
		}

		public MenuPositionCreator then(String key) {
			return then(key, DEFAULT_NAME);
		}

		public MenuPositionCreator then(String key, String name) {
			return then(key, name, DEFAULT_ORDER);
		}

		public MenuPositionCreator then(String key, String name, int order) {
			return new MenuPositionCreator(key, name, order, get());
		}
	}

	public static MenuPositionCreator top(String key) {
		return top(key, DEFAULT_NAME);
	}

	public static MenuPositionCreator top(String key, String name) {
		return top(key, name, DEFAULT_ORDER);
	}

	public static MenuPositionCreator top(String key, String name, int order) {
		return new MenuPositionCreator(key, name, order, null);
	}

	private static final String DEFAULT_NAME = null;

	private static final int DEFAULT_ORDER = 0;
}