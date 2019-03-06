import java.util.Scanner;

public abstract class Graph {

    DrawingApi drawingApi;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
    }

    public abstract void readGraph(Scanner in);
    public abstract void drawGraph();
}
