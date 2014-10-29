package name.ajuhzee.imageproc.localization;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

	public void put(String key, T value) {
		strings.put(key, value);
	}

}
