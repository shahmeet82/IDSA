package mxs170043;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implementation of Data Structure and Algorithms
 * Created by aak170230 Ameya Kasar  and mxs170043 Meet Shah on 9/2/2018.
 *
 * {@link DoublyLinkedList} class
 * This class provides the various methods for the implementation of DoublyLinkedList operations.
 * It extends the SinglyLinkedList class and adds additional features like methods prev(), hasPrev() and add(x).
 * This class defines its iterator which implements the @interface {@link DoublyLinkedListIterator} and
 * defines the implementation for the above mentioned methods.
 **/
public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

    /**
     * This is a static inner class which defines the structure of
     * an Entry of DoublyLinkedList.
     * It extends {@link mxs170043.SinglyLinkedList.Entry} and adds a new prev
     * elements which stores a reference to the previous element
     * @param <E> Type that is used to represent the Entry.
     */
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        SinglyLinkedList.Entry<E> prev;

        public Entry(E x, SinglyLinkedList.Entry<E> nxt, SinglyLinkedList.Entry<E> prev) {
            super(x, nxt);
            this.prev = prev;
        }
    }

    public DoublyLinkedList() {
        super();
    }

    /**
     * Returns an instance of {@link DLLIterator} which can be used to iterate over the list
     * @return an instance of DLLiteraotr.
     */
    public DLLIterator dllIterator() {
        return new DLLIterator();
    }

    /**
     * An interface which defines a contract of various methods that an interator should implement
     * @param <T> Type that is used to represent the Entry.
     */
    public interface DoublyLinkedListIterator<T> {
        boolean hasNext();

        boolean hasPrevious();

        T next();

        T previous();

        void add(T x);

        void remove();
    }


    public class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {
        DLLIterator() {
            super();
        }


        /**
         * This methods checks if there exists an element before the cursor
         * @return false if cursor points to head or cursor is the first element in the list
         */
        @Override
        public boolean hasPrevious() {
            return !(cursor == head || head.next == cursor);
        }

        /**
         * Returns the element present previous to the element pointed by the cursor.
         * A hasPrevious should be called before calling this method else it will throw {@link NoSuchElementException}
         * @return element that is situated previous to the element pointed by the cursor
         */
        @Override
        public T previous() {
            if (hasPrevious()) {
                ready = true;
                cursor = ((Entry<T>) cursor).prev;
                return cursor.element;
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * Adds an element after the element pointed by the cursor.
         * Moves the cursor to the newly added element
         * Increases the size of the list by 1.
         *
         * @param x element to be added in the list
         */
        @Override
        public void add(T x) {
            Entry<T> newEntry = new Entry<>(x, null, null);

            if (cursor == tail) {
                cursor.next = newEntry;
                newEntry.prev = cursor;
                tail = newEntry;
            } else {
                newEntry.prev = cursor;
                newEntry.next = cursor.next;
                ((Entry<T>) cursor.next).prev = newEntry;
                cursor.next = newEntry;
            }
            cursor = newEntry;
            ready = false;
            size++;
        }

        /**
         * Removes the element pointed by the cursor
         * If cursor points to the last element then tail also needs to be
         * dated accordingly
         * Reduces the size of the list by 1
         */
        @Override
        public void remove() {
            if (ready) {
                if (cursor == tail) {
                    cursor = ((Entry<T>) cursor).prev;
                    cursor.next = null;
                    tail = cursor;
                } else {
                    ((Entry<T>) cursor).prev.next = cursor.next;
                    ((Entry<T>) cursor.next).prev = ((Entry<T>) cursor).prev;
                    cursor = ((Entry<T>) cursor).prev;
                }
                size--;
            } else {
                throw new NoSuchElementException();
            }

            ready = false;
        }
    }
    // End of DLL iterator

    /**
     * Adds an element at the end of the list
     * @param x element to be added
     */
    @Override
    public void add(T x) {
        super.add(new Entry<>(x, null, tail));
    }

    public static void main(String[] args) throws NoSuchElementException {
        int n = 3;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
        for (int i = 1; i <= n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();
        DoublyLinkedListIterator<Integer> it = lst.dllIterator();

        Scanner in = new Scanner(System.in);
        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                case 3: //hasPrev
                    System.out.println(it.hasPrevious());
                    break;
                case 4://Prev
                    if (it.hasPrevious()) {
                        System.out.println(it.previous().toString());
                    } else {
                        break whileloop;
                    }
                    break;
                case 5:// add

                    System.out.println("Enter the value ");
                    it.add(in.nextInt());
                    lst.printList();
                    break;
                case 6: //Print list and show tail position
                    lst.printList();
                    System.out.println("Tail ->" + lst.tail.element);
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }
        lst.printList();
    }
}