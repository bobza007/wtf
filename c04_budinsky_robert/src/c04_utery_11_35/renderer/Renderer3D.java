package c04_utery_11_35.renderer;

import c04_utery_11_35.model.Element;
import c04_utery_11_35.model.ElementType;
import c04_utery_11_35.model.Vertex;
import c04_utery_11_35.view.Raster;
import transforms.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public class Renderer3D implements GPURenderer {

    private final Raster raster;
    private Mat4 model, view, projection;

    private final ZBuffer<Double> zb;
    private Shader<Vertex, Color> shader;

    public Renderer3D(Raster raster) {
        this.raster = raster;
        view = new Mat4Identity();

        projection = new Mat4PerspRH(Math.PI / 4, Raster.HEIGHT / (float) Raster.WIDTH, 1, 200);

        zb = new ZBuffer<>(new Double[Raster.WIDTH][Raster.HEIGHT]);
    }
    @Override
    public void draw(List<Element> elements, List<Vertex> vb, List<Integer> ib) {
        for (Element element : elements) {
            final int start = element.getStart();
            final int count = element.getCount();
            if (element.getElementType() == ElementType.TRIANGLE) {
                for (int i = start; i < ib.size()-1; i += 3) {
                    final Integer i1 = ib.get(i);
                    final Integer i2 = ib.get(i + 1);
                    final Integer i3 = ib.get(i + 2);
                    final Vertex v1 = vb.get(i1);
                    final Vertex v2 = vb.get(i2);
                    final Vertex v3 = vb.get(i3);

                    prepareTriangle(v1, v2, v3);
                }
            } else if (element.getElementType() == ElementType.LINE) {

            } else {
                // point
            }
        }
    }

    private void prepareTriangle(Vertex a, Vertex b, Vertex c) {
        // 1. transformace vrcholů
        a = new Vertex(a.getPoint().mul(model).mul(view).mul(projection), a.getColor());
        
        b = new Vertex(b.getPoint().mul(model).mul(view).mul(projection), b.getColor());
        c = new Vertex(c.getPoint().mul(model).mul(view).mul(projection), c.getColor());

        // 2. ořezání
        // rychlé ořezání zobrazovacím objemem
        // vyhodí trojúhelníky, které jsou celé mimo zobrazovací objem

        //x
        if (a.x < -a.w && b.x < -b.w && c.x < -c.w) return;
        if (a.x > a.w && b.x > b.w && c.x > c.w) return;

        //y
        if (a.y < -a.w && b.y < -b.w && c.y < -c.w) return;
        if (a.y > a.w && b.y > b.w && c.y > c.w) return;

        //z
        if (a.z < -a.w && b.z < -b.w && c.z < -c.w) return;
        if (a.z > a.w && b.z > b.w && c.z > c.w) return;

        // 3. seřazení trojúhelníků podle souřadnice z
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }
        if (b.z < c.z) {
            Vertex temp = b;
            b = c;
            c = temp;
        }
        if (a.z < b.z) {
            Vertex temp = a;
            a = b;
            b = temp;
        }

        // 4. ořezání a interpolace podle hrany Z
        if (a.z < 0) {
            // a.z je největší a je záporné, takže celý trojúhelník není vidět
            return;
        } else if (b.z < 0) {

            double t = (0 - a.z) / (b.z - a.z);
            // double t = a.z / (a.z - b.z)
            Point3D dPoint = a.getPoint().mul(1 - t).add(b.getPoint().mul(t));
            Vertex ab = new Vertex(dPoint, a.getColor());
            // nekorektně barva, měla by se také interpolovat; volitelně

            double t2 = -a.z / (c.z - a.z);
            Vertex ac = new Vertex(a.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());
            // lze vytvořit funkci pro ořezání, aby se neopakoval kód

            drawTriangle(a, ab, ac);

        } else if (c.z < 0) {

            // TODO ac, bc

            double t = (0 - a.z) / (c.z - a.z);
            Point3D dPoint = a.getPoint().mul(1 - t).add(c.getPoint().mul(t));
            Vertex ac = new Vertex(dPoint, a.getColor());

            double t2 = (0-b.z) / (c.z - b.z);
            Vertex bc = new Vertex(b.getPoint().mul(1 - t2).add(c.getPoint().mul(t2)), c.getColor());

            drawTriangle(a, b, bc);
            drawTriangle(a, bc, ac);

        } else {
            drawTriangle(a, b, c);
        }
    }

    private void drawTriangle(Vertex a, Vertex b, Vertex c) {

        Color color1 = a.getColor();
        Color color2 = b.getColor();
        Color color3 = c.getColor();

        Optional<Vec3D> d1 = a.getPoint().dehomog();
        Optional<Vec3D> d2 = b.getPoint().dehomog();
        Optional<Vec3D> d3 = c.getPoint().dehomog();

        // zahodit trojúhelník, pokud některý vrchol má w==0 (nelze provést dehomogenizaci)
        if (!d1.isPresent() || !d2.isPresent() || !d3.isPresent()) return;

        Vec3D v1 = d1.get();
        Vec3D v2 = d2.get();
        Vec3D v3 = d3.get();

        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);
        v3 = transformToWindow(v3);

//        Vertex aa = new Vertex(new Point3D(v1), color1);

        if (v1.getY() > v2.getY()) {
            Vec3D temp = v1;
            v1 = v2;
            v2 = temp;
            Color tempC = color1;
            color1 = color2;
            color2 = tempC;
        }
        if (v2.getY() > v3.getY()) {
            Vec3D temp = v2;
            v2 = v3;
            v3 = temp;
            Color tempC = color2;
            color2 = color3;
            color3 = tempC;
        }
        if (v1.getY() > v2.getY()) {
            Vec3D temp = v1;
            v1 = v2;
            v2 = temp;
            Color tempC = color1;
            color1 = color2;
            color2 = tempC;
        }

        for (int y = (int) Math.max((int) v1.getY()+1,0); y <= Math.min((int) v2.getY(), Raster.HEIGHT-1); y++) {
            double t1 = (y - v2.getY()) / (v1.getY() - v2.getY());
            Vec3D v12 = v2.mul(1 - t1).add(v1.mul(t1));
            Color c12 = new Color((int)(color2.getRGB()*(1-t1)+color1.getRGB()*t1));

            double t2 = (y - v3.getY()) / (v1.getY() - v3.getY());
            Vec3D v13 = v3.mul(1 - t2).add(v1.mul(t2));
            Color c13 = new Color((int)(color3.getRGB()*(1-t2)+color1.getRGB()*t2));
            fillLine(y, v12, v13, c12, c13);
        }

        for (int y = (int) Math.max((int) v2.getY()+1,0); y <= Math.min((int) v3.getY(), Raster.HEIGHT-1); y++) {
            double t1 = (y - v2.getY()) / (v3.getY() - v2.getY());
            Vec3D v23 = v2.mul(1 - t1).add(v3.mul(t1));
            Color c23 = new Color((int)(color3.getRGB()*(1-t1)+color2.getRGB()*t1));
            double t2 = (y - v3.getY()) / (v1.getY() - v3.getY());
            Vec3D v13 = v3.mul(1 - t2).add(v1.mul(t2));
            Color c13 = new Color((int)(color3.getRGB()*(1-t2)+color1.getRGB()*t2));
            fillLine(y, v23, v13, c23, c13);
        }
    }

    private void fillLine(int y, Vec3D a, Vec3D b, Color colorA, Color colorB) {
        if (a.getX() > b.getX()) {
            Vec3D temp = a;
            a = b;
            b = temp;
            Color tempC = colorA;
            colorA = colorB;
            colorB = tempC;

        }
        int start = (int) Math.max(a.getX()+1, 0);
        int end = (int) Math.min(b.getX(),Raster.WIDTH - 1);
        for (int x = start; x <=end ; x++) {
            double t = (x - a.getX()) / (b.getX() - a.getX());
            Color color = new Color((int)(colorB.getRGB()*(1-t)+colorA.getRGB()*t));
            double z = a.getZ() * (1 - t) + b.getZ() * t;
            drawPixel(x, y, z, color);
        }
    }

    private void drawPixel(int x, int y, double z, Color color) {
        if(x >= Raster.WIDTH || y >= Raster.HEIGHT)
            return;
        if (zb.get(x, y) > z) {
            zb.set(z, x, y);
            raster.drawPixel(x, y, color.getRGB());
        }
    }

    private Vec3D transformToWindow(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)) // Y jde nahoru, chceme dolu
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0, 2> -> vynásobíme polovinou velikosti plátna
                .mul(new Vec3D(Raster.WIDTH / 2f, Raster.HEIGHT / 2f, 1));
    }

    @Override
    public void clear() {
        raster.clear();
        zb.clear(1d);
    }

    @Override
    public Mat4 getModel() {
        return model;
    }

    @Override
    public void setModel(Mat4 model) {
        this.model = model;
    }

    @Override
    public Mat4 getView() {
        return view;
    }

    @Override
    public void setView(Mat4 view) {
        this.view = view;
    }

    @Override
    public Mat4 getProjection() {
        return projection;
    }

    @Override
    public void setProjection(Mat4 projection) {
        this.projection = projection;
    }

    @Override
    public void setShader(Shader<Vertex, Color> shader) {
        this.shader = shader;
    }
}

