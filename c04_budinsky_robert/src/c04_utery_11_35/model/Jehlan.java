package c04_utery_11_35.model;

import transforms.Col;
import transforms.Point3D;

public class Jehlan extends Teleso {
    public Jehlan(){
        vertexBuffer.add(new Vertex(new Point3D(-2, -2, -2),new Col(0xFF00FA)));
        vertexBuffer.add(new Vertex(new Point3D(2, -2, 2),new Col(0x760394)));
        vertexBuffer.add(new Vertex(new Point3D(-2, 2,2),new Col(0x000000)));
        vertexBuffer.add(new Vertex(new Point3D(2,2, -2),new Col(0xFF0000)));

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

        element = (new Element(ElementType.TRIANGLE,12,0));
    }
}
