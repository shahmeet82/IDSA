/**
 *
 * @author Shreeya Degaonkar(sxd174830), Meet Shah (mxs170043)
 * Implementation of the select algorithm to find k largest elements using priority queue
 */


package mxs170043;

import java.util.*;
import java.util.stream.Stream;

public class SelectAlgorithm {

    /**
     * select algorithm finds k largest elements
     * @param A
     * @param k
     * @return Queue<Integers> that stores the k largest elements
     */
    public static Queue<Integer> Select(Stream<Integer> A, int k) {
        Iterator<Integer> it = A.iterator();
        Queue<Integer> q = new PriorityQueue<>();  //New a min heap
        for (int i = 1; i <= k; i++) {    //Initial min-heap
            if (it.hasNext()) {
                q.add(it.next());
            }else
                return q;
        }
        while (it.hasNext()) {
            int x = it.next();
            if (q.peek() < x) {
                q.add(x);
                q.remove();
            }
        }
        return q;
    }


    public static void main(String[] args)
    {

        int k;
        System.out.println("Input the number of elements  of the array ");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 0) {
            System.out.println("Input a valid number of n:");
            n = scanner.nextInt();
        }

        scanner.close();
        ArrayList<Integer> a = new ArrayList<>(n);
        for (int i=0;i<=n;i++)
        {
            a.add(i);
        }
        k=n/2;
        Timer timer = new Timer();
        Queue<Integer> q = Select(a.stream(), k);
        timer.end();
        System.out.println(timer);
    }
}
