package name.ajuhzee.imageproc.util;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import name.ajuhzee.imageproc.processing.Area;
import name.ajuhzee.imageproc.processing.Direction;
import org.junit.Test;

import java.io.File;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class ImageUtilsTest {

	@Test
	public void testCropToContent() throws Exception {
		Image img = ImageUtils.loadImage(new File("C:/charactermap.png"));
		Image cropped = ImageUtils.cropToContent(img, Color.BLACK);
		ImageUtils.saveImage(Paths.get("C:/output.png"), cropped);
	}

	@Test
	public void getFirstSliceWithColor_ShouldReturn3_WhenCalledWithPositiveX() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceTest.png"));

		int firstSlice = ImageUtils.getFirstSliceWithColor(img, Color.BLACK, 0, Direction.POSITIVE_X).getAsInt();

		assertThat(firstSlice, is(3));
	}

	@Test
	public void getFirstSliceWithColor_ShouldReturn0_WhenCalledWithPositiveY() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceTest.png"));

		int firstSlice = ImageUtils.getFirstSliceWithColor(img, Color.BLACK, 0, Direction.POSITIVE_Y).getAsInt();

		assertThat(firstSlice, is(0));
	}

	@Test
	public void getFirstSliceWithColor_ShouldReturn8_WhenCalledWithNegativeX() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceTest.png"));

		int firstSlice =
				ImageUtils.getFirstSliceWithColor(img, Color.BLACK, (int) (img.getWidth() - 1), Direction.NEGATIVE_X)
						.getAsInt();

		assertThat(firstSlice, is(8));
	}

	@Test
	public void getFirstSliceWithColor_ShouldReturn7_WhenCalledWithNegativeY() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceTest.png"));

		int firstSlice = ImageUtils.getFirstSliceWithColor(img, Color.BLACK, (int) (img.getHeight() - 1), Direction.
				NEGATIVE_Y).getAsInt();

		assertThat(firstSlice, is(7));
	}

	@Test
	public void getFirstSliceWithoutColor_ShouldReturn0_WhenCalledWithPositiveY() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceWithoutTest" +
						".png"));

		int firstSlice = ImageUtils.getFirstSliceWithoutColor(img, Color.WHITE, 0, Direction.
				POSITIVE_Y).getAsInt();

		assertThat(firstSlice, is(0));
	}

	@Test
	public void getFirstSliceWithoutColor_ShouldReturn3_WhenCalledWithPositiveX() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceWithoutTest" +
						".png"));

		int firstSlice = ImageUtils.getFirstSliceWithoutColor(img, Color.WHITE, 0, Direction.
				POSITIVE_X).getAsInt();

		assertThat(firstSlice, is(3));
	}

	@Test
	public void getFirstSliceWithoutColor_ShouldReturn7_WhenCalledWithNegativeY() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceWithoutTest" +
						".png"));

		int firstSlice = ImageUtils.getFirstSliceWithoutColor(img, Color.WHITE, (int) (img.getHeight() - 1), Direction.
				NEGATIVE_Y).getAsInt();

		assertThat(firstSlice, is(7));
	}

	@Test
	public void getFirstSliceWithoutColor_ShouldReturn8_WhenCalledWithNegativeX() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceWithoutTest" +
						".png"));

		int firstSlice = ImageUtils.getFirstSliceWithoutColor(img, Color.WHITE, (int) (img.getWidth() - 1), Direction.
				NEGATIVE_X).getAsInt();

		assertThat(firstSlice, is(8));
	}

	@Test
	public void getFistSliceWithColor_ShouldReturn4_WhenCalledWithAreaAndPositiveY() throws Exception {
		Image img =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("firstSliceTest.png"));
		Area area = new Area(0, 4, 2, 8);

		int firstSlice = ImageUtils.getFirstSliceWithColor(img, area, Color.BLACK, Direction.
				POSITIVE_Y).getAsInt();

		assertThat(firstSlice, is(4));
	}
}
