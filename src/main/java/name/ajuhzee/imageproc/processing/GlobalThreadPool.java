package name.ajuhzee.imageproc.processing;

import java.util.concurrent.ForkJoinPool;

/**
 * Provides a global thread pool to be used by all processing functions
 */
public class GlobalThreadPool {

	/**
	 * The global fork/join pool
	 */
	public static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
}
