package katas.battlefield;

/*
    Battleship field validator


 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BattleField {

    public static void main(String[] args) {

    }

    private static int[] ships = new int[]{4,3,3,2,2,2,1,1,1,1};

    public static boolean fieldValidator(int[][] field) {
        // your code here!

        return false;
    }

    private static boolean doesShipExist(int shipNum) {
        for (int ship : ships) {
            if (ship == shipNum)
                return true;
        }
        return false;
    }
}