package name.ajuhzee.imageproc.processing;

import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Represents an image buffer with the BGRA-premultiplied format.
 * 
 * @see PixelFormat.Type#BYTE_BGRA_PRE
 * @see PixelFormat#getByteBgraPreInstance()
 */
public class BgraPreImageBuffer {

	/**
	 * Creates an image from the values of this buffer.
	 * 
	 * @param buffer
	 *            the source buffer
	 * @param width
	 *            the width of the buffer
	 * @param height
	 *            the height of the buffer
	 * 
	 * @return the new image
	 */
	public static WritableImage createBgraPreImage(BgraPreImageBuffer buffer, int width, int height) {
		final WritableImage newImage = new WritableImage(width, height);
		final PixelWriter writer = newImage.getPixelWriter();
		writer.setPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer.getRawBuffer(), 0, width
				* BgraPreImageBuffer.BYTES_PER_PIXEL);
		return newImage;
	}

	/**
	 * Gets the BGRA-premultiplied buffer out of the image.
	 * 
	 * @param img
	 * @return the BGRA premultiplied buffer
	 */
	public static BgraPreImageBuffer getBgraPreBuffer(Image img) {
		final int width = (int) img.getWidth();
		final int height = (int) img.getHeight();
		final byte[] buffer = new byte[width * height * BgraPreImageBuffer.BYTES_PER_PIXEL];

		final PixelReader reader = img.getPixelReader();
		reader.getPixels(0, 0, width, height, PixelFormat.getByteBgraPreInstance(), buffer, 0, width
				* BgraPreImageBuffer.BYTES_PER_PIXEL);
		return new BgraPreImageBuffer(buffer);
	}

	/**
	 * Bytes per pixel of this buffer
	 */
	public static final int BYTES_PER_PIXEL = 4;

	private final byte[] buffer;

	/**
	 * @param buffer
	 */
	public BgraPreImageBuffer(byte[] buffer) {
		this.buffer = buffer;
	}

	/**
	 * @param width
	 *            the width of the buffer
	 * @param height
	 *            the height of the buffer
	 */
	public BgraPreImageBuffer(int width, int height) {
		this.buffer = new byte[BYTES_PER_PIXEL * width * height];
	}

	/**
	 * @return the amount of pixels the buffer has
	 */
	public int getPixelCount() {
		return buffer.length / BYTES_PER_PIXEL;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @return the blue value
	 */
	public int getBlue(int idx) {
		return buffer[idx * BYTES_PER_PIXEL] & 0xFF;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @return the green value
	 */
	public int getGreen(int idx) {
		return buffer[idx * BYTES_PER_PIXEL + 1] & 0xFF;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @return the red value
	 */
	public int getRed(int idx) {
		return buffer[idx * BYTES_PER_PIXEL + 2] & 0xFF;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @param value
	 *            the blue value to set
	 */
	public void setBlue(int idx, int value) {
		buffer[idx * BYTES_PER_PIXEL] = (byte) value;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @param value
	 *            the green value to set
	 */
	public void setGreen(int idx, int value) {
		buffer[idx * BYTES_PER_PIXEL + 1] = (byte) value;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @param value
	 *            the red value to set
	 */
	public void setRed(int idx, int value) {
		buffer[idx * BYTES_PER_PIXEL + 2] = (byte) value;
	}

	/**
	 * 
	 * @param startPixelIdx
	 *            inclusive
	 * @param endPixelIdx
	 *            exclusive
	 * @return a copy
	 */
	public BgraPreImageBuffer copySubBuffer(int startPixelIdx, int endPixelIdx) {
		final int size = (endPixelIdx - startPixelIdx) * BYTES_PER_PIXEL;
		byte[] sub = new byte[size];
		int startIdx = startPixelIdx * BYTES_PER_PIXEL;
		System.arraycopy(buffer, startIdx, sub, 0, size);
		return new BgraPreImageBuffer(sub);
	}

	/**
	 * @return the raw buffer
	 */
	public byte[] getRawBuffer() {
		return buffer;
	}

	/**
	 * @param idx
	 *            the pixel index
	 * @param value
	 *            the alpha value to set
	 */
	public void setAlpha(int idx, int value) {
		buffer[idx * BYTES_PER_PIXEL + 3] = (byte) value;

	}

}