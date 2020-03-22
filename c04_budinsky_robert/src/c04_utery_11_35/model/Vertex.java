package c04_utery_11_35.model;

import transforms.Point3D;

import java.awt.*;

public class Vertex {

    private final Point3D point;
    private final Color color;
    public final double x, y, z, w;

    public Vertex(Point3D point, Color color) {
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

    public Color getColor() {
        return color;
    }

//    add(Vertex)
//
//    mul(double)

}
