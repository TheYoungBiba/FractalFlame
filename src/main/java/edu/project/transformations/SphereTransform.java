package edu.project.transformations;

import edu.project.Point;
import edu.project.Transformation;

public class SphereTransform implements Transformation {
    @Override
    public Point apply(Point point) {
        double x = point.x() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double y = point.y() / (Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        return new Point(x, y);
    }
}
