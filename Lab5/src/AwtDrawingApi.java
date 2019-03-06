import javafx.util.Pair;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class AwtDrawingApi extends Frame implements DrawingApi {

    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final static double R = 10.0;

    private static ArrayList<Vertex> circles = new ArrayList<>();
    private static ArrayList<Pair<Vertex, Vertex>> lines = new ArrayList<>();

    AwtDrawingApi() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        setSize(WIDTH, HEIGHT);
        setVisible(true);
    }

    @Override
    public long getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public long getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public double getCircleRadius() {
        return R;
    }

    @Override
    public void drawCircle(Vertex v) {
        circles.add(v);
    }

    @Override
    public void drawLine(Vertex v1, Vertex v2) {
        lines.add(new Pair<>(v1, v2));
    }

    @Override
    public void showDrawing() {
        Graphics2D g2D = (Graphics2D)getGraphics();
        g2D.setPaint(Color.pink);
        for (Pair<Vertex, Vertex> line : lines) {
            g2D.drawLine((int)line.getKey().x, (int)line.getKey().y, (int)line.getValue().x, (int)line.getValue().y);
        }
        g2D.setPaint(Color.blue);
        for (Vertex circle : circles) {
            g2D.fill(new Ellipse2D.Double(circle.x - R / 2, circle.y - R / 2, R, R));
        }
    }
}
