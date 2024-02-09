package edu.project;

public class FractalFlame {
    private final Pixel[][] histogram;
    private final int width;
    private final int height;

    @SuppressWarnings("ParameterNumber")
    public FractalFlame(
        int width,
        int height,
        HistogramGenerator generator,
        int countOfPoints,
        int countOfIterations,
        int countOfSymmetricalParts,
        int countOfAffineCoefficients,
        Transformation... transforms
    ) {
        this.width = width;
        this.height = height;
        histogram = generator.generate(
            width,
            height,
            countOfPoints,
            countOfIterations,
            countOfSymmetricalParts,
            countOfAffineCoefficients,
            transforms
        );
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Pixel[][] getHistogram() {
        return histogram;
    }
}
