package katas.mazeSolver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/*
That's terrible! Some evil korrigans have abducted you during your sleep and threw you into a maze of thorns in the scrubland D:
But have no worry, as long as you're asleep your mind is floating freely in the sky above your body.

Seeing the whole maze from above in your sleep, can you remember the list of movements you'll have to do to get out when you awake?

Input
You are given the whole maze maze as a 2D grid, more specifically in your language:

a char[][]

maze[0][0] is the top left-hand corner

maze[maze.length - 1][maze[0].length - 1] is the bottom right-hand corner

Inside this 2D grid:

' ' is some walkable space
'#' is a thorn bush (you can't pass through)
'^', '<', 'v' or '>' is your sleeping body facing respectively the top, left, bottom or right side of the map.
Output
Write the function escape that returns the list/array of moves you need to do relatively to the direction you're
facing in order to escape the maze (you won't be able to see the map when you wake up). as an array of the following instructions:

'F' move one step forward
'L' turn left
'R' turn right
'B' turn back
Note: 'L','R', and 'B' ONLY perform a rotation and will not move your body

If the maze has no exit, return an empty array.

This is a real maze, there is no "escape" point. Just reach the edge of the map and you're free!
You don't explicitely HAVE to find the shortest possible route, but you're quite likely to timeout if you don't ;P
Aside from having no escape route the mazes will all be valid (they all contain one and only one "body" character and no
other characters than the body, "#" and " ". Besides, the map will always be rectangular, you don't have to check that either)

    assumptions:
        arrow points up
 */
public class Maze {

    private static final int WALL = -1;
    private static final int BLANK = -2;

    private static final int DIR_UP = 1;
    private static final int DIR_DOWN = 2;
    private static final int DIR_LEFT = 3;
    private static final int DIR_RIGHT = 4;

    public static void main(String[] args) {
        char[][] array = new char[][] {
                "#########################################".toCharArray(),
                "#<    #       #     #         # #   #   #".toCharArray(),
                "##### # ##### # ### # # ##### # # # ### #".toCharArray(),
                "# #   #   #   #   #   # #     #   #   # #".toCharArray(),
                "# # # ### # ########### # ####### # # # #".toCharArray(),
                "#   #   # # #       #   # #   #   # #   #".toCharArray(),
                "####### # # # ##### # ### # # # #########".toCharArray(),
                "#   #     # #     # #   #   # # #       #".toCharArray(),
                "# # ####### ### ### ##### ### # ####### #".toCharArray(),
                "# #             #   #     #   #   #   # #".toCharArray(),
                "# ############### ### ##### ##### # # # #".toCharArray(),
                "#               #     #   #   #   # #   #".toCharArray(),
                "##### ####### # ######### # # # ### #####".toCharArray(),
                "#   # #   #   # #         # # # #       #".toCharArray(),
                "# # # # # # ### # # ####### # # ### ### #".toCharArray(),
                "# # #   # # #     #   #     # #     #   #".toCharArray(),
                "# # ##### # # ####### # ##### ####### # #".toCharArray(),
                "# #     # # # #   # # #     # #       # #".toCharArray(),
                "# ##### ### # ### # # ##### # # ### ### #".toCharArray(),
                "#     #     #     #   #     #   #   #    ".toCharArray(),
                "#########################################".toCharArray()
        };

        System.out.println(escape(array));
    }

    public static List<Character> escape(char[][] maze) {

        int[][] intArray = prepareMaze(maze);
        printMaze(intArray);
        int[] exit = markMaze(intArray);
        // what if there is NO exit ???
        printMaze(intArray);
        System.out.println(Arrays.toString(exit));
        List<int[]> path = buildPath(intArray, exit);

        path.stream().map(Arrays::toString).forEach(System.out::println);

        int[] start = path.get(0);
        char startDir = maze[start[0]][start[1]];
        List<Character> directions = formatPath(path, parseDirection(startDir));

        return directions;
    }

    private static List<Character> formatPath(List<int[]> path, int currentDirection) {
        List<Character> result = new ArrayList<>();

        for(int i = 0; i < path.size()-1; i++) {
            int[] currentPosition = path.get(i);
            int[] nextPosition = path.get(i+1);
            int nextDirection = getDirection(
                    nextPosition[0] - currentPosition[0],
                    nextPosition[1] - currentPosition[1]);

            char c = makeTurn(currentDirection, nextDirection);
            if(c != 0) {
                result.add(c);
                currentDirection = nextDirection;
            }
            result.add('F');
        }

//        ############
//        #          #
//        # <
//        #          #
//        ############
//
//        ############
//        #          #
//        # <   ##
//        #          #
//        ############
//
//
//        ############
//        #          #
//        #  ####### #
//        ####     ###
//        # <  ##
//        ####     ###
//        #  ####### #
//        #          #
//        ############


        return result;
    }

    private static char makeTurn(int direction, int nextDirection) {
        if(direction == nextDirection)
            return 0;

        if(direction == DIR_UP) {
            switch (nextDirection) {
                case DIR_LEFT:
                    return 'L';
                case DIR_RIGHT:
                    return 'R';
                case DIR_DOWN:
                    return 'B';
            }
        } else if(direction == DIR_RIGHT) {
                switch (nextDirection) {
                    case DIR_LEFT:
                        return 'B';
                    case DIR_UP:
                        return 'L';
                    case DIR_DOWN:
                        return 'R';
                }
            } else if(direction == DIR_LEFT) {
            switch (nextDirection) {
                case DIR_RIGHT:
                    return 'B';
                case DIR_UP:
                    return 'R';
                case DIR_DOWN:
                    return 'L';
            }
        } else if(direction == DIR_DOWN) {
            switch (nextDirection) {
                case DIR_RIGHT:
                    return 'L';
                case DIR_UP:
                    return 'B';
                case DIR_LEFT:
                    return 'R';
            }
        }

        throw new IllegalStateException("This should not happen!");
    }

    private static List<int[]> buildPath(int[][] maze, int[] exit) {
        ArrayList<int[]> result = new ArrayList<>();
        result.add(exit);

        int erow = exit[0];
        int ecol = exit[1];

        while(true) {
            int currentValue = maze[erow][ecol];
            if (currentValue == 0) {
                Collections.reverse(result);
                return result;
            }

            int prevValue = currentValue - 1;

            if ((erow > 0) && maze[erow - 1][ecol] == prevValue) {
                result.add(new int[]{erow - 1, ecol});
                erow--;
            } else if ((erow < maze.length) && maze[erow + 1][ecol] == prevValue) {
                result.add(new int[]{erow + 1, ecol});
                erow++;
            } else if ((ecol > 0) && maze[erow][ecol - 1] == prevValue) {
                result.add(new int[]{erow, ecol - 1});
                ecol--;
            } else if ((ecol < maze[0].length) && maze[erow][ecol = 1] == prevValue) {
                result.add(new int[]{erow + 1, ecol});
                ecol++;
            }
        }
    }

    private static int[] markMaze(int[][] array) {
        int step = 1;

        boolean wasMarked = true;
        int[] lastNum = new int[2];
        while (wasMarked) {
            wasMarked = false;
            for (int row = 0; row < array.length; row++) {
                for (int col = 0; col < array[0].length; col++) {
                    if (markNextSquare(row, col, step, array)) {
                        step = step + 1;
                        lastNum[0] = row;
                        lastNum[1] = col;
                        wasMarked = true;
                    }
                }
            }
        }
        return lastNum;
    }

    private static void printMaze(int[][] array) {
        String test = Arrays
                .stream(array)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        System.out.println(test + "\n----------------");
    }

    private static int[][] prepareMaze(char[][] maze) {
        int[][] array = new int[maze.length][maze[0].length];

        for (int col = 0; col < maze[0].length; col++) {
            for (int row = 0; row < maze.length; row++) {
                if (maze[row][col] == '#')
                    array[row][col] = WALL;
                if (maze[row][col] == ' ')
                    array[row][col] = BLANK;
                if (maze[row][col] == '<' || maze[row][col] == '^' || maze[row][col] == '>' || maze[row][col] == 'v')
                    array[row][col] = 0;
            }
        }
        return array;
    }

    private static boolean markNextSquare(int row, int col, int step, int[][] maze) {

        if (maze[row][col] == BLANK) {
            if (col < maze[0].length - 1)
                if (maze[row][col + 1] >= 0) {
                    maze[row][col] = step;
                    return true;
                }
            if (col > 0)
                if (maze[row][col - 1] >= 0) {
                    maze[row][col] = step;
                    return true;
                }
            if (row < maze.length - 1)
                if (maze[row + 1][col] >= 0) {
                    maze[row][col] = step;
                    return true;
                }
            if (row > 0)
                if (maze[row - 1][col] >= 0) {
                    maze[row][col] = step;
                    return true;
                }
        }
        return false;
    }

    private static int parseDirection(char c) {
        switch(c) {
            case '^': return DIR_UP;
            case '<': return DIR_LEFT;
            case '>': return DIR_RIGHT;
            case 'v': return DIR_DOWN;
            default: throw new IllegalArgumentException("Unsupported direction: " + c);
        }
    }

    private static int getDirection(double dRow, double dCol) {
        if(dRow < 0)
            return DIR_UP;
        if(dRow > 0)
            return DIR_DOWN;
        if(dCol < 0)
            return DIR_LEFT;
        if(dCol > 0)
            return DIR_RIGHT;
        throw new IllegalStateException("Invalid delta");
    }
}