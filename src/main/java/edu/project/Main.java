package edu.project;

import edu.project.transformations.CardioidTransform;
import edu.project.transformations.DiscTransform;
import edu.project.transformations.PolarTransform;
import edu.project.transformations.SinTransform;
import edu.project.transformations.SphereTransform;
import java.nio.file.Path;

public class Main {
    private Main() {}

//    TODO добавить пооержку задания цвета вручную

    @SuppressWarnings("MagicNumber")
    public static void main(String[] args) {
        FractalFlame flame = new FractalFlame(
            3840,
            2160,
            new MultithreadHistogramGenerator(),
            300,
            100_000,
            3,
            20,
            new CardioidTransform(),
            new DiscTransform(),
            new SphereTransform(),
            new SinTransform(),
            new PolarTransform()
        );
        FlameRenderer.render(
            FlameRenderer.logarithmicGammaCorrection(flame, 0.2),
            "png",
            Path.of("src", "main", "java", "edu", "project", "images")
        );
    }
}
