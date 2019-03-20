package mxs170043;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Num implements Comparable<Num> {

    static long defaultBase = 10; // Change as needed
    long base = 10; // Change as needed
    long[] arr; // array to store arbitrarily large integers
    boolean isNegative; // boolean flag to represent negative numbers
    int len; // actual number of elements of array that are used; number is stored in
    // arr[0..len-1]

    public Num(String s) {
        len = s.length();
        if (s.charAt(0) == '-') {
            isNegative = true;
            s = s.substring(1, s.length());
            len = s.length();
        }

        arr = new long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = Long.parseLong(s.charAt(len - i - 1) + "");
        }
    }

    public Num(long x) {
        String s = String.valueOf(x);
        len = s.length();

        if (s.charAt(0) == '-') {
            isNegative = true;
            s = s.substring(1, s.length());
            len = s.length();
        }
        arr = new long[len];
        for (int i = 0; i < len; i++) {
            arr[i] = Long.parseLong(s.charAt(len - i - 1) + "");
        }
    }

    public Num() {
    }

    public void printNum() {
        for (int i = 0; i < len; i++)
            System.out.print(arr[i]);
    }

    public static Num add(Num a, Num b) {
        Num added = new Num(0);
        if ((!a.isNegative && !b.isNegative) || (a.isNegative && b.isNegative)) {
            int minLen = 0;
            if (a.compareTo(b) == 1) {
                minLen = b.len;
            } else {
                minLen = a.len;
            }
            StringBuilder sb = new StringBuilder();
            long carry = 0;
            for (int i = 0; i < minLen; i++) {
                sb.append(((a.arr[i] + b.arr[i] + carry) % a.base));
                carry = (a.arr[i] + b.arr[i] + carry) / a.base;
            }
            for (int i = minLen; i < a.len; i++) {
                sb.append((a.arr[i] + carry) % a.base);
                carry = (a.arr[i] + carry) / a.base;
            }
            for (int i = minLen; i < b.len; i++) {
                sb.append((b.arr[i] + carry) % a.base);
                carry = (b.arr[i] + carry) / a.base;
            }
            if (carry != 0)
                sb.append(carry);
            if (!a.isNegative && !b.isNegative) {
                added = new Num(sb.reverse().toString());
            } else if (a.isNegative && b.isNegative) {
                added = new Num(sb.reverse().toString());
                added.isNegative = true;
            }
        } else if (a.isNegative && !b.isNegative) {
            Num no1 = a;
            no1.isNegative = false;
            if (a.compareTo(b) >= 0) {
                added = subtract(no1, b);
                added.isNegative = true;
            } else {
                added = subtract(no1, b);
                added.isNegative = false;
            }
        } else if (!a.isNegative && b.isNegative) {
            Num no1 = b;
            no1.isNegative = false;
            if (a.compareTo(b) >= 0)
                added = subtract(a, no1);
            else {
                added = subtract(a, no1);
                added.isNegative = true;
            }
        }
        return added;
    }

    public static Num subtract(Num a, Num b) {
        Num subtracted = new Num(0);
        if ((!a.isNegative && !b.isNegative) || (a.isNegative && b.isNegative)) {
            Num num1, num2;
            if (a.compareTo(b) == 1 || a.compareTo(b) == 0) {
                num1 = a;
                num2 = b;
            } else {
                num1 = b;
                num2 = a;
            }
            String sub = "";
            for (int i = 0; i < num2.len; i++) {
                if (num1.arr[i] < num2.arr[i]) {
                    num1.arr[i + 1]--;
                    num1.arr[i] += num1.base;
                }
                sub += (num1.arr[i] - num2.arr[i]) + "";
            }
            for (int i = num2.len; i < num1.len; i++) {
                if (num1.arr[i] < 0) {
                    num1.arr[i] += num1.base;
                    num1.arr[i + 1]--;
                }
                sub += num1.arr[i];
            }
            StringBuilder sb = new StringBuilder(sub);

            if (a.isNegative && b.isNegative) {
                if (a.compareTo(b) >= 0) {
                    subtracted = new Num(sb.reverse().toString());
                    subtracted.isNegative = true;
                } else {
                    subtracted = new Num(sb.reverse().toString());
                }
            } else if (!a.isNegative && !b.isNegative) {
                if (a.compareTo(b) >= 0) {
                    subtracted = new Num(sb.reverse().toString());
                } else {
                    subtracted = new Num(sb.reverse().toString());
                    subtracted.isNegative = true;
                }
            }
        } else if (a.isNegative && !b.isNegative) {
            Num no1 = a;
            no1.isNegative = false;

            subtracted = add(no1, b);
            subtracted.isNegative = true;

        } else if (!a.isNegative && b.isNegative) {
            Num no1 = b;
            no1.isNegative = false;
            subtracted = add(a, no1);
            subtracted.isNegative = false;
        }
        return subtracted;
    }

    public static Num product(Num a, Num b) {
        long arr1[] = new long[a.len];
        long arr2[] = new long[b.len];

        for (int i = 0; i < a.len; i++)
            arr1[i] = a.arr[i];
        for (int i = 0; i < b.len; i++)
            arr2[i] = b.arr[i];

        long mul[] = new long[a.len + b.len];

        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                mul[i + j] += arr1[i] * arr2[j];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mul.length; i++) {
            long mod = mul[i] % a.base;
            long carry = mul[i] / a.base;
            if ((i + 1) < mul.length) {
                mul[i + 1] += carry;
            }
            sb.insert(0, mod);
        }

        while (sb.charAt(0) == '0' && sb.length() > 1)
            sb.deleteCharAt(0);

        Num finalProduct = new Num(sb.toString());

        if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative))
            finalProduct.isNegative = false;
        else
            finalProduct.isNegative = true;

        return finalProduct;
    }

    // Use divide and conquer
    public static Num power(Num a, long n) {
//		Num temp = new Num(1);
        // base case
        if (n == 0) {
            return new Num("1");
        }
        Num temp = power(a, n / 2);
        if (n % 2 == 0) {
            return (product(temp, temp));
        } else {
            Num op = product(temp, temp);
            return (product(a, op));
        }
    }

    // Use binary search to calculate a/b
    public static Num divide(Num a, Num b) {
        Num start = new Num("0");
        Num end = a;
        Num current_val = new Num("0");
        if (b.compareTo(new Num(0)) == 0) {
            return null;
        }
        if (a.compareTo(b) < 0) {
            return new Num(0);
        }
        if (a.compareTo(new Num(1)) == 0) {
            return new Num(1);
        }

        while (true) {
            if (product(current_val, b).compareTo(a) <= 0
                    && product(add(current_val, new Num(1)), b).compareTo(a) > 0) {
                break;
            } else if (product(current_val, b).compareTo(a) < 0) {
                Num temp1 = current_val;
                current_val = add(start, end).by2();
                start = temp1;

            } else if (product(current_val, b).compareTo(a) > 0) {
                Num temp2 = current_val;
                current_val = add(start, end).by2();
                end = temp2;
            }
        }

        Num finalDiv = current_val;

        if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative)) {
            finalDiv.isNegative = false;
        } else {
            finalDiv.isNegative = true;
        }
        return finalDiv;
    }

    // return a%b
    public static Num mod(Num a, Num b) {
        return subtract(a, product(divide(a, b), b));
    }

    // Use binary search
    public static Num squareRoot(Num a) {
        Num start = new Num("0");
        Num end = a;
        Num current_val = new Num("0");
        if (a.isNegative == true) {
            return null;
        }
        if (a.compareTo(new Num(1)) == 0) {
            return new Num(1);
        }
        while (true) {
            if (power(current_val, 2).compareTo(a) <= 0 && power(add(current_val, new Num(1)), 2).compareTo(a) > 0) {
                break;
            } else if (power(current_val, 2).compareTo(a) < 0) {
                Num temp1 = current_val;
                current_val = add(start, end).by2();
                start = temp1;
            } else if (power(current_val, 2).compareTo(a) > 0) {
                Num temp2 = current_val;
                current_val = add(start, end).by2();
                end = temp2;
            }
        }
        return current_val;
    }

    // Utility functions
    // compare "this" to "other": return +1 if this is greater, 0 if equal, -1
    // otherwise
    public int compareTo(Num other) {
        if (this.len > other.len) {
            return 1;
        } else if (this.len < other.len) {
            return -1;
        } else {
            // if length is same, we have to check 3 cases
            for (int i = this.len - 1; i >= 0; i--) {
                if (this.arr[i] > other.arr[i]) {
                    return 1;
                } else if (this.arr[i] < other.arr[i]) {
                    return -1;
                } else {
                    continue;
                }
            }
        }
        return 0;
    }

    // Output using the format "base: elements of list ..."
    // For example, if base=100, and the number stored corresponds to 10965,
    // then the output is "100: 65 9 1"
    public void printList() {
    }

    // Return number to a string in base 10
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isNegative) {
            sb.append('-');
        }
        for (int i = len - 1; i >= 0; i--) {
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    public long base() {
        return base;
    }

    // Return number equal to "this" number, in base=newBase
    public Num convertBase(int newBase) {
        Num newNum = new Num();
        newNum.base = newBase;
        Num numberInBase10 = convertBaseTo10(this);
        return new Num(5);
    }

    public static Num convertBaseTo10(Num num) {
        // check if base greater than 10, then divide
        // else power
        Num[] array = new Num[10000];
        int counter = 0;
        for (int i = 0; i < num.arr.length; i++) {
            Num powerNum = power(new Num(num.base), i);
            System.out.println(powerNum);
            array[i] = product(new Num(num.arr[i]), powerNum);
            counter++;
        }
        // opNum contains more 0
        Num opNum = new Num(String.format("%0" + num.len + "d", 0));
        for (int i = 0; i < counter; i++) {
            opNum = add(opNum, array[i]);
        }
        String opString = opNum.toString();
        System.out.println("Without removing 0's: " + opString);
        while (opString.length() > 1 && opString.charAt(0) == '0')
            opString = opString.substring(1);
        return new Num(opString);
    }

    public static Num convertFromBase10(Num num, int newBase) {

        return null;
    }

    // Divide by 2, for using in binary search
    public Num by2() {
        StringBuilder sb = new StringBuilder();
        long carry = 0;
        for (int i = this.len - 1; i >= 0; i--) {
            sb.append((carry * this.base + arr[i]) / 2);
            carry = arr[i] % 2;

        }
        String result = sb.toString();
        if (result.charAt(0) == '0' && result.length() > 1) {
            result = result.substring(1, result.length());
        }
        return new Num(result);
    }

    // Evaluate an expression in postfix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "0", or
    // a number: [1-9][0-9]*. There is no unary minus operator.
    public static Num evaluatePostfix(String[] expr) {

        Stack<String> stack = new Stack<>();

        // Scan all characters one by one
        for (int i = 0; i < expr.length; i++) {
            String symbol = expr[i];
            if (symbol != "/" && symbol != "*" && symbol != "^" && symbol != "+" && symbol != "-" && symbol != "%") {
                stack.push(expr[i]);
            } else {
                String no2 = stack.pop();
                String no1 = stack.pop();
                System.out.println("No1: " + new Num(no1) + "\nSign: " + new Num(no1).isNegative);
                System.out.println("Symbol" + symbol);
                System.out.println("No2: " + new Num(no2) + "\nSign: " + new Num(no2).isNegative);
                switch (symbol) {
                    case "*":
                        stack.push(product(new Num(no2), new Num(no1)).toString());
                        break;
                    case "/":
                        stack.push(divide(new Num(no1), new Num(no2)).toString());
                        break;
                    case "^":
                        stack.push(power(new Num(no1), Long.parseLong(no2)).toString());
                        break;
                    case "+":
                        stack.push(add(new Num(no2), new Num(no1)).toString());
                        break;
                    case "-":
                        stack.push(subtract(new Num(no1), new Num(no2)).toString());
                        break;
                    case "%":
                        stack.push(mod(new Num(no1), new Num(no2)).toString());
                        break;
                }
                System.out.println("Output: " + stack.peek());
                System.out.println("\n\n\n\n");
            }
        }
        return new Num(stack.pop());
    }

    // Evaluate an expression in infix and return resulting number
    // Each string is one of: "*", "+", "-", "/", "%", "^", "(", ")", "0", or
    // a number: [1-9][0-9]*. There is no unary minus operator.
    public static Num evaluateInfix(String[] expr) {
        Stack<String> stack = new Stack<>();
        String[] queue = new String[expr.length];
        HashMap<String, Integer> hashmap = new HashMap<>();
        hashmap.put("^", 1);
        hashmap.put("*", 2);
        hashmap.put("/", 2);
        hashmap.put("%", 2);
        hashmap.put("+", 3);
        hashmap.put("-", 3);
        hashmap.put("(", 4);
        int i = 0;
        for (String exp : expr) {
            if (exp.matches("-?\\d+"))
                queue[i++] = exp;
            else {
                if (stack.isEmpty() || exp.equals("("))
                    stack.push(exp);
                else if (exp.equals(")")) {
                    String top = stack.pop();
                    while (!top.equals("(")) {
                        queue[i++] = top;
                        if (stack.isEmpty())
                            return null;
                        top = stack.pop();
                    }
                } else {
                    while (isHigherPrecedence(exp, stack.peek(), hashmap)) {
                        String top = stack.pop();
                        queue[i++] = top;
                        if (stack.isEmpty())
                            break;
                    }
                    stack.push(exp);
                }
            }
        }
        while (!stack.isEmpty())
            queue[i++] = stack.pop();
        ArrayList<String> op = new ArrayList();
        for (int ii = 0; ii < queue.length; ii++) {
            if (queue[ii] != null)
                op.add(queue[ii]);
            else {
                String arr[] = new String[op.size()];
                int j = 0;
                while (!op.isEmpty()) {
                    arr[j] = op.remove(0);
                    j++;
                }
                return evaluatePostfix(arr);
            }
        }
        System.out.println(queue.length);
        return evaluatePostfix(queue);
    }

    private static boolean isHigherPrecedence(String exp, String peek, HashMap<String, Integer> hashmap) {
        System.out.println(exp + " => " + hashmap.get(exp) + " ---- " + peek + " => " + hashmap.get(peek));
        return (hashmap.get(exp) > hashmap.get(peek));
    }

    public static void main(String[] args) {
        Num x = new Num("13");
        Num y = new Num(12);
        String[] expr = { "13", "14", "-" };
        Num obj = evaluatePostfix(expr);
        System.out.println(obj);
    }
}
