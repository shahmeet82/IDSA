package mxs170043;

/*
	Starter code for LP2
	Aishwarya Patel - aap171530
	Abhishek Kulkarni - ask171730
	Meet Shah
	Yash Modi
*/

import java.util.Iterator;
import java.util.Random;

//Skeleton for skip list implementation.
public class SkipList<T extends Comparable<? super T>> {

	static final int PossibleLevels = 33;
	Entry<T> head, tail;
	int size;
	int maxLevel;
	Entry<T>[] last;
	Random random;

	static class Entry<E> {

		E element;
		Entry[] next;
		Entry prev;
		int[] span;

		public Entry(E x, int level) {
			element = x;
			next = new Entry[level];
			span = new int[level]; //
			prev = null;
		}

		public E getElement() {
			return element;
		}

		public String toString() {
			return String.valueOf("Element: " + this.element + ", Length: " + next.length);
		}

		public boolean equals(Entry<E> entry) {
			return (this.element == entry.element);
		}
	}

	// Constructor
	public SkipList() {
		head = new Entry<>(null, PossibleLevels);
		tail = new Entry<>(null, PossibleLevels);
		size = 0;
		maxLevel = 1;
		last = new Entry[PossibleLevels];
		random = new Random();
	}

	// Add x to list. If x already exists, reject it. Returns true if new node is
	// added to list
	public boolean add(T x) {
		if (contains(x))
			return false;
		int level = chooseLevel();
		Entry<T> entry = new Entry<>(x, level);
		for (int i = 0; i < level; i++) {
			if (last[i] != null) {
				entry.next[i] = last[i].next[i];
				last[i].next[i] = entry;
			}
		}
		if (entry.next[0] != null) {
			(entry.next[0]).prev = entry;
			entry.prev = last[0];
		} else {
			entry.prev = last[0];
			tail.prev = entry;
		}
		size++;
		return true;
	}

	public int chooseLevel() {
		int level = 1;
		while (random.nextBoolean() && level < PossibleLevels) {
			level++;
		}
		if (level > maxLevel) {
			maxLevel = level;
		}
		return level;
	}

	// Returns element. If found return entry object, else null
	public Entry<T> find(T x) {
		Entry<T> cursor = head;
		for (int i = (maxLevel - 1); i >= 0; i--) {
			while (cursor.next[i] != null && ((Comparable<T>) cursor.next[i].element).compareTo(x) < 0
					&& cursor.next[i].element != null) {
				cursor = cursor.next[i];
			}
			last[i] = cursor;
		}
		return cursor.next[0];
	}

	// Find smallest element that is greater or equal to x
	public T ceiling(T x) {
		find(x);
		T element = null;
		try {
			element = (T) last[0].next[0].element;
		} catch (Exception e) {
		}
		return element;
	}

	// Does list contain x?
	public boolean contains(T x) {
		if (find(x) == null || last[0].next[0] == null) {
			return false;
		}
		return last[0].next[0].element.equals(x);
	}

	// Return first element of list
	public T first() {
		if (head == null || head.next[0] == null) {
			return null;
		}
		return (T) head.next[0].element;
	}

	// Find largest element that is less than or equal to x
	public T floor(T x) {
		find(x);
		if (last[0].next[0].element.equals(x)) {
			return (T) last[0].next[0].element;
		}
		return (T) last[0].element;
	}

	// Return element at index n of list. First element is at index 0.
	public T get(int n) {
		return getLinear(n);
	}

	// O(n) algorithm for get(n)
	public T getLinear(int n) {
		if (n > (size) || n < 0) {
			return null;
		}
		Entry<T> cursor = head;
		for (int i = 0; i < n; i++) {
			cursor = cursor.next[0];
		}
		return (T) cursor.next[0].element;
	}

	// Optional operation: Eligible for EC.
	// O(log n) expected time for get(n). Requires maintenance of spans, as
	// discussed in class.
	public T getLog(int n) {
		rebuild();
		if (n > (size) || n < 0) {
			return null;
		}
		Entry<T> cursor = head;
		int counter = (int) ((int) Math.log(n) / Math.log(2) + 1);
		// System.out.println("Counter " + counter);
		for (int i = (counter); i >= 0; i--) {
			if (cursor.next[i] != null && cursor.next[i].element != null) {
				cursor = cursor.next[i];
			}
			last[i] = cursor;
		}
		return (T) cursor.element;

	}

	// Is the list empty?
	public boolean isEmpty() {
		return (size == 0);
	}

	// Iterate through the elements of list in sorted order
	public Iterator<T> iterator() {
		return new SkipListIterator<T>();
	}

	protected class SkipListIterator<T> implements Iterator<T> {

		Entry<T> cursor;
		boolean ready; // is item ready to be removed?

		SkipListIterator() {
			cursor = (Entry<T>) head;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next[0] != null;
		}

		public T next() {
			cursor = cursor.next[0];
			if (cursor != null) {
				return (T) cursor.next[0].element;
			}
			return null;
		}

	} // end of class SkipListIterator

	// Return last element of list
	public T last() {
		if (tail.prev != null) {
			return (T) tail.prev.element;
		}
		return null;
	}

	public int distanceChooseLevel(int distance, int size) {
		int[] distArray = calculateDistance(size);
		return distArray[distance];
	}

	public int[] calculateDistance(int size) {

		int distance[] = new int[size + 1];

		distance[0] = 33;
		int index = 0;
		int value = 0;
		int sizeTemp = size;
		if (sizeTemp > 0) {
			value = (int) (Math.log(sizeTemp) / Math.log(2)) + 1;
			index = (int) Math.pow(2, value - 1);
			sizeTemp = index;
			distance[index] = value;
		}
		while (index != 1) {
			value = (int) (Math.log(sizeTemp) / Math.log(2));
			index = (int) Math.pow(2, value - 1);
			sizeTemp = index;
			distance[index] = value;
		}
		int count = 2;
		if (size >= 3) {
			distance[3] = 1;
		}
		int indexx = 4;
		while (indexx <= size) {
			for (int i = 1; i < indexx; i++) {
				if (indexx + i <= size) {
					distance[indexx + i] = distance[indexx - i];
				}
			}
			count++;
			indexx = (int) Math.pow(2, count);
		}

		return distance;
	}
	// Optional operation: Reorganize the elements of the list into a perfect skip
	// list
	// Not a standard operation in skip lists. Eligible for EC.

	public void rebuild() {
		int count = 1;
		if (head == null) {
			return;
		}
		Entry<T> cursor = head;
		cursor = cursor.next[0];
		while (cursor != null) {
			int level = distanceChooseLevel(count, size);
			Entry<T> removeElement = cursor;
			// System.out.println(removeElement + "--------" + level + "--" + count);
			T removedElement = remove(removeElement.element);
			if (removedElement == null)
				return;
			Entry<T> entry = new Entry<>(removedElement, level);
			for (int i = 0; i < level; i++) {
				if (last[i] != null) {
					entry.next[i] = last[i].next[i];
					last[i].next[i] = entry;
				}
			}
			if (entry.next[0] != null) {
				(entry.next[0]).prev = entry;
				entry.prev = last[0];
			} else {
				entry.prev = last[0];
				tail.prev = entry;
			}
			size++;
			count++;
			cursor = cursor.next[0];
		}
	}

	// Remove x from list. Removed element is returned. Return null if x not in list
	public T remove(T x) {
		if (!contains(x)) {
			return null;
		}
		Entry<T> entry = last[0].next[0];
		for (int i = 0; i < entry.next.length; i++) {
			last[i].next[i] = entry.next[i];
		}
		if (entry.next[0] != null) {
			entry.next[0].prev = last[0];
		} else {
			tail.prev = last[0];
		}
		size--;
		return entry.element;
	}

	// Return the number of elements in the list
	public int size() {
		return size;
	}

	// Print the list by iterating
	public void printList() {
		if (head == null) {
			return;
		}
		Entry<T> cursor = head;
		if (cursor.next[0] != null)
			cursor = cursor.next[0];
		while (cursor != null) {
			System.out.print(cursor.element + (cursor.next[0] == null ? "" : "-"));
			cursor = cursor.next[0];
		}
		System.out.println();
	}

	public void printListReverse() {
		if (tail == null) {
			return;
		}
		Entry<T> cursor = tail;
		if (cursor.prev != null)
			cursor = cursor.prev;
		while (cursor != null && cursor != head) {
			System.out.print(cursor.element + (cursor.prev == head ? "" : "-"));
			cursor = cursor.prev;
		}
		System.out.println();
	}
}
