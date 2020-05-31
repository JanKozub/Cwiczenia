package katas.bouncingBall;

public class BouncingBall {

    public static void main(String[] args) {
        System.out.println(bouncingBall(30, 0.66, 1.5));
    }

    public static int bouncingBall(double h, double bounce, double window) {
        if (h < 0)
            return -1;
        if (bounce < 0 || bounce >= 1)
            return -1;
        if (window > h)
            return -1;

        int crossCount = -1;
        double bounceH = h;

        while (bounceH > window) {
            crossCount = crossCount + 2;
            bounceH = bounceH * bounce;
        }
        return crossCount;
    }
}