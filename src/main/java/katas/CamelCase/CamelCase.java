package katas.CamelCase;

public class CamelCase {

    public static void main(String[] args) {
        System.out.println(toCamelCase("test"));
        System.out.println(toCamelCase("You_have_chosen_to_translate_this_kata_For_your_convenience_we_have_provided_the_existing_test_cases_used_for_the_language_that_you_have_already_completed_as_well_as_all_of_the_other_related_fields"));
    }

    static String toCamelCase(String s) {
        StringBuilder string = new StringBuilder(s);
        int i;
        do {
            i = string.indexOf("_");
            if (i != -1) {
                string.replace(i, i + 2, Character.toString(string.charAt(i + 1)).toUpperCase());
            }
        } while (i != -1);
        do {
            i = string.indexOf("-");
            if (i != -1) {
                string.replace(i, i + 2, Character.toString(string.charAt(i + 1)).toUpperCase());
            }
        } while (i != -1);
        return string.toString();
    }
}
