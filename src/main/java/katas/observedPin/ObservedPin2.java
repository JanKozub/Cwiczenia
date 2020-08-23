package katas.observedPin;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class ObservedPin2 {

    private static final int[][] combinations = {
            {8},
            {2,4},
            {1,3,5},
            {2,6},
            {1,5,7},
            {2,4,6,8},
            {3,5,9},
            {4,8},
            {5,7,9,0},
            {6,8}
    };

    public static void main(String[] args) {
        System.out.println(getPINs("11"));
    }


    public static List<String> getPINs(String observed) {
        Set<String> result = new HashSet<>();
        goThroughCombinations(observed.toCharArray(), 0, result);
        return new ArrayList<>(result);
    }

    private static void goThroughCombinations(char[] code, int position, @NotNull Collection<String> codes) {
        String codeStr = new String(code);
        if (!codes.contains(codeStr)) {
            codes.add(codeStr);
        }

        if (position < code.length) {
            char currentNum = code[position];
            goThroughCombinations(code, position + 1, codes);

            for (int num : combinations[currentNum - '0']) {
                code[position] = (char)('0' + num);
                goThroughCombinations(code, position + 1, codes);
            }

            code[position] = currentNum;
        }
    }
}