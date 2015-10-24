package name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber;

import name.ajuhzee.imageproc.util.ImageUtils;
import org.junit.Test;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class EulerNumberComputeTest {

	private int getEulerNumberForResource(String resource) throws Exception {
		InputStream img = EulerNumberComputeTest.class.getClassLoader().getResourceAsStream(resource);
		return EulerNumberCompute.getEulerNumber(ImageUtils.loadImage(img));
	}

	@Test
	public void GetEulerNumber_ShouldReturn0_WhenImgHas0Holes0Objects() throws Exception {
		int eulerNumber = getEulerNumberForResource("Img0Holes0Objects.png");

		assertThat(eulerNumber, is(0));
	}

	@Test
	public void GetEulerNumber_ShouldReturn1_WhenImgHas0Holes1Objects() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img0Holes1Objects.png");

		assertThat(eulerNumberForResource, is(1));
	}

	@Test
	public void GetEulerNumber_ShouldReturn2_WhenImgHas0Holes2Objects() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img0Holes2Objects.png");

		assertThat(eulerNumberForResource, is(2));
	}

	@Test
	public void GetEulerNumber_ShouldReturn0_WhenImgHas1Holes1Objects() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img1Holes1Objects.png");

		assertThat(eulerNumberForResource, is(0));
	}


	@Test
	public void GetEulerNumber_ShouldReturnMinus1_WhenImgHas2Holes1Objects() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img2Holes1Objects.png");

		assertThat(eulerNumberForResource, is(-1));
	}

	@Test
	public void GetEulerNumber_ShouldReturn2_WhenImgHas2Holes4Objects() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img2Holes4Objects.png");

		assertThat(eulerNumberForResource, is(2));
	}

	@Test
	public void GetEulerNumber_ShouldReturnMinus1_WhenImgHas2Holes1ObjectsAndBlackTouchesEdge() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img2Holes1Objects_2.png");

		assertThat(eulerNumberForResource, is(-1));
	}

	@Test
	public void GetEulerNumber_ShouldReturn0_WhenImgHas1Holes1ObjectsAndBlackTouchesEdge() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img1Holes1Objects_3.png");

		assertThat(eulerNumberForResource, is(0));
	}

	@Test
	public void GetEulerNumber_ShouldReturn1_WhenImgHas0Holes1ObjectsAndHasDiagonalPixels() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img0Holes1Objects_2.png");

		assertThat(eulerNumberForResource, is(1));
	}

	@Test
	public void GetEulerNumber_ShouldReturn1_WhenImgHas0Holes1ObjectsAndHasPixelNoise() throws Exception {
		int eulerNumberForResource = getEulerNumberForResource("Img0Holes1Objects_3.png");

		assertThat(eulerNumberForResource, is(1));
	}
}
