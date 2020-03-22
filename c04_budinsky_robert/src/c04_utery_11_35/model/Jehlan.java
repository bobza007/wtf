package c04_utery_11_35.model;

import transforms.Point3D;

import java.awt.*;

public class Jehlan extends Teleso {
    public Jehlan(){
        vertexBuffer.add(new Vertex(new Point3D(-2, -2, -2),new Color(0xFF00FA)));
        vertexBuffer.add(new Vertex(new Point3D(2, -2, 2),new Color(0x760394)));
        vertexBuffer.add(new Vertex(new Point3D(-2, 2,2),new Color(0x00FF33)));
        vertexBuffer.add(new Vertex(new Point3D(2,2, -2),new Color(0x00FFFD)));

//1
        indexBuffer.add(0);
        indexBuffer.add(1);
        indexBuffer.add(2);
//2
        indexBuffer.add(2);
        indexBuffer.add(0);
        indexBuffer.add(3);
//3
        indexBuffer.add(2);
        indexBuffer.add(3);
        indexBuffer.add(1);
//4
        indexBuffer.add(3);
        indexBuffer.add(0);
        indexBuffer.add(1);

        elements.add(new Element(ElementType.TRIANGLE,12,0));
    }
}
