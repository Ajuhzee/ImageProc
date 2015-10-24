package name.ajuhzee.imageproc.util.cache;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber.EulerNumberCompute;
import name.ajuhzee.imageproc.processing.ocr.criteria.pixelamount.PixelAmountCompute;

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

public class ImagePropertyCache {

	private static class ImageProperties {

		private OptionalInt pixelAmount = OptionalInt.empty();

		private OptionalInt eulerNumber = OptionalInt.empty();

		public OptionalInt getPixelAmount() {
			return pixelAmount;
		}

		public void setPixelAmount(int pixelAmount) {
			this.pixelAmount = OptionalInt.of(pixelAmount);
		}

		public OptionalInt getEulerNumber() {
			return eulerNumber;
		}

		public void setEulerNumber(int eulerNumber) {
			this.eulerNumber = OptionalInt.of(eulerNumber);
		}
	}

	@FunctionalInterface
	private interface IntFromImage {

		int get(Image img);
	}

	private static final Map<Image, ImageProperties> cache = new HashMap<>();

	public static int getPixelAmount(Image img) {
		ImageProperties properties = getImageProperties(img);

		int pixelAmount = getProperty(img, properties::setPixelAmount, properties::getPixelAmount,
				PixelAmountCompute::pixelAmount);

		return pixelAmount;
	}

	public static int getEulerNumber(Image img) {
		ImageProperties properties = getImageProperties(img);

		int eulerNumber = getProperty(img, properties::setEulerNumber, properties::getEulerNumber,
				EulerNumberCompute::getEulerNumber);

		return eulerNumber;
	}

	private static ImageProperties getImageProperties(Image img) {
		ImageProperties imageProperties = cache.get(img);
		if (imageProperties == null) {
			imageProperties = new ImageProperties();
			cache.put(img, imageProperties);
		}
		return imageProperties;
	}

	public static int getProperty(Image img, IntConsumer setter, Supplier<OptionalInt> getter,
								  IntFromImage computeFunction) {
		OptionalInt potentialProperty = getter.get();
		if (!potentialProperty.isPresent()) {
			setter.accept(computeFunction.get(img));
		}
		return getter.get().getAsInt();
	}


}
