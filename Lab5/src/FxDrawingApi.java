import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class FxDrawingApi extends Application implements DrawingApi {

    private final static int WIDTH = 600;
    private final static int HEIGHT = 600;
    private final static double R = 10.0;

    private static ArrayList<Vertex> circles = new ArrayList<>();
    private static ArrayList<Pair<Vertex, Vertex>> lines = new ArrayList<>();

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
        Application.launch();
    }

    @Override
    public void start(Stage stage) {
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext g2D = canvas.getGraphicsContext2D();

        g2D.setStroke(Color.PINK);
        for (Pair<Vertex, Vertex> line : lines) {
            g2D.beginPath();
            g2D.moveTo(line.getKey().x, line.getKey().y);
            g2D.lineTo(line.getValue().x, line.getValue().y);
            g2D.stroke();
            g2D.closePath();;
        }
        g2D.setFill(Color.BLUE);
        for (Vertex circle : circles) {
            g2D.fillOval(circle.x - R / 2, circle.y - R / 2, R, R);
        }

        root.getChildren().add(canvas);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
