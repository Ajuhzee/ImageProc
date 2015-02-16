package name.ajuhzee.imageproc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * Is used to sort the menu items of the menu bar.
 * 
 * @author Ajuhzee
 *
 * @param <T>
 *            defines after what gets sorted
 */
public class SortedList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;

	private final Comparator<T> comparator;

	/**
	 * Creates the sorted list.
	 * 
	 * @param comparator
	 */
	public SortedList(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	/**
	 * <b>For SortedList, the index has no effect.</b><br />
	 * <br />
	 * {@inheritDoc}
	 */
	@Override
	public void add(int index, T element) {
		add(element);
	}

	@Override
	public boolean add(T e) {
		return binaryInsert(e) != -1;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean inserted = false;

		for (final T elem : c) {
			if (add(elem)) {
				inserted = true;
			}
		}

		return inserted;
	}

	/**
	 * <b>For SortedList, the index has no effect.</b><br />
	 * <br />
	 * {@inheritDoc}
	 */
	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		boolean inserted = false;

		for (final T elem : c) {
			if (add(elem)) {
				inserted = true;
			}
		}

		return inserted;
	}

	private int binaryInsert(T e) {
		@SuppressWarnings("unchecked")
		int insertAt = Arrays.binarySearch((T[]) this.toArray(), e, comparator);
		if (insertAt < 0) {
			insertAt = -(insertAt + 1);
		}
		super.add(insertAt, e);
		return insertAt;
	}

	/**
	 * Inserts the element at the correct position in the list.
	 *
	 * @param e
	 *            the element to insert
	 * @return the position at which the element has been inserted
	 */
	public int insert(T e) {
		return binaryInsert(e);
	}

	/**
	 * <b>For SortedList, the index has no effect.</b><br />
	 * <br />
	 * {@inheritDoc}
	 */
	@Override
	public T set(int index, T element) {
		final T prev = remove(index);
		add(element);
		return prev;
	}

}
