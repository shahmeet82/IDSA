/** Sample starter code for SP9.
 *  @author Meet Shah & Jaiminee Kataria
 */

package mxs170043;
import java.util.Random;

public class SP9 {
    public static Random random = new Random();
    public static int numTrials = 100;
    public static int threshold=11;
    public static void main(String[] args) {
    int n = 80000000;  int choice =  1 + random.nextInt(4); //+ random.nextInt(4);
    if(args.length > 0) { n = Integer.parseInt(args[0]); }
    if(args.length > 1) { choice = Integer.parseInt(args[1]); }
    int[] arr = new int[n];
    for(int i=0; i<n; i++) {
        arr[i] = i;
    }
    Timer timer = new Timer();
    switch(choice) {
    	case 1: //Insertionsort @2800 ms to run
        Shuffle.shuffle(arr);
        numTrials = 1;
        insertionSort(arr);
        break;
        case 2: //Mergesort take 1 @15 ms to run.
        for(int i=0; i<numTrials; i++) {
           Shuffle.shuffle(arr);
           mergeSort1(arr);
        }
        break;
        case 3: //Mergesort take 2 @14ms to run.
        for(int i=0; i<numTrials; i++) {
        	Shuffle.shuffle(arr);
            mergeSort2(arr);
        }
        break;
        case 4: //Mergesort take 3 @9ms to run
        for(int i=0; i<numTrials; i++) {
        	Shuffle.shuffle(arr);
            mergeSort3(arr);
        }
        break;
     }
        timer.end();
        timer.scale(numTrials);

        System.out.println("Choice: " + choice + "\n" + timer);
    }

    public static void insertionSort(int[] arr) {
        insertionSort(arr,0,arr.length-1);
    }

    //Insertion sort
    public static void insertionSort(int[] arr, int p, int r) {
        int temp;
        for (int i = p+1; i <= r; i++) {
            temp=arr[i];
            int j=i-1;
            while(j>=p && temp<arr[j]) //shifting higher values in right side once it finds right position
            {
                arr[j+1]=arr[j];
                j-=1;
                arr[j+1]=temp;

            }
        }
    }

    //Mergesort Take 1
    public static void mergeSort1(int[] arr) {
        mergeSort1(arr,0,arr.length-1);
    }

    private static void mergeSort1(int[] arr, int p, int r) {
        if(p<r) {
            int q=(p+r)/2;
            mergeSort1(arr,p,q);
            mergeSort1(arr,q+1,r);
            merge1(arr,p,q,r);
        }
    }
    
    //Mergesort Take 2
    public static void mergeSort2(int[] arr) {
        mergeSort2(arr,0,arr.length-1);

    }

    public static void mergeSort2(int[] arr, int p, int r) {
        int q;
        int []arr2=new int[arr.length];
        if((r-p+1)<threshold)
        {
            insertionSort(arr,p,r); //below threshold call
        }
        else
        {
            q=(p+r)/2; //find mid point
            mergeSort1(arr,p,q);
            mergeSort1(arr,q+1,r);
            merge2(arr,arr2,p,q,r);
        }

    }
    
    //MergeSort Take 3
    public static void mergeSort3(int[] A) {
        int[] B = new int[A.length];
        System.arraycopy(A, 0, B, 0, A.length); //Copying A to B
        mergeSort3(A,B,0,A.length);
    }

    private static void mergeSort3(int[] A, int[] B, int left, int n) {
        if(n<threshold) insertionSort(A,left,left+n-1); //Call insertion sort if below threshold
        else {
            int ln = n/2; //divide in half
            mergeSort3(B,A,left,ln);
            mergeSort3(B,A,left+ln,n-ln);
            merge3(B,A,left,left+ln-1,left+n-1);
        }
    }

    //Merge for Take 1
    private static void merge1(int arr[], int p, int q, int r)
    {
        int[] L = new int [q-p+1];
        int[] R = new int [r-q];

        System.arraycopy(arr, p, L, 0, q-p+1);
        System.arraycopy(arr, q+1, R, 0, r-q);
        int i=0,j=0;
        for(int k=p;k<=r;k++) {
        	// copy L value if it is less than R  value to arr
            if(j>=(R.length)|| (i<L.length && L[i]<=R[j])) arr[k]=L[i++];
            else arr[k]=R[j++]; //else copy R value to arr
        }

    }

    //Merge for Take 2
    public static void merge2(int[] arr, int[] arr2, int p, int q, int r) {
        System.arraycopy(arr,p,arr2,p,r-p+1);
        int i=p,j=q+1;
        for(int k=p; k<=r; k++){
            if(j>r || (i<=q && (arr2[i]<=arr2[j])))
            {
                arr[k]=arr2[i++];
            }
            else{
                arr[k]=arr2[j++];
            }
        }
    }
    
    //Merge for Take 3
    private static void merge3(int[] B, int[] A, int p, int q, int r) {
        int i=p,j=q+1,k=p;
        while(i<=q && j<=r) {
            if(B[i]<=B[j]) A[k++]=B[i++];
            else A[k++]=B[j++];
        }
        while(i<=q) A[k++]=B[i++];
        while(j<=r) A[k++]=B[j++];
    }

    /** Timer class for roughly calculating running time of programs
     *  @author rbk
     *  Usage:  Timer timer = new Timer();
     *          timer.start();
     *          timer.end();
     *          System.out.println(timer);  // output statistics
     */

    public static class Timer {
        long startTime, endTime, elapsedTime, memAvailable, memUsed;
        boolean ready;

        public Timer() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public void start() {
            startTime = System.currentTimeMillis();
            ready = false;
        }

        public Timer end() {
            endTime = System.currentTimeMillis();
            elapsedTime = endTime-startTime;
            memAvailable = Runtime.getRuntime().totalMemory();
            memUsed = memAvailable - Runtime.getRuntime().freeMemory();
            ready = true;
            return this;
        }

        public long duration() { if(!ready) { end(); }  return elapsedTime; }

        public long memory()   { if(!ready) { end(); }  return memUsed; }

        public void scale(int num) { elapsedTime /= num; }

        public String toString() {
            if(!ready) { end(); }
            return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
        }
    }

    /** @author rbk : based on algorithm described in a book
     */


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
