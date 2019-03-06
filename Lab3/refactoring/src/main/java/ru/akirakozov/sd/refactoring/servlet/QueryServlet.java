package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        HTMLWriter htmlWriter = new HTMLWriter(response);
        if ("max".equals(command)) {
            DBManager.executeRequest(htmlWriter, "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1",
                    "<h1>Product with max price: </h1>", Answer.PAIR);
        } else if ("min".equals(command)) {
            DBManager.executeRequest(htmlWriter, "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1",
                    "<h1>Product with min price: </h1>", Answer.PAIR);
        } else if ("sum".equals(command)) {
            DBManager.executeRequest(htmlWriter, "SELECT SUM(price) FROM PRODUCT", "Summary price: ", Answer.INT);
        } else if ("count".equals(command)) {
            DBManager.executeRequest(htmlWriter, "SELECT COUNT(*) FROM PRODUCT", "Number of products: ", Answer.INT);
        } else {
            htmlWriter.writeFull(null, "Unknown command: " + command, Answer.NOTHING);
        }
    }

}
