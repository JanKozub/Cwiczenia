package katas.createPhoneNumber;

public class CreatePhoneNumber {
    public static void main(String[] args) {
        System.out.println(createPhoneNumber(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0}));
    }

    public static String createPhoneNumber(int[] numbers) {
        StringBuilder string = new StringBuilder();
        string.append("(");
        for (int i = 0; i < 10; i++) {
            string.append(numbers[i]);
            if (i == 2) string.append(") ");
            if (i == 5) string.append("-");
        }

        return string.toString();
    }
}
