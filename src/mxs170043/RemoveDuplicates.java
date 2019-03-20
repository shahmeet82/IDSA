package mxs170043;



import java.util.*;
public class RemoveDuplicates<T> {

    public static<T> int distinctElements(T[] arr){
        HashSet<T> hSet = new HashSet<>();
//        for(T i:arr){
//            hSet.add(i);
//        }
//        int k=hSet.size(); // Number of distinct elements in the set
//
//        arr=hSet.toArray(arr);

        for (int i=0;i<arr.length;i++ )
        {

            if (hSet.contains(arr[i]))
            {
                hSet.remove(arr[i]);
                continue ;
            }
            else
                hSet.add(arr[i]);
        }
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
        int k=hSet.size();
        return k;
    }

    public static void main(String[] args){
        Integer[] arr={4,3,2,4,2,3,1,2,1,2,6,7,3,2,8,6,7,9};
        int count=distinctElements(arr);
        System.out.println("Total no. distinct elements are:"+ count);

    }

}

