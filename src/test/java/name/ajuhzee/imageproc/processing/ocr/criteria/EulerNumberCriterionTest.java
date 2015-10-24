package name.ajuhzee.imageproc.processing.ocr.criteria;

import javafx.scene.image.Image;
import name.ajuhzee.imageproc.processing.ocr.criteria.eulernumber.EulerNumberCriterion;
import name.ajuhzee.imageproc.util.ImageUtils;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;
import org.junit.Test;

import static org.junit.Assert.*;

public class EulerNumberCriterionTest {

	@Test
	public void Matches_ShouldReturnTrue_WhenImagesHaveSameNumberOfHolesAndObjects() throws Exception {
		Image img1 =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("Img2Holes1Objects.png"));
		Image img2 =
				ImageUtils.loadImage(this.getClass().getClassLoader().getResourceAsStream("Img2Holes1Objects_2.png"));

		assertThat(img1, hasSameEulerNumber(img2));
	}

	private Matcher<? super Image> hasSameEulerNumber(Image img) {
		return new HasSameEulerNumber(img);
	}

	private class HasSameEulerNumber extends TypeSafeDiagnosingMatcher<Image> {

		private final Image toTest;

		public HasSameEulerNumber(Image img) {
			toTest = img;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("the two images to have the same euler number");
		}

		@Override
		protected boolean matchesSafely(Image item, Description mismatchDescription) {
			boolean matches = new EulerNumberCriterion().matches(toTest, item);
			mismatchDescription.appendText("they did not have the same euler number");
			return matches;
		}
	}
}
