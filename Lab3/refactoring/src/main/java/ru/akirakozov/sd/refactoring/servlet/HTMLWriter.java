package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

public class HTMLWriter {

    private HttpServletResponse response;

    public HTMLWriter(HttpServletResponse response) {
        this.response = response;
    }

    public void write(String str) throws IOException {
        response.getWriter().println(str);
    }

    public void writeFull(ResultSet rs, String header, Answer answer) throws RuntimeException {
        try {
            if (response != null) {
                write("<html><body>");
                if (!header.isEmpty()) {
                    write(header);
                }
                if (rs != null) {
                    switch (answer) {
                        case INT:
                            if (rs.next()) {
                                write(String.valueOf(rs.getInt(1)));
                            }
                            break;
                        case PAIR:
                            while (rs.next()) {
                                String name = rs.getString("name");
                                int price = rs.getInt("price");
                                write(name + "\t" + price + "</br>");
                            }
                            break;
                        default:
                    }
                }
                write("</body></html>");
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
