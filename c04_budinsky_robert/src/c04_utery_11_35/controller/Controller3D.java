package c04_utery_11_35.controller;

import c04_utery_11_35.model.*;
import c04_utery_11_35.renderer.Renderer3D;
import c04_utery_11_35.renderer.Shader;
import c04_utery_11_35.view.Raster;
import transforms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Controller3D {

    private Renderer3D renderer3D;
    private Camera camera;
    private Shader<Vertex, Color> testShader;
    private Teleso krychle;
    private Teleso jehlan;
    private Mat4 projection;
    private List<Element> elements;


    private int mx, my;


    public Controller3D(Raster raster) {
        initObjects(raster);
        initListeners(raster);
    }

    private void display() {
        renderer3D.clear();
        renderer3D.setModel(new Mat4Identity());
        renderer3D.setView(camera.getViewMatrix());
        renderer3D.setProjection(projection);
        renderer3D.setShader(testShader);
        renderer3D.setShader(vertex -> new Color(Math.round(vertex.x % 255), 0, 0));
    }

    private void initObjects(Raster raster) {
        renderer3D = new Renderer3D(raster);
        camera =new Camera().withPosition(new Vec3D(0.5,-6,2))
                .withAzimuth(Math.toRadians(90)).withZenith(Math.toRadians(-20));
        resetCamera();
        krychle = new Kvadr();
        jehlan = new Jehlan();

        testShader = vertex -> {
            long zbytek = Math.round(vertex.x) % 2;

            return (zbytek == 0) ? Color.GREEN : Color.BLUE;
        };
        projection = new Mat4PerspRH(Math.PI/4, Raster.HEIGHT/(float)Raster.WIDTH, 1, 200);
        renderer3D.setModel(new Mat4Identity());
        renderer3D.setView(camera.getViewMatrix());
        renderer3D.setProjection(projection);
        elements = new ArrayList<>();
        elements.add(new Element(ElementType.TRIANGLE, 36, 0));
        renderer3D.clear();
        renderer3D.draw(elements,jehlan.getVertexBuffer(),jehlan.getIndexBuffer());
        renderer3D.draw(elements,krychle.getVertexBuffer(),krychle.getIndexBuffer());
    }

    private void resetCamera() {
        camera = new Camera(
                new Vec3D(0.5, -6, 2),
                Math.toRadians(90),
                Math.toRadians(-40),
                1, true
        );
        renderer3D.setView(camera.getViewMatrix());
    }

    private void initListeners(Raster raster) {
        raster.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mx = e.getX();
                my = e.getY();
            }
        });

        raster.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {

                int tmpX = mx;
                int tmpY = my;

                mx = e.getX();
                my = e.getY();

                int xDiff = tmpX - mx;
                int yDiff = tmpY - my;
                if (SwingUtilities.isLeftMouseButton(e)) {
                    double diff = (mx - e.getX()) / 100.0;
                    double azimuth = camera.getAzimuth() + diff;
                    camera = camera.withAzimuth(azimuth);
                    renderer3D.setView(camera.getViewMatrix());
                }

                if(SwingUtilities.isLeftMouseButton(e)){
                    renderer3D.clear();
                    renderer3D.setModel(renderer3D.getModel().mul(new Mat4RotXYZ(0,-yDiff * Math.PI / 180, xDiff * Math.PI / 180)));
                    renderer3D.draw(elements,jehlan.getVertexBuffer(),jehlan.getIndexBuffer());
                    renderer3D.draw(elements,krychle.getVertexBuffer(),krychle.getIndexBuffer());

                }


            }
        });
        raster.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyChar() == 'w') camera = camera.forward(0.5);
                if (e.getKeyChar() == 'a') camera = camera.left(0.5);
                if (e.getKeyChar() == 's') camera = camera.backward(0.5);
                if (e.getKeyChar() == 'd') camera = camera.right(0.5);

                elements = new ArrayList<>();
                elements.add(new Element(ElementType.TRIANGLE, 36, 0));
                renderer3D.clear();
                renderer3D.setView(camera.getViewMatrix());
                renderer3D.draw(elements,jehlan.getVertexBuffer(),jehlan.getIndexBuffer());
                renderer3D.draw(elements,krychle.getVertexBuffer(),krychle.getIndexBuffer());

            }
        });
    }
}
