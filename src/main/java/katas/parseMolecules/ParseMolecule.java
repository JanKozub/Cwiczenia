package katas.parseMolecules;

import javax.swing.*;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

import static java.lang.Character.*;

/*
For a given chemical formula represented by a string, count the number of atoms of each element contained in the molecule and return an object (associative array in PHP, Dictionary<string, int> in C#, Map<String,Integer> in Java).

For example:

String water = "H2O";
parseMolecule.getAtoms(water); // return [H: 2, O: 1]

String magnesiumHydroxide = "Mg(OH)2";
parseMolecule.getAtoms(magnesiumHydroxide); // return ["Mg": 1, "O": 2, "H": 2]

String fremySalt = "K4[ON(SO3)2]2";
parseMolecule.getAtoms(fremySalt); // return ["K": 4, "O": 14, "N": 2, "S": 4]

parseMolecule.getAtoms("pie"); // throw an IllegalArgumentException
As you can see, some formulas have brackets in them. The index outside the brackets tells you that you have to multiply count of each atom inside the bracket on this index. For example, in Fe(NO3)2 you have one iron atom, two nitrogen atoms and six oxygen atoms.

Note that brackets may be round, square or curly and can also be nested. Index after the braces is optional.


[H2, O]

K4[ON(SO3)2]2 -> {
    k,  -> P
    4,  -> N
    [,  -> O
    O,  -> P
    N,  -> P
    (,  -> O
    S,  -> P
    O,  -> P
    3,  -> N
    ),  -> C
    2,  -> N
    ],  -> C
    2   -> N
}
 */

class ParseMolecule {
    public static void main(String[] args) {
//        System.out.println(Arrays.toString(splitToTokens("H2O")));
//        System.out.println(Arrays.toString(splitToTokens("Mg(OH)2")));
//        System.out.println(Arrays.toString(splitToTokens("K4[ON(SO3)2]2")));
        System.out.println(Arrays.toString(splitToTokens("H2a")));


//        System.out.println(getAtoms("H2O"));
//        System.out.println(getAtoms("2Mg(S4OH)2"));
//        System.out.println(getAtoms("K4[ON(SO3)2]2"));
//        System.out.println(getAtoms("4[4ON(SO3)2]2"));
        //System.out.println(getAtoms("K4ON[SO3]2)2"));
       // System.out.println(getAtoms("pie"));
    }

    public static Map<String, Integer> getAtoms(String formula) {
        String[] tokens = splitToTokens(formula);

        int last = tokens.length - 1;
        while (tokens[last] == null)
            last--;

        HashMap<String, Integer> counts = new HashMap<>();
        countMolecules(last, '0', tokens, counts);
        return counts;
    }

    private static int countMolecules(int lastElement, char lastBracket, String[] formula, HashMap<String, Integer> counts) {
        int multiplier = 1;

        int i = lastElement;
        boolean lastWasDigit = false;
        while (i >= 0) {
            char element = formula[i].charAt(0);
            if (isDigit(element)) {
                multiplier = Integer.parseInt(formula[i]);
                i--;
                lastWasDigit = true;
            } else if (isAlphabetic(element)) {
                counts.put(formula[i], counts.getOrDefault(formula[i], 0) + multiplier);
                multiplier = 1;
                i--;
                lastWasDigit = false;
            } else if (element == lastBracket) {
                if(lastWasDigit) {
                    throw new IllegalArgumentException("Invalid syntax");
                }
                return i - 1;
            } else if (isOpeningBracket(element)) {
                throw new IllegalArgumentException("Invalid bracket");
            } else if (isBracket(element)) {
                HashMap<String, Integer> innerCounts = new HashMap<>();
                int nextElement = countMolecules(i - 1, getComplementaryBracket(element), formula, innerCounts);
                mergeCounts(counts, multiplier, innerCounts);
                multiplier = 1;
                i = nextElement;
                lastWasDigit = false;
            }
        }

        if (lastBracket == '0') {
            if(lastWasDigit) {
                throw new IllegalArgumentException("Invalid syntax");
            }
            return -1;
        } else
            throw new IllegalArgumentException("Missing bracket");
    }

    private static void mergeCounts(HashMap<String, Integer> counts, int multiplier, HashMap<String, Integer> innerCounts) {
        for (Map.Entry<String, Integer> e : innerCounts.entrySet()) {
            counts.put(e.getKey(), counts.getOrDefault(e.getKey(), 0) + (e.getValue() * multiplier));
        }
    }

    private static String[] splitToTokens(String formula) {
        String[] output = new String[formula.length()];

        int j = 0;
        for (int i = 0; i < output.length; i++) {
            char c = formula.charAt(i);
            if(isDigit(c) || isBracket(c) || isUpperCase(c)) {
                j = addOrAppend(output, j, c);
            } else if(isLowerCase(c)) {
                //if((output[j] == null) || ((j > 0) && isDigit(output[j-1].charAt(0)) || isBracket(output[j-1].charAt(0))))
                if(output[j] == null)
                    throw new IllegalArgumentException("Invalid syntax");
                j = addOrAppend(output, j, c);
            } else
                throw new IllegalArgumentException("Illegal character: " + c);
        }
        return output;
    }

    private static int addOrAppend(String[] output, int j, char c) {
        if(output[j] == null) {
            output[j] = "" + c;
        } else if(isDigit(c) && isDigit(output[j].charAt(0))) {
            output[j] = output[j] + c;
        } else if(isAlphabetic(c) && isAlphabetic(output[j].charAt(0))) {
            if(isUpperCase(c)) {
                j++;
            }
            output[j] = ((output[j] == null) ? "" : output[j]) + c;
        } else {
            j++;
            output[j] = "" + c;
        }
        return j;
    }

    private static boolean isOpeningBracket(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private static boolean isBracket(char c) {
        return c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}';
    }

    private static char getComplementaryBracket(char bracket) {
        switch (bracket) {
            case ']':
                return '[';
            case '[':
                return ']';
            case ')':
                return '(';
            case '(':
                return ')';
            case '}':
                return '{';
            case '{':
                return '}';
            default:
                throw new IllegalArgumentException("Invalid bracket");
        }
    }
}
