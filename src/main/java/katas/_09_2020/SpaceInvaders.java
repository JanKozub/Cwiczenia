package katas._09_2020;

import java.util.ArrayList;
import java.util.List;

/*
T

T-tn >= 3

[idx, x, y, sp, t1, tn]
[idx, x, y, sp, t2, tn]
[idx, x, y, sp, t3, tn]
[idx, x, y, sp, t4, tn]
[idx, x, y, sp, t5, tn]
[idx, x, y, sp, t6, tn]
[idx, x, y, sp, t7, tn]
 */
public class SpaceInvaders {

    private static final int[][][] exampleAliens = {
            {{3, 1, 2, -2, 2, 3, 6, -3, 7, 1}},
            {{5, 2, -2, 3, 1, 0, 4, 8, 3, -2, 5}, {1, 4, -1, 0, 3, 6, 1, -3, 1, 2, -4}},
            {{4, 1, -7, -5, 1, 6, 3, -2, 1, 0, 2, 6, 5}, {2, 0, 3, -4, 0, 2, -1, 5, -8, -3, -2, -5, 1}, {1, 2, 0, -6, 4, 7, -2, 4, -4, 2, -5, 0, 4}},
    };

    private static final int[][] examplePositions = {{6, 4}, {10, 2}, {15, 6}};

    private final int height;
    private final int length;
    private final int ship;
    private final int[][] initialAliens;

    public static void main(String[] args) {
        System.out.println(new SpaceInvaders(exampleAliens[0], examplePositions[0]).blastSequence());
    }

    public SpaceInvaders(int[][] aliens, int[] position) {
        ship = position[0];
        height = position[1];
        length = aliens[0].length;
        initialAliens = aliens;
    }

    public List<Integer> blastSequence() {
        List<Alien> aliens = initAliens();

        int step = 0;
        while (!aliensWon(aliens)) {
            printBoard(step++, aliens);
            moveAliens(aliens);

            int index = pickAlienToShoot(aliens);
            if(index >= 0) {
                //fireCannon();
                aliens.remove(index);
            }
        }

        return null;
    }

    private int pickAlienToShoot(List<Alien> aliens) {

        return 0;
    }

    private void moveAliens(List<Alien> aliens) {
        for (Alien alien : aliens) {
            alien.move();
        }
    }

    private boolean aliensWon(List<Alien> aliens) {
        return aliens.stream().anyMatch(alien -> alien.y == height);
    }

    private List<Alien> initAliens() {
        List<Alien> aliens = new ArrayList<>();
        for (int y = 0; y < initialAliens.length; y++) {
            for (int x = 0; x < initialAliens[0].length; x++) {
                aliens.add(new Alien(x, y, initialAliens[y][x]));
            }
        }
        return aliens;
    }

    private void printBoard(int i, List<Alien> aliens) {
        System.out.println("---------------------------------------");
        System.out.println("step == " + i);
        for (Alien alien : aliens) {
            System.out.println(alien.toString());
        }
    }

    private class Alien {

        private int x;
        private int y;
        private int speed;
        private int fireT;
        private int loseT;

        public Alien(int x, int y, int speed) {
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.fireT = calculateFireT(x, y, speed);
            this.loseT = calculateLoseT(x, y, speed);
        }

        public void move() {
            x = x + speed;

            if (x >= length) {
                int of = x - length; //overflow
                y = y + 1;
                x = length - of - 1;
                speed = -speed;
            } else if (x < 0) {
                y = y + 1;
                x = -x - 1;
                speed = -speed;
            }
            fireT = calculateFireT(x, y, speed);
        }

        private int calculateFireT(int x, int y, int speed) {
            int steps = 0;
            do {
                steps++;
                x += speed;

                if (x >= length) {
                    int of = x - length; //overflow
                     y++;
                     x = length - of - 1;
                     speed = -speed;
                } else if (x < 0) {
                    y++;
                    x = -x - 1;
                    speed = -speed;
                }

                if (x == ship)
                    return steps;

            } while (y < height - 1);
            return 0;
        }

        private int calculateLoseT(int x, int y, int speed) {
            int steps = 0;
            do {
                steps++;
                x += speed;

                if (x >= length) {
                    int of = x - length; //overflow
                    y++;
                    x = length - of - 1;
                    speed = -speed;
                } else if (x < 0) {
                    y++;
                    x = -x - 1;
                    speed = -speed;
                }
            } while (y < height - 1);
            return steps;
        }

        @Override
        public String toString() {
            return "Alien{" +
                    "x=" + x +
                    ", y=" + y +
                    ", speed=" + speed +
                    ", fireT=" + fireT +
                    ", loseT=" + loseT +
                    ", atFireLine=" + (x == ship) +
                    '}';
        }
    }
}