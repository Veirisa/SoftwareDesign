package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

public class Tester {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static PrintWriter writer;

    @BeforeEach
    void init() throws IOException {

        request = mock(HttpServletRequest.class);
        when(request.getParameter("name")).thenReturn("product");
        response = mock(HttpServletResponse.class);
        writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        String sql = "DROP TABLE IF EXISTS PRODUCT";
        DBManager.executeRequest(null, sql, "", Answer.NOTHING);
        sql = "CREATE TABLE IF NOT EXISTS PRODUCT" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        DBManager.executeRequest(null, sql, "", Answer.NOTHING);
    }

    @Test
    void getTest() throws RuntimeException, IOException {

        AddProductServlet addServlet = new AddProductServlet();
        GetProductsServlet getServlet = new GetProductsServlet();

        for (int i = 0; i < 1; ++i) {
            when(request.getParameter("price")).thenReturn(String.valueOf(i));
            addServlet.doGet(request, response);
        }

        getServlet.doGet(request, response);
        for (int i = 0; i < 1; ++i) {
            verify(writer, times(1)).println("product\t" + String.valueOf(i) + "</br>");
        }
    }

    @Test
    void queryTest() throws RuntimeException, IOException {

        AddProductServlet addServlet = new AddProductServlet();
        QueryServlet quetyServlet = new QueryServlet();

        for (int i = 0; i < 10; ++i) {
            when(request.getParameter("price")).thenReturn(String.valueOf(i));
            addServlet.doGet(request, response);
        }

        when(request.getParameter("command")).thenReturn("max");
        quetyServlet.doGet(request, response);
        verify(writer, times(1)).println("product\t9</br>");
        when(request.getParameter("command")).thenReturn("min");
        quetyServlet.doGet(request, response);
        verify(writer, times(1)).println("product\t0</br>");
        when(request.getParameter("command")).thenReturn("sum");
        quetyServlet.doGet(request, response);
        verify(writer, times(1)).println("45");
        when(request.getParameter("command")).thenReturn("count");
        quetyServlet.doGet(request, response);
        verify(writer, times(1)).println("10");
    }

    @Test
    void randomTest() throws RuntimeException, IOException {

        AddProductServlet addServlet = new AddProductServlet();
        GetProductsServlet getServlet = new GetProductsServlet();
        QueryServlet quetyServlet = new QueryServlet();
        Random generator = new Random();

        when(request.getParameter("price")).thenReturn("1000");
        addServlet.doGet(request, response);
        List<Integer> addedPrices = new LinkedList<Integer>();
        addedPrices.add(1000);
        Integer maxValue = 1000;
        Integer minValue = 1000;
        Integer sum = 1000;
        Integer count = 1;

        for (int t = 0; t < 100; ++t) {
            if (generator.nextBoolean()) {
                Integer price = generator.nextInt() % 2000;
                when(request.getParameter("price")).thenReturn(price.toString());
                addServlet.doGet(request, response);
                addedPrices.add(price);
                maxValue = Math.max(maxValue, price);
                minValue = Math.min(minValue, price);
                sum += price;
                ++count;
            } else {
                switch (generator.nextInt() % 5) {
                    case 0:
                        when(request.getParameter("command")).thenReturn("max");
                        quetyServlet.doGet(request, response);
                        verify(writer, atLeastOnce()).println("product\t" + maxValue.toString() + "</br>");
                        break;
                    case 1:
                        when(request.getParameter("command")).thenReturn("min");
                        quetyServlet.doGet(request, response);
                        verify(writer, atLeastOnce()).println("product\t" + minValue.toString()+ "</br>");
                        break;
                    case 2:
                        when(request.getParameter("command")).thenReturn("sum");
                        quetyServlet.doGet(request, response);
                        verify(writer, atLeastOnce()).println(sum.toString());
                        break;
                    case 3:
                        when(request.getParameter("command")).thenReturn("count");
                        quetyServlet.doGet(request, response);
                        verify(writer, atLeastOnce()).println(count.toString());
                        break;
                    default:
                        getServlet.doGet(request, response);
                        for (Integer curPrice : addedPrices) {
                            verify(writer, atLeastOnce()).println("product\t" + curPrice.toString() + "</br>");
                        }
                }
            }
        }
    }

}
