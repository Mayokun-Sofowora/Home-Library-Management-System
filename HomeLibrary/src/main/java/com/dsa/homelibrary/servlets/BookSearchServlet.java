package com.dsa.homelibrary.servlets;

import com.dsa.homelibrary.utils.DatabaseUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class BookSearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchTerm = request.getParameter("q");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            try (Connection conn = DatabaseUtil.getConnection();
                 PreparedStatement statement = conn.prepareStatement("SELECT * FROM libros WHERE titulo LIKE ?")) {

                statement.setString(1, "%" + searchTerm + "%");
                ResultSet resultSet = statement.executeQuery();

                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Resultado de la búsqueda</title></head><body>");
                out.println("<h2>Resultado de la búsqueda:</h2>");
                out.println("<ul>");
                while (resultSet.next()) {
                    String titulo = resultSet.getString("titulo");
                    String autor = resultSet.getString("autor");
                    out.println("<li>" + titulo + " - " + autor + "</li>");
                }
                out.println("</ul>");
                out.println("</body></html>");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><head><title>Error</title></head><body>");
            out.println("<h2>Error: Debes proporcionar un término de búsqueda</h2>");
            out.println("</body></html>");
        }
    }
}
