package katas.mazeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Mazev3 {

    private static int mazeLength = 0;
    private static int mazeHeight = 0;
    private static char player = ' ';
    private static int[] playerPos;
    private static int[] exitField;

    private static final int WALL = -2;
    private static final int FIELD = -1;
    private static final int START_FIELD = 0;

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
        char[][] array = new char[][]{
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

        exitField = getExitField(maze);

        if (exitField == null) {
            return result;
        }

        System.out.println(maze[exitField[0]][exitField[1]]);

        Field[][] convertedMaze = convertCharsToFields(maze);

        printMaze(convertedMaze);

        markFields(convertedMaze, playerPos[0], playerPos[1]);

        printMaze(convertedMaze);

        result = solveMaze(convertedMaze, playerPos);

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

    private static Field[][] convertCharsToFields(char[][] maze) {
        Field[][] convertedArray = new Field[mazeHeight][mazeLength];

        for (int ver = 0; ver < mazeHeight; ver++) {
            for (int hor = 0; hor < mazeLength; hor++) {
                switch (maze[ver][hor]) {
                    case '#':
                        convertedArray[ver][hor] = new Field(WALL, new int[]{ver, hor});
                        break;
                    case ' ':
                        convertedArray[ver][hor] = new Field(FIELD, new int[]{ver, hor});
                        break;
                    default:
                        player = maze[ver][hor];
                        playerPos = new int[]{ver, hor};
                        convertedArray[ver][hor] = new Field(START_FIELD, new int[]{ver, hor});
                }
            }
        }
        return convertedArray;
    }

    private static void markFields(Field[][] maze, int ver, int hor) {
        if (ver > 0 && maze[ver - 1][hor].getType() == FIELD) {
            maze[ver - 1][hor].setType(maze[ver][hor].getType() + 1);
            maze[ver - 1][hor].setPrevField(new int[]{ver, hor});

            markFields(maze, ver - 1, hor);
        }
        if (ver < (mazeHeight - 1) && maze[ver + 1][hor].getType() == FIELD) {
            maze[ver + 1][hor].setType(maze[ver][hor].getType() + 1);
            maze[ver + 1][hor].setPrevField(new int[]{ver, hor});

            markFields(maze, ver + 1, hor);
        }
        if (hor > 0 && maze[ver][hor - 1].getType() == FIELD) {
            maze[ver][hor - 1].setType(maze[ver][hor].getType() + 1);
            maze[ver][hor - 1].setPrevField(new int[]{ver, hor});

            markFields(maze, ver, hor - 1);
        }
        if (hor < (mazeLength - 1) && maze[ver][hor + 1].getType() == FIELD) {
            maze[ver][hor + 1].setType(maze[ver][hor].getType() + 1);
            maze[ver][hor + 1].setPrevField(new int[]{ver, hor});

            markFields(maze, ver, hor + 1);
        }
    }

    private static List<Character> solveMaze(Field[][] maze, int[] currentPos) {
        List<Character> result = new ArrayList<>();

        stepIntoMaze(maze, currentPos, result);
        return result;
    }

    private static boolean stepIntoMaze(Field[][] maze, int[] currentPos, List<Character> steps) {
        int currentVal = maze[currentPos[0]][currentPos[1]].getType();
        int maxVal = maze[exitField[0]][exitField[1]].getType();

        if (currentVal != maxVal) {
            if (maze[currentPos[0] - 1][currentPos[1]].getType() == currentVal + 1) {
                steps.add('F');
                if (stepIntoMaze(maze, new int[]{currentPos[0] - 1, currentPos[1]}, steps))
                    return true;
                else
                    steps.remove(steps.size() - 1);
            }
            if (maze[currentPos[0] + 1][currentPos[1]].getType() == currentVal + 1) {
                steps.add('F');
                if (stepIntoMaze(maze, new int[]{currentPos[0] + 1, currentPos[1]}, steps))
                    return true;
                else
                    steps.remove(steps.size() - 1);
            }
            if (maze[currentPos[0]][currentPos[1] - 1].getType() == currentVal + 1) {
                steps.add('L');
                if (stepIntoMaze(maze, new int[]{currentPos[0], currentPos[1] - 1}, steps))
                    return true;
                else
                    steps.remove(steps.size() - 1);
            }
            if (maze[currentPos[0]][currentPos[1] + 1].getType() == currentVal + 1) {
                steps.add('R');
                if (stepIntoMaze(maze, new int[]{currentPos[0], currentPos[1] + 1}, steps))
                    return true;
                else
                    steps.remove(steps.size() - 1);
            }

            return false;
        } else
            return true;
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
