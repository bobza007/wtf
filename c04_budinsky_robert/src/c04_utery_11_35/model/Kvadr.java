package c04_utery_11_35.model;

import transforms.Point3D;

import java.awt.*;
import java.util.List;

public class Kvadr extends Teleso {
    public Kvadr() {
        vertexBuffer.add(new Vertex(new Point3D(0,-2,-3),new Color(0x00FF00)));
        vertexBuffer.add(new Vertex(new Point3D(1,-2,-3),new Color(0xFF0006)));
        vertexBuffer.add(new Vertex(new Point3D(1,4,-3),new Color(0x1B12FF)));
        vertexBuffer.add(new Vertex(new Point3D(0,4,-3),new Color(0x08FFEB)));
        vertexBuffer.add(new Vertex(new Point3D(0,-2,1),new Color(0xFFFBF9)));
        vertexBuffer.add(new Vertex(new Point3D(1,-2,1),new Color(0x000000)));
        vertexBuffer.add(new Vertex(new Point3D(1,4,1),new Color(0xFF00EE)));
        vertexBuffer.add(new Vertex(new Point3D(0,4,1),new Color(0xDC1FFF)));


//1
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
//2
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(0);
//3
        indexBuffer.add(4);
        indexBuffer.add(5);
        indexBuffer.add(6);
//4
        indexBuffer.add(6);
        indexBuffer.add(7);
        indexBuffer.add(4);
//5
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(5);
//6
        indexBuffer.add(5);
        indexBuffer.add(4);
        indexBuffer.add(0);
//7
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(7);
//8
        indexBuffer.add(7);
        indexBuffer.add(6);
        indexBuffer.add(2);
//9
        indexBuffer.add(1);
        indexBuffer.add(2);
        indexBuffer.add(6);
//10
        indexBuffer.add(6);
        indexBuffer.add(5);
        indexBuffer.add(1);
//11
        indexBuffer.add(0);
        indexBuffer.add(3);
        indexBuffer.add(7);
//12
        indexBuffer.add(7);
        indexBuffer.add(4);
        indexBuffer.add(0);

        elements.add(new Element(ElementType.TRIANGLE, 36, 0));
    }
}