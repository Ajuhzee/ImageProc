package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Objects;

public abstract class PluginBase {

	private final MenuPosition menuPosition;

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

	public MenuPosition getMenuPosition() {
		return menuPosition;
	}

	@Override
	public int hashCode() {
		return menuPosition.hashCode();
	}

	public abstract void started();

}
