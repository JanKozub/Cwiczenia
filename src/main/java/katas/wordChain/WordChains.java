package katas.wordChain;

import org.assertj.core.api.Java6AbstractBDDSoftAssertions;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.readAllBytes;

public class WordChains {

    private Map<Integer, List<String>> words;

    public WordChains(List<String> words) {
        this.words = words.stream()
            .collect(Collectors.groupingBy(s -> s.length()));
    }


    public static void main(String[] args) throws IOException {
        byte[] data = readAllBytes(Paths.get("/home/jpk/Projects/Cwiczenia/src/main/java/katas/wordlist.txt"));
        /*String s = new String(data);
        List<String> words = Arrays.asList(s.split("\n")).stream()
                .map(String::toLowerCase)
                .sorted()
                .collect(Collectors.toList());*/
        List<String> words = List.of("cat", "dog", "car", "put", "cab", "cot", "lot", "get", "pot", "put", "cot", "cog", "cal", "bal", "bag", "dag");

        // [cat, cot, cog, dog]
        // [cat, car, cab, cal, bal, bag, dag, dog]

        WordChains wordChains = new WordChains(words);

        wordChains.createWordChain("cat", "dog");
    }

    public void createWordChain(String startWord, String endWord) {
        ArrayList<String> chain = new ArrayList<>();
        chain.add(startWord);
        findChain(endWord, chain);
        System.out.println(chain.toString());
    }

    private boolean findChain(String finishWord, ArrayList<String> chain) {
        if (chain.isEmpty())
            return false;

        String prevWord = chain.get(chain.size() - 1);
        String[] words = findConnections(prevWord);
        for (String word : words) {
            System.out.println(chain + " -> " + word);
            if (chain.contains(word))
                continue;
            chain.add(word);

            if (word.equals(finishWord)) {
                System.out.println("Found " + chain.size() + ": " + chain);
                return true;
            } else {
                if (findChain(finishWord, chain)) {
                    return true;
                }
                chain.remove(chain.size() - 1);
            }
        }
        return false;
    }

    private String[] findConnections(String word) {
        return words.get(word.length()).stream()
                .filter(s -> oneDiff(s, word))
                .toArray(String[]::new);
    }

    private boolean oneDiff(String word1, String word2) {
        if (word1.length() != word2.length())
            return false;

        char[] w1a = word1.toCharArray();
        char[] w2a = word2.toCharArray();

        int counter = 0;
        for (int i = 0; i < w1a.length; i++) {
            if (w1a[i] != w2a[i]) {
                counter++;
            }
            if (counter > 1) return false;
        }
        return counter == 1;
    }

}
