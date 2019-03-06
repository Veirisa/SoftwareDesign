import java.util.ArrayList;
import java.util.Scanner;

public class AdjMatrixGraph extends Graph {

    private int sizeVertices = 0;
    private ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

    public AdjMatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(Scanner in) {
        sizeVertices = in.nextInt();
        for (int i = 0; i < sizeVertices; ++i) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < sizeVertices; ++j) {
                matrix.get(i).add(in.nextInt());
            }
        }
    }

    @Override
    public void drawGraph() {
        if (sizeVertices == 0) {
            return;
        }
        double partHeight = (double)drawingApi.getDrawingAreaHeight() / 2;
        double partWidth = (double)drawingApi.getDrawingAreaWidth() / 2;
        double radius = drawingApi.getCircleRadius();
        ArrayList<Vertex> vertices = new ArrayList<>();
        double changeAlpha = 2 * Math.PI / sizeVertices;
        double alpha = 0;
        for (int i = 0; i < sizeVertices; ++i) {
            Vertex vertex = new Vertex(Math.cos(alpha) * (partWidth - 3 * radius) + partWidth,
                                       Math.sin(alpha) * (partHeight - 3 * radius) + partHeight);
            drawingApi.drawCircle(vertex);
            vertices.add(vertex);
            alpha += changeAlpha;
        }
        for (int i = 0; i < sizeVertices; ++i) {
            for (int j = i + 1; j < sizeVertices; ++j) {
                if (matrix.get(i).get(j) == 1) {
                    drawingApi.drawLine(vertices.get(i), vertices.get(j));
                }
            }
        }
        drawingApi.showDrawing();
    }
}