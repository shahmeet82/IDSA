package mxs170043;

import java.security.Key;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class DoubleHashing<K> {
    static int size=0;
    static int capacity;
    K[] table;
    boolean[] deleted;
    int primesize;
    boolean flag;

    //constructor
    public DoubleHashing(int capacity){
        size=0;
        this.capacity=capacity;
        table= (K[]) new Object[this.capacity];
        deleted = new boolean[this.capacity];
        flag=false;
        for (int i=0;i<this.capacity;i++)
        {
            table[i]=null;
            deleted[i]=false;
        }
        primesize=getPrime();
    }

    //prime for hashcode 2
    public int getPrime() {
        for (int i = capacity - 1; i >= 1; i--) {
            int count = 0;
            for (int j = 2; j <= (int) Math.sqrt(i); j++)
                if (i % j == 0)
                    count++;
            if (count == 0)
                return i;
        }
        /* Return a prime number */
        return 3;
    }

    //find a key in hashtable
    public int find(K key)
    {
        int k=0,i;
        int indexH1 = hashKey1(key);
        int indexH2 = hashKey2(key);
        i=(indexH1+(k*indexH2))%capacity;
        if(i<0) i=(i+capacity)%capacity;

        //iterate until it gets a right position for a key
        while(i>=0 && i<capacity && table[i]!=null && !table[i].equals(key)) {
            k++;
            i=(indexH1+(k*indexH2))%capacity;
        }
        if(i<0) i=i+capacity;
        return i;
    }

    //add a key once find method returns right location for a key in the hashtable
    public boolean add(K key)
    {
        flag=true;
        if(size()==capacity) return false;
        int loc = find(key);
        if(table[loc] !=null && table[loc].equals(key)) return false;
        else {
            table[loc] = key;
            deleted[loc]=false;
            size++;
            return true;
        }
    }

    //returns current size of hashtable
    public int size()
    {
        return size;
    }

    //checks if size is equal to zero
    public boolean isEmpty()
    {
        return (size==0);
    }

    //find a hashkey 1
    public int hashKey1(K key){
        int hashval=Math.abs(key.hashCode())%capacity;
        if(hashval<0)
        {
            hashval+=capacity;

        }
        return hashval;
    }

    //find a hashkey 2
    public int hashKey2(K key)
    {
        int hashVal = Math.abs(key.hashCode( ))%capacity;
        if (hashVal < 0)
            hashVal += capacity;
        return primesize - hashVal % primesize;
    }

    //check whether key exists in table or not. If yes then returns true otherwise false.
    public boolean contains(K key) {
        flag=false;
        int loc = find(key);
        if(table[loc]!=null) return true;
        else return false;
    }

    //remove the key if exists otherwise return null
    public K remove(K key) {
        int loc=find(key);
        if(table[loc]!=null) {
            K result = table[loc];
            deleted[loc]=true;
            table[loc]=null;
            size--;
            return result;
        }else return null;
    }

    //print hashtable
    public void printHash() {
        for (int i = 0; i < capacity; i++) {
            if (table[i] != null) System.out.println(i + ": " + table[i]);
        }

    }
    public static void main(String[] args) {
        Random r = new Random(); // to generate random numbers

        DoubleHashing<Integer> obj = new DoubleHashing<>(100);

        for(int i=0;i<capacity/2;i++) {
            obj.add(r.nextInt(10000));
        }

        //a: add a key-if same key is tried to be entered method will return false
        //r: remove a key
        //c: check whether key exists or not
        //p: print a hashtable
        //first try to print a hashtable and then check for other operations
        Scanner sc = new Scanner(System.in);
        whileloop:
        while(sc.hasNext()) {
            char ch=sc.next().charAt(0);
            switch(ch) {
                case 'a':
                    System.out.println(obj.add(sc.nextInt()));
                    break;
                case 'r':
                    System.out.println(obj.remove(sc.nextInt()));
                    break;
                case 'c':
                    System.out.println(obj.contains(sc.nextInt()));
                    break;
                case 'p':
                    obj.printHash();
                    break;
                default:  // Exit loop
                    break whileloop;
            }
        }

    }
}
