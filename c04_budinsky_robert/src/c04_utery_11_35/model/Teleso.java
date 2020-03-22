package c04_utery_11_35.model;


import java.util.ArrayList;
import java.util.List;

public abstract class Teleso {
    List<Vertex> vertexBuffer = new ArrayList<>();
    List<Integer> indexBuffer = new ArrayList<>();
    Element element;

    public List<Vertex> getVertexBuffer() {
        return vertexBuffer;
    }

    public List<Integer> getIndexBuffer() {
        return indexBuffer;
    }
}
