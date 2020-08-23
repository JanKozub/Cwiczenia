package katas.observedPin;

import java.util.ArrayList;
import java.util.List;

/*
Alright, detective, one of our colleagues successfully observed our target person, Robby the robber. We followed him to a secret warehouse, where we assume to find all the stolen stuff. The door to this warehouse is secured by an electronic combination lock. Unfortunately our spy isn't sure about the PIN he saw, when Robby entered it.

The keypad has the following layout:

┌───┬───┬───┐a
│ 1 │ 2 │ 3 │
├───┼───┼───┤
│ 4 │ 5 │ 6 │
├───┼───┼───┤
│ 7 │ 8 │ 9 │
└───┼───┼───┘
    │ 0 │
    └───┘
He noted the PIN 1357, but he also said, it is possible that each of the digits he saw could actually be another adjacent digit (horizontally or vertically, but not diagonally). E.g. instead of the 1 it could also be the 2 or 4. And instead of the 5 it could also be the 2, 4, 6 or 8.

He also mentioned, he knows this kind of locks. You can enter an unlimited amount of wrong PINs, they never finally lock the system or sound the alarm. That's why we can try out all possible (*) variations.

* possible in sense of: the observed PIN itself and all variations considering the adjacent digits

Can you help us to find all those variations? It would be nice to have a function, that returns an array (or a list in Java and C#) of all variations for an observed PIN with a length of 1 to 8 digits. We could name the function getPINs (get_pins in python, GetPINs in C#). But please note that all PINs, the observed one and also the results, must be strings, because of potentially leading '0's. We already prepared some test cases for you.

Detective, we count on you!

For C# user: Do not use Mono. Mono is too slower when run your code.
 */

public class ObservedPin {

    public static void main(String[] args) {
        System.out.println(getPINs("11"));
    }

    public static List<String> getPINs(String observed) {
        ArrayList<String> result = new ArrayList<>();

        goThroughCombinations(observed, 0, result);

        return result;
    }

    public static void goThroughCombinations(String code, int position, ArrayList<String> codes) {
        if (!codes.contains(code)) {
            codes.add(code);
        }
        if (position < code.length()) {
            char currentNum = code.charAt(position);
            StringBuilder newCode = new StringBuilder(code);
           switch (currentNum) {
                case '1':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '2');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '4');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '2':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '1');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '3');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '5');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '3':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '2');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '6');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '4':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '1');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '5');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '7');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '5':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '2');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '4');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '6');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '8');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '6':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '3');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '5');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '9');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '7':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '4');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '8');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '8':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '7');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '5');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '9');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '0');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '9':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '8');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '6');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
                case '0':
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    newCode.deleteCharAt(position);
                    newCode.insert(position, '8');
                    goThroughCombinations(newCode.toString(), position + 1, codes);
                    break;
            }
        }
    }
}