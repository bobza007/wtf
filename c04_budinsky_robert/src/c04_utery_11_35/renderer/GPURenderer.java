package c04_utery_11_35.renderer;

import c04_utery_11_35.model.Element;
import c04_utery_11_35.model.Vertex;
import transforms.Mat4;

import java.awt.*;
import java.util.List;

public interface GPURenderer {

    void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib);

    void clear();

    Mat4 getModel();

    void setModel(Mat4 model);

    Mat4 getView();

    void setView(Mat4 view);

    Mat4 getProjection();

    void setProjection(Mat4 projection);

    void setShader(Shader<Vertex, Color> shader);

}
