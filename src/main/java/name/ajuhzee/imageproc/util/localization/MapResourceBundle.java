package name.ajuhzee.imageproc.util.localization;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Map implementation of {@link ResourceBundle}
 *
 * @author Ajuhzee
 *
 * @param <T>
 *            type of the object that is being stored
 */
public class MapResourceBundle<T> extends ResourceBundle {

	private final Map<String, T> strings = new HashMap<>();

	@Override
	public Enumeration<String> getKeys() {
		return Collections.enumeration(strings.keySet());
	}

	@Override
	protected Object handleGetObject(String key) {
		return strings.get(key);
	}

	/**
	 * Stores an object by key in the bundle
	 *
	 * @param key
	 * @param value
	 */
	public void put(String key, T value) {
		strings.put(key, value);
	}

}
