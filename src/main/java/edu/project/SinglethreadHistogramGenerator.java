package edu.project;

import java.util.Random;

public class SinglethreadHistogramGenerator implements HistogramGenerator {
    private final double maxXCoefficient = 1.0;
    private final double minXCoefficient = -1.0;
    private final double maxYCoefficient = 1.0;
    private final double minYCoefficient = -1.0;
    private Pixel[][] pixels;

    public SinglethreadHistogramGenerator() {}

    public Pixel[][] generate(
        int width,
        int height,
        int countOfPoints,
        int countOfIterations,
        int countOfSymmetricalParts,
        int countOfAffineCoefficients,
        Transformation... transforms
        ) {
        pixels = new Pixel[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = new Pixel(i, j);
            }
        }
        AffineCoefficientsAndColor[] affineCoefficientsAndColors = initStartCoefficients(countOfAffineCoefficients);
        for (int i = 0; i < countOfPoints; i++) {
            localRender(
                width,
                height,
                countOfIterations,
                countOfSymmetricalParts,
                affineCoefficientsAndColors,
                transforms
            );
        }
        return pixels;
    }

    @SuppressWarnings("NestedIfDepth")
    private void localRender(
        int width,
        int height,
        int countOfIterations,
        int countOfSymmetricalParts,
        AffineCoefficientsAndColor[] affineCoefficientsAndColors,
        Transformation[] transforms
    ) {
        Random random = new Random();
        Point point = initPoint();
        final int shiftToSkippableIterations = -20;
        for (int j = shiftToSkippableIterations; j < countOfIterations; j++) {
            int coefficientIndex = random.nextInt(0, affineCoefficientsAndColors.length);
            point = affineCoefficientsAndColors[coefficientIndex].transformByCoefficients(point);
            point = transforms[random.nextInt(0, transforms.length)].apply(point);
            if (j > 0) {
                double theta = 0;
                for (int s = 0; s < countOfSymmetricalParts; s++) {
                    theta += ((2 * Math.PI) / countOfSymmetricalParts);
                    point = rotate(point, theta);
                    if (
                        isInRange(point.x(), minXCoefficient, maxXCoefficient)
                        && isInRange(point.y(), minYCoefficient, maxYCoefficient)
                    ) {
                        int x1 = width - (int) ((maxXCoefficient - point.x()) / (2 * maxXCoefficient) * width);
                        int y1 = height - (int) ((maxYCoefficient - point.y()) / (2 * maxYCoefficient) * height);
                        if (x1 < width && y1 < height) {
                            if (pixels[x1][y1].getNumberOfHits() == 0) {
                                pixels[x1][y1].setColor(
                                    affineCoefficientsAndColors[coefficientIndex].red(),
                                    affineCoefficientsAndColors[coefficientIndex].green(),
                                    affineCoefficientsAndColors[coefficientIndex].blue()
                                );
                            } else {
                                pixels[x1][y1].setColor(
                                    (pixels[x1][y1].getRed()
                                        + affineCoefficientsAndColors[coefficientIndex].red()) / 2,
                                    (pixels[x1][y1].getGreen()
                                        + affineCoefficientsAndColors[coefficientIndex].green()) / 2,
                                    (pixels[x1][y1].getBlue()
                                        + affineCoefficientsAndColors[coefficientIndex].blue()) / 2
                                );
                            }
                            pixels[x1][y1].increaseCountOfHits();
                        }
                    }
                }
            }
        }
    }

    private AffineCoefficientsAndColor[] initStartCoefficients(int countOfCoefficients) {
        AffineCoefficientsAndColor[] affineCoefficientsAndColors = new AffineCoefficientsAndColor[countOfCoefficients];
        for (int i = 0; i < affineCoefficientsAndColors.length; i++) {
            affineCoefficientsAndColors[i] = AffineCoefficientsAndColor.getCoefficients();
        }
        return affineCoefficientsAndColors;
    }

    private Point initPoint() {
        Random random = new Random();
        double x = random.nextDouble(minXCoefficient, maxXCoefficient);
        double y = random.nextDouble(minYCoefficient, maxYCoefficient);
        return new Point(x, y);
    }

    private boolean isInRange(double value, double leftLim, double rightLim) {
        return value >= leftLim && value <= rightLim;
    }

    public Point rotate(Point point, double theta) {
        var x = point.x();
        var y = point.y();
        double xRotated = x * Math.cos(theta) - y * Math.sin(theta);
        double yRotated = x * Math.sin(theta) + y * Math.cos(theta);
        return new Point(xRotated, yRotated);
    }
}
