public interface DrawingApi {
    long getDrawingAreaWidth();
    long getDrawingAreaHeight();
    double getCircleRadius();
    void drawCircle(Vertex v);
    void drawLine(Vertex v1, Vertex v2);
    void showDrawing();
}
