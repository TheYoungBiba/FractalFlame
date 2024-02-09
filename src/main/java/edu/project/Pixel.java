package edu.project;

public class Pixel {
    private final int x;
    private final int y;
    private int red;
    private int green;
    private int blue;
    private int numberOfHits;

    public Pixel(int x, int y) {
        this.x = x;
        this.y = y;
        red = 0;
        blue = 0;
        green = 0;
        numberOfHits = 0;
    }

    public void setColor(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void increaseCountOfHits() {
        numberOfHits++;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public int getRed() {
        return red;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumberOfHits() {
        return numberOfHits;
    }
}
