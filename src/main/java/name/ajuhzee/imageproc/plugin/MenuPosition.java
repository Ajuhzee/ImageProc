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

	private final String label;

	private final Optional<PluginInformation> pInfo;

	private final Optional<Integer> order;

	/**
	 * Creates a menu position.
	 *
	 * @param key
	 *            unique menu identifier
	 * @param pInfo
	 *            plugin information of the plugin at this position (optional)
	 * @param label
	 *            unique menu identifier (optional)
	 * @param order
	 *            unique menu identifier (optional)
	 * @param parent
	 *            unique menu identifier (optional)
	 */
	public MenuPosition(String key, String label, Optional<PluginInformation> pInfo, Optional<Integer> order,
			Optional<MenuPosition> parent) {
		checkNotNull(key);
		checkNotNull(label);

		this.key = key;
		this.label = label;
		this.pInfo = pInfo;
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
		if (!Objects.equals(pInfo, other.pInfo)) {
			return false;
		}
		if (!Objects.equals(order, other.order)) {
			return false;
		}
		if (!Objects.equals(parent, other.parent)) {
			return false;
		}
		if (!Objects.equals(label, other.label)) {
			return false;
		}
		return true;
	}

	/**
	 *
	 * @return the key
	 */
	public String getKey() {
		if (parent.isPresent()) {
			return parent.get().key + "." + key;
		}
		return key;
	}

	/**
	 *
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @return the plugin
	 */
	public Optional<PluginInformation> getPluginInformation() {
		return pInfo;
	}

	/**
	 *
	 * @return the order number
	 */
	public int getOrder() {
		return order.orElse(0);
	}

	/**
	 * @return the parent of the menu, if there is one
	 */
	public Optional<MenuPosition> getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

}