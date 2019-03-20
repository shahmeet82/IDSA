package mxs170043;// Starter code for bounded-size Binary Heap implementation


import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;
import java.util.Comparator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    int pqSize;
    static final int pqCapacity = 10;

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
        this.pqSize = 0;

    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        comp = c;
    }

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     * Implement this if solving optional problem.  To be called from heap sort.
     */
    /*
    private BinaryHeap(T[] q, Comparator<T> c, int n) {
        pq = q;
        comp = c;
        // You need to add more code here to build queue
    }
    */

    public void add(T x) { /* throw exception if pq is full */
        if (pqSize >= pqCapacity){
            throw new java.lang.Error("Priority Queue Full!!");
        }
        pq[pqSize] = x;
        if (pqSize > 0) {
            percolateUp(pqSize);
        }
        pqSize++;
    }





        public boolean offer(T x) {
        if (pqSize >= pqCapacity) {
            return false;

        } else {
//            pq[pqSize] = x;
//            if (pqSize > 0) {
//                percolateUp(pqSize);
//            }
//            pqSize++;
            add(x);
           return true;
        }/* return false if pq is full */
    }


    public T remove() { /* throw exception if pq is empty */

        if (pqSize == 0){
            throw new java.lang.Error("Priority Queue Empty!!");
        }
        T root = pq[0];
        pq[0] = pq[pqSize - 1];
        pqSize--;

        if (pqSize > 0){
            percolateDown(0);
        }
        return root;
    }

    public T poll() { /* return false if pq is empty */
        T root = pq[0];

        if (pqSize == 0){
            return null;
        }
        else {
            root = this.remove();
//        T root = pq[0];
//        pq[0] = pq[pqSize - 1];
//        pqSize--;
//        percolateDown(0);
//            return root;
        }return root;
    }

    public T peek() { /* return null if pq is empty */

        if (pqSize == 0) {
            return null;
        }
        return pq[0];
    }




    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) { /* to be implemented */
        //System.out.println(parentPosition);

        int parentPosition = parent(i);
        int pos = i;
        T num = pq[i];

        while(pos > 0 && comp.compare(pq[parentPosition], num) == 1) {
            pq[pos] = pq[parentPosition];
            pos = parentPosition;
            parentPosition = parent(pos);
        }
        pq[pos] = num;
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) { /* to be implemented */

        int child = leftChild(i);
        System.out.println(i);
        System.out.println(pq[child]);
        T num = pq[i];
        while (child <= pqSize - 1) {
            if (child < pqSize - 1 && (comp.compare(pq[child], pq[child + 1]) > 0)){
                System.out.println("Right Child");
                child = child + 1;
            }

            if (comp.compare(pq[child], num) >= 0){
                System.out.println("Child Greater");
                break;
            }
            System.out.println("Decision:" + comp.compare(pq[child], num));
            System.out.println("compute node");
            pq[i] = pq[child];
            i = child;
            child = leftChild(i);

        }

        pq[i] = num;

    }

    // Assign x to pq[i].  Indexed heap will override this method
    void move(int i, T x) {
        pq[i] = x;
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    int leftChild(int i) {
        return 2 * i + 1;
    }

// end of functions for team project


    public static void main(String[] args) {
        int size=pqCapacity;

        Integer[] arr = {5, 3, 0, 21, 4, 2, 15, 9, 6,7};


        BinaryHeap<Integer> obj = new BinaryHeap<>(arr);
        //for (int i = 0; i < size; i++) {
            //obj.offer(Integer.valueOf(rand.nextInt(1000)));
          //  obj.add(arr[i]);
        //}

        Scanner in = new Scanner(System.in);
        System.out.println("Binary Heap Operations:");
        System.out.println("1. Add");
        System.out.println("2. Offer");
        System.out.println("3. remove");
        System.out.println("4. poll");
        System.out.println("5. Peek");
        System.out.print("> ");
        whileLoop:
        while (in.hasNext()) {
            int choice = in.nextInt();
            switch (choice) {
                //Adds elements at the end of the queue.
                case 1: {
                    System.out.print("Enter the element: ");
                    int addTemp = in.nextInt();
                    obj.add(addTemp);
//                    if (!obj.add(Integer.valueOf(addTemp))) {
//                        System.out.println("Element not added; queue full");
//                        break;
//                    }
                    System.out.println("Element added successfully");
                    break;
                }
                //Remove elements from the front of the queue.
                case 2: {
                    System.out.print("Enter the element: ");
                    int addTemp = in.nextInt();
                    if (!obj.offer(Integer.valueOf(addTemp))) {
                        System.out.println("Element not added; Priority queue full");
                        break;
                    }
                    System.out.println("Element added successfully");
                    break;
                }
                case 3: {
                    System.out.println("Removed Element:" + obj.remove());
                    break;
                }
                case 4: {
                    System.out.println("Removed Element:" + obj.poll());
                    break;
                }
                //Peek the top most element
                case 5: {
                    System.out.println("Top Most Element:" + obj.peek());
                    break;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break whileLoop;
                }

//        for (Integer item: arr) {
//            System.out.print(item);
//        }
//
//        for (Integer item: arr) {
//            obj.add(item);
//        }
//        System.out.println();
//
//        for (Integer vble: obj.pq) {
//            System.out.print(vble);
//        }
//
//        System.out.println();
//
//        Integer root = obj.poll();
//        System.out.println(root);
//
//        for (Integer vble: obj.pq) {
//            System.out.print(vble);
//        }


            }

        }
    }
};
