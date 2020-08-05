package katas.sillyAdditon;

import org.apache.commons.lang3.ArrayUtils;

public class SillyAdditon {

    public static void main(String[] args) {
        System.out.println(add(2, 11)); // 1103
    }

    public static int add(int num1, int num2) {
        System.out.println(num1 + " | " + num2);
        int[] firstNum = intToIntArray(num1);
        int[] secNum = intToIntArray(num2);

        if (firstNum.length >= secNum.length)
            return addArrays(firstNum, secNum);
        else
            return addArrays(secNum, firstNum);
    }

    private static int addArrays(int[] longerArr, int[] shorterArr) {
        int[] summedArray = sumArrays(longerArr, shorterArr);

        if (summedArray.length == 0)
            return 0;

        int result = 0;
        int idx = 0;
        do {
            result = (int) (result + (summedArray[idx] * (Math.pow(10, summedArray.length - 1 - idx))));
            idx++;
        } while (idx != summedArray.length);

        return result;
    }

    private static int[] sumArrays(int[] longerArr, int[] shorterArr) {
        int[] summedArray = new int[0];

        for (int i = 1; i <= longerArr.length; i++) {
            int sum;
            if (i <= shorterArr.length)
                sum = longerArr[longerArr.length - i] + shorterArr[shorterArr.length - i];
            else
                sum = longerArr[longerArr.length - i];

            if (sum < 10) {
                summedArray = ArrayUtils.insert(0, summedArray, sum);
            } else {
                int[] secNum = intToIntArray(sum);
                summedArray = ArrayUtils.insert(0, summedArray, secNum);
            }
        }

        System.out.println(ArrayUtils.toString(summedArray));

        return summedArray;
    }

    private static int[] intToIntArray(int num) {
        if (num == 0)
            return new int[0];

        int length = (int) (Math.log10(num) + 1);

        int[] result = new int[length];

        int i = 1;
        do {
            result[length - i] = num % 10;
            num /= 10;
            i++;
        } while (num > 0);
        return result;
    }
}