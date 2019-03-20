/**
 *
 * @author Shreeya Degaonkar(sxd174830), Meet Shah (mxs170043)
 * Implementation of the O(n) algorithm to find K Largest elements (select)
 */


package mxs170043;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Stream;


public class KLargestElements {
    public static Random random = new Random();
    int q;
    int T = 17;  //Threshold
    int x;
    int[] arr;
    int left;
    int right;
    /**
     * Sort the elements of the array when n<T
     * @param arr
     * @param p
     * @param r
     */
    private void insertionSort(int[] arr, int p, int r) {
        for(int i=p;i<r;i++) {
            int key = arr[i];
            int j = i-1;
            while(j>=p && arr[j]>key) {
                arr[j+1] = arr[j];
                j=j-1;
            }
            arr[j+1] =key;
        }
    }

    /**
     * Select algorithm to find k largest elements
     * @param arr
     * @param k
     */
    public void select(int[] arr, int k) {
        int res;
        res= select(arr, 0, arr.length,k);
    }

    /**
     *
     * @param arr
     * @param p
     * @param n
     * @param k
     * @return the index of the kth largest element
     * the kth largest elements are present in arr[arr.length-k....arr.length-1]
     */
    public int select(int[] arr, int p, int n, int k) {
        //int q;
        if(n<T) {
            insertionSort(arr, p, p+n-1);
        }
        q= randomizedPartition(arr, p, p+n-1);
        left=q-p;
        right=n-left-1;
        if(right>=k) {
            return select(arr,q+1,right,k);
        }
        else if((right+1)==k) {
            return q;
        }
        else {
            return select(arr,p,left,k-right-1);
        }
    }

    /**
     *
     * @param arr
     * @param p
     * @param r
     * @return index for partition
     */
    private int randomizedPartition(int[] arr, int p, int r) {
        Random rand = new Random();
        int i= rand.nextInt(r-p+1)+p;
        Shuffle.swap(arr, i, r);
        return partition(arr,p,r);
    }
    private int partition(int[] arr, int p, int r) {
        x=arr[r];
        int i=p-1;
        // TODO check if j<r-1 or j<=r-1
        for(int j=p;j<=r-1;j++) {
            if(arr[j]<=x) {
                i=i+1;
                Shuffle.swap(arr, i, j);
            }
        }
        Shuffle.swap(arr, r, i+1);
        return i+1;
    }

    public static void main(String[] args) {
        int n=256000000;
        int k = n/2;
        if(args.length > 0) { n = Integer.parseInt(args[0]); }

        KLargestElements test = new KLargestElements();
        test.arr = new int[n];
        for(int i=0; i<n; i++) {
            test.arr[i] = i;
        }
        Timer timer = new Timer();
        Shuffle.shuffle(test.arr);
        test.select(test.arr,k);
        timer.end();
        System.out.println(timer);
    }



    /* Shuffle the elements of an array arr[from..to] randomly */
    public static class Shuffle {

        public static void shuffle(int[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static<T> void shuffle(T[] arr) {
            shuffle(arr, 0, arr.length-1);
        }

        public static void shuffle(int[] arr, int from, int to) {
            int n = to - from  + 1;
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        public static<T> void shuffle(T[] arr, int from, int to) {
            int n = to - from  + 1;
            Random random = new Random();
            for(int i=1; i<n; i++) {
                int j = random.nextInt(i);
                swap(arr, i+from, j+from);
            }
        }

        static void swap(int[] arr, int x, int y) {
            int tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        static<T> void swap(T[] arr, int x, int y) {
            T tmp = arr[x];
            arr[x] = arr[y];
            arr[y] = tmp;
        }

        public static<T> void printArray(T[] arr, String message) {
            printArray(arr, 0, arr.length-1, message);
        }

        public static<T> void printArray(T[] arr, int from, int to, String message) {
            System.out.print(message);
            for(int i=from; i<=to; i++) {
                System.out.print(" " + arr[i]);
            }
            System.out.println();
        }
    }
}