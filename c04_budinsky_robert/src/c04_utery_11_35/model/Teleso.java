package c04_utery_11_35.model;

import transforms.Mat4;
import transforms.Mat4Identity;

import java.util.ArrayList;
import java.util.List;

public abstract class Teleso {
    List<Vertex> vertexBuffer = new ArrayList<>();
    List<Integer> indexBuffer = new ArrayList<>();
    List<Element> elements = new ArrayList<>();
    //Element element = new Element()

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }

    public List<Element> getElements() {
        return elements;
    }
}
