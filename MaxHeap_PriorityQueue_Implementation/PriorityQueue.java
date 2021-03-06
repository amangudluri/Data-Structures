public class PQ<E> {

	private transient E[] queue;
	private int currentsize = 0;
	private Comparator<E> comparator = null;

	/**
	 * Creates a PQ with the default initial capacity (11) that orders its
	 * elements according to their natural ordering (Using Comparable).
	 */
	public PQ() {
		this(11, null);
	}

	/**
	 * Initializes a PriorityQueue object with the specified initial capacity
	 * that orders its elements according to the specified comparator.
	 *
	 * @param initialCapacity
	 *            the initial capacity for this priority queue.
	 * @param comparator
	 *            the comparator used to order this priority queue. If null then
	 *            the order depends on the elements’ natural ordering.
	 * @throws IllegalArgumentException
	 *             if initialCapacity is less than 1
	 */
	public PQ(int initialCapacity, Comparator<E> comparator) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		queue = (E[]) new Object[initialCapacity];
		this.comparator = comparator;
	} // constructor

	/**
	 * Inserts the specified element into this priority queue.
	 *
	 * @return a boolean representing the success of addition
	 * @throws ClassCastException
	 *             if the specified element cannot be compared with elements
	 *             currently in this priority queue according to the priority
	 *             queue's ordering
	 * @throws NullPointerException
	 *             if the specified element is null
	 */
	public boolean add(E e) {
		if (e == null)
			throw new NullPointerException();

		int tempsize = currentsize;

		if (tempsize >= queue.length)
			grow(tempsize + 1);

		currentsize = tempsize + 1;

		if (tempsize == 0)
			queue[0] = e;
		else
			siftUp(tempsize, e);

		return true;
	}

	/**
	 * Removes the highest valued element from this PriorityQueue object. The
	 * worstTime(n) is O(log n).
	 *
	 * @return the element removed.
	 *
	 * @throws NoSuchElementException
	 *             – if this PriorityQueue object is empty.
	 *
	 */
	public E remove() {
		if (currentsize == 0)
			throw new NoSuchElementException();

		int s = --currentsize;

		E elem = (E) queue[0];
		E last = (E) queue[s];

		queue[s] = null;
		if (s != 0)
			siftDown(0, last);

		return elem;
	}

	public String toString() {
		String s = "";

		for (int i = 0; i < currentsize; i++)
			s += queue[i] + " ";

		return s;
	}

	/* --- Private methods --- */

	/**
	 * Increases the capacity of the array.
	 *
	 * @param minCapacity
	 *            the desired minimum capacity
	 */
	private void grow(int minCapacity) {
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		int oldCapacity = queue.length;
		// Double current size if small; else grow by 50%
		int newCapacity = ((oldCapacity < 64) ? ((oldCapacity + 1) * 2) : ((oldCapacity / 2) * 3));
		if (newCapacity < 0) // overflow
			newCapacity = Integer.MAX_VALUE;
		if (newCapacity < minCapacity)
			newCapacity = minCapacity;
		queue = Arrays.copyOf(queue, newCapacity);
	}

	/**
	 * Inserts item x at position k, maintaining heap invariant by promoting x
	 * up the tree until it is greater than or equal to its parent, or is the
	 * root. (according to the elements’ implementation of the Comparable
	 * interface)
	 * 
	 * @param k
	 *            the position to fill
	 * @param x
	 *            the item to insert
	 */
	private void siftUp(int n, E x) {
		while (n > 0 && condition((n - 1) >>> 1, x)) {
			int parent = (n - 1) >>> 1;
			queue[n] = queue[parent]; // swap
			n = parent;
		}
		queue[n] = x;
	}

	/**
	 * A support method for the SiftUp method for checking the condition between
	 * a node and its parent
	 * 
	 * @param parentindex,
	 *            the index of the nodes parent
	 * @param x,
	 *            the element to be inserted
	 * @return a boolean denoting the correctness of the condition
	 */
	@SuppressWarnings("unchecked")
	private boolean condition(int parentindex, E x) {
		if (comparator == null) {
			return ((Comparable<E>) queue[parentindex]).compareTo(x) < 0;
		} else {
			return comparator.compare(queue[parentindex], x) < 0;
		}

	}

	/**
	 * Maintains the heap properties in this PriorityQueue object while,
	 * starting at a specified index, inserts a specified element where it
	 * belongs. The worstTime(n) is O(log n).
	 *
	 * @param k
	 *            –the specified position where the restoration of the heap will
	 *            begin.
	 * @param x
	 *            –the specified element to be inserted.
	 *
	 */
	private void siftDown(int k, E x) {
		Comparable<E> key = (Comparable<E>) x;
		int half = currentsize >>> 1; // loop while a non-leaf
		while (k < half) {
			int child = (k << 1) + 1; // assume left child is least
			E c = queue[child];
			int right = child + 1;
			if (right < currentsize && ((Comparable<E>) c).compareTo((E) queue[right]) < 0)
				c = queue[child = right];
			if (key.compareTo((E) c) >= 0)
				break;
			queue[k] = c;
			k = child;
		}
		queue[k] = x;
	}
}
