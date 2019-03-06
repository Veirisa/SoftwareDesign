import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        if (args.length != 2 || args[0] == null || args[1] == null) {
            System.err.println("Wrong arguments");
        }

        DrawingApi drawingApi;
        String dApiStr = args[0];
        //System.out.println("Drawing API [awt / fx]: ");
        //String dApiStr = scanner.nextLine();
        switch (dApiStr) {
            case "awt":
                drawingApi = new AwtDrawingApi();
                break;
            case "fx":
                drawingApi = new FxDrawingApi();
                break;
            default:
                System.err.println("Incorrect drawing API: " + dApiStr);
                return;
        }

        Graph graph;
        String graphStr = args[1];
        //System.out.println("Graph realisation [edges / matrix]: ");
        //String graphStr = scanner.nextLine();
        switch (graphStr) {
            case "edges":
                graph = new EdgesListGraph(drawingApi);
                break;
            case "matrix":
                graph = new AdjMatrixGraph(drawingApi);
                break;
            default:
                System.err.println("Incorrect graph realisation: " + graphStr);
                return;
        }

        //System.out.println("Graph: ");
        graph.readGraph(scanner);
        graph.drawGraph();
    }
}
