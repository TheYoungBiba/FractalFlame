package edu.project.transformations;

import edu.project.Point;
import edu.project.Transformation;

public class SinTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = Math.sin(point.x());
        double y = Math.sin(point.y());
        return new Point(x, y);
    }
}
