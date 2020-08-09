package katas.battleship;

import java.util.Arrays;

/*
Write a method that takes a field for well-known board game "Battleship" as an argument and returns true if it has a valid disposition of ships, false otherwise. Argument is guaranteed to be 10*10 two-dimension array. Elements in the array are numbers, 0 if the cell is free and 1 if occupied by ship.

Battleship (also Battleships or Sea Battle) is a guessing game for two players. Each player has a 10x10 grid containing several "ships" and objective is to destroy enemy's forces by targetting individual cells on his field. The ship occupies one or more cells in the grid. Size and number of ships may differ from version to version. In this kata we will use Soviet/Russian version of the game.


Before the game begins, players set up the board and place the ships accordingly to the following rules:
There must be single battleship (size of 4 cells), 2 cruisers (size 3), 3 destroyers (size 2) and 4 submarines (size 1). Any additional ships are not allowed, as well as missing ships.
Each ship must be a straight line, except for submarines, which are just single cell.

The ship cannot overlap or be in contact with any other ship, neither by edge nor by corner.

This is all you need to solve this kata. If you're interested in more information about the game, visit this link.
 */

public class BattleField {

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
        int[] ships = new int[]{4, 3, 3, 2, 2, 2, 1, 1, 1, 1};

        for (int ver = 0; ver < FIELD_HEIGHT; ver++) {
            for (int hor = 0; hor < FIELD_LENGTH; hor++) {

                if (field[ver][hor] == 1 || field[ver][hor] == 2) {
                    if (!initialCheck(field, ver, hor))
                        return false;
                }

                if (field[ver][hor] == 1) {
                    int shipLength = checkShipLength(field, ships, ver, hor);

                    if (shipLength == -1)
                        return false;

                    if (doesShipExists(ships, shipLength)) {
                        ships = removeShip(ships, shipLength);
                    } else {
                        return false;
                    }
                }
            }
        }
        System.out.println(Arrays.toString(ships));
        return ships.length == 0;
    }

    private static boolean initialCheck(int[][] field, int ver, int hor) {
        if (ver > 0) {
            return isShapeValid(field, ver - 1, hor);
        }
        return isShapeValid(field, ver + 1, hor);
    }

    private static int checkShipLength(int[][] field, int[] ships, int ver, int hor) {
        boolean fieldsAvailable;
        int length = 1;
        do {
            fieldsAvailable = false;
            field[ver][hor] = 2;

            if (ver > 0) {
                if (field[ver - 1][hor] == 1) {
                    if (!isShapeValid(field, ver, hor)) return -1;
                    fieldsAvailable = true;
                    ver = ver - 1;
                    length = length + 1;
                }
            }
            if (ver < FIELD_HEIGHT - 1) {
                if (field[ver + 1][hor] == 1) {
                    if (!isShapeValid(field, ver, hor)) return -1;
                    fieldsAvailable = true;
                    ver = ver + 1;
                    length = length + 1;
                }
            }
            if (hor > 0) {
                if (field[ver][hor - 1] == 1) {
                    fieldsAvailable = true;
                    hor = hor - 1;
                    length = length + 1;
                }
            }
            if (hor < FIELD_LENGTH - 1) {
                if (field[ver][hor + 1] == 1) {
                    fieldsAvailable = true;
                    hor = hor + 1;
                    length = length + 1;
                }
            }
            printField(ships, field);
        } while (fieldsAvailable);
        return length;
    }

    private static boolean isShapeValid(int[][] field, int ver, int hor) {
        if (hor > 0) {
            if (field[ver][hor - 1] == 1 || field[ver][hor - 1] == 2)
                return false;
        }
        if (hor < FIELD_LENGTH - 1) {
            return field[ver][hor + 1] != 1 && field[ver][hor + 1] != 2;
        }
        return true;
    }


    private static int[] removeShip(int[] ships, int ship) {
        int[] newShips = new int[ships.length - 1];
        int shipIdx = 0;
        for (int i = 0; i < ships.length; i++) {
            if (ships[i] == ship) {
                shipIdx = i;
                break;
            }
            newShips[i] = ships[i];
        }
        for (int j = shipIdx + 1; j < ships.length; j++) {
            newShips[j - 1] = ships[j];
        }
        return newShips;
    }

    private static boolean doesShipExists(int[] ships, int ship) {
        for (int s : ships) {
            if (s == ship)
                return true;
        }
        return false;
    }

    private static void printField(int[] ships, int[][] battleField) {
        for (int[] ver : battleField) {
            System.out.println(Arrays.toString(ver));
        }
        System.out.println("->");
        System.out.println(Arrays.toString(ships));
        System.out.println("---------------------------");
    }
}