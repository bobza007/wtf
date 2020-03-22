package c04_utery_11_35.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class Raster extends JPanel {

    private final BufferedImage img; // objekt pro zápis pixelů
    private final Graphics g; // objekt nad kterým jsou k dispozici grafické funkce
    private static final int FPS = 1000 / 30; // 30 FPS

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    Raster() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // inicializace image, nastavení rozměrů (nastavení typu - pro nás nedůležité)
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        g = img.getGraphics();
        setTimer();
        clear();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        // pro zájemce - co dělá observer - https://stackoverflow.com/a/1684476
    }

    private void setTimer() {
        // časovač, který 30 krát za vteřinu obnoví obsah plátna aktuálním img
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // říct plátnu, aby zobrazilo aktuální img
                repaint();
            }
        }, 0, FPS);
    }

    public void clear() {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public void drawPixel(int x, int y, int color) {
        if (x >= 0 && y >= 0 && x < Raster.WIDTH && y < Raster.HEIGHT) {
            img.setRGB(x, y, color);
        }
    }

    public int getPixel(int x, int y) {
        return img.getRGB(x, y);
    }

    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        g.setColor(color);
        g.drawLine(x1, y1, x2, y2);
    }

    public void drawLine(double x1, double y1, double x2, double y2, Color color) {
        drawLine(
                (int) Math.round(x1),
                (int) Math.round(y1),
                (int) Math.round(x2),
                (int) Math.round(y2),
                color
        );
    }

}
