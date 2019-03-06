package ru.akirakozov.sd.refactoring.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBManager {

    public static void executeRequest(HTMLWriter htmlWriter, String sql, String header, Answer answer) throws RuntimeException {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                stmt.execute(sql);
                ResultSet rs = stmt.getResultSet();
                if (htmlWriter != null) {
                    htmlWriter.writeFull(rs, header, answer);
                }
                if (rs != null) {
                    rs.close();
                }
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
