import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Scanner;

public class EdgesListGraph extends Graph {

    private int sizeVertices = 0;
    private ArrayList<Pair<Integer, Integer>> edges = new ArrayList<>();

    public EdgesListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void readGraph(Scanner in) {
        int sizeEdges = in.nextInt();
        int left, right;
        for (int i = 0; i < sizeEdges; ++i) {
            left = in.nextInt();
            right = in.nextInt();
            edges.add(new Pair<>(left, right));
            sizeVertices = Math.max(sizeVertices, Math.max(left, right) + 1);
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
        for (Pair<Integer, Integer> edge : edges) {
            drawingApi.drawLine(vertices.get(edge.getKey()), vertices.get(edge.getValue()));
        }
        drawingApi.showDrawing();
    }
}
