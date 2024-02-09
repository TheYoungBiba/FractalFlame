package edu.project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;

public class FlameRenderer {
    private final static int MAX_RGB_VALUE = 255;
    private final static int RED_CONFIG_VALUE = 16;
    private final static int GREEN_CONFIG_VALUE = 8;

    private FlameRenderer() {}

    public static void render(FractalFlame flame, String format, Path pathToDirectory) {
        render(flame.getHistogram(), format, pathToDirectory);
    }

    public static void render(Pixel[][] pixels, String format, Path pathToDirectory) {
        pathValidator(pathToDirectory);
        BufferedImage image = new BufferedImage(pixels.length, pixels[0].length, BufferedImage.TYPE_INT_RGB);
        for (Pixel[] pixel : pixels) {
            for (Pixel value : pixel) {
                image.setRGB(value.getX(), value.getY(), parseRGB(value));
            }
        }
        File outputFile = Path.of(
            pathToDirectory.toString(),
            "Fractal_Flame_"
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss-MM-dd-yyyy"))
                + "."
                + format
        ).toFile();
        try {
            ImageIO.write(image, format, outputFile);
        } catch (IOException e) {
            throw new RuntimeException("Exception during the writing in file");
        }
    }

    public static Pixel[][] logarithmicGammaCorrection(FractalFlame flame, double gammaIncrease) {
        Pixel[][] pixels = flame.getHistogram();
        int max = 0;
        for (int row = 0; row < flame.getWidth(); row++) {
            for (int col = 0; col < flame.getHeight(); col++) {
                if (pixels[row][col].getNumberOfHits() != 0) {
                    max = Math.max(max, pixels[row][col].getNumberOfHits());
                }
            }
        }
        double maxLog = Math.log10(max);
        for (int row = 0; row < flame.getWidth(); row++) {
            for (int col = 0; col < flame.getHeight(); col++) {
                double gammaFactor = Math.log10(pixels[row][col].getNumberOfHits()) / maxLog;
                gammaFactor += gammaFactor * gammaIncrease;
                pixels[row][col]
                    .setRed(Math.min((int) (pixels[row][col].getRed() * gammaFactor), MAX_RGB_VALUE));
                pixels[row][col]
                    .setGreen(Math.min((int) (pixels[row][col].getGreen() * gammaFactor), MAX_RGB_VALUE));
                pixels[row][col]
                    .setBlue(Math.min((int) (pixels[row][col].getBlue() * gammaFactor), MAX_RGB_VALUE));
            }
        }
        return pixels;
    }

    private static void pathValidator(Path path) {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Invalid path, it is not a directory");
        }
    }

    private static int parseRGB(Pixel pixel) {
        return (pixel.getRed() << RED_CONFIG_VALUE) | (pixel.getGreen() << GREEN_CONFIG_VALUE) | pixel.getBlue();
    }
}
