package DidUMean;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/*
I'm sure, you know Google's "Did you mean ...?", when you entered a search term and mistyped a word. In this kata we want to implement something similar.

You'll get an entered term (lowercase string) and an array of known words (also lowercase strings). Your task is to find out, which word from the
dictionary is most similar to the entered one. The similarity is described by the minimum number of letters you have to add, remove or replace in
order to get from the entered word to one of the dictionary. The lower the number of required changes, the higher the similarity between each two words.

Same words are obviously the most similar ones. A word that needs one letter to be changed is more similar to another word that needs 2 (or more) letters to be changed.
E.g. the mistyped term berr is more similar to beer (1 letter to be replaced) than to barrel (3 letters to be changed in total).

Extend the dictionary in a way, that it is able to return you the most similar word from the list of known words.

Code Examples:

Dictionary fruits = new Dictionary(new String[]{"cherry", "pineapple", "melon", "strawberry", "raspberry"});

fruits.findMostSimilar("strawbery"); // must return "strawberry"
fruits.findMostSimilar("berry"); // must return "cherry"

Dictionary things = new Dictionary(new String[]{"stars", "mars", "wars", "codec", "codewars"});
things.findMostSimilar("coddwars"); // must return "codewars"

Dictionary languages = new Dictionary(new String[]{"javascript", "java", "ruby", "php", "python", "coffeescript"});
languages.findMostSimilar("heaven"); // must return "java"
languages.findMostSimilar("javascript"); // must return "javascript" (same words are obviously the most similar ones)
I know, many of you would disagree that java is more similar to heaven than all the other ones, but in this kata it is ;)

Additional notes:

there is always exactly one possible correct solution
 */
public class Dictionary {

    public static String randomString(int max) {
        int len = (int) (1 + Math.random() * max);
        char[] c = new char[len];
        for (int i = 0; i < len; i++) {
            c[i] = (char) ('a' + (int) (Math.random() * 26));
        }
        return new String(c);
    }

    public static void main(String[] args) {
        //Dictionary fruits = new Dictionary(new String[]{"cherry", "pineapple", "melon", "strawberry", "raspberry"});
        Dictionary fruits = new Dictionary(IntStream.range(0, 50).mapToObj(i -> randomString(16)).toArray(String[]::new));

        System.out.println(fruits.findMostSimilar("rkacypviuburk")); // must return "strawberry"
/*
        System.out.println(fruits.findMostSimilar("berry")); // must return "cherry"

        Dictionary languages = new Dictionary(new String[]{"javascript", "java", "ruby", "php", "python", "coffeescript"});
        System.out.println(languages.findMostSimilar("heaven")); // must return "java"
        System.out.println(languages.findMostSimilar("javascript")); // must return "javascript" (same words are obviously the most similar ones)
*/
    }

    private final String[] words;

    public Dictionary(String[] words) {
        this.words = words;
    }

    public String findMostSimilar(String to) {

        String[] sortedWords = Arrays.stream(words)
                .sorted(Comparator.comparingInt(s -> (Math.abs(to.length() - s.length()))))
                .toArray(String[]::new);

        String output = "";
        int bestDiff = Integer.MAX_VALUE;
        for (String word : sortedWords) {
            if (Math.abs(to.length() - word.length()) > bestDiff)
                continue;

            int wordDiff = getNumberOfDiff(to, word, bestDiff);

            if (wordDiff < bestDiff) {
                output = word;
                bestDiff = wordDiff;

                if (wordDiff == 0) {
                    break;
                }
            }
        }

        return output;
    }

    private int getNumberOfDiff(String source, String target, int bestDiff) {
        return lev(source, 0, target, 0, bestDiff);
    }

    private int lev(String source, int sourceIdx, String target, int targetIdx, int maxDiffs) {

        if (source.length() == sourceIdx) {
            return target.length() - targetIdx;
        }

        if (target.length() == targetIdx) {
            return source.length() - sourceIdx;
        }

        if (source.charAt(sourceIdx) == target.charAt(targetIdx)) {
            return lev(source, sourceIdx + 1, target, targetIdx + 1, maxDiffs);
        } else {
            if (maxDiffs == 0) {
                return Integer.MAX_VALUE / 2;
            }

            int minCost = lev(source, sourceIdx + 1, target, targetIdx, maxDiffs - 1);
            if (minCost == 0)
                return 1;

            minCost = Math.min(minCost, lev(source, sourceIdx, target, targetIdx + 1, Math.min(minCost, maxDiffs - 1)));
            if (minCost == 0)
                return 1;

            minCost = Math.min(minCost, lev(source, sourceIdx + 1, target, targetIdx + 1, Math.min(minCost, maxDiffs - 1)));

            return 1 + minCost;
        }
    }
}
