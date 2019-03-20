package mxs170043; /** @author
 *  Binary search tree (starter code)
 **/

import java.util.*;


public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
        public T getE(){
            return element;
        }
    }

    Entry<T> root;
    int size;
    Deque<Entry<T>> stack;
    Iterator<T> it;
    Comparator<T> comp = (T a, T b) -> a.compareTo(b);
    int position;

    public Iterator<T> iterator() {
        return null;
    }





    public BinarySearchTree() {
        root = null;
        size = 0;
        stack = new ArrayDeque<>(100);
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     //     */

    public Entry<T> get(T x){
        //Entry[] stack = new Entry[100];
        stack.clear();
        stack.push(new Entry(null, null, null));
        return get(root, x);
    }

    public Entry<T> get(Entry x, T element){
        if (x == null || comp.compare((T)x.getE(), element) == 0){
            return x;
        }
        while (true){
            if (comp.compare(element, (T)x.getE()) < 0){
                if(x.left == null){
                    break;
                }
                stack.push(x);
                x = x.left;
            }
            else if(comp.compare(element, (T)x.getE()) == 0){
                break;
            }
            else{
                if (x.right == null){
                    break;
                }
                stack.push(x);
                x = x.right;
            }
        }
        return x;
    }




    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry element = get(x);
        if (element == null || comp.compare((T)element.getE(), x) != 0){
            return false;
        }
        return true;
    }



    /** TO DO: Add x to tree. 
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        Entry<T> element, newEntry;
        if (size == 0){
            root = new Entry<>(x, null, null);
            size = 1;
            return true;
        }
        else{
            stack.clear();
            element = get(x);
            if (element.getE() == x){
                newEntry = new Entry(x, element.left, element.right);
                element = newEntry;
                return false;
            }
            else {
                newEntry = new Entry<>(x, null, null);
                if (comp.compare(x, (T)element.getE()) > 0){
                    element.right = newEntry;
                }
                else{
                    element.left = newEntry;
                }
                size++;
                return true;
            }
        }
    }

    /** TO DO: Remove x from tree. 
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        Entry minRight;
        T result;
        stack.clear();

        if (size == 0){
            return null;
        }
        Entry t = get(x);

        if (comp.compare(x, (T)t.getE()) != 0) {
            return null;
        }
        result = (T)t.getE();
        if (t.left == null || t.right == null){
            bypass(t);
        }
        else{
            stack.clear();
            stack.push(t);
            minRight = get(t.right, (T)t.getE());
            t.element = minRight.element;
            bypass(minRight);
        }
        size--;
        return result;
    }

    public void bypass(Entry t){
        Entry parent = stack.peek();
        Entry child = t.left == null? t.right: t.left;

        if (parent == null){

            root = child;
        }
        else if (parent.left == t){
            parent.left = child;
        }
        else{
            parent.right = child;
        }
    }

    public T min() {
        if (size == 0){
            return null;
        }
        Entry<T> t = root;
        while (t.left != null){
            t = t.left;
        }
        return t.element;
    }

    public T max() {
        if (size == 0){
            return null;
        }
        Entry<T> t = root;
        while(t.right != null){
            t = t.right;
        }
        return t.element;
    }

    // TODO: Create an array with the elements using in-order traversal of tree


    public Comparable[] toArray(){
        position = 0;
        Comparable[] arr = new Comparable[size];
        populate(root, arr);
        return arr;
    }

    public void populate(Entry<T> node, Comparable[] arr){
        if (node != null){
            populate(node.left, arr);
            arr[position] = node.element;
            position++;
            populate(node.right, arr);
        }
    }


    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        System.out.println("Binary Search Tree Operations:");
        System.out.println("1. Add");
        System.out.println("2. remove");
        System.out.println("3. contains");
        System.out.println("4. min");
        System.out.println("5. max");
        System.out.println("6. toArray");
        System.out.print("> ");
        whileLoop:
        while(in.hasNext()) {
            int choice = in.nextInt();
            switch (choice) {
                case 1: {
                    System.out.print("Enter the element to add: ");
                    int addTemp = in.nextInt();
                    System.out.print("Add " + addTemp + " : ");
                    t.add(addTemp);
                    t.printTree();
                    break;
            }   case 2:{
                    System.out.print("Enter the element to remove: ");
                    int removeTemp = in.nextInt();
                    System.out.print("Remove " + removeTemp + " : ");
                    t.remove(Math.abs(removeTemp));
                    t.printTree();
                    break;
            }   case 3: {
                    System.out.print("Enter the element to check: ");
                    int cont = in.nextInt();
                    System.out.print("contains " + cont + " : ");
                    System.out.print(t.contains(cont));
                    //t.printTree();
                    break;
                }
                case 4: {

                    System.out.print("Minimum "  + " : "+ t.min());
                    t.printTree();
                    break;
                }
                case 5: {

                    System.out.print("Maximum "  + " : "+ t.max());
                    t.printTree();
                    break;
                }
                case 6: {
                    Comparable[] arr = t.toArray();
                    System.out.print("Final: ");
                    for (int i = 0; i < t.size; i++) {
                        System.out.print(arr[i] + " ");
                    }break;
                }
                default: {
                    System.out.println("Invalid Choice!");
                    break whileLoop;
                    }

            }
        }

    }



}


/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/
