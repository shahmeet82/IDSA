package mxs170043;

public class MergeSort<T> {
    public static<T extends Comparable<T>> int  mSort(T A[]) {
        return mSort(A,0,A.length-1);
    }
    public static<T extends Comparable<T>> int mSort(T A[], int p, int r){
        int threshold=10;
        int q,out1,out2;
        T B[] = null;
        if((r-p+1)<threshold)
        {
            insertionSort(A,p,r);
            return 0;
        }
        else
        {
            q=(p+r)/2;
            out1=mSort(A,p,q);
            out2=mSort(A,q+1,r);
        }
        if(out1==0)
        {
            merge(A,B,p,q,r);
            return 1;
        }
        else{
            merge(B,A,p,q,r);
            return 0;
        }

    }
    public static<T extends Comparable<T>> void merge(T A[], T B[], int p, int q, int r){
        System.arraycopy(A,p,B,q,r);
        int i=p, j=q+1;
        for(int k=p; k<r; k++){
            if(j>r || (i<=q && (B[i].compareTo(B[j]))<=0)){
                B[k++]=A[i];
            }
            else{
                B[k++]=A[j];
            }
        }
    }

    public static<T extends Comparable<T>> void insertionSort(T A[], int p, int r){
        T temp;
        for (int i = p; i < r; i++) {
            temp=A[i];
            int j=i-1;
            while(j>=p && A[j].compareTo(temp)<0)
            {
                A[j+1]=A[j];
                j-=1;
                A[j+1]=temp;
            }
        }

    }
}
