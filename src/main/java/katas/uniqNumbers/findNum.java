package katas.uniqNumbers;

//There is an array with some numbers. All numbers are equal except for one. Try to find it!
//min size = 3
public class findNum {
    public static void main(String[] args) {
        System.out.println(findUniq(new double[]{4.0, 4.0, 4.0, 3.0, 4.0, 4.0, 4.0, 4.0}));
    }

    public static double findUniq(double arr[]) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] != arr[i]) {
                if (i < arr.length - 2)
                    if (arr[i] == arr[i + 1])
                        return arr[i - 1];
                    else
                        return arr[i];
                else
                    return arr[i];
            }
        }
        return arr[0];
    }
}
