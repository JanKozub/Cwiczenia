package katas.battleship;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a valid disposition of ships, false otherwise. Argument is guaranteed to be 10*10 two-dimension array. Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.

Battleship (also Battleships or Sea Battle) is a guessing game for two players. Each player has a 10x10 grid containing several "ships" and objective is to destroy enemy's forces by targetting individual cells on his field. The ship occupies one or more cells in the grid. Size and number of ships may differ from version to version. In this kata we will use Soviet/Russian version of the game.


Before the game begins, players set up the board and place the ships accordingly to the following rules:
There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1). Any additional ships are not allowed, as well as missing ships.
Each ship must be a straight line, except for submarines, which are just single cell.

The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.

This is all you need to solve this kata. If you're interested in more information about the game, visit this link.
 */

public class BattleField2 {

    public static final int HEIGHT = 0;
    private static final int FIELD_HEIGHT = 10;
    private static final int FIELD_LENGTH = 10;

    private static int[][] battleField = {
            {0, 0, 1, 1, 1, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 1, 0, 1},
            {0, 0, 1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public static void main(String[] args) {
        System.out.println(fieldValidator(battleField));
    }

    public static boolean fieldValidator(int[][] field) {
        if (!isLayoutValid(field)) {
            return false;
        }
        return shipsAreOK(field);
    }

    private static boolean isLayoutValid(int[][] field) {
        for (int ver = 0; ver < FIELD_HEIGHT - 1; ver++) {
            for (int hor = 0; hor < FIELD_LENGTH; hor++) {
                if (isShipAt(field, hor, ver)) {
                    if (isShipAt(field, hor - 1, ver + 1) || isShipAt(field, hor + 1, ver + 1))
                        return false;
                }
            }
        }
        return true;
    }

    private static boolean isShipAt(int[][] field, int hor, int ver) {
        if ((ver < 0) || (hor < 0) || (ver >= FIELD_HEIGHT) || (hor >= FIELD_LENGTH))
            return false;
        return field[ver][hor] == 1;
    }

    private static void clearShipAt(int[][] field, int hor, int ver) {
        if ((ver < 0) || (hor < 0) || (ver >= FIELD_HEIGHT) || (hor >= FIELD_LENGTH))
            return;
        field[ver][hor] = 0;
    }

    private static boolean shipsAreOK(int[][] field) {
        Collection<Integer> ships = new LinkedList<>(Arrays.asList(4, 3, 3, 2, 2, 2, 1, 1, 1, 1));

        for (int ver = 0; ver < FIELD_HEIGHT; ver++) {
            for (int hor = 0; hor < FIELD_LENGTH; hor++) {
                if (isShipAt(field, hor, ver)) {
                    if(!ships.remove(Integer.valueOf(getShipLenght(field, hor, ver))))
                        return false;
                }
            }
        }

        return ships.size() == 0;
    }

    private static int getShipLenght(int[][] field, int hor, int ver) {
        clearShipAt(field, hor, ver);

        int width = 1;
        while(isShipAt(field, hor + width, ver)) {
            clearShipAt(field, hor + width, ver);
            width++;
        }

        int height = 1;
        while(isShipAt(field, hor, ver + height)) {
            clearShipAt(field, hor, ver + height);
            height++;
        }

        return Math.max(width, height);
    }
}