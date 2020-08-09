package katas.xo;

/*
    Check to see if a string has the same amount of 'x's and 'o's.
    The method must return a boolean and be case insensitive. The string can contain any char.

    Examples input/output:

    XO("ooxx") => true
    XO("xooxx") => false
    XO("ooxXm") => true
    XO("zpzpzpp") => true // when no 'x' and 'o' is present should return true
    XO("zzoo") => false
 */

public class XO {

    public static void main(String[] args) {
        System.out.println(getXO("zzoo"));
    }

    public static boolean getXO(String str) {
        String slowo = "jfjfjf";

        char arr[] = str.toCharArray();
        int numO = 0;
        int numX = 0;


        for (char c : arr) {
            char lc = Character.toLowerCase(c);

            if (lc == 'o') {
                numO++;
            } else {
                if (lc == 'x') {
                    numX++;
                }
            }
        }
        return numO == numX;
    }
}