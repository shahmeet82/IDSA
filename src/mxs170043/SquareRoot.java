package mxs170043;

import java.util.Scanner;

public class SquareRoot {


public static long getSquareRoot(long num)
{
    long start=0;
    long end=num;
    long current=0;
    if(num==1)return 1;
    if (num<1)return 0;
    while(true){
        if ((current*current<=num)&& ((current+1)*(current+1)>num))
            break;
        else if ((current*current)<num)
        {
            long temp1=current;
            current=(end+start)/2;
            start=temp1;
        }
        else if ((current*current)>num)
        {
            long temp2=current;
            current=(end+start)/2;
            end=temp2;
        }

    }return current;
}
public static void main(String args[])
{
    Scanner in = new Scanner(System.in);
    System.out.println("enter an element to find SQRT");
    int x= in.nextInt();
   System.out.println(getSquareRoot(x));

}

};