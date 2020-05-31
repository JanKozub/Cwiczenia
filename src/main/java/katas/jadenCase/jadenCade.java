package katas.jadenCase;

public class jadenCade {
    public static void main(String[] args) {
        System.out.println(toJadenCase(""));
    }

    public static String toJadenCase(String phrase) {
        if (phrase != null && phrase.length() > 0) {
            String[] str = phrase.split("");
            StringBuilder output = new StringBuilder();

            str[0] = str[0].toUpperCase();
            for (int i = 0; i < str.length; i++) {
                if (str[i].equals(" ")) {
                    str[i + 1] = str[i + 1].toUpperCase();
                }
                output.append(str[i]);
            }

            return output.toString();
        } else return null;
    }
}
