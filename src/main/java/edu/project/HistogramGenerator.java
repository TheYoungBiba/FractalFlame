package edu.project;

public interface HistogramGenerator {
    Pixel[][] generate(
        int width,
        int height,
        int countOfPoints,
        int countOfIterations,
        int countOfSymmetricalParts,
        int countOfAffineCoefficients,
        Transformation... transforms
    );
}
