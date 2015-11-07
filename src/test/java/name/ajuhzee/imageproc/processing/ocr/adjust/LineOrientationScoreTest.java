package name.ajuhzee.imageproc.processing.ocr.adjust;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.util.ImageUtils;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class LineOrientationScoreTest {


	@Test
	public void getScore_ShouldReturn40_WhenCalledWithRotateTest() throws Exception {
		final Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("rotatetest.png"));

		double score = new LineOrientationScore(img).getScore();

		assertThat(score, closeTo(40, 0.001));
	}

	@Test
	public void getBlankLines_ShouldReturn5_WhenCalledWithImg2Holes1Objects() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("Img2Holes1Objects" +
				".png"));

		double score = new LineOrientationScore(img).getScore();

		assertThat(score, closeTo(5, 0.001));
	}
	@Test
	public void getBlankLines_ShouldReturn5_WhenCalledWithImgImg0Holes1Objects_3() throws Exception {
		Image img = ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("Img0Holes1Objects_3.png"));

		double score = new LineOrientationScore(img).getScore();

		assertThat(score, closeTo(5, 0.001));
	}




	@Test
	public void getScore_ShouldReturn40_WhenCalledWithCharacterMap() throws Exception {
		final Image img = ImageUtils.loadImage(new File("C:/charactermap.png"));

		double score = new LineOrientationScore(img).getScore();

		assertThat(score, closeTo(40, 0.001));
	}
	@Test
	public void getScore_ShouldReturn40_WhenCalledWithCharacterMap90() throws Exception {
		final Image img = ImageUtils.loadImage(new File("C:/testAdjustImage.png"));

		double score = new LineOrientationScore(img).getScore();

		assertThat(score, closeTo(40, 0.001));
	}


}
