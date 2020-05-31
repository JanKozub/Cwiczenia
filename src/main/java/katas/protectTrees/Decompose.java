package katas.protectTrees;

/*
My little sister came back home from school with the following task: given a squared sheet of paper she has to cut it in pieces which, when assembled, give squares the sides of which form an increasing sequence of numbers. At the beginning it was lot of fun but little by little we were tired of seeing the pile of torn paper. So we decided to write a program that could help us and protects trees.

Task
Given a positive integral number n, return a strictly increasing sequence (list/array/string depending on the language) of numbers, so that the sum of the squares is equal to n².

If there are multiple solutions (and there will be), return as far as possible the result with the largest possible values:

Examples
decompose(11) must return [1,2,4,10]. Note that there are actually two ways to decompose 11², 11² = 121 = 1 + 4 + 16 + 100 = 1² + 2² + 4² + 10² but don't return [2,6,9], since 9 is smaller than 10.

For decompose(50) don't return [1, 1, 4, 9, 49] but [1, 3, 5, 8, 49] since [1, 1, 4, 9, 49] doesn't form a strictly increasing sequence.

Note
Neither [n] nor [1,1,1,…,1] are valid solutions. If no valid solution exists, return nil, null, Nothing, None (depending on the language) or "[]" (C) ,{} (C++), [] (Swift, Go).

The function "decompose" will take a positive integer n and return the decomposition of N = n² as:

[x1 ... xk] or
"x1 ... xk" or
Just [x1 ... xk] or
Some [x1 ... xk] or
{x1 ... xk} or
"[x1,x2, ... ,xk]"
depending on the language (see "Sample tests")

Note for Bash
decompose 50 returns "1,3,5,8,49"
decompose 4  returns "Nothing"

Hint
Very often xk will be n-1.

 */

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

public class Decompose {

    public static void main(String[] args) throws IOException {
        System.out.println(new Decompose().decompose(12));

//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//        String s = reader.readLine();
//
//        // 2 + 6 * 3 -> ['2', '+', '6', '*', ['1' + '5'], '3' ]
//        System.out.println("Result = " + evaluate(s));
    }

    public static double evaluate(String expression) {
        return Double.NaN;
    }

    // X -> X^2
    // X^2 - (X-1)^2 = R ?
    // X = 50 -> X^2 = 2500
    // 49^2 = 2401  -> 2500-2401 = 99
    // 10 = 100
    // 9 = 81 -> 99-81 = 18
    // [49, 9, ...]
    //

    // byte = 2^8 = 1 2 4 6 14 32 64 128 256
    // int = 2^32
    // long = 2^64
    // long / int = 2^64 / 2^32 = 2^32


    public String decompose(long n) {
        String result = decompose(n - 1, n * n);
        if (result == null)
            result = "Nothing";
        return result;
    }

    /**
     * Decomposes 'value' into sum of squares using values not greater than N
     *
     * @param n     Maximum value used in decomposition
     * @param value Value to decompose
     * @return decomposition string
     */
    private String decompose(long n, long value) {
        while(n > 0) {
            long diff = value - (n * n);
            if (diff == 0) {
                return "" + n;
            }

            long nextPossibleFactor = Math.min(n - 1, (long) Math.sqrt(diff));
            String result = decompose(nextPossibleFactor, diff);
            if (result != null) {
                return result + " " + n;
            }
            n--;
        }
        return null;
    }

    private String decompose2(long n, long value) {
        if (n == 0)
            return null;

        long diff = value - (n * n);
        if (diff == 0) {
            return "" + n;
        }

        long sqrt = Math.min(n - 1, (long) Math.sqrt(diff));
        String result = decompose(sqrt, diff);
        if (result == null) {
            return decompose(n - 1, value);
        } else {
            return result + " " + n;
        }
    }


}
