package mxs170043;

import java.util.Scanner;

public class power {
    public static long getPower(long a,long b)
    {
        long temp=1;
        if(b==0)return 1;
        temp=getPower(a,b/2);
        if((b%2)==0)
        {
            return (temp*temp);

        }
        else{
            return (a*temp*temp);

        }
    }
    public static void main(String args[])
    {
        Scanner in = new Scanner(System.in);
        System.out.println("enter an element to find power");
        int x= in.nextInt();
        System.out.println("enter an power");
        int y= in.nextInt();
        System.out.println(getPower(x,y));
    }
}
