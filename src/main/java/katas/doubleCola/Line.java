package katas.doubleCola;

/*
Sheldon, Leonard, Penny, Rajesh and Howard are in the queue for a "Double Cola" drink vending machine; there are no other people in the queue. The first one in the queue (Sheldon) buys a can, drinks it and doubles! The resulting two Sheldons go to the end of the queue. Then the next in the queue (Leonard) buys a can, drinks it and gets to the end of the queue as two Leonards, and so on.

For example, Penny drinks the third can of cola and the queue will look like this:

Rajesh, Howard, Sheldon, Sheldon, Leonard, Leonard, Penny, Penny
Write a program that will return the name of the person who will drink the n-th cola.

Input
The input data consist of an array which contains at least 1 name, and single integer n which may go as high as the biggest number your language of choice supports (if there's such limit, of course).

Output / Examples
Return the single line — the name of the person who drinks the n-th can of cola. The cans are numbered starting from 1.

string[] names = new string[] { "Sheldon", "Leonard", "Penny", "Rajesh", "Howard" };
Line.WhoIsNext(names, 1) == "Sheldon"
Line.WhoIsNext(names, 52) == "Penny"
Line.WhoIsNext(names, 7230702951) == "Leonard"
 */

public class Line {


    public static void main(String[] args) {
        String[] names = new String[]{"Sheldon", "Leonard", "Penny", "Rajesh", "Howard", "Marek", "Jasiek", "Piotrek", "Basia", "a", "b", "c", "d"};
        //System.out.println(WhoIsNext_3(names, 52));

        System.out.println("Version 1:"); // O(N+M) N - names.length, M - count
        measure(() -> WhoIsNext_1(names, Long.MAX_VALUE / 10));
        System.out.println("Version 2:"); // O(M) M - count | O(0.0001 * M)
        measure(() -> WhoIsNext_2(names, Integer.MAX_VALUE / 2));
        System.out.println("Version 3:"); // O(1)           | O(4)
        measure(() -> WhoIsNext_3(names, Long.MAX_VALUE / 10));
    }

    /**
     * 1 -> 5   5   1
     * 2 -> 10  15  2
     * 3 -> 20  35  4
     * 4 -> 40  75  8
     *
     * 52 -> 4 kolejka -> 52 - 35 -> 17 / 8 = 2.125 [2.125] -> 2 Penny
     * 60 -> 3 kolejka -> 60 - 35 -> 25
     */


    public static void measure(Runnable r) {
        int k = 100000;
        long start = System.currentTimeMillis();
        for(int i = 0; i < k; i++) {
            r.run();
        }
        long end = System.currentTimeMillis();
        System.out.println("Took " + ((end - start) / (k * 1000.0)) + "ms");
    }

    public static String WhoIsNext_3(String[] names, long n) {
        // T = 5 + 10 + 20 + ...
        // T = 5 ( 1 + 2 + 4 + ... )    ciąg geometryczny: a1=5 q=2
        // T = SUM_N = a1 * (1-q^N)/(1-q)
        // n = a1 * (1-2^N)/(1-2)
        // -1 * n = a1 * (1-2^N)
        // (-1 * n) / a1 = 1-2^N
        // 2^N = (1 * n) / a1) + 1
        // log_2(2^N) = log_2((1 * n) / a1) + 1)
        // N = log_2((1 * n) / a1) + 1)
        // log_a(b) = log_c(b) / log_c(a)
        // N = log_10((1 * n) / a1) + 1) / log_10(2)
        double a1 = names.length;
        double N = Math.log10(((1 * n) / a1) + 1) / Math.log10(2);
        int repetition = (int)N;
        int total = (int)(a1 * (1 - Math.pow(2, repetition)) / (1 - 2));
        int personsInLine = (int)Math.pow(2, repetition);

        int person = (int)(n - total);

        if(repetition == 0) {
            return names[person-1];
        } else {
            int result = person / personsInLine;
            return names[result];
        }
    }

    public static String WhoIsNext_2(String[] names, long n) {
        long total = 0;
        int i = 1;
        while (total + i * names.length < n) {
            total = total + names.length * i;
            i *= 2;
        }
        int person = (int)(n - total);

        if(i == 1) {
            return names[person-1];
        } else {
            int result = person / i;
            return names[result];
        }
    }


    public static String WhoIsNext_1(String[] names, long n) {
        long[] counters = new long[names.length];
        long total = 0;
        int i = 0;
        while (total < n) {

            long currentCounter = 0;
            if (total < names.length) {
                currentCounter++;
            } else {
                currentCounter = (counters[i] + 1) * 2;
            }

            total = total + currentCounter;
            counters[i] = counters[i] + currentCounter;
            if (i == names.length - 1) {
                i = 0;
            } else {
                i++;
            }
        }
        return names[i - 1];
    }
}
