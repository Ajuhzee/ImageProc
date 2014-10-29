package name.ajuhzee.imageproc.plugin;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.ComparisonChain;

public class MenuPosition {

	public static class MenuPositionComparator implements Comparator<MenuPosition> {

		@Override
		public int compare(MenuPosition left, MenuPosition right) {
			return ComparisonChain.start().compare(left.getOrder(), right.getOrder())
					.compare(left.getLabel(), right.getLabel()).result();
		}
	}

	private final Optional<MenuPosition> parent;

	private final String key;

	private final Optional<String> name;

	private final Optional<Integer> order;

	public MenuPosition(String key, Optional<String> name, Optional<Integer> order, Optional<MenuPosition> parent) {
		checkNotNull(key);

		this.key = key;
		this.name = name;
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
		if (!Objects.equals(name, other.name)) {
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

	public String getKey() {
		if (parent.isPresent()) {
			return parent.get().key + "." + key;
		}
		return key;
	}

	public String getLabel() {
		return name.orElse(key);
	}

	public Optional<String> getName() {
		return name;
	}

	public int getOrder() {
		return order.orElse(0);
	}

	public Optional<MenuPosition> getParent() {
		return parent;
	}

	@Override
	public int hashCode() {
		return getKey().hashCode();
	}

}