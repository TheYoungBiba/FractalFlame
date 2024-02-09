package edu.project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AffineCoefficientsAndColorTest {
    @Test
    void getCoefficientWithRangeTest() {
        for (int i = 0; i < 10; i++) {
            edu.project.AffineCoefficientsAndColor testCase = edu.project.AffineCoefficientsAndColor.getCoefficients();
            assertTrue(
                Math.pow(testCase.a(), 2) + Math.pow(testCase.d(), 2) < 1 &&
                Math.pow(testCase.b(), 2) + Math.pow(testCase.e(), 2) < 1 &&
                Math.pow(testCase.a(), 2) + Math.pow(testCase.d(), 2)
                    + Math.pow(testCase.b(), 2) + Math.pow(testCase.e(), 2)
                    < 1 + Math.pow(testCase.a() * testCase.e() - testCase.b() * testCase.d(), 2)
            );
        }
    }

    @Test
    void getCoefficientTest() {
        for (int i = 0; i < 10; i++) {
            edu.project.AffineCoefficientsAndColor testCase = edu.project.AffineCoefficientsAndColor.getCoefficients(3);
            assertTrue(
                Math.pow(testCase.a(), 2) + Math.pow(testCase.d(), 2) < 1 &&
                    Math.pow(testCase.b(), 2) + Math.pow(testCase.e(), 2) < 1 &&
                    Math.pow(testCase.a(), 2) + Math.pow(testCase.d(), 2)
                        + Math.pow(testCase.b(), 2) + Math.pow(testCase.e(), 2)
                        < 1 + Math.pow(testCase.a() * testCase.e() - testCase.b() * testCase.d(), 2)
            );
        }
    }
}
