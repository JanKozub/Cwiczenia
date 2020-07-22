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

        int[][] numericalMaze = translateCharsToNumbers(maze);

        printMaze(numericalMaze);

        markNextSteps(numericalMaze, playerPos[0], playerPos[1]);

        printMaze(numericalMaze);

        result = solveMaze(numericalMaze, exit);

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

    private static int[][] translateCharsToNumbers(char[][] maze) {
        int[][] result = new int[mazeHeight][mazeLength];

        for (int ver = 0; ver < mazeHeight; ver++) {
            for (int hor = 0; hor < mazeLength; hor++) {
                switch (maze[ver][hor]) {
                    case '#':
                        result[ver][hor] = wall;
                        break;
                    case ' ':
                        result[ver][hor] = field;
                        break;
                    default:
                        player = maze[ver][hor];
                        playerPos = new int[]{ver, hor};
                        result[ver][hor] = 0;
                }
            }
        }
        return result;
    }

    private static void markNextSteps(int[][] maze, int ver, int hor) {
        if (ver > 0 && maze[ver - 1][hor] == field) {
            maze[ver - 1][hor] = maze[ver][hor] + 1;
            markNextSteps(maze, ver - 1, hor);
        }
        if (ver < (mazeHeight - 1) && maze[ver + 1][hor] == field) {
            maze[ver + 1][hor] = maze[ver][hor] + 1;
            markNextSteps(maze, ver + 1, hor);
        }
        if (hor > 0 && maze[ver][hor - 1] == field) {
            maze[ver][hor - 1] = maze[ver][hor] + 1;
            markNextSteps(maze, ver, hor - 1);
        }
        if (hor < (mazeLength - 1) && maze[ver][hor + 1] == field) {
            maze[ver][hor + 1] = maze[ver][hor] + 1;
            markNextSteps(maze, ver, hor + 1);
        }
    }

    private static List<Character> solveMaze(int[][] maze, int[] exit) {
        List<Character> result = new ArrayList<>();
        int[] lastMove = new int[0];
        int currentVal;
        do {
            currentVal = maze[exit[0]][exit[1]];

            if (currentVal == 15) {
                System.out.println("debug");
            }
            if (currentVal == -1)
                return result;

            if (currentVal == 1)
                lastMove = new int[]{exit[0], exit[1]};


            if (exit[0] > 0)
                if (maze[exit[0] - 1][exit[1]] + 1 == currentVal) {

                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1] == currentVal + 1) {
                            result.add('L');
                        }

                    if (exit[1] > 0)
                        if (maze[exit[0]][exit[1] - 1] == currentVal + 1) {
                            result.add('R');
                        }

                    result.add('F');
                    exit[0] = exit[0] - 1;
                    continue;
                }

            if (exit[0] < mazeHeight - 1)
                if (maze[exit[0] + 1][exit[1]] + 1 == currentVal) {

                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1] == currentVal + 1) {
                            result.add('R');
                        }

                    if (exit[1] > 0)
                        if (maze[exit[0]][exit[1] - 1] == currentVal + 1) {
                            result.add('L');
                        }

                    result.add('F');
                    exit[0] = exit[0] + 1;
                    continue;
                }

            if (exit[1] > 0)
                if (maze[exit[0]][exit[1] - 1] + 1 == currentVal) {
                    if (exit[1] < mazeLength - 1)
                        if (maze[exit[0]][exit[1] + 1] != currentVal + 1) {
                            if (maze[exit[0] + 1][exit[1]] - 1 == currentVal)
                                result.add('R');
                            else if (maze[exit[0] - 1][exit[1]] - 1 == currentVal)
                                result.add('L');
                        }
                    result.add('F');

                    exit[1] = exit[1] - 1;
                    continue;
                }

            if (exit[1] < mazeLength - 1)
                if (maze[exit[0]][exit[1] + 1] + 1 == currentVal) {
                    if (maze[exit[0]][exit[1] - 1] != currentVal + 1) {
                        if (maze[exit[0] + 1][exit[1]] - 1 == currentVal)
                            result.add('L');
                        else if (maze[exit[0] - 1][exit[1]] - 1 == currentVal)
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

    private static void printMaze(int[][] maze) {
        for (int[] ver : maze) {
            System.out.println(Arrays.toString(ver));
        }
        System.out.println("--------------------------------");
    }

}
