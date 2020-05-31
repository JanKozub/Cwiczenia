package katas.smileFaces;

/*
Given an array (arr) as an argument complete the function countSmileys that should return the total number of smiling faces.
Rules for a smiling face:
-Each smiley face must contain a valid pair of eyes. Eyes can be marked as : or ;
-A smiley face can have a nose but it does not have to. Valid characters for a nose are - or ~
-Every smiling face must have a smiling mouth that should be marked with either ) or D.
No additional characters are allowed except for those mentioned.
Valid smiley face examples:
:) :D ;-D :~)
Invalid smiley faces:
;( :> :} :]

Example cases:

countSmileys([':)', ';(', ';}', ':-D']);       // should return 2;
countSmileys([';D', ':-(', ':-)', ';~)']);     // should return 3;
countSmileys([';]', ':[', ';*', ':$', ';-D']); // should return 1;

Note: In case of an empty array return 0. You will not be tested with invalid input (input will always be an array). Order of the face (eyes, nose, mouth) elements will always be the same
Happy coding!
 */

import java.util.Arrays;
import java.util.List;

public class SmileFaces {

    public static void main(String[] args) {
        List<String> list = Arrays.asList(":)", ";(", ";}", ":-D");

        System.out.println(countSmileys(list));
    }

    public static int countSmileys(List<String> arr) {
        int smiles = 0;
        for (String s : arr) {
            if (s.charAt(0) == ':' || s.charAt(0) == ';') {
                int lng = s.length();
                if (lng == 2) {
                    if (s.charAt(1) == ')' || s.charAt(1) == 'D') {
                        smiles++;
                    }
                } else {
                    if (lng == 3) {
                        if (s.charAt(1) == '~' || s.charAt(1) == '-') {
                            if (s.charAt(2) == ')' || s.charAt(2) == 'D') {
                                smiles++;
                            }
                        }
                    }
                }
            }
        }
        return smiles;
    }
}
