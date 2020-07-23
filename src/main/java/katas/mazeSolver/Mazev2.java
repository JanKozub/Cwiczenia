package katas.mazeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Mazev2 {

    private static int mazeLength = 0;
    private static int mazeHeight = 0;
    private static char player = ' ';
    private static int[] playerPos;

    private static int wall = -2;
    private static int field = -1;
    private static int startField = 0;

    public static void main(String[] args) {
//
//        char[][] array = new char[][]{
//                "# #".toCharArray(),
//                " > ".toCharArray(),
//                "# #".toCharArray()
//        };

//        char[][] array = new char[][]{
//                "###########".toCharArray(),
//                "#>        #".toCharArray(),
//                "######### #".toCharArray()
//        };

//        char[][] array = new char[][] {
//                "# #########".toCharArray(),
//                "#        >#".toCharArray(),
//                "###########".toCharArray()
//        };
//
//        char[][] array = new char[][] {
//                "####### #".toCharArray(),
//                "#>#   # #".toCharArray(),
//                "#   #   #".toCharArray(),
//                "#########".toCharArray()
//        };
//
        char[][] array = new char[][] {
                "##########".toCharArray(),
                "#        #".toCharArray(),
                "#  ##### #".toCharArray(),
                "#  #   # #".toCharArray(),
                "#  #^# # #".toCharArray(),
                "#  ### # #".toCharArray(),
                "#      # #".toCharArray(),
                "######## #".toCharArray()
        };

//        char[][] array = new char[][]{
//                "#########################################".toCharArray(),
//                "#<    #       #     #         # #   #   #".toCharArray(),
//                "##### # ##### # ### # # ##### # # # ### #".toCharArray(),
//                "# #   #   #   #   #   # #     #   #   # #".toCharArray(),
//                "# # # ### # ########### # ####### # # # #".toCharArray(),
//                "#   #   # # #       #   # #   #   # #   #".toCharArray(),
//                "####### # # # ##### # ### # # # #########".toCharArray(),
//                "#   #     # #     # #   #   # # #       #".toCharArray(),
//                "# # ####### ### ### ##### ### # ####### #".toCharArray(),
//                "# #             #   #     #   #   #   # #".toCharArray(),
//                "# ############### ### ##### ##### # # # #".toCharArray(),
//                "#               #     #   #   #   # #   #".toCharArray(),
//                "##### ####### # ######### # # # ### #####".toCharArray(),
//                "#   # #   #   # #         # # # #       #".toCharArray(),
//                "# # # # # # ### # # ####### # # ### ### #".toCharArray(),
//                "# # #   # # #     #   #     # #     #   #".toCharArray(),
//                "# # ##### # # ####### # ##### ####### # #".toCharArray(),
//                "# #     # # # #   # # #     # #       # #".toCharArray(),
//                "# ##### ### # ### # # ##### # # ### ### #".toCharArray(),
//                "#     #     #     #   #     #   #   #    ".toCharArray(),
//                "#########################################".toCharArray()
//        };

        System.out.println(escape(array));
    }

    public static List<Character> escape(char[][] maze) {
        mazeLength = maze[0].length;
        mazeHeight = maze.length;

        List<Character> result = new ArrayList<>();

        int[] exit = getExitField(maze);

        if (exit == null) {
            return result;
        }

        System.out.println(maze[exit[0]][exit[1]]);

        Field[][] convertedMaze = ConvertCharsToFields(maze);

        printMaze(convertedMaze);

        markFields(convertedMaze, playerPos[0], playerPos[1]);

        printMaze(convertedMaze);

        result = solveMaze(convertedMaze, exit);

        return result;
    }

    private static int[] getExitField(char[][] maze) {
        for (int i = 0; i < mazeLength; i++) {
            if (maze[0][i] == ' ')
                return new int[]{0, i};
            if (maze[mazeHeight - 1][i] == ' ')
                return new int[]{mazeHeight - 1, i};
        }

        for (int i = 0; i < mazeHeight; i++) {
            if (maze[i][0] == ' ')
                return new int[]{i, 0};

            if (maze[i][mazeLength - 1] == ' ')
                return new int[]{i, mazeLength - 1};
        }

        return null;
    }

    private static Field[][] ConvertCharsToFields(char[][] maze) {
        Field[][] convertedArray = new Field[mazeHeight][mazeLength];

        for (int ver = 0; ver < mazeHeight; ver++) {
            for (int hor = 0; hor < mazeLength; hor++) {
                switch (maze[ver][hor]) {
                    case '#':
                        convertedArray[ver][hor] = new Field(wall, new int[]{ver, hor});
                        break;
                    case ' ':
                        convertedArray[ver][hor] = new Field(field, new int[]{ver, hor});
                        break;
                    default:
                        player = maze[ver][hor];
                        playerPos = new int[]{ver, hor};
                        convertedArray[ver][hor] = new Field(startField, new int[]{ver, hor});
                }
            }
        }
        return convertedArray;
    }

    private static void markFields(Field[][] maze, int ver, int hor) {
        if (ver > 0 && maze[ver - 1][hor].getType() == field) {
            maze[ver - 1][hor].setType(maze[ver][hor].getType() + 1);
            maze[ver - 1][hor].setPrevField(new int[]{ver, hor});

            markFields(maze, ver - 1, hor);
        }
        if (ver < (mazeHeight - 1) && maze[ver + 1][hor].getType() == field) {
            maze[ver + 1][hor].setType(maze[ver][hor].getType() + 1);
            maze[ver + 1][hor].setPrevField(new int[]{ver, hor});

            markFields(maze, ver + 1, hor);
        }
        if (hor > 0 && maze[ver][hor - 1].getType() == field) {
            maze[ver][hor - 1].setType(maze[ver][hor].getType() + 1);
            maze[ver][hor - 1].setPrevField(new int[]{ver, hor});

            markFields(maze, ver, hor - 1);
        }
        if (hor < (mazeLength - 1) && maze[ver][hor + 1].getType() == field) {
            maze[ver][hor + 1].setType(maze[ver][hor].getType() + 1);
            maze[ver][hor + 1].setPrevField(new int[]{ver, hor});

            markFields(maze, ver, hor + 1);
        }
    }

    private static List<Character> solveMaze(Field[][] maze, int[] exit) {
        List<Character> result = new ArrayList<>();
        int[] lastMove = new int[0];
        int currentVal;
        do {
            Field field = maze[exit[0]][exit[1]];
            currentVal = field.getType();

            if (currentVal == 15) {
                System.out.println("test");
            }

            if (currentVal == -1)
                return result;

            if (currentVal == 1)
                lastMove = new int[]{exit[0], exit[1]};


            if (exit[0] > 0)
                if (exit[0] - 1 == field.getPrevField()[0]) {

                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1].getType() == currentVal + 1) {
                            result.add('L');
                        }

                    if (exit[1] > 0)
                        if (maze[exit[0]][exit[1] - 1].getType() == currentVal + 1) {
                            result.add('R');
                        }

                    result.add('F');
                    exit[0] = exit[0] - 1;
                    continue;
                }

            if (exit[0] < mazeHeight - 1)
                if (exit[0] + 1 == field.getPrevField()[0]) {

                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1].getType() == currentVal + 1) {
                            result.add('R');
                        }

                    if (exit[1] > 0)
                        if (maze[exit[0]][exit[1] - 1].getType() == currentVal + 1) {
                            result.add('L');
                        }

                    result.add('F');
                    exit[0] = exit[0] + 1;
                    continue;
                }

            if (exit[1] > 0)
                if (exit[1] - 1 == field.getPrevField()[1]) {
                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1].getType() != currentVal + 1) {
                            if (maze[exit[0] + 1][exit[1]].getType() - 1 == currentVal)
                                result.add('R');
                            else if (maze[exit[0] - 1][exit[1]].getType() - 1 == currentVal)
                                result.add('L');
                        }
                    result.add('F');

                    exit[1] = exit[1] - 1;
                    continue;
                }

            if (exit[1] < mazeLength - 1)
                if (exit[1] + 1 == field.getPrevField()[1]) {
                    if (maze[exit[0]][exit[1] - 1].getType() != currentVal + 1) {
                        if (maze[exit[0] + 1][exit[1]].getType() - 1 == currentVal)
                            result.add('L');
                        else if (maze[exit[0] - 1][exit[1]].getType() - 1 == currentVal)
                            result.add('R');
                    }
                    result.add('F');

                    exit[1] = exit[1] + 1;
                }
        } while (currentVal != 0);

        Collections.reverse(result);

        int[] pointingField;
        switch (player) {
            case '<':
                pointingField = new int[]{exit[0], exit[1] - 1};
                break;
            case '^':
                pointingField = new int[]{exit[0] - 1, exit[1]};
                break;
            case '>':
                pointingField = new int[]{exit[0], exit[1] + 1};
                break;
            case 'v':
                pointingField = new int[]{exit[0] + 1, exit[1]};
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + player);
        }

        if (pointingField[0] != lastMove[0] && pointingField[1] == lastMove[1])
            result.add(0, 'B');
        else if (pointingField[0] == lastMove[0] && pointingField[1] != lastMove[1])
            result.add(0, 'B');
        else if (pointingField[0] < lastMove[0] && pointingField[1] > lastMove[1])
            result.add(0, 'R');
        else if (pointingField[0] > lastMove[0] && pointingField[1] < lastMove[1])
            result.add(0, 'L');
        else if (pointingField[0] < lastMove[0] && pointingField[1] < lastMove[1])
            result.add(0, 'R');
        else if (pointingField[0] > lastMove[0] && pointingField[1] > lastMove[1])
            result.add(0, 'L');

        return result;
    }

    private static void printMaze(Field[][] maze) {
        for (Field[] ver : maze) {
            System.out.println(Arrays.toString(ver));
        }
        System.out.println("--------------------------------");
    }

    private static class Field {
        private int type;
        private int[] prevField;

        public Field(int type, int[] prevField) {
            this.type = type;
            this.prevField = prevField;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int[] getPrevField() {
            return prevField;
        }

        public void setPrevField(int[] prevField) {
            this.prevField = prevField;
        }

        @Override
        public String toString() {
            return "" + type;
        }
    }
}
