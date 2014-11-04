package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.ComparisonChain;

/**
 * Represents the position of a menu.
 *
 * @author Ajuhzee
 *
 */
public class MenuPosition {

	/**
	 * Compares menu positions.
	 *
	 * @author Ajuhzee
	 *
	 */
	public static class MenuPositionComparator implements Comparator<MenuPosition> {

		@Override
		public int compare(MenuPosition left, MenuPosition right) {
			return ComparisonChain.start().compare(left.getOrder(), right.getOrder())
					.compare(left.getLabel(), right.getLabel()).result();
		}
	}

	private final Optional<MenuPosition> parent;

	private final String key;

	private final Optional<String> label;

	private final Optional<Integer> order;

	/**
	 * Creates a menu position.
	 *
	 * @param key
	 *            unique menu identifier
	 * @param label
	 *            unique menu identifier (optional)
	 * @param order
	 *            unique menu identifier (optional)
	 * @param parent
	 *            unique menu identifier (optional)
	 */
	public MenuPosition(String key, Optional<String> label, Optional<Integer> order, Optional<MenuPosition> parent) {
		checkNotNull(key);

		this.key = key;
		this.label = label;
		this.order = order;
		this.parent = parent;
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
		final MenuPosition other = (MenuPosition) obj;
		if (!Objects.equals(key, other.key)) {
			return false;
		}
		if (!Objects.equals(label, other.label)) {
			return false;
		}
		if (!Objects.equals(order, other.order)) {
			return false;
		}
		if (!Objects.equals(parent, other.parent)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @return the key of the plugin
	 */
	public String getKey() {
		if (parent.isPresent()) {
			return parent.get().key + "." + key;
		}
		return key;
	}

	/**
	 *
	 * @return the name of the plugin or its key
	 */
	public String getLabel() {
		return label.orElse(key);
	}

	/**
	 *
	 * @return the order number of the plugin
	 */
	public int getOrder() {
		return order.orElse(0);
	}

	/**
	 * @return the parent of the plugin, if there is one
	 */
	public Optional<MenuPosition> getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

}