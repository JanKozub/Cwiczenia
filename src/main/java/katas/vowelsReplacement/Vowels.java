package katas.vowelsReplacement;

public class Vowels {
    public static void main(String[] args) {
        System.out.println(disemvowel("This website is for losers LOL!"));
    }

    public static String disemvowel(String str) {
        String[] vowels = {"A", "E", "I", "O", "U"};

        for (String vowel : vowels) {
            str = str.replaceAll(vowel, "");
            str = str.replaceAll(vowel.toLowerCase(), "");
        }
        return str;
    }
}
// A, E, I, O, U
