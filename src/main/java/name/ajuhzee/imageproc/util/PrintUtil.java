package name.ajuhzee.imageproc.util;

import java.util.OptionalInt;

/**
 * Provides a class to print out 2D Arrays.
 *
 * @author Ajuhzee
 */
public class PrintUtil {

	/**
	 * Prints out the array.
	 * @param array the array to print out
	 */
	public static void print2DIntArray(OptionalInt[][] array) {
		for (int i = 0; i < array.length; ++i) {
			for (int j = 0; j < array[0].length; ++j) {
				if (array[i][j].isPresent()) {
					System.out.print(String.format(" %2d", array[i][j].getAsInt()));
				} else {
					System.out.print(" xx");
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
	}
}
