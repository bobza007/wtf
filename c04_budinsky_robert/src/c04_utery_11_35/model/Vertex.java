package c04_utery_11_35.model;

import transforms.Col;
import transforms.Point3D;

import java.awt.*;

public class Vertex {

    private final Point3D point;
    private final Col color;
    public final double x, y, z, w;

    public Vertex(Point3D point, Col color) {
        this.point = point;
        this.color = color;
        x = point.getX();
        y = point.getY();
        z = point.getZ();
        w = point.getW();
    }

    public Point3D getPoint() {
        return point;
    }

    public Col getColor() {
        return color;
    }


}
