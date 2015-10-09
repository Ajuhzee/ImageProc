package name.ajuhzee.imageproc.util;

public class MathUtil {

	/**
	 * Compares the base with the second and returns how much the deviatingNumber deviates in percent.
	 *
	 * @param base
	 * @param deviatingNumber
	 * @return
	 */
	public static double deviation(double base, double deviatingNumber) {return Math.abs(base / deviatingNumber - 1);}

}
