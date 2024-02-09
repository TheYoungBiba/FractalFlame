package edu.project;

import java.util.Random;

public record AffineCoefficientsAndColor(
    double a,
    double b,
    double c,
    double d,
    double e,
    double f,
    int red,
    int green,
    int blue
) {
    private final static int MAX_RGB_VALUE = 255;

    public static AffineCoefficientsAndColor getCoefficients() {
        final double defaultRange = 1.5;
        return getCoefficients(defaultRange);
    }

    public static AffineCoefficientsAndColor getCoefficients(double range) {
        double a = getRandomVal(range);
        double b = getRandomVal(range);
        double c = getRandomVal(range + 2);
        double d = getRandomVal(range);
        double e = getRandomVal(range);
        double f = getRandomVal(range + 2);
        while (!isValidCoefficients(a, b, d, e)) {
            a = getRandomVal(range);
            b = getRandomVal(range);
            d = getRandomVal(range);
            e = getRandomVal(range);
        }
        Random random = new Random();
        return new AffineCoefficientsAndColor(a, b, c, d, e, f,
            random.nextInt(0, MAX_RGB_VALUE + 1),
            random.nextInt(0, MAX_RGB_VALUE + 1),
            random.nextInt(0, MAX_RGB_VALUE + 1)
        );
    }

    public Point transformByCoefficients(Point point) {
        double x = point.x() * this.a + point.y() * this.b + this.c;
        double y = point.x() * this.d + point.y() * this.e + this.f;
        return new Point(x, y);
    }

    private static boolean isValidCoefficients(double a, double b, double d, double e) {
        if (!(Math.pow(a, 2) + Math.pow(d, 2) < 1)) {
            return false;
        }
        if (!(Math.pow(b, 2) + Math.pow(e, 2) < 1)) {
            return false;
        }
        return Math.pow(a, 2) + Math.pow(d, 2) + Math.pow(b, 2) + Math.pow(e, 2) < 1 + Math.pow(a * e - b * d, 2);
    }

    private static double getRandomVal(double range) {
        Random random = new Random();
        return range * random.nextDouble() * Math.pow(-1, random.nextInt(0, 2));
    }
}
